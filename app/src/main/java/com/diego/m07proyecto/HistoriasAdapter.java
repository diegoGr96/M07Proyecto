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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/***
 * The adapter class for the RecyclerView, contains the games data.
 */
public class HistoriasAdapter extends RecyclerView.Adapter<HistoriasAdapter.ViewHolder>  {

    // Member variables.
    private ArrayList<Historia> mHistoryData;
    private Context mContext;

    /**
     * Constructor that passes in the games data and the context.
     *
     * @param gamesData ArrayList containing the gamess data.
     * @param context Context of the application.
     */
    public HistoriasAdapter(Context context, ArrayList<Historia> gamesData) {
        this.mHistoryData = gamesData;
        this.mContext = context;
    }


    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
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
     * @param holder The viewholder into which the data should be put.
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
        Historia currentHistory = mHistoryData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentHistory);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mHistoryData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mGamesImage;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.titleDetail);
            mInfoText = itemView.findViewById(R.id.subTitleDetail);
            mGamesImage = itemView.findViewById(R.id.gamesImageDetail);

            itemView.setOnClickListener(this);
        }

        /*
        Mostramos los datos que hemos recibido a través del método 'onBindViewHolder' de la clase superior
        y nos encargamos de mostrarlos en pantalla.
         */
        void bindTo(Historia currentHistory){
            // Populate the textviews with data.
            mTitleText.setText(currentHistory.getTitle());
            mInfoText.setText(currentHistory.getInfo());
            Glide.with(mContext).load(currentHistory.getImageResource()).into(mGamesImage);

        }

        @Override
        public void onClick(View view) {
            Historia currentHistory = mHistoryData.get(getAdapterPosition());
            Log.d("A","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAsddfnuirn");
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentHistory.getTitle());
            detailIntent.putExtra("image_resource",
                    currentHistory.getImageResource());
            mContext.startActivity(detailIntent);
        }
    }
}
