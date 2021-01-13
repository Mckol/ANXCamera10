package com.android.gallery3d.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ResourceTexture extends UploadedTexture {
    protected final Context mContext;
    protected final int mResId;

    public ResourceTexture(Context context, int i) {
        Utils.checkNotNull(context);
        this.mContext = context;
        this.mResId = i;
        setOpaque(false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.UploadedTexture
    public void onFreeBitmap(Bitmap bitmap) {
        if (!BasicTexture.inFinalizer()) {
            bitmap.recycle();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.UploadedTexture
    public Bitmap onGetBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(this.mContext.getResources(), this.mResId, options);
    }
}
