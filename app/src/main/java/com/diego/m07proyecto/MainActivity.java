package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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
    private TextView textoRecuperarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            checkLogin(currentUser);
            //System.out.println("NO SOY NULL");
        }

        textoUsuario = findViewById(R.id.textoUsuario);
        textoClave = findViewById(R.id.textoClave);
        textoRegistro = findViewById(R.id.textoRegistrar);
        textoRecuperarPassword = findViewById(R.id.textoRecuperarContrasenya);

        textoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentSegundaActivity = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                startActivityForResult(intentSegundaActivity, NUEVO_USUARIO_ACTIVITY_REQUEST_CODE);

            }
        });
        textoRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textoUsuario.getText().toString();
                if (!email.isEmpty()) {
                    Snackbar.make(textoUsuario,  getResources().getText(R.string.string_cargando), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                textoRecuperarPassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.fabLoad));
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        textoRecuperarPassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorRecuperarPassword));
                                        Snackbar.make(textoUsuario, getResources().getText(R.string.recuperar_password_success), Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                }, 2000);


                            } else {
                                Snackbar.make(textoUsuario, getResources().getText(R.string.error_recuperar_contrasenya), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }
                    });
                }else{
                    Snackbar.make(textoUsuario, getResources().getText(R.string.recuperar_password_vacio), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

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

        if ((!usuario.equals("") && !password.equals("")) && isNetworkAvailable()) {
            mAuth.signInWithEmailAndPassword(usuario, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d("", "signInWithEmail:success");
                                //FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseUser currentUser = task.getResult().getUser();
                                checkLogin(currentUser);
                            } else {
                                // If sign in fails, display a message to the user.
                                Snackbar.make(viewButton, getResources().getText(R.string.login_error), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                            // ...
                        }
                    });
        } else {

            if (!isNetworkAvailable()) {
                Snackbar.make(viewButton, getResources().getString(R.string.no_hay_conexion), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                //Toast.makeText(getApplicationContext(), "-"+usuario+"-"+password+"-", Toast.LENGTH_LONG).show();
                Snackbar.make(viewButton, getResources().getText(R.string.login_error), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NUEVO_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Snackbar.make(this.textoRegistro, getResources().getText(R.string.registroCorrecto), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void checkLogin(FirebaseUser aux) {
        final String userUid = aux.getUid();
        DatabaseReference myRef = database.getReference("Usuarios/" + userUid + "/nick");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals("")) {
                    Intent intentSegundaActivity = new Intent(getApplicationContext(), Bienvenida.class);
                    startActivity(intentSegundaActivity);
                } else {
                    Intent intentSegundaActivity = new Intent(getApplicationContext(), MenuPrincipal.class);
                    startActivity(intentSegundaActivity);
                }
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Sin hacer nada
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
