package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_INTENT_SEGUNDA_ACTIVITY = "keyEnviarSegundaActivity";
    public static final int NUEVO_USUARIO_ACTIVITY_REQUEST_CODE = 1;
    private final String USUARIO_KEY = "usuario";
    private final String CLAVE_KEY = "clave";

    private SharedPreferences preferencias;
    private String sharedPreFile = "com.diego.m07proyecto";

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

        usuario = preferencias.getString(USUARIO_KEY, "");
        clave = preferencias.getString(CLAVE_KEY, "");

        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSegundaActivity = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                startActivityForResult(intentSegundaActivity, NUEVO_USUARIO_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    public void iniciarSesion(View view) {
        if (!textoUsuario.getText().toString().equals("") && !textoClave.getText().toString().equals("")) {
            if (textoUsuario.getText().toString().equals(usuario) &&
                    textoClave.getText().toString().equals(clave)) {
                Intent intentSegundaActivity = new Intent(getApplicationContext(), MenuPrincipal.class);
                startActivity(intentSegundaActivity);
            } else {
               Toast.makeText(getApplicationContext(),"Credenciales incorrectas", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Los campos no pueden estár en blanco.", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NUEVO_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            usuario = data.getStringExtra(RegistrarUsuarioActivity.EXTRA_REPLY_USUARIO);
            clave = data.getStringExtra(RegistrarUsuarioActivity.EXTRA_REPLY_CLAVE);

            Toast.makeText(
                    this, "Se ha registrado al usuario.", Toast.LENGTH_LONG).show();


        } else if (requestCode == NUEVO_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    this, "El usuario no ha sido registrado porque estaba vacio.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor = preferencias.edit();
        preferencesEditor.putString(USUARIO_KEY, usuario);
        preferencesEditor.putString(CLAVE_KEY, clave);
        preferencesEditor.apply();
    }
}
