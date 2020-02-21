package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bienvenida3 extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseAuth mAuth;

    FirebaseUser currentUser;

    private TextView text;
    private EditText textoUsuario;
    private EditText textoNombre;
    private EditText textoApellidos;
    private EditText fechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida3);
        textoUsuario = findViewById(R.id.textoUsuario);
        textoNombre = findViewById(R.id.textoNombre);
        textoApellidos = findViewById(R.id.textoApellidos);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);
        text = findViewById(R.id.text);
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            text.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        System.out.println("El UID es: " + currentUser.getUid());

        fechaNacimiento.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (fechaNacimiento.length() == 2 || fechaNacimiento.length() == 5 && fechaNacimiento.length()>0){
                    fechaNacimiento.append("/");
                }else if(fechaNacimiento.length() > 10){
                    fechaNacimiento.setText(fechaNacimiento.getText().subSequence(0,fechaNacimiento.length()-1));
                    fechaNacimiento.setSelection(fechaNacimiento.length());
                }
                return false;
            }
        });
    }

    public void goToPage2(View view) {
        Intent intent = new Intent(this, Bienvenida2.class);
        startActivity(intent);
    }

    public void envia(View view) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String usuario = "";
        String nombre = "";
        String apellidos = "";
        String nacimiento = "";
        if(!textoUsuario.getText().toString().equals("")) usuario = textoUsuario.getText().toString();
        if(!textoNombre.getText().toString().equals("")) nombre = textoNombre.getText().toString();
        if(!textoApellidos.getText().toString().equals("")) apellidos = textoApellidos.getText().toString();
        if(!fechaNacimiento.getText().toString().equals("")) nacimiento = fechaNacimiento.getText().toString();
        DatabaseReference myRef = database.getReference("Usuarios/"+currentUser.getUid());
        myRef.child("Nick").setValue(usuario);
        myRef.child("Edad").setValue(nacimiento);
        myRef.child("Nombre").setValue(nombre + " " + apellidos);
        myRef.child("NumRespuestas").setValue(0);
        myRef.child("NumTemas").setValue(0);
        /*if(nacimiento.length() == 10) {  // Si se cambia para que vaya poniendo las barras durante la escritura del campo no hace falta hacer esta comprobación. Poner también que no deje escribir más de 10 carácteres.
            Date fecha = formatter.parse(nacimiento);
        } else{

        }
        */
    }
}
