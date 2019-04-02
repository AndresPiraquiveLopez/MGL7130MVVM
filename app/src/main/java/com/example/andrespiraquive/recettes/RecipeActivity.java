package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Data.Database.DataBase;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.ViewModels.RecipeViewModel;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvingredient, tvpreparation;
    private ImageView img, modifyImg, deleteImg;
    private RatingBar note;
    private RatingBar ratingBar;
    private FirebaseFirestore db;
    private ImageButton btnAImage;
    private DataBase baseRecette;
    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_recipe);
        baseRecette=new DataBase(this);

        db = FirebaseFirestore.getInstance ();
        img = (ImageView) findViewById (R.id.recipe_details_thumbnail_id);
        tvtitle = (TextView) findViewById (R.id.recipe_details_title_id);
        note = (RatingBar) findViewById (R.id.recipe_details_raiting_id);
        tvdescription = (TextView) findViewById (R.id.recipe_details_description_id);
        tvingredient = (TextView) findViewById (R.id.recipe_details_ingredient_id);
        tvpreparation = (TextView) findViewById (R.id.recipe_details_preparation_id);
        btnAImage=(ImageButton )findViewById(R.id.btnImage);
        modifyImg=findViewById(R.id.modifyImageId);
        deleteImg=findViewById(R.id.deleteImageId);
        recipeViewModel = new RecipeViewModel();

        Picasso.get ()
                .load ("https://firebasestorage.googleapis.com/v0/b/recettes-bb215.appspot.com/o/image_plat_base_free.jpg?alt=media&token=29c46ebf-a107-45f8-9957-25b103108dd1")
                .into (img);

        //Recieve data
        Intent intent = getIntent ();
        final boolean IsFavorie =intent.getExtras ().getBoolean ("isFavorie");
        //final String Title = intent.getExtras ().getString ("Title");
        //final double Note = intent.getExtras ().getDouble ("Note");
        //final String Description = intent.getExtras ().getString ("Description");
        //final String Ingredient = intent.getExtras ().getString ("Ingredient");
        //final String Preparation = intent.getExtras ().getString ("Preparation");
        final String Document = intent.getExtras ().getString ("Document");
        recipeViewModel.getRecipes(new RecipeViewModel.FirestoreCallback() {
            @Override
            public void onCallback(Recipes mRecipes) {
                final String Title = mRecipes.getTitle();
                final double Note = mRecipes.getNote();
                final String Description = mRecipes.getDescription();
                final String Ingredient = mRecipes.getIngredients();
                final String Preparation = mRecipes.getPreparations();



        //settings values
        tvtitle.setText (Title);
        note.setRating ((float) Note);
        tvdescription.setText (Description);
        tvingredient.setText (Ingredient);
        tvpreparation.setText (Preparation);

        if(!IsFavorie){
            addListenerOnRatingBar (Document);
            AjoutBaseDonnees();
        }
        else{
            note.setEnabled (false);
            btnAImage.setEnabled (false);
        }

        modifyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, ModifyRecipeActivity.class);
                intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Title",Title);
                intent.putExtra("Description",Description);
                intent.putExtra("Ingredient",Ingredient);
                intent.putExtra("Preparation",Preparation);
                intent.putExtra("DocumentId", Document);
                startActivity(intent);
            }
        });
        }
    },Document);
    }

    public void addListenerOnRatingBar(final String documentId) {

        ratingBar = (RatingBar) findViewById (R.id.recipe_details_raiting_id);

        ratingBar.setOnRatingBarChangeListener (new RatingBar.OnRatingBarChangeListener () {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                addRating (rating,documentId);
            }
        });
    }

    public void AjoutBaseDonnees()
    {
        btnAImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted=baseRecette.insertData(tvtitle.getText().toString(),
                                tvingredient.getText().toString(),
                                tvpreparation.getText().toString(),tvdescription.getText().toString(),
                                imageViewToByte(img), String.valueOf (note.getRating()));

                        if(isInserted==true) {
                            Toast.makeText(RecipeActivity.this, "Sauvegarde effectuée!!", Toast.LENGTH_LONG).show();

                        }
                        else
                            Toast.makeText(RecipeActivity.this , "Sauvegarde non effectuée!", Toast.LENGTH_LONG).show();
                    }
                }

        );

    }
    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte[] byteArray=stream.toByteArray();
        return  byteArray;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.main_menu, menu);
        return super.onCreateOptionsMenu (menu);
    }

    private Task<Void> addRating(final float rating, String documentId) {

        DocumentReference newRecipeRef = db.collection ("Recipes").document (documentId);
        newRecipeRef.update("note", rating)
                .addOnSuccessListener(new OnSuccessListener < Void > () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RecipeActivity.this, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        return null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId () == R.id.list_recipes) {
            Intent listRecipes = new Intent (getApplicationContext (), GridViewActivity.class);
            startActivity (listRecipes);
            finish ();
        }

        if (item.getItemId () == R.id.add_recipe) {
            Intent addRecipe = new Intent (getApplicationContext (), AddRecipeActivity.class);
            startActivity (addRecipe);
            finish ();
        }
        if (item.getItemId () == R.id.search_recipe) {
            Intent searchActivity = new Intent (getApplicationContext (), SearchActivity.class);
            startActivity (searchActivity);
            finish ();
        }
        if(item.getItemId()==R.id.user_favoris){
            Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
            startActivity(Recipes_list);
            finish();
        }
        if (item.getItemId () == R.id.user_settings) {
            Intent searchActivity = new Intent (getApplicationContext (), MainActivity.class);
            startActivity (searchActivity);
            finish ();
        }
        if (item.getItemId () == R.id.list_recipes) {
            Intent recipeActivity = new Intent (getApplicationContext (), GridViewActivity.class);
            startActivity (recipeActivity);
            finish ();
        }
        return super.onOptionsItemSelected (item);
    }
}
