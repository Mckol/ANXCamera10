package com.android.camera.aiwatermark.data;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.log.Log;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class WatermarkItem {
    private static final String TAG = "WatermarkItem";
    private int[] mCaptureCoordinate;
    private int[] mCoordinate;
    private int mCountry;
    private boolean mHasMove;
    private String mKey;
    private Rect mLimitArea;
    private int mLocation;
    private int mResId;
    private int mResRvItem;
    private String mText;
    private Bitmap mTimeBitmap;
    private String[] mTimeWatermark;
    private int mType;

    public WatermarkItem() {
        this.mKey = "";
        this.mType = 1;
        this.mText = null;
        this.mResId = -1;
        this.mLocation = 1;
        this.mCountry = -1;
        this.mResRvItem = -1;
        this.mCoordinate = new int[4];
        this.mCaptureCoordinate = new int[4];
        this.mHasMove = false;
        this.mTimeBitmap = null;
        this.mTimeWatermark = null;
    }

    public WatermarkItem(String str, int i, int i2, int i3) {
        this(str, i, null, i2, i3);
    }

    public WatermarkItem(String str, int i, String str2, int i2, int i3) {
        this.mKey = "";
        this.mType = 1;
        this.mText = null;
        this.mResId = -1;
        this.mLocation = 1;
        this.mCountry = -1;
        this.mResRvItem = -1;
        this.mCoordinate = new int[4];
        this.mCaptureCoordinate = new int[4];
        this.mHasMove = false;
        this.mTimeBitmap = null;
        this.mTimeWatermark = null;
        this.mKey = str;
        this.mType = i;
        this.mText = str2;
        this.mResId = i2;
        this.mLocation = i3;
    }

    public WatermarkItem(String str, int i, String str2, int i2, int i3, int i4) {
        this(str, i, str2, i2, i3);
        this.mCountry = i4;
    }

    public WatermarkItem(String str, int i, String str2, int i2, int i3, int i4, int i5) {
        this(str, i, str2, i2, i3, i4);
        this.mResRvItem = i5;
    }

    public int[] getCaptureCoordinate() {
        return this.mCaptureCoordinate;
    }

    public int[] getCoordinate() {
        return this.mCoordinate;
    }

    public int getCountry() {
        return this.mCountry;
    }

    public String getKey() {
        return this.mKey;
    }

    public Rect getLimitArea() {
        return this.mLimitArea;
    }

    public int getLocation() {
        return this.mLocation;
    }

    public int getResId() {
        return this.mResId;
    }

    public int getResRvItem() {
        return this.mResRvItem;
    }

    public String getText() {
        return this.mText;
    }

    public Bitmap getTimeBitmap() {
        return this.mTimeBitmap;
    }

    public String[] getTimeWatermark() {
        if (this.mTimeWatermark == null) {
            updateTimeWatermark();
        }
        return this.mTimeWatermark;
    }

    public int getTimeWatermarkType() {
        if (TextUtils.equals("time", this.mKey)) {
            return 1;
        }
        if (TextUtils.equals(WatermarkConstant.EXTEND_TIME_1, this.mKey)) {
            return 2;
        }
        return TextUtils.equals(WatermarkConstant.EXTEND_TIME_2, this.mKey) ? 3 : 0;
    }

    public int getType() {
        return this.mType;
    }

    public boolean hasMove() {
        return this.mHasMove;
    }

    public boolean isTimeWatermark() {
        return TextUtils.equals("time", this.mKey) || TextUtils.equals(WatermarkConstant.EXTEND_TIME_1, this.mKey) || TextUtils.equals(WatermarkConstant.EXTEND_TIME_2, this.mKey);
    }

    public void setCaptureCoordinate(int[] iArr) {
        this.mCaptureCoordinate = iArr;
    }

    public void setCountry(int i) {
        this.mCountry = i;
    }

    public void setHasMove(boolean z) {
        this.mHasMove = z;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public void setLimitArea(Rect rect) {
        this.mLimitArea = rect;
    }

    public void setLocation(int i) {
        this.mLocation = i;
    }

    public void setResId(int i) {
        this.mResId = i;
    }

    public void setResRvItem(int i) {
        this.mResRvItem = i;
    }

    public void setText(String str) {
        this.mText = str;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public String toString() {
        return "WatermarkItem: key is " + getKey() + "; type is " + getType() + "; text is " + getText() + "; res id is " + getResId() + "; location is " + getLocation() + "; country is " + getCountry();
    }

    public void updateCoordinate(int[] iArr) {
        this.mCoordinate = iArr;
    }

    public void updateTimeBitmap(View view) {
        view.clearFocus();
        view.setPressed(false);
        boolean willNotCacheDrawing = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);
        int drawingCacheBackgroundColor = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(0);
        if (drawingCacheBackgroundColor != 0) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) {
            Log.w(TAG, "[updateTimeBitmap] cacheBitmap is null");
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawingCache);
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCacheDrawing);
        view.setDrawingCacheBackgroundColor(drawingCacheBackgroundColor);
        this.mTimeBitmap = createBitmap;
    }

    public String[] updateTimeWatermark() {
        int timeWatermarkType = getTimeWatermarkType();
        if (timeWatermarkType == 2) {
            this.mTimeWatermark = new SimpleDateFormat("HH:mm-MMM dd'th'.yyyy", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())).split("-");
        } else if (timeWatermarkType != 3) {
            this.mTimeWatermark = new SimpleDateFormat("MMM-dd-hh:mm a", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())).split("-");
        } else {
            this.mTimeWatermark = new SimpleDateFormat("dd-MMM", Locale.ENGLISH).format(Long.valueOf(System.currentTimeMillis())).split("-");
        }
        return this.mTimeWatermark;
    }
}
