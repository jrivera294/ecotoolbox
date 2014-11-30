package com.ucab.ecotoolbox.ecotoolbox;

/**
 * Created by Toshiba PC on 11/25/2014.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//prueba
public class Rssfeed extends ActionBarActivity{

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rss);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentrss, new PlaceholderFragmentRss())
                    .commit();
        }
    }

    public static class PlaceholderFragmentRss extends Fragment {

        public PlaceholderFragmentRss() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rss, container, false);
            return rootView;
        }
    }

}
