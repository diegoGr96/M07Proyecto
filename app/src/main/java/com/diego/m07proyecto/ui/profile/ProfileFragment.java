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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.diego.m07proyecto.AddRespuesta;
import com.diego.m07proyecto.CambioColorEditText;
import com.diego.m07proyecto.EditProfile;
import com.diego.m07proyecto.HistoriasAdapter;
import com.diego.m07proyecto.MisTemas;
import com.diego.m07proyecto.R;
import com.diego.m07proyecto.Tema;
import com.diego.m07proyecto.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference loadContador;

    private String uidAutor;

    private int contadorTemas = -1;

    private Map<String, HashMap<String, Object>> temasListh;
    private List<Tema> temasList;

    private boolean firstAttempt;

    private TextView txtNick;
    private TextView txtBorn;
    private TextView txtDescription;
    private Button btnVerTemas;
    private ImageButton btnEditProfile;

    private RecyclerView mRecyclerView;
    private HistoriasAdapter mAdapter;

    /*
    private Map<String, Object> consulta;

    private EditText textNick;
    private EditText textNombre;
    private EditText textNacimiento;
    private EditText textTemasCreados;
    private EditText textRespuestas;

    private List<EditText> listaTextos;

    //private Button btnMisTemas;
    private Button btnGuardar;
    private String nacimientoOriginal;
    private String nombreOriginal;
    private int contadorCaracteres;
    */

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uidAutor = mAuth.getCurrentUser().getUid();

        btnVerTemas = root.findViewById(R.id.btnVerTemas);
        btnEditProfile = root.findViewById(R.id.btnEditProfile);
        txtNick = root.findViewById(R.id.txtNickProfile);
        txtBorn = root.findViewById(R.id.txtBornDateProfile);
        txtDescription = root.findViewById(R.id.txtDescripcionProfile);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), EditProfile.class));
            }
        });

        temasList = new ArrayList<>();

        firstAttempt = true;

        btnVerTemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), MisTemas.class));
            }
        });

        loadContador = database.getReference("contador");
        loadContador.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contadorTemas = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                /*finalConsulta=contadorTemas-1;
                inicioConsulta = finalConsulta-9;*/
                //contadorConsulta = contadorTemas-1;
                //System.out.println("Contador es: " + contadorTemas);
                /*
                if (firstAttempt) {
                    initializeData();
                    firstAttempt = false;
                }
                 */
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        /*
        textNick = root.findViewById(R.id.textNick);
        textNombre = root.findViewById(R.id.textNombre);
        textNacimiento = root.findViewById(R.id.textNacimiento);
        textTemasCreados = root.findViewById(R.id.textTemasCreados);
        textRespuestas = root.findViewById(R.id.textRespuestas);
        */

/*
        mRecyclerView = root.findViewById(R.id.rvMisTemas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mAdapter = new HistoriasAdapter(getActivity().getApplicationContext(), temasList);
        mRecyclerView.setAdapter(mAdapter);
 */

        /*
        listaTextos = new ArrayList<>();
        listaTextos.add(textRespuestas);
        listaTextos.add(textNick);
        listaTextos.add(textTemasCreados);
        listaTextos.add(textNacimiento);
        listaTextos.add(textNombre);

        //btnMisTemas = root.findViewById(R.id.btnMisTemas);
        btnGuardar = root.findViewById(R.id.btnGuardar);
        */

        DatabaseReference datosUsuario = database.getReference("Usuarios/" + currentUser.getUid() + "/");
        datosUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                txtNick.setText(dataSnapshot.child("nick").getValue().toString());
                txtBorn.setText(getResources().getString(R.string.born_in) + dataSnapshot.child("fechaNacimiento").getValue().toString() + ".");
                if(dataSnapshot.child("descripcion").exists()){
                    String auxDescription = dataSnapshot.child("descripcion").getValue().toString();
                    if(auxDescription == ""){
                        txtDescription.setText(getResources().getString(R.string.no_description));
                    } else{
                        txtDescription.setText(auxDescription);
                    }
                } else{
                    txtDescription.setText(getResources().getString(R.string.no_description));
                }

                /*
                Usuario usuario = new Usuario((Usuario) dataSnapshot.getValue());
                System.out.println("El usuario es: " + usuario);

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
                */
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*
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
        */

        /*
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
                    contadorCaracteres--;
                }
                return false;
            }
        });
        */

        /*
        btnMisTemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRespuesta = new Intent(getContext(), MisTemas.class);
                startActivityForResult(iRespuesta, 1);
            }
        });*/

        return root;
    }
/*
    private void initializeData() {
        //System.out.println("Contador tema " + contadorTemas);
        while (contadorTemas == -1) ;
        DatabaseReference dbRef = database.getReference("Temas");
        Query myQuery = dbRef.orderByChild("uidAutor").equalTo(uidAutor);
        myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                temasListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (temasListh == null) {

                } else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Tema tema = Tema.convertTema(temasListh.get(snapshot.getKey()));
                        temasList.add(tema);
                    }
                    mAdapter.notifyDataSetChanged();
                    //System.out.println("Hola -- " + temasList);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w("VVVV", "Failed to read value.", error.toException());
            }
        });
    }

 */
}