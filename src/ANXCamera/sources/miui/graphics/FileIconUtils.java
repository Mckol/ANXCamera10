package miui.graphics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.camera.statistic.MistatsConstants;
import com.ss.android.vesdk.VEEditor;
import java.util.HashMap;
import miui.R;

public class FileIconUtils {
    private static final String LOG_TAG = "FileIconHelper";
    private static final String TYPE_APK = "apk";
    private static HashMap<String, Integer> sFileExtToIcons = new HashMap<>();

    static {
        addItem(new String[]{"mp3"}, R.drawable.file_icon_mp3);
        addItem(new String[]{"wma"}, R.drawable.file_icon_wma);
        addItem(new String[]{"wav"}, R.drawable.file_icon_wav);
        addItem(new String[]{"mid"}, R.drawable.file_icon_mid);
        addItem(new String[]{"mp4", "wmv", "mpeg", "m4v", "3gp", "3g2", "3gpp2", "asf", "flv", "mkv", "vob", "ts", "f4v", "rm", "mov", "rmvb"}, R.drawable.file_icon_video);
        addItem(new String[]{"jpg", "jpeg", "gif", "png", "bmp", "wbmp"}, R.drawable.file_icon_picture);
        addItem(new String[]{"txt", MistatsConstants.Manual.LOG, "ini", "lrc"}, R.drawable.file_icon_txt);
        addItem(new String[]{"doc", "docx"}, R.drawable.file_icon_doc);
        addItem(new String[]{"ppt", "pptx"}, R.drawable.file_icon_ppt);
        addItem(new String[]{"xls", "xlsx"}, R.drawable.file_icon_xls);
        addItem(new String[]{"wps"}, R.drawable.file_icon_wps);
        addItem(new String[]{"pps"}, R.drawable.file_icon_pps);
        addItem(new String[]{"et"}, R.drawable.file_icon_et);
        addItem(new String[]{"wpt"}, R.drawable.file_icon_wpt);
        addItem(new String[]{"ett"}, R.drawable.file_icon_ett);
        addItem(new String[]{"dps"}, R.drawable.file_icon_dps);
        addItem(new String[]{"dpt"}, R.drawable.file_icon_dpt);
        addItem(new String[]{"pdf"}, R.drawable.file_icon_pdf);
        addItem(new String[]{"zip"}, R.drawable.file_icon_zip);
        addItem(new String[]{"mtz"}, R.drawable.file_icon_theme);
        addItem(new String[]{"rar"}, R.drawable.file_icon_rar);
        addItem(new String[]{TYPE_APK}, R.drawable.file_icon_apk);
        addItem(new String[]{"amr"}, R.drawable.file_icon_amr);
        addItem(new String[]{"vcf"}, R.drawable.file_icon_vcf);
        addItem(new String[]{"flac"}, R.drawable.file_icon_flac);
        addItem(new String[]{"aac"}, R.drawable.file_icon_aac);
        addItem(new String[]{"ape"}, R.drawable.file_icon_ape);
        addItem(new String[]{"m4a"}, R.drawable.file_icon_m4a);
        addItem(new String[]{"ogg"}, R.drawable.file_icon_ogg);
        addItem(new String[]{VEEditor.MVConsts.TYPE_AUDIO}, R.drawable.file_icon_audio);
        addItem(new String[]{"html"}, R.drawable.file_icon_html);
        addItem(new String[]{"xml"}, R.drawable.file_icon_xml);
        addItem(new String[]{"3gpp"}, R.drawable.file_icon_3gpp);
    }

    protected FileIconUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    private static void addItem(String[] strArr, int i) {
        if (strArr != null) {
            for (String str : strArr) {
                sFileExtToIcons.put(str.toLowerCase(), Integer.valueOf(i));
            }
        }
    }

    private static Drawable getApkIcon(Context context, String str) {
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 1);
        if (!(packageArchiveInfo == null || (applicationInfo = packageArchiveInfo.applicationInfo) == null)) {
            try {
                applicationInfo.publicSourceDir = str;
                return packageManager.getApplicationIcon(applicationInfo);
            } catch (OutOfMemoryError e2) {
                Log.e(LOG_TAG, e2.toString());
            }
        }
        return context.getResources().getDrawable(R.drawable.file_icon_default);
    }

    private static String getExtFromFilename(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf != -1 ? str.substring(lastIndexOf + 1, str.length()) : "";
    }

    public static Drawable getFileIcon(Context context, String str) {
        String extFromFilename = getExtFromFilename(str);
        return extFromFilename.equals(TYPE_APK) ? getApkIcon(context, str) : context.getResources().getDrawable(getFileIconId(extFromFilename));
    }

    public static int getFileIconId(String str) {
        Integer num = sFileExtToIcons.get(str.toLowerCase());
        return num == null ? R.drawable.file_icon_default : num.intValue();
    }
}
