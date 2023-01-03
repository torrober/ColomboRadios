package com.torrober.colomboradios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EstacionesPorRegion extends AppCompatActivity {
    private static int index;
    String url;
    ArrayList<String> radios;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new cargar().execute();
        Intent intent = getIntent();
        String ciudad = intent.getStringExtra("cityname");
        int index = Integer.parseInt(intent.getStringExtra("pos"));
        this.index = index;
        final String url = getResources().getStringArray(R.array.regiones_url)[index];
        this.url = url;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones_por_region);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Radios de " + ciudad);
    }

    public static int getIndex() {
        return index;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class cargar extends AsyncTask<String, Integer, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
        }

        protected ArrayList<String> doInBackground(String... address) {
            try {
                radios = Estaciones.obtenerEstacionesByUrl(url);
            } catch (NullPointerException ex) {

            } catch (Exception e) {
                System.out.println(e);
            }
            return radios;
        }

        protected void onProgressUpdate(Integer... values) {
        }

        protected void onPostExecute(ArrayList<String> s) {
            final ArrayList<Estacion> lista = new ArrayList<>();
            try {
                for (String radio : s) {
                    lista.add(new Estacion(radio.split(",")[1], radio.split(",")[3], radio.split(",")[0]));
                    //System.out.println(radio);
                }
            } catch (NullPointerException ex) {
                Toast error = Toast.makeText(getApplicationContext(), "Ha ocurrido un error", Toast.LENGTH_LONG);
                error.show();
                finish();
            }
            ListView lv = (ListView) findViewById(R.id.listaRadios);
            CustomAdapter arrayAdapter = new CustomAdapter(EstacionesPorRegion.this, lista);
            lv.setAdapter(arrayAdapter);
            mQueue = Volley.newRequestQueue(getApplicationContext());
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String e = radios.get(i);
                    String id = "https://api.webrad.io/data/streams/11/"+e.split(",")[2];
                    ReproducirEstacion(id);
                    Toast toastId = Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT);
                    toastId.show();
                }
            });
        }

    }
    private void ReproducirEstacion (String url){
        final MediaPlayer mediaPlayer = new MediaPlayer();
        final String[] urlStation = {""};
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject res = response.getJSONObject("result");
                            JSONArray jsonArray = res.getJSONArray("streams");
                            String title = res.getJSONObject("station").getString("title");
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject radio = jsonArray.getJSONObject(i);
                                String mediaType = radio.getString("mediaType");
                                String stream = radio.getString("url");
                                if(mediaType.equals("AAC")|| mediaType.equals("MP3")){
                                    Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                                    intent.putExtra("url", stream);
                                    intent.putExtra("title", title);
                                    startActivity(intent);
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}