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

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private HistoriasAdapter mAdapter;

    private List<HashMap<String,Object>> temasListh;
    private List<Tema> temasList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();

        initializeData();

        System.out.println(temasList);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        Log.d("A", mRecyclerView + "   AAAAAAAAAAAAAAAAAa");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/
        return root;
    }


    private void initializeData() {
        // Get the resources from the XML file.
        DatabaseReference dbRef = database.getReference("Temas");
        Query myQuery = dbRef.orderByChild("idTema").startAt(0).endAt(10);

        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                temasListh = (List<HashMap<String,Object>>) dataSnapshot.getValue();
                if (temasListh == null){

                }else {
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
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.

        // Notify the adapter of the change.
       // mAdapter.notifyDataSetChanged();
    }
}