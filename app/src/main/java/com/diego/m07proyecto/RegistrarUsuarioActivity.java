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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

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
    }

    public void registrarse(View view) {
        final View viewButton = view;
        if (checkCondiciones.isChecked()) {
            String email = textoUsuario.getText().toString().trim();
            String password = textoClave.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent returnIntent = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    FirebaseUser aux = task.getResult().getUser();
                                    DatabaseReference myRef = database.getReference("Usuarios/" + aux.getUid());
                                    myRef.child("email").setValue(aux.getEmail());
                                    myRef.child("nick").setValue("");
                                    myRef.child("uid").setValue(aux.getUid());
                                    finish();
                                } else {
                                    Log.w("a", "createUserWithEmail:failure", task.getException());
                                    Snackbar.make(viewButton, getResources().getText(R.string.registroErroneo), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
            } else {
                Snackbar.make(viewButton, getResources().getText(R.string.register_null_fields), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else {
            Snackbar.make(viewButton, getResources().getText(R.string.register_mark_checkbox), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
