package com.xiaomi.stat;

public class HttpEvent {

    /* renamed from: a  reason: collision with root package name */
    private int f388a;

    /* renamed from: b  reason: collision with root package name */
    private long f389b;

    /* renamed from: c  reason: collision with root package name */
    private long f390c;

    /* renamed from: d  reason: collision with root package name */
    private String f391d;

    /* renamed from: e  reason: collision with root package name */
    private String f392e;

    public HttpEvent(String str, long j) {
        this(str, j, -1, (String) null);
    }

    public HttpEvent(String str, long j, int i, String str2) {
        this(str, j, 0, i, str2);
    }

    public HttpEvent(String str, long j, long j2) {
        this(str, j, j2, -1, null);
    }

    public HttpEvent(String str, long j, long j2, int i) {
        this(str, j, j2, i, null);
    }

    public HttpEvent(String str, long j, long j2, int i, String str2) {
        this.f390c = 0;
        this.f391d = str;
        this.f389b = j;
        this.f388a = i;
        this.f392e = str2;
        this.f390c = j2;
    }

    public HttpEvent(String str, long j, String str2) {
        this(str, j, -1, str2);
    }

    public String getExceptionName() {
        return this.f392e;
    }

    public long getNetFlow() {
        return this.f390c;
    }

    public int getResponseCode() {
        return this.f388a;
    }

    public long getTimeCost() {
        return this.f389b;
    }

    public String getUrl() {
        return this.f391d;
    }
}
