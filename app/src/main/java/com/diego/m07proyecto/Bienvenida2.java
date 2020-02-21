package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Bienvenida2 extends AppCompatActivity {

    private TextView presentacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida2);
        presentacion = findViewById(R.id.presentacion);
        if(android.os.Build.VERSION.SDK_INT >= 26) {
            presentacion.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    public void goToPage3(View view) {
        Intent intent = new Intent(this, Bienvenida3.class);
        startActivity(intent);
        finish();
    }

    public void goToPage1(View view) {
        Intent intent = new Intent(this, Bienvenida.class);
        startActivity(intent);
        finish();
    }
}
