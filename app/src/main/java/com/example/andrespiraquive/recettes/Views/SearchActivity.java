package com.example.andrespiraquive.recettes.Views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
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

import com.example.andrespiraquive.recettes.GridViewAdapter;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.Presenter.SearchPresenter;
import com.example.andrespiraquive.recettes.R;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView displayRecipe;
    private EditText searchLine;
    private SearchPresenter mSearchPresenter = new SearchPresenter ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        displayRecipe = findViewById(R.id.search_recycle_view);
        searchLine = findViewById(R.id.editSearch);

        Button button = findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                mSearchPresenter.getAllRecipes(new SearchPresenter.FirestoreCallback() {
                    @Override
                    public void onCallback(List<Recipes> listRecipes) {
                        RecyclerView resultSearchView = displayRecipe;
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

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

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
