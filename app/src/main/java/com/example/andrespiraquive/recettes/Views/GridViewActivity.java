package com.example.andrespiraquive.recettes.Views;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.andrespiraquive.recettes.GridViewAdapter;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.Presenter.GridPresenter;
import com.example.andrespiraquive.recettes.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    List<Recipes> mRecipe;

    GridPresenter mGridPresenter = new GridPresenter ();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_grid_view);

        mRecipe = new ArrayList<> ();

        mGridPresenter.getAllRecipes (new GridPresenter.FirestoreCallback () {
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
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:


                //Log.d("TAG", "WIDTH_PORTRAIT : " + getResources().getConfiguration().screenWidthDp);
                if (getResources().getConfiguration().screenWidthDp >= 600) {
                    mRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                }
                if (getResources().getConfiguration().screenWidthDp <= 599 && getResources().getConfiguration().screenWidthDp >= 355) {
                    mRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                }
                if (getResources().getConfiguration().screenWidthDp <= 354) {
                    mRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                }
                break;
            case Configuration.ORIENTATION_LANDSCAPE:

                //Log.d("TAG", "WIDTH_LANDSCAPE : " + getResources().getConfiguration().screenWidthDp);
                if (getResources().getConfiguration().screenWidthDp >= 921) {
                    mRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
                }
                if (getResources().getConfiguration().screenWidthDp <= 920 && getResources().getConfiguration().screenWidthDp >= 601) {
                    mRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
                }
                if (getResources().getConfiguration().screenWidthDp <= 600) {
                    mRv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
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
