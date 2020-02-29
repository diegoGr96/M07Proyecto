package com.diego.m07proyecto.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.diego.m07proyecto.HistoriasAdapter;
import com.diego.m07proyecto.R;
import com.diego.m07proyecto.Respuesta;
import com.diego.m07proyecto.Tema;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference loadContador;

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private HistoriasAdapter mAdapter;



    SwipeRefreshLayout swipeRefreshTemas;

    private List<HashMap<String, Object>> temasListh;
    private List<Tema> temasList;
    private int contadorConsulta;
    private int contadorTemas = -1;

    private boolean firstAttempt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        firstAttempt = true;
        database = FirebaseDatabase.getInstance();
        loadContador = database.getReference("contador");
        loadContador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contadorTemas = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                contadorConsulta = contadorTemas - 11;
                System.out.println("Contador es: " + contadorTemas);
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





        System.out.println(temasList);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        swipeRefreshTemas = root.findViewById(R.id.swipeRefreshTemas);
        swipeRefreshTemas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contadorConsulta = contadorTemas - 10;
                initializeData();
                swipeRefreshTemas.setRefreshing(false);
            }
        });
        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        Log.d("A", mRecyclerView + "   AAAAAAAAAAAAAAAAAa");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }


    private void initializeData() {
        System.out.println("Contador tema "+ contadorTemas);
        while(contadorTemas == -1);
        contadorConsulta = contadorTemas;
        DatabaseReference dbRef = database.getReference("Temas");
        Query myQuery = dbRef.orderByChild("idTema").startAt(contadorConsulta-10).endAt(contadorConsulta);

        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                temasListh = (List<HashMap<String, Object>>) dataSnapshot.getValue();
                if (temasListh == null) {

                } else {
                    temasList = new ArrayList<>();
                    for (int i =contadorConsulta-10; i < temasListh.size(); i++) {
                        Tema tema = Tema.convertTema(temasListh.get(i));
                        temasList.add(tema);
                    }
                    System.out.println("Hola -- " + temasList);
                    Collections.reverse(temasList);
                    // Initialize the adapter and set it to the RecyclerView.
                    mAdapter = new HistoriasAdapter(getContext(), temasList);
                    mRecyclerView.setAdapter(mAdapter);
                }
                contadorConsulta+=10;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });
    }
}