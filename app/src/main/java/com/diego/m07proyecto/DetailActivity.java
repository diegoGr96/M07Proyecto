package com.diego.m07proyecto;

//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    //Activity que se muestra cada ve que pulsamos en un juego (Elemento del Recycler View).

    FirebaseDatabase database;
    DatabaseReference refClickedTema;
    DatabaseReference respuestasReference;

    private RecyclerView mRecyclerView;
    private RespuestasAdapter mAdapter;

    private FloatingActionButton fab;
    private HashMap<String,HashMap<String,Object>> mapaRespuestas;
    private List<Respuesta> listaRespuestas;

    private ImageView categoryImage;
    private int idTema = -1;
    private String tituloTema;
    private String nickAutor;
    private String cuerpoAutor;
    private String uidAutor;

    private Respuesta temaAutor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

          database= FirebaseDatabase.getInstance();

        fab = findViewById(R.id.addRespuesta);

        mRecyclerView = findViewById(R.id.recyclerRespuestas);

        // Set the Layout Manager.
        Log.d("A", mRecyclerView + "   AAAAAAAAAAAAAAAAAa");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRespuesta = new Intent(getApplicationContext(), AddRespuesta.class);
                startActivity(iRespuesta);
            }
        });

        idTema = getIntent().getIntExtra("ID_TEMA", -1);
        tituloTema = getIntent().getStringExtra("TITLE");
        nickAutor = getIntent().getStringExtra("USER");
        cuerpoAutor = getIntent().getStringExtra("BODY");
        //uidAutor = getIntent().getStringExtra("UID");

        temaAutor = new Respuesta(tituloTema,nickAutor,cuerpoAutor);
        listaRespuestas = new ArrayList<>();
        listaRespuestas.add(temaAutor);

        respuestasReference = database.getReference("Temas/"+idTema);
        Query myQuery = respuestasReference.orderByChild("idRespuesta").startAt(0).endAt(10);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapaRespuestas = (HashMap<String,HashMap<String,Object>>) dataSnapshot.getValue();
                if (mapaRespuestas == null){

                }else {
                    /*
                    temasList = new ArrayList<>();
                    for(int i =0; i < temasListh.size();i++){
                        Tema tema = Tema.convertTema(temasListh.get(i));
                        temasList.add(tema);
                    }
                    System.out.println("Hola -- "+temasList);
                    Collections.reverse(temasList);
                    // Initialize the adapter and set it to the RecyclerView.
                    mAdapter = new HistoriasAdapter(getContext(), temasList);
                    mRecyclerView.setAdapter(mAdapter);

                     */
                }
                mAdapter = new RespuestasAdapter(getApplicationContext(), listaRespuestas);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });

        //categoryImage = findViewById(R.id.categoryImageDetail);


        //Glide.with(this).load(getIntent().getIntExtra("image_resource", 0))
        //        .into(categoryImage);
    }
}
