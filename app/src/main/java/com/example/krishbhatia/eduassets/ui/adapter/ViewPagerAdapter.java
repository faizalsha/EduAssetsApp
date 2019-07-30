package com.example.krishbhatia.eduassets.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.krishbhatia.eduassets.Constants;
import com.example.krishbhatia.eduassets.ui.fragments.AllCoursesFragment;
import com.example.krishbhatia.eduassets.ui.fragments.MySubjectsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Fragment[] childFragments;
    private String[] titles={Constants.ALL_COURSES,Constants.MY_SUBJECTS};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {
                new AllCoursesFragment(), //0
                new MySubjectsFragment() //1

        };
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length; //3 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
     return titles[position];
    }
}