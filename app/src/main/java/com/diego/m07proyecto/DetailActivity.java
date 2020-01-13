package com.diego.m07proyecto;

//import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    //Activity que se muestra cada ve que pulsamos en un juego (Elemento del Recycler View).
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView gamesTitle = findViewById(R.id.titleDetail);
        ImageView gamesImage = findViewById(R.id.gamesImageDetail);

        gamesTitle.setText(getIntent().getStringExtra("title"));
        Glide.with(this).load(getIntent().getIntExtra("image_resource", 0))
                .into(gamesImage);
    }
}
