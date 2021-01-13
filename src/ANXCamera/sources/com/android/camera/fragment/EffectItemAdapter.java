package com.android.camera.fragment;

import android.content.Context;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.ActivityBase;
import com.android.camera.CameraScreenNail;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.ui.GLTextureView;
import com.android.camera.ui.ScrollTextview;
import com.android.gallery3d.ui.GLCanvasImpl;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import miui.view.animation.CubicEaseOutInterpolator;

public class EffectItemAdapter extends RecyclerView.Adapter {
    protected ComponentData mComponentData;
    protected final Context mContext;
    private int mDegree;
    private int mDisplayRotation;
    private IEffectItemListener mEffectItemListener;
    protected LayoutInflater mLayoutInflater;
    private View.OnClickListener mOnClickListener;
    private boolean mSupportRealtimeEffect;

    /* access modifiers changed from: package-private */
    public static abstract class EffectItemHolder extends RecyclerView.ViewHolder {
        protected int mEffectIndex;
        protected ImageView mSelectedIndicator;
        protected ScrollTextview mTextView;

        public EffectItemHolder(View view) {
            super(view);
            this.mTextView = (ScrollTextview) view.findViewById(R.id.effect_item_text);
        }

        public void bindEffectIndex(int i, ComponentDataItem componentDataItem) {
            this.mEffectIndex = getRenderId(i, componentDataItem);
            int i2 = componentDataItem.mDisplayNameRes;
            if (i2 > 0) {
                this.mTextView.setText(i2);
            } else {
                this.mTextView.setText("");
            }
        }

        /* access modifiers changed from: protected */
        public int getRenderId(int i, ComponentDataItem componentDataItem) {
            return Integer.parseInt(componentDataItem.mValue);
        }

        public void requestRender() {
        }
    }

    public static class EffectItemPadding extends RecyclerView.ItemDecoration {
        protected boolean mIsRTL = false;
        protected int padding;

        public EffectItemPadding(Context context) {
            this.padding = context.getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
            this.mIsRTL = Util.isLayoutRTL(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childPosition = recyclerView.getChildPosition(view);
            if (this.mIsRTL) {
                int i = this.padding;
                rect.set(i, 0, childPosition == 0 ? i : 0, 0);
                return;
            }
            rect.set(childPosition == 0 ? this.padding : 0, 0, this.padding, 0);
        }
    }

    /* access modifiers changed from: private */
    public class EffectRealtimeItemHolder extends EffectItemHolder {
        private EffectRealtimeRender effectRenderer;
        private GLTextureView mTextureView;

        public EffectRealtimeItemHolder(View view) {
            super(view);
            this.mTextureView = (GLTextureView) view.findViewById(R.id.effect_item_realtime);
            this.mSelectedIndicator = (ImageView) view.findViewById(R.id.effect_item_selected_indicator);
            this.effectRenderer = new EffectRealtimeRender(EffectItemAdapter.this.mContext);
            this.mTextureView.setEGLContextClientVersion(2);
            this.mTextureView.setEGLShareContextGetter(new d(this));
            this.mTextureView.setRenderer(this.effectRenderer);
            this.mTextureView.setRenderMode(0);
        }

        public /* synthetic */ EGLContext ab() {
            return ((ActivityBase) EffectItemAdapter.this.mContext).getGLView().getEGLContext();
        }

        @Override // com.android.camera.fragment.EffectItemAdapter.EffectItemHolder
        public void bindEffectIndex(int i, ComponentDataItem componentDataItem) {
            super.bindEffectIndex(i, componentDataItem);
            this.effectRenderer.bindEffectIndex(i, componentDataItem);
            if (EffectItemAdapter.this.mEffectItemListener == null || EffectItemAdapter.this.mEffectItemListener.getCurrentIndex() != i) {
                this.itemView.setActivated(false);
                if (EffectItemAdapter.this.mEffectItemListener == null || !EffectItemAdapter.this.mEffectItemListener.isAnimation() || EffectItemAdapter.this.mEffectItemListener == null || EffectItemAdapter.this.mEffectItemListener.getLastIndex() != i) {
                    this.mSelectedIndicator.setVisibility(8);
                    this.mSelectedIndicator.setAlpha(0.0f);
                    return;
                }
                ViewCompat.setAlpha(this.mSelectedIndicator, 1.0f);
                ViewCompat.animate(this.mSelectedIndicator).setDuration(500).alpha(0.0f).setInterpolator(new CubicEaseOutInterpolator()).setListener(new ViewPropertyAnimatorListener() {
                    /* class com.android.camera.fragment.EffectItemAdapter.EffectRealtimeItemHolder.AnonymousClass3 */

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationCancel(View view) {
                    }

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        EffectRealtimeItemHolder.this.mSelectedIndicator.setVisibility(8);
                    }

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationStart(View view) {
                    }
                }).start();
                return;
            }
            this.itemView.setActivated(true);
            if (Util.isAccessible() || Util.isSetContentDesc()) {
                View view = this.itemView;
                view.setContentDescription(EffectItemAdapter.this.mContext.getString(componentDataItem.mDisplayNameRes) + EffectItemAdapter.this.mContext.getString(R.string.accessibility_selected));
                this.itemView.postDelayed(new Runnable() {
                    /* class com.android.camera.fragment.EffectItemAdapter.EffectRealtimeItemHolder.AnonymousClass1 */

                    public void run() {
                        if (EffectItemAdapter.this.mEffectItemListener != null && EffectItemAdapter.this.mEffectItemListener.isAdded()) {
                            EffectRealtimeItemHolder.this.itemView.sendAccessibilityEvent(128);
                        }
                    }
                }, 100);
            }
            if (EffectItemAdapter.this.mEffectItemListener == null || !EffectItemAdapter.this.mEffectItemListener.isAnimation()) {
                this.mSelectedIndicator.setVisibility(0);
                this.mSelectedIndicator.setAlpha(1.0f);
                return;
            }
            ViewCompat.setAlpha(this.mSelectedIndicator, 0.0f);
            ViewCompat.animate(this.mSelectedIndicator).setDuration(500).alpha(1.0f).setInterpolator(new CubicEaseOutInterpolator()).setListener(new ViewPropertyAnimatorListener() {
                /* class com.android.camera.fragment.EffectItemAdapter.EffectRealtimeItemHolder.AnonymousClass2 */

                @Override // androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationCancel(View view) {
                }

                @Override // androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                }

                @Override // androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                    EffectRealtimeItemHolder.this.mSelectedIndicator.setVisibility(0);
                }
            }).start();
        }

        @Override // com.android.camera.fragment.EffectItemAdapter.EffectItemHolder
        public void requestRender() {
            this.mTextureView.requestRender();
        }
    }

    protected class EffectRealtimeRender implements GLSurfaceView.Renderer {
        private ComponentDataItem filterData;
        private int height;
        private Context mContext;
        private DrawExtTexAttribute mExtTexture = new DrawExtTexAttribute(true);
        float[] mTransform = new float[16];
        private int viewPosition;
        private int width;

        public EffectRealtimeRender(Context context) {
            this.mContext = context;
        }

        public void bindEffectIndex(int i, ComponentDataItem componentDataItem) {
            this.viewPosition = i;
            this.filterData = componentDataItem;
        }

        public void onDrawFrame(GL10 gl10) {
            CameraScreenNail cameraScreenNail = ((ActivityBase) this.mContext).getCameraScreenNail();
            GLCanvasImpl gLCanvas = ((ActivityBase) this.mContext).getGLView().getGLCanvas();
            if (cameraScreenNail != null && gLCanvas != null && cameraScreenNail.getSurfaceTexture() != null) {
                synchronized (gLCanvas) {
                    gLCanvas.clearBuffer();
                    int width2 = gLCanvas.getWidth();
                    int height2 = gLCanvas.getHeight();
                    gLCanvas.getState().pushState();
                    if (EffectItemAdapter.this.mEffectItemListener != null) {
                        gLCanvas.setSize(EffectItemAdapter.this.mEffectItemListener.getHolderWidth(), EffectItemAdapter.this.mEffectItemListener.getHolderHeight());
                    }
                    cameraScreenNail.getSurfaceTexture().getTransformMatrix(this.mTransform);
                    EffectController instance = EffectController.getInstance();
                    int effectForPreview = EffectController.getInstance().getEffectForPreview(false);
                    instance.notifyRealtimePreviewFilterChanged(Integer.parseInt(this.filterData.mValue));
                    if (EffectItemAdapter.this.mEffectItemListener != null) {
                        gLCanvas.draw(this.mExtTexture.init(cameraScreenNail.getExtTexture(), this.mTransform, EffectItemAdapter.this.mEffectItemListener.getTextureOffsetX(), EffectItemAdapter.this.mEffectItemListener.getTextureOffsetY(), EffectItemAdapter.this.mEffectItemListener.getTextureWidth(), EffectItemAdapter.this.mEffectItemListener.getTextureHeight()));
                    }
                    instance.notifyRealtimePreviewFilterChanged(effectForPreview);
                    gLCanvas.setSize(width2, height2);
                    gLCanvas.getState().popState();
                    gLCanvas.recycledResources();
                }
            }
        }

        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        }
    }

    public class EffectStillItemHolder extends EffectItemHolder {
        private ImageView mImageView;

        public EffectStillItemHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view.findViewById(R.id.effect_item_image);
            this.mSelectedIndicator = (ImageView) view.findViewById(R.id.effect_item_selected_indicator);
        }

        @Override // com.android.camera.fragment.EffectItemAdapter.EffectItemHolder
        public void bindEffectIndex(int i, ComponentDataItem componentDataItem) {
            super.bindEffectIndex(i, componentDataItem);
            this.mImageView.setImageResource(componentDataItem.mIconRes);
            if (EffectItemAdapter.this.mEffectItemListener == null || EffectItemAdapter.this.mEffectItemListener.getCurrentIndex() != i) {
                this.itemView.setActivated(false);
                if (EffectItemAdapter.this.mEffectItemListener == null || !EffectItemAdapter.this.mEffectItemListener.isAnimation() || EffectItemAdapter.this.mEffectItemListener == null || EffectItemAdapter.this.mEffectItemListener.getLastIndex() != i) {
                    this.mSelectedIndicator.setVisibility(8);
                    this.mSelectedIndicator.setAlpha(0.0f);
                    return;
                }
                ViewCompat.setAlpha(this.mSelectedIndicator, 1.0f);
                ViewCompat.animate(this.mSelectedIndicator).setDuration(500).alpha(0.0f).setInterpolator(new CubicEaseOutInterpolator()).setListener(new ViewPropertyAnimatorListener() {
                    /* class com.android.camera.fragment.EffectItemAdapter.EffectStillItemHolder.AnonymousClass3 */

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationCancel(View view) {
                    }

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        EffectStillItemHolder.this.mSelectedIndicator.setVisibility(8);
                    }

                    @Override // androidx.core.view.ViewPropertyAnimatorListener
                    public void onAnimationStart(View view) {
                    }
                }).start();
                return;
            }
            this.itemView.setActivated(true);
            if (Util.isAccessible() || Util.isSetContentDesc()) {
                int i2 = R.string.lighting_pattern_null;
                int i3 = componentDataItem.mDisplayNameRes;
                if (i3 > 0) {
                    i2 = i3;
                }
                View view = this.itemView;
                view.setContentDescription(EffectItemAdapter.this.mContext.getString(i2) + EffectItemAdapter.this.mContext.getString(R.string.accessibility_selected));
                this.itemView.postDelayed(new Runnable() {
                    /* class com.android.camera.fragment.EffectItemAdapter.EffectStillItemHolder.AnonymousClass1 */

                    public void run() {
                        if (EffectItemAdapter.this.mEffectItemListener != null && EffectItemAdapter.this.mEffectItemListener.isAdded()) {
                            EffectStillItemHolder.this.itemView.sendAccessibilityEvent(128);
                        }
                    }
                }, 100);
            }
            if (EffectItemAdapter.this.mEffectItemListener == null || !EffectItemAdapter.this.mEffectItemListener.isAnimation()) {
                this.mSelectedIndicator.setVisibility(0);
                this.mSelectedIndicator.setAlpha(1.0f);
                return;
            }
            ViewCompat.setAlpha(this.mSelectedIndicator, 0.0f);
            ViewCompat.animate(this.mSelectedIndicator).setDuration(500).alpha(1.0f).setInterpolator(new CubicEaseOutInterpolator()).setListener(new ViewPropertyAnimatorListener() {
                /* class com.android.camera.fragment.EffectItemAdapter.EffectStillItemHolder.AnonymousClass2 */

                @Override // androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationCancel(View view) {
                }

                @Override // androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationEnd(View view) {
                }

                @Override // androidx.core.view.ViewPropertyAnimatorListener
                public void onAnimationStart(View view) {
                    EffectStillItemHolder.this.mSelectedIndicator.setVisibility(0);
                }
            }).start();
        }

        @Override // com.android.camera.fragment.EffectItemAdapter.EffectItemHolder
        public /* bridge */ /* synthetic */ void requestRender() {
            super.requestRender();
        }
    }

    public interface IEffectItemListener {
        int getCurrentIndex();

        int getHolderHeight();

        int getHolderWidth();

        int getLastIndex();

        int getTextureHeight();

        int getTextureOffsetX();

        int getTextureOffsetY();

        int getTextureWidth();

        int getTotalWidth();

        boolean isAdded();

        boolean isAnimation();
    }

    public static class ItemChangeData {
        int index;
        boolean select;

        public ItemChangeData(boolean z, int i) {
            this.select = z;
            this.index = i;
        }

        public String toString() {
            return "ItemChangeData{select=" + this.select + ", index=" + this.index + '}';
        }
    }

    public EffectItemAdapter(Context context, ComponentData componentData) {
        this.mContext = context;
        this.mComponentData = componentData;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public EffectItemAdapter(Context context, ComponentData componentData, boolean z) {
        this.mContext = context;
        this.mComponentData = componentData;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mSupportRealtimeEffect = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mComponentData.getItems().size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        EffectItemHolder effectItemHolder = (EffectItemHolder) viewHolder;
        effectItemHolder.itemView.setTag(Integer.valueOf(i));
        effectItemHolder.bindEffectIndex(i, this.mComponentData.getItems().get(i));
        if (this.mSupportRealtimeEffect) {
            viewHolder.itemView.findViewById(R.id.effect_item_text).setRotation((float) this.mDegree);
            viewHolder.itemView.findViewById(R.id.effect_item_realtime).setRotation((float) this.mDisplayRotation);
            return;
        }
        viewHolder.itemView.setRotation((float) this.mDegree);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
        IEffectItemListener iEffectItemListener;
        if (list.isEmpty()) {
            onBindViewHolder(viewHolder, i);
        } else if (viewHolder instanceof EffectItemHolder) {
            EffectItemHolder effectItemHolder = (EffectItemHolder) viewHolder;
            if (list.get(0) instanceof ItemChangeData) {
                ItemChangeData itemChangeData = (ItemChangeData) list.get(0);
                if (!(!itemChangeData.select || (iEffectItemListener = this.mEffectItemListener) == null || iEffectItemListener.getCurrentIndex() == itemChangeData.index)) {
                    itemChangeData.select = false;
                }
                effectItemHolder.itemView.setActivated(itemChangeData.select);
                ImageView imageView = effectItemHolder.mSelectedIndicator;
                if (imageView != null) {
                    Util.updateSelectIndicator(imageView, itemChangeData.select, true);
                }
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (this.mSupportRealtimeEffect) {
            view = this.mLayoutInflater.inflate(R.layout.effect_realtime_item, viewGroup, false);
            viewHolder = new EffectRealtimeItemHolder(view);
        } else {
            view = this.mLayoutInflater.inflate(R.layout.effect_still_item, viewGroup, false);
            viewHolder = new EffectStillItemHolder(view);
        }
        view.setOnClickListener(this.mOnClickListener);
        FolmeUtils.handleListItemTouch(view);
        return viewHolder;
    }

    public void setDisplayRotation(int i) {
        this.mDisplayRotation = i;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnEffectItemListener(IEffectItemListener iEffectItemListener) {
        this.mEffectItemListener = iEffectItemListener;
    }

    public void setRotation(int i) {
        this.mDegree = i;
    }

    public void updateData(ComponentData componentData) {
        this.mComponentData = componentData;
        notifyDataSetChanged();
    }
}
