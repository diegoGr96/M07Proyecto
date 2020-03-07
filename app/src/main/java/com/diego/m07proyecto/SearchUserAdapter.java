package com.diego.m07proyecto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    // Member variables.
    private List<SearchUsers> mSearchUserData;
    private Context mContext;
    private long contadorChats = -1;
    private String nickOrigen;
    private SearchUsers currentSearch;
    private String nuevoChat;
    private FirebaseUser currentUser;

    /**
     * Constructor that passes in the games data and the context.
     *
     * @param searchUsersData ArrayList containing the gamess data.
     * @param context         Context of the application.
     */
    public SearchUserAdapter(Context context, List<SearchUsers> searchUsersData) {
        this.mSearchUserData = searchUsersData;
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added
     *                 after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */

    /*
    Método encargado de crear el VIEWHOLDER para el Recycler View.
    Enlacamos los elementos graficos que queremos que salgan en cada HOLDER. En este proyecto se llama 'listItem'
     */
    @Override
    public SearchUserAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new SearchUserAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.search_users_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */

    /*
    Enlacamos los datos con cada ViewHolder.
    Obtenemos los datos del array list y se los pasamos al método 'bindTo'
    que se encargará de mostrarlos en pantalla.

     */
    @Override
    public void onBindViewHolder(SearchUserAdapter.ViewHolder holder,
                                 int position) {
        // Get current games.
        SearchUsers currentSearch = mSearchUserData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentSearch);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mSearchUserData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView textoSearchNick;
        private TextView textoSearchEmail;


        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            textoSearchNick = itemView.findViewById(R.id.textoSearchNick);
            textoSearchEmail = itemView.findViewById(R.id.textoSearchEmail);
            itemView.setOnClickListener(this);
        }

        /*
        Mostramos los datos que hemos recibido a través del método 'onBindViewHolder' de la clase superior
        y nos encargamos de mostrarlos en pantalla.
         */
        void bindTo(SearchUsers currentSearch) {
            // Populate the textviews with data.

            textoSearchNick.setText(currentSearch.getNickDestino());
            textoSearchEmail.setText(currentSearch.getCorreoDestino());

        }

        @Override
        public void onClick(View view) {
            currentSearch = mSearchUserData.get(getAdapterPosition());

            final String correoDestino = currentSearch.getCorreoDestino();
            final String uidDestino = currentSearch.getUidDestino();
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();

            DatabaseReference referenciaNickOrigen = database.getReference("Usuarios/" + currentUser.getUid() + "/nick");
            referenciaNickOrigen.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    nickOrigen = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //Procedemos a mirar si el usuario que hemos seleccionado en el buscador ya tiene una conversación con nosotros.
            //Lo miramos en la Tabla 'RelacionChatUsuario'
            DatabaseReference referenciaSearchUser = database.getReference("RelacionChatUsuario/" + currentUser.getUid());
            Query query = referenciaSearchUser.orderByChild("correoDestino").equalTo(correoDestino);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                Map<String, HashMap<String, Object>> mapaResultado;

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mapaResultado = (Map<String, HashMap<String, Object>>) dataSnapshot.getValue();
                    //Si no hemos encontrado ningún resultado quiere decir que nunca hemos hablado con esa persona.
                    //Por lo tando tenemos que inicializar dicha conversación y ingresar un nuevo campo en la tabla 'RelacionChatUsuario'
                    //para la nueva conversación.
                    if (mapaResultado == null) {
                        inicializarNuevoChat(database, currentUser, correoDestino, uidDestino);

                    } else {
                        HashMap<String, Object> mapaHijo;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            mapaHijo = mapaResultado.get(snapshot.getKey());
                            nuevoChat = (String) mapaHijo.get("nombreChat");
                            break;
                        }
                        //operacionesTerminadas = true;
                        Intent intentConversacion = new Intent(mContext, ActivityConversacion.class);
                        intentConversacion.putExtra("NOMBRE_CHAT", nuevoChat);
                        intentConversacion.putExtra("CORREO_CHAT", currentSearch.getCorreoDestino());
                        intentConversacion.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intentConversacion);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void inicializarNuevoChat(final FirebaseDatabase database, final FirebaseUser currentUser, final String correoDestino, final String uidDestino) {
        //Primero crearemos la nueva Tabla en 'Chats', para eso primero necesitamos obtener el contador de chats
        //Una vez lo obtengamos ya podremos crear el nuevo chat.
        final DatabaseReference contadorChatsReference = database.getReference("Chats/contadorChats");
        contadorChatsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //En este punto ya tenemos el contador de chats y estamos en posición de crear el nuevo tema.
                //Crearemos el nuevo tema inicializando su propio contador de mensajes a 0.
                contadorChats = (long) dataSnapshot.getValue();

                while (contadorChats == -1 && nickOrigen == null) ;
                nuevoChat = "Chat_" + contadorChats;
                //newChatReference = database.getReference("Chats/" + nuevoChat + "/Mens_0/Texto");
                //newChatReference.setValue(generarMensajeIniciacion(nickOrigen, currentSearch.getNickDestino()));
                DatabaseReference newChatReference = database.getReference("Chats/" + nuevoChat + "/Mensajes/contadorMensajes");
                newChatReference.setValue(0);
                //Actualizamos el valor del contador de Chats en Firebase porque ya se ha creado el nuevo Chat.
                contadorChatsReference.setValue(contadorChats + 1);
                //En este punto ya hemos creado el nuevo chat y procederemos a crear el nuevo campo en la tabla 'RelacionChatUsuario'
                //la ininializaremos con el nombre del chat, el correo destino y el UID destino.
                //- El nombre del chat lo obtenemos a partir del contador de chats
                //- El correo destino lo tenemos del Objeto 'currentSearch'
                //- El UID destino lo tenemos a del Objeto 'currentSearch'
                iniciarRelacionOrigen(database, currentUser.getUid(), correoDestino, uidDestino);
                //En este punto hemos creado el nuevo chat en la tabla 'RelacionChatUsuario' y la hemos inicializado con el campo 'nombreChat'
                //y el campo 'correoDestino'.
                //Por último tenemos que crear la tabla 'RelacionChatUsuario' para el usuario destino.
                iniciarRelacionDestino(database, uidDestino);
                //Después de todas estas operaciones solo nos queda asignar el valor a la variable de clase 'nuevoChat' y inicializar la nueva activity.
                //operacionesTerminadas = true;
                Intent intentConversacion = new Intent(mContext, ActivityConversacion.class);
                intentConversacion.putExtra("NOMBRE_CHAT", nuevoChat);
                intentConversacion.putExtra("CORREO_CHAT", currentSearch.getCorreoDestino());
                intentConversacion.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentConversacion);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //En este caso quiere decir que ya tenemos una conversación iniciada con este usuario.
    }

    private void iniciarRelacionOrigen(final FirebaseDatabase database, String uidOrigen, String correoDestino, String uidDestino) {
        DatabaseReference newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidOrigen + "/" + nuevoChat + "/nombreChat");
        newRelacionChatReference.setValue("Chat_" + contadorChats);
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidOrigen + "/" + nuevoChat + "/correoDestino");
        newRelacionChatReference.setValue(correoDestino);
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidOrigen + "/" + nuevoChat + "/uidDestino");
        newRelacionChatReference.setValue(uidDestino);
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidOrigen + "/" + nuevoChat + "/nickDestino");
        newRelacionChatReference.setValue(currentSearch.getNickDestino());
        /*
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidOrigen + "/" + nuevoChat + "/ultimoMensaje");
        //String ultimoMensaje = generarMensajeIniciacion(nickOrigen, currentSearch.getNickDestino());
        newRelacionChatReference.setValue(primerMensaje);

         */
    }

    private void iniciarRelacionDestino(final FirebaseDatabase database, String uidDestino) {
        DatabaseReference newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + nuevoChat + "/nombreChat");
        newRelacionChatReference.setValue("Chat_" + contadorChats);
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + nuevoChat + "/correoDestino");
        newRelacionChatReference.setValue(currentUser.getEmail());
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + nuevoChat + "/uidDestino");
        newRelacionChatReference.setValue(currentUser.getUid());
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + nuevoChat + "/nickDestino");
        newRelacionChatReference.setValue(nickOrigen);
        /*
        newRelacionChatReference = database.getReference("RelacionChatUsuario/" + uidDestino + "/" + nuevoChat + "/ultimoMensaje");
        //primerMensaje = generarMensajeIniciacion(currentSearch.getNickDestino(), nickOrigen);
        newRelacionChatReference.setValue(primerMensaje);

         */
    }
/*
    private String generarMensajeIniciacion(String nickOrigen, String nickDestino) {
        String mensaje1 = mContext.getApplicationContext().getString(R.string.mensajeIniciacion1);
        String mensaje2 = mContext.getApplicationContext().getString(R.string.mensajeIniciacion2);
        String mensaje3 = mContext.getApplicationContext().getString(R.string.mensajeIniciacion3);
        return mensaje1 + nickOrigen + mensaje2 + nickDestino + mensaje3 + " 🎉🎉";
    }
*/
}
