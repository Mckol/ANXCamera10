package androidx.fragment.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(21)
class FragmentTransitionCompat21 extends FragmentTransitionImpl {
    FragmentTransitionCompat21() {
    }

    private static boolean hasSimpleTarget(Transition transition) {
        return !FragmentTransitionImpl.isNullOrEmpty(transition.getTargetIds()) || !FragmentTransitionImpl.isNullOrEmpty(transition.getTargetNames()) || !FragmentTransitionImpl.isNullOrEmpty(transition.getTargetTypes());
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void addTarget(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).addTarget(view);
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void addTargets(Object obj, ArrayList<View> arrayList) {
        Transition transition = (Transition) obj;
        if (transition != null) {
            int i = 0;
            if (transition instanceof TransitionSet) {
                TransitionSet transitionSet = (TransitionSet) transition;
                int transitionCount = transitionSet.getTransitionCount();
                while (i < transitionCount) {
                    addTargets(transitionSet.getTransitionAt(i), arrayList);
                    i++;
                }
            } else if (!hasSimpleTarget(transition) && FragmentTransitionImpl.isNullOrEmpty(transition.getTargets())) {
                int size = arrayList.size();
                while (i < size) {
                    transition.addTarget(arrayList.get(i));
                    i++;
                }
            }
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void beginDelayedTransition(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition) obj);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public boolean canHandle(Object obj) {
        return obj instanceof Transition;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public Object cloneTransition(Object obj) {
        if (obj != null) {
            return ((Transition) obj).clone();
        }
        return null;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3) {
        Transition transition = (Transition) obj;
        Transition transition2 = (Transition) obj2;
        Transition transition3 = (Transition) obj3;
        if (transition != null && transition2 != null) {
            transition = new TransitionSet().addTransition(transition).addTransition(transition2).setOrdering(1);
        } else if (transition == null) {
            transition = transition2 != null ? transition2 : null;
        }
        if (transition3 == null) {
            return transition;
        }
        TransitionSet transitionSet = new TransitionSet();
        if (transition != null) {
            transitionSet.addTransition(transition);
        }
        transitionSet.addTransition(transition3);
        return transitionSet;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public Object mergeTransitionsTogether(Object obj, Object obj2, Object obj3) {
        TransitionSet transitionSet = new TransitionSet();
        if (obj != null) {
            transitionSet.addTransition((Transition) obj);
        }
        if (obj2 != null) {
            transitionSet.addTransition((Transition) obj2);
        }
        if (obj3 != null) {
            transitionSet.addTransition((Transition) obj3);
        }
        return transitionSet;
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void removeTarget(Object obj, View view) {
        if (obj != null) {
            ((Transition) obj).removeTarget(view);
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void replaceTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        List<View> targets;
        Transition transition = (Transition) obj;
        int i = 0;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                replaceTargets(transitionSet.getTransitionAt(i), arrayList, arrayList2);
                i++;
            }
        } else if (!hasSimpleTarget(transition) && (targets = transition.getTargets()) != null && targets.size() == arrayList.size() && targets.containsAll(arrayList)) {
            int size = arrayList2 == null ? 0 : arrayList2.size();
            while (i < size) {
                transition.addTarget(arrayList2.get(i));
                i++;
            }
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                transition.removeTarget(arrayList.get(size2));
            }
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void scheduleHideFragmentView(Object obj, final View view, final ArrayList<View> arrayList) {
        ((Transition) obj).addListener(new Transition.TransitionListener() {
            /* class androidx.fragment.app.FragmentTransitionCompat21.AnonymousClass2 */

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                view.setVisibility(8);
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((View) arrayList.get(i)).setVisibility(0);
                }
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
            }
        });
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void scheduleRemoveTargets(Object obj, final Object obj2, final ArrayList<View> arrayList, final Object obj3, final ArrayList<View> arrayList2, final Object obj4, final ArrayList<View> arrayList3) {
        ((Transition) obj).addListener(new Transition.TransitionListener() {
            /* class androidx.fragment.app.FragmentTransitionCompat21.AnonymousClass3 */

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }

            public void onTransitionStart(Transition transition) {
                Object obj = obj2;
                if (obj != null) {
                    FragmentTransitionCompat21.this.replaceTargets(obj, arrayList, null);
                }
                Object obj2 = obj3;
                if (obj2 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(obj2, arrayList2, null);
                }
                Object obj3 = obj4;
                if (obj3 != null) {
                    FragmentTransitionCompat21.this.replaceTargets(obj3, arrayList3, null);
                }
            }
        });
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void setEpicenter(Object obj, final Rect rect) {
        if (obj != null) {
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback() {
                /* class androidx.fragment.app.FragmentTransitionCompat21.AnonymousClass4 */

                public Rect onGetEpicenter(Transition transition) {
                    Rect rect = rect;
                    if (rect == null || rect.isEmpty()) {
                        return null;
                    }
                    return rect;
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void setEpicenter(Object obj, View view) {
        if (view != null) {
            final Rect rect = new Rect();
            getBoundsOnScreen(view, rect);
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback() {
                /* class androidx.fragment.app.FragmentTransitionCompat21.AnonymousClass1 */

                public Rect onGetEpicenter(Transition transition) {
                    return rect;
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void setSharedElementTargets(Object obj, View view, ArrayList<View> arrayList) {
        TransitionSet transitionSet = (TransitionSet) obj;
        List<View> targets = transitionSet.getTargets();
        targets.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            FragmentTransitionImpl.bfsAddViewChildren(targets, arrayList.get(i));
        }
        targets.add(view);
        arrayList.add(view);
        addTargets(transitionSet, arrayList);
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public void swapSharedElementTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        TransitionSet transitionSet = (TransitionSet) obj;
        if (transitionSet != null) {
            transitionSet.getTargets().clear();
            transitionSet.getTargets().addAll(arrayList2);
            replaceTargets(transitionSet, arrayList, arrayList2);
        }
    }

    @Override // androidx.fragment.app.FragmentTransitionImpl
    public Object wrapTransitionInSet(Object obj) {
        if (obj == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition((Transition) obj);
        return transitionSet;
    }
}
