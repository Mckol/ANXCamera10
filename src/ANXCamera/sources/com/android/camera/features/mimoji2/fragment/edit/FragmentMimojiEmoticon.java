package com.android.camera.features.mimoji2.fragment.edit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.ActivityBase;
import com.android.camera.R;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiEmoticonInfo;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.baseview.BaseNoScrollGridLayoutManager;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.log.Log;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.storage.Storage;
import com.arcsoft.avatar2.emoticon.EmoInfo;
import io.reactivex.Completable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miui.app.AlertDialog;
import miui.app.ProgressDialog;

public class FragmentMimojiEmoticon extends BaseFragment implements MimojiModeProtocol.MimojiEditor2.MimojiEmoticon, View.OnClickListener, ModeProtocol.HandleBackTrace {
    private static final int FRAGMENT_INFO = 65522;
    public static final String TAG = "FragmentMimojiEmoticon";
    private static final int THUMBNAIL_TOTAL_COUNT = 6;
    private AlertDialog mAlertDialog;
    private LinearLayout mBottomActionLinearLayout;
    private RecyclerView mEmoticonRecyclerView;
    private boolean mIsBackToPreview;
    private boolean mIsNeedShare;
    private boolean mIsRTL;
    private MimojiEmoticonAdapter mMimojiEmoticonAdapter;
    private ProgressDialog mProgressDialog;
    private TextView mSaveEmoticonBtn;
    private ImageView mSelectBtn;
    private ArrayList<EmoInfo> mSelectedEmoInfoList = new ArrayList<>();
    private ArrayList<Uri> mShareEmoInfoList = new ArrayList<>();
    private TextView mShareEmoticonBtn;
    private Paint mThumbnailPaint;

    private boolean checkInitThumbnaiFinish() {
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        return mimojiEmoticonAdapter != null && mimojiEmoticonAdapter.getItemCount() == 6;
    }

    private void deleteEmoticonCache() {
        FileUtils.deleteFile(MimojiHelper2.EMOTICON_MP4_CACHE_DIR);
        FileUtils.deleteFile(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
        FileUtils.deleteFile(MimojiHelper2.EMOTICON_JPEG_CACHE_DIR);
    }

    private void dissmissDialog() {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mProgressDialog = null;
        }
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAlertDialog = null;
        }
    }

    private void getEmoticonThumbnail() {
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        if (mimojiEditor2 != null) {
            mimojiEditor2.createEmoticonThumbnail();
        } else {
            Log.e(TAG, "mimoji void initEmoticon[] null");
        }
    }

    private void saveEmoticonGif(boolean z) {
        this.mSelectedEmoInfoList.clear();
        this.mIsNeedShare = false;
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        if (mimojiEmoticonAdapter == null || mimojiEmoticonAdapter.getItemCount() != 0) {
            for (MimojiEmoticonInfo mimojiEmoticonInfo : this.mMimojiEmoticonAdapter.getDataList()) {
                if (mimojiEmoticonInfo.isSelected()) {
                    this.mSelectedEmoInfoList.add(mimojiEmoticonInfo.getEmoInfo());
                }
            }
            if (this.mSelectedEmoInfoList.size() == 0) {
                ToastUtils.showToast(getContext(), (int) R.string.mimoji_select_number_error, 80);
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mSelectedEmoInfoList);
            File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
            if (file.exists() && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (((EmoInfo) it.next()).getEmoName().equals(fileName)) {
                            it.remove();
                        }
                    }
                }
            }
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 == null) {
                Log.e(TAG, "mimoji void saveEmoticonGif[] null");
            } else if (arrayList.size() != 0) {
                showProgressDialog(getString(R.string.mimoji_create_emoticon_progress), 0);
                if (!z) {
                    mimojiEditor2.createEmoticonVideo(arrayList);
                } else {
                    mimojiEditor2.createEmoticonPicture(arrayList);
                }
            } else if (z) {
                mimojiEditor2.createEmoticonPicture(arrayList);
            } else {
                coverEmoticonSuccess();
            }
        } else {
            backToPreview(false);
            ToastUtils.showToast(getContext(), (int) R.string.unknow_error, 80);
        }
    }

    @SuppressLint({"StringFormatInvalid"})
    private void shareEmoticonGif() {
        this.mSelectedEmoInfoList.clear();
        this.mShareEmoInfoList.clear();
        this.mIsNeedShare = true;
        this.mIsBackToPreview = true;
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        if (mimojiEmoticonAdapter == null || mimojiEmoticonAdapter.getItemCount() != 0) {
            for (MimojiEmoticonInfo mimojiEmoticonInfo : this.mMimojiEmoticonAdapter.getDataList()) {
                if (mimojiEmoticonInfo.isSelected()) {
                    this.mSelectedEmoInfoList.add(mimojiEmoticonInfo.getEmoInfo());
                }
            }
            if (this.mSelectedEmoInfoList.size() == 0) {
                ToastUtils.showToast(getContext(), String.format(getResources().getString(R.string.mimoji_select_number_error), 1), 80);
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mSelectedEmoInfoList);
            File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
            if (file.exists() && file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (((EmoInfo) it.next()).getEmoName().equals(fileName)) {
                            it.remove();
                        }
                    }
                }
            }
            if (arrayList.size() == 0) {
                coverEmoticonSuccess();
                return;
            }
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                showProgressDialog(getString(R.string.mimoji_create_emoticon_progress), 0);
                mimojiEditor2.createEmoticonVideo(arrayList);
                return;
            }
            return;
        }
        backToPreview(false);
        ToastUtils.showToast(getContext(), (int) R.string.unknow_error, 80);
    }

    private void showBackDialog() {
        if (getActivity() != null) {
            dissmissDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.mimoji_edit_abandon_alert);
            builder.setCancelable(true);
            builder.setPositiveButton(R.string.mimoji_confirm, new f(this));
            builder.setNegativeButton(R.string.mimoji_cancle, new l(this));
            this.mAlertDialog = builder.show();
        }
    }

    private void showProgressDialog(String str, int i) {
        if (getActivity() != null) {
            if (TextUtils.isEmpty(str) || i < 0) {
                dissmissDialog();
                return;
            }
            if (this.mProgressDialog == null) {
                this.mProgressDialog = new ProgressDialog(getActivity());
                this.mProgressDialog.setProgressStyle(1);
                this.mProgressDialog.setCancelable(false);
                this.mProgressDialog.setMax(100);
                this.mProgressDialog.setOnKeyListener(new g(this));
            }
            this.mProgressDialog.setMessage(str);
            this.mProgressDialog.setProgress(i);
            ProgressDialog progressDialog = this.mProgressDialog;
            if (progressDialog != null && !progressDialog.isShowing()) {
                this.mProgressDialog.show();
            }
        }
    }

    private void showSaveDialog() {
        if (getActivity() != null) {
            dissmissDialog();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.mimoji_emoticon_save);
            builder.setCancelable(true);
            builder.setMessage(R.string.mimoji_save_gif_emoticon);
            builder.setCheckBox(true, getString(R.string.mimoji_emoticon_with_jpeg_save));
            builder.setPositiveButton(R.string.mimoji_save, new p(this));
            builder.setNegativeButton(R.string.mimoji_cancle, new n(this));
            this.mAlertDialog = builder.show();
        }
    }

    public /* synthetic */ void a(MimojiEmoticonInfo mimojiEmoticonInfo, int i, View view) {
        mimojiEmoticonInfo.setSelected(!mimojiEmoticonInfo.isSelected());
        this.mMimojiEmoticonAdapter.setSelectState(mimojiEmoticonInfo, i);
    }

    public /* synthetic */ void a(EmoInfo emoInfo, int i) {
        if (this.mThumbnailPaint == null) {
            this.mThumbnailPaint = new Paint();
            this.mThumbnailPaint.setAntiAlias(true);
        }
        Log.d(TAG, "mimoji void updateEmoticonThumbnailProgress[num, emoInfo]");
        Bitmap bitmap = null;
        if (emoInfo.getThumbnailData() != null) {
            Bitmap createBitmap = Bitmap.createBitmap(AvatarEngineManager2.CONFIG_EMO_THUM_SIZE.getWidth(), AvatarEngineManager2.CONFIG_EMO_THUM_SIZE.getHeight(), Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(emoInfo.getThumbnailData()));
            if (createBitmap != null) {
                bitmap = Util.getRoundedCornerBitmap(createBitmap, 20.0f);
            } else {
                String str = TAG;
                Log.e(str, "mimoji void updateEmoticonThumbnailProgress[num, emoInfo]" + i + " , " + emoInfo.getEmoName());
            }
        }
        if (bitmap == null || bitmap.isRecycled()) {
            Log.e(TAG, "mimoji thumbnail null");
        } else if (this.mMimojiEmoticonAdapter != null) {
            MimojiEmoticonInfo mimojiEmoticonInfo = new MimojiEmoticonInfo(emoInfo, bitmap);
            mimojiEmoticonInfo.setSelected(true);
            this.mMimojiEmoticonAdapter.addData(mimojiEmoticonInfo);
        }
    }

    public /* synthetic */ boolean b(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 4) {
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                mimojiEditor2.quitCoverEmoticon();
                dissmissDialog();
            } else {
                Log.e(TAG, "mimoji void saveEmoticonGif[] mimojiEditor2 null");
            }
            MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
            if (mimojiVideoEditor != null) {
                mimojiVideoEditor.cancelVideo2gif();
            } else {
                Log.e(TAG, "mimoji void saveEmoticonGif[] mimojiVideoEditor null");
            }
        }
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void backToPreview(boolean z) {
        deleteEmoticonCache();
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            mimojiVideoEditor.cancelVideo2gif();
        }
        getActivity().runOnUiThread(new o(this, z));
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void coverEmoticonError() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new j(this));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void coverEmoticonSuccess() {
        IOException e2;
        Uri uri;
        Throwable th;
        StringBuilder sb;
        String str;
        Exception e3;
        File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
        if (!file.exists() || !file.isDirectory()) {
            ToastUtils.showToast(getContext(), (int) R.string.unknow_error, 80);
            backToPreview(false);
            return;
        }
        int i = 300;
        if (this.mIsNeedShare) {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            int i2 = 0;
            while (i2 < length) {
                File file2 = listFiles[i2];
                String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                Iterator<EmoInfo> it = this.mSelectedEmoInfoList.iterator();
                while (it.hasNext()) {
                    if (it.next().getEmoName().equals(fileName)) {
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(Storage.DIRECTORY);
                            sb2.append(File.separator);
                            sb2.append(FileUtils.createtFileName("MIMOJI_GIF_" + fileName + "_", ".gif"));
                            String sb3 = sb2.toString();
                            FileUtils.copyFile(file2, new File(sb3));
                            uri = ((ActivityBase) getActivity()).getImageSaver().addGifSync(sb3, i, i);
                            try {
                                Log.d(TAG, "mimoji void shareEmoticonGif[] f.getAbsolutePath() : " + file2.getAbsolutePath() + " \n  " + file2.getPath() + "   " + file2.getCanonicalPath());
                                if (uri != null) {
                                    this.mShareEmoInfoList.add(uri);
                                }
                                str = TAG;
                                sb = new StringBuilder();
                            } catch (Exception e4) {
                                e3 = e4;
                                try {
                                    Log.e(TAG, "failed to add video to media store", e3);
                                    str = TAG;
                                    sb = new StringBuilder();
                                    sb.append("Current video URI: ");
                                    sb.append(uri);
                                    Log.d(str, sb.toString());
                                    i = 300;
                                } catch (Throwable th2) {
                                    th = th2;
                                    Log.d(TAG, "Current video URI: " + uri);
                                    throw th;
                                }
                            }
                        } catch (Exception e5) {
                            e3 = e5;
                            uri = null;
                            Log.e(TAG, "failed to add video to media store", e3);
                            str = TAG;
                            sb = new StringBuilder();
                            sb.append("Current video URI: ");
                            sb.append(uri);
                            Log.d(str, sb.toString());
                            i = 300;
                        } catch (Throwable th3) {
                            th = th3;
                            uri = null;
                            Log.d(TAG, "Current video URI: " + uri);
                            throw th;
                        }
                        sb.append("Current video URI: ");
                        sb.append(uri);
                        Log.d(str, sb.toString());
                    }
                    i = 300;
                }
                i2++;
                i = 300;
            }
            if (this.mShareEmoInfoList.size() == 0) {
                ToastUtils.showToast(getContext(), (int) R.string.unknow_error, 80);
                return;
            }
            if (this.mShareEmoInfoList.size() == 1) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.STREAM", this.mShareEmoInfoList.get(0));
                intent.setType(Storage.MIME_GIF);
                startActivity(Intent.createChooser(intent, getString(R.string.share)));
            } else {
                Intent intent2 = new Intent("android.intent.action.SEND_MULTIPLE");
                intent2.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mShareEmoInfoList);
                intent2.setType(Storage.MIME_GIF);
                startActivity(Intent.createChooser(intent2, getString(R.string.share)));
            }
            deleteEmoticonCache();
            dissmissDialog();
            return;
        }
        File[] listFiles2 = file.listFiles();
        for (File file3 : listFiles2) {
            String fileName2 = FileUtils.getFileName(file3.getAbsolutePath());
            Iterator<EmoInfo> it2 = this.mSelectedEmoInfoList.iterator();
            while (it2.hasNext()) {
                if (it2.next().getEmoName().equals(fileName2)) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(Storage.DIRECTORY);
                    sb4.append(File.separator);
                    sb4.append(FileUtils.createtFileName("MIMOJI_GIF_" + fileName2 + "_", ".gif"));
                    String sb5 = sb4.toString();
                    try {
                        FileUtils.copyFile(file3, new File(sb5));
                        try {
                            ((ActivityBase) getActivity()).getImageSaver().addGif(sb5, 300, 300);
                        } catch (IOException e6) {
                            e2 = e6;
                        }
                    } catch (IOException e7) {
                        e2 = e7;
                        e2.printStackTrace();
                    }
                }
            }
        }
        ToastUtils.showToast(getActivity(), (int) R.string.mimoji_save_success, 80);
        this.mIsBackToPreview = true;
        backToPreview(true);
    }

    public /* synthetic */ void e(DialogInterface dialogInterface, int i) {
        backToPreview(false);
    }

    public /* synthetic */ void f(DialogInterface dialogInterface, int i) {
        dissmissDialog();
    }

    public /* synthetic */ void g(DialogInterface dialogInterface, int i) {
        AlertDialog alertDialog = this.mAlertDialog;
        saveEmoticonGif(alertDialog == null ? false : alertDialog.isChecked());
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65522;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_mimoji_emoticon;
    }

    public /* synthetic */ void h(DialogInterface dialogInterface, int i) {
        dissmissDialog();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mSaveEmoticonBtn = (TextView) view.findViewById(R.id.btn_save_emoticon);
        this.mSaveEmoticonBtn.setOnClickListener(this);
        this.mShareEmoticonBtn = (TextView) view.findViewById(R.id.btn_share_emoticon);
        this.mShareEmoticonBtn.setOnClickListener(this);
        view.findViewById(R.id.btn_back).setOnClickListener(this);
        FolmeUtils.touchButtonTint(R.color.mimoji_btn_pressed_bg, this.mSaveEmoticonBtn, this.mShareEmoticonBtn);
        this.mBottomActionLinearLayout = (LinearLayout) view.findViewById(R.id.ll_bottom_action);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mBottomActionLinearLayout.getLayoutParams();
        layoutParams.bottomMargin = Util.sNavigationBarHeight + getResources().getDimensionPixelOffset(R.dimen.mimoji_emoticon_margin_bottom_action);
        this.mBottomActionLinearLayout.setLayoutParams(layoutParams);
        this.mSelectBtn = (ImageView) view.findViewById(R.id.btn_select_all);
        this.mSelectBtn.setOnClickListener(this);
        this.mEmoticonRecyclerView = (RecyclerView) view.findViewById(R.id.rv_emoticon);
        this.mIsRTL = Util.isLayoutRTL(getContext());
        deleteEmoticonCache();
        if (this.mMimojiEmoticonAdapter == null) {
            this.mMimojiEmoticonAdapter = new MimojiEmoticonAdapter(null);
            this.mEmoticonRecyclerView.setLayoutManager(new BaseNoScrollGridLayoutManager(getContext(), 2));
            this.mEmoticonRecyclerView.getItemAnimator().setChangeDuration(0);
            this.mEmoticonRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                /* class com.android.camera.features.mimoji2.fragment.edit.FragmentMimojiEmoticon.AnonymousClass1 */
                final int margin = FragmentMimojiEmoticon.this.getResources().getDimensionPixelSize(R.dimen.mimoji_emoticon_offset);

                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                    int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                    if (FragmentMimojiEmoticon.this.mIsRTL) {
                        if (childAdapterPosition % 2 == 0) {
                            rect.set(this.margin, 0, 0, 0);
                        }
                    } else if (childAdapterPosition % 2 == 0) {
                        rect.set(0, 0, this.margin, 0);
                    }
                }
            });
            this.mEmoticonRecyclerView.setAdapter(this.mMimojiEmoticonAdapter);
        }
        this.mMimojiEmoticonAdapter.setOnRecyclerItemClickListener(new k(this));
        this.mMimojiEmoticonAdapter.setOnAllSelectStateChangeListener(new i(this));
        getEmoticonThumbnail();
    }

    public /* synthetic */ void m(int i) {
        if (i != 0) {
            showProgressDialog(getString(R.string.mimoji_create_emoticon_progress), ((6 - i) * 100) / 6);
            return;
        }
        MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
        if (mimojiVideoEditor != null) {
            mimojiVideoEditor.video2gif(this.mSelectedEmoInfoList);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i != 1) {
            return false;
        }
        showBackDialog();
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                backToPreview(false);
                return;
            case R.id.btn_confirm:
            case R.id.btn_mimoji_change_timbre:
            default:
                return;
            case R.id.btn_save_emoticon:
                if (checkInitThumbnaiFinish()) {
                    showSaveDialog();
                    return;
                }
                return;
            case R.id.btn_select_all:
                if (checkInitThumbnaiFinish()) {
                    if (this.mMimojiEmoticonAdapter.getIsAllSelected()) {
                        this.mMimojiEmoticonAdapter.clearState();
                        return;
                    } else {
                        this.mMimojiEmoticonAdapter.selectAll();
                        return;
                    }
                } else {
                    return;
                }
            case R.id.btn_share_emoticon:
                if (checkInitThumbnaiFinish()) {
                    shareEmoticonGif();
                    return;
                }
                return;
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        String str = TAG;
        Log.d(str, "provideAnimateElement, animateInElements" + list + "resetType = " + i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(250, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void release() {
        MimojiEmoticonAdapter mimojiEmoticonAdapter = this.mMimojiEmoticonAdapter;
        if (mimojiEmoticonAdapter != null && mimojiEmoticonAdapter.getItemCount() > 0) {
            Iterator it = ((ArrayList) this.mMimojiEmoticonAdapter.getDataList()).iterator();
            while (it.hasNext()) {
                MimojiEmoticonInfo mimojiEmoticonInfo = (MimojiEmoticonInfo) it.next();
                if (mimojiEmoticonInfo.getBitmap() != null && !mimojiEmoticonInfo.getBitmap().isRecycled()) {
                    mimojiEmoticonInfo.getBitmap().recycle();
                }
                mimojiEmoticonInfo.setBitmap(null);
            }
        }
        dissmissDialog();
        this.mMimojiEmoticonAdapter = null;
        AvatarEngineManager2.getInstance().setEmoManager(null);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void setIsBackToPreview(boolean z) {
        this.mIsBackToPreview = z;
    }

    public /* synthetic */ void u(boolean z) {
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(19);
        }
        if (this.mIsBackToPreview) {
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
            if (mimojiEditor2 != null) {
                mimojiEditor2.quitAndSaveEdit(false);
                return;
            }
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null) {
                mimojiAvatarEngine2.backToPreview(z, false);
                return;
            }
            return;
        }
        DataRepository.dataItemLive().getMimojiStatusManager2().setMode(6);
        MimojiModeProtocol.MimojiEditor2 mimojiEditor22 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        if (mimojiEditor22 != null) {
            mimojiEditor22.reInitMimojiEdit();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(250, this);
        release();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void updateEmoticonGifProgress(int i) {
        if (getContext() != null) {
            ((Activity) getContext()).runOnUiThread(new h(this, i));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void updateEmoticonPictureProgress(String str, EmoInfo emoInfo, boolean z) {
        if (getActivity() != null) {
            Log.d(TAG, "mimoji void updateEmoticonPictureProgress[path, emoInfo, isFinal]: " + str);
            if (z) {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.mSelectedEmoInfoList);
                File file = new File(MimojiHelper2.EMOTICON_GIF_CACHE_DIR);
                if (file.exists() && file.isDirectory()) {
                    for (File file2 : file.listFiles()) {
                        String fileName = FileUtils.getFileName(file2.getAbsolutePath());
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((EmoInfo) it.next()).getEmoName().equals(fileName)) {
                                it.remove();
                            }
                        }
                    }
                }
                MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
                if (mimojiEditor2 != null) {
                    mimojiEditor2.createEmoticonVideo(arrayList);
                } else {
                    Log.e(TAG, "mimoji void saveEmoticonGif[] null");
                }
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiEditor2.MimojiEmoticon
    public void updateEmoticonThumbnailProgress(int i, EmoInfo emoInfo) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new m(this, emoInfo, i));
        }
    }

    public /* synthetic */ void v(boolean z) {
        this.mSelectBtn.setImageResource(z ? R.drawable.bg_mimoji_btn_emoticon_all_selected : R.drawable.bg_mimoji_btn_emoticon_all_unselected);
    }

    public /* synthetic */ void za() {
        dissmissDialog();
        ToastUtils.showToast(getActivity(), (int) R.string.mimoji_create_emoticon_error, 80);
    }
}
