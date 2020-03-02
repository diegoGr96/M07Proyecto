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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    //Activity que se muestra cada ve que pulsamos en un juego (Elemento del Recycler View).

    FirebaseDatabase database;
    DatabaseReference refClickedTema;
    DatabaseReference respuestasReference;

    private RecyclerView mRecyclerView;
    private RespuestasAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshTemas;

    private FloatingActionButton fab;
    private Map<String, HashMap<String, Object>> mapaRespuestas;
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

        database = FirebaseDatabase.getInstance();

        fab = findViewById(R.id.addRespuesta);

        mRecyclerView = findViewById(R.id.recyclerRespuestas);
        swipeRefreshTemas = findViewById(R.id.swipeRefreshRespuestas);
        swipeRefreshTemas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //contadorConsulta = contadorTemas - 1;
                reiniciarLista();
                swipeRefreshTemas.setRefreshing(false);
            }
        });

        // Set the Layout Manager.
        //Log.d("A", mRecyclerView + "   AAAAAAAAAAAAAAAAAa");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        idTema = getIntent().getIntExtra("ID_TEMA", -1);
        tituloTema = getIntent().getStringExtra("TITLE");
        nickAutor = getIntent().getStringExtra("USER");
        cuerpoAutor = getIntent().getStringExtra("BODY");
        uidAutor = getIntent().getStringExtra("UID");

        temaAutor = new Respuesta(tituloTema, nickAutor, cuerpoAutor);
        listaRespuestas = new ArrayList<>();
        listaRespuestas.add(temaAutor);

        initializeData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRespuesta = new Intent(getApplicationContext(), AddRespuesta.class);
                iRespuesta.putExtra("ID_TEMA", idTema);
                iRespuesta.putExtra("UID_USER", uidAutor);
                iRespuesta.putExtra("NICK_USER", nickAutor);
                startActivityForResult(iRespuesta, 1);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        reiniciarLista();
    }

    private void initializeData() {
        respuestasReference = database.getReference("Temas/Tema_" + idTema + "/Respuestas");
        Query myQuery = respuestasReference.orderByChild("idRespuesta").startAt(0).endAt(10);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapaRespuestas = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (mapaRespuestas == null) {

                } else {
                    //listaRespuestas = new ArrayList<>();
                    for (int i = 0; i < mapaRespuestas.size(); i++) {
                        Respuesta respuesta = Respuesta.convertRespuesta(mapaRespuestas.get("respuesta_" + i));
                        listaRespuestas.add(respuesta);
                    }

                    //System.out.println("Hola -- " + listaRespuestas);
                    //Collections.reverse(temasList);
                    // Initialize the adapter and set it to the RecyclerView.
                    // mAdapter = new RespuestasAdapter(getApplicationContext(), listaRespuestas);
                    // mRecyclerView.setAdapter(mAdapter);
                }
                mAdapter = new RespuestasAdapter(getApplicationContext(), listaRespuestas);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });
    }

    private void reiniciarLista(){
        listaRespuestas.clear();
        listaRespuestas.add(temaAutor);
        initializeData();
    }
}
