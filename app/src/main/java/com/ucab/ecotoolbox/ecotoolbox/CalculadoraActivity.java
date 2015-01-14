package com.ucab.ecotoolbox.ecotoolbox;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * Created by Jose Gabriel on 17/12/2014.
 */
public class CalculadoraActivity extends ActionBarActivity implements ActionBar.TabListener {
    ActionBar actionbar;
    ViewPager viewpager;
    FragmentPageAdapter ft;
    Double valorCarro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_fragment_pager);
        viewpager = (ViewPager) findViewById(R.id.pager);
        ft = new FragmentPageAdapter(getSupportFragmentManager());
        actionbar = getSupportActionBar();
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(ft);

        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionbar.addTab(actionbar.newTab().setText("Hogar").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Transporte").setTabListener(this));
        actionbar.addTab(actionbar.newTab().setText("Total").setTabListener(this));
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

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        if (tab.getPosition()==2) {
            FragmentPageAdapter adapter = (FragmentPageAdapter) viewpager.getAdapter();
            TotalFragment fragment = (TotalFragment)adapter.getItem(2);
            fragment.calcular();
        }
        viewpager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

}

