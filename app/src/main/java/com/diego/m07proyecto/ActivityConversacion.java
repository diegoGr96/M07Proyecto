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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ActivityConversacion extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference referenciaChat;
    private DatabaseReference referenciaNuevoMensaje;
    private DatabaseReference refSumarMensSinLeer;
    private ValueEventListener eventoNuevoMensaje;

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
    //private long mensajesSinLeer = 0;
    private String uidDestino;
    private String NOMBRE_CHAT;
    private String CORREO_CHAT_DESTINO;

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
        NOMBRE_CHAT = getIntent().getStringExtra("NOMBRE_CHAT");
        CORREO_CHAT_DESTINO = getIntent().getStringExtra("CORREO_CHAT");
        textoCorreoDestino.setText(CORREO_CHAT_DESTINO.length() > 27 ? CORREO_CHAT_DESTINO.split("@")[0] : CORREO_CHAT_DESTINO);

        //Esta referencia se usa una sola vez al principio para cargar la conversación.
        //Las siguientes veces solo se cargará el último mensaje enviado y/o recibido.
        //Referencia Testeo
        //referenciaChat = database.getReference("Chats/Chat_0/Mensajes");
        referenciaChat = database.getReference("Chats/" + NOMBRE_CHAT + "/Mensajes");
        Query queryCargarConversacion = referenciaChat.orderByChild("idMensaje");
        queryCargarConversacion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversacionListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (conversacionListh == null) {

                } else {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!(snapshot.getValue() instanceof Long)) {
                            Mensaje mensaje = Mensaje.convertMensaje(conversacionListh.get(snapshot.getKey()));
                            conversacionList.add(mensaje);
                        } else {
                            //long mapaHijo = (Long)snapshot.getValue();
                            contadorMensajes = (Long) snapshot.getValue();
                        }
                    }
                    DatabaseReference refReiniciarContMensSinLeer = database.getReference("RelacionChatUsuario/" + currentUser.getUid() + "/" + NOMBRE_CHAT + "/mensajesSinLeer");
                    refReiniciarContMensSinLeer.setValue(0);
                    mAdapterConversacion.notifyDataSetChanged();
                    mRecyclerView.getLayoutManager().scrollToPosition(conversacionList.size() - 1);
                    //mRecyclerView.getLayoutManager().scrollToPosition(conversacionList.size() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        eventoNuevoMensaje = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (firstAttempt) {
                    firstAttempt = false;
                } else {
                    contadorMensajes = dataSnapshot.getValue(Long.class);
                    //Referencia Testeo
                    //DatabaseReference refGetNuevoMensaje = database.getReference("Chats/Chat_0/Mensajes/Mens_" + (contadorMensajes - 1));
                    //Definitiva
                    DatabaseReference refGetNuevoMensaje = database.getReference("Chats/" + NOMBRE_CHAT + "/Mensajes/Mens_" + (contadorMensajes - 1));
                    refGetNuevoMensaje.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Toast.makeText(getApplicationContext(), "Nuevo Mensaje!!!", Toast.LENGTH_LONG).show();
                            conversacionListh.clear();
                            HashMap<String, Object> mapaNuevoMensaje = (HashMap<String, Object>) dataSnapshot.getValue();
                            final Mensaje mensaje = Mensaje.convertMensaje(mapaNuevoMensaje);

                            conversacionList.add(mensaje);
                            mAdapterConversacion.notifyDataSetChanged();
                            mRecyclerView.getLayoutManager().scrollToPosition(conversacionList.size() - 1);
                            if (!mensaje.getRemitente().equals(CORREO_CHAT_DESTINO)) {
                                DatabaseReference refGetUidDestino = database.getReference("RelacionChatUsuario/" + currentUser.getUid() + "/" + NOMBRE_CHAT + "/uidDestino");

                                refGetUidDestino.addListenerForSingleValueEvent(new ValueEventListener() {
                                    long mensajesSinLeer = -1;

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        uidDestino = dataSnapshot.getValue(String.class);
                                        refSumarMensSinLeer = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + NOMBRE_CHAT + "/mensajesSinLeer");
                                        refSumarMensSinLeer.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                mensajesSinLeer = dataSnapshot.getValue(Long.class);
                                                while (mensajesSinLeer == -1) ;
                                                refSumarMensSinLeer.setValue(mensajesSinLeer + 1);
                                                mensajesSinLeer = -1;
                                                String textoUltimoMensaje = mensaje.getTexto().length() > 28 ? mensaje.getTexto().substring(0, 29)+"..." : mensaje.getTexto();
                                                SimpleDateFormat fecha = new SimpleDateFormat("HH:mm   dd-MM-yyyy ", Locale.getDefault());
                                                //Chat Propio
                                                //Actualizamos el ultimoMensaje
                                                DatabaseReference refUltimoMensajeYFecha = database.getReference("RelacionChatUsuario/" + currentUser.getUid() + "/" + NOMBRE_CHAT + "/ultimoMensaje");
                                                refUltimoMensajeYFecha.setValue(textoUltimoMensaje);
                                                //Actualizamos Fecha ultimoMensaje
                                                refUltimoMensajeYFecha = database.getReference("RelacionChatUsuario/" + currentUser.getUid() + "/" + NOMBRE_CHAT + "/fechaUltimoMensaje");
                                                //Obtenemos la fecha y hora
                                                String currentDateandTime = fecha.format(new Date());
                                                refUltimoMensajeYFecha.setValue(currentDateandTime);
                                                //Chat Destino
                                                //Actualizamos el ultimoMensaje
                                                refUltimoMensajeYFecha = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + NOMBRE_CHAT + "/ultimoMensaje");
                                                refUltimoMensajeYFecha.setValue(textoUltimoMensaje);
                                                //Actualizamos Fecha ultimoMensaje
                                                refUltimoMensajeYFecha = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + NOMBRE_CHAT + "/fechaUltimoMensaje");
                                                refUltimoMensajeYFecha.setValue(currentDateandTime);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        //Testeo
        //referenciaNuevoMensaje = database.getReference("Chats/Chat_0/Mensajes/contadorMensajes");
        //Definitiva
        referenciaNuevoMensaje = database.getReference("Chats/" + NOMBRE_CHAT + "/Mensajes/contadorMensajes");
        referenciaNuevoMensaje.addValueEventListener(eventoNuevoMensaje);

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sNuevoMensaje = textoNuevoMensaje.getText().toString();
                if (sNuevoMensaje.length() > 0) {
                    while (contadorMensajes == -1) ;
                    //Referencia TESTEO
                    //DatabaseReference ref = database.getReference("Chats/Chat_0/Mensajes/Mens_" + contadorMensajes);
                    //Referencia DEFINITIVA
                    DatabaseReference ref = database.getReference("Chats/" + NOMBRE_CHAT + "/Mensajes/Mens_" + contadorMensajes);
                    Mensaje nuevoMensaje = new Mensaje(currentUser.getEmail(), sNuevoMensaje, contadorMensajes);
                    ref.setValue(nuevoMensaje);
                    //Referencia TESTEO
                    //ref = database.getReference("Chats/Chat_0/Mensajes/contadorMensajes");
                    //Referencia DEFINITIVA
                    ref = database.getReference("Chats/" + NOMBRE_CHAT + "/Mensajes/contadorMensajes");
                    ref.setValue(contadorMensajes + 1);
                    textoNuevoMensaje.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "He entrado en el ELSE", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onPause() {
        super.onPause();
        referenciaNuevoMensaje.removeEventListener(eventoNuevoMensaje);
        DatabaseReference refReiniciarContMensSinLeer = database.getReference("RelacionChatUsuario/" + currentUser.getUid() + "/" + NOMBRE_CHAT + "/mensajesSinLeer");
        refReiniciarContMensSinLeer.setValue(0);
        finish();
        //Falta hacer que se actualize la vista previa de los mensajes.
    }

}
