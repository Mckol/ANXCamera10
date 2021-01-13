package com.miui.internal.variable;

import android.view.ViewGroup;
import com.miui.internal.util.ClassProxy;
import com.miui.internal.variable.Android_View_View_class;

public abstract class Android_View_ViewGroup_class extends ClassProxy<ViewGroup> implements IManagedClassProxy {

    public static class Factory extends AbsClassFactory {
        private Android_View_ViewGroup_class Android_View_ViewGroup_class;

        /* access modifiers changed from: private */
        public static class Holder {
            static final Factory INSTANCE = new Factory();

            private Holder() {
            }
        }

        private Factory() {
            this.Android_View_ViewGroup_class = (Android_View_ViewGroup_class) create("Android_View_ViewGroup_class");
        }

        public static Factory getInstance() {
            return Holder.INSTANCE;
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_View_ViewGroup_class get() {
            return this.Android_View_ViewGroup_class;
        }
    }

    public Android_View_ViewGroup_class() {
        super(ViewGroup.class);
    }

    public boolean getTagChildrenSequenceState(ViewGroup viewGroup) {
        return Android_View_View_class.Factory.getInstance().get().getCustomizedValue(viewGroup).mTagChildrenSequenceState;
    }

    public void setTagChildrenSequenceState(ViewGroup viewGroup, boolean z) {
        Android_View_View_class.Factory.getInstance().get().getCustomizedValue(viewGroup).mTagChildrenSequenceState = z;
    }
}
