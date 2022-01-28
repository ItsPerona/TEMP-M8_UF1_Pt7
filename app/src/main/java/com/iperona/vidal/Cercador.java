package com.iperona.vidal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cercador extends AppCompatActivity implements View.OnClickListener {

    EditText inputSearch;
    public int idSearch;
    String URL;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cercador);

        Intent intent = getIntent();
        idSearch = intent.getIntExtra("idSearch", 0);

        inputSearch = findViewById(R.id.inSearch);
        done = findViewById(R.id.done);

        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int idSearch = this.idSearch;
        String param = inputSearch.getText().toString();

        String params[] = param.split(" ");

        StringBuilder sb = new StringBuilder(params[0]);
        for (int i = 1; i < params.length; i++) {
            sb.append("%20" + params[i]);
        }

        if (idSearch == 0) {
            URL = "https://www.omdbapi.com/?t=" + sb + "&apikey=d368dd8a";
            Intent intent = new Intent(this, MostraPeli.class);
            intent.putExtra("URL", URL);
            startActivity(intent);
        } else {
            URL = "https://ws.audioscrobbler.com/2.0/?method=artist.gettopalbums&artist=" + sb + "&api_key=8a263c45ed98a92790c525a6ee0d5c76&format=json";
            Intent intent = new Intent(this, LlistaAlbums.class);
            intent.putExtra("URL", URL);
            intent.putExtra("artist", sb.toString());
            startActivity(intent);
        }
    }
}