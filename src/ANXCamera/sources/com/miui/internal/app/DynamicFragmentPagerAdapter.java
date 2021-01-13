package com.miui.internal.app;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import miui.view.PagerAdapter;
import miui.view.ViewPager;

/* access modifiers changed from: package-private */
/* compiled from: ActionBarViewPagerController */
public class DynamicFragmentPagerAdapter extends PagerAdapter {
    private Context mContext;
    private FragmentTransaction mCurTransaction = null;
    private Fragment mCurrentPrimaryItem = null;
    private ArrayList<FragmentInfo> mFragmentInfos = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private boolean mInitialized;
    private WeakReference<ViewPager> mViewPagerRef;

    /* access modifiers changed from: package-private */
    /* compiled from: ActionBarViewPagerController */
    public class FragmentInfo {
        Bundle args;
        Class<? extends Fragment> clazz;
        Fragment fragment = null;
        boolean hasActionMenu;
        ActionBar.Tab tab;
        String tag;

        FragmentInfo(String str, Class<? extends Fragment> cls, Bundle bundle, ActionBar.Tab tab2, boolean z) {
            this.tag = str;
            this.clazz = cls;
            this.args = bundle;
            this.tab = tab2;
            this.hasActionMenu = z;
        }
    }

    public DynamicFragmentPagerAdapter(Context context, FragmentManager fragmentManager, ViewPager viewPager) {
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.mViewPagerRef = new WeakReference<>(viewPager);
        viewPager.setAdapter(this);
    }

    private void removeAllFragmentFromManager() {
        FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
        int size = this.mFragmentInfos.size();
        for (int i = 0; i < size; i++) {
            beginTransaction.remove(getFragment(i, false));
        }
        beginTransaction.commitAllowingStateLoss();
        this.mFragmentManager.executePendingTransactions();
    }

    private void removeFragmentFromManager(Fragment fragment) {
        FragmentManager fragmentManager;
        if (fragment != null && (fragmentManager = fragment.getFragmentManager()) != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.remove(fragment);
            beginTransaction.commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
    }

    /* access modifiers changed from: package-private */
    public int addFragment(String str, int i, Class<? extends Fragment> cls, Bundle bundle, ActionBar.Tab tab, boolean z) {
        FragmentInfo fragmentInfo = new FragmentInfo(str, cls, bundle, tab, z);
        if (!isRTL()) {
            this.mFragmentInfos.add(i, fragmentInfo);
        } else if (i >= this.mFragmentInfos.size()) {
            this.mFragmentInfos.add(0, fragmentInfo);
        } else {
            this.mFragmentInfos.add(toIndexForRTL(i) + 1, fragmentInfo);
        }
        notifyDataSetChanged();
        return i;
    }

    /* access modifiers changed from: package-private */
    public int addFragment(String str, Class<? extends Fragment> cls, Bundle bundle, ActionBar.Tab tab, boolean z) {
        if (isRTL()) {
            this.mFragmentInfos.add(0, new FragmentInfo(str, cls, bundle, tab, z));
        } else {
            this.mFragmentInfos.add(new FragmentInfo(str, cls, bundle, tab, z));
        }
        notifyDataSetChanged();
        return this.mFragmentInfos.size() - 1;
    }

    @Override // miui.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach((Fragment) obj);
    }

    /* access modifiers changed from: package-private */
    public int findPositionByTag(String str) {
        int size = this.mFragmentInfos.size();
        for (int i = 0; i < size; i++) {
            if (this.mFragmentInfos.get(i).tag.equals(str)) {
                return toIndexForRTL(i);
            }
        }
        return -1;
    }

    @Override // miui.view.PagerAdapter
    public void finishUpdate(ViewGroup viewGroup) {
        FragmentTransaction fragmentTransaction = this.mCurTransaction;
        if (fragmentTransaction != null) {
            fragmentTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
        }
        if (!this.mInitialized && isRTL()) {
            this.mInitialized = true;
            ViewPager viewPager = this.mViewPagerRef.get();
            if (viewPager != null) {
                viewPager.setCurrentItem(toIndexForRTL(viewPager.getCurrentItem()));
            }
        }
    }

    @Override // miui.view.PagerAdapter
    public int getCount() {
        return this.mFragmentInfos.size();
    }

    /* access modifiers changed from: package-private */
    public Fragment getFragment(int i, boolean z) {
        return getFragment(i, z, true);
    }

    /* access modifiers changed from: package-private */
    public Fragment getFragment(int i, boolean z, boolean z2) {
        if (this.mFragmentInfos.isEmpty()) {
            return null;
        }
        ArrayList<FragmentInfo> arrayList = this.mFragmentInfos;
        if (z2) {
            i = toIndexForRTL(i);
        }
        FragmentInfo fragmentInfo = arrayList.get(i);
        if (fragmentInfo.fragment == null) {
            fragmentInfo.fragment = this.mFragmentManager.findFragmentByTag(fragmentInfo.tag);
            if (fragmentInfo.fragment == null && z) {
                fragmentInfo.fragment = Fragment.instantiate(this.mContext, fragmentInfo.clazz.getName(), fragmentInfo.args);
                fragmentInfo.clazz = null;
                fragmentInfo.args = null;
            }
        }
        return fragmentInfo.fragment;
    }

    @Override // miui.view.PagerAdapter
    public int getItemPosition(Object obj) {
        int size = this.mFragmentInfos.size();
        for (int i = 0; i < size; i++) {
            if (obj == this.mFragmentInfos.get(i).fragment) {
                return i;
            }
        }
        return -2;
    }

    /* access modifiers changed from: package-private */
    public ActionBar.Tab getTabAt(int i) {
        return this.mFragmentInfos.get(i).tab;
    }

    @Override // miui.view.PagerAdapter
    public boolean hasActionMenu(int i) {
        if (i < 0 || i >= this.mFragmentInfos.size()) {
            return false;
        }
        return this.mFragmentInfos.get(i).hasActionMenu;
    }

    @Override // miui.view.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        Fragment fragment = getFragment(i, true, false);
        if (fragment.getFragmentManager() != null) {
            this.mCurTransaction.attach(fragment);
        } else {
            this.mCurTransaction.add(viewGroup.getId(), fragment, this.mFragmentInfos.get(i).tag);
        }
        if (fragment != this.mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    /* access modifiers changed from: package-private */
    public boolean isRTL() {
        return this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    @Override // miui.view.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    /* access modifiers changed from: package-private */
    public void removeAllFragment() {
        removeAllFragmentFromManager();
        this.mFragmentInfos.clear();
        notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public int removeFragment(ActionBar.Tab tab) {
        int size = this.mFragmentInfos.size();
        for (int i = 0; i < size; i++) {
            FragmentInfo fragmentInfo = this.mFragmentInfos.get(i);
            if (fragmentInfo.tab == tab) {
                removeFragmentFromManager(fragmentInfo.fragment);
                this.mFragmentInfos.remove(i);
                notifyDataSetChanged();
                return toIndexForRTL(i);
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int removeFragment(Fragment fragment) {
        int size = this.mFragmentInfos.size();
        for (int i = 0; i < size; i++) {
            if (getFragment(i, false) == fragment) {
                removeFragmentFromManager(fragment);
                this.mFragmentInfos.remove(i);
                notifyDataSetChanged();
                return toIndexForRTL(i);
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public void removeFragmentAt(int i) {
        removeFragmentFromManager(getFragment(i, false));
        this.mFragmentInfos.remove(toIndexForRTL(i));
        notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    public void setFragmentActionMenuAt(int i, boolean z) {
        FragmentInfo fragmentInfo = this.mFragmentInfos.get(toIndexForRTL(i));
        if (fragmentInfo.hasActionMenu != z) {
            fragmentInfo.hasActionMenu = z;
            notifyDataSetChanged();
        }
    }

    @Override // miui.view.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.mCurrentPrimaryItem;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (!isRTL() || this.mInitialized) {
                if (fragment != null) {
                    fragment.setMenuVisibility(true);
                    fragment.setUserVisibleHint(true);
                }
                this.mCurrentPrimaryItem = fragment;
            }
        }
    }

    @Override // miui.view.PagerAdapter
    public void startUpdate(ViewGroup viewGroup) {
    }

    /* access modifiers changed from: package-private */
    public int toIndexForRTL(int i) {
        if (!isRTL()) {
            return i;
        }
        int size = this.mFragmentInfos.size() - 1;
        if (size > i) {
            return size - i;
        }
        return 0;
    }
}
