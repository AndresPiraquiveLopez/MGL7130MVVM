package com.example.andrespiraquive.recettes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.andrespiraquive.recettes.Models.RecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter <GridViewAdapter.MyViewHolder> {

    private List<RecipeResponse> mData;

    private Context mContext;

    public GridViewAdapter(List<RecipeResponse> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_grid,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //holder.img_recipe_thumbnaild.setImageResource(mData.get(position).getPictureId());
        holder.tv_recipe_title.setText(mData.get(position).getTitle());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeActivity.class);

                intent.putExtra("Image",mData.get(position).getPictureId());
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Note",mData.get(position).getNote());
                intent.putExtra("Description",mData.get(position).getDescription());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_recipe_thumbnaild;
        TextView tv_recipe_title;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            img_recipe_thumbnaild = (ImageView) itemView.findViewById(R.id.recipe_image_id);
            Picasso.get()
                    .load("https://firebasestorage.googleapis.com/v0/b/recettes-bb215.appspot.com/o/image_plat_base.jpg?alt=media&token=c2ab783a-4d54-4234-a621-9ec0cd43bf07")
                    .into(img_recipe_thumbnaild);
            tv_recipe_title = (TextView) itemView.findViewById(R.id.recipe_title_id);
            cardView = (CardView) itemView.findViewById(R.id.cardView_id);
        }
    }
}
