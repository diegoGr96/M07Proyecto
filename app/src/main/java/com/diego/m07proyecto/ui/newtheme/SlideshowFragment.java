package com.diego.m07proyecto.ui.newtheme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.diego.m07proyecto.R;

public class SlideshowFragment extends Fragment {

    private EditText tituloTema;
    private EditText descripcionTema;
    private CheckBox checkAnonim;
    private Button btnCrear;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newtheme, container, false);

        tituloTema = root.findViewById(R.id.tituloTema);
        descripcionTema = root.findViewById(R.id.edtDescripcion);
        checkAnonim = root.findViewById(R.id.checkAnon);
        btnCrear = root.findViewById(R.id.btnCrear);

        //btnCrear.setOnClickListener();

        return root;
    }
}