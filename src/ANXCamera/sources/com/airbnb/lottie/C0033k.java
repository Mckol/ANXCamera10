package com.airbnb.lottie;

import android.os.Parcel;
import android.os.Parcelable;
import com.airbnb.lottie.LottieAnimationView;

/* renamed from: com.airbnb.lottie.k  reason: case insensitive filesystem */
/* compiled from: LottieAnimationView */
class C0033k implements Parcelable.Creator<LottieAnimationView.SavedState> {
    C0033k() {
    }

    @Override // android.os.Parcelable.Creator
    public LottieAnimationView.SavedState createFromParcel(Parcel parcel) {
        return new LottieAnimationView.SavedState(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    public LottieAnimationView.SavedState[] newArray(int i) {
        return new LottieAnimationView.SavedState[i];
    }
}
