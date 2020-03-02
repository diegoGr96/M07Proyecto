package com.diego.m07proyecto.ui.home;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.diego.m07proyecto.CambioColorEditText;
import com.diego.m07proyecto.HistoriasAdapter;
import com.diego.m07proyecto.R;
import com.diego.m07proyecto.Respuesta;
import com.diego.m07proyecto.Tema;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

    private boolean isScrolling = false;
    private int currentItems, totaltItems, scrollOutItems;
    private LinearLayoutManager manager;


    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshTemas;

    private Map<String, HashMap<String, Object>> temasListh;
    private List<Tema> temasList;
    private int inicioConsulta;
    private int finalConsulta;
    private int contadorTemas = -1;

    private boolean firstAttempt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        temasList = new ArrayList<>();

        firstAttempt = true;
        database = FirebaseDatabase.getInstance();
        loadContador = database.getReference("contador");
        loadContador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contadorTemas = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                finalConsulta = contadorTemas - 1;
                inicioConsulta = finalConsulta - 9;
                //contadorConsulta = contadorTemas-1;
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
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        fab = root.findViewById(R.id.fabMenuPrincipal);
        fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabDefault)));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalConsulta >= 0) {
                    finalConsulta = inicioConsulta - 1;
                    inicioConsulta = finalConsulta - 9;
                    if (finalConsulta >= 0) {
                        fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabLoad)));
                        fab.setRippleColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabLoadDark)));
                        initializeData();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabDefault)));
                                fab.setRippleColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabDefaultDark)));
                            }
                        }, 1500);
                    } else {
                        fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabNotLoad)));
                        fab.setRippleColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabNotLoadDark)));
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabDefault)));
                                fab.setRippleColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.fabDefaultDark)));
                            }
                        }, 1500);
                    }
                }
            }
        });

        swipeRefreshTemas = root.findViewById(R.id.swipeRefreshTemas);
        swipeRefreshTemas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finalConsulta = contadorTemas - 1;
                inicioConsulta = finalConsulta - 9;
                temasList.clear();
                initializeData();
                swipeRefreshTemas.setRefreshing(false);
            }
        });

        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerViewTemas);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        manager = new LinearLayoutManager(mRecyclerView.getContext());
        mAdapter = new HistoriasAdapter(getContext(), temasList);
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }


    private void initializeData() {
        System.out.println("Contador tema " + contadorTemas);
        while (contadorTemas == -1) ;
        //contadorConsulta = contadorTemas;
        DatabaseReference dbRef = database.getReference("Temas");
        Query myQuery = dbRef.orderByChild("idTema").startAt(inicioConsulta).endAt(finalConsulta);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                temasListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (temasListh == null) {

                } else {
                    for (int i = 0; i < temasListh.size(); i++) {
                        Tema tema = Tema.convertTema(temasListh.get("Tema_" + (finalConsulta - i)));
                        temasList.add(tema);
                    }

                    mAdapter.notifyDataSetChanged();
                    System.out.println("Hola -- " + temasList);

                    // Initialize the adapter and set it to the RecyclerView.
                }
                //contadorConsulta+=10;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });
    }
}