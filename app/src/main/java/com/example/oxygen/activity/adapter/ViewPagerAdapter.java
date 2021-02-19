package com.example.oxygen.activity.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.oxygen.activity.ui.fragment.ActionPlan;
import com.example.oxygen.activity.ui.fragment.PeakFlowValues;
import com.example.oxygen.activity.ui.fragment.PlanList;
import com.example.oxygen.activity.ui.fragment.Privacy;
import com.example.oxygen.activity.ui.fragment.Info;
import com.example.oxygen.activity.ui.fragment.Weather;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {
                new Info(),
                new ActionPlan(),
                new PlanList(),
                new PeakFlowValues(),
                new Privacy(),
                new Weather()
        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length; //7 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = getItem(position).getClass().getName();
        return title.subSequence(title.lastIndexOf(".") + 1, title.length());
    }
}