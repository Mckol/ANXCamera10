package miui.date;

import android.content.res.Resources;
import android.util.SparseIntArray;
import com.miui.internal.R;
import com.miui.internal.util.DeviceHelper;
import java.util.HashMap;
import java.util.Locale;
import miui.os.SystemProperties;
import miui.util.AppConstants;
import miui.util.Pools;
import miui.util.SoftReferenceSingleton;

public class Holidays {
    private static final Pools.Pool<Calendar> CALENDAR_POOL = Pools.createSoftReferencePool(new Pools.Manager<Calendar>() {
        /* class miui.date.Holidays.AnonymousClass1 */

        @Override // miui.util.Pools.Manager
        public Calendar createInstance() {
            return new Calendar();
        }
    }, 1);
    private static final int[][] CHINESE_CALENDAR_HOLIDAYS = {new int[]{-1, R.string.the_eve_of_the_spring_festival}, new int[]{101, R.string.the_spring_festival}, new int[]{102, R.string.the_second_day}, new int[]{103, R.string.the_third_day}, new int[]{104, R.string.the_forth_day}, new int[]{105, R.string.the_fifth_day}, new int[]{106, R.string.the_sixth_day}, new int[]{107, R.string.the_seventh_day}, new int[]{115, R.string.the_lantern_festival}, new int[]{505, R.string.the_dragon_boat_festival}, new int[]{707, R.string.the_night_of_sevens}, new int[]{715, R.string.the_spirit_festival}, new int[]{815, R.string.the_mid_autumn_festival}, new int[]{909, R.string.the_double_ninth_festival}, new int[]{1015, R.string.the_water_lantern_festival}, new int[]{1208, R.string.the_laba_festival}};
    private static final int EASTER_DAYS = -1;
    private static final SparseIntArray EASTER_DAY_CACHE = new SparseIntArray();
    private static final int EVE_OF_THE_SPRING_FESTIVAL = -1;
    private static final HashMap<String, int[][]> HOLIDAYS = new HashMap<>();
    private static final SoftReferenceSingleton<Holidays> INSTANCE = new HolidaysSingleton();
    private String mCountry;
    private boolean mIsChinese;
    private Resources mResources;

    private static class HolidaysSingleton extends SoftReferenceSingleton<Holidays> {
        static {
            SystemProperties.addChangeCallback(new Runnable() {
                /* class miui.date.Holidays.HolidaysSingleton.AnonymousClass1 */

                public void run() {
                    ((Holidays) Holidays.INSTANCE.get()).setCountry(DeviceHelper.getRegion());
                }
            });
        }

        private HolidaysSingleton() {
        }

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public Holidays createInstance() {
            return new Holidays();
        }
    }

    static {
        HOLIDAYS.put("", new int[][]{new int[]{101, R.string.the_new_years_day}, new int[]{214, R.string.the_valentines_day}, new int[]{308, R.string.the_international_womens_day}, new int[]{312, R.string.the_arbor_day}, new int[]{401, R.string.the_fools_day}, new int[]{501, R.string.the_labour_day}, new int[]{1225, R.string.the_christmas_day}});
        HOLIDAYS.put("CN", new int[][]{new int[]{101, R.string.the_new_years_day}, new int[]{214, R.string.the_valentines_day}, new int[]{308, R.string.the_international_womens_day}, new int[]{312, R.string.the_arbor_day}, new int[]{401, R.string.the_fools_day}, new int[]{501, R.string.the_labour_day}, new int[]{504, R.string.the_chinese_youth_day}, new int[]{601, R.string.the_childrens_day}, new int[]{701, R.string.the_partys_day}, new int[]{801, R.string.the_armys_day}, new int[]{910, R.string.the_teachers_day}, new int[]{1001, R.string.the_national_day}, new int[]{1225, R.string.the_christmas_day}});
        HOLIDAYS.put("TW", new int[][]{new int[]{101, R.string.the_new_years_day}, new int[]{214, R.string.the_valentines_day}, new int[]{228, R.string.the_peace_day}, new int[]{308, R.string.the_international_womens_day}, new int[]{312, R.string.the_arbor_day}, new int[]{314, R.string.the_anti_aggression_day}, new int[]{329, R.string.the_tw_youth_day}, new int[]{401, R.string.the_fools_day}, new int[]{404, R.string.the_tw_childrens_day}, new int[]{501, R.string.the_labour_day}, new int[]{715, R.string.the_anniversary_of_lifting_martial_law}, new int[]{903, R.string.the_armed_forces_day}, new int[]{928, R.string.the_teachers_day}, new int[]{1010, R.string.the_national_day}, new int[]{1024, R.string.the_united_nations_day}, new int[]{1025, R.string.the_retrocession_day}, new int[]{1112, R.string.the_national_father_day}, new int[]{1225, R.string.the_christmas_day}});
        HOLIDAYS.put("HK", new int[][]{new int[]{-1, R.string.the_easter_day}, new int[]{101, R.string.the_new_years_day}, new int[]{214, R.string.the_valentines_day}, new int[]{501, R.string.the_labour_day}, new int[]{701, R.string.the_hksar_establishment_day}, new int[]{1001, R.string.the_national_day}, new int[]{1225, R.string.the_christmas_day}});
    }

    private Holidays() {
        this(DeviceHelper.getRegion());
    }

    private Holidays(String str) {
        this.mResources = AppConstants.getCurrentApplication().getResources();
        setCountry(str);
    }

    protected Holidays(Locale locale) {
        this(locale.getCountry());
    }

    public static Holidays getDefault() {
        return INSTANCE.get();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setCountry(String str) {
        this.mCountry = str.toUpperCase();
        this.mIsChinese = "CN".equals(this.mCountry) || "HK".equals(this.mCountry) || "TW".equals(this.mCountry);
    }

    /* access modifiers changed from: protected */
    public final int getEasterDayOfYear(int i) {
        int i2;
        int i3 = EASTER_DAY_CACHE.get(i, 0);
        if (i3 != 0) {
            return i3;
        }
        Calendar calendar = CALENDAR_POOL.acquire().set(i, 2, Calendar.solarTermDaysOfMonth(i, 2) & 255, 0, 0, 0, 0);
        if (calendar.get(10) < 15) {
            i2 = 15 - calendar.get(10);
        } else {
            i2 = 15 + ((calendar.isChineseLeapMonth() ? Calendar.leapDaysInChineseYear(i) : Calendar.daysInChineseMonth(i, calendar.get(6))) - calendar.get(10));
        }
        calendar.add(9, i2);
        switch (calendar.get(14)) {
            case 1:
                calendar.add(9, 7);
                break;
            case 2:
                calendar.add(9, 6);
                break;
            case 3:
                calendar.add(9, 5);
                break;
            case 4:
                calendar.add(9, 4);
                break;
            case 5:
                calendar.add(9, 3);
                break;
            case 6:
                calendar.add(9, 2);
                break;
            case 7:
                calendar.add(9, 1);
                break;
        }
        int i4 = ((calendar.get(5) + 1) * 100) + calendar.get(9);
        CALENDAR_POOL.release(calendar);
        EASTER_DAY_CACHE.put(i, i4);
        return i4;
    }

    public String getHolidayName(Calendar calendar) {
        int[][] iArr = HOLIDAYS.get(this.mCountry);
        if (iArr == null) {
            iArr = HOLIDAYS.get("");
        }
        int i = ((calendar.get(5) + 1) * 100) + calendar.get(9);
        for (int[] iArr2 : iArr) {
            if (iArr2[0] != -1) {
                if (iArr2[0] == i) {
                    return this.mResources.getString(iArr2[1]);
                }
            } else if (i == getEasterDayOfYear(calendar.get(1))) {
                return this.mResources.getString(iArr2[1]);
            }
        }
        if (this.mIsChinese && !calendar.outOfChineseCalendarRange()) {
            int i2 = ((calendar.get(6) + 1) * 100) + calendar.get(10);
            int[][] iArr3 = CHINESE_CALENDAR_HOLIDAYS;
            for (int[] iArr4 : iArr3) {
                if (iArr4[0] != -1) {
                    if (!calendar.isChineseLeapMonth() && i2 == iArr4[0]) {
                        return this.mResources.getString(iArr4[1]);
                    }
                } else if (Calendar.daysInChineseYear(calendar.get(2)) == calendar.get(13)) {
                    return this.mResources.getString(iArr4[1]);
                }
            }
        }
        return "";
    }
}
