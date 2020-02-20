package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public static final String KEY_INTENT_SEGUNDA_ACTIVITY = "keyEnviarSegundaActivity";
    public static final int NUEVO_USUARIO_ACTIVITY_REQUEST_CODE = 1;
    private final String USUARIO_KEY = "usuario";
    private final String CLAVE_KEY = "clave";

    private SharedPreferences preferencias;
    private String sharedPreFile = "com.diego.m07proyecto";

    private TextView textoUsuario;
    private TextView textoClave;
    private TextView textoRegistro;

    /*
    private String usuario;
    private String clave;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoUsuario = findViewById(R.id.textoUsuario);
        textoClave = findViewById(R.id.textoClave);
        textoRegistro = findViewById(R.id.textoRegistrar);

        mAuth = FirebaseAuth.getInstance();

       /* preferencias = getSharedPreferences(sharedPreFile, MODE_PRIVATE);

        usuario = preferencias.getString(USUARIO_KEY, "");
        clave = preferencias.getString(CLAVE_KEY, "");*/

        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSegundaActivity = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                //startActivity(intentSegundaActivity);
                startActivityForResult(intentSegundaActivity, NUEVO_USUARIO_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    public void iniciarSesion(View view) {
        String usuario = textoUsuario.getText().toString().trim();
        String password = textoClave.getText().toString().trim();
        if (!usuario.equals("") || !password.equals("")) {
                mAuth.signInWithEmailAndPassword(usuario, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intentSegundaActivity = new Intent(getApplicationContext(), MenuPrincipal.class);
                                    startActivity(intentSegundaActivity);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
        } else {
            Toast.makeText(getApplicationContext(), "-"+usuario+"-"+password+"-", Toast.LENGTH_LONG).show();

            //Toast.makeText(getApplicationContext(), "Los campos no pueden estár en blanco.", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NUEVO_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Snackbar.make(this.textoRegistro, getResources().getText(R.string.registroCorrecto), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            /*
            Toast.makeText(
                    this, "Se ha registrado al usuario.", Toast.LENGTH_LONG).show();
*/
        } else if (requestCode == NUEVO_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    this, "El usuario no ha sido registrado porque estaba vacio.", Toast.LENGTH_LONG).show();
        }
    }

/*    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor = preferencias.edit();
        preferencesEditor.putString(USUARIO_KEY, usuario);
        preferencesEditor.putString(CLAVE_KEY, clave);
        preferencesEditor.apply();
    }*/
}
