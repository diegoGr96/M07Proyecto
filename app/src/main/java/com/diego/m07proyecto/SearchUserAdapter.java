package com.diego.m07proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {
    // Member variables.
    private List<SearchUsers> mSearchUserData;
    private Context mContext;

    /**
     * Constructor that passes in the games data and the context.
     *
     * @param searchUsersData ArrayList containing the gamess data.
     * @param context   Context of the application.
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
            /*
            Chat currentChat = mChatData.get(getAdapterPosition());
            //Log.d("A", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAsddfnuirn");
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("ID_TEMA", currentChat.getIdTema());
            detailIntent.putExtra("TITLE", currentChat.getTitulo());
            detailIntent.putExtra("USER", currentChat.isAnonimo() ? mContext.getResources().getString(R.string.temaUsuarioAnonimo) : currentChat.getNickAutor());
            detailIntent.putExtra("BODY", currentChat.getCuerpo());
            detailIntent.putExtra("UID", currentChat.getUidAutor());
            detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(detailIntent);

             */
        }
    }
}
