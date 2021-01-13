package com.xiaomi.fenshen;

import android.content.Context;
import android.text.TextUtils;
import com.android.camera.CameraAppImpl;
import com.android.camera.log.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class CloneUtil {
    private static final String APU_ASSET_FILE_NAME = "apu_model.json";
    public static final String DEFAULT_VERSION = "v_default";
    private static final String SNPE_ASSET_FILE_NAME = "snpe_model.json";
    private static final String TAG = "CloneUtil";

    private static String convertStreamToString(InputStream inputStream) {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : "";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        if (r2 != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0028, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0029, code lost:
        r3.addSuppressed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002c, code lost:
        throw r0;
     */
    private static String getAssetCache(String str, Context context) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            InputStream open = context.getApplicationContext().getAssets().open(str);
            String convertStreamToString = convertStreamToString(open);
            if (open != null) {
                open.close();
            }
            return convertStreamToString;
        } catch (IOException unused) {
            Log.w(TAG, "open file failed, use default v_default");
            return "";
        }
    }

    public static String getCloneModelVersion(boolean z) {
        String assetCache = getAssetCache(z ? APU_ASSET_FILE_NAME : SNPE_ASSET_FILE_NAME, CameraAppImpl.getAndroidContext());
        if (TextUtils.isEmpty(assetCache)) {
            return DEFAULT_VERSION;
        }
        try {
            Object obj = new JSONObject(assetCache).get("version");
            if (obj == null) {
                return DEFAULT_VERSION;
            }
            String obj2 = obj.toString();
            return TextUtils.isEmpty(obj2) ? DEFAULT_VERSION : obj2;
        } catch (JSONException unused) {
            Log.w(TAG, "getCloneModelVersion parse json failed, use default v_default");
            return DEFAULT_VERSION;
        }
    }
}
