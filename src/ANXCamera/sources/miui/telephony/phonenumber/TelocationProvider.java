package miui.telephony.phonenumber;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.miui.internal.R;
import com.miui.internal.util.DirectIndexedFileExtractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import miui.util.AppConstants;
import miui.util.DirectIndexedFile;

public class TelocationProvider extends ContentProvider {
    public static final int AREACODE_INDEX = 1;
    public static final String AUTHORITY = "com.miui.core.telocation";
    private static final int COMMON_OPERATOR_INDEX_LENGTH = 3;
    private static final String DATA_ASSET_NAME = "telocation.idf";
    public static final String EMPTY = "";
    private static final int IOT_NUMBER_LENGTH = 13;
    private static final int IOT_OPERATOR_INDEX_LENGTH = 5;
    public static final int LOCATION_INDEX = 0;
    public static final int LOCATION_KIND = 0;
    private static final int LONG_UNIQID_LENGTH = 8;
    private static final int NORMAL_UNIQID_LENGTH = 7;
    private static final String OPERATORS_DATA_ASSET_NAME = "operators.dat";
    private static final String TAG = "TelocationProvider";
    private static final String UNIQID_LEN8_NUMBER_ASSET_NAME = "tel_uniqid_len8.dat";
    public static final int UNIQUE_ID_NONE = 0;
    private static final int VIRTUAL_OPERATOR_INDEX_LENGTH = 4;
    private static final HashSet<CharSequence> VIRTUAL_OPERATOR_PREFIXES = new HashSet<>();
    private static final String XIAOMI_DATA_ASSET_NAME = "xiaomi_mobile.dat";
    private static final int XIAOMI_OPERATOR_INDEX_LENGTH = 7;
    private static UriMatcher sUriMatcher = new UriMatcher(-1);
    private final ConcurrentHashMap<String, String> mAreaCodeTelocations = new ConcurrentHashMap<>();
    private DirectIndexedFile.Reader mDensityIndexFileReader;
    private final HashMap<CharSequence, String> mOperatorsMap = new HashMap<>();
    private final HashSet<Integer> mUniqIdLen8Numbers = new HashSet<>();
    private final HashSet<CharSequence> mXiaomiMobileSet = new HashSet<>();

    public static class Contract {
        public static final String[] COLUMNS = {"_id", "location", "query_area_code", "parse_area_code", Column.OPERATOR, "version", Column.UNIQ_ID, "query_area_code_by_address"};
        public static final String PARSE_AREA_CODE = "parse_area_code";
        public static final String QUERY_AREA_CODE = "query_area_code";
        public static final String QUERY_AREA_CODE_BY_ADDRESS = "query_area_code_by_address";
        public static final String QUERY_LOCATION = "query_location";
        public static final String QUERY_OPERATOR = "query_operator";
        public static final String QUERY_UNIQ_ID = "query_uniq_id";
        public static final String QUERY_VERSION = "query_version";
        public static final String RELOAD = "reload";
        public static final int URI_PARSE_AREA_CODE = 4;
        public static final int URI_QUERY_AREA_CODE = 3;
        public static final int URI_QUERY_AREA_CODE_BY_ADDRESS = 8;
        public static final int URI_QUERY_LOCATION = 2;
        public static final int URI_QUERY_OPERATOR = 5;
        public static final int URI_QUERY_UNIQ_ID = 7;
        public static final int URI_QUERY_VERSION = 6;
        public static final int URI_RELOAD = 1;

        public static class Column {
            public static final int INDEX_ID = 0;
            public static final int INDEX_LOCATION = 1;
            public static final int INDEX_OPERATOR = 4;
            public static final int INDEX_PARSE_AREA_CODE = 3;
            public static final int INDEX_QUERY_AREA_CODE = 2;
            public static final int INDEX_QUERY_AREA_CODE_BY_ADDRESS = 7;
            public static final int INDEX_UNIQ_ID = 6;
            public static final int INDEX_VERSION = 5;
            public static final String LOCATION = "location";
            public static final String OPERATOR = "operator";
            public static final String PARSE_AREA_CODE = "parse_area_code";
            public static final String QUERY_AREA_CODE = "query_area_code";
            public static final String QUERY_AREA_CODE_BY_ADDRESS = "query_area_code_by_address";
            public static final String UNIQ_ID = "uniq_id";
            public static final String VERSION = "version";
            public static final String _ID = "_id";
        }

        public static class Params {
            public static final String ADMIN_AREA = "adminArea";
            public static final String ENABLE_MOBILE = "enable_mobile";
            public static final String LENGTH = "length";
            public static final String LOCALITY = "locality";
            public static final String NUMBER = "number";
            public static final String START = "start";
            public static final String WITH_AREA_CODE = "with_area_code";
        }
    }

    /* access modifiers changed from: private */
    public interface DatFileRawReadListener {
        void onReadRaw(String[] strArr);
    }

    static {
        VIRTUAL_OPERATOR_PREFIXES.add("170");
        VIRTUAL_OPERATOR_PREFIXES.add("171");
        VIRTUAL_OPERATOR_PREFIXES.add("167");
        VIRTUAL_OPERATOR_PREFIXES.add("162");
        UriMatcher uriMatcher = sUriMatcher;
        uriMatcher.addURI(AUTHORITY, Contract.RELOAD, 1);
        uriMatcher.addURI(AUTHORITY, Contract.QUERY_LOCATION, 2);
        uriMatcher.addURI(AUTHORITY, "query_area_code", 3);
        uriMatcher.addURI(AUTHORITY, "parse_area_code", 4);
        uriMatcher.addURI(AUTHORITY, Contract.QUERY_OPERATOR, 5);
        uriMatcher.addURI(AUTHORITY, Contract.QUERY_VERSION, 6);
        uriMatcher.addURI(AUTHORITY, Contract.QUERY_UNIQ_ID, 7);
        uriMatcher.addURI(AUTHORITY, "query_area_code_by_address", 8);
    }

    private String getAreaCode(CharSequence charSequence, int i, int i2) {
        if (this.mDensityIndexFileReader == null) {
            return "";
        }
        return (String) this.mDensityIndexFileReader.get(0, getUniqId(charSequence, i, i2, true), 1);
    }

    private String getAreaCode(String str, String str2) {
        if (this.mDensityIndexFileReader == null) {
            return "";
        }
        if (this.mAreaCodeTelocations.isEmpty()) {
            synchronized (this.mAreaCodeTelocations) {
                if (this.mAreaCodeTelocations.isEmpty()) {
                    for (int i = 0; i < 1000; i++) {
                        String str3 = (String) this.mDensityIndexFileReader.get(0, i, 0);
                        if (!TextUtils.isEmpty(str3)) {
                            this.mAreaCodeTelocations.put(String.valueOf(i), str3);
                        }
                    }
                }
            }
        }
        Log.d(TAG, "adminArea:" + str + " locality:" + str2);
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return "";
        }
        String replace = str.replace("省", "").replace("市", "");
        String replace2 = str2.replace("市", "").replace("区", "").replace("县", "");
        for (Map.Entry<String, String> entry : this.mAreaCodeTelocations.entrySet()) {
            String value = entry.getValue();
            if (value.startsWith(replace) && value.contains(replace2)) {
                return entry.getKey();
            }
        }
        return "";
    }

    private String getLocation(CharSequence charSequence, int i, int i2, boolean z) {
        int uniqId;
        return (this.mDensityIndexFileReader != null && z && (uniqId = getUniqId(charSequence, i, i2, true)) > 0) ? (String) this.mDensityIndexFileReader.get(0, uniqId, 0) : "";
    }

    private String getOperator(CharSequence charSequence, int i, int i2) {
        if (TextUtils.isEmpty(charSequence) || charSequence.length() < 3) {
            return "";
        }
        CharSequence subSequence = charSequence.subSequence(i, i2 + i);
        CharSequence subSequence2 = subSequence.subSequence(0, 3);
        if (VIRTUAL_OPERATOR_PREFIXES.contains(subSequence2)) {
            if (subSequence.length() < 7) {
                return "";
            }
            if (this.mXiaomiMobileSet.size() == 0) {
                synchronized (this.mXiaomiMobileSet) {
                    if (this.mXiaomiMobileSet.size() == 0) {
                        initxiaomiMobile();
                    }
                }
            }
            if (this.mXiaomiMobileSet.contains(subSequence.subSequence(0, 7))) {
                return AppConstants.getCurrentApplication().getResources().getString(R.string.xiaomi_mobile);
            }
            subSequence2 = subSequence.subSequence(0, 4);
        } else if (subSequence.length() >= 5 && subSequence.subSequence(0, 4).equals("1064")) {
            subSequence2 = subSequence.subSequence(0, 5);
        }
        if (this.mOperatorsMap.size() == 0) {
            synchronized (this.mOperatorsMap) {
                if (this.mOperatorsMap.size() == 0) {
                    initOperators();
                }
            }
        }
        if (this.mOperatorsMap.containsKey(subSequence2)) {
            return this.mOperatorsMap.get(subSequence2);
        }
        for (int i3 = 4; i3 >= 3; i3--) {
            CharSequence subSequence3 = subSequence.subSequence(0, i3);
            if (this.mOperatorsMap.containsKey(subSequence3)) {
                return this.mOperatorsMap.get(subSequence3);
            }
        }
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0093 A[SYNTHETIC, Splitter:B:28:0x0093] */
    /* JADX WARNING: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    private void initDatFile(String str, DatFileRawReadListener datFileRawReadListener) {
        Throwable th;
        Exception e2;
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(AppConstants.getCurrentApplication().getResources().getAssets().open(str), "UTF-8"));
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        try {
                            bufferedReader2.close();
                            return;
                        } catch (Exception e3) {
                            Log.e(TAG, "Failed to close reader", e3);
                            return;
                        }
                    } else if (!readLine.trim().startsWith("#")) {
                        if (readLine.trim().startsWith("version")) {
                            Log.i(TAG, String.format("%s version: %s", str, readLine.split(":")[1]));
                        } else {
                            datFileRawReadListener.onReadRaw(readLine.split(" "));
                        }
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    bufferedReader = bufferedReader2;
                    try {
                        Log.e(TAG, "Failed to get " + str, e2);
                        if (bufferedReader == null) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedReader2 = bufferedReader;
                        if (bufferedReader2 != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                        } catch (Exception e5) {
                            Log.e(TAG, "Failed to close reader", e5);
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e6) {
            e2 = e6;
            Log.e(TAG, "Failed to get " + str, e2);
            if (bufferedReader == null) {
                bufferedReader.close();
            }
        }
    }

    private void initOperators() {
        initDatFile(OPERATORS_DATA_ASSET_NAME, new DatFileRawReadListener() {
            /* class miui.telephony.phonenumber.TelocationProvider.AnonymousClass1 */

            @Override // miui.telephony.phonenumber.TelocationProvider.DatFileRawReadListener
            public void onReadRaw(String[] strArr) {
                TelocationProvider.this.mOperatorsMap.put(strArr[0], strArr[1]);
            }
        });
    }

    private void initReader() {
        String directIndexedFilePath = DirectIndexedFileExtractor.getDirectIndexedFilePath(AppConstants.getCurrentApplication(), DATA_ASSET_NAME);
        if (directIndexedFilePath != null && new File(directIndexedFilePath).exists()) {
            try {
                this.mDensityIndexFileReader = DirectIndexedFile.open(directIndexedFilePath);
                Log.v(TAG, "Read Telocation from " + directIndexedFilePath);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        if (this.mDensityIndexFileReader == null) {
            try {
                this.mDensityIndexFileReader = DirectIndexedFile.open(AppConstants.getCurrentApplication().getResources().getAssets().open(DATA_ASSET_NAME, 1));
                Log.v(TAG, "Read Telocation from assets");
            } catch (IOException unused) {
                Log.w(TAG, "Can't read telocation.idf, NO mobile telocation supported!");
            }
        }
        DirectIndexedFile.Reader reader = this.mDensityIndexFileReader;
        if (reader != null) {
            int dataVersion = reader.getDataVersion();
            Log.i(TAG, "Telocation version :" + dataVersion);
        }
    }

    private void initUniqIdLen8Numbers() {
        initDatFile(UNIQID_LEN8_NUMBER_ASSET_NAME, new DatFileRawReadListener() {
            /* class miui.telephony.phonenumber.TelocationProvider.AnonymousClass3 */

            @Override // miui.telephony.phonenumber.TelocationProvider.DatFileRawReadListener
            public void onReadRaw(String[] strArr) {
                int i = 0;
                try {
                    i = Integer.parseInt(strArr[0]);
                } catch (NumberFormatException unused) {
                }
                if (i != 0) {
                    TelocationProvider.this.mUniqIdLen8Numbers.add(Integer.valueOf(i));
                }
            }
        });
    }

    private void initxiaomiMobile() {
        initDatFile(XIAOMI_DATA_ASSET_NAME, new DatFileRawReadListener() {
            /* class miui.telephony.phonenumber.TelocationProvider.AnonymousClass2 */

            @Override // miui.telephony.phonenumber.TelocationProvider.DatFileRawReadListener
            public void onReadRaw(String[] strArr) {
                TelocationProvider.this.mXiaomiMobileSet.add(strArr[0]);
            }
        });
    }

    private String parseAreaCode(CharSequence charSequence, int i, int i2) {
        if (this.mDensityIndexFileReader == null) {
            return "";
        }
        return (String) this.mDensityIndexFileReader.get(0, getUniqId(charSequence, i, i2, false), 1);
    }

    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // java.lang.Object
    public void finalize() throws Throwable {
        DirectIndexedFile.Reader reader = this.mDensityIndexFileReader;
        if (reader != null) {
            reader.close();
        }
        super.finalize();
    }

    public String getType(Uri uri) {
        return null;
    }

    public int getUniqId(CharSequence charSequence, int i, int i2, boolean z) {
        int charAt;
        char charAt2;
        if (i2 > 0 && charSequence.charAt(i) == '0') {
            i++;
            i2--;
        }
        if (i2 <= 1) {
            return 0;
        }
        if (this.mUniqIdLen8Numbers.size() == 0) {
            synchronized (this.mUniqIdLen8Numbers) {
                if (this.mUniqIdLen8Numbers.size() == 0) {
                    initUniqIdLen8Numbers();
                }
            }
        }
        switch (charSequence.charAt(i)) {
            case '0':
                break;
            case '1':
                int i3 = i + 1;
                if (charSequence.charAt(i3) == '0' && (!z || i2 < 13 || !charSequence.subSequence(i, i + i2).toString().startsWith("1064"))) {
                    return 10;
                }
                if (z && i2 >= 7) {
                    int charAt3 = ((charSequence.charAt(i3) - '0') * 100000) + 1000000 + ((charSequence.charAt(i + 2) - '0') * 10000) + ((charSequence.charAt(i + 3) - '0') * 1000) + ((charSequence.charAt(i + 4) - '0') * 100) + ((charSequence.charAt(i + 5) - '0') * 10) + (charSequence.charAt(i + 6) - '0');
                    if (charAt3 == 1380013 && i2 > 10 && charSequence.charAt(i + 7) == '8' && charSequence.charAt(i + 8) == '0' && charSequence.charAt(i + 9) == '0' && charSequence.charAt(i + 10) == '0') {
                        return 0;
                    }
                    if (i2 >= 8 && this.mUniqIdLen8Numbers.contains(Integer.valueOf(charAt3))) {
                        charAt = charAt3 * 10;
                        charAt2 = charSequence.charAt(i + 7);
                    } else if (i2 < 13) {
                        return charAt3;
                    } else {
                        if ((charAt3 < 1410000 || charAt3 > 1419999) && (charAt3 < 1064000 || charAt3 > 1064999)) {
                            return charAt3;
                        }
                        charAt = (charAt3 * 100) + ((charSequence.charAt(i + 7) - '0') * 10);
                        charAt2 = charSequence.charAt(i + 8);
                    }
                    return charAt + (charAt2 - '0');
                }
                break;
            case '2':
                return (charSequence.charAt(i + 1) - '0') + 20;
            default:
                if (i2 > 2) {
                    return ((((charSequence.charAt(i) - '0') * 10) + (charSequence.charAt(i + 1) - '0')) * 10) + (charSequence.charAt(i + 2) - '0');
                }
                break;
        }
        return 0;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        initReader();
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        MatrixCursor matrixCursor = new MatrixCursor(Contract.COLUMNS);
        Object[] objArr = new Object[Contract.COLUMNS.length];
        objArr[0] = 1;
        switch (sUriMatcher.match(uri)) {
            case 1:
                initReader();
                break;
            case 2:
                objArr[1] = getLocation(uri.getQueryParameter(Contract.Params.NUMBER), Integer.valueOf(uri.getQueryParameter("start")).intValue(), Integer.valueOf(uri.getQueryParameter(Contract.Params.LENGTH)).intValue(), Boolean.valueOf(uri.getQueryParameter(Contract.Params.WITH_AREA_CODE)).booleanValue());
                break;
            case 3:
                objArr[2] = getAreaCode(uri.getQueryParameter(Contract.Params.NUMBER), Integer.valueOf(uri.getQueryParameter("start")).intValue(), Integer.valueOf(uri.getQueryParameter(Contract.Params.LENGTH)).intValue());
                break;
            case 4:
                objArr[3] = parseAreaCode(uri.getQueryParameter(Contract.Params.NUMBER), Integer.valueOf(uri.getQueryParameter("start")).intValue(), Integer.valueOf(uri.getQueryParameter(Contract.Params.LENGTH)).intValue());
                break;
            case 5:
                objArr[4] = getOperator(uri.getQueryParameter(Contract.Params.NUMBER), Integer.valueOf(uri.getQueryParameter("start")).intValue(), Integer.valueOf(uri.getQueryParameter(Contract.Params.LENGTH)).intValue());
                break;
            case 6:
                if (this.mDensityIndexFileReader == null) {
                    initReader();
                }
                objArr[5] = Integer.valueOf(this.mDensityIndexFileReader.getDataVersion());
                break;
            case 7:
                objArr[6] = Integer.valueOf(getUniqId(uri.getQueryParameter(Contract.Params.NUMBER), Integer.valueOf(uri.getQueryParameter("start")).intValue(), Integer.valueOf(uri.getQueryParameter(Contract.Params.LENGTH)).intValue(), uri.getBooleanQueryParameter(Contract.Params.ENABLE_MOBILE, true)));
                break;
            case 8:
                objArr[7] = getAreaCode(uri.getQueryParameter(Contract.Params.ADMIN_AREA), uri.getQueryParameter(Contract.Params.LOCALITY));
                break;
        }
        matrixCursor.addRow(objArr);
        return matrixCursor;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
