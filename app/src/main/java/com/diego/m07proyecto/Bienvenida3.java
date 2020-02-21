package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bienvenida3 extends AppCompatActivity {

    private TextView text;
    private Button btnEnvia;
    private EditText textoUsuario;
    private EditText textoNombre;
    private EditText textoApellidos;
    private EditText fechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida3);
        btnEnvia = findViewById(R.id.btnEnvia);
        textoUsuario = findViewById(R.id.textoUsuario);
        textoNombre = findViewById(R.id.textoNombre);
        textoApellidos = findViewById(R.id.textoApellidos);
        fechaNacimiento = findViewById(R.id.fechaNacimiento);
        text = findViewById(R.id.text);
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            text.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
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

        /*if(nacimiento.length() == 10) {  // Si se cambia para que vaya poniendo las barras durante la escritura del campo no hace falta hacer esta comprobación. Poner también que no deje escribir más de 10 carácteres.
            Date fecha = formatter.parse(nacimiento);
        } else{

        }
        */
    }
}
