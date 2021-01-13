package com.arcsoft.avatar2.recoder;

import android.opengl.GLES30;
import com.arcsoft.avatar2.gl.GLFramebuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FrameQueue {

    /* renamed from: a  reason: collision with root package name */
    private static final String f260a = "FrameQueue";

    /* renamed from: b  reason: collision with root package name */
    private FrameItem f261b = null;

    /* renamed from: c  reason: collision with root package name */
    private FrameItem f262c = null;

    /* renamed from: d  reason: collision with root package name */
    private List<FrameItem> f263d = new ArrayList();

    /* renamed from: e  reason: collision with root package name */
    private Queue<FrameItem> f264e = new LinkedList();
    private boolean f;

    public void addEmptyFrameForConsumer() {
        FrameItem frameItem = this.f262c;
        if (frameItem != null) {
            this.f263d.add(frameItem);
            this.f262c = null;
        }
    }

    public void addFrameForProducer() {
        FrameItem frameItem = this.f261b;
        if (frameItem != null) {
            this.f264e.offer(frameItem);
            this.f261b = null;
        }
    }

    public void deleteSync(FrameItem frameItem) {
        try {
            if (0 != frameItem.f259a) {
                GLES30.glDeleteSync(frameItem.f259a);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            frameItem.f259a = 0;
            throw th;
        }
        frameItem.f259a = 0;
    }

    public FrameItem getFrameForConsumer() {
        FrameItem frameItem = this.f262c;
        if (frameItem != null) {
            return frameItem;
        }
        if (this.f264e.isEmpty()) {
            return null;
        }
        this.f262c = this.f264e.poll();
        return this.f262c;
    }

    public FrameItem getFrameForProducer() {
        FrameItem frameItem = this.f261b;
        if (frameItem != null) {
            return frameItem;
        }
        if (!this.f263d.isEmpty()) {
            this.f261b = this.f263d.remove(0);
        } else if (this.f264e.isEmpty()) {
            return null;
        } else {
            this.f261b = this.f264e.poll();
        }
        return this.f261b;
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
            this.f263d.add(frameItem);
        }
        this.f = true;
    }

    public boolean isIsInited() {
        return this.f;
    }

    public int queueSize() {
        return this.f264e.size();
    }

    public void unInit() {
        GLFramebuffer gLFramebuffer;
        GLFramebuffer gLFramebuffer2;
        GLFramebuffer gLFramebuffer3;
        FrameItem frameItem = this.f261b;
        if (!(frameItem == null || (gLFramebuffer3 = frameItem.mFramebuffer) == null)) {
            gLFramebuffer3.unInit();
            deleteSync(this.f261b);
            this.f261b.mFramebuffer = null;
            this.f261b = null;
        }
        FrameItem frameItem2 = this.f262c;
        if (!(frameItem2 == null || (gLFramebuffer2 = frameItem2.mFramebuffer) == null)) {
            gLFramebuffer2.unInit();
            deleteSync(this.f262c);
            this.f262c.mFramebuffer = null;
            this.f262c = null;
        }
        if (!this.f263d.isEmpty()) {
            for (FrameItem frameItem3 : this.f263d) {
                GLFramebuffer gLFramebuffer4 = frameItem3.mFramebuffer;
                if (gLFramebuffer4 != null) {
                    gLFramebuffer4.unInit();
                    deleteSync(frameItem3);
                    frameItem3.mFramebuffer = null;
                }
            }
        }
        this.f263d.clear();
        while (!this.f264e.isEmpty()) {
            FrameItem poll = this.f264e.poll();
            if (!(poll == null || (gLFramebuffer = poll.mFramebuffer) == null)) {
                gLFramebuffer.unInit();
                deleteSync(poll);
                poll.mFramebuffer = null;
            }
        }
        this.f264e.clear();
        this.f = false;
    }
}
