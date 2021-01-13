package com.arcsoft.avatar2;

public class PreHumanConfigInfo {

    /* renamed from: a  reason: collision with root package name */
    private String f173a = "";

    /* renamed from: b  reason: collision with root package name */
    private String f174b = "";

    /* renamed from: c  reason: collision with root package name */
    private String f175c = "";

    public String getPreHumanConfigPath() {
        return this.f175c;
    }

    public String getPreHumanName() {
        return this.f173a;
    }

    public String getPreHumanThumbnailPath() {
        return this.f174b;
    }

    public void setPreHumanConfigPath(String str) {
        this.f175c = str;
    }

    public void setPreHumanName(String str) {
        this.f173a = str;
    }

    public void setPreHumanThumbnailPath(String str) {
        this.f174b = str;
    }

    public String toString() {
        return "name = " + this.f173a + "\n, thumbNail path = " + this.f174b + "\n, config path = " + this.f175c;
    }
}
