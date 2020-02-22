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

    private EditText tituloTema;
    private EditText descripcionTema;
    private CheckBox checkAnonim;
    private Button btnCrear;

    private String nick;

    private int numTema;

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

        btnCrear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!tituloTema.getText().toString().equals("") && !descripcionTema.getText().toString().equals("")){
                    String titulo = tituloTema.getText().toString();
                    String descripcion = descripcionTema.getText().toString();
                    DatabaseReference nickUsuario = database.getReference("Usuarios/"+currentUser.getUid()+"/Nick");
                    nickUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println(dataSnapshot.getValue().toString());
                            nick = dataSnapshot.getValue().toString();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    DatabaseReference TemaUsuario = database.getReference("Usuarios/"+currentUser.getUid()+"/NumTemas");
                    TemaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            System.out.println(dataSnapshot.getValue().toString());
                            numTema = Integer.parseInt(dataSnapshot.getValue().toString());
                            numTema++;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    DatabaseReference newTema = database.getReference("Temas/"+currentUser.getUid()+"_"+numTema);
                    newTema.child("Autor").setValue(currentUser.getUid());
                    newTema.child("Nick").setValue(nick);
                    newTema.child("Titulo").setValue(titulo);
                    newTema.child("Cuerpo").setValue(descripcion);
                    DatabaseReference incNumTema = database.getReference("Usuarios/"+currentUser.getUid()+"/NumTemas");
                    incNumTema.setValue(numTema);
                } else{
                    Snackbar.make(view, getResources().getText(R.string.white_camps), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        return root;
    }
}