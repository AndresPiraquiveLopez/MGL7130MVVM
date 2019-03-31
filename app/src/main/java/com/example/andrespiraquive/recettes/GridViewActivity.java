package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.ViewModels.GridViewModel;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private static final String IMAGE_KEY = "image";
    private static final String TITLE_KEY = "title";
    private static final String NOTE_KEY = "note";
    private static final String DESCRIPTION_KEY = "description";
    private static final String INGREDIENTS_KEY = "ingredients";
    private static final String PREPARATIONS_KEY = "preparations";
    private static final String POSITION_KEY = "position";

    private RecyclerView recyclerView;

    private GridViewAdapter adapter;

    List<Recipes> mRecipe;

    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_grid_view);

        mRecipe = new ArrayList<> ();

        //Data from firestore
        GridViewModel model = new GridViewModel ();
        List<Recipes> mRecipes = model.getRecipes ();


        RecyclerView mRv = findViewById (R.id.recycle_view_id);
        GridViewAdapter myAdapter = new GridViewAdapter (mRecipes, getApplicationContext (), false);

        switch (getResources ().getConfiguration ().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:

                int mWidthPortraitDp = getResources ().getConfiguration ().screenWidthDp >= 600 ? 1 : 0;
                switch (mWidthPortraitDp) {
                    case 1:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 3));
                        break;
                    default:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 2));
                }
                break;

            case Configuration.ORIENTATION_LANDSCAPE:

                int mWidthLandscapeDp = getResources ().getConfiguration ().screenWidthDp >= 921 ? 1 : 0;
                switch (mWidthLandscapeDp) {
                    case 1:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 5));
                        break;
                    default:
                        mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 4));
                }

            default:
                mRv.setLayoutManager (new GridLayoutManager (getApplicationContext (), 2));
                break;
        }

        mRv.setAdapter (myAdapter);

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
            Intent addRecipe = new Intent (getApplicationContext (), AddRecipeActivity.class);
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
        return super.onOptionsItemSelected (item);
    }
}
