package com.arcsoft.camera.utils;

/* compiled from: MSize */
public class g implements Comparable<g> {

    /* renamed from: a  reason: collision with root package name */
    private int f326a;

    /* renamed from: b  reason: collision with root package name */
    private int f327b;

    public g() {
    }

    public g(int i, int i2) {
        this.f326a = i;
        this.f327b = i2;
    }

    /* renamed from: a */
    public int c(g gVar) {
        if (gVar == null) {
            return 1;
        }
        int i = this.f326a;
        int i2 = gVar.f326a;
        return i == i2 ? this.f327b - gVar.f327b : i - i2;
    }

    public String a() {
        return new String("[" + this.f326a + "," + this.f327b + "]");
    }

    public boolean a(int i, int i2) {
        return this.f326a == i && this.f327b == i2;
    }

    public int b() {
        return (this.f326a * 31) + this.f327b;
    }

    public boolean b(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof g) || obj == null) {
            return false;
        }
        g gVar = (g) obj;
        return a(gVar.f326a, gVar.f327b);
    }
}
