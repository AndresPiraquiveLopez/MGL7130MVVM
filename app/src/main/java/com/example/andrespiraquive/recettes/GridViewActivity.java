package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.andrespiraquive.recettes.Models.RecipeResponse;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private GridViewAdapter adapter;

    List<RecipeResponse> lsRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        lsRecipe = new ArrayList<>();
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 1", (float) 4, "Easy"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 2",(float) 2, "Difficult"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 3",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 4", (float)1, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 5",(float) 4, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 6",(float) 2, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 7",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 8",(float) 1, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 1",(float) 4, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 2",(float) 2, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 3",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 4",(float) 1, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 5",(float) 4, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 6",(float) 2, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 7",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 8",(float) 1, "Traditional"));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recycle_view_id);
        GridViewAdapter myAdapter = new GridViewAdapter(lsRecipe, this);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.list_recipes) {
            Intent listRecipes = new Intent(getApplicationContext(), GridViewActivity.class);
            startActivity(listRecipes);
            finish();
        }

        if(item.getItemId()==R.id.add_recipe){
            Intent addRecipe = new Intent(getApplicationContext(),AddRecipe.class);
            startActivity(addRecipe);
            finish();
        }
        if(item.getItemId()==R.id.search_recipe){
            Intent searchActivity = new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(searchActivity);
            finish();
        }
        if(item.getItemId()==R.id.user_settings){
            Intent searchActivity = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(searchActivity);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
