package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferencias;
    private String sharedPreFile = "com.diego.m07proyecto";
    private final String USUARIO_KEY = "usuario";
    private final String CLAVE_KEY = "clave";

    public static final String KEY_INTENT_SEGUNDA_ACTIVITY = "keyEnviarSegundaActivity";
    private TextView textoUsuario;
    private TextView textoClave;
    private TextView textoRegistro;

    private String usuario;
    private String clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoUsuario = findViewById(R.id.textoUsuario);
        textoClave = findViewById(R.id.textoClave);
        textoRegistro = findViewById(R.id.textoRegistrar);

        preferencias = getSharedPreferences(sharedPreFile, MODE_PRIVATE);

        usuario = preferencias.getString(USUARIO_KEY, new String());
        clave = preferencias.getString(CLAVE_KEY, new String());
        textoUsuario.setText(usuario);
        textoClave.setText(clave);

        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "REGISTROOOO", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void iniciarSesion(View view) {
        Toast.makeText(getApplicationContext(), "INICIAR SESIOOON", Toast.LENGTH_LONG).show();
    }

/*
    public void enviarEdadSegundaActivity(View view) {
        if (!textoEdad.getText().toString().equals("") && Integer.parseInt(textoEdad.getText().toString()) > 1
                && Integer.parseInt(textoEdad.getText().toString()) < 100) {
            Intent intentSegundaActivity = new Intent(this, segundaActivity.class);
            intentSegundaActivity.putExtra(KEY_INTENT_SEGUNDA_ACTIVITY, textoEdad.getText().toString());
            textoEdad.setText("");
            startActivity(intentSegundaActivity);
        } else {
            Toast.makeText(this, "Edat invalida...", Toast.LENGTH_LONG).show();
        }
    }

 */

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor = preferencias.edit();
        preferencesEditor.putString(USUARIO_KEY, usuario);
        preferencesEditor.putString(CLAVE_KEY, clave);
        preferencesEditor.apply();
    }
}
