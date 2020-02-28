package com.diego.m07proyecto;

//import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diego.m07proyecto.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailActivity extends AppCompatActivity {
    //Activity que se muestra cada ve que pulsamos en un juego (Elemento del Recycler View).

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refClickedTema;

    private TextView temaTitle;
    private TextView temaUser;
    private TextView temaCuerpo;
    private ImageView categoryImage;
    private int idTema = -1;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fab = findViewById(R.id.addRespuesta);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        temaTitle = findViewById(R.id.txtTitle);
        temaUser = findViewById(R.id.txtAutor);
        temaCuerpo = findViewById(R.id.txtCuerpo);
        categoryImage = findViewById(R.id.categoryImageDetail);

        idTema = getIntent().getIntExtra("ID_TEMA", -1);

        refClickedTema = database.getReference("Temas/"+idTema);

        refClickedTema.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temaTitle.setText(String.valueOf(dataSnapshot.child("titulo").getValue()));
                temaUser.setText(String.valueOf(dataSnapshot.child("nickAutor").getValue()));
                temaCuerpo.setText(String.valueOf(dataSnapshot.child("cuerpo").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Glide.with(this).load(getIntent().getIntExtra("image_resource", 0))
                .into(categoryImage);
    }
}
