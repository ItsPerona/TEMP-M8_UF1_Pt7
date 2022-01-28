package com.iperona.vidal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button movie, music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movie = findViewById(R.id.peli);
        music = findViewById(R.id.musica);

        movie.setOnClickListener(this);
        music.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent moveScreen = new Intent(this, Cercador.class);

        switch (view.getId()) {
            case R.id.peli:
                moveScreen.putExtra("idSearch", 0);
                startActivity(moveScreen);
                break;
            default:
                moveScreen.putExtra("idSearch", 1);
                startActivity(moveScreen);
        }
    }
}