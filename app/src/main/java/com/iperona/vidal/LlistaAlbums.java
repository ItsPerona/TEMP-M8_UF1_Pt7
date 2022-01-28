package com.iperona.vidal;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LlistaAlbums extends AppCompatActivity {

    private RequestQueue queue = null;
    private final List<Album> elements = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_grup);

        Intent intent = getIntent();
        final String URL = intent.getStringExtra("URL");
        final String artist = intent.getStringExtra("artist");

        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(elements, URL);

        RecyclerView viewLlista = findViewById(R.id.viewLlista);
        viewLlista.setAdapter(adapter);
        viewLlista.setHasFixedSize(true);

        showAlbums(URL, artist, viewLlista);

    }

    private void showAlbums(String URL, String artist, RecyclerView viewLlista) {
        System.out.println(URL);
        if (hiHaConnexio()) {
            if (queue == null)
                queue = Volley.newRequestQueue(this);
            JsonObjectRequest request = new JsonObjectRequest(URL, null,
                    new Response.Listener<JSONObject>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                elements.clear();
                                JSONObject jsonObject = response.getJSONObject("topalbums");
                                JSONArray jsonArray = jsonObject.getJSONArray("album");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONArray images = jsonArray.getJSONObject(i).getJSONArray("image");
                                    String imagesURL = images.getJSONObject(2).getString("#text");

                                    System.out.println(jsonArray.getJSONObject(i).getString("name"));
                                    System.out.println(imagesURL);

                                    Album album = new Album(
                                            jsonArray.getJSONObject(i).getString("name"),
                                            imagesURL
                                    );
                                    elements.add(album);
                                }
                                viewLlista.getAdapter().notifyDataSetChanged();
                                System.out.println("finalizado");
                                System.out.println(elements.size());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LlistaAlbums.this, "Error en obtenir dades", Toast.LENGTH_SHORT).show();
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