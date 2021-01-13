package miui.text;

import java.lang.Character;
import java.util.ArrayList;
import java.util.Iterator;
import miui.text.ChinesePinyinConverter;
import miui.util.SoftReferenceSingleton;

public class SortKeyGenerator {
    private static final SoftReferenceSingleton<SortKeyGenerator> INSTANCE = new SoftReferenceSingleton<SortKeyGenerator>() {
        /* class miui.text.SortKeyGenerator.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public SortKeyGenerator createInstance() {
            return new SortKeyGenerator();
        }
    };
    private BaseGenerator[] mUtils;

    /* access modifiers changed from: private */
    public class BaseGenerator {
        private BaseGenerator() {
        }

        public String getSortKey(String str) {
            return str;
        }
    }

    /* access modifiers changed from: private */
    public class ChineseLocaleUtils extends BaseGenerator {
        private ChineseLocaleUtils() {
            super();
        }

        @Override // miui.text.SortKeyGenerator.BaseGenerator
        public String getSortKey(String str) {
            ArrayList<ChinesePinyinConverter.Token> arrayList = ChinesePinyinConverter.getInstance().get(str);
            if (arrayList == null || arrayList.size() <= 0) {
                super.getSortKey(str);
                return str;
            }
            StringBuilder sb = new StringBuilder();
            Iterator<ChinesePinyinConverter.Token> it = arrayList.iterator();
            while (it.hasNext()) {
                ChinesePinyinConverter.Token next = it.next();
                if (2 == next.type) {
                    if (sb.length() > 0) {
                        sb.append(' ');
                    }
                    sb.append(next.target.charAt(0));
                    sb.append("   ");
                    if (next.target.length() > 1) {
                        sb.append(next.target.substring(1));
                    }
                    sb.append(' ');
                    sb.append(next.source);
                } else {
                    if (sb.length() > 0) {
                        sb.append(' ');
                    }
                    sb.append(next.source);
                }
            }
            return sb.toString();
        }
    }

    /* access modifiers changed from: private */
    public enum NameStyle {
        UNDEFINED,
        WESTERN,
        CJK,
        CHINESE,
        JAPANESE,
        KOREAN;

        private static NameStyle guessCJKNameStyle(String str, int i) {
            int length = str.length();
            while (i < length) {
                int codePointAt = Character.codePointAt(str, i);
                if (Character.isLetter(codePointAt)) {
                    Character.UnicodeBlock of = Character.UnicodeBlock.of(codePointAt);
                    if (isJapanesePhoneticUnicodeBlock(of)) {
                        return JAPANESE;
                    }
                    if (isKoreanUnicodeBlock(of)) {
                        return KOREAN;
                    }
                    if (isChineseUnicode(Character.toChars(codePointAt))) {
                        return CHINESE;
                    }
                }
                i += Character.charCount(codePointAt);
            }
            return CJK;
        }

        public static NameStyle guessFullNameStyle(String str) {
            if (str == null) {
                return UNDEFINED;
            }
            NameStyle nameStyle = UNDEFINED;
            int length = str.length();
            int i = 0;
            while (i < length) {
                int codePointAt = Character.codePointAt(str, i);
                if (Character.isLetter(codePointAt)) {
                    Character.UnicodeBlock of = Character.UnicodeBlock.of(codePointAt);
                    if (!isLatinUnicodeBlock(of)) {
                        if (isCJKUnicodeBlock(of)) {
                            return guessCJKNameStyle(str, i);
                        }
                        if (isJapanesePhoneticUnicodeBlock(of)) {
                            return JAPANESE;
                        }
                        if (isKoreanUnicodeBlock(of)) {
                            return KOREAN;
                        }
                    }
                    nameStyle = WESTERN;
                }
                i += Character.charCount(codePointAt);
            }
            return nameStyle;
        }

        private static boolean isCJKUnicodeBlock(Character.UnicodeBlock unicodeBlock) {
            return unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || unicodeBlock == Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT;
        }

        private static boolean isChineseUnicode(char[] cArr) {
            for (char c2 : cArr) {
                if (ChinesePinyinConverter.getInstance().isChinesePinyinKnown(c2)) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isJapanesePhoneticUnicodeBlock(Character.UnicodeBlock unicodeBlock) {
            return unicodeBlock == Character.UnicodeBlock.KATAKANA || unicodeBlock == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS || unicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || unicodeBlock == Character.UnicodeBlock.HIRAGANA;
        }

        private static boolean isKoreanUnicodeBlock(Character.UnicodeBlock unicodeBlock) {
            return unicodeBlock == Character.UnicodeBlock.HANGUL_SYLLABLES || unicodeBlock == Character.UnicodeBlock.HANGUL_JAMO || unicodeBlock == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO;
        }

        private static boolean isLatinUnicodeBlock(Character.UnicodeBlock unicodeBlock) {
            return unicodeBlock == Character.UnicodeBlock.BASIC_LATIN || unicodeBlock == Character.UnicodeBlock.LATIN_1_SUPPLEMENT || unicodeBlock == Character.UnicodeBlock.LATIN_EXTENDED_A || unicodeBlock == Character.UnicodeBlock.LATIN_EXTENDED_B || unicodeBlock == Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL;
        }
    }

    private SortKeyGenerator() {
        this.mUtils = new BaseGenerator[NameStyle.values().length];
        this.mUtils[NameStyle.UNDEFINED.ordinal()] = new BaseGenerator();
    }

    private synchronized BaseGenerator getForSort(NameStyle nameStyle) {
        BaseGenerator baseGenerator;
        baseGenerator = this.mUtils[nameStyle.ordinal()];
        if (baseGenerator == null && nameStyle == NameStyle.CHINESE) {
            baseGenerator = new ChineseLocaleUtils();
            this.mUtils[nameStyle.ordinal()] = baseGenerator;
        }
        if (baseGenerator == null) {
            baseGenerator = this.mUtils[NameStyle.UNDEFINED.ordinal()];
        }
        return baseGenerator;
    }

    public static SortKeyGenerator getInstance() {
        return INSTANCE.get();
    }

    public String getSortKey(String str) {
        return getForSort(NameStyle.guessFullNameStyle(str)).getSortKey(str);
    }
}
