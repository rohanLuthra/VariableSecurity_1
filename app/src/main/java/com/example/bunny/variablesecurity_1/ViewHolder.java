package com.example.bunny.variablesecurity_1;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.sql.Timestamp;

class ViewHolder extends RecyclerView.ViewHolder{

    public TextView result,timestamp,crimnal;
    public ImageView imageView;
    public CardView cardView;

    public ViewHolder(View itemView) {
        super(itemView);
        result =  itemView.findViewById(R.id.cardResult);
        timestamp = itemView.findViewById(R.id.cardTime);
        imageView =  itemView.findViewById(R.id.cardImage);
        crimnal = itemView.findViewById(R.id.criminalText);
        cardView = itemView.findViewById(R.id.parentCard);
    }
}
