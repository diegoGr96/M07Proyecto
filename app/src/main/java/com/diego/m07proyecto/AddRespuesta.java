package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddRespuesta extends AppCompatActivity {

    private EditText edtRespuesta;
    private Button sendRespuesta;
    private CheckBox checkAnonimo;

    private int respuestaLength = 0;

    private int numRespuestaTema;
    private int numRespuestaUser;

    private int idTema;
    private String uidUser;
    private String nickUser;

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    DatabaseReference respuestaRef;
    DatabaseReference numRespuestaTemaRef;
    DatabaseReference numRespuestaUserRef;
    DatabaseReference refNickUsuario;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_respuesta);
        edtRespuesta = findViewById(R.id.edtRespuesta);
        sendRespuesta = findViewById(R.id.btnSendRespuesta);
        checkAnonimo = findViewById(R.id.checkAnonimoRespuesta);
        idTema = getIntent().getIntExtra("ID_TEMA", -1);
        //uidUser = getIntent().getStringExtra("UID_USER");
        nickUser = getIntent().getStringExtra("NICK_USER");

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = String.valueOf(mAuth.getCurrentUser().getUid());
        numRespuestaTemaRef = database.getReference("Temas/Tema_" + idTema + "/contRespuestas");
        numRespuestaTemaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numRespuestaTema = Integer.valueOf(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        numRespuestaUserRef = database.getReference("Usuarios/" + currentUser + "/numRespuestas");
        numRespuestaUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numRespuestaUser = Integer.valueOf(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        refNickUsuario = database.getReference("Usuarios/" + currentUser + "/nick");
        refNickUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //System.out.println("EEEEEEEEEEL NICK ES: " + dataSnapshot.getValue().toString());
                //nick = dataSnapshot.getValue().toString();
                nickUser = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        sendRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idTema != -1) {
                    String textRespuesta = edtRespuesta.getText().toString();
                    respuestaLength = textRespuesta.length();
                    if (respuestaLength > 0) {
                        if (respuestaLength < 250) {
                            respuestaRef = database.getReference("Temas/Tema_" + idTema + "/Respuestas/respuesta_" + numRespuestaTema);
                            respuestaRef.setValue(new Respuesta(numRespuestaTema, textRespuesta, currentUser, nickUser, checkAnonimo.isChecked()));
                            numRespuestaTemaRef.setValue(++numRespuestaTema);
                            numRespuestaUserRef.setValue(++numRespuestaUser);
                            finish();
                        } else {
                            Snackbar.make(v, getResources().getText(R.string.respuesta_erronea), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } else {
                        Snackbar.make(v,  getResources().getText(R.string.respuesta_vacia), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(v, getResources().getText(R.string.unknown_error), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}