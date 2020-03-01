package com.diego.m07proyecto.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.diego.m07proyecto.FilteredThemes;
import com.diego.m07proyecto.R;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private Spinner cbFilter;
    private Spinner cbCategories;
    private EditText txtFilter;
    private CheckBox checkboxFilter;
    private Button btnFilter;

    private int selectedFilter;
    private int selectedCategory;


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

        txtFilter = root.findViewById(R.id.txtFilter);

        checkboxFilter = root.findViewById(R.id.checkboxFilter);

        btnFilter = root.findViewById(R.id.btnFilter);

        cbFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("Test " + parent);
                //System.out.println("Test " + view);
                //System.out.println("Test " + position);
                //System.out.println("Test " + id);
                //System.out.println("Test " + parent.getItemAtPosition(position));
                selectedFilter = (int) id;
                switch((int)id){
                    case 0:
                        cbCategories.setVisibility(View.VISIBLE);
                        checkboxFilter.setVisibility(View.INVISIBLE);
                        txtFilter.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        cbCategories.setVisibility(View.INVISIBLE);
                        checkboxFilter.setVisibility(View.VISIBLE);
                        txtFilter.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        cbCategories.setVisibility(View.INVISIBLE);
                        checkboxFilter.setVisibility(View.INVISIBLE);
                        txtFilter.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("Test " + parent);
                //System.out.println("Test " + view);
                //System.out.println("Test " + position);
                //System.out.println("Test " + id);
                //System.out.println("Test " + parent.getItemAtPosition(position));
                selectedCategory = (int) id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent filterIntent = new Intent(getContext(), FilteredThemes.class);
                //System.out.println(sFilter);
                switch(selectedFilter){
                    case 0:
                        filterIntent.putExtra("CATEGORY", selectedCategory);
                        //System.out.println(sCategory);
                        break;
                    case 1:
                        boolean sAnnonymous = checkboxFilter.isChecked();
                        filterIntent.putExtra("ANNONYMOUS", sAnnonymous);
                        //System.out.println(sAnnonymous);
                        break;
                    default:
                        if(selectedFilter == 2 || selectedFilter == 4){
                            int sText = Integer.parseInt(txtFilter.getText().toString());
                            filterIntent.putExtra("TEXT", sText);
                        }else {
                            String sText = txtFilter.getText().toString();
                            filterIntent.putExtra("TEXT", sText);
                            //System.out.println(sText);
                        }
                        break;
                }
                filterIntent.putExtra("FILTER", selectedFilter);
                startActivity(filterIntent);
            }
        });

        return root;
    }
}