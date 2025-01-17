package com.android.camera;

import android.location.Location;
import android.os.Bundle;
import android.os.HandlerThread;
import com.android.camera.log.Log;
import com.android.camera.permission.PermissionManager;
import java.util.Timer;
import java.util.TimerTask;

public class LocationManager {
    private static final int GPS_REQUEST_LOCATION_TIME_OUT = 60000;
    private static final long LOCATION_TIME_THRESHOLD = 3600000;
    private static final String TAG = "LocationManager";
    private static final long VALID_LAST_KNOWN_LOCATION_AGE = 180000;
    private Location mCacheLocation;
    private Location mLastKnownLocation;
    private Listener mListener;
    LocationListener[] mLocationListeners;
    private android.location.LocationManager mLocationManager;
    private boolean mRecordLocation;
    private HandlerThread mThreadHandler;
    private Timer mTimer;

    public interface Listener {
        void hideGpsOnScreenIndicator();

        void showGpsOnScreenIndicator(boolean z);
    }

    /* access modifiers changed from: private */
    public class LocationListener implements android.location.LocationListener {
        Location mLastLocation;
        String mProvider;
        boolean mValid = false;

        public LocationListener(String str) {
            this.mProvider = str;
            this.mLastLocation = new Location(this.mProvider);
        }

        public Location current() {
            if (this.mValid) {
                return this.mLastLocation;
            }
            return null;
        }

        public void onLocationChanged(Location location) {
            if (location.getLatitude() != 0.0d || location.getLongitude() != 0.0d) {
                if (LocationManager.this.mRecordLocation && "gps".equals(this.mProvider)) {
                    LocationManager.this.cancelTimer();
                    if (LocationManager.this.mListener != null) {
                        LocationManager.this.mListener.showGpsOnScreenIndicator(true);
                    }
                }
                if (!this.mValid) {
                    Log.d(LocationManager.TAG, "Got first location, it is from " + this.mProvider);
                } else {
                    Log.v(LocationManager.TAG, "update location, it is from " + this.mProvider);
                }
                this.mLastLocation.set(location);
                LocationManager.this.updateCacheLocation(this.mLastLocation);
                this.mValid = true;
            }
        }

        public void onProviderDisabled(String str) {
            this.mValid = false;
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            if (i == 0 || i == 1) {
                this.mValid = false;
                if (LocationManager.this.mListener != null && LocationManager.this.mRecordLocation && "gps".equals(str)) {
                    LocationManager.this.mListener.showGpsOnScreenIndicator(false);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static class LocationManagerHolder {
        private static LocationManager sLocationManager = new LocationManager();

        private LocationManagerHolder() {
        }
    }

    private LocationManager() {
        this.mLocationListeners = new LocationListener[]{new LocationListener("gps"), new LocationListener("network")};
        this.mThreadHandler = new HandlerThread("Camera Handler Thread");
        this.mThreadHandler.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void cancelTimer() {
        Timer timer = this.mTimer;
        if (timer != null) {
            timer.cancel();
            this.mTimer = null;
        }
    }

    private Location getBetterLocation(Location location, Location location2) {
        return location2 == null ? location : (location == null || location.getTime() < location2.getTime() || (location.getTime() == location2.getTime() && "gps".equals(location2.getProvider()))) ? location2 : location;
    }

    private void getLastLocation() {
        Location location;
        try {
            this.mLastKnownLocation = getBetterLocation(this.mLocationManager.getLastKnownLocation("gps"), this.mLocationManager.getLastKnownLocation("network"));
            location = getBetterLocation(this.mCacheLocation, this.mLastKnownLocation);
        } catch (SecurityException e2) {
            Log.e(TAG, "fail to request last location update, ignore", e2);
            location = this.mCacheLocation;
        }
        if (isValidLastKnownLocation(location)) {
            this.mCacheLocation = location;
        } else {
            this.mCacheLocation = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("last cache location is ");
        sb.append(this.mCacheLocation != null ? "not null" : "null");
        Log.d(TAG, sb.toString());
    }

    public static LocationManager instance() {
        return LocationManagerHolder.sLocationManager;
    }

    private boolean isValidLastKnownLocation(Location location) {
        return location != null && Math.abs(System.currentTimeMillis() - location.getTime()) < VALID_LAST_KNOWN_LOCATION_AGE;
    }

    private void startReceivingLocationUpdates() {
        if (this.mLocationManager == null) {
            this.mLocationManager = (android.location.LocationManager) CameraAppImpl.getAndroidContext().getSystemService("location");
        }
        android.location.LocationManager locationManager = this.mLocationManager;
        if (locationManager != null) {
            try {
                locationManager.requestLocationUpdates("network", 1000, 0.0f, this.mLocationListeners[1], this.mThreadHandler.getLooper());
            } catch (SecurityException e2) {
                Log.i(TAG, "fail to request location update, ignore", e2);
            } catch (IllegalArgumentException e3) {
                Log.d(TAG, "provider does not exist " + e3.getMessage());
            }
            try {
                this.mLocationManager.requestLocationUpdates("gps", 1000, 0.0f, this.mLocationListeners[0], this.mThreadHandler.getLooper());
                cancelTimer();
                this.mTimer = new Timer(true);
                this.mTimer.schedule(new TimerTask() {
                    /* class com.android.camera.LocationManager.AnonymousClass1 */

                    public void run() {
                        LocationManager.this.stopReceivingGPSLocationUpdates();
                        LocationManager.this.mTimer = null;
                    }
                }, 60000);
                if (this.mListener != null) {
                    this.mListener.showGpsOnScreenIndicator(false);
                }
            } catch (SecurityException e4) {
                Log.i(TAG, "fail to request location update, ignore", e4);
            } catch (IllegalArgumentException e5) {
                Log.d(TAG, "provider does not exist " + e5.getMessage());
            }
            Log.d(TAG, "startReceivingLocationUpdates");
            getLastLocation();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stopReceivingGPSLocationUpdates() {
        android.location.LocationManager locationManager = this.mLocationManager;
        if (locationManager != null) {
            try {
                locationManager.removeUpdates(this.mLocationListeners[0]);
            } catch (Exception e2) {
                Log.i(TAG, "fail to remove location listeners, ignore", e2);
            }
            this.mLocationListeners[0].mValid = false;
            Log.d(TAG, "stopReceivingGPSLocationUpdates");
        }
        Listener listener = this.mListener;
        if (listener != null) {
            listener.hideGpsOnScreenIndicator();
        }
    }

    private void stopReceivingLocationUpdates() {
        cancelTimer();
        if (this.mLocationManager != null) {
            int i = 0;
            while (true) {
                LocationListener[] locationListenerArr = this.mLocationListeners;
                if (i >= locationListenerArr.length) {
                    break;
                }
                try {
                    this.mLocationManager.removeUpdates(locationListenerArr[i]);
                } catch (Exception e2) {
                    Log.i(TAG, "fail to remove location listeners, ignore", e2);
                }
                this.mLocationListeners[i].mValid = false;
                i++;
            }
            Log.d(TAG, "stopReceivingLocationUpdates");
        }
        Listener listener = this.mListener;
        if (listener != null) {
            listener.hideGpsOnScreenIndicator();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void updateCacheLocation(Location location) {
        Location betterLocation = getBetterLocation(this.mCacheLocation, location);
        Location location2 = this.mCacheLocation;
        if (location2 != null) {
            location2.set(betterLocation);
        } else {
            this.mCacheLocation = new Location(betterLocation);
        }
    }

    private static Location validateLocation(Location location) {
        long currentTimeMillis = System.currentTimeMillis();
        if (location != null && Math.abs(location.getTime() - currentTimeMillis) > LOCATION_TIME_THRESHOLD) {
            Log.d(TAG, "validateLocation: modify to now from " + location.getTime());
            location.setTime(currentTimeMillis);
        }
        return location;
    }

    public Location getCurrentLocation() {
        if (!this.mRecordLocation) {
            return null;
        }
        int i = 0;
        while (true) {
            LocationListener[] locationListenerArr = this.mLocationListeners;
            if (i < locationListenerArr.length) {
                Location current = locationListenerArr[i].current();
                if (current != null) {
                    Log.v(TAG, "get current location, it is from " + this.mLocationListeners[i].mProvider);
                    validateLocation(current);
                    return current;
                }
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("No location received yet. cache location is ");
                sb.append(this.mCacheLocation != null ? "not null" : "null");
                Log.d(TAG, sb.toString());
                Location location = this.mCacheLocation;
                validateLocation(location);
                return location;
            }
        }
    }

    public Location getLastKnownLocation() {
        if (!this.mRecordLocation) {
            return null;
        }
        return this.mLastKnownLocation;
    }

    public void recordLocation(boolean z) {
        if (this.mRecordLocation != z) {
            this.mRecordLocation = z;
            if (!z || !PermissionManager.checkCameraLocationPermissions()) {
                stopReceivingLocationUpdates();
            } else {
                startReceivingLocationUpdates();
            }
        }
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void unsetListener(Listener listener) {
        if (this.mListener == listener) {
            this.mListener = null;
        }
    }
}
