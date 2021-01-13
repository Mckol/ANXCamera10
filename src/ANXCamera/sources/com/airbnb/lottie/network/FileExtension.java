package com.airbnb.lottie.network;

import com.airbnb.lottie.d.d;

public enum FileExtension {
    JSON(".json"),
    ZIP(".zip");
    
    public final String extension;

    private FileExtension(String str) {
        this.extension = str;
    }

    public static FileExtension ba(String str) {
        FileExtension[] values = values();
        for (FileExtension fileExtension : values) {
            if (str.endsWith(fileExtension.extension)) {
                return fileExtension;
            }
        }
        d.warning("Unable to find correct extension for " + str);
        return JSON;
    }

    public String Do() {
        return ".temp" + this.extension;
    }

    public String toString() {
        return this.extension;
    }
}
