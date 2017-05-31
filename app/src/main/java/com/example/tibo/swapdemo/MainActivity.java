package com.example.tibo.swapdemo;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity{

    ViewPager viewPager;
    public static Context contextOfApplication;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        contextOfApplication = getApplicationContext();

        tabLayout = (TabLayout)findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }


}
