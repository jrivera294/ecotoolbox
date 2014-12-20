package com.ucab.ecotoolbox.ecotoolbox;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class Splash extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lanzarThread();
    }
    
    private void lanzarThread(){
    	Thread timer = new Thread(){
    		public void run(){
    			try {
					sleep(2000); //espera de 2 segundos
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					Intent intent = new Intent(Splash.this, Principal.class);
					startActivity(intent);
				}
    		}
    	};
    	
    	timer.start();
    }

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
 

    
}
