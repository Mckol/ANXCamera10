package com.android.camera.aiwatermark.handler;

import android.location.Location;
import android.os.SystemProperties;
import com.android.camera.LocationManager;
import com.android.camera.aiwatermark.data.CityWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public abstract class CityHandler extends AbstractHandler {
    private static final String TAG = "CityHandler";

    public CityHandler(boolean z) {
        super(z);
        this.mData = new CityWatermark();
    }

    private Location getLocation() {
        return LocationManager.instance().getCurrentLocation();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        double d2;
        double d3;
        Location location = getLocation();
        if (location != null) {
            if (AbstractHandler.debugGPS()) {
                d3 = Double.valueOf(SystemProperties.get("test.gps.latitude", "0")).doubleValue();
                d2 = Double.valueOf(SystemProperties.get("test.gps.longtitude", "0")).doubleValue();
            } else {
                d3 = location.getLatitude();
                d2 = location.getLongitude();
            }
            String address = getAddress(d3, d2);
            if (address != null) {
                Iterator<WatermarkItem> it = getCityWatermarkList().iterator();
                while (it.hasNext()) {
                    WatermarkItem next = it.next();
                    if (address.toLowerCase(Locale.ENGLISH).contains(next.getKey().toLowerCase(Locale.ENGLISH))) {
                        return next;
                    }
                }
            }
        }
        Log.d(TAG, "CityHandler findWatermark");
        return null;
    }

    public abstract String getAddress(double d2, double d3);

    public abstract ArrayList<WatermarkItem> getCityWatermarkList();
}
