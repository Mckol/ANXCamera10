package com.android.camera.aiwatermark.handler;

import android.location.Location;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.camera.LocationManager;
import com.android.camera.aiwatermark.data.Region;
import com.android.camera.aiwatermark.data.ScenicSpotsWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class ScenicSpotsHandler extends AbstractHandler {
    private static final String TAG = "ScenicSpotsHandler";

    public ScenicSpotsHandler(boolean z) {
        super(z);
        this.mData = new ScenicSpotsWatermark();
    }

    private Location getLocation() {
        return LocationManager.instance().getCurrentLocation();
    }

    /* access modifiers changed from: protected */
    public String findScenicSpot() {
        double d2;
        double d3;
        Location location = getLocation();
        String str = TAG;
        Log.d(str, "loc=" + location);
        if (location == null) {
            return null;
        }
        if (AbstractHandler.debugGPS()) {
            d3 = Double.valueOf(SystemProperties.get("test.gps.latitude", "0")).doubleValue();
            d2 = Double.valueOf(SystemProperties.get("test.gps.longtitude", "0")).doubleValue();
        } else {
            d3 = location.getLatitude();
            d2 = location.getLongitude();
        }
        HashMap<String, Region> regionMap = getRegionMap();
        if (regionMap == null || regionMap.isEmpty()) {
            return null;
        }
        for (String str2 : regionMap.keySet()) {
            boolean isInRegion = getRegionMap().get(str2).isInRegion(d3, d2);
            String str3 = TAG;
            Log.d(str3, "key=" + str2 + "; isInRegion=" + isInRegion);
            if (isInRegion) {
                return str2;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        String findScenicSpot = findScenicSpot();
        if (findScenicSpot == null) {
            return null;
        }
        Iterator<WatermarkItem> it = getWatermarkList().iterator();
        while (it.hasNext()) {
            WatermarkItem next = it.next();
            if (TextUtils.equals(findScenicSpot, next.getKey())) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public abstract HashMap<String, Region> getRegionMap();

    /* access modifiers changed from: protected */
    public abstract ArrayList<WatermarkItem> getWatermarkList();
}
