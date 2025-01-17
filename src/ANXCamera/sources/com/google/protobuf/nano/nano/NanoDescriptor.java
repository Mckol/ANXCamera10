package com.google.protobuf.nano.nano;

import com.google.protobuf.nano.DescriptorProtos;
import com.google.protobuf.nano.Extension;
import com.google.protobuf.nano.NanoEnumValue;

public abstract class NanoDescriptor {
    public static final Extension<DescriptorProtos.EnumValueOptions, Boolean> emeritus = Extension.createPrimitiveTyped(8, Boolean.class, 1307892264);
    public static final Extension<DescriptorProtos.FileOptions, byte[]> encodedMungee = Extension.createPrimitiveTyped(12, byte[].class, 1257962002);
    public static final Extension<DescriptorProtos.EnumOptions, Boolean> enumAsLite = Extension.createPrimitiveTyped(8, Boolean.class, 1195355736);
    public static final Extension<DescriptorProtos.EnumOptions, String> enumUnmungedFileDescriptorName = Extension.createPrimitiveTyped(9, String.class, 1522362506);
    public static final Extension<DescriptorProtos.FileOptions, Boolean> fileAsLite = Extension.createPrimitiveTyped(8, Boolean.class, 1445185760);
    public static final Extension<DescriptorProtos.FieldOptions, Integer> generateAs = Extension.createPrimitiveTyped(14, Integer.class, 1437615632);
    public static final Extension<DescriptorProtos.EnumOptions, Boolean> legacyEnum = Extension.createPrimitiveTyped(8, Boolean.class, 1308211224);
    public static final Extension<DescriptorProtos.MessageOptions, Boolean> legacyOneof = Extension.createPrimitiveTyped(8, Boolean.class, 1180950304);
    public static final Extension<DescriptorProtos.MessageOptions, Boolean> messageAsLite = Extension.createPrimitiveTyped(8, Boolean.class, 1195348696);
    public static final Extension<DescriptorProtos.MessageOptions, String> messageUnmungedFileDescriptorName = Extension.createPrimitiveTyped(9, String.class, 1522304402);
    public static final Extension<DescriptorProtos.FileOptions, Integer> munger = Extension.createPrimitiveTyped(14, Integer.class, 1243722024);
    public static final Extension<DescriptorProtos.EnumValueOptions, Boolean> unmungedDepsCompliant = Extension.createPrimitiveTyped(8, Boolean.class, 1362093848);
    public static final Extension<DescriptorProtos.EnumValueOptions, Boolean> watermarkCompliant = Extension.createPrimitiveTyped(8, Boolean.class, 1301621224);
    public static final Extension<DescriptorProtos.EnumValueOptions, String[]> whitelisted = Extension.createRepeatedPrimitiveTyped(9, String[].class, 1432768322, 1432768322, 0);

    public interface GenerateAs {
        @NanoEnumValue(legacy = false, value = GenerateAs.class)
        public static final int INVALID = 0;
        @NanoEnumValue(legacy = false, value = GenerateAs.class)
        public static final int LITE = 2;
        @NanoEnumValue(legacy = false, value = GenerateAs.class)
        public static final int NANO = 1;
        @NanoEnumValue(legacy = false, value = GenerateAs.class)
        public static final int OMIT = 3;
    }

    public interface Munger {
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int ADS_EXPRESS_MOBILEAPP = 10;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int AGSA_PROTO = 2;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int ANDROID_UTIL_CONVERT_TO_NANO_PROTOS = 7;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int CLOCKWORK_PROTO = 15;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int COMMERCE_PAYMENTS_INSTORE_TOOLS_CONVERT_TO_NANO_PROTOS = 6;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int DOTS = 18;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int FIXED_CORRECT_PROTO_PLAY_COMMON = 14;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int FIXED_CORRECT_PROTO_PLAY_ENTERTAINMENT = 3;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int FIXED_CORRECT_PROTO_PLAY_STORE = 4;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int GOGGLES_PROTO = 16;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int HANGOUTS = 12;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int INNERTUBE = 11;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int KIDS_MANAGEMENT = 9;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int NONE = 0;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int REDUCED_NANO_PROTO = 1;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int S3_SPEECH_PROTO = 17;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int SIMUX = 8;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int TESTING_NOT_WATERMARK_COMPLIANT = 100;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int TESTING_WATERMARK_COMPLIANT = 101;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int TVSEARCH = 13;
        @NanoEnumValue(legacy = false, value = Munger.class)
        public static final int WASP = 5;
    }

    private NanoDescriptor() {
    }

    @NanoEnumValue(legacy = false, value = GenerateAs.class)
    public static int checkGenerateAsOrThrow(int i) {
        if (i >= 0 && i <= 3) {
            return i;
        }
        StringBuilder sb = new StringBuilder(42);
        sb.append(i);
        sb.append(" is not a valid enum GenerateAs");
        throw new IllegalArgumentException(sb.toString());
    }

    @NanoEnumValue(legacy = false, value = GenerateAs.class)
    public static int[] checkGenerateAsOrThrow(int[] iArr) {
        int[] iArr2 = (int[]) iArr.clone();
        for (int i : iArr2) {
            checkGenerateAsOrThrow(i);
        }
        return iArr2;
    }

    @NanoEnumValue(legacy = false, value = Munger.class)
    public static int checkMungerOrThrow(int i) {
        if (i >= 0 && i <= 18) {
            return i;
        }
        if (i >= 100 && i <= 101) {
            return i;
        }
        StringBuilder sb = new StringBuilder(38);
        sb.append(i);
        sb.append(" is not a valid enum Munger");
        throw new IllegalArgumentException(sb.toString());
    }

    @NanoEnumValue(legacy = false, value = Munger.class)
    public static int[] checkMungerOrThrow(int[] iArr) {
        int[] iArr2 = (int[]) iArr.clone();
        for (int i : iArr2) {
            checkMungerOrThrow(i);
        }
        return iArr2;
    }
}
