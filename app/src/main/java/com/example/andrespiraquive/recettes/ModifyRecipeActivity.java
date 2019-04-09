package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ModifyRecipeActivity extends AppCompatActivity {

    private ImageView img;
    private EditText titre, ingredients, description, preparation;
    private FirebaseFirestore db;
    private boolean IsFavorie;

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


        //Receive Data
        Intent intent = getIntent ();
        IsFavorie =intent.getExtras ().getBoolean ("isFavorie");
        final String ImageId = intent.getExtras().getString("ImageId");
        final String Title = intent.getExtras ().getString ("Title");
        final String Description = intent.getExtras ().getString ("Description");
        final String Ingredient = intent.getExtras ().getString ("Ingredient");
        final String Preparation = intent.getExtras ().getString ("Preparation");
        final String Document = intent.getExtras ().getString ("DocumentId");

        Log.d("TAG ","IMAGEID in MODIFY:" + ImageId);


        //Show Data
        Picasso.get ()
                .load (ImageId)
                .into (img);
        titre.setText(Title);
        ingredients.setText(Ingredient);
        description.setText(Description);
        preparation.setText(Preparation);


        final Button buttonSave = findViewById (R.id.saveRecipeButtonModify);
        final Button buttonCancel = findViewById (R.id.cancelRecipeButtonModify);

        buttonSave.setOnClickListener (new View.OnClickListener () {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                updateRecipe(Document, titre.getText().toString(), ingredients.getText().toString(),
                        description.getText().toString(), preparation.getText().toString());
            }
        });


    }

    private void updateRecipe(final String DocumentId, String titre, String ingredients, String description, String preparation) {

        //Recipes recetteAjouter = new Recipes (titre.getText ().toString (),
                //ingredients.getText ().toString (), description.getText ().toString (),
                //preparation.getText ().toString ());

        DocumentReference newRecipeRef = db.collection ("Recipes").document (DocumentId);
        newRecipeRef.update ("title", titre, "ingredients", ingredients, "description", description, "preparations", preparation)
                .addOnSuccessListener (new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText (ModifyRecipeActivity.this, "Recipe Updated",
                                Toast.LENGTH_SHORT).show ();
                        Intent intent = new Intent (getApplicationContext(), RecipeActivity.class);
                        intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Document", DocumentId);
                        intent.putExtra("isFavorie", IsFavorie);
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
