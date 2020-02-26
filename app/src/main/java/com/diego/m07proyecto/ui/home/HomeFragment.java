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
import com.diego.m07proyecto.Tema;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FirebaseDatabase database;

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private ArrayList<Tema> mTemasData;
    private HistoriasAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        Log.d("A", mRecyclerView + "   AAAAAAAAAAAAAAAAAa");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Initialize the ArrayList that will contain the data.
        mTemasData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new HistoriasAdapter(getContext(), mTemasData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();


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

        final ArrayList<Tema> temasList = new ArrayList<>();
        DatabaseReference myRef = database.getReference("TemasOrdenados");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(int i = 0; i < dataSnapshot.getChildrenCount() || i < 10; i++){

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });

        // Clear the existing data (to avoid duplication).
        mTemasData.clear();

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for (int i = 0; i < 10; i++) {
            if(temasList.get(i) != null) {
                mTemasData.add(temasList.get(i));
            }
        }

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }
}