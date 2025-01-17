package com.facebook.rebound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class BaseSpringSystem {
    private final Set<Spring> mActiveSprings = new CopyOnWriteArraySet();
    private boolean mIdle = true;
    private final CopyOnWriteArraySet<SpringSystemListener> mListeners = new CopyOnWriteArraySet<>();
    private final SpringLooper mSpringLooper;
    private final Map<String, Spring> mSpringRegistry = new HashMap();

    public BaseSpringSystem(SpringLooper springLooper) {
        if (springLooper != null) {
            this.mSpringLooper = springLooper;
            this.mSpringLooper.setSpringSystem(this);
            return;
        }
        throw new IllegalArgumentException("springLooper is required");
    }

    /* access modifiers changed from: package-private */
    public void activateSpring(String str) {
        Spring spring = this.mSpringRegistry.get(str);
        if (spring != null) {
            this.mActiveSprings.add(spring);
            if (getIsIdle()) {
                this.mIdle = false;
                this.mSpringLooper.start();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("springId " + str + " does not reference a registered spring");
    }

    public void addListener(SpringSystemListener springSystemListener) {
        if (springSystemListener != null) {
            this.mListeners.add(springSystemListener);
            return;
        }
        throw new IllegalArgumentException("newListener is required");
    }

    /* access modifiers changed from: package-private */
    public void advance(double d2) {
        for (Spring spring : this.mActiveSprings) {
            if (spring.systemShouldAdvance()) {
                spring.advance(d2 / 1000.0d);
            } else {
                this.mActiveSprings.remove(spring);
            }
        }
    }

    public Spring createSpring() {
        Spring spring = new Spring(this);
        registerSpring(spring);
        return spring;
    }

    /* access modifiers changed from: package-private */
    public void deregisterSpring(Spring spring) {
        if (spring != null) {
            this.mActiveSprings.remove(spring);
            this.mSpringRegistry.remove(spring.getId());
            return;
        }
        throw new IllegalArgumentException("spring is required");
    }

    public List<Spring> getAllSprings() {
        Collection<Spring> values = this.mSpringRegistry.values();
        return Collections.unmodifiableList(values instanceof List ? (List) values : new ArrayList(values));
    }

    public boolean getIsIdle() {
        return this.mIdle;
    }

    public Spring getSpringById(String str) {
        if (str != null) {
            return this.mSpringRegistry.get(str);
        }
        throw new IllegalArgumentException("id is required");
    }

    public void loop(double d2) {
        Iterator<SpringSystemListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onBeforeIntegrate(this);
        }
        advance(d2);
        if (this.mActiveSprings.isEmpty()) {
            this.mIdle = true;
        }
        Iterator<SpringSystemListener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onAfterIntegrate(this);
        }
        if (this.mIdle) {
            this.mSpringLooper.stop();
        }
    }

    /* access modifiers changed from: package-private */
    public void registerSpring(Spring spring) {
        if (spring == null) {
            throw new IllegalArgumentException("spring is required");
        } else if (!this.mSpringRegistry.containsKey(spring.getId())) {
            this.mSpringRegistry.put(spring.getId(), spring);
        } else {
            throw new IllegalArgumentException("spring is already registered");
        }
    }

    public void removeAllListeners() {
        this.mListeners.clear();
    }

    public void removeListener(SpringSystemListener springSystemListener) {
        if (springSystemListener != null) {
            this.mListeners.remove(springSystemListener);
            return;
        }
        throw new IllegalArgumentException("listenerToRemove is required");
    }
}
