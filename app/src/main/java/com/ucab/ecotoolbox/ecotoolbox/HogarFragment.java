package com.ucab.ecotoolbox.ecotoolbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HogarFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //setRetainInstance(true);
        return inflater.inflate(R.layout.calc_fragment_hogar, container,false);
    }
}
