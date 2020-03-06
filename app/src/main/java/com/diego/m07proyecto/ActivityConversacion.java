package com.diego.m07proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

    private Map<String, HashMap<String, Object>> conversacionListh;
    private List<Mensaje> conversacionList;

    private boolean firstAttempt = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversacion);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        conversacionList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerConversacion);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapterConversacion = new ConversacionAdapter(getApplicationContext(), conversacionList, currentUser);
        mRecyclerView.setAdapter(mAdapterConversacion);
        referenciaChat = database.getReference("Chats/Chat_0/Mensajes");
        referenciaChat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversacionListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (conversacionListh == null) {

                } else {
                    //conversacionList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!(snapshot.getValue() instanceof Long)) {
                            Mensaje mensaje = Mensaje.convertConversacion(conversacionListh.get(snapshot.getKey()));
                            conversacionList.add(mensaje);
                        }
                    }
                    mAdapterConversacion.notifyDataSetChanged();
                    //mAdapterConversacion = new ConversacionAdapter(getApplicationContext(), conversacionList,currentUser);
                    //mRecyclerView.setAdapter(mAdapterConversacion);
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
                    long idNuevoMensaje = dataSnapshot.getValue(Long.class);
                    DatabaseReference refGetNuevoMensaje = database.getReference("Chats/Chat_0/Mensajes/Mens_" + (idNuevoMensaje - 1));
                    refGetNuevoMensaje.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Toast.makeText(getApplicationContext(), "Nuevo Mensaje!!!", Toast.LENGTH_LONG).show();
                            conversacionListh.clear();
                            Map<String, Object> mapaNuevoMensaje = (HashMap<String, Object>) dataSnapshot.getValue();
                            String remitente = (String) mapaNuevoMensaje.get("Remitente");
                            String textoMensaje = (String) mapaNuevoMensaje.get("Texto");
                            Mensaje mensaje = new Mensaje(remitente, textoMensaje);
                            //Mensaje mensaje = Mensaje.convertConversacion(mapaNuevoMensaje.get(snapshot.getKey()));
                            conversacionList.add(mensaje);
                            mAdapterConversacion.notifyDataSetChanged();
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
