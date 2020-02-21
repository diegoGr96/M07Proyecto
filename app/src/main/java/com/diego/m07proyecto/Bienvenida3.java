package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bienvenida3 extends AppCompatActivity {

    //private TextView descripcion;
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
        /*
        descripcion = findViewById(R.id.test);
        descripcion.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        NO VA POR LA API
        */
    }

    public void goToPage2(View view) {
        finish();
    }

    public void envia(View view) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String usuario = textoUsuario.getText().toString();
        String nombre = textoNombre.getText().toString();
        String apellidos = textoApellidos.getText().toString();
        String nacimiento = fechaNacimiento.getText().toString();
        System.out.println("La fecha " + nacimiento);
        System.out.println("La fecha " + nacimiento.length());
        if(nacimiento.length() == 8) {
            Date fecha = formatter.parse(nacimiento);
            System.out.println(usuario);
            System.out.println(nombre);
            System.out.println(apellidos);
            System.out.println(fecha);
            System.out.println(nacimiento);
        } else{
            System.out.println("La fecha no es correcta.");
        }
    }
}
