package com.airbnb.lottie.model;

/* compiled from: Marker */
public class g {
    private static String CARRIAGE_RETURN = "\r";
    public final float Yd;
    public final float lb;
    private final String name;

    public g(String str, float f, float f2) {
        this.name = str;
        this.Yd = f2;
        this.lb = f;
    }

    public boolean N(String str) {
        if (this.name.equalsIgnoreCase(str)) {
            return true;
        }
        if (this.name.endsWith(CARRIAGE_RETURN)) {
            String str2 = this.name;
            if (str2.substring(0, str2.length() - 1).equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}
