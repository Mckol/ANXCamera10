package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.e.l;
import com.airbnb.lottie.model.d;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottieAnimationView extends AppCompatImageView {
    private static final String TAG = "LottieAnimationView";
    private static final T<Throwable> Va = new C0028f();
    private String Ea;
    @RawRes
    private int Fa;
    private final T<C0035m> Ha = new C0029g(this);
    private final T<Throwable> Ia = new C0030h(this);
    @Nullable
    private T<Throwable> Ja;
    @DrawableRes
    private int Ka = 0;
    private final Q La = new Q();
    private boolean Ma;
    private boolean Na = false;
    private boolean Oa = false;
    private boolean Pa = false;
    private boolean Qa = true;
    private RenderMode Ra = RenderMode.AUTOMATIC;
    private Set<V> Sa = new HashSet();
    private int Ta = 0;
    @Nullable
    private Z<C0035m> Ua;
    @Nullable
    private C0035m ha;

    /* access modifiers changed from: private */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new C0033k();
        String Ea;
        int Fa;
        boolean Ga;
        String oa;
        float progress;
        int repeatCount;
        int repeatMode;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.Ea = parcel.readString();
            this.progress = parcel.readFloat();
            this.Ga = parcel.readInt() != 1 ? false : true;
            this.oa = parcel.readString();
            this.repeatMode = parcel.readInt();
            this.repeatCount = parcel.readInt();
        }

        /* synthetic */ SavedState(Parcel parcel, C0028f fVar) {
            this(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.Ea);
            parcel.writeFloat(this.progress);
            parcel.writeInt(this.Ga ? 1 : 0);
            parcel.writeString(this.oa);
            parcel.writeInt(this.repeatMode);
            parcel.writeInt(this.repeatCount);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        a((AttributeSet) null);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public LottieAnimationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(attributeSet);
    }

    private void Lo() {
        Z<C0035m> z = this.Ua;
        if (z != null) {
            z.e(this.Ha);
            this.Ua.d(this.Ia);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003b, code lost:
        if (r3 != false) goto L_0x003d;
     */
    private void Mo() {
        C0035m mVar;
        int i = C0032j.bb[this.Ra.ordinal()];
        int i2 = 2;
        if (i != 1) {
            if (i != 2 && i == 3) {
                C0035m mVar2 = this.ha;
                boolean z = false;
                if ((mVar2 == null || !mVar2.ib() || Build.VERSION.SDK_INT >= 28) && (((mVar = this.ha) == null || mVar.gb() <= 4) && Build.VERSION.SDK_INT >= 21)) {
                    z = true;
                }
            }
            i2 = 1;
        }
        if (i2 != getLayerType()) {
            setLayerType(i2, null);
        }
    }

    private void O() {
        this.ha = null;
        this.La.O();
    }

    private void a(@Nullable AttributeSet attributeSet) {
        String string;
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.LottieAnimationView);
        boolean z = false;
        if (!isInEditMode()) {
            this.Qa = obtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_cacheComposition, true);
            boolean hasValue = obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_rawRes);
            boolean hasValue2 = obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_fileName);
            boolean hasValue3 = obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_url);
            if (!hasValue || !hasValue2) {
                if (hasValue) {
                    int resourceId = obtainStyledAttributes.getResourceId(R.styleable.LottieAnimationView_lottie_rawRes, 0);
                    if (resourceId != 0) {
                        k(resourceId);
                    }
                } else if (hasValue2) {
                    String string2 = obtainStyledAttributes.getString(R.styleable.LottieAnimationView_lottie_fileName);
                    if (string2 != null) {
                        u(string2);
                    }
                } else if (hasValue3 && (string = obtainStyledAttributes.getString(R.styleable.LottieAnimationView_lottie_url)) != null) {
                    y(string);
                }
                l(obtainStyledAttributes.getResourceId(R.styleable.LottieAnimationView_lottie_fallbackRes, 0));
            } else {
                throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use only one at once.");
            }
        }
        if (obtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_autoPlay, false)) {
            this.Oa = true;
            this.Pa = true;
        }
        if (obtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_loop, false)) {
            this.La.setRepeatCount(-1);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_repeatMode)) {
            setRepeatMode(obtainStyledAttributes.getInt(R.styleable.LottieAnimationView_lottie_repeatMode, 1));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_repeatCount)) {
            setRepeatCount(obtainStyledAttributes.getInt(R.styleable.LottieAnimationView_lottie_repeatCount, -1));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_speed)) {
            setSpeed(obtainStyledAttributes.getFloat(R.styleable.LottieAnimationView_lottie_speed, 1.0f));
        }
        z(obtainStyledAttributes.getString(R.styleable.LottieAnimationView_lottie_imageAssetsFolder));
        setProgress(obtainStyledAttributes.getFloat(R.styleable.LottieAnimationView_lottie_progress, 0.0f));
        o(obtainStyledAttributes.getBoolean(R.styleable.LottieAnimationView_lottie_enableMergePathsForKitKatAndAbove, false));
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_colorFilter)) {
            a(new d("**"), W.qk, new j(new da(obtainStyledAttributes.getColor(R.styleable.LottieAnimationView_lottie_colorFilter, 0))));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_scale)) {
            this.La.setScale(obtainStyledAttributes.getFloat(R.styleable.LottieAnimationView_lottie_scale, 1.0f));
        }
        if (obtainStyledAttributes.hasValue(R.styleable.LottieAnimationView_lottie_renderMode)) {
            int i = obtainStyledAttributes.getInt(R.styleable.LottieAnimationView_lottie_renderMode, RenderMode.AUTOMATIC.ordinal());
            if (i >= RenderMode.values().length) {
                i = RenderMode.AUTOMATIC.ordinal();
            }
            a(RenderMode.values()[i]);
        }
        if (getScaleType() != null) {
            this.La.setScaleType(getScaleType());
        }
        obtainStyledAttributes.recycle();
        Q q = this.La;
        if (h.F(getContext()) != 0.0f) {
            z = true;
        }
        q.b(Boolean.valueOf(z));
        Mo();
        this.Ma = true;
    }

    private void b(Z<C0035m> z) {
        O();
        Lo();
        this.Ua = z.c(this.Ha).b(this.Ia);
    }

    public float Q() {
        return this.La.Q();
    }

    public float R() {
        return this.La.R();
    }

    @MainThread
    public void S() {
        this.Pa = false;
        this.Oa = false;
        this.Na = false;
        this.La.S();
        Mo();
    }

    @MainThread
    public void T() {
        if (isShown()) {
            this.La.T();
            Mo();
            return;
        }
        this.Na = true;
    }

    @MainThread
    public void V() {
        if (isShown()) {
            this.La.V();
            Mo();
            return;
        }
        this.Na = true;
    }

    public void W() {
        this.La.W();
    }

    public void Y() {
        this.La.Y();
    }

    @Nullable
    public Bitmap a(String str, @Nullable Bitmap bitmap) {
        return this.La.a(str, bitmap);
    }

    public List<d> a(d dVar) {
        return this.La.a(dVar);
    }

    public void a(Animator.AnimatorListener animatorListener) {
        this.La.a(animatorListener);
    }

    public void a(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.La.a(animatorUpdateListener);
    }

    public void a(RenderMode renderMode) {
        this.Ra = renderMode;
        Mo();
    }

    public void a(@Nullable T<Throwable> t) {
        this.Ja = t;
    }

    public void a(C0012c cVar) {
        this.La.a(cVar);
    }

    public void a(AbstractC0026d dVar) {
        this.La.a(dVar);
    }

    public void a(ea eaVar) {
        this.La.a(eaVar);
    }

    public <T> void a(d dVar, T t, j<T> jVar) {
        this.La.a(dVar, t, jVar);
    }

    public <T> void a(d dVar, T t, l<T> lVar) {
        this.La.a(dVar, t, new C0031i(this, lVar));
    }

    public void a(InputStream inputStream, @Nullable String str) {
        b(C0047y.b(inputStream, str));
    }

    public boolean a(@NonNull V v) {
        C0035m mVar = this.ha;
        if (mVar != null) {
            v.a(mVar);
        }
        return this.Sa.add(v);
    }

    @Nullable
    public C0035m aa() {
        return this.ha;
    }

    public void b(@FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.La.b(f, f2);
    }

    public void b(int i, int i2) {
        this.La.b(i, i2);
    }

    public void b(Animator.AnimatorListener animatorListener) {
        this.La.b(animatorListener);
    }

    public void b(String str, String str2, boolean z) {
        this.La.b(str, str2, z);
    }

    public boolean b(@NonNull V v) {
        return this.Sa.remove(v);
    }

    @Nullable
    public String ba() {
        return this.La.ba();
    }

    public void buildDrawingCache(boolean z) {
        C0027e.beginSection("buildDrawingCache");
        this.Ta++;
        super.buildDrawingCache(z);
        if (this.Ta == 1 && getWidth() > 0 && getHeight() > 0 && getLayerType() == 1 && getDrawingCache(z) == null) {
            a(RenderMode.HARDWARE);
        }
        this.Ta--;
        C0027e.D("buildDrawingCache");
    }

    public void c(@NonNull C0035m mVar) {
        if (C0027e.DBG) {
            String str = TAG;
            Log.v(str, "Set Composition \n" + mVar);
        }
        this.La.setCallback(this);
        this.ha = mVar;
        boolean c2 = this.La.c(mVar);
        Mo();
        if (getDrawable() != this.La || c2) {
            setImageDrawable(null);
            setImageDrawable(this.La);
            onVisibilityChanged(this, getVisibility());
            requestLayout();
            for (V v : this.Sa) {
                v.a(mVar);
            }
        }
    }

    @Nullable
    public ca ca() {
        return this.La.ca();
    }

    @MainThread
    public void cancelAnimation() {
        this.Na = false;
        this.La.cancelAnimation();
        Mo();
    }

    public boolean ea() {
        return this.La.ea();
    }

    public void f(int i) {
        this.La.f(i);
    }

    public boolean fa() {
        return this.La.fa();
    }

    public void g(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.La.g(f);
    }

    public long getDuration() {
        C0035m mVar = this.ha;
        if (mVar != null) {
            return (long) mVar.getDuration();
        }
        return 0;
    }

    public int getFrame() {
        return this.La.getFrame();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.La.getProgress();
    }

    public int getRepeatCount() {
        return this.La.getRepeatCount();
    }

    public int getRepeatMode() {
        return this.La.getRepeatMode();
    }

    public float getScale() {
        return this.La.getScale();
    }

    public float getSpeed() {
        return this.La.getSpeed();
    }

    public void h(float f) {
        this.La.h(f);
    }

    public boolean ha() {
        return this.La.ha();
    }

    public void ia() {
        this.La.ia();
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        Drawable drawable2 = getDrawable();
        Q q = this.La;
        if (drawable2 == q) {
            super.invalidateDrawable(q);
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    public boolean isAnimating() {
        return this.La.isAnimating();
    }

    public void j(int i) {
        this.La.j(i);
    }

    public void k(@RawRes int i) {
        this.Fa = i;
        this.Ea = null;
        b(this.Qa ? C0047y.a(getContext(), i) : C0047y.a(getContext(), i, (String) null));
    }

    public void l(@DrawableRes int i) {
        this.Ka = i;
    }

    public void m(String str) {
        this.La.m(str);
    }

    public void m(String str, @Nullable String str2) {
        a(new ByteArrayInputStream(str.getBytes()), str2);
    }

    public void n(String str) {
        this.La.n(str);
    }

    public void o(String str) {
        this.La.o(str);
    }

    public void o(boolean z) {
        this.La.o(z);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.Pa || this.Oa) {
            T();
            this.Pa = false;
            this.Oa = false;
        }
        if (Build.VERSION.SDK_INT < 23) {
            onVisibilityChanged(this, getVisibility());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (isAnimating()) {
            cancelAnimation();
            this.Oa = true;
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.Ea = savedState.Ea;
        if (!TextUtils.isEmpty(this.Ea)) {
            u(this.Ea);
        }
        this.Fa = savedState.Fa;
        int i = this.Fa;
        if (i != 0) {
            k(i);
        }
        setProgress(savedState.progress);
        if (savedState.Ga) {
            T();
        }
        this.La.l(savedState.oa);
        setRepeatMode(savedState.repeatMode);
        setRepeatCount(savedState.repeatCount);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.Ea = this.Ea;
        savedState.Fa = this.Fa;
        savedState.progress = this.La.getProgress();
        savedState.Ga = this.La.isAnimating() || (!ViewCompat.isAttachedToWindow(this) && this.Oa);
        savedState.oa = this.La.ba();
        savedState.repeatMode = this.La.getRepeatMode();
        savedState.repeatCount = this.La.getRepeatCount();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(@NonNull View view, int i) {
        if (this.Ma) {
            if (isShown()) {
                if (this.Na) {
                    V();
                    this.Na = false;
                }
            } else if (isAnimating()) {
                S();
                this.Na = true;
            }
        }
    }

    @Deprecated
    public void p(boolean z) {
        this.La.setRepeatCount(z ? -1 : 0);
    }

    public void pa() {
        this.Sa.clear();
    }

    public void q(boolean z) {
        this.La.q(z);
    }

    public void r(boolean z) {
        this.La.r(z);
    }

    public void removeAllUpdateListeners() {
        this.La.removeAllUpdateListeners();
    }

    public void removeUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.La.b(animatorUpdateListener);
    }

    public void s(boolean z) {
        this.La.s(z);
    }

    public void setFrame(int i) {
        this.La.setFrame(i);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView
    public void setImageBitmap(Bitmap bitmap) {
        Lo();
        super.setImageBitmap(bitmap);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView
    public void setImageDrawable(Drawable drawable) {
        Lo();
        super.setImageDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView
    public void setImageResource(int i) {
        Lo();
        super.setImageResource(i);
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.La.setProgress(f);
    }

    public void setRepeatCount(int i) {
        this.La.setRepeatCount(i);
    }

    public void setRepeatMode(int i) {
        this.La.setRepeatMode(i);
    }

    public void setScale(float f) {
        this.La.setScale(f);
        if (getDrawable() == this.La) {
            setImageDrawable(null);
            setImageDrawable(this.La);
        }
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        super.setScaleType(scaleType);
        Q q = this.La;
        if (q != null) {
            q.setScaleType(scaleType);
        }
    }

    public void setSpeed(float f) {
        this.La.setSpeed(f);
    }

    public void t(boolean z) {
        this.Qa = z;
    }

    public void u(String str) {
        this.Ea = str;
        this.Fa = 0;
        b(this.Qa ? C0047y.h(getContext(), str) : C0047y.c(getContext(), str, null));
    }

    @Deprecated
    public void x(String str) {
        m(str, null);
    }

    public void y(String str) {
        b(this.Qa ? C0047y.j(getContext(), str) : C0047y.e(getContext(), str, null));
    }

    public void z(String str) {
        this.La.l(str);
    }
}
