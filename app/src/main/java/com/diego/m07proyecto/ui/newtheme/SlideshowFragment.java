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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SlideshowFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    DatabaseReference myRef;

    private EditText tituloTema;
    private EditText descripcionTema;
    private CheckBox checkAnonim;
    private Button btnCrear;

    private String nick;

    private int numTema;

    private String titulo;
    private String descripcion;

    private int contador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newtheme, container, false);

        tituloTema = root.findViewById(R.id.tituloTema);
        descripcionTema = root.findViewById(R.id.edtDescripcion);
        checkAnonim = root.findViewById(R.id.checkAnon);
        btnCrear = root.findViewById(R.id.btnCrear);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        myRef = database.getReference("contador");

        DatabaseReference nickUsuario = database.getReference("Usuarios/" + currentUser.getUid() + "/Nick");
        nickUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //System.out.println("EEEEEEEEEEL NICK ES: " + dataSnapshot.getValue().toString());
                //nick = dataSnapshot.getValue().toString();
                nick = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference TemaUsuario = database.getReference("Usuarios/" + currentUser.getUid() + "/NumTemas");
        TemaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //System.out.println("EEEEEEEEEEL NUM ES: " + dataSnapshot.getValue().toString());
                //numTema = Integer.parseInt(dataSnapshot.getValue().toString());
                numTema = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tituloTema.getText().toString().equals("") && !descripcionTema.getText().toString().equals("")) {
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            contador = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                        }
                    });
                    numTema++;
                    titulo = tituloTema.getText().toString();
                    descripcion = descripcionTema.getText().toString();
                    DatabaseReference newTema = database.getReference("Temas/" + contador);
                    newTema.child("uidAutor").setValue(currentUser.getUid());
                    //System.out.println("ZZZAutor" + currentUser.getUid());
                    newTema.child("nickAutor").setValue(nick);
                    //System.out.println("ZZZNick del creador " + nick);
                    newTema.child("titulo").setValue(titulo);
                    //System.out.println("ZZZTitulo " + titulo);
                    newTema.child("cuerpo").setValue(descripcion);
                    //System.out.println("ZZZCuerpo " + titulo);
                    newTema.child("isAnonimo").setValue(checkAnonim.isChecked());
                    //
                    newTema.child("contRespuestas").setValue(0);
                    newTema.child("idTema").setValue(contador);
                    DatabaseReference incNumTema = database.getReference("Usuarios/" + currentUser.getUid() + "/NumTemas");
                    incNumTema.setValue(numTema);
                    contador++;
                    myRef.setValue(contador);
                    //System.out.println("ZZZNumero de temas " + numTema);
                    //.out.println("ZZZ------------------------------------");
                } else {
                    Snackbar.make(view, getResources().getText(R.string.white_camps), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        return root;
    }
}