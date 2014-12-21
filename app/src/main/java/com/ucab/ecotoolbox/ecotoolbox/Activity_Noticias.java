package com.ucab.ecotoolbox.ecotoolbox;

/**
 * Created by Vicky on 18/12/2014.
 */

        import java.util.ArrayList;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ListView;
        import android.widget.Toast;

public class Activity_Noticias extends Activity {

    public ArrayList<Noticia> Array_Noticias = new ArrayList<Noticia>();
    private Noticias_Adapter adapter;
    String feed;

    private String URL;
                            // http://www.mariano-bueno.com/feed/ <-- Feed internacional BELLO BELLO
                            // http://www.boletinecologico.org/feed/ <-- Feed EXCELENTE (pero es de Nicaragua)
                            // http://noticias.masverdedigital.com/feed/ <-- Feed internacional (Feed viene cn ese descuadre del content).
                            // http://venezuelaverde.com/feed//   <--feed vzla (Leo Di Caprio)
                            // http://www.ecologiayconservacion.com/feed/ <-- Este da nullPointer cuando deslizas
                                                                            // el dedo pero te abre las noticias
                            //http://feeds.feedburner.com/elblogverde?format=xml <-- Feed fino, pero hay unos videos
                                                                                    // q no se muestran
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        Bundle bundle = getIntent().getExtras();


        if(bundle.getString("in").compareTo("i0")==0){
            URL = "http://www.mariano-bueno.com/feed/";
        }
        else{
            if(bundle.getString("in").compareTo("i1")==0){
                URL = "http://www.boletinecologico.org/feed/";
            }
            else {
                if (bundle.getString("in").compareTo("i2") == 0) {
                    URL = "http://noticias.masverdedigital.com/feed/";
                }
                else{
                    if(bundle.getString("in").compareTo("i3")==0){
                        URL = "http://feeds.feedburner.com/elblogverde?format=xml";
                    }
                    else{
                        URL = "http://venezuelaverde.com/feed/";
                    }
                }
            }

        }
        rellenarNoticias();

    }

    private void inicializarListView() {
        lista = (ListView) findViewById(R.id.noticias_listview);
        adapter = new Noticias_Adapter(this, Array_Noticias);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(Activity_Noticias.this, Activity_Articulo.class);
                intent.putExtra("parametro", Array_Noticias.get(arg2));
                //intent.putExtra("parametro", "Artículo número "+(arg2+1));
                startActivity(intent);
            }
        });
    }

    private void rellenarNoticias() {
        if (isOnline()) {
            new DescargarNoticias(getBaseContext(), URL).execute();
        }

    }

    private class DescargarNoticias extends AsyncTask<String, Void, Boolean> {

        private String feedUrl;
        private Context ctx;

        public DescargarNoticias(Context c, String url) {
            this.feedUrl = url;
            this.ctx = c;
        }

        @Override
        protected Boolean doInBackground(final String... args) {
            XMLParser parser = new XMLParser(feedUrl, getBaseContext());
            Array_Noticias = parser.parse();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                try {
                    inicializarListView();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(ctx, "Error en la lectura", Toast.LENGTH_LONG)
                        .show();
            }
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
}