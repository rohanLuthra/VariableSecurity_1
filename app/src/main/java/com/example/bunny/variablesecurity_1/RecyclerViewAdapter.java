package com.example.bunny.variablesecurity_1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private JSONArray jsonArray;
    private Context mContext;


    public RecyclerViewAdapter(JSONArray jsonArray, Context mContext) {
        this.jsonArray = jsonArray;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.card_view_2,parent, false);
        return new ViewHolder(itemView);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.crimnal.setVisibility(View.GONE);
            holder.cardView.setVisibility(View.VISIBLE);
            Resources r = mContext.getResources();
            int px = Math.round(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 130,r.getDisplayMetrics()));
            holder.cardView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, px));
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            if(!jsonObject.has("FullName")){
                holder.cardView.setVisibility(View.GONE);
                holder.cardView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

                return;
            }
            holder.result.setText(jsonObject.get("FullName").toString());
            if(jsonObject.get("FullName").toString().equals("No humans detected") ||jsonObject.get("FullName").toString().equals("Unknown person") ||
            jsonObject.get("FullName").toString().equals("no match found"))
                holder.result.setTextColor(holder.itemView.getResources().getColor(R.color.black));
            else
                holder.result.setTextColor(holder.itemView.getResources().getColor(R.color.green));
            holder.timestamp.setText(jsonObject.get("Timestamp").toString());
            if(jsonObject.has("Criminal"))
                if(jsonObject.get("Criminal").toString().equals("1") ){
                    holder.crimnal.setVisibility(View.VISIBLE);
                    holder.result.setTextColor(holder.itemView.getResources().getColor(R.color.blue_primary));
            }
            //        https://s3.amazonaws.com/multiple-face-rekog-test/images/2018-10-13181738.jpg

            String url = "https://s3.amazonaws.com/multiple-face-rekog-test/" + jsonObject.get("Key");


            Glide.with(mContext)
                    .load(Uri.parse(url))
                    .into(holder.imageView);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    @Override
    public int getItemCount() {
        return jsonArray.length();
    }
}
