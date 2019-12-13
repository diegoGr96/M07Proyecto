package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
}
