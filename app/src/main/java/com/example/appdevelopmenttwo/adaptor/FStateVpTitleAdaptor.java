package com.example.appdevelopmenttwo.adaptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appdevelopmenttwo.fragment.TabScheduleFragment;

import java.util.List;

public class FStateVpTitleAdaptor extends FragmentStatePagerAdapter {
    private List<TabScheduleFragment> fragmentList;
    private List<String> titleList;

    public FStateVpTitleAdaptor(@NonNull FragmentManager fm, List<TabScheduleFragment> fragmentList, List<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList == null? null: fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null? 0: fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList == null? "": titleList.get(position);
    }
}
