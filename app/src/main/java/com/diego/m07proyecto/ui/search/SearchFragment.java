package com.diego.m07proyecto.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.diego.m07proyecto.R;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private Spinner cbFilter;
    private Spinner cbCategories;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        /*

        final TextView textView = root.findViewById(R.id.text_tools);
        searchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        */

        cbFilter = root.findViewById(R.id.cbFilter);
        ArrayAdapter<CharSequence> adapterFilter = ArrayAdapter.createFromResource(getContext(), R.array.search_filters, android.R.layout.simple_spinner_item);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbFilter.setAdapter(adapterFilter);

        cbCategories = root.findViewById(R.id.cbCategories);
        ArrayAdapter<CharSequence> adapterCategories = ArrayAdapter.createFromResource(getContext(), R.array.categories, android.R.layout.simple_spinner_item);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbCategories.setAdapter(adapterCategories);

        return root;
    }
}