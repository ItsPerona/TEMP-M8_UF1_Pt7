package com.iperona.vidal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MostraPeli extends AppCompatActivity {

    private RequestQueue queue = null;

    TextView titol;
    TextView actors;
    TextView sinopsis;

    private ImageView portada = null;

    private String title = null;
    private String actor = null;
    private String description = null;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_peli);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String URL = intent.getStringExtra("URL");

        sinopsis = (TextView) findViewById(R.id.textView);
        titol = (TextView) findViewById(R.id.textView2);
        actors = (TextView) findViewById(R.id.textView3);
        portada = (ImageView) findViewById(R.id.poster);

        getExtras(URL);
    }

    private void getExtras(String URL) {
        if (hiHaConnexio()) {
            if (queue == null)
                queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(URL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                title = response.getString("Title");
                                actor = response.getString("Actors");
                                description = response.getString("Plot");
                                img = response.getString("Poster");

                                ImageRequest imageRequest = new ImageRequest(img, new Response.Listener<Bitmap>() {

                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onResponse(final Bitmap response) {
                                        titol.setText("Titol" + ": " + title);
                                        actors.setText("Actors" + ": " + actor);
                                        sinopsis.setText("Sinopsis" + ": " + description);
                                        portada.setImageBitmap(response);
                                    }
                                }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MostraPeli.this, error.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });

                                RequestQueue requestQueue = Volley.newRequestQueue(MostraPeli.this);
                                requestQueue.add(imageRequest);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MostraPeli.this, "Error en obtenir dades", Toast.LENGTH_SHORT).show();
                        }
                    });
            queue.add(request);
        }
    }

    public boolean hiHaConnexio() {
        boolean resultat = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    resultat = true;
                }
            }
        } else {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                resultat = true;
            } else {
                resultat = false;
            }
        }

        return resultat;
    }
}