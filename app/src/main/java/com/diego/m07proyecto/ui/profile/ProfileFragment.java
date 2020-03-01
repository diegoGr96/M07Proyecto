package com.diego.m07proyecto.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.diego.m07proyecto.AddRespuesta;
import com.diego.m07proyecto.CambioColorEditText;
import com.diego.m07proyecto.MisTemas;
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

public class ProfileFragment extends Fragment {

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Map<String, Object> consulta;

    private EditText textNick;
    private EditText textNombre;
    private EditText textNacimiento;
    private EditText textTemasCreados;
    private EditText textRespuestas;

    private List<EditText> listaTextos;

    private Button btnMisTemas;
    private Button btnGuardar;

    private String nacimientoOriginal;
    private String nombreOriginal;
    private int contadorCaracteres;

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

        listaTextos = new ArrayList<>();
        listaTextos.add(textRespuestas);
        listaTextos.add(textNick);
        listaTextos.add(textTemasCreados);
        listaTextos.add(textNacimiento);
        listaTextos.add(textNombre);

        btnMisTemas = root.findViewById(R.id.btnMisTemas);
        btnGuardar = root.findViewById(R.id.btnGuardar);


        DatabaseReference nickUsuario = database.getReference("Usuarios/" + currentUser.getUid() + "/");
        nickUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consulta = (HashMap<String, Object>) dataSnapshot.getValue();
                consulta.remove("email");
                int i = 0;
                textNick.setText(consulta.get("nick").toString());
                textNombre.setText(consulta.get("nombre").toString());
                textNacimiento.setText(consulta.get("fechaNacimiento").toString());
                textTemasCreados.setText(consulta.get("numTemas").toString());
                textRespuestas.setText(consulta.get("numRespuestas").toString());
                nacimientoOriginal = textNacimiento.getText().toString();
                nombreOriginal = textNombre.getText().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombreOriginal.equals(textNombre.getText().toString()) || !nacimientoOriginal.equals(textNacimiento.getText().toString())) {
                    DatabaseReference newTema = database.getReference("Usuarios/" + currentUser.getUid());
                    newTema.child("nombre").setValue(textNombre.getText().toString());
                    newTema.child("fechaNacimiento").setValue(textNacimiento.getText().toString());

                    Snackbar.make(textNick, getResources().getText(R.string.cambiosMiPerfil), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    nacimientoOriginal = textNacimiento.getText().toString();
                    nombreOriginal = textNombre.getText().toString();

                    Thread hiloCambioColores = new Thread(new CambioColorEditText(textNacimiento, textNombre));
                    hiloCambioColores.start();
                } else {
                    Snackbar.make(textNick, getResources().getText(R.string.noCambiosMiPerfil), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        textNacimiento.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int sizeFecha = textNacimiento.length();
                String textoFecha = textNacimiento.getText().toString();
                if (contadorCaracteres < sizeFecha) {
                    if (sizeFecha == 2 || sizeFecha == 5) {
                        textNacimiento.append("/");
                    } else if (textNacimiento.length() > 10) {
                        textNacimiento.setText(textNacimiento.getText().subSequence(0, sizeFecha - 1));
                        textNacimiento.setSelection(sizeFecha - 1);
                    }
                    contadorCaracteres++;

                } else {
                    Toast.makeText(getContext(), "Borrando...", Toast.LENGTH_LONG).show();
                    contadorCaracteres--;
                }
                return false;
            }
        });

        btnMisTemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRespuesta = new Intent(getContext(), MisTemas.class);
                startActivityForResult(iRespuesta, 1);
            }
        });

        return root;
    }
}