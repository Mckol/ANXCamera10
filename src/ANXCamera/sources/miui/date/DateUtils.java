package miui.date;

import android.app.Application;
import android.content.res.Resources;
import android.text.format.DateFormat;
import com.miui.internal.R;
import java.util.TimeZone;
import miui.util.AppConstants;
import miui.util.Pools;

public class DateUtils {
    private static final Pools.Pool<Calendar> CALENDAR_POOL = Pools.createSoftReferencePool(new Pools.Manager<Calendar>() {
        /* class miui.date.DateUtils.AnonymousClass1 */

        @Override // miui.util.Pools.Manager
        public Calendar createInstance() {
            return new Calendar();
        }
    }, 1);
    public static final int FORMAT_12HOUR = 16;
    public static final int FORMAT_24HOUR = 32;
    public static final int FORMAT_ABBREV_ALL = 28672;
    public static final int FORMAT_ABBREV_MONTH = 4096;
    public static final int FORMAT_ABBREV_TIME = 16384;
    public static final int FORMAT_ABBREV_WEEKDAY = 8192;
    public static final int FORMAT_NO_AM_PM = 64;
    public static final int FORMAT_NUMERIC_DATE = 32768;
    public static final int FORMAT_SHOW_BRIEF_TIME = 12;
    public static final int FORMAT_SHOW_DATE = 896;
    public static final int FORMAT_SHOW_HOUR = 8;
    public static final int FORMAT_SHOW_MILLISECOND = 1;
    public static final int FORMAT_SHOW_MINUTE = 4;
    public static final int FORMAT_SHOW_MONTH = 256;
    public static final int FORMAT_SHOW_MONTH_DAY = 128;
    public static final int FORMAT_SHOW_SECOND = 2;
    public static final int FORMAT_SHOW_TIME = 15;
    public static final int FORMAT_SHOW_TIME_ZONE = 2048;
    public static final int FORMAT_SHOW_WEEKDAY = 1024;
    public static final int FORMAT_SHOW_YEAR = 512;

    protected DateUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class");
    }

    public static String formatDateTime(long j, int i) {
        StringBuilder acquire = Pools.getStringBuilderPool().acquire();
        formatDateTime(acquire, j, i, null);
        String sb = acquire.toString();
        Pools.getStringBuilderPool().release(acquire);
        return sb;
    }

    public static String formatDateTime(long j, int i, TimeZone timeZone) {
        StringBuilder acquire = Pools.getStringBuilderPool().acquire();
        formatDateTime(acquire, j, i, timeZone);
        String sb = acquire.toString();
        Pools.getStringBuilderPool().release(acquire);
        return sb;
    }

    public static StringBuilder formatDateTime(StringBuilder sb, long j, int i) {
        formatDateTime(sb, j, i, null);
        return sb;
    }

    public static StringBuilder formatDateTime(StringBuilder sb, long j, int i, TimeZone timeZone) {
        Application currentApplication = AppConstants.getCurrentApplication();
        if ((i & 16) == 0 && (i & 32) == 0) {
            i |= DateFormat.is24HourFormat(currentApplication) ? 32 : 16;
        }
        String string = currentApplication.getString(getFormatResId(i));
        StringBuilder acquire = Pools.getStringBuilderPool().acquire();
        Calendar acquire2 = CALENDAR_POOL.acquire();
        acquire2.setTimeZone(timeZone);
        acquire2.setTimeInMillis(j);
        int length = string.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = string.charAt(i2);
            if (charAt == 'D') {
                acquire.append(currentApplication.getString(getDatePatternResId(i)));
            } else if (charAt == 'T') {
                acquire.append(currentApplication.getString(getTimePatternResId(acquire2, i)));
            } else if (charAt != 'W') {
                acquire.append(charAt);
            } else {
                acquire.append(currentApplication.getString(getWeekdayPatternResId(i)));
            }
        }
        acquire2.format(sb, acquire);
        Pools.getStringBuilderPool().release(acquire);
        CALENDAR_POOL.release(acquire2);
        return sb;
    }

    public static String formatRelativeTime(long j, boolean z) {
        StringBuilder acquire = Pools.getStringBuilderPool().acquire();
        formatRelativeTime(acquire, j, z, null);
        String sb = acquire.toString();
        Pools.getStringBuilderPool().release(acquire);
        return sb;
    }

    public static String formatRelativeTime(long j, boolean z, TimeZone timeZone) {
        StringBuilder acquire = Pools.getStringBuilderPool().acquire();
        formatRelativeTime(acquire, j, z, timeZone);
        String sb = acquire.toString();
        Pools.getStringBuilderPool().release(acquire);
        return sb;
    }

    public static StringBuilder formatRelativeTime(StringBuilder sb, long j, boolean z) {
        formatRelativeTime(sb, j, z, null);
        return sb;
    }

    public static StringBuilder formatRelativeTime(StringBuilder sb, long j, boolean z, TimeZone timeZone) {
        long currentTimeMillis = System.currentTimeMillis();
        boolean z2 = currentTimeMillis >= j;
        long abs = Math.abs(currentTimeMillis - j) / 60000;
        Resources resources = AppConstants.getCurrentApplication().getResources();
        int i = (abs > 60 ? 1 : (abs == 60 ? 0 : -1));
        if (i > 0 || z) {
            Calendar acquire = CALENDAR_POOL.acquire();
            acquire.setTimeZone(timeZone);
            acquire.setTimeInMillis(currentTimeMillis);
            int i2 = acquire.get(1);
            int i3 = acquire.get(12);
            int i4 = acquire.get(14);
            acquire.setTimeInMillis(j);
            boolean z3 = i2 == acquire.get(1);
            if (z3 && i3 == acquire.get(12)) {
                formatDateTime(sb, j, 12300, timeZone);
            } else if (!z3 || Math.abs(i3 - acquire.get(12)) >= 2) {
                if (z3 && Math.abs(i3 - acquire.get(12)) < 7) {
                    if (z2 == (i4 > acquire.get(14))) {
                        formatDateTime(sb, j, 13324, timeZone);
                    }
                }
                if (z3) {
                    formatDateTime(sb, j, (z ? 396 : 384) | 12288, timeZone);
                } else {
                    formatDateTime(sb, j, (z ? 908 : FORMAT_SHOW_DATE) | 12288, timeZone);
                }
            } else {
                sb.append(resources.getString(z2 ? R.string.yesterday : R.string.tomorrow));
                sb.append(' ');
                formatDateTime(sb, j, 12300, timeZone);
            }
            CALENDAR_POOL.release(acquire);
        } else {
            sb.append(String.format(resources.getQuantityString(z2 ? i == 0 ? R.plurals.abbrev_a_hour_ago : abs == 30 ? R.plurals.abbrev_half_hour_ago : abs == 0 ? R.plurals.abbrev_less_than_one_minute_ago : R.plurals.abbrev_num_minutes_ago : i == 0 ? R.plurals.abbrev_in_a_hour : abs == 30 ? R.plurals.abbrev_in_half_hour : abs == 0 ? R.plurals.abbrev_in_less_than_one_minute : R.plurals.abbrev_in_num_minutes, (int) abs), Long.valueOf(abs)));
        }
        return sb;
    }

    private static int getDatePatternResId(int i) {
        if ((i & 32768) == 32768) {
            if ((i & 512) == 512) {
                return (i & 256) == 256 ? (i & 128) == 128 ? R.string.fmt_date_numeric_year_month_day : R.string.fmt_date_numeric_year_month : R.string.fmt_date_numeric_year;
            }
            if ((i & 256) == 256) {
                return (i & 128) == 128 ? R.string.fmt_date_numeric_month_day : R.string.fmt_date_numeric_month;
            }
            if ((i & 128) == 128) {
                return R.string.fmt_date_numeric_day;
            }
            throw new IllegalArgumentException("no any time date");
        } else if ((i & 4096) == 4096) {
            if ((i & 512) == 512) {
                return (i & 256) == 256 ? (i & 128) == 128 ? R.string.fmt_date_short_year_month_day : R.string.fmt_date_short_year_month : R.string.fmt_date_year;
            }
            if ((i & 256) == 256) {
                return (i & 128) == 128 ? R.string.fmt_date_short_month_day : R.string.fmt_date_short_month;
            }
            if ((i & 128) == 128) {
                return R.string.fmt_date_day;
            }
            throw new IllegalArgumentException("no any time date");
        } else if ((i & 512) == 512) {
            return (i & 256) == 256 ? (i & 128) == 128 ? R.string.fmt_date_long_year_month_day : R.string.fmt_date_long_year_month : R.string.fmt_date_year;
        } else {
            if ((i & 256) == 256) {
                return (i & 128) == 128 ? R.string.fmt_date_long_month_day : R.string.fmt_date_long_month;
            }
            if ((i & 128) == 128) {
                return R.string.fmt_date_day;
            }
            throw new IllegalArgumentException("no any time date");
        }
    }

    private static int getFormatResId(int i) {
        return (i & 1024) == 1024 ? (i & FORMAT_SHOW_DATE) != 0 ? (i & 15) != 0 ? (i & 2048) == 2048 ? R.string.fmt_weekday_date_time_timezone : R.string.fmt_weekday_date_time : (i & 2048) == 2048 ? R.string.fmt_weekday_date_timezone : R.string.fmt_weekday_date : (i & 15) != 0 ? (i & 2048) == 2048 ? R.string.fmt_weekday_time_timezone : R.string.fmt_weekday_time : (i & 2048) == 2048 ? R.string.fmt_weekday_timezone : R.string.fmt_weekday : (i & FORMAT_SHOW_DATE) != 0 ? (i & 15) != 0 ? (i & 2048) == 2048 ? R.string.fmt_date_time_timezone : R.string.fmt_date_time : (i & 2048) == 2048 ? R.string.fmt_date_timezone : R.string.fmt_date : (i & 15) != 0 ? (i & 2048) == 2048 ? R.string.fmt_time_timezone : R.string.fmt_time : (i & 2048) == 2048 ? R.string.fmt_timezone : R.string.empty;
    }

    private static int getTimePatternResId(Calendar calendar, int i) {
        if ((i & 16384) == 16384 && (((i & 1) != 1 || calendar.get(22) == 0) && (i & 14) != 0)) {
            i &= -2;
            if (((i & 2) != 2 || calendar.get(21) == 0) && (i & 12) != 0) {
                i &= -3;
                if (calendar.get(20) == 0 && (i & 8) != 0) {
                    i &= -5;
                }
            }
        }
        if ((i & 8) == 8) {
            return (i & 16) == 16 ? (i & 64) == 64 ? (i & 4) == 4 ? (i & 2) == 2 ? (i & 1) == 1 ? R.string.fmt_time_12hour_minute_second_millis : R.string.fmt_time_12hour_minute_second : R.string.fmt_time_12hour_minute : R.string.fmt_time_12hour : (i & 4) == 4 ? (i & 2) == 2 ? (i & 1) == 1 ? R.string.fmt_time_12hour_minute_second_millis_pm : R.string.fmt_time_12hour_minute_second_pm : R.string.fmt_time_12hour_minute_pm : R.string.fmt_time_12hour_pm : (i & 4) == 4 ? (i & 2) == 2 ? (i & 1) == 1 ? R.string.fmt_time_24hour_minute_second_millis : R.string.fmt_time_24hour_minute_second : R.string.fmt_time_24hour_minute : R.string.fmt_time_24hour;
        }
        if ((i & 4) == 4) {
            return (i & 2) == 2 ? (i & 1) == 1 ? R.string.fmt_time_minute_second_millis : R.string.fmt_time_minute_second : R.string.fmt_time_minute;
        }
        if ((i & 2) == 2) {
            return (i & 1) == 1 ? R.string.fmt_time_second_millis : R.string.fmt_time_second;
        }
        if ((i & 1) == 1) {
            return R.string.fmt_time_millis;
        }
        throw new IllegalArgumentException("no any time date");
    }

    private static int getWeekdayPatternResId(int i) {
        return (i & 8192) == 8192 ? R.string.fmt_weekday_short : R.string.fmt_weekday_long;
    }
}
