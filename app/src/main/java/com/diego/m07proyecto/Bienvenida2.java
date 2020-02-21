package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Bienvenida2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida2);
    }

    public void goToPage3(View view) {
        Intent intent = new Intent(this, Bienvenida3.class);
        startActivity(intent);
        finish();
    }

    public void goToPage1(View view) {
        finish();
    }
}
