package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvingredient, tvpreparation;
    private ImageView img;
    private RatingBar note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        img = (ImageView) findViewById(R.id.recipe_details_thumbnail_id);
        tvtitle = (TextView) findViewById(R.id.recipe_details_title_id);
        note = (RatingBar) findViewById(R.id.recipe_details_raiting_id);
        tvdescription = (TextView) findViewById(R.id.recipe_details_description_id);
        tvingredient = (TextView) findViewById(R.id.recipe_details_ingredient_id);
        tvpreparation = (TextView) findViewById(R.id.recipe_details_preparation_id);

        Picasso.get()
                .load("https://firebasestorage.googleapis.com/v0/b/recettes-bb215.appspot.com/o/image_plat_base_free.jpg?alt=media&token=29c46ebf-a107-45f8-9957-25b103108dd1")
                .into(img);

        //Recieve data
        Intent intent = getIntent();
        //int Image = intent.getExtras().getInt("Image");
        String Title = intent.getExtras().getString("Title");
        Float Note = intent.getExtras().getFloat("Note");
        String Description = intent.getExtras().getString("Description");
        String Ingredient = intent.getExtras().getString("Ingredient");
        String Preparation = intent.getExtras().getString("Preparation");
        //settings values
        //img.setImageResource(Image);
        tvtitle.setText(Title);
        note.setRating(Note);
        tvdescription.setText(Description);
        tvingredient.setText(Ingredient);
        tvpreparation.setText(Preparation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.list_recipes) {
            Intent listRecipes = new Intent(getApplicationContext(), GridViewActivity.class);
            startActivity(listRecipes);
            finish();
        }

        if (item.getItemId() == R.id.add_recipe) {
            Intent addRecipe = new Intent(getApplicationContext(), AddRecipe.class);
            startActivity(addRecipe);
            finish();
        }
        if (item.getItemId() == R.id.search_recipe) {
            Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(searchActivity);
            finish();
        }
        if (item.getItemId() == R.id.user_settings) {
            Intent searchActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(searchActivity);
            finish();
        }
        if(item.getItemId()==R.id.list_recipes){
            Intent recipeActivity = new Intent(getApplicationContext(),GridViewActivity.class);
            startActivity(recipeActivity);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
