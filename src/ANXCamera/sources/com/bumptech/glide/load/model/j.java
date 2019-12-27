package com.bumptech.glide.load.model;

import android.os.ParcelFileDescriptor;
import com.arcsoft.camera.wideselfie.WideSelfieEngine;
import com.bumptech.glide.load.model.i;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: FileLoader */
class j implements i.d<ParcelFileDescriptor> {
    j() {
    }

    public Class<ParcelFileDescriptor> M() {
        return ParcelFileDescriptor.class;
    }

    /* renamed from: a */
    public void e(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        parcelFileDescriptor.close();
    }

    public ParcelFileDescriptor b(File file) throws FileNotFoundException {
        return ParcelFileDescriptor.open(file, WideSelfieEngine.MPAF_RGB_BASE);
    }
}
