package com.arcsoft.avatar.recoder;

import android.opengl.GLES30;
import com.arcsoft.avatar.gl.GLFramebuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FrameQueue {

    /* renamed from: a  reason: collision with root package name */
    private static final String f102a = "FrameQueue";

    /* renamed from: b  reason: collision with root package name */
    private FrameItem f103b = null;

    /* renamed from: c  reason: collision with root package name */
    private FrameItem f104c = null;

    /* renamed from: d  reason: collision with root package name */
    private List<FrameItem> f105d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private Queue<FrameItem> f106e = new LinkedList();
    private boolean f;

    public void addEmptyFrameForConsumer() {
        FrameItem frameItem = this.f104c;
        if (frameItem != null) {
            this.f105d.add(frameItem);
            this.f104c = null;
        }
    }

    public void addFrameForProducer() {
        FrameItem frameItem = this.f103b;
        if (frameItem != null) {
            this.f106e.offer(frameItem);
            this.f103b = null;
        }
    }

    public void deleteSync(FrameItem frameItem) {
        try {
            if (0 != frameItem.f101a) {
                GLES30.glDeleteSync(frameItem.f101a);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            frameItem.f101a = 0;
            throw th;
        }
        frameItem.f101a = 0;
    }

    public FrameItem getFrameForConsumer() {
        FrameItem frameItem = this.f104c;
        if (frameItem != null) {
            return frameItem;
        }
        if (this.f106e.isEmpty()) {
            return null;
        }
        this.f104c = this.f106e.poll();
        return this.f104c;
    }

    public FrameItem getFrameForProducer() {
        FrameItem frameItem = this.f103b;
        if (frameItem != null) {
            return frameItem;
        }
        if (!this.f105d.isEmpty()) {
            this.f103b = this.f105d.remove(0);
        } else if (this.f106e.isEmpty()) {
            return null;
        } else {
            this.f103b = this.f106e.poll();
        }
        return this.f103b;
    }

    public void init(int i, int i2, int i3, boolean z) {
        unInit();
        for (int i4 = 0; i4 < i; i4++) {
            FrameItem frameItem = new FrameItem();
            frameItem.mIsEmpty = true;
            frameItem.mIsInited = true;
            frameItem.mFrameIndex = i4;
            frameItem.mFramebuffer = new GLFramebuffer();
            frameItem.mFramebuffer.init(i2, i3, z);
            this.f105d.add(frameItem);
        }
        this.f = true;
    }

    public boolean isIsInited() {
        return this.f;
    }

    public int queueSize() {
        return this.f106e.size();
    }

    public void unInit() {
        GLFramebuffer gLFramebuffer;
        GLFramebuffer gLFramebuffer2;
        GLFramebuffer gLFramebuffer3;
        FrameItem frameItem = this.f103b;
        if (!(frameItem == null || (gLFramebuffer3 = frameItem.mFramebuffer) == null)) {
            gLFramebuffer3.unInit();
            deleteSync(this.f103b);
            this.f103b.mFramebuffer = null;
            this.f103b = null;
        }
        FrameItem frameItem2 = this.f104c;
        if (!(frameItem2 == null || (gLFramebuffer2 = frameItem2.mFramebuffer) == null)) {
            gLFramebuffer2.unInit();
            deleteSync(this.f104c);
            this.f104c.mFramebuffer = null;
            this.f104c = null;
        }
        if (!this.f105d.isEmpty()) {
            for (FrameItem frameItem3 : this.f105d) {
                GLFramebuffer gLFramebuffer4 = frameItem3.mFramebuffer;
                if (gLFramebuffer4 != null) {
                    gLFramebuffer4.unInit();
                    deleteSync(frameItem3);
                    frameItem3.mFramebuffer = null;
                }
            }
        }
        this.f105d.clear();
        while (!this.f106e.isEmpty()) {
            FrameItem poll = this.f106e.poll();
            if (!(poll == null || (gLFramebuffer = poll.mFramebuffer) == null)) {
                gLFramebuffer.unInit();
                deleteSync(poll);
                poll.mFramebuffer = null;
            }
        }
        this.f106e.clear();
        this.f = false;
    }
}
