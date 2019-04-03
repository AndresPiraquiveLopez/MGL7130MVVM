package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
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

    List<Recipes> mRecipe;

    GridViewModel mGridViewModel = new GridViewModel ();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_grid_view);

        mRecipe = new ArrayList<> ();

        mGridViewModel.getAllRecipes (new GridViewModel.FirestoreCallback () {
            @Override
            public void onCallback(List<Recipes> listRecipes) {

                RecyclerView mRv = findViewById (R.id.recycle_view_id);
                GridViewAdapter myAdapter = new GridViewAdapter (listRecipes, getApplicationContext (), false);
                setLayout (mRv);
                mRv.setAdapter (myAdapter);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.main_menu, menu);
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_recipes:
                Intent listRecipes = new Intent(getApplicationContext(), GridViewActivity.class);
                startActivity(listRecipes);
                finish();
                return true;
            case R.id.add_recipe:
                Intent addRecipe = new Intent(getApplicationContext(), AddRecipeActivity.class);
                startActivity(addRecipe);
                finish();
                return true;
            case R.id.search_recipe:
                Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(searchActivity);
                finish();
                return true;
            case R.id.user_favoris:
                Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
                startActivity(Recipes_list);
                finish();
                return true;
            case R.id.user_settings:
                Intent userSettings = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(userSettings);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
