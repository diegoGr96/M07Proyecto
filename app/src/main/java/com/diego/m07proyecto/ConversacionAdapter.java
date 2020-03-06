package com.diego.m07proyecto;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConversacionAdapter extends RecyclerView.Adapter<ConversacionAdapter.ViewHolder> {
    // Member variables.
    private List<Mensaje> mMensajeData;
    private Context mContext;
    private FirebaseUser currentUser;
    private boolean isMe;
    /**
     * Constructor that passes in the games data and the context.
     *
     * @param mensajeData ArrayList containing the gamess data.
     * @param context   Context of the application.
     */
    public ConversacionAdapter(Context context, List<Mensaje> mensajeData, FirebaseUser currentUser) {
        this.mMensajeData = mensajeData;
        this.mContext = context;
        this.currentUser = currentUser;
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
    public ConversacionAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        if (viewType == 1){
            return new ConversacionAdapter.ViewHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.conversacion_item_origen, parent, false));
        }else{
            return new ConversacionAdapter.ViewHolder(LayoutInflater.from(mContext).
                    inflate(R.layout.conversacion_item_destino, parent, false));
        }
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
    public void onBindViewHolder(ConversacionAdapter.ViewHolder holder,
                                 int position) {
        // Get current games.
        Mensaje currentMensaje = mMensajeData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentMensaje);
    }

    @Override
    public int getItemViewType(int position) {
        if (mMensajeData.get(position).getCorreoDestino().equals(currentUser.getEmail())){
            isMe = true;
            return 1;
        }else{
            isMe = false;
            return 0;
        }
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mMensajeData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        // Member Variables for the TextViews
        private TextView mensaje;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            if (isMe){
                mensaje = itemView.findViewById(R.id.textoConversacionOrigen);
            }else{
                mensaje = itemView.findViewById(R.id.textoConversacionDestino);
            }
        }

        /*
        Mostramos los datos que hemos recibido a través del método 'onBindViewHolder' de la clase superior
        y nos encargamos de mostrarlos en pantalla.
         */
        void bindTo(Mensaje currentMensaje) {
            // Populate the textviews with data.
            mensaje.setText(currentMensaje.getMensaje());
        }
    }
}

