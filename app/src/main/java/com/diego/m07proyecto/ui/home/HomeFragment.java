package com.diego.m07proyecto.ui.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diego.m07proyecto.Historia;
import com.diego.m07proyecto.HistoriasAdapter;
import com.diego.m07proyecto.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private ArrayList<Historia> mHistoriasData;
    private HistoriasAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        Log.d("A", mRecyclerView + "   AAAAAAAAAAAAAAAAAa");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Initialize the ArrayList that will contain the data.
        mHistoriasData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new HistoriasAdapter(getContext(), mHistoriasData);
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
        String[] sportsList = getResources()
                .getStringArray(R.array.games_titles);
        String[] sportsInfo = getResources()
                .getStringArray(R.array.games_info);

        // Clear the existing data (to avoid duplication).
        mHistoriasData.clear();

        // Create the ArrayList of Sports objects with titles and
        // information about each sport.
        for (int i = 0; i < sportsList.length; i++) {
            mHistoriasData.add(new Historia(sportsList[i], sportsInfo[i]));
        }

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }
}