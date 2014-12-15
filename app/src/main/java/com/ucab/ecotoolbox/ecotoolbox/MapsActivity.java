package com.ucab.ecotoolbox.ecotoolbox;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Provider;

public class MapsActivity extends FragmentActivity{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Bitmap myBitmap;
    private String foto;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if(status!= ConnectionResult.SUCCESS){ // Google Play Services are not available

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }

        else{
            setUpMapIfNeeded();
        }
    }

//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_update:
                Location myLocation = getLocation();
                ObtenerPuntos op = new ObtenerPuntos((float)myLocation.getLatitude(),(float)myLocation.getLongitude(),5);
                op.execute();
                Toast.makeText(getApplicationContext(), "Actualizando",
                        Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            LocationListener locationListener = new LocationListener(){
                public void onLocationChanged(Location location){
                    float lati = (float) location.getLatitude();
                    float lon = (float) location.getLongitude();

                    ObtenerPuntos op = new ObtenerPuntos(lati,lon,1);
                    op.execute();
                }
                public void onStatusChanged(String provider, int status, Bundle extras){
                }
                public void onProviderEnabled(String provider){
                }
                public void onProviderDisabled(String provider){
                }

            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 35000, 5,locationListener);
                // Create a criteria object to retrieve provider
                Criteria criteria = new Criteria();
                // Get the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);
                // Get Current Location
//            myLocation = locationManager.getLast
                myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(myLocation==null){
                    myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
//            }else{
//                Toast.makeText(getApplicationContext(), "Revise GPS y su conexion a internet",
//                        Toast.LENGTH_SHORT).show();
//            }

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Revise GPS y su conexion a internet",
                    Toast.LENGTH_LONG).show();
            
        }
        return myLocation;

    }

    private void setUpMap() {
        MarkerOptions markerOptions;

        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        Location myLocation = getLocation();

        if(myLocation==null){
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error. Revise GPS y su conexion a internet",
                    Toast.LENGTH_LONG).show();
            return;
        }
        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        final double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        final double longitude = myLocation.getLongitude();


        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        ObtenerPuntos op = new ObtenerPuntos((float)latitude,(float)longitude,5);
        op.execute();
        // Zoom in the Google Map
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                MarkerOptions markerOptions = new MarkerOptions();

                markerOptions.position(point);

                mMap.addMarker(markerOptions);
                FragmentoSubirFoto subirfoto = new FragmentoSubirFoto();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.map, subirfoto) // f2_container is your FrameLayout container
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
                subirfoto.setLon((float)point.longitude);
                subirfoto.setLat((float)point.latitude);


            }

        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("hola", "Asfg");
                String snip =  marker.getSnippet();
                String id = snip.substring(0,snip.length());
                Log.d("idS", id);
                ObtenerDetalle opi = new ObtenerDetalle(Integer.parseInt(id));
                opi.execute();
                return false;
            }
        });
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

        LatLng coordinate = new LatLng(latitude, longitude);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 12);
        mMap.animateCamera(yourLocation);
    }

     public class ObtenerDetalle  extends AsyncTask<String,Integer,JSONObject> {

        private Integer id;
        public ObtenerDetalle(int id) {
            this.id = id;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject respuesta = null;
//            if (isOnline()) {
            String url = "http://api2-ecotoolbox.rhcloud.com/api/points/" + this.id.toString();
            BufferedReader in = null;
            InputStream is = null;

            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(url));
                HttpResponse response = client.execute(request);
                HttpEntity entidad = response.getEntity();
                is = entidad.getContent();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            try {// PROCESO LA RESPUESTA DEL SERVER

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "0";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();

                String result = sb.toString();
                respuesta = new JSONObject(result); //CREO EL JSONObject para luego obtener el url
                JSONObject arregloPtosD = respuesta.getJSONObject("data");
                foto= arregloPtosD.getString("foto");

            } catch (Exception e) {
                Log.d("Problema Servidor: Get", "Servidor offline");
                Log.d("Prob", e.toString());
            }

            try {
                URL urlImg = new URL(foto);
                HttpURLConnection connection = (HttpURLConnection) urlImg.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
//            }
//            else{
//                Toast.makeText(getApplicationContext(), "Revise su conexión a internet",
//                        Toast.LENGTH_SHORT).show();
//            }
            return respuesta;
        }

        protected void onPostExecute(JSONObject respuesta) {
            super.onPostExecute(respuesta);
            try {
                String status = respuesta.getString("status");
                if (status.compareTo("200")== 0){
                    JSONObject arregloPtosD = respuesta.getJSONObject("data");
                    Log.d("descripcion",arregloPtosD.getString("descripcion"));
//                        MarkerOptions markerOptions = new MarkerOptions();
                        final String nomb = arregloPtosD.getString("nombre");
                        final String urlImg = arregloPtosD.getString("foto");
                        final String descrip = arregloPtosD.getString("descripcion");

                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                // Use default InfoWindow frame
                                @Override
                                public View getInfoWindow(Marker arg0) {
                                    return null;
                                }

                                // Defines the contents of the InfoWindow
                                @Override
                                public View getInfoContents(Marker arg0) {

                                    // Getting view from the layout file info_window_layout

                                    View v = getLayoutInflater().inflate(R.layout.fragment_map, null);
                                    TextView nombre = (TextView) v.findViewById(R.id.nombreI);
                                    TextView descripv = (TextView)v.findViewById(R.id.descripI);
                                    nombre.setText("Nombre:"+nomb);
                                    descripv.setText("Descripcion:"+descrip);
                                    ImageView imageView = (ImageView)v.findViewById(R.id.imagen);
                                    imageView.setImageBitmap(myBitmap);
//                                    new DownloadImageTask(imageView).execute(urlImg);
                                    return v;

                                }
                            });



                }else if (status.compareTo("404")== 0)
                {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error obteniendo puntos del servidor",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("try obtener detalle",e.toString());
            }
        }
    }



    public class ObtenerPuntos  extends AsyncTask<String,Integer,JSONObject> {

        private Integer radio;
        private Float lon;
        private Float lat;

        public ObtenerPuntos(float lat, float lon, int radio) {
            this.radio = radio;
            this.lon = lon;
            this.lat = lat;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject respuestaPunto = null;
//            if (isOnline()) {
                String url = "http://api2-ecotoolbox.rhcloud.com/api/nearbyPoints/" + this.lat.toString() + "/" + this.lon.toString() + "/" + this.radio.toString();
                BufferedReader in = null;
                InputStream is = null;
                try {

                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(url));
                    HttpResponse response = client.execute(request);
                    HttpEntity entidad = response.getEntity();
                    is = entidad.getContent();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {// PROCESO LA RESPUESTA DEL SERVER

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = "0";

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();

                    String result = sb.toString();
                    respuestaPunto = new JSONObject(result); //CREO EL JSONObject para luego obtener el url


                } catch (Exception e) {
                    Log.d("Problema Servidor", "Servidor offline");
                }

//            }
//            else{
//                Toast.makeText(getApplicationContext(), "Revise su conexión a internet",
//                        Toast.LENGTH_SHORT).show();
//            }
            return respuestaPunto;
        }




        protected void onPostExecute(JSONObject respuestaPuntos) {
            super.onPostExecute(respuestaPuntos);
            try {
                String status = respuestaPuntos.getString("status");
                    if (status.compareTo("200")== 0){
                    JSONArray arregloPtos = respuestaPuntos.getJSONArray("data");

                        for (int i = 0 ; i<arregloPtos.length(); i++){// ojo

                            MarkerOptions markerOptions = new MarkerOptions();
                            double lat = arregloPtos.getJSONObject(i).getDouble("lat");
                            double lon = arregloPtos.getJSONObject(i).getDouble("lng");

                            markerOptions.position(new LatLng(lat, lon));

                            Log.d("ID",arregloPtos.getJSONObject(i).getString("id"));
                            switch(arregloPtos.getJSONObject(i).getInt("categoria")){
                                case 0: markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_basura));
                                        markerOptions.title("Basura").snippet(arregloPtos.getJSONObject(i).getString("id".toString()));

                                        break;
                                case 1: markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_evento));
                                        markerOptions.title("Evento").snippet(arregloPtos.getJSONObject(i).getString("id".toString()));
                                        break;
                                case 2: markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_reciclaje));
                                        markerOptions.title("Reciclar").snippet(arregloPtos.getJSONObject(i).getString("id".toString()));
                                        break;
                            }

                            mMap.addMarker(markerOptions);

                    }

                }else if (status.compareTo("404")== 0)
                {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error obteniendo puntos del servidor",
                            Toast.LENGTH_LONG).show();
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("try obtener puntos",e.toString());
            }
        }
    }
}
