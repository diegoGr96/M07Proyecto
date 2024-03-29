/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.diego.m07proyecto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/***
 * The adapter class for the RecyclerView, contains the games data.
 */
public class HistoriasAdapter extends RecyclerView.Adapter<HistoriasAdapter.ViewHolder> {

    // Member variables.
    private List<Tema> mTemaData;
    private Context mContext;

    /**
     * Constructor that passes in the games data and the context.
     *
     * @param temasData ArrayList containing the gamess data.
     * @param context   Context of the application.
     */
    public HistoriasAdapter(Context context, List<Tema> temasData) {
        this.mTemaData = temasData;
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
    public HistoriasAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
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
    public void onBindViewHolder(HistoriasAdapter.ViewHolder holder,
                                 int position) {
        // Get current games.
        Tema currentTema = mTemaData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentTema);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mTemaData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView mTituloText;
        private TextView mNickText;
        private TextView cuerpoTema;
        private ImageView ctgBackground;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTituloText = itemView.findViewById(R.id.titleDetail);
            mNickText = itemView.findViewById(R.id.userDetail);
            cuerpoTema = itemView.findViewById(R.id.cuerpoTema);
            ctgBackground = itemView.findViewById(R.id.ctgBackground);
            itemView.setOnClickListener(this);
        }

        /*
        Mostramos los datos que hemos recibido a través del método 'onBindViewHolder' de la clase superior
        y nos encargamos de mostrarlos en pantalla.
         */
        void bindTo(Tema currentTema) {
            // Populate the textviews with data.

            mTituloText.setText(currentTema.getTitulo());
            String cuerpo = currentTema.getCuerpo();
            if (cuerpo.length() > 70) cuerpo = cuerpo.substring(0, 71) + "...";
            cuerpoTema.setText(cuerpo);
            if (currentTema.isAnonimo()) {
                mNickText.setText(R.string.temaUsuarioAnonimo);
            } else {
                mNickText.setText(currentTema.getNickAutor());
            }
            //fondoCardView.setBackgroundColor(mContext.getResources().getColor(R.color.fondoCardView));
            switch(currentTema.getCategoria()){
                case 0:
                    ctgBackground.setImageResource(R.drawable.family_category);
                    break;
                case 1:
                    ctgBackground.setImageResource(R.drawable.work_category);
                    break;
                case 2:
                    ctgBackground.setImageResource(R.drawable.love_category);
                    break;
                case 3:
                    ctgBackground.setImageResource(R.drawable.money_category);
                    break;
                case 4:
                    ctgBackground.setImageResource(R.drawable.music_category);
                    break;
                case 5:
                    ctgBackground.setImageResource(R.drawable.sports_category);
                    break;
                case 6:
                    ctgBackground.setImageResource(R.drawable.art_category);
                    break;
                case 7:
                    ctgBackground.setImageResource(R.drawable.study_category);
                    break;
            }
        }

        @Override
        public void onClick(View view) {
            Tema currentTema = mTemaData.get(getAdapterPosition());
            //Log.d("A", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAsddfnuirn");
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("ID_TEMA", currentTema.getIdTema());
            detailIntent.putExtra("TITLE", currentTema.getTitulo());
            detailIntent.putExtra("USER", currentTema.isAnonimo()?mContext.getResources().getString(R.string.temaUsuarioAnonimo):currentTema.getNickAutor());
            detailIntent.putExtra("BODY", currentTema.getCuerpo());
            detailIntent.putExtra("UID",currentTema.getUidAutor());
            detailIntent.putExtra("CATEGORY", currentTema.getCategoria());
            detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(detailIntent);
        }
    }
}
