package com.diego.m07proyecto.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    private boolean isScrolling=false;
    private int currentItems,totaltItems,scrollOutItems;
    private LinearLayoutManager manager;



    SwipeRefreshLayout swipeRefreshTemas;

    private Map<String,HashMap<String, Object>> temasListh;
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
                contadorConsulta = contadorTemas-1;
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




        swipeRefreshTemas = root.findViewById(R.id.swipeRefreshTemas);
        swipeRefreshTemas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                contadorConsulta = contadorTemas - 1;
                initializeData();
                swipeRefreshTemas.setRefreshing(false);
            }
        });

        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        manager = new LinearLayoutManager(mRecyclerView.getContext());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totaltItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

            //    Toast.makeText(root.getContext(),currentItems +" "+totaltItems+" "+scrollOutItems,Toast.LENGTH_LONG).show();
                if (isScrolling && (currentItems + scrollOutItems == totaltItems)){
                    isScrolling = false;
                    Toast.makeText(root.getContext(),"Holaaaaaa",Toast.LENGTH_LONG).show();
                }

            }
        });


        return root;
    }


    private void initializeData() {
        System.out.println("Contador tema "+ contadorTemas);
        while(contadorTemas == -1);
        //contadorConsulta = contadorTemas;
        DatabaseReference dbRef = database.getReference("Temas");
        Query myQuery;
        if (contadorTemas>10){
            myQuery = dbRef.orderByChild("idTema").startAt(contadorConsulta-10).endAt(contadorConsulta);
        }else{
            myQuery = dbRef.orderByChild("idTema").startAt(0).endAt(contadorConsulta);
        }

        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                temasListh = (Map<String,HashMap<String, Object>>) dataSnapshot.getValue();
                if (temasListh == null) {

                } else {

                    temasList = new ArrayList<>();
                    for (int i = 0; i < temasListh.size(); i++) {
                        Tema tema = Tema.convertTema(temasListh.get("Tema_"+(contadorConsulta--)));
                        temasList.add(tema);
                    }

                    System.out.println("Hola -- " + temasList);
                    //Collections.reverse(temasList);
                    // Initialize the adapter and set it to the RecyclerView.
                    mAdapter = new HistoriasAdapter(getContext(), temasList);
                    mRecyclerView.setAdapter(mAdapter);
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