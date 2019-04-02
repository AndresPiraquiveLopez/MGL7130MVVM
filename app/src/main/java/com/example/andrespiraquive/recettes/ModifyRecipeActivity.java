package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Models.Recipes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ModifyRecipeActivity extends AppCompatActivity {

    private ImageView img;
    private EditText titre, ingredients, description, preparation;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_recipe);

        db = FirebaseFirestore.getInstance ();
        img = findViewById (R.id.imageRecipeModify);
        titre = findViewById(R.id.titreModify);
        ingredients = findViewById(R.id.editIngredientModify);
        description = findViewById(R.id.editDescriptionModify);
        preparation = findViewById(R.id.editPreparationModify);
        Picasso.get ()
                .load ("https://firebasestorage.googleapis.com/v0/b/recettes-bb215.appspot.com/o/image_plat_base_free.jpg?alt=media&token=29c46ebf-a107-45f8-9957-25b103108dd1")
                .into (img);

        //Receive Data
        Intent intent = getIntent ();
        final String Title = intent.getExtras ().getString ("Title");
        final String Description = intent.getExtras ().getString ("Description");
        final String Ingredient = intent.getExtras ().getString ("Ingredient");
        final String Preparation = intent.getExtras ().getString ("Preparation");
        final String DocumentId = intent.getExtras ().getString ("DocumentId");

        //Show Data
        titre.setText(Title);
        ingredients.setText(Ingredient);
        description.setText(Description);
        preparation.setText(Preparation);


        final Button buttonSave = findViewById (R.id.saveRecipeButtonModify);
        final Button buttonCancel = findViewById (R.id.cancelRecipeButtonModify);

        buttonSave.setOnClickListener (new View.OnClickListener () {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                updateRecipe (DocumentId, titre.getText().toString());
            }
        });


    }

    private void updateRecipe(String DocumentId, String titre) {

        //Recipes recetteAjouter = new Recipes (titre.getText ().toString (),
                //ingredients.getText ().toString (), description.getText ().toString (),
                //preparation.getText ().toString ());

        DocumentReference newRecipeRef = db.collection ("Recipes").document (DocumentId);
        newRecipeRef.update ("title", titre)
                .addOnSuccessListener (new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText (ModifyRecipeActivity.this, "Recipe Updated",
                                Toast.LENGTH_SHORT).show ();

                        Intent intent = new Intent (ModifyRecipeActivity.this, RecipeActivity.class);
                        startActivity (intent);
                        finish ();
                    }
                })
                .addOnFailureListener (new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText (ModifyRecipeActivity.this, "ERROR" + e.toString (),
                                Toast.LENGTH_SHORT).show ();
                        Log.d ("TAG", e.toString ());
                    }
                });
    }
}
