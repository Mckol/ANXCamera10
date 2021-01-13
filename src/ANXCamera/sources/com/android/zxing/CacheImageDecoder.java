package com.android.zxing;

import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.util.Log;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera2.Camera2Proxy;
import com.xiaomi.camera.base.ImageUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheImageDecoder extends Decoder {
    public static final int MAX_CACHED_COUNT = 20;
    public static final int MAX_NO_GAUSSIAN_TIME = 60000000;
    private static final String TAG = "CacheImage";
    private AtomicBoolean mCacheStarted = new AtomicBoolean(false);
    private AtomicInteger mCachedCount = new AtomicInteger(0);
    private Map<Long, Image> mCachedImageMap = new ConcurrentHashMap(20);
    private LinkedList<Image> mCachedImages = new LinkedList<>();
    private WeakReference<Camera2Proxy.AnchorPreviewCallback> mCallback;
    private ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    private Camera2Proxy.PreviewCallback mPreviewCallback = new Camera2Proxy.PreviewCallback() {
        /* class com.android.zxing.CacheImageDecoder.AnonymousClass1 */

        @Override // com.android.camera2.Camera2Proxy.PreviewCallback
        public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
            if (!CacheImageDecoder.this.isNeedImage()) {
                return true;
            }
            CacheImageDecoder.this.onPreviewFrame(image);
            return false;
        }
    };
    private Lock mReadLock = this.mLock.readLock();
    private PublishSubject<FrameInfo> mSaver = PublishSubject.create();
    private Disposable mSaverDisposable = this.mSaver.subscribeOn(Schedulers.computation()).map(new Function<FrameInfo, FrameInfo>() {
        /* class com.android.zxing.CacheImageDecoder.AnonymousClass4 */

        public FrameInfo apply(FrameInfo frameInfo) throws Exception {
            frameInfo.setImage(CacheImageDecoder.this.getAnchorImage(frameInfo.mTimestamp));
            return frameInfo;
        }
    }).map(new Function<FrameInfo, Long>() {
        /* class com.android.zxing.CacheImageDecoder.AnonymousClass3 */

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0085, code lost:
            if (r0 != null) goto L_0x00a5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a3, code lost:
            if (r0 != null) goto L_0x00a5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a5, code lost:
            r0.close();
            r10.this$0.mCachedCount.decrementAndGet();
         */
        public Long apply(FrameInfo frameInfo) throws Exception {
            Image image;
            Image image2;
            Image image3;
            ImageWrapper imageWrapper = frameInfo.mImageWrapper;
            if (imageWrapper == null || (image = imageWrapper.mImage) == null) {
                return 0L;
            }
            byte[] yUVFromPreviewImage = ImageUtil.getYUVFromPreviewImage(image);
            if (yUVFromPreviewImage != null) {
                try {
                    Camera2Proxy.AnchorPreviewCallback anchorPreviewCallback = (Camera2Proxy.AnchorPreviewCallback) CacheImageDecoder.this.mCallback.get();
                    if (anchorPreviewCallback != null) {
                        YuvImage yuvImage = new YuvImage(yUVFromPreviewImage, 17, frameInfo.mWidth, frameInfo.mHeight, null);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        yuvImage.compressToJpeg(new Rect(0, 0, frameInfo.mWidth, frameInfo.mHeight), 80, byteArrayOutputStream);
                        anchorPreviewCallback.saveBitmapAsThumbnail(BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size()), frameInfo.mWidth, frameInfo.mHeight, true, frameInfo.mImageWrapper.noGaussian);
                        byteArrayOutputStream.close();
                    } else {
                        Log.e(CacheImageDecoder.TAG, "only camera module could anchor frame");
                    }
                } catch (Exception e2) {
                    Log.e(CacheImageDecoder.TAG, "Error:" + e2.getMessage());
                    ImageWrapper imageWrapper2 = frameInfo.mImageWrapper;
                    if (imageWrapper2 != null) {
                        image2 = imageWrapper2.mImage;
                    }
                } catch (Throwable th) {
                    ImageWrapper imageWrapper3 = frameInfo.mImageWrapper;
                    if (!(imageWrapper3 == null || (image3 = imageWrapper3.mImage) == null)) {
                        image3.close();
                        CacheImageDecoder.this.mCachedCount.decrementAndGet();
                    }
                    throw th;
                }
            }
            ImageWrapper imageWrapper4 = frameInfo.mImageWrapper;
            if (imageWrapper4 != null) {
                image2 = imageWrapper4.mImage;
            }
            return Long.valueOf(frameInfo.mTimestamp);
        }
    }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
        /* class com.android.zxing.CacheImageDecoder.AnonymousClass2 */

        public void accept(Long l) throws Exception {
            Camera2Proxy.AnchorPreviewCallback anchorPreviewCallback = (Camera2Proxy.AnchorPreviewCallback) CacheImageDecoder.this.mCallback.get();
            if (l.longValue() > 0) {
                if (anchorPreviewCallback != null) {
                    anchorPreviewCallback.onFrameThumbnailSuccess(l.longValue());
                }
            } else if (anchorPreviewCallback != null) {
                anchorPreviewCallback.onFrameThumbnailFail();
            }
        }
    });
    private Lock mWriteLock = this.mLock.writeLock();

    /* access modifiers changed from: private */
    public static class FrameInfo {
        public String mFileName;
        public int mHeight;
        public ImageWrapper mImageWrapper;
        public int[] mStrides;
        public long mTimestamp;
        public int mWidth;

        public FrameInfo(long j, int i, int i2, int[] iArr, String str) {
            this.mTimestamp = j;
            this.mWidth = i;
            this.mHeight = i2;
            this.mStrides = iArr;
            this.mFileName = str;
        }

        public void setImage(ImageWrapper imageWrapper) {
            this.mImageWrapper = imageWrapper;
        }
    }

    /* access modifiers changed from: private */
    public static class ImageWrapper {
        public Image mImage;
        public boolean noGaussian;

        public ImageWrapper(Image image, boolean z) {
            this.mImage = image;
            this.noGaussian = z;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private ImageWrapper getAnchorImage(long j) {
        ImageWrapper imageWrapper;
        this.mReadLock.lock();
        Image image = null;
        try {
            Log.d(TAG, "target timestamp is " + j);
            if (this.mCachedImageMap.containsKey(Long.valueOf(j))) {
                Image remove = this.mCachedImageMap.remove(Long.valueOf(j));
                Log.d(TAG, "find image in cache " + j + " index " + this.mCachedImages.indexOf(remove));
                this.mCachedImages.remove(remove);
                imageWrapper = new ImageWrapper(remove, true);
            } else {
                Log.d(TAG, "could not find image in cache " + j + " use nearest");
                long j2 = Long.MAX_VALUE;
                Iterator<Image> it = this.mCachedImages.iterator();
                while (it.hasNext()) {
                    Image next = it.next();
                    if (Math.abs(next.getTimestamp() - j) < j2) {
                        Log.d(TAG, "preview timestamp is " + next.getTimestamp());
                        j2 = next.getTimestamp();
                        image = next;
                    }
                }
                boolean z = false;
                if (image != null) {
                    Image remove2 = this.mCachedImageMap.remove(Long.valueOf(image.getTimestamp()));
                    this.mCachedImages.remove(remove2);
                    if (Math.abs(remove2.getTimestamp() - j) < 60000000) {
                        Log.d(TAG, "nearest timestamp is small than 60000000, no gaussian");
                        z = true;
                    } else {
                        Log.d(TAG, "nearest timestamp is bigger than 60000000, use gaussian");
                    }
                    imageWrapper = new ImageWrapper(remove2, z);
                } else {
                    imageWrapper = new ImageWrapper(image, false);
                }
            }
            return imageWrapper;
        } finally {
            this.mReadLock.unlock();
        }
    }

    public Camera2Proxy.PreviewCallback getAnchorPreviewCallback() {
        return this.mPreviewCallback;
    }

    @Override // com.android.zxing.Decoder
    public void init(int i) {
        Log.d(TAG, "init");
        if (this.mCacheStarted.get()) {
            Log.e(TAG, "Cache Image already init");
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.zxing.Decoder
    public boolean isNeedImage() {
        return this.mCacheStarted.get();
    }

    @Override // com.android.zxing.Decoder
    public boolean needPreviewFrame() {
        return this.mCacheStarted.get();
    }

    @Override // com.android.zxing.Decoder
    public void onPreviewFrame(Image image) {
        if (this.mCacheStarted.get()) {
            this.mWriteLock.lock();
            try {
                if (this.mCachedCount.get() >= 20) {
                    Image removeFirst = this.mCachedImages.removeFirst();
                    this.mCachedImageMap.remove(Long.valueOf(removeFirst.getTimestamp()));
                    removeFirst.close();
                    this.mCachedImages.addLast(image);
                    this.mCachedImageMap.put(Long.valueOf(image.getTimestamp()), image);
                    this.mCachedCount.getAndSet(20);
                } else {
                    this.mCachedImages.add(image);
                    this.mCachedImageMap.put(Long.valueOf(image.getTimestamp()), image);
                    this.mCachedCount.getAndIncrement();
                }
            } finally {
                this.mWriteLock.unlock();
            }
        }
    }

    @Override // com.android.zxing.Decoder
    public void onPreviewFrame(PreviewImage previewImage) {
    }

    @Override // com.android.zxing.Decoder
    public void quit() {
        super.quit();
        Log.d(TAG, "quit");
        reset();
    }

    @Override // com.android.zxing.Decoder
    public void reset() {
        Log.d(TAG, MistatsConstants.BaseEvent.RESET);
        if (!this.mCacheStarted.get()) {
            Log.d(TAG, "already reset");
        } else if (this.mCacheStarted.compareAndSet(true, false)) {
            this.mSaver.onComplete();
            this.mSaverDisposable.dispose();
            this.mSaver = null;
            this.mSaverDisposable = null;
            this.mWriteLock.lock();
            try {
                Iterator<Image> it = this.mCachedImages.iterator();
                while (it.hasNext()) {
                    it.next().close();
                }
                this.mCachedImages.clear();
                this.mCachedImageMap.clear();
                this.mCachedCount.getAndSet(0);
            } finally {
                this.mWriteLock.unlock();
            }
        }
    }

    public void saveAnchorFrameThumbnail(long j, int i, int i2, int[] iArr, String str) {
        Disposable disposable;
        if (!this.mCacheStarted.get()) {
            Log.d(TAG, "queue already quit");
        } else if (this.mSaver != null && (disposable = this.mSaverDisposable) != null && !disposable.isDisposed()) {
            this.mSaver.onNext(new FrameInfo(j, i, i2, iArr, str));
        }
    }

    public void setAnchorPreviewCallback(Camera2Proxy.AnchorPreviewCallback anchorPreviewCallback) {
        this.mCallback = new WeakReference<>(anchorPreviewCallback);
    }

    @Override // com.android.zxing.Decoder
    public void startDecode() {
        Log.d(TAG, "start decode");
        if (this.mCacheStarted.compareAndSet(false, true)) {
            Log.d(TAG, "cache image start decode success");
        }
    }

    @Override // com.android.zxing.Decoder
    public void stopDecode() {
        super.stopDecode();
    }
}
