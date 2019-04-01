package com.example.andrespiraquive.recettes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.ViewModels.SearchViewModel;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView affichageRecette;
    EditText searchLine;
    SearchViewModel mSearchViewModel = new SearchViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = FirebaseFirestore.getInstance();
        affichageRecette = findViewById(R.id.search_recycle_view);
        searchLine = findViewById(R.id.editSearch);

        Button button = findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                mSearchViewModel.getAllRecipes(new SearchViewModel.FirestoreCallback() {
                    @Override
                    public void onCallback(List<Recipes> listRecipes) {
                        RecyclerView resultSearchView = affichageRecette;
                        GridViewAdapter myAdapter = new GridViewAdapter(listRecipes, getApplicationContext(),false);
                        resultSearchView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                        resultSearchView.setAdapter(myAdapter);
                    }
                }, searchLine.getText().toString());
                closeKeyboard();
            }

        });

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
            Intent addRecipe = new Intent(getApplicationContext(),AddRecipeActivity.class);
            startActivity(addRecipe);
            finish();
        }
        if(item.getItemId()==R.id.search_recipe){
            Intent searchActivity = new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(searchActivity);
            finish();
        }
        if(item.getItemId()==R.id.user_favoris){
            Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
            startActivity(Recipes_list);
            finish();
        }
        if(item.getItemId()==R.id.user_settings){
            Intent searchActivity = new Intent(getApplicationContext(),MainActivity.class);
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
