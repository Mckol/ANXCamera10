package com.android.camera.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import java.util.List;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public BaseFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> list) {
        super(fragmentManager);
        this.mFragmentList = list;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        List<Fragment> list = this.mFragmentList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public List<Fragment> getFragmentList() {
        return this.mFragmentList;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.mFragmentList.get(i);
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public long getItemId(int i) {
        Fragment item = getItem(i);
        return item != null ? (long) (item.hashCode() | i) : super.getItemId(i);
    }

    public void recycleFragmentList(FragmentManager fragmentManager) {
        if (this.mFragmentList != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            for (Fragment fragment : this.mFragmentList) {
                beginTransaction.remove(fragment);
            }
            beginTransaction.commitAllowingStateLoss();
        }
    }
}
