package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.andrespiraquive.recettes.Models.Recipes;

import java.util.ArrayList;

public class favorisActivity extends AppCompatActivity {

    ArrayList<Recipes> lsRecipe;
    DataBase baseRecette;
    RecipesListAdapter adapter = null;
    RecyclerView affichageRecette;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_liste_favoris);


        baseRecette = new DataBase (this);
        lsRecipe = new ArrayList<> ();

        affichageRecette = findViewById (R.id.favorite_recycle_view);

        Cursor res = baseRecette.getAllData ();

        if (res.getCount () == 0) {
            ShowMessage ("Message", "Les favoris sont vides");
            return;
        } else {
            StringBuffer buffer = new StringBuffer ();
            while (res.moveToNext ()) {
                int id = res.getInt (0);
                String imageID = "Image";
                String title = res.getString (1);
                String preparation = res.getString (2);
                String ingredient = res.getString (3);
                String description = res.getString(4);
                String note = String.valueOf (res.getDouble (6));
                String position = "45.462252,-73.437309";

                lsRecipe.add (new Recipes (imageID, title, ingredient, description, preparation, note, position));


            }
        }

        RecyclerView resultSearchView = affichageRecette;
        GridViewAdapter myAdapter = new GridViewAdapter (lsRecipe, getApplicationContext (),true);
        resultSearchView.setLayoutManager (new GridLayoutManager (getApplicationContext (), 2));
        resultSearchView.setAdapter (myAdapter);

    }

    public void ShowMessage(String title, String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setCancelable (true);
        builder.setTitle (title);
        builder.setMessage (Message);
        builder.show ();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.main_menu, menu);
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId () == R.id.list_recipes) {
            Intent listRecipes = new Intent (getApplicationContext (), GridViewActivity.class);
            startActivity (listRecipes);
            finish ();
        }

        if (item.getItemId () == R.id.add_recipe) {
            Intent addRecipe = new Intent (getApplicationContext (), AddRecipe.class);
            startActivity (addRecipe);
            finish ();
        }
        if (item.getItemId () == R.id.search_recipe) {
            Intent searchActivity = new Intent (getApplicationContext (), SearchActivity.class);
            startActivity (searchActivity);
            finish ();
        }
        if (item.getItemId () == R.id.user_favoris) {

            Intent Recipes_list = new Intent (getApplicationContext (), favorisActivity.class);
            startActivity (Recipes_list);
            finish ();
        }
        if (item.getItemId () == R.id.user_settings) {
            Intent searchActivity = new Intent (getApplicationContext (), MainActivity.class);
            startActivity (searchActivity);
            finish ();
        }
        if (item.getItemId () == R.id.list_recipes) {
            Intent recipeActivity = new Intent (getApplicationContext (), GridViewActivity.class);
            startActivity (recipeActivity);
            finish ();
        }
        return super.onOptionsItemSelected (item);
    }

}



