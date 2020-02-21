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
        String usuario = textoUsuario.getText().toString();
        String nombre = textoNombre.getText().toString();
        String apellidos = textoApellidos.getText().toString();
        String nacimiento = fechaNacimiento.getText().toString();
        if(nacimiento.length() == 10) {
            Date fecha = formatter.parse(nacimiento);
        } else{

        }
    }
}
