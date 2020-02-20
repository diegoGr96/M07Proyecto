package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static final int NUEVO_USUARIO_ACTIVITY_REQUEST_CODE = 1;

    private TextView textoUsuario;
    private TextView textoClave;
    private TextView textoRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoUsuario = findViewById(R.id.textoUsuario);
        textoClave = findViewById(R.id.textoClave);
        textoRegistro = findViewById(R.id.textoRegistrar);

        mAuth = FirebaseAuth.getInstance();

        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSegundaActivity = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                startActivityForResult(intentSegundaActivity, NUEVO_USUARIO_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        textoUsuario.setText("");
        textoClave.setText("");
    }

    public void iniciarSesion(View view) {
        final View viewButton = view;
        String usuario = textoUsuario.getText().toString().trim();
        String password = textoClave.getText().toString().trim();

        if (!usuario.equals("") &&  !password.equals("")) {
                mAuth.signInWithEmailAndPassword(usuario, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseUser aux = task.getResult().getUser();
                                    DatabaseReference myRef = database.getReference("Usuarios/"+aux.getUid()+"/Nick");
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            if(dataSnapshot.getValue().toString().equals(""))
                                                Toast.makeText(getApplicationContext(),"Es la primera vez que inicia sesión.",Toast.LENGTH_LONG).show();
                                            else{
                                                Intent intentSegundaActivity = new Intent(getApplicationContext(), MenuPrincipal.class);
                                                startActivity(intentSegundaActivity);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Sin hacer nada
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Snackbar.make(viewButton, getResources().getText(R.string.login_error), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }

                                // ...
                            }
                        });
        } else {
            //Toast.makeText(getApplicationContext(), "-"+usuario+"-"+password+"-", Toast.LENGTH_LONG).show();
            Snackbar.make(viewButton, getResources().getText(R.string.login_error), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NUEVO_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Snackbar.make(this.textoRegistro, getResources().getText(R.string.registroCorrecto), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (requestCode == NUEVO_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    this, "El usuario no ha sido registrado porque estaba vacio.", Toast.LENGTH_LONG).show();
        }
    }
}
