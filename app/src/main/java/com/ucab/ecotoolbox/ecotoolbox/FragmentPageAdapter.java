package com.ucab.ecotoolbox.ecotoolbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentPageAdapter extends FragmentPagerAdapter {
    HogarFragment hf=null;
    TransporteFragment traf=null;
    TotalFragment totf=null;
    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                    if (hf == null)
                        hf = new HogarFragment();
                return hf;
            case 1:
                if (traf == null)
                    traf = new TransporteFragment();
                return traf;
            case 2:
                if (totf == null)
                    totf = new TotalFragment();
                return totf;
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }
}
