package com.example.andrespiraquive.recettes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.andrespiraquive.recettes.Models.Recipes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //private Button boutonRecherche;

    private static final String IMAGE_KEY = "image";
    private static final String TITLE_KEY = "title";
    private static final String NOTE_KEY = "note";
    private static final String DESCRIPTION_KEY = "description";
    private static final String INGREDIENTS_KEY = "ingredients";
    private static final String PREPARATIONS_KEY = "preparations";
    private static final String POSITION_KEY = "position";



    FirebaseFirestore db;
    RecyclerView affichageRecette;
    EditText searchLine;

    List<Recipes> lsRecipe;

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
                ReadRecipes();
                closeKeyboard();
            }

        });

    }

    private void ReadRecipes() {

        lsRecipe = new ArrayList<>();

        db.collection("Recipes")
        .whereEqualTo("title", searchLine.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lsRecipe.add(new Recipes(document.get(IMAGE_KEY).toString(),
                                        document.get(TITLE_KEY).toString(), document.get(INGREDIENTS_KEY).toString(),
                                        document.get(DESCRIPTION_KEY).toString(), document.get(PREPARATIONS_KEY).toString(),
                                        (double) document.get(NOTE_KEY), document.get(POSITION_KEY).toString(),document.getId ()));
                            }
                            RecyclerView resultSearchView = affichageRecette;
                            GridViewAdapter myAdapter = new GridViewAdapter(lsRecipe, getApplicationContext());
                            resultSearchView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                            resultSearchView.setAdapter(myAdapter);
                        }else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
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
            Intent addRecipe = new Intent(getApplicationContext(),AddRecipe.class);
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
