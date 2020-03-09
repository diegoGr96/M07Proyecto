package com.diego.m07proyecto;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    // Member variables.
    private List<Chat> mChatData;
    private Context mContext;

    /**
     * Constructor that passes in the games data and the context.
     *
     * @param chatsData ArrayList containing the gamess data.
     * @param context   Context of the application.
     */
    public ChatAdapter(Context context, List<Chat> chatsData) {
        this.mChatData = chatsData;
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
    public ChatAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ChatAdapter.ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.chat_item, parent, false));
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
    public void onBindViewHolder(ChatAdapter.ViewHolder holder,
                                 int position) {
        // Get current games.
        Chat currentChat = mChatData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentChat);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mChatData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView textoDestino;
        private TextView textoUltimoMensaje;
        private TextView textoFecha;
        private TextView textoMensajesSinLeer;



        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            textoDestino = itemView.findViewById(R.id.textoDestino);
            textoUltimoMensaje = itemView.findViewById(R.id.textoUltimoMensaje);
            textoFecha = itemView.findViewById(R.id.textoFecha);
            textoMensajesSinLeer = itemView.findViewById(R.id.textoMensajesSinLeer);


            itemView.setOnClickListener(this);
        }

        /*
        Mostramos los datos que hemos recibido a través del método 'onBindViewHolder' de la clase superior
        y nos encargamos de mostrarlos en pantalla.
         */
        void bindTo(Chat currentChat) {
            // Populate the textviews with data.

            textoDestino.setText(currentChat.getCorreoDestino());
            if (currentChat.getUltimoMensaje() != null){
                textoUltimoMensaje.setText(currentChat.getUltimoMensaje());
            }

            textoFecha.setText(currentChat.getFechaUltimoMensaje());
            int mensajesSinLeer = currentChat.getMensajesSinLeer();
            if (mensajesSinLeer>0) {
                textoMensajesSinLeer.setText(String.valueOf(mensajesSinLeer));
            }else{
                textoMensajesSinLeer.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onClick(View view) {
            Chat currentChat = mChatData.get(getAdapterPosition());
            Intent intent = new Intent(mContext,ActivityConversacion.class);
            intent.putExtra("NOMBRE_CHAT",currentChat.getNombreChat());
            intent.putExtra("CORREO_CHAT", currentChat.getCorreoDestino());
            mContext.startActivity(intent);
        }
    }
}

