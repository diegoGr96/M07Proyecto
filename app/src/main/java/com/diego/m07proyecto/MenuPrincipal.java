package com.diego.m07proyecto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private TextView nickShow;
    private TextView emailShow;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private DatabaseReference myRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        database = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fabMenuPrincipal);

       /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/







       /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        //fragmentManager.getFragment().getLayoutInflater().inflate();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                super.onFragmentPreAttached(fm, f, context);
                System.out.println("1");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
                System.out.println("context: " + context);
            }

            @Override
            public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                super.onFragmentAttached(fm, f, context);
                System.out.println("2");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
                System.out.println("context: " + context);
            }

            @Override
            public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentPreCreated(fm, f, savedInstanceState);
                System.out.println("3");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
                System.out.println("savedInstanceState: " + savedInstanceState);
            }

            @Override
            public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
                System.out.println("4");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
                System.out.println("savedInstanceState: " + savedInstanceState);
            }

            @Override
            public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState);
                System.out.println("5");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
                System.out.println("savedInstanceState: " + savedInstanceState);
            }

            @Override
            public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                System.out.println("6");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
                System.out.println("v: " + v);
                System.out.println("savedInstanceState: " + savedInstanceState);
            }

            @Override
            public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentStarted(fm, f);
                System.out.println("7");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
            }

            @Override
            public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentResumed(fm, f);
                System.out.println("8");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
            }

            @Override
            public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentPaused(fm, f);
                System.out.println("9");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
            }

            @Override
            public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentStopped(fm, f);
                System.out.println("10");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
            }

            @Override
            public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
                super.onFragmentSaveInstanceState(fm, f, outState);
                System.out.println("11");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
                System.out.println("outState" + outState);
            }

            @Override
            public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
                System.out.println("12");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
            }

            @Override
            public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDestroyed(fm, f);
                System.out.println("13");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
            }

            @Override
            public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDetached(fm, f);
                System.out.println("14");
                System.out.println("fm: " + fm);
                System.out.println("f: " + f);
            }
        }, true);

        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_newtheme,
                R.id.nav_search, R.id.nav_signout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View hView = navigationView.getHeaderView(0);

        nickShow = hView.findViewById(R.id.miNick);
        emailShow = hView.findViewById(R.id.miEmail);

        getUserNick();
        emailShow.setText(getUserEmail());

        toolbar.announceForAccessibility(getString(R.string.loginCorrecto));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void signOut(MenuItem item) {
        mAuth.signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getUserNick() {
        // Read from the database
        myRef = database.getReference("Usuarios/"+mAuth.getCurrentUser().getUid()+"/nick");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                nickShow.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });
    }

    public String getUserEmail() {
        return mAuth.getCurrentUser().getEmail();
    }

    public void cerrarSesion(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        mAuth.signOut();
        finish();
    }
}
