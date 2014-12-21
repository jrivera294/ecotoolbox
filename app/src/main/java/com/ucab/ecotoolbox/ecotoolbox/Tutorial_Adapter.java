package com.ucab.ecotoolbox.ecotoolbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Tutorial_Adapter extends FragmentPagerAdapter {
    public Tutorial_Adapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                return new Tutorial_Fragment1();
            case 1:
                return new Tutorial_Fragment2();
            case 2:
                return new Tutorial_Fragment3();
            case 3:
                return new Tutorial_Fragment4();
            case 4:
                return new Tutorial_Fragment5();
            default:
                break;
        }
        return null;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 5;
    }
}
