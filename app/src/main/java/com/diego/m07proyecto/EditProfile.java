package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userData;

    private Map<String, Object> consulta;

    private EditText textNick;
    private EditText textNombre;
    private EditText textNacimiento;
    private EditText textDescripcion;
    /*
    private EditText textTemasCreados;
    private EditText textRespuestas;
    */

    private List<EditText> listaTextos;

    //private Button btnMisTemas;
    private Button btnGuardar;
    private String nacimientoOriginal;
    private String nombreOriginal;
    private String descripcionOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userData = database.getReference("Usuarios/"+currentUser.getUid());

        btnGuardar = findViewById(R.id.btnGuardar);
        textNick = findViewById(R.id.textNick);
        textNombre = findViewById(R.id.textNombre);
        textNacimiento = findViewById(R.id.textNacimiento);
        textDescripcion = findViewById(R.id.textDescripcion);
        /*
        textTemasCreados = findViewById(R.id.textTemasCreados);
        textRespuestas = findViewById(R.id.textRespuestas);
        */

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                textNick.setText(dataSnapshot.child("nick").getValue().toString());
                textNombre.setText(dataSnapshot.child("nombre").getValue().toString());
                textNacimiento.setText(dataSnapshot.child("fechaNacimiento").getValue().toString());
                textDescripcion.setText(dataSnapshot.child("descripcion").getValue().toString());
                /*
                textTemasCreados.setText(dataSnapshot.child("numTemas").getValue().toString());
                textRespuestas.setText(dataSnapshot.child("numRespuestas").getValue().toString());
                */
                nombreOriginal = textNombre.getText().toString();
                nacimientoOriginal = textNacimiento.getText().toString();
                descripcionOriginal = textDescripcion.getText().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nombreOriginal.equals(textNombre.getText().toString()) || !nacimientoOriginal.equals(textNacimiento.getText().toString()) || !descripcionOriginal.equals((textDescripcion.getText().toString()))) {
                    DatabaseReference newTema = database.getReference("Usuarios/" + currentUser.getUid());
                    newTema.child("nombre").setValue(textNombre.getText().toString());
                    newTema.child("fechaNacimiento").setValue(textNacimiento.getText().toString());
                    newTema.child("descripcion").setValue(textDescripcion.getText().toString());
                    finish();

                    /*
                    Snackbar.make(textNick, getResources().getText(R.string.cambiosMiPerfil), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    nacimientoOriginal = textNacimiento.getText().toString();
                    nombreOriginal = textNombre.getText().toString();

                    Thread hiloCambioColores = new Thread(new CambioColorEditText(textNacimiento, textNombre));
                    hiloCambioColores.start();

                     */
                } else {
                    Snackbar.make(textNick, getResources().getText(R.string.noCambiosMiPerfil), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
}
