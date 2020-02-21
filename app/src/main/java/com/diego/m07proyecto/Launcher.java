package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (isUserLogged() && connectionIsActive()) {
                    checkFirstLog();
                } else if (!connectionIsActive()) {
                    final Handler checkConnection = new Handler(getMainLooper());
                    checkConnection.postDelayed(new Runnable() {
                        int timer = 5000;

                        @Override
                        public void run() {
                            if (connectionIsActive()) {
                                if (isUserLogged()) {
                                    checkFirstLog();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                timer = timer > 60000 ? timer + 5000 : timer;
                            }
                            checkConnection.postDelayed(this, timer);
                        }
                    }, 10);
                } else {
                    Intent intentSegundaActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentSegundaActivity);
                    finish();
                }
            }
        }, 500);
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
        DatabaseReference myRef = database.getReference("Usuarios/" + userUid + "/Nick");
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
}
