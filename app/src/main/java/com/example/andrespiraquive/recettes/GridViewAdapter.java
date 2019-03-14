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
import android.widget.RatingBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter <GridViewAdapter.MyViewHolder> {

    private List<Recipes> mData;

    private Context mContext;

    public GridViewAdapter(List<Recipes> mData, Context mContext) {
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
        holder.note.setRating ((float) mData.get(position).getNote ());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Note",mData.get(position).getNote());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Ingredient",mData.get(position).getIngredients());
                intent.putExtra("Preparation",mData.get(position).getPreparations());
                intent.putExtra ("Document",mData.get(position).getDocument ());
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
        RatingBar note;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            img_recipe_thumbnaild = (ImageView) itemView.findViewById(R.id.recipe_image_id);
            Picasso.get()
                    .load("https://firebasestorage.googleapis.com/v0/b/recettes-bb215.appspot.com/o/image_plat_base_free.jpg?alt=media&token=29c46ebf-a107-45f8-9957-25b103108dd1")
                    .into(img_recipe_thumbnaild);
            tv_recipe_title = (TextView) itemView.findViewById(R.id.recipe_title_id);
            cardView = (CardView) itemView.findViewById(R.id.cardView_id);
            note = (RatingBar) itemView.findViewById (R.id.recipe_ratingBar_id);
        }
    }
}
