package com.diego.m07proyecto.ui.send;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.diego.m07proyecto.Chat;
import com.diego.m07proyecto.ChatAdapter;
import com.diego.m07proyecto.R;
import com.diego.m07proyecto.SearchUserAdapter;
import com.diego.m07proyecto.SearchUsers;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    DatabaseReference referenciaChat;
    ValueEventListener eventoChat;

    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapterChat;
    private SearchUserAdapter mAdapterSearch;

    private FloatingActionButton fab;

    private EditText textoBuscar;
    private ImageButton btnBuscar;
    private ImageButton btnCerrarBuscar;

    private Map<String, HashMap<String, Object>> chatListh;
    private List<Chat> chatList;

    private MediaPlayer audioMario = new MediaPlayer();
    private boolean firstAttempt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Aquí ha cargado la vista.
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        firstAttempt = true;
        chatList = new ArrayList<>();
        mRecyclerView = root.findViewById(R.id.recyclerChat);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapterChat = new ChatAdapter(getContext(), chatList);
        mRecyclerView.setAdapter(mAdapterChat);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String userrr = mAuth.getCurrentUser().getUid();
        //Testeo
        referenciaChat = database.getReference("RelacionChatUsuario/uidDiego");
        //referenciaChat = database.getReference("RelacionChatUsuario/"+currentUser.getUid());
        eventoChat = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                if (chatListh == null) {

                } else {
                    if (firstAttempt){
                        firstAttempt=false;
                    }else{
                        managerOfSound();
                    }
                    chatList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chat chat = Chat.convertChat(chatListh.get(snapshot.getKey()));
                        chatList.add(chat);
                    }
                    mAdapterChat = new ChatAdapter(getContext(), chatList);
                    mRecyclerView.setAdapter(mAdapterChat);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        };

        referenciaChat.addValueEventListener(eventoChat);

        textoBuscar = root.findViewById(R.id.textoBuscarChat);
        btnBuscar =  root.findViewById(R.id.btnBuscarChat);
        btnCerrarBuscar = root.findViewById(R.id.btnCerrarBuscar);

        textoBuscar.setVisibility(View.INVISIBLE);
        btnCerrarBuscar.setVisibility(View.INVISIBLE);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textoBuscar.getVisibility() == View.INVISIBLE){
                    textoBuscar.setVisibility(View.VISIBLE);
                    btnCerrarBuscar.setVisibility(View.VISIBLE);
                    textoBuscar.requestFocus();
                }else{
                    hideKeyboard(getActivity());
                    //contadorConsulta = contadorTemas;
                    String valorBusqueda = textoBuscar.getText().toString();
                    DatabaseReference dbRef = database.getReference("Usuarios/");
                    Query myQuery;
                    if (valorBusqueda.contains("@")){
                        myQuery = dbRef.orderByChild("email").equalTo(valorBusqueda);
                    }else{
                        myQuery = dbRef.orderByChild("nick").equalTo(valorBusqueda);
                    }
                    myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, HashMap<String, Object>> searchListh;
                            List<SearchUsers> searchResult = new ArrayList<>();

                            searchListh = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                            if (searchListh == null) {
                                Snackbar.make(mRecyclerView, getResources().getText(R.string.searchChatNotFound), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                textoBuscar.setText("");

                                SearchUsers userResult = null;
                                for(Map.Entry<String,HashMap<String,Object>> entry : searchListh.entrySet()){
                                    String key = entry.getKey();
                                    HashMap value = entry.getValue();

                                    String nickResult = String.valueOf(value.get("nick"));
                                    String emailResult = String.valueOf(value.get("email"));
                                    userResult = new SearchUsers(nickResult,emailResult);
                                }

                                searchResult.add(userResult);
                                mAdapterSearch = new SearchUserAdapter(getContext(), searchResult);
                                mRecyclerView.setAdapter(mAdapterSearch);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            //Log.w("VVVV", "Failed to read value.", error.toException());
                        }
                    });

                }
            }
        });

        btnCerrarBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                textoBuscar.setVisibility(View.INVISIBLE);
                btnCerrarBuscar.setVisibility(View.INVISIBLE);

                mRecyclerView.setAdapter(mAdapterChat);
            }
        });

        return root;
    }

    private void initializeData() {

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void managerOfSound() {
        audioMario = MediaPlayer.create(getContext(), R.raw.moneda_mario);
        if (!audioMario.isPlaying()) {
            audioMario.start();
        } else {
            audioMario.stop();
        }
        audioMario.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
    }

    public void onPause() {
        super.onPause();
        referenciaChat.removeEventListener(eventoChat);
    }
}