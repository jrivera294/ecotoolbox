package com.ucab.ecotoolbox.ecotoolbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by Vicky 20/12/2014.
 */
public class Tutorial_Activity extends ActionBarActivity implements ActionBar.TabListener {
    ActionBar actionbar;
    ViewPager viewpager;
    Tutorial_Adapter ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_pager);
        viewpager = (ViewPager) findViewById(R.id.pager);
        ft = new Tutorial_Adapter(getSupportFragmentManager());
        actionbar = getSupportActionBar();
        viewpager.setAdapter(ft);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.addTab(actionbar.newTab().setText("1").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("2").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("3").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("4").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("5").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("6").setTabListener(this));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                actionbar.setSelectedNavigationItem(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void onClickHome(View v){
        Intent intent1 = new Intent(this, Home.class);
        startActivity(intent1);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}

