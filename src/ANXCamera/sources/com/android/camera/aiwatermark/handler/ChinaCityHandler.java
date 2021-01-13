package com.android.camera.aiwatermark.handler;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.android.camera.aiwatermark.data.CityWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.log.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChinaCityHandler extends CityHandler {
    private static final String TAG = "ChinaCityHandler";
    private Context mContext = null;

    public ChinaCityHandler(boolean z, Context context) {
        super(z);
        this.mContext = context;
    }

    private String format(String str) {
        return str != null ? str.replace(" ", "") : "";
    }

    @Override // com.android.camera.aiwatermark.handler.CityHandler
    public String getAddress(double d2, double d3) {
        Geocoder geocoder = new Geocoder(this.mContext, Locale.ENGLISH);
        StringBuilder sb = new StringBuilder();
        try {
            List<Address> fromLocation = geocoder.getFromLocation(d2, d3, 1);
            if (fromLocation != null) {
                for (Address address : fromLocation) {
                    if (address != null) {
                        sb.append(format(address.getLocality()));
                        sb.append(format(address.getSubAdminArea()));
                        sb.append(format(address.getAdminArea()));
                    }
                }
            }
        } catch (IOException | IllegalArgumentException e2) {
            String str = TAG;
            Log.e(str, "[getAddress] ex = " + e2.getMessage());
        }
        return sb.toString();
    }

    @Override // com.android.camera.aiwatermark.handler.CityHandler
    public ArrayList<WatermarkItem> getCityWatermarkList() {
        return new CityWatermark().getForAI();
    }
}
