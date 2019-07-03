package com.example.krishbhatia.eduassets.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;


import com.example.krishbhatia.eduassets.R;
import com.example.krishbhatia.eduassets.ui.adapter.ViewPagerAdapter;

public class HomePageActivity extends AppCompatActivity  {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        tabLayout = findViewById(R.id.tabs);
//        toolbar=findViewById(R.id.mainToolbar);
//
//        tabLayout.setupWithViewPager(viewPager);


    }

}