package com.diego.m07proyecto;

import android.widget.EditText;

import com.diego.m07proyecto.R;

import java.util.Arrays;
import java.util.List;

public class CambioColorEditText extends Thread {

    private List<Object> listaElementos;

    public CambioColorEditText(Object... elementos) {
        listaElementos = Arrays.asList(elementos);
    }

    @Override
    public void run() {
        try {
            Object elemento;
            for (int i = 0; i < listaElementos.size(); i++) {
                elemento = listaElementos.get(i);
                ((EditText) elemento).setBackgroundResource(R.drawable.back_cambios_guardados);
            }
            Thread.sleep(3000);
            for (int i = 0; i < listaElementos.size(); i++) {
                elemento = listaElementos.get(i);
                ((EditText) elemento).setBackgroundResource(R.drawable.back_edit_text);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
