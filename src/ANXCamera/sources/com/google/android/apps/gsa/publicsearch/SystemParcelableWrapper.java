package com.google.android.apps.gsa.publicsearch;

import android.os.Parcel;
import android.os.Parcelable;

public final class SystemParcelableWrapper implements Parcelable {
    public static final Parcelable.Creator<SystemParcelableWrapper> CREATOR = new Parcelable.Creator<SystemParcelableWrapper>() {
        /* class com.google.android.apps.gsa.publicsearch.SystemParcelableWrapper.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public SystemParcelableWrapper createFromParcel(Parcel parcel) {
            return new SystemParcelableWrapper(parcel.readParcelable(null));
        }

        @Override // android.os.Parcelable.Creator
        public SystemParcelableWrapper[] newArray(int i) {
            return new SystemParcelableWrapper[i];
        }
    };
    private final Parcelable parcelable;

    public SystemParcelableWrapper(Parcelable parcelable2) {
        if (isSystemParcelable(parcelable2)) {
            this.parcelable = parcelable2;
            return;
        }
        throw new IllegalArgumentException("Only Android system classes can be passed in SystemParcelableWrapper.");
    }

    static boolean isSystemParcelable(Parcelable parcelable2) {
        return parcelable2.getClass().getName().startsWith("android.os.");
    }

    public int describeContents() {
        return 0;
    }

    public Parcelable getParcelable() {
        return this.parcelable;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.parcelable, i);
    }
}
