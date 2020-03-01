package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class FilteredThemes extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Query filter;
    private Intent intent;

    private RecyclerView mRecyclerView;
    private HistoriasAdapter mAdapter;

    private boolean isScrolling = false;
    private int currentItems, totaltItems, scrollOutItems;
    private LinearLayoutManager manager;

    SwipeRefreshLayout swipeRefreshTemas;

    private Map<String, HashMap<String, Object>> temasListh;
    private List<Tema> temasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_themes);
        intent = getIntent();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Temas");

        swipeRefreshTemas = findViewById(R.id.swipeRefreshFiltroTemas);
        swipeRefreshTemas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeData();
                swipeRefreshTemas.setRefreshing(false);
            }
        });

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerViewFiltro);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        manager = new LinearLayoutManager(mRecyclerView.getContext());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totaltItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if (isScrolling && (currentItems + scrollOutItems == totaltItems)) {
                    isScrolling = false;
                }
            }
        });

        initializeData();

    }

    public void initializeData() {
        switch (intent.getIntExtra("FILTER", -1)) {
            case 0:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                filter = ref.orderByChild("categoria").equalTo(getIntent().getStringExtra("CATEGORY")).limitToLast(10);
                break;
            case 1:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                filter = ref.orderByChild("anonimo").equalTo(getIntent().getBooleanExtra("ANNONYMOUS", true)).limitToLast(10);
                break;
            case 2:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                filter = ref.orderByChild("contRespuestas").equalTo(getIntent().getIntExtra("TEXT", -1)).limitToLast(10);
                break;
            case 3:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                filter = ref.orderByChild("cuerpo").startAt("\uf8ff" + getIntent().getStringExtra("TEXT")+"\uf8ff").endAt("\uf8ff" + getIntent().getStringExtra("TEXT")+"\uf8ff").limitToLast(10);
                break;
            case 4:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                filter = ref.orderByChild("idTema").equalTo(getIntent().getIntExtra("TEXT", -1)).limitToLast(10);
                break;
            case 5:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                filter = ref.orderByChild("nickAutor").startAt("\uf8ff" + getIntent().getStringExtra("TEXT")+"\uf8ff").endAt("\uf8ff" + getIntent().getStringExtra("TEXT")+"\uf8ff").limitToLast(10);
                break;
            case 6:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                filter = ref.orderByChild("titulo").startAt("\uf8ff" + getIntent().getStringExtra("TEXT")).endAt(getIntent().getStringExtra("TEXT")+"\uf8ff").limitToLast(10);
                break;
            default:
                //System.out.println("ESTOY EN EL CASO: " + intent.getIntExtra("FILTER", -1));
                Snackbar.make(mRecyclerView, "Something went wrong", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    temasListh = (Map<String,HashMap<String, Object>>) dataSnapshot.getValue();
                    temasList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Tema tema = Tema.convertTema(temasListh.get(snapshot.getKey()));
                        temasList.add(tema);
                    }
                    Collections.reverse(temasList);
                    mAdapter = new HistoriasAdapter(getApplicationContext(), temasList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        filter.addListenerForSingleValueEvent(valueEventListener);
    }
}
