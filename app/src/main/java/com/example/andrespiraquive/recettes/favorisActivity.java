package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.andrespiraquive.recettes.Data.Database.DataBase;
import com.example.andrespiraquive.recettes.Presenter.FavorisPresenter;
import com.example.andrespiraquive.recettes.Views.GridViewActivity;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.example.andrespiraquive.recettes.Views.SearchActivity;

import java.util.ArrayList;

public class favorisActivity extends AppCompatActivity {

    private ArrayList<FavorisPresenter> lsRecipe;
    private DataBase baseRecette;
    private RecipesListAdapter adapter = null;
    private RecyclerView affichageRecette;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_liste_favoris);


        baseRecette = new DataBase (this);
        //lsRecipe = new ArrayList<> ();
        lsRecipe = new ArrayList<> ();


        affichageRecette = findViewById (R.id.favorite_recycle_view);

        Cursor res = baseRecette.getAllData ();

        if (res.getCount () == 0) {
            ShowMessage ("Message", "You have no favorites");
            return;
        } else {
            StringBuffer buffer = new StringBuffer ();
            while (res.moveToNext ()) {
                int id = res.getInt (0);
                byte[] imageID = res.getBlob(5);
                //String imageID = "IMAGE";
                String title = res.getString (1);
                String preparation = res.getString (2);
                String ingredient = res.getString (3);
                String description = res.getString(4);
                String note = String.valueOf (res.getDouble (6));
                String position = res.getString(7);

                //lsRecipe.add (new Recipes (imageID, title, ingredient, description, preparation, note, position));
                lsRecipe.add (new FavorisPresenter (id, imageID, title, ingredient, description, preparation, note, position));

            }
        }

        RecyclerView resultSearchView = affichageRecette;
        GridViewAdapter myAdapter = new GridViewAdapter (lsRecipe, getApplicationContext ());
        setLayout (resultSearchView);
        //resultSearchView.setLayoutManager (new GridLayoutManager (getApplicationContext (), 2));
        resultSearchView.setAdapter (myAdapter);

    }

    private void setLayout(RecyclerView mRv) {
        switch (getResources ().getConfiguration ().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:

                int mWidthPortraitDp = getResources ().getConfiguration ().screenWidthDp >= 600 ? 1 : 0;
                switch (mWidthPortraitDp) {
                    case 0:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 2));
                        break;

                    case 1:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 3));
                        break;
                }
                break;

            case Configuration.ORIENTATION_LANDSCAPE:

                int mWidthLandscapeDp = getResources ().getConfiguration ().screenWidthDp >= 921 ? 1 : 0;

                switch (mWidthLandscapeDp) {
                    case 0:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 4));
                        break;

                    case 1:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 5));
                        break;

                }
                break;

            default:
                break;
        }
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
        switch (item.getItemId()){
            case R.id.list_recipes:
                Intent listRecipes = new Intent (getApplicationContext (), GridViewActivity.class);
                startActivity (listRecipes);
                finish ();
                return true;
            case R.id.add_recipe:
                Intent addRecipe = new Intent (getApplicationContext (), AddRecipeActivity.class);
                startActivity (addRecipe);
                finish ();
                return true;
            case R.id.search_recipe:
                Intent searchActivity = new Intent (getApplicationContext (), SearchActivity.class);
                startActivity (searchActivity);
                finish ();
                return true;
            case R.id.user_favoris:
                Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
                startActivity(Recipes_list);
                finish();
                return true;
            case R.id.user_settings:
                Intent userSettings = new Intent (getApplicationContext (), MainActivity.class);
                startActivity (userSettings);
                finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }
        }
    }



