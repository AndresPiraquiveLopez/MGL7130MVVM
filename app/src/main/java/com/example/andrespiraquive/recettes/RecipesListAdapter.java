package com.example.andrespiraquive.recettes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipesListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Recipes> repasList;

    public RecipesListAdapter(Context context, int layout, ArrayList<Recipes> repasList) {
        this.context = context;
        this.layout = layout;
        this.repasList = repasList;
    }

    @Override
    public int getCount() {
        return repasList.size();
    }

    @Override
    public Object getItem(int position) {
        return repasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class viewHolder {

        TextView tvtitle, tvtingredient, tvpreparation, tvdescription;
        ImageView image;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        viewHolder holder = new viewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.tvtitle = (TextView) row.findViewById(R.id.recipe_title_id);

            holder.image = (ImageView) row.findViewById(R.id.recipe_image_id);

            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }

        Recipes lesrapas = repasList.get(position);
        holder.tvtitle.setText(lesrapas.getTitle());

        String recipesImage = lesrapas.getImage();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(recipesImage, 0, recipesImage.length);
        //holder.image.setImageBitmap(bitmap);

        return row;
    }
}
