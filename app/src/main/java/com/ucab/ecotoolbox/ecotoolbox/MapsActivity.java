package com.ucab.ecotoolbox.ecotoolbox;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Provider;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private Location getLocation(){
        Location myLocation = null;
      try {
          LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
          Log.d("Log", locationManager.toString());

          // Create a criteria object to retrieve provider
          Criteria criteria = new Criteria();
//
          // Get the name of the best provider
          String provider = locationManager.getBestProvider(criteria, true);

          Log.d("Log", provider.toString());

          // Get Current Location
          myLocation = locationManager.getLastKnownLocation(provider);

          Log.d("Log", myLocation.toString());
      }catch(Exception e){
              Log.d("Error", "Actica GPS e internet");
      }
      return myLocation;

    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);
/*dese aqui*/
        // Get LocationManager object from System Service LOCATION_SERVICE
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
////
//        Log.d("Log",locationManager.toString());
//
//        // Create a criteria object to retrieve provider
//        Criteria criteria = new Criteria();
////
//        // Get the name of the best provider
//        String provider = locationManager.getBestProvider(criteria, true);
//
//        Log.d("Log", provider.toString());
//
//        // Get Current Location
//        Location myLocation = locationManager.getLastKnownLocation(provider);
//
//        Log.d("Log", myLocation.toString());

/* hasta aqui en una funcion getlocation que evuelva Location */

        Location myLocation = getLocation();
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                // Creating an instance of MarkerOptions
                MarkerOptions markerOptions = new MarkerOptions();
                // Setting position for the marker
                markerOptions.position(point);
                // Setting custom icon for the marker
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.basura));
                // Setting title for the infowindow
                markerOptions.title("Basura");
                //markerOptions.title(point.latitude + ","+point.longitude);

                // Adding the marker to the map
                mMap.addMarker(markerOptions);
            }
        });

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));


        LatLng coordinate = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 12);
        mMap.animateCamera(yourLocation);
    }
}
