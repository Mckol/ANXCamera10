package com.arcsoft.camera.utils;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: MPoint */
public class b implements Parcelable {

    /* renamed from: c  reason: collision with root package name */
    public static final Parcelable.Creator<b> f316c = new f();

    /* renamed from: a  reason: collision with root package name */
    public int f317a;

    /* renamed from: b  reason: collision with root package name */
    public int f318b;

    public b() {
    }

    public b(int i, int i2) {
        this.f317a = i;
        this.f318b = i2;
    }

    public b(b bVar) {
        this.f317a = bVar.f317a;
        this.f318b = bVar.f318b;
    }

    public int a() {
        return 0;
    }

    public void a(Parcel parcel) {
        this.f317a = parcel.readInt();
        this.f318b = parcel.readInt();
    }

    public void a(Parcel parcel, int i) {
        parcel.writeInt(this.f317a);
        parcel.writeInt(this.f318b);
    }

    public final boolean a(int i, int i2) {
        return this.f317a == i && this.f318b == i2;
    }

    public int b() {
        return (this.f317a * 31) + this.f318b;
    }

    public boolean b(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && b.class == obj.getClass()) {
            b bVar = (b) obj;
            if (this.f317a == bVar.f317a && this.f318b == bVar.f318b) {
                return true;
            }
        }
        return false;
    }

    public final void c() {
        this.f317a = -this.f317a;
        this.f318b = -this.f318b;
    }

    public final void c(int i, int i2) {
        this.f317a += i;
        this.f318b += i2;
    }

    public String d() {
        return "Point(" + this.f317a + ", " + this.f318b + ")";
    }

    public void d(int i, int i2) {
        this.f317a = i;
        this.f318b = i2;
    }
}
