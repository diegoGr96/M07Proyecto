package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public static final String EXTRA_REPLY_USUARIO = "usuario_devuelto";
    public static final String EXTRA_REPLY_CLAVE = "clave_devuelto";

    private CheckBox checkCondiciones;
    private Button botonRegistrarse;
    private EditText textoUsuario;
    private EditText textoClave;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        mAuth = FirebaseAuth.getInstance();

        checkCondiciones = findViewById(R.id.checkCondiciones);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        textoUsuario = findViewById(R.id.textoUsuario);
        textoClave = findViewById(R.id.textoClave);

        botonRegistrarse.setEnabled(false);

    }
/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
*/
    public void botonCondiciones(View view){
        botonRegistrarse.setEnabled(checkCondiciones.isChecked());
    }

    public void registrarse(View view) {
       final View viewButton = view;
        if(checkCondiciones.isChecked()) {
            String email = textoUsuario.getText().toString().trim();
            String password = textoClave.getText().toString().trim();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent returnIntent = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            } else {

                                Log.w("a", "createUserWithEmail:failure", task.getException());
                                /*Toast.makeText(RegistrarUsuarioActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();*/
                                Snackbar.make(viewButton, getResources().getText(R.string.registroErroneo), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });
        } else{
            Toast.makeText(this, "Tienes que aceptar las Condiciones y los Términos de servicio.", Toast.LENGTH_LONG).show();
        }
    }
}
