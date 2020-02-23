package com.diego.m07proyecto.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.diego.m07proyecto.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private EditText textNick;
    private EditText textNombre;
    private EditText textNacimiento;
    private EditText textTemasCreados;
    private EditText textRespuestas;
    private EditText textEmail;

    private List<EditText> listaTextos;

    private Button btnMisTemas;
    private Button btnGuardar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        textNick = root.findViewById(R.id.textNick);
        textNombre = root.findViewById(R.id.textNombre);
        textNacimiento = root.findViewById(R.id.textNacimiento);
        textTemasCreados = root.findViewById(R.id.textTemasCreados);
        textRespuestas = root.findViewById(R.id.textRespuestas);
        textEmail = root.findViewById(R.id.textEmail);

        listaTextos = new ArrayList<>();
        listaTextos.add(textNacimiento);
        listaTextos.add(textNombre);
        listaTextos.add(textEmail);
        listaTextos.add(textNick);
        listaTextos.add(textRespuestas);
        listaTextos.add(textTemasCreados);

        btnMisTemas = root.findViewById(R.id.btnMisTemas);
        btnGuardar = root.findViewById(R.id.btnGuardar);


        DatabaseReference nickUsuario = database.getReference("Usuarios/" + currentUser.getUid() + "/");
        nickUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> consulta = (HashMap<String, Object>) dataSnapshot.getValue();
                int i = 0;
                for(Map.Entry<String,Object> elemento : consulta.entrySet()){
                    System.out.println("AAA   "+elemento.getKey()+" -- "+elemento.getValue());
                    listaTextos.get(i).setText(elemento.getValue().toString());
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    DatabaseReference newTema = database.getReference("Usuarios/" + currentUser.getUid());
                    newTema.child("Nombre").setValue(textNombre.getText().toString());
                    //System.out.println("ZZZAutor" + currentUser.getUid());
                    newTema.child("FechaNacimiento").setValue(textNacimiento.getText().toString());
            }
        });
        return root;
    }

}