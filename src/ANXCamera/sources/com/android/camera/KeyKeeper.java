package com.android.camera;

public class KeyKeeper {
    private int mAssistantHash;

    /* access modifiers changed from: private */
    public static class Holder {
        private static KeyKeeper mInstance = new KeyKeeper();

        private Holder() {
        }
    }

    private KeyKeeper() {
    }

    public static KeyKeeper getInstance() {
        return Holder.mInstance;
    }

    public int getAssistantHash() {
        return this.mAssistantHash;
    }

    public void setAssistantHash(int i) {
        this.mAssistantHash = i;
    }
}
