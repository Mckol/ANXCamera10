package com.android.camera2.vendortag.struct;

public class SuperNightEvValue {
    private int mSequenceNum;
    private int[] mValue;

    private SuperNightEvValue(int i, int[] iArr) {
        this.mSequenceNum = i;
        this.mValue = iArr;
    }

    /*  JADX ERROR: Type inference failed with exception
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [byte, int], vars: [r1v0 ??, r1v1 ??, r1v4 ??, r1v2 ??, r1v3 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:51)
        	at jadx.core.dex.visitors.InitCodeVariables.rerun(InitCodeVariables.java:39)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.trySplitConstInsns(TypeInferenceVisitor.java:583)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:102)
        */
    public static com.android.camera2.vendortag.struct.SuperNightEvValue parseSuperNightEvValue(byte[] r3, java.lang.String r4, boolean r5) {
        /*
        // Method dump skipped, instructions count: 124
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.camera2.vendortag.struct.SuperNightEvValue.parseSuperNightEvValue(byte[], java.lang.String, boolean):com.android.camera2.vendortag.struct.SuperNightEvValue");
    }

    public int getSequenceNum() {
        return this.mSequenceNum;
    }

    public int[] getValue() {
        return this.mValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((this.mSequenceNum * 2) + 2);
        int[] iArr = this.mValue;
        if (iArr != null && iArr.length > 0) {
            sb.append("[");
            int i = 0;
            while (true) {
                int[] iArr2 = this.mValue;
                if (i >= iArr2.length) {
                    break;
                }
                sb.append(iArr2[i]);
                if (i != this.mValue.length - 1) {
                    sb.append(",");
                }
                i++;
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
