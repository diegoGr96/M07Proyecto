package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddRespuesta extends AppCompatActivity {

    private EditText edtRespuesta;
    private Button sendRespuesta;

    private int respuestaLength = 0;

    private int numRespuestaTema;
    private int numRespuestaUser;

    private int idTema;
    private String uidUser;
    private String nickUser;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference respuestaRef;
    DatabaseReference numRespuestaTemaRef;
    DatabaseReference numRespuestaUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_respuesta);
        edtRespuesta = findViewById(R.id.edtRespuesta);
        sendRespuesta = findViewById(R.id.btnSendRespuesta);
        idTema = getIntent().getIntExtra("ID_TEMA", -1);
        uidUser = getIntent().getStringExtra("UID_USER");
        nickUser = getIntent().getStringExtra("NICK_USER");
        numRespuestaTemaRef = database.getReference("Temas/Tema_"+ idTema + "/contRespuestas");
        numRespuestaUserRef = database.getReference("Usuarios/"+uidUser+"/numRespuestas");
        numRespuestaTemaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numRespuestaTema = Integer.valueOf(String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        numRespuestaUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numRespuestaUser = Integer.valueOf(String.valueOf(dataSnapshot.getValue()));
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
                    if (respuestaLength > 250 || respuestaLength < 1) {
                        Snackbar.make(v, getResources().getText(R.string.respuesta_erronea), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        respuestaRef = database.getReference("Temas/Tema_" + idTema + "/respuestas/respuesta"+ numRespuestaTema);
                        respuestaRef.setValue(new Respuesta(numRespuestaTema, textRespuesta, uidUser, nickUser, false, "test"));
                        numRespuestaTemaRef.setValue(++numRespuestaTema);
                        numRespuestaUserRef.setValue(++numRespuestaUser);
                        finish();
                    }
                } else {
                    Snackbar.make(v, getResources().getText(R.string.unknown_error), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}