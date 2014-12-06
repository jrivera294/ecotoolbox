package com.ucab.ecotoolbox.ecotoolbox;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;


public class Home extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.intro_tutorial);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isfirstrun",true);
//
        if(isFirstRun){
            setContentView(R.layout.intro_tutorial);
            VideoView video = (VideoView) findViewById(R.id.videoView);
            video.setVideoPath("android.resource://"+getPackageName()+"/"+ R.drawable.example);
            video.setMediaController(new MediaController(this));
            video.requestFocus();
            video.start();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_tuto, new PlaceholderFragmentV())
                        .commit();
            }
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun",false).commit();

            video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    setContentView(R.layout.activity_home);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new PlaceholderFragment())
                            .commit();
                }
            });

        }
        else {
            setContentView(R.layout.activity_home);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new PlaceholderFragment())
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static class PlaceholderFragmentV extends Fragment {

        public PlaceholderFragmentV() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.intro_tutorial, container, false);
            return rootView;
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }
    }

//   Click RSS Feed
     public void onClickF(View v) {
         Rssfeed.PlaceholderFragmentRss f4 = new Rssfeed.PlaceholderFragmentRss();
         FragmentManager fragmentManager = getSupportFragmentManager();
         FragmentTransaction ft = fragmentManager.beginTransaction();
         ft.replace(R.id.container, f4); // f2_container is your FrameLayout container
         ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
         ft.addToBackStack(null);
         ft.commit();
    }
//  Click Calculadora
    public void onClickC(View v) {
        setContentView(R.layout.fragment_calc);
    }
//  Click UV
    public void onClickUV(View v) {
        FragmentoSubirFoto subirfoto = new FragmentoSubirFoto();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container,subirfoto); // f2_container is your FrameLayout container
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
//  Click mapa
    public void onClickMaps(View v) {
        Intent mainIntent = new Intent(this, MapsActivity.class);
        startActivity(mainIntent);
    }

}

