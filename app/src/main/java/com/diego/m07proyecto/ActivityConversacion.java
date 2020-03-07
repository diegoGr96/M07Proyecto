package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityConversacion extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    DatabaseReference referenciaChat;
    DatabaseReference referenciaNuevoMensaje;
    ValueEventListener eventoChat;

    private RecyclerView mRecyclerView;
    private ConversacionAdapter mAdapterConversacion;
    RecyclerView.LayoutManager layoutManager;

    private Map<String, HashMap<String, Object>> conversacionListh;
    private List<Mensaje> conversacionList;

    private TextView textoCorreoDestino;
    private ImageButton btnVolver;
    private EditText textoNuevoMensaje;
    private ImageButton btnEnviarMensaje;

    private boolean firstAttempt = true;
    private long contadorMensajes = -1;
    // private String nombreChat;
    //private String CORREO_CHAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        textoCorreoDestino = findViewById(R.id.textoNickConversacion);
        btnVolver = findViewById(R.id.btnVolverConversacion);
        textoNuevoMensaje = findViewById(R.id.textoNuevoMensaje);
        btnEnviarMensaje = findViewById(R.id.btnEnviarMensaje);


        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        conversacionList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerConversacion);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapterConversacion = new ConversacionAdapter(getApplicationContext(), conversacionList, currentUser);
        mRecyclerView.setAdapter(mAdapterConversacion);

        //En este punto obtendremos el valor que recibimos del Intent para saber en que Chat nos encontramos.
        final String NOMBRE_CHAT = getIntent().getStringExtra("NOMBRE_CHAT");
        final String CORREO_CHAT = getIntent().getStringExtra("CORREO_CHAT");


        textoCorreoDestino.setText(CORREO_CHAT.length() > 27 ? CORREO_CHAT.split("@")[0] : CORREO_CHAT);

        //Referencia Testeo
        referenciaChat = database.getReference("Chats/Chat_0/Mensajes");
        referenciaChat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversacionListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (conversacionListh == null) {

                } else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!(snapshot.getValue() instanceof Long)) {
                            Mensaje mensaje = Mensaje.convertMensaje(conversacionListh.get(snapshot.getKey()));
                            conversacionList.add(mensaje);
                        }
                    }
                    mAdapterConversacion.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //Testeo
        referenciaNuevoMensaje = database.getReference("Chats/Chat_0/Mensajes/contadorMensajes");
        //referenciaChat = database.getReference("RelacionChatUsuario/"+currentUser.getUid());
        referenciaNuevoMensaje.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (firstAttempt) {
                    firstAttempt = false;
                } else {
                    contadorMensajes = dataSnapshot.getValue(Long.class);
                    //Referencia Testeo
                    DatabaseReference refGetNuevoMensaje = database.getReference("Chats/Chat_0/Mensajes/Mens_" + (contadorMensajes - 1));
                    refGetNuevoMensaje.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Toast.makeText(getApplicationContext(), "Nuevo Mensaje!!!", Toast.LENGTH_LONG).show();
                            conversacionListh.clear();
                            HashMap<String, Object> mapaNuevoMensaje = (HashMap<String, Object>) dataSnapshot.getValue();
                            //String remitente = (String) mapaNuevoMensaje.get("Remitente");
                            //String textoMensaje = (String) mapaNuevoMensaje.get("Texto");
                            Mensaje mensaje = Mensaje.convertMensaje(mapaNuevoMensaje);

                            conversacionList.add(mensaje);
                            mAdapterConversacion.notifyDataSetChanged();
                            mRecyclerView.getLayoutManager().scrollToPosition(conversacionList.size() - 1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNuevoMensaje = textoNuevoMensaje.getText().toString();
                if (sNuevoMensaje.length() > 0) {
                    while (contadorMensajes == -1) ;
                    DatabaseReference ref = database.getReference("Chats/"+NOMBRE_CHAT+"Mensajes/");
                } else {

                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
/*
        RecyclerView.SmoothScroller smoothScroller = new
                LinearSmoothScroller(getApplicationContext()) {
                    @Override
                    protected int getVerticalSnapPreference() {
                        return LinearSmoothScroller.SNAP_TO_END;
                    }
                };

 */

    }
/*
    private String generarMensajeIniciacion(String nickOrigen, String nickDestino) {
        String mensaje1 = getApplicationContext().getApplicationContext().getString(R.string.mensajeIniciacion1);
        String mensaje2 = getApplicationContext().getApplicationContext().getString(R.string.mensajeIniciacion2);
        String mensaje3 = getApplicationContext().getApplicationContext().getString(R.string.mensajeIniciacion3);
        return mensaje1 + nickOrigen + mensaje2 + nickDestino + mensaje3 + " 🎉🎉";
    }

 */
}
