package com.xiaomi.stat;

public class NetAvailableEvent {
    public static final int RESULT_TYPE_0 = 0;
    public static final int RESULT_TYPE_1 = 1;
    public static final int RESULT_TYPE_2 = 2;

    /* renamed from: a  reason: collision with root package name */
    private String f400a;

    /* renamed from: b  reason: collision with root package name */
    private int f401b;

    /* renamed from: c  reason: collision with root package name */
    private int f402c;

    /* renamed from: d  reason: collision with root package name */
    private String f403d;

    /* renamed from: e  reason: collision with root package name */
    private int f404e;
    private long f;
    private int g;
    private String h;

    public static final class Builder {

        /* renamed from: a  reason: collision with root package name */
        private String f405a;

        /* renamed from: b  reason: collision with root package name */
        private int f406b;

        /* renamed from: c  reason: collision with root package name */
        private int f407c;

        /* renamed from: d  reason: collision with root package name */
        private String f408d;

        /* renamed from: e  reason: collision with root package name */
        private int f409e;
        private long f;
        private int g;
        private String h;

        public NetAvailableEvent build() {
            return new NetAvailableEvent(this);
        }

        public Builder exception(String str) {
            this.f408d = str;
            return this;
        }

        public Builder ext(String str) {
            this.h = str;
            return this;
        }

        public Builder flag(String str) {
            this.f405a = str;
            return this;
        }

        public Builder requestStartTime(long j) {
            this.f = j;
            return this;
        }

        public Builder responseCode(int i) {
            this.f406b = i;
            return this;
        }

        public Builder resultType(int i) {
            this.f409e = i;
            return this;
        }

        public Builder retryCount(int i) {
            this.g = i;
            return this;
        }

        public Builder statusCode(int i) {
            this.f407c = i;
            return this;
        }
    }

    private NetAvailableEvent(Builder builder) {
        this.f400a = builder.f405a;
        this.f401b = builder.f406b;
        this.f402c = builder.f407c;
        this.f403d = builder.f408d;
        this.f404e = builder.f409e;
        this.f = builder.f;
        this.g = builder.g;
        this.h = builder.h;
    }

    public String getException() {
        return this.f403d;
    }

    public String getExt() {
        return this.h;
    }

    public String getFlag() {
        return this.f400a;
    }

    public long getRequestStartTime() {
        return this.f;
    }

    public int getResponseCode() {
        return this.f401b;
    }

    public int getResultType() {
        return this.f404e;
    }

    public int getRetryCount() {
        return this.g;
    }

    public int getStatusCode() {
        return this.f402c;
    }
}
