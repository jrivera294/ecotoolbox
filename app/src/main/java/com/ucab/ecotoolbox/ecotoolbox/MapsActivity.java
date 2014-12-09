package com.ucab.ecotoolbox.ecotoolbox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Provider;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }
        else{ setUpMapIfNeeded();}
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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
        boolean gpsIsEnabled;
        boolean networkIsEnabled;
        try {
            LocationManager locationManager = (LocationManager)getSystemService(getBaseContext().LOCATION_SERVICE);
                // Create a criteria object to retrieve provider
                Criteria criteria = new Criteria();
                // Get the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);
                // Get Current Location
//            myLocation = locationManager.getLast
                myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

//            }else{
//                Toast.makeText(getApplicationContext(), "Revise GPS y su conexion a internet",
//                        Toast.LENGTH_SHORT).show();
//            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Revise GPS y su conexion a internet",
                    Toast.LENGTH_SHORT).show();
            
        }
        return myLocation;

    }

    public static class PlaceholderFragmentMaps extends Fragment {

        public PlaceholderFragmentMaps() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_map, container, false);
            return rootView;
        }
    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {


        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        Location myLocation = getLocation();

        if(myLocation==null){
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error. Revise GPS y su conexion a internet",
                    Toast.LENGTH_SHORT).show();

        }
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();
//        ObtenerPuntos op = new ObtenerPuntos((float)latitude,(float)longitude,5);
//        op.execute();
        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(point);

                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.basura));

                markerOptions.title("Basura");

                mMap.addMarker(markerOptions);

                FragmentoSubirFoto subirfoto = new FragmentoSubirFoto();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.map, subirfoto) // f2_container is your FrameLayout container
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));


        LatLng coordinate = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 12);
        mMap.animateCamera(yourLocation);
    }

    public class ObtenerPuntos  extends AsyncTask<String,Integer,JSONObject> {

        private Integer radio;
        private Float lon;
        private Float lat;
        public ObtenerPuntos(float lat , float lon , int radio){
            this.radio = radio;
            this.lon = lon;
            this.lat = lat;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url = "http://api2-ecotoolbox.rhcloud.com/api/nearbyPoints/"+this.lat.toString()+"/"+this.lon.toString()+"/"+this.radio.toString();
            BufferedReader in = null;
            JSONObject respuestaPunto = null;
            InputStream is = null;
            try
            {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = client.execute(request);
                HttpEntity entidad = response.getEntity();
                is = entidad.getContent();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            try{// PROCESO LA RESPUESTA DEL SERVER

                BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line = "0";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();

                String result=sb.toString();
                respuestaPunto  = new JSONObject(result); //CREO EL JSONObject para luego obtener el url


            }catch(Exception e){

            }
            return respuestaPunto;
        }


        protected void onPostExecute(JSONObject respuestaPuntos) {
            super.onPostExecute(respuestaPuntos);
            try {
                String status = respuestaPuntos.getString("status");
                if (status=="200"){
                    JSONArray arregloPtos = respuestaPuntos.getJSONArray("data");
                        for (int i = 0 ; arregloPtos.getJSONObject(i)!=null ; i++){// ojo
                            MarkerOptions markerOptions = new MarkerOptions();
                            double lat = arregloPtos.getJSONObject(i).getDouble("lat");
                            double lon = arregloPtos.getJSONObject(i).getDouble("lng");

                            markerOptions.position(new LatLng(lat, lon));

                            switch(arregloPtos.getJSONObject(i).getInt("categoria")){
                                case 0: markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.basura));
                                        markerOptions.title("Basura");
                                        break;
                                case 1: markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_evento));
                                        markerOptions.title("Evento");
                                        break;
                                case 2: markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_reciclaje));
                                        markerOptions.title("Reciclar");
                                        break;
                            }

                           // markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.basura));



                            mMap.addMarker(markerOptions);


                    }

                }else if (status=="404")
                {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error obteniendo puntos del servidor",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
