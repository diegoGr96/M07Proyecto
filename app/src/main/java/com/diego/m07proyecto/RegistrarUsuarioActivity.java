package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_USUARIO = "usuario_devuelto";
    public static final String EXTRA_REPLY_CLAVE = "clave_devuelto";

    private TextView textoNombreUsuario;
    private TextView textoClaveUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        textoNombreUsuario = findViewById(R.id.textonombreUsuario);
        textoClaveUsuario = findViewById(R.id.textoClaveUsuario);
    }

    public void addArticulo(View view) {
        Intent respuestaIntent = new Intent();
        if (TextUtils.isEmpty(textoNombreUsuario.getText()) || TextUtils.isEmpty(textoClaveUsuario.getText())) {
            setResult(RESULT_CANCELED, respuestaIntent);
        } else {
            String usuario = textoNombreUsuario.getText().toString();
            String clave = textoClaveUsuario.getText().toString();

            respuestaIntent.putExtra(EXTRA_REPLY_USUARIO, usuario);
            respuestaIntent.putExtra(EXTRA_REPLY_CLAVE, clave);
            setResult(RESULT_OK, respuestaIntent);
        }
        finish();
    }
}
