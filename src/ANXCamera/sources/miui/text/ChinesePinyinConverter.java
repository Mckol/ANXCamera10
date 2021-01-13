package miui.text;

import android.text.TextUtils;
import android.util.Log;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.miui.internal.util.DirectIndexedFileExtractor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import miui.util.AppConstants;
import miui.util.DirectIndexedFile;
import miui.util.SoftReferenceSingleton;

public class ChinesePinyinConverter {
    private static final char FIRST_BASIC_UNIHAN = 19968;
    private static final char FIRST_HINDI_UNICODE = 2304;
    private static final SoftReferenceSingleton<ChinesePinyinConverter> INSTANCE = new SoftReferenceSingleton<ChinesePinyinConverter>() {
        /* class miui.text.ChinesePinyinConverter.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public ChinesePinyinConverter createInstance() {
            return new ChinesePinyinConverter();
        }
    };
    private static final char LAST_BASIC_UNIHAN = 40869;
    private static final char LAST_HINDI_UNICODE = 2431;
    private static final char SPECIAL_LING = 12295;
    private static final String TAG = "ChinesePinyinConverter";
    private static HashMap<String, String[]> sHyphenatedNamePolyPhoneMap = new HashMap<>();
    private static HashMap<Character, String> sLastNamePolyPhoneMap = new HashMap<>();
    private ChinesePinyinDictionary mDictionary;

    private static class BopomofoConvertor {
        private static final String[] sPinyinToZhuyin = {SupportedConfigFactory.CLOSE_BY_GROUP, "ㄅ", "p", "ㄆ", "m", "ㄇ", SupportedConfigFactory.CLOSE_BY_BOKEH, "ㄈ", SupportedConfigFactory.CLOSE_BY_BURST_SHOOT, "ㄉ", "t", "ㄊ", "n", "ㄋ", SupportedConfigFactory.CLOSE_BY_RATIO, "ㄌ", SupportedConfigFactory.CLOSE_BY_HDR, "ㄍ", SupportedConfigFactory.CLOSE_BY_FILTER, "ㄎ", SupportedConfigFactory.CLOSE_BY_VIDEO, "ㄏ", SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL, "ㄐ", "q", "ㄑ", "x", "ㄒ", "zh", "ㄓ", "ch", "ㄔ", "sh", "ㄕ", "r", "ㄖ", "z", "ㄗ", SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, "ㄘ", "s", "ㄙ", "y", "ㄧ", "w", "ㄨ", SupportedConfigFactory.CLOSE_BY_HHT, "ㄚ", SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "ㄛ", "e", "ㄜ", SupportedConfigFactory.CLOSE_BY_ULTRA_WIDE, "ㄧ", "u", "ㄨ", "v", "ㄩ", "ao", "ㄠ", "ai", "ㄞ", "an", "ㄢ", "ang", "ㄤ", "ou", "ㄡ", "ong", "ㄨㄥ", "ei", "ㄟ", "en", "ㄣ", "eng", "ㄥ", "er", "ㄦ", "ie", "ㄧㄝ", "iu", "ㄧㄡ", "in", "ㄧㄣ", "ing", "ㄧㄥ", "iong", "ㄩㄥ", "ui", "ㄨㄟ", "un", "ㄨㄣ", "ue", "ㄩㄝ", "ve", "ㄩㄝ", "van", "ㄩㄢ", "vn", "ㄩㄣ", "", "", "zhi", "ㄓ", "chi", "ㄔ", "shi", "ㄕ", "ri", "ㄖ", "zi", "ㄗ", "ci", "ㄘ", "si", "ㄙ", "yi", "ㄧ", "ye", "ㄧㄝ", "you", "ㄧㄡ", "yin", "ㄧㄣ", "ying", "ㄧㄥ", "wu", "ㄨ", "wei", "ㄨㄟ", "wen", "ㄨㄣ", "yu", "ㄩ", "yue", "ㄩㄝ", "yuan", "ㄩㄢ", "yun", "ㄩㄣ", "yong", "ㄩㄥ", "ju", "ㄐㄩ", "jue", "ㄐㄩㄝ", "juan", "ㄐㄩㄢ", "jun", "ㄐㄩㄣ", "qu", "ㄑㄩ", "que", "ㄑㄩㄝ", "quan", "ㄑㄩㄢ", "qun", "ㄑㄩㄣ", "xu", "ㄒㄩ", "xue", "ㄒㄩㄝ", "xuan", "ㄒㄩㄢ", "xun", "ㄒㄩㄣ"};
        private static final Node sRoot = build();

        /* access modifiers changed from: private */
        public static class Node {
            public char ch;
            public Node children;
            public Node next;
            public String output;
            public boolean specials;

            private Node() {
            }
        }

        private BopomofoConvertor() {
        }

        private static Node build() {
            Node node = new Node();
            node.output = "";
            int i = 0;
            boolean z = false;
            while (true) {
                String[] strArr = sPinyinToZhuyin;
                if (i >= strArr.length) {
                    return node;
                }
                String str = strArr[i];
                if (str.length() == 0) {
                    z = true;
                } else {
                    int length = str.length();
                    Node node2 = node;
                    for (int i2 = 0; i2 < length; i2++) {
                        char lowerCase = Character.toLowerCase(str.charAt(i2));
                        Node node3 = node2.children;
                        while (node3 != null && node3.ch != lowerCase) {
                            node3 = node3.next;
                        }
                        if (node3 == null) {
                            node3 = new Node();
                            node3.ch = lowerCase;
                            node3.next = node2.children;
                            node2.children = node3;
                        }
                        node2 = node3;
                    }
                    node2.specials = z;
                    node2.output = sPinyinToZhuyin[i + 1];
                }
                i += 2;
            }
        }

        private static int convert(String str, int i, StringBuilder sb) {
            int length = str.length();
            int i2 = i;
            Node node = null;
            Node node2 = sRoot;
            for (int i3 = i2; i3 < length && node2 != null; i3++) {
                char lowerCase = Character.toLowerCase(str.charAt(i3));
                node2 = node2.children;
                while (node2 != null && node2.ch != lowerCase) {
                    node2 = node2.next;
                }
                if (!(node2 == null || node2.output == null || (node2.specials && i3 != length - 1))) {
                    i2 = i3 + 1;
                    node = node2;
                }
            }
            if (node != null) {
                sb.append(node.output);
            }
            return i2 - i;
        }

        public static CharSequence convert(String str, StringBuilder sb) {
            if (str == null || str.length() == 0) {
                return str;
            }
            int i = 0;
            if (sb == null) {
                sb = new StringBuilder();
            } else {
                sb.setLength(0);
            }
            int length = str.length();
            while (i != length) {
                int convert = convert(str, i, sb);
                if (convert <= 0) {
                    return "";
                }
                i += convert;
            }
            return sb;
        }
    }

    /* access modifiers changed from: private */
    public static class ChinesePinyinDictionary {
        private static final String UNICODE_2_PINYIN_ASSET_NAME = "pinyinindex.idf";
        private DirectIndexedFile.Reader mReader;

        private ChinesePinyinDictionary() {
            String directIndexedFilePath = DirectIndexedFileExtractor.getDirectIndexedFilePath(AppConstants.getCurrentApplication(), UNICODE_2_PINYIN_ASSET_NAME);
            if (directIndexedFilePath != null && new File(directIndexedFilePath).exists()) {
                try {
                    this.mReader = DirectIndexedFile.open(directIndexedFilePath);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            if (this.mReader == null) {
                try {
                    this.mReader = DirectIndexedFile.open(AppConstants.getCurrentApplication().getResources().getAssets().open(UNICODE_2_PINYIN_ASSET_NAME, 1));
                } catch (Exception unused) {
                    Log.e(ChinesePinyinConverter.TAG, "Init resource IOException");
                }
            }
        }

        /* access modifiers changed from: protected */
        public void finalize() throws Throwable {
            DirectIndexedFile.Reader reader = this.mReader;
            if (reader != null) {
                reader.close();
            }
            super.finalize();
        }

        public String[] getPinyinString(char c2) {
            DirectIndexedFile.Reader reader = this.mReader;
            if (reader == null) {
                return null;
            }
            String str = (String) reader.get(0, c2 - 19968, 0);
            if (!TextUtils.isEmpty(str)) {
                return str.split(",");
            }
            Log.e(ChinesePinyinConverter.TAG, "The ChinesePinyinConverter dictionary is not correct, need rebuild or reset the ROM.");
            return null;
        }
    }

    public static class Token {
        public static final int HINDI = 4;
        public static final int LATIN = 1;
        public static final int PINYIN = 2;
        private static final char SEPARATOR = ' ';
        public static final int UNKNOWN = 3;
        public String[] polyPhones;
        public String source;
        public String target;
        public int type;

        public Token() {
        }

        public Token(int i, String str, String str2) {
            this.type = i;
            this.source = str;
            this.target = str2;
        }
    }

    static {
        sHyphenatedNamePolyPhoneMap.put("单于", new String[]{"CHAN", "YU"});
        sHyphenatedNamePolyPhoneMap.put("长孙", new String[]{"ZHANG", "SUN"});
        sHyphenatedNamePolyPhoneMap.put("子车", new String[]{"ZI", "JU"});
        sHyphenatedNamePolyPhoneMap.put("万俟", new String[]{"MO", "QI"});
        sHyphenatedNamePolyPhoneMap.put("澹台", new String[]{"TAN", "TAI"});
        sHyphenatedNamePolyPhoneMap.put("尉迟", new String[]{"YU", "CHI"});
        sLastNamePolyPhoneMap.put((char) 20040, "YAO");
        sLastNamePolyPhoneMap.put((char) 19969, "DING");
        sLastNamePolyPhoneMap.put((char) 20446, "YU");
        sLastNamePolyPhoneMap.put((char) 36158, "JIA");
        sLastNamePolyPhoneMap.put((char) 27784, "SHEN");
        sLastNamePolyPhoneMap.put((char) 21340, "BU");
        sLastNamePolyPhoneMap.put((char) 34180, "BO");
        sLastNamePolyPhoneMap.put((char) 23387, "BO");
        sLastNamePolyPhoneMap.put((char) 36146, "BEN");
        sLastNamePolyPhoneMap.put((char) 36153, "FEI");
        sLastNamePolyPhoneMap.put((char) 27850, "BAN");
        sLastNamePolyPhoneMap.put((char) 33536, "BI");
        sLastNamePolyPhoneMap.put((char) 35098, "CHU");
        sLastNamePolyPhoneMap.put((char) 20256, "CHUAN");
        sLastNamePolyPhoneMap.put((char) 21442, "CAN");
        sLastNamePolyPhoneMap.put((char) 21333, "SHAN");
        sLastNamePolyPhoneMap.put((char) 37079, "CHI");
        sLastNamePolyPhoneMap.put((char) 38241, "CHAN");
        sLastNamePolyPhoneMap.put((char) 26397, "CHAO");
        sLastNamePolyPhoneMap.put((char) 21852, "CHUAI");
        sLastNamePolyPhoneMap.put((char) 34928, "CUI");
        sLastNamePolyPhoneMap.put((char) 26216, "CHANG");
        sLastNamePolyPhoneMap.put((char) 19985, "CHOU");
        sLastNamePolyPhoneMap.put((char) 30259, "CHOU");
        sLastNamePolyPhoneMap.put((char) 38271, "CHANG");
        sLastNamePolyPhoneMap.put((char) 36710, "CHE");
        sLastNamePolyPhoneMap.put((char) 32735, "ZHAI");
        sLastNamePolyPhoneMap.put((char) 20291, "DIAN");
        sLastNamePolyPhoneMap.put((char) 20992, "DIAO");
        sLastNamePolyPhoneMap.put((char) 35843, "DIAO");
        sLastNamePolyPhoneMap.put((char) 36934, "DI");
        sLastNamePolyPhoneMap.put((char) 26123, "GUI");
        sLastNamePolyPhoneMap.put((char) 33445, "GAI");
        sLastNamePolyPhoneMap.put((char) 33554, "KUANG");
        sLastNamePolyPhoneMap.put((char) 37063, "HUAN");
        sLastNamePolyPhoneMap.put((char) 24055, "XIANG");
        sLastNamePolyPhoneMap.put((char) 25750, "HAN");
        sLastNamePolyPhoneMap.put((char) 35265, "JIAN");
        sLastNamePolyPhoneMap.put((char) 38477, "JIANG");
        sLastNamePolyPhoneMap.put((char) 35282, "JIAO");
        sLastNamePolyPhoneMap.put((char) 32564, "JIAO");
        sLastNamePolyPhoneMap.put((char) 35760, "JI");
        sLastNamePolyPhoneMap.put((char) 29722, "JU");
        sLastNamePolyPhoneMap.put((char) 21095, "JI");
        sLastNamePolyPhoneMap.put((char) 38589, "JUAN");
        sLastNamePolyPhoneMap.put((char) 38551, "KUI");
        sLastNamePolyPhoneMap.put((char) 38752, "KU");
        sLastNamePolyPhoneMap.put((char) 20048, "YUE");
        sLastNamePolyPhoneMap.put((char) 21895, "LA");
        sLastNamePolyPhoneMap.put((char) 38610, "LUO");
        sLastNamePolyPhoneMap.put((char) 20102, "LIAO");
        sLastNamePolyPhoneMap.put((char) 32554, "MIAO");
        sLastNamePolyPhoneMap.put((char) 20340, "MI");
        sLastNamePolyPhoneMap.put((char) 35884, "MIAO");
        sLastNamePolyPhoneMap.put((char) 20060, "NIE");
        sLastNamePolyPhoneMap.put((char) 36898, "PANG");
        sLastNamePolyPhoneMap.put((char) 34028, "PENG");
        sLastNamePolyPhoneMap.put((char) 26420, "PIAO");
        sLastNamePolyPhoneMap.put((char) 20167, "QIU");
        sLastNamePolyPhoneMap.put((char) 35203, "QIN");
        sLastNamePolyPhoneMap.put((char) 30655, "QU");
        sLastNamePolyPhoneMap.put((char) 20160, "SHI");
        sLastNamePolyPhoneMap.put((char) 25240, "SHE");
        sLastNamePolyPhoneMap.put((char) 30509, "SUI");
        sLastNamePolyPhoneMap.put((char) 35299, "XIE");
        sLastNamePolyPhoneMap.put((char) 31995, "XI");
        sLastNamePolyPhoneMap.put((char) 38500, "XU");
        sLastNamePolyPhoneMap.put((char) 21592, "YUAN");
        sLastNamePolyPhoneMap.put((char) 36128, "YUAN");
        sLastNamePolyPhoneMap.put((char) 26366, "ZENG");
        sLastNamePolyPhoneMap.put((char) 26597, "ZHA");
        sLastNamePolyPhoneMap.put((char) 20256, "CHUAN");
        sLastNamePolyPhoneMap.put((char) 21484, "SHAO");
        sLastNamePolyPhoneMap.put((char) 37325, "chong");
        sLastNamePolyPhoneMap.put((char) 21306, "OU");
        sLastNamePolyPhoneMap.put((char) 26044, "YU");
        sLastNamePolyPhoneMap.put((char) 31181, "CHONG");
    }

    private ChinesePinyinConverter() {
        this.mDictionary = new ChinesePinyinDictionary();
    }

    private void addToken(StringBuilder sb, ArrayList<Token> arrayList, int i) {
        String sb2 = sb.toString();
        Token token = new Token(i, sb2, sb2);
        if (4 == i) {
            String[] hindiToPinyins = HindiPinyinConverter.getInstance().hindiToPinyins(token.source);
            if (hindiToPinyins.length > 0) {
                if (hindiToPinyins.length == 1) {
                    token.target = hindiToPinyins[0];
                    token.polyPhones = new String[]{token.target};
                } else {
                    token.target = hindiToPinyins[0];
                    token.polyPhones = hindiToPinyins;
                }
            }
        }
        arrayList.add(token);
        sb.setLength(0);
    }

    public static ChinesePinyinConverter getInstance() {
        return INSTANCE.get();
    }

    private ArrayList<Token> getPolyPhoneLastNameTokens(String str) {
        String substring;
        String[] strArr;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<Token> arrayList = new ArrayList<>();
        if (str.length() < 2 || (strArr = sHyphenatedNamePolyPhoneMap.get((substring = str.substring(0, 2)))) == null) {
            Character valueOf = Character.valueOf(str.charAt(0));
            String str2 = sLastNamePolyPhoneMap.get(valueOf);
            if (str2 == null) {
                return null;
            }
            Token token = new Token();
            token.type = 2;
            token.source = valueOf.toString();
            token.target = str2;
            arrayList.add(token);
            return arrayList;
        }
        for (int i = 0; i < strArr.length; i++) {
            Token token2 = new Token();
            token2.type = 2;
            token2.source = String.valueOf(substring.charAt(i));
            token2.target = strArr[i];
            arrayList.add(token2);
        }
        return arrayList;
    }

    public CharSequence convertPinyin2Bopomofo(String str, StringBuilder sb) {
        return BopomofoConvertor.convert(str, sb);
    }

    public ArrayList<Token> get(String str) {
        return get(str, true, true);
    }

    public ArrayList<Token> get(String str, boolean z, boolean z2) {
        int i;
        ArrayList<Token> polyPhoneLastNameTokens;
        ArrayList<Token> arrayList = new ArrayList<>();
        if (TextUtils.isEmpty(str)) {
            return arrayList;
        }
        if (z2 || (polyPhoneLastNameTokens = getPolyPhoneLastNameTokens(str)) == null || polyPhoneLastNameTokens.size() <= 0) {
            i = 0;
        } else {
            arrayList.addAll(polyPhoneLastNameTokens);
            i = polyPhoneLastNameTokens.size();
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        int i2 = 1;
        while (i < length) {
            char charAt = str.charAt(i);
            int i3 = 2;
            if (charAt == ' ') {
                if (sb.length() > 0) {
                    addToken(sb, arrayList, i2);
                }
                if (!z) {
                    String valueOf = String.valueOf(' ');
                    arrayList.add(new Token(3, valueOf, valueOf));
                }
            } else {
                if (charAt < 256) {
                    if (i > 0) {
                        char charAt2 = str.charAt(i - 1);
                        if ((charAt2 >= '0' && charAt2 <= '9') != (charAt >= '0' && charAt <= '9') && sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                    }
                    if (i2 != 1 && sb.length() > 0) {
                        addToken(sb, arrayList, i2);
                    }
                    sb.append(charAt);
                    i2 = 1;
                } else {
                    if (charAt == 12295) {
                        Token token = new Token();
                        token.type = 2;
                        token.target = "ling";
                        if (sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                        arrayList.add(token);
                    } else if (charAt >= 19968 && charAt <= 40869) {
                        String[] pinyinString = this.mDictionary.getPinyinString(charAt);
                        Token token2 = new Token();
                        token2.source = Character.toString(charAt);
                        if (pinyinString == null) {
                            token2.type = 3;
                            token2.target = Character.toString(charAt);
                        } else {
                            token2.type = 2;
                            token2.target = pinyinString[0];
                            if (pinyinString.length > 1) {
                                token2.polyPhones = pinyinString;
                            }
                        }
                        int i4 = token2.type;
                        if (i4 == 2) {
                            if (sb.length() > 0) {
                                addToken(sb, arrayList, i2);
                            }
                            arrayList.add(token2);
                        } else {
                            if (i2 != i4 && sb.length() > 0) {
                                addToken(sb, arrayList, i2);
                            }
                            i3 = token2.type;
                            sb.append(charAt);
                        }
                    } else if (charAt < 2304 || charAt > 2431) {
                        if (i2 != 3 && sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                        sb.append(charAt);
                    } else {
                        if (i2 != 4 && sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                        sb.append(charAt);
                        i2 = 4;
                    }
                    i2 = i3;
                }
                i++;
            }
            i2 = 3;
            i++;
        }
        if (sb.length() > 0) {
            addToken(sb, arrayList, i2);
        }
        return arrayList;
    }

    public boolean isChinesePinyinKnown(char c2) {
        return c2 >= 19968 && c2 <= 40869;
    }
}
