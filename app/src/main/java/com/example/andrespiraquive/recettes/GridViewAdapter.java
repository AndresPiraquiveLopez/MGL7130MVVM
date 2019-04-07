package com.example.andrespiraquive.recettes;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.ViewModels.FavorisViewModel;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter <GridViewAdapter.MyViewHolder> {

    private List<Recipes> mData;
    private List<FavorisViewModel> mDataFavorite;
    private Context mContext;
    private boolean isFavorie;


    public GridViewAdapter(List<Recipes> mData, Context mContext, boolean isFavorie) {
        this.mData = mData;
        this.mDataFavorite = null;
        this.mContext = mContext;
        this.isFavorie = isFavorie;
    }
    public GridViewAdapter(List<FavorisViewModel> mDataFavorite, Context mContext) {
        this.mDataFavorite = mDataFavorite;
        this.mData = null;
        this.mContext = mContext;
        this.isFavorie = true;
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

        if(mData != null){
            Picasso.get()
                  .load(mData.get(position).getImage())
                .into(holder.img_recipe_thumbnaild);
            holder.tv_recipe_title.setText(mData.get(position).getTitle());
            holder.note.setRating ((float) mData.get(position).getNote ());

            Log.d("TAG : ", "DOCUMENT mData non null");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RecipeActivity.class);
                    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ImageId", mData.get(position).getImage());
                    intent.putExtra("Title",mData.get(position).getTitle());
                    intent.putExtra("Note",mData.get(position).getNote());
                    intent.putExtra("Description",mData.get(position).getDescription());
                    intent.putExtra("Ingredient",mData.get(position).getIngredients());
                    intent.putExtra("Preparation",mData.get(position).getPreparations());
                    intent.putExtra ("Document",mData.get(position).getDocument ());
                    intent.putExtra ("isFavorie",isFavorie);
                    mContext.startActivity(intent);
                }
            });
        }
        else{
            holder.img_recipe_thumbnaild.setImageBitmap(BitmapFactory.decodeByteArray(mDataFavorite.get(position).getImageId(),
                    0,
                    mDataFavorite.get(position).getImageId().length));
            holder.tv_recipe_title.setText(mDataFavorite.get(position).getTitle());
            holder.note.setRating ((float) mDataFavorite.get(position).getNote ());

            //Log.d("TAG : ", "DOCUMENT = " + mDataFavorite.get(position).getDocument());
            Log.d("TAG : ", "ID = " + mDataFavorite.get(position).getId());
            Log.d("TAG : ", "Title = " + mDataFavorite.get(position).getTitle());
            Log.d("TAG : ", "Note = " + mDataFavorite.get(position).getNote());
            Log.d("TAG : ", "Descritption = " + mDataFavorite.get(position).getDescription());
            Log.d("TAG : ", "Preparation = " + mDataFavorite.get(position).getPreparations());
            Log.d("TAG : ", "Ingredient = " + mDataFavorite.get(position).getIngredients());
            Log.d("TAG : ", "DOCUMENT mData null = " + mDataFavorite.get(position).getDocument());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, RecipeActivity.class);
                    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Id", mDataFavorite.get(position).getId());
                    intent.putExtra("Title",mDataFavorite.get(position).getTitle());
                    intent.putExtra("Note",mDataFavorite.get(position).getNote());
                    intent.putExtra("Description",mDataFavorite.get(position).getDescription());
                    intent.putExtra("Ingredient",mDataFavorite.get(position).getIngredients());
                    intent.putExtra("Preparation",mDataFavorite.get(position).getPreparations());
                    //intent.putExtra ("Document",mDataFavorite.get(position).getDocument ());
                    intent.putExtra ("isFavorie",isFavorie);
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(mData != null)
            return mData.size();
        else
            return mDataFavorite.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_recipe_thumbnaild;
        TextView tv_recipe_title;
        RatingBar note;
        CardView cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            img_recipe_thumbnaild = (ImageView) itemView.findViewById(R.id.recipe_image_id);
            //Picasso.get()
              //      .load("https://firebasestorage.googleapis.com/v0/b/recettes-bb215.appspot.com/o/image_plat_base_free.jpg?alt=media&token=29c46ebf-a107-45f8-9957-25b103108dd1")
                //    .into(img_recipe_thumbnaild);
            tv_recipe_title = (TextView) itemView.findViewById(R.id.recipe_title_id);
            cardView = (CardView) itemView.findViewById(R.id.cardView_id);
            note = (RatingBar) itemView.findViewById (R.id.recipe_ratingBar_id);
        }
    }
}
