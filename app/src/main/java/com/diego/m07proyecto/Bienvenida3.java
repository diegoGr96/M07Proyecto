package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
    private Spinner spnDia;
    private Spinner spnMes;
    private Spinner spnAny;

    private int contadorCaracteres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida3);
        textoUsuario = findViewById(R.id.textoUsuario);
        textoNombre = findViewById(R.id.textoNombre);
        textoApellidos = findViewById(R.id.textoApellidos);
        spnDia = findViewById(R.id.spnDia);
        spnMes = findViewById(R.id.spnMes);
        spnAny = findViewById(R.id.spnAny);

        ArrayAdapter<CharSequence> adapterDies = ArrayAdapter.createFromResource(getApplicationContext(), R.array.dies, android.R.layout.simple_spinner_item);
        adapterDies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDia.setAdapter(adapterDies);

        ArrayAdapter<CharSequence> adapterMesos = ArrayAdapter.createFromResource(getApplicationContext(), R.array.mesos, android.R.layout.simple_spinner_item);
        adapterMesos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMes.setAdapter(adapterMesos);

        ArrayAdapter<CharSequence> adapterAnys = ArrayAdapter.createFromResource(getApplicationContext(), R.array.anys, android.R.layout.simple_spinner_item);
        adapterAnys.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAny.setAdapter(adapterAnys);


        //fechaNacimiento = findViewById(R.id.fechaNacimiento);
        text = findViewById(R.id.text);
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            text.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        //System.out.println("El UID es: " + currentUser.getUid());


        /*
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
                    //Toast.makeText(getApplicationContext(), "Borrando...", Toast.LENGTH_LONG).show();
                    contadorCaracteres--;
                }
                return false;
            }
        });

         */
    }

    public void goToPage2(View view) {
        Intent intent = new Intent(this, Bienvenida2.class);
        startActivity(intent);
    }

    public void envia(View view) {
        String usuario = "";
        String nombre = "";
        String apellidos = "";
        String nacimiento = "";
        if (!textoUsuario.getText().toString().equals(""))
            usuario = textoUsuario.getText().toString();
        if (!textoNombre.getText().toString().equals("")) nombre = textoNombre.getText().toString();
        if (!textoApellidos.getText().toString().equals(""))
            apellidos = textoApellidos.getText().toString();
        System.out.println(spnDia.getSelectedItem().toString());

        if (usuario.length() > 0) {
            DatabaseReference myRef = database.getReference("Usuarios/" + currentUser.getUid());

            usuario += ("#" + mAuth.getCurrentUser().getUid().substring(0, 5));
            myRef.child("nick").setValue(usuario);
            myRef.child("fechaNacimiento").setValue(nacimiento);
            myRef.child("nombre").setValue(nombre + " " + apellidos);
            myRef.child("numRespuestas").setValue(0);
            myRef.child("numTemas").setValue(0);
            myRef.child("descripcion").setValue("");

            Intent intent = new Intent(this, MenuPrincipal.class);
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(textoUsuario, getResources().getText(R.string.AdvertenciaNickVacio), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
