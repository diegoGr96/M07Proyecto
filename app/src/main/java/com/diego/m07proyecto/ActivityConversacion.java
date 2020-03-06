package com.diego.m07proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    ValueEventListener eventoChat;

    private RecyclerView mRecyclerView;
    private ConversacionAdapter mAdapterConversacion;

    private Map<String, HashMap<String, Object>> conversacionListh;
    private List<Mensaje> conversacionList;

    private FloatingActionButton fab;

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
        mAdapterConversacion = new ConversacionAdapter(getApplicationContext(), conversacionList,currentUser);
        mRecyclerView.setAdapter(mAdapterConversacion);
        //Testeo
        referenciaChat = database.getReference("Chats/Chat_0/Mensajes");
        //referenciaChat = database.getReference("RelacionChatUsuario/"+currentUser.getUid());
        eventoChat = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversacionListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (conversacionListh == null) {

                } else {
                    //conversacionList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!(snapshot.getValue() instanceof Long)){
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
        };

        referenciaChat.addValueEventListener(eventoChat);
    }
}
