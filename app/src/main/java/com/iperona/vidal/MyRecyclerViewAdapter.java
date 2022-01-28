package com.iperona.vidal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.Objects;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private int idSearch;
    private List<Album> elements;
    private String defaultURL;

    public MyRecyclerViewAdapter(List<Album> elements, String url) {
        this.elements = elements;
        this.defaultURL = url;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View viewElement = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder, parent, false);

        return new ViewHolder(viewElement);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTxtElement().setText(elements.get(position).getAlbumName());


        String URLIMG = null;
        for (int i = 0; i < elements.size(); i++) {
            URLIMG = elements.get(i).getimatgeURL();
        }

        ImageRequest imageRequest = new ImageRequest(URLIMG, new Response.Listener<Bitmap>() {

            public void onResponse(final Bitmap response) {
                assert response != null;
                Objects.requireNonNull(holder).getImatge().setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText("ViewHolder.this, error.toString(), Toast.LENGTH_LONG").show();
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtElement;
        private ImageView imatge;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder.this.mostraElement(v);
                }
            });

            txtElement = itemView.findViewById(R.id.albumn);
            imatge = itemView.findViewById(R.id.albumimage);
        }

        private void mostraElement(View v) {

            Intent llistaAlbums = new Intent(v.getContext(), MostraAlbum.class);
            Album album = elements.get(getAdapterPosition());
            llistaAlbums.putExtra("URL", defaultURL);
            //llistaAlbums.putExtra("imatge", album.getImatgeIMG());
            v.getContext().startActivity(llistaAlbums);

        }

        public TextView getTxtElement() {
            return txtElement;
        }

        public ImageView getImatge() {
            return imatge;
        }
    }
}
