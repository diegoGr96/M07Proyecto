package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MisTemas extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference loadContador;
    FirebaseAuth mAuth;
    private String uidAutor;

    private RecyclerView mRecyclerView;
    private HistoriasAdapter mAdapter;

    //private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshMisTemas;

    private Map<String, HashMap<String, Object>> temasListh;
    private List<Tema> temasList;
    private int inicioConsulta;
    private int finalConsulta;
    private int contadorTemas = -1;

    private boolean firstAttempt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_temas);


        temasList = new ArrayList<>();

        firstAttempt = true;
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        uidAutor = mAuth.getCurrentUser().getUid();

        loadContador = database.getReference("contador");
        loadContador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contadorTemas = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                /*finalConsulta=contadorTemas-1;
                inicioConsulta = finalConsulta-9;*/
                //contadorConsulta = contadorTemas-1;
                //System.out.println("Contador es: " + contadorTemas);
                if (firstAttempt) {
                    initializeData();
                    firstAttempt = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //System.out.println(temasList);

        swipeRefreshMisTemas = findViewById(R.id.swipeRefreshMisTemas);
        swipeRefreshMisTemas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finalConsulta = contadorTemas - 1;
                inicioConsulta = finalConsulta - 9;
                temasList.clear();
                initializeData();
                swipeRefreshMisTemas.setRefreshing(false);
            }
        });

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerViewMisTemas);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new HistoriasAdapter(getApplicationContext(), temasList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initializeData() {
        //System.out.println("Contador tema " + contadorTemas);
        while (contadorTemas == -1) ;
        DatabaseReference dbRef = database.getReference("Temas");
        Query myQuery = dbRef.orderByChild("uidAutor").equalTo(uidAutor);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                temasListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (temasListh == null) {

                } else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Tema tema = Tema.convertTema(temasListh.get(snapshot.getKey()));
                        temasList.add(tema);
                    }
                    mAdapter.notifyDataSetChanged();
                    //System.out.println("Hola -- " + temasList);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });
    }
}
