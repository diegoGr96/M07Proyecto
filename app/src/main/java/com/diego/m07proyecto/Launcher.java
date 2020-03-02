package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class Launcher extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    FirebaseUser usuario;
    private boolean postback = true;
    int timer = 5000;
    int timerAux;
    private TextView txtReconnecting;
    //Handler checkConnection = new Handler(getMainLooper());
    Handler txtReconnectingVisualizer = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        txtReconnecting = findViewById(R.id.txtReconnecting);
        final Handler handler = new Handler();



        /*
        handler.postDelayed(new Runnable() {
            public void run() {
                if (isUserLogged() && connectionIsActive()) {
                    checkFirstLog();
                } else if (!connectionIsActive()) {
                    checkConnection.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            timerAux = timer;
                            txtReconnecting.setText(getResources().getString(R.string.connection_wait) + " " + (timerAux / 1000));
                            txtReconnectingVisualizer.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Holaaa");
                                    txtReconnecting.setText(getResources().getString(R.string.connection_wait) + " " + (timerAux / 1000));
                                    timerAux -= 1000;
                                    if (timerAux != 0) {
                                        txtReconnectingVisualizer.postDelayed(this, 1000);
                                    }
                                }
                            }, 1000);

        if (connectionIsActive()) {
            if (isUserLogged()) {
                checkFirstLog();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                postback = false;
                finish();
            }
        } else {
            if (timer < 30000) timer += 5000;
            System.out.println(timer);
        }
        if (postback) {
            checkConnection.postDelayed(this, timer);
        }

    }
                    }, 10);
                } else {
                    Intent intentSegundaActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentSegundaActivity);
                    finish();
                }
            }
        }, 500);
        *//*
        if (connectionIsActive()) {
            if (isUserLogged()) {
                checkFirstLog();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                postback = false;
                finish();
            }
        } else {
            try {
                Toast.makeText(Launcher.this, "Reconectadndo", Toast.LENGTH_LONG).show();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (timer < 30000) timer += 5000;
            System.out.println("aaaaa" + timer);
        }
        if (postback) {
            //checkConnection.postDelayed(this, timer);
        }
*/

    }

    public void onResume() {
        super.onResume();
        Reconector reconector = new Reconector();
        Thread hilo = new Thread(reconector);
        hilo.start();
    }

    public boolean isUserLogged() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public boolean connectionIsActive() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void checkFirstLog() {
        final String userUid = usuario.getUid();
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
                postback = false;
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Sin hacer nada
            }
        });
    }

    private void checkInicioSesion() {
        if (connectionIsActive()) {
            if (isUserLogged()) {
                checkFirstLog();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                postback = false;
                finish();
            }
        } else {
            try {
                Toast.makeText(Launcher.this, "Reconectadndo", Toast.LENGTH_LONG).show();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (timer < 30000) timer += 5000;
            System.out.println("aaaaa" + timer);
        }
        if (postback) {
            //checkConnection.postDelayed(this, timer);
        }
    }

    private class Reconector extends Thread {
        private int temporizador = 0;

        @Override
        public void run() {

            while (postback) {
                if (connectionIsActive()) {
                    postback = false;
                } else {
                    temporizador += 3000;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            txtReconnecting.setText("Sin conexión, Reconectando en " + (temporizador / 1000) + " segundos. ");
                            //Snackbar.make(txtReconnecting,"Reconectando en " + (temporizador/1000) +" segundos. ", Snackbar.LENGTH_LONG)
                            // .setAction("Action", null).show();
                            //Toast.makeText(Launcher.this, "Reconectando en " + (temporizador/1000) +" segundos. ", Toast.LENGTH_LONG).show();
                        }
                    });
                    espera(temporizador);
                }
            }
            checkInicioSesion();
        }
    }

    private void espera(int mili) {
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


