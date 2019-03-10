package com.example.andrespiraquive.recettes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import android.widget.TextView;

import com.example.andrespiraquive.recettes.Models.RecipeResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    //private Button boutonRecherche;

    private static final String TITRE_KEY = "titre";
    private static final String INGREDIENT_KEY = "ingredient";
    private static final String DESCRIPTION_KEY = "description";
    private static final String PREPARATION_KEY = "preparation";
    private static final String POSITION_KEY = "position";



    FirebaseFirestore db;
    RecyclerView affichageRecette;
    EditText searchLine;

    List<RecipeResponse> lsRecipe;

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
        .whereEqualTo("titre", searchLine.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,document.get(TITRE_KEY).toString(),(float) 2, document.get(DESCRIPTION_KEY).toString()));
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
