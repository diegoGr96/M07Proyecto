package com.diego.m07proyecto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_USUARIO = "usuario_devuelto";
    public static final String EXTRA_REPLY_CLAVE = "clave_devuelto";

    private TextView textoNombreUsuario;
    private TextView textoClaveUsuario;
    private RadioButton rbPublico;
    private RadioButton rbPrivado;
    private Spinner spGenero;
    private CheckBox checkCondiciones;
    private Switch swRecibirUpdate;
    private SeekBar sbEdad;
    private TextView valueEdad;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        textoNombreUsuario = findViewById(R.id.textoUsuario);
        textoClaveUsuario = findViewById(R.id.textoClave);
        rbPublico = findViewById(R.id.rbPublico);
        rbPrivado = findViewById(R.id.rbPrivado);
        spGenero = findViewById(R.id.spGenero);
        checkCondiciones = findViewById(R.id.checkCondiciones);
        swRecibirUpdate = findViewById(R.id.swRecibirUpdate);
        sbEdad = findViewById(R.id.sbEdad);
        valueEdad = findViewById(R.id.valueEdad);

        List<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("Hombre");
        spinnerArray.add("Mujer");
        spinnerArray.add("Sin especificar");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapter);

        swRecibirUpdate.setChecked(true);

        sbEdad.setMin(12);
        sbEdad.setMax(100);
        sbEdad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueEdad.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        valueEdad.setText(String.valueOf(sbEdad.getProgress()));
    }

    public void registrarse(View view) {
        if(checkCondiciones.isChecked()) {
            Intent respuestaIntent = new Intent();
            if (TextUtils.isEmpty(textoNombreUsuario.getText()) || TextUtils.isEmpty(textoClaveUsuario.getText())) {
                setResult(RESULT_CANCELED, respuestaIntent);
            } else {
                String usuario = textoNombreUsuario.getText().toString();
                String clave = textoClaveUsuario.getText().toString();

                respuestaIntent.putExtra(EXTRA_REPLY_USUARIO, usuario);
                respuestaIntent.putExtra(EXTRA_REPLY_CLAVE, clave);
                setResult(RESULT_OK, respuestaIntent);
            }
            finish();
        } else{
            Toast.makeText(this, "Tienes que aceptar las Condiciones y los Términos de servicio.", Toast.LENGTH_LONG).show();
        }
    }
}
