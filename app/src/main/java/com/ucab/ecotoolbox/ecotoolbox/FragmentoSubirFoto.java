package com.ucab.ecotoolbox.ecotoolbox;

import android.app.Activity;
//import android.app.Fragment;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 02/12/2014.
 */
public class FragmentoSubirFoto extends Fragment{

        private Button botonCamara;
        private Button botonEnviar;
        private static final int TAKE_PICTURE = 1;
        private ImageView iv;
        private ImageButton ib;
        private Uri imageUri;
        private String imagePath;
        private String[] opcTipo = new String[]{"Basura","Evento","Reciclaje"};
        public static String dataFromAsyncTask="no cambio";

        //variables a enviale al servidor
        private float lon;
        private float lat;
        private String nombre;
        private String desc;
        private int categoria;


        public void setLon(float nuevaLongitud){
            this.lon = nuevaLongitud;
        }

        public void setLat(float nuevaLatitud){
            this.lat = nuevaLatitud;
        }

        public float getLon(){
            return this.lon;
        }

        public float getLat(){
            return this.lat;
        }

        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            if (savedInstanceState != null) {
                imagePath = savedInstanceState.getString("path");
                nombre = savedInstanceState.getString("nombre");
                desc =  savedInstanceState.getString("descripcion");
                lon = savedInstanceState.getFloat("lon");
                lat = savedInstanceState.getFloat("lat");
            }

        }

        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("nombre",nombre);
            outState.putString("descripcion",desc);
            outState.putFloat("lon",this.lon);
            outState.putFloat("lat",this.lat);
            Spinner sp = (Spinner)getActivity().findViewById(R.id.spTipo);
            outState.putInt("categoria",sp.getSelectedItemPosition());
            outState.putString("path",imagePath);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view;
            view = inflater.inflate(R.layout.fragment_subir_img,container,false);

            //creo el spinner con las categorias de
            Spinner sp = (Spinner)view.findViewById(R.id.spTipo);
            ArrayAdapter aaTipo = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,opcTipo);
            sp.setAdapter(aaTipo);
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                             @Override
                                             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                             }

                                             @Override
                                             public void onNothingSelected(AdapterView<?> parent) {

                                             }
                                         }
            );
            //coloco la imagen previa a mostrar
            // = (ImageView)view.findViewById(R.id.ivAhri);
      //      ib = (ImageButton)view.findViewById(R.id.ibCamara);

            ib = (ImageButton)view.findViewById(R.id.ibCamara);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tomarFoto(v);
                }
            });

            if (imagePath!=null){
                Bitmap bm = getBitmapFoto(imagePath);

                ib.setImageBitmap(bm);
            }
            botonEnviar = (Button)view.findViewById(R.id.bEnviarPto);
            botonEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText etNombre = (EditText) getActivity().findViewById(R.id.etNombre);
                    EditText etDesc = (EditText) getActivity().findViewById(R.id.etDescripcion);
                    Spinner spTipo = (Spinner) getActivity().findViewById(R.id.spTipo);


                    nombre = etNombre.getText().toString();
                    desc = etDesc.getText().toString();
                    categoria = spTipo.getSelectedItemPosition();

                    SubirImagen ta = new SubirImagen(lon,lat,nombre,desc,categoria,imagePath);

                    ta.execute("https://api.cloudinary.com/v1_1/dvksfej4j/image/upload");

                }
            });


            return view;

        }

        protected void tomarFoto(View v){
            Context ctx = getActivity();
            PackageManager pm = ctx.getPackageManager();
            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)== false){
                Toast.makeText(ctx, "Este dispositivo no tiene camara", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent takePictureIntent  = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

            File foto = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"FOTO_ECOTOLBOX.jpg");
            this.imageUri = Uri.fromFile(foto);
            this.imagePath = imageUri.getPath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri);
            startActivityForResult(takePictureIntent,TAKE_PICTURE);
        }

        public void onActivityResult(int requestCode , int resultCode , Intent data){
            super.onActivityResult(requestCode,resultCode,data);

            if (resultCode == Activity.RESULT_OK) {
                Bitmap rotatedBitmap = getBitmapFoto(imagePath);
                if (rotatedBitmap != null)
                    ib.setImageBitmap(rotatedBitmap);
            }
        }

        public Bitmap getBitmapFoto(String rutaImagen){
            Bitmap b=null;
            if (rutaImagen!=null) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = 4;
                bmOptions.inPurgeable = true;

                Bitmap bm = BitmapFactory.decodeFile(rutaImagen, bmOptions);
                Matrix matrix = new Matrix();
                matrix.postRotate(0);
                b = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            }

            return b;
        }

        public class SubirImagen  extends AsyncTask<String,Integer,JSONObject> {
            private Float lon;
            private Float lat;
            private String nombre;
            private String desc;
            private Integer categoria;
            private String imagePath;


            public SubirImagen(float lon, float lat, String nombre,String desc,int categoria,String imagePath) {
                this.lon = lon;
                this.lat = lat;
                this.nombre = nombre;
                this.desc = desc;
                this.categoria = categoria;
                this.imagePath = imagePath;

        }

            public boolean isOnline() {
                ConnectivityManager cm =
                        (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnected()) {
                    return true;
                }
                return false;
            }

            @Override
            protected JSONObject doInBackground(String... params) {
                //EditText etDesc = (EditText) getActivity().findViewById(R.id.etDescripcion);
                //etDesc.setText("Exitoo");
                InputStream is = null;
                StringBuilder sb=null;
                String urlImagen = null;
                JSONObject jsonObject = null;
                Bitmap bm = getBitmapFoto(imagePath);

                if (bm != null && isOnline()) {

                    String signature=null;


                    //creo la firma con el timestamp y encriptacion aes PARA EL SERVER IMAGEN
                    Long timestamp = (Long) (System.currentTimeMillis()) / 1000L;
                    try {
                        signature = AeSimpleSHA1.SHA1("timestamp=" + timestamp.toString()+"YXudlgudgmNL_Kg33psZ3TNmZQc");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    DefaultHttpClient cliente = new DefaultHttpClient();
                    HttpPost post = new HttpPost(params[0]);

                    //PARAMETROS SERVIDOR IMAGEN
                    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    try {
                        multipartEntity.addPart("api_key", new StringBody("369932418155599"));
                        multipartEntity.addPart("timestamp", new StringBody(timestamp.toString()));
                        multipartEntity.addPart("signature", new StringBody(signature));

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    //para enviar la foto AL SERVER IMAGEN
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 75, bos);
                    byte[] data = bos.toByteArray();
                    ByteArrayBody bab = new ByteArrayBody(data, imagePath);
                    multipartEntity.addPart("file", bab);

                    post.setEntity(multipartEntity);
                    try {
                        HttpResponse respuesta = cliente.execute(post);
                        HttpEntity entidad = respuesta.getEntity();
                        is = entidad.getContent();
                        // responseText = EntityUtils.toString(entidad);


                    } catch (ClientProtocolException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try{// PROCESO LA RESPUESTA DEL SERVER
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                        sb = new StringBuilder();
                        String line = "0";

                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();

                        String result=sb.toString();
                        jsonObject = new JSONObject(result); //CREO EL JSONObject para luego obtener el url


                    }catch(Exception e){

                    }



                    try {


                        //OBTENGO EL STRING CON LA URL
                        urlImagen = jsonObject.getString("url");


                        //AHORA ENVIO LOS PUNTOS AL SERVER APII

                        HttpClient clienteAPI = new DefaultHttpClient();
                        HttpPost postAPI = new HttpPost("http://api2-ecotoolbox.rhcloud.com/api/points");
                        // Add your data
                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
                        nameValuePairs.add(new BasicNameValuePair("lat" ,this.lat.toString()));
                        nameValuePairs.add(new BasicNameValuePair("lng", this.lon.toString()));
                        nameValuePairs.add(new BasicNameValuePair("nombre", this.nombre));
                        nameValuePairs.add(new BasicNameValuePair("categoria", this.categoria.toString()));
                        nameValuePairs.add(new BasicNameValuePair("descripcion", this.desc));
                        nameValuePairs.add(new BasicNameValuePair("foto", urlImagen));

                        postAPI.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                        HttpResponse respuestaAPI = clienteAPI.execute(postAPI);
                        Toast.makeText(getActivity().getApplicationContext(), "Su punto se ha creado exitosamente",
                        Toast.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try{// PROCESO LA RESPUESTA DEL SERVER
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                    sb = new StringBuilder();
                    String line = "0";

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();

                    String result=sb.toString();
                    jsonObject = new JSONObject(result); //CREO EL JSONObject para luego obtener el url


                }catch(Exception e){

                }
                return jsonObject;
            }

            protected void onPostExecute(JSONObject jsonArray) {
                super.onPostExecute(jsonArray);
                try {
                    String urlImagen = jsonArray.getString("msg");
                   /*    jArray.getString(1);*/


                    //fragmento.dataFromAsyncTask = urlImagen;
                    // Convert String to json object*/

                    EditText etDesc = (EditText) getActivity().findViewById(R.id.etDescripcion);
                    etDesc.setText(urlImagen);


                }
                catch (Exception e){
                  //  dataFromAsyncTask = "entro al catch";
                }
            }


        }


}
