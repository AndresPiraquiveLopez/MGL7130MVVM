package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRecipe extends AppCompatActivity {

    private static final String TITRE_KEY = "titre";
    private static final String INGREDIENT_KEY = "ingredient";
    private static final String DESCRIPTION_KEY = "description";
    private static final String PREPARATION_KEY = "preparation";
    private static final String POSITION_KEY = "position";

    FirebaseFirestore db;
    EditText editTitre;
    EditText editIngredient;
    EditText editDescription;
    EditText editPreparation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        db = FirebaseFirestore.getInstance();
        editTitre = findViewById(R.id.titre);
        editIngredient = findViewById(R.id.editIngredient);
        editDescription = findViewById(R.id.editDescription);
        editPreparation = findViewById(R.id.editPreparation);


        final Button buttonSave = findViewById(R.id.saveRecipeButton);
        final Button buttonCancel = findViewById(R.id.cancelRecipeButton);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                addNewRecipe();
            }
        });

    }


    private void addNewRecipe() {
        Map<String, Object> newRecipe = new HashMap<>();
        newRecipe.put(TITRE_KEY, editTitre.getText().toString());
        newRecipe.put(INGREDIENT_KEY, editIngredient.getText().toString());
        newRecipe.put(DESCRIPTION_KEY, editDescription.getText().toString());
        newRecipe.put(PREPARATION_KEY, editPreparation.getText().toString());
        newRecipe.put(POSITION_KEY, "45.462252,-73.437309");

        db.collection("Recipes")
                .add(newRecipe)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddRecipe.this, "Recipe Registered",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddRecipe.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
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
