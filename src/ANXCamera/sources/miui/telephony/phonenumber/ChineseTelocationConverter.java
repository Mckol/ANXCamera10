package miui.telephony.phonenumber;

import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import miui.telephony.phonenumber.TelocationProvider;
import miui.util.AppConstants;

public class ChineseTelocationConverter {
    public static final int AREACODE_INDEX = 1;
    public static final int LOCATION_INDEX = 0;
    public static final int LOCATION_KIND = 0;
    private static final String TAG = "ChineseTelocation";
    public static final Uri TELOCATION_CONTENT_URI = Uri.parse("content://com.miui.core.telocation");
    public static final int UNIQUE_ID_NONE = 0;
    private static ChineseTelocationConverter sInstance = new ChineseTelocationConverter();
    private Context context = AppConstants.getCurrentApplication();

    private ChineseTelocationConverter() {
    }

    private void checkContext() {
        if (this.context == null) {
            this.context = AppConstants.getCurrentApplication();
        }
    }

    public static ChineseTelocationConverter getInstance() {
        return sInstance;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0075, code lost:
        if (r8.isClosed() == false) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0077, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0087, code lost:
        if (r8.isClosed() == false) goto L_0x0077;
     */
    public String getAreaCode(Address address) {
        String str = "";
        if (address == null) {
            return str;
        }
        checkContext();
        String adminArea = address.getAdminArea();
        String locality = address.getLocality();
        Log.d(TAG, "adminArea:" + adminArea + " locality:" + locality);
        Uri build = TELOCATION_CONTENT_URI.buildUpon().appendPath("query_area_code_by_address").appendQueryParameter(TelocationProvider.Contract.Params.ADMIN_AREA, String.valueOf(adminArea)).appendQueryParameter(TelocationProvider.Contract.Params.LOCALITY, String.valueOf(locality)).build();
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(build, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                str = cursor.getString(7);
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0056, code lost:
        if (r8.isClosed() == false) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0058, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        if (r8.isClosed() == false) goto L_0x0058;
     */
    public String getAreaCode(CharSequence charSequence, int i, int i2) {
        checkContext();
        Uri build = TELOCATION_CONTENT_URI.buildUpon().appendPath("query_area_code").appendQueryParameter(TelocationProvider.Contract.Params.NUMBER, String.valueOf(charSequence)).appendQueryParameter("start", String.valueOf(i)).appendQueryParameter(TelocationProvider.Contract.Params.LENGTH, String.valueOf(i2)).build();
        String str = "";
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(build, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                str = cursor.getString(2);
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0060, code lost:
        if (r8.isClosed() == false) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0062, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0072, code lost:
        if (r8.isClosed() == false) goto L_0x0062;
     */
    public String getLocation(CharSequence charSequence, int i, int i2, boolean z) {
        checkContext();
        Uri build = TELOCATION_CONTENT_URI.buildUpon().appendPath(TelocationProvider.Contract.QUERY_LOCATION).appendQueryParameter(TelocationProvider.Contract.Params.NUMBER, String.valueOf(charSequence)).appendQueryParameter("start", String.valueOf(i)).appendQueryParameter(TelocationProvider.Contract.Params.LENGTH, String.valueOf(i2)).appendQueryParameter(TelocationProvider.Contract.Params.WITH_AREA_CODE, String.valueOf(z)).build();
        String str = "";
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(build, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                str = cursor.getString(1);
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0056, code lost:
        if (r8.isClosed() == false) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0058, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        if (r8.isClosed() == false) goto L_0x0058;
     */
    public String getOperator(CharSequence charSequence, int i, int i2) {
        checkContext();
        Uri build = TELOCATION_CONTENT_URI.buildUpon().appendPath(TelocationProvider.Contract.QUERY_OPERATOR).appendQueryParameter(TelocationProvider.Contract.Params.NUMBER, String.valueOf(charSequence)).appendQueryParameter("start", String.valueOf(i)).appendQueryParameter(TelocationProvider.Contract.Params.LENGTH, String.valueOf(i2)).build();
        String str = "";
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(build, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                str = cursor.getString(4);
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x006d, code lost:
        if (r7.isClosed() == false) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x006f, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007f, code lost:
        if (r7.isClosed() == false) goto L_0x006f;
     */
    public int getUniqId(CharSequence charSequence, int i, int i2, boolean z) {
        checkContext();
        Uri build = TELOCATION_CONTENT_URI.buildUpon().appendPath(TelocationProvider.Contract.QUERY_UNIQ_ID).appendQueryParameter(TelocationProvider.Contract.Params.NUMBER, String.valueOf(charSequence)).appendQueryParameter("start", String.valueOf(i)).appendQueryParameter(TelocationProvider.Contract.Params.LENGTH, String.valueOf(i2)).appendQueryParameter(TelocationProvider.Contract.Params.ENABLE_MOBILE, String.valueOf(z)).build();
        Cursor cursor = null;
        int i3 = 0;
        try {
            cursor = this.context.getContentResolver().query(build, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String string = cursor.getString(6);
                if (!TextUtils.isEmpty(string)) {
                    i3 = Integer.valueOf(string).intValue();
                }
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return i3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0045, code lost:
        if (r0.isClosed() == false) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0047, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0057, code lost:
        if (r0.isClosed() == false) goto L_0x0047;
     */
    public int getVersion() {
        checkContext();
        Cursor cursor = null;
        int i = -1;
        try {
            cursor = this.context.getContentResolver().query(TELOCATION_CONTENT_URI.buildUpon().appendPath(TelocationProvider.Contract.QUERY_VERSION).build(), null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String string = cursor.getString(5);
                if (!TextUtils.isEmpty(string)) {
                    i = Integer.valueOf(string).intValue();
                }
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0056, code lost:
        if (r8.isClosed() == false) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0058, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0068, code lost:
        if (r8.isClosed() == false) goto L_0x0058;
     */
    public String parseAreaCode(CharSequence charSequence, int i, int i2) {
        checkContext();
        Uri build = TELOCATION_CONTENT_URI.buildUpon().appendPath("parse_area_code").appendQueryParameter(TelocationProvider.Contract.Params.NUMBER, String.valueOf(charSequence)).appendQueryParameter("start", String.valueOf(i)).appendQueryParameter(TelocationProvider.Contract.Params.LENGTH, String.valueOf(i2)).build();
        String str = "";
        Cursor cursor = null;
        try {
            cursor = this.context.getContentResolver().query(build, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                str = cursor.getString(3);
            }
            if (cursor != null) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (0 != 0) {
            }
        } catch (Throwable th) {
            if (0 != 0 && !cursor.isClosed()) {
                cursor.close();
            }
            throw th;
        }
        return str;
    }

    public void reloadInstance() {
        checkContext();
        try {
            Cursor query = this.context.getContentResolver().query(TELOCATION_CONTENT_URI.buildUpon().appendPath(TelocationProvider.Contract.RELOAD).build(), null, null, null, null);
            if (query != null && !query.isClosed()) {
                query.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
