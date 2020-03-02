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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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

    private int contadorCaracteres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida3);
        textoUsuario = findViewById(R.id.textoUsuario);
        textoNombre = findViewById(R.id.textoNombre);
        textoApellidos = findViewById(R.id.textoApellidos);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);
        text = findViewById(R.id.text);
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            text.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //System.out.println("El UID es: " + currentUser.getUid());

        fechaNacimiento.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int sizeFecha = fechaNacimiento.length();
                String textoFecha = fechaNacimiento.getText().toString();
                if (contadorCaracteres < sizeFecha) {
                    if (sizeFecha == 2 || sizeFecha == 5) {
                        fechaNacimiento.append("/");
                    } else if (fechaNacimiento.length() > 10) {
                        fechaNacimiento.setText(fechaNacimiento.getText().subSequence(0, sizeFecha - 1));
                        fechaNacimiento.setSelection(sizeFecha - 1);
                    }
                    contadorCaracteres++;

                } else {
                    Toast.makeText(getApplicationContext(), "Borrando...", Toast.LENGTH_LONG).show();
                    contadorCaracteres--;
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
        String usuario = "";
        String nombre = "";
        String apellidos = "";
        String nacimiento = "";
        if (!textoUsuario.getText().toString().equals(""))
            usuario = textoUsuario.getText().toString();
        if (!textoNombre.getText().toString().equals("")) nombre = textoNombre.getText().toString();
        if (!textoApellidos.getText().toString().equals(""))
            apellidos = textoApellidos.getText().toString();
        if (!fechaNacimiento.getText().toString().equals(""))
            nacimiento = fechaNacimiento.getText().toString();

        if (usuario.length() > 0) {
            DatabaseReference myRef = database.getReference("Usuarios/" + currentUser.getUid());

            usuario += ("#" + mAuth.getCurrentUser().getUid().substring(0, 5));
            myRef.child("nick").setValue(usuario);
            myRef.child("fechaNacimiento").setValue(nacimiento);
            myRef.child("nombre").setValue(nombre + " " + apellidos);
            myRef.child("numRespuestas").setValue(0);
            myRef.child("numTemas").setValue(0);

            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(textoUsuario, getResources().getText(R.string.AdvertenciaNickVacio), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
