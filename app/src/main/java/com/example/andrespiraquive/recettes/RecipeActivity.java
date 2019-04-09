package com.example.andrespiraquive.recettes;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Data.Database.DataBase;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.Presenter.RecipePresenter;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class RecipeActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvingredient, tvpreparation;
    private ImageView img, btnImageFavorite;
    private RatingBar note;
    private RatingBar ratingBar;
    private FirebaseFirestore db;
    private DataBase baseRecette;
    private RecipePresenter recipePresenter;

    private int Id;
    private byte[] ImageId;
    private String image;
    private String Title;
    private double Note;
    private String Description;
    private String Ingredient;
    private String Preparation;
    private String Document;
    private boolean IsFavorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        baseRecette = new DataBase(this);

        db = FirebaseFirestore.getInstance();
        img = (ImageView) findViewById(R.id.recipe_details_thumbnail_id);
        tvtitle = (TextView) findViewById(R.id.recipe_details_title_id);
        note = (RatingBar) findViewById(R.id.recipe_details_raiting_id);
        tvdescription = (TextView) findViewById(R.id.recipe_details_description_id);
        tvingredient = (TextView) findViewById(R.id.recipe_details_ingredient_id);
        tvpreparation = (TextView) findViewById(R.id.recipe_details_preparation_id);
        btnImageFavorite = findViewById(R.id.btn_image_favorite_id);
        recipePresenter = new RecipePresenter ();

        //Recieve data
        Intent intent = getIntent();
        IsFavorie = intent.getExtras().getBoolean("isFavorie");
        //image = intent.getExtras().getString("ImageId");
        //Log.d("TAG", "IMAGEID in RECIPE :" + image);
        //ImageId = intent.getExtras().getByteArray("ImageId");
        //final String Title = intent.getExtras ().getString ("Title");
        //final double Note = intent.getExtras ().getDouble ("Note");
        //final String Description = intent.getExtras ().getString ("Description");
        //final String Ingredient = intent.getExtras ().getString ("Ingredient");
        //final String Preparation = intent.getExtras ().getString ("Preparation");
        Document = intent.getExtras().getString("Document");
        if (Document == null) {
            Id = intent.getExtras().getInt("Id");
            Title = intent.getExtras().getString("Title");
            Note = intent.getExtras().getDouble("Note");
            Description = intent.getExtras().getString("Description");
            Ingredient = intent.getExtras().getString("Ingredient");
            Preparation = intent.getExtras().getString("Preparation");

            Cursor recipeFavorite = baseRecette.geDataById(Id);
            if(recipeFavorite.getCount() == 1){
                recipeFavorite.moveToFirst();
                ImageId = recipeFavorite.getBlob(5);
                img.setImageBitmap(BitmapFactory.decodeByteArray(ImageId, 0, ImageId.length));
            }
            //settings values
            tvtitle.setText(Title);
            note.setRating((float) Note);
            tvdescription.setText(Description);
            tvingredient.setText(Ingredient);
            tvpreparation.setText(Preparation);

            if (!IsFavorie) {
                btnImageFavorite.setImageResource(R.drawable.favorite_no);
                addListenerOnRatingBar(Document);
                FavoriteManager();
            } else {
                btnImageFavorite.setImageResource(R.drawable.favorite_yes);
                note.setEnabled(false);
                FavoriteManager();
                //btnImageFavorite.setEnabled(false);
            }
        } else {
            recipePresenter.getRecipes(new RecipePresenter.FirestoreCallback() {
                @Override
                public void onCallback(Recipes mRecipes) {
                    image = mRecipes.getImage();
                    Title = mRecipes.getTitle();
                    Note = mRecipes.getNote();
                    Description = mRecipes.getDescription();
                    Ingredient = mRecipes.getIngredients();
                    Preparation = mRecipes.getPreparations();

                    //settings values
                    Picasso.get()
                            .load(mRecipes.getImage())
                            .into(img);
                    tvtitle.setText(Title);
                    note.setRating((float) Note);
                    tvdescription.setText(Description);
                    tvingredient.setText(Ingredient);
                    tvpreparation.setText(Preparation);

                    if (!IsFavorie) {
                        btnImageFavorite.setImageResource(R.drawable.favorite_no);
                        addListenerOnRatingBar(Document);
                        FavoriteManager();
                    } else {
                        btnImageFavorite.setImageResource(R.drawable.favorite_yes);
                        note.setEnabled(false);
                        FavoriteManager();
                        //btnImageFavorite.setEnabled(false);
                    }
                }
            }, Document);
        }
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


    public void FavoriteManager(){
        btnImageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsFavorie) {
                    Log.d("TAG isFavorite = ","NO");
                    AjoutBaseDonnees();
                }
                else {
                    Log.d("TAG isFavorite = ","YES");
                    SupprimerBaseDonnées();
                }
            }
        });
    }

    public void AjoutBaseDonnees()
    {
        //btnImageFavorite.setOnClickListener(
                //new View.OnClickListener() {
                    //@Override
                    //public void onClick(View v) {
                        boolean isInserted=baseRecette.insertData(tvtitle.getText().toString(),
                                tvingredient.getText().toString(),
                                tvpreparation.getText().toString(),tvdescription.getText().toString(),
                                imageViewToByte(img), String.valueOf (note.getRating()));
                                btnImageFavorite.setImageResource(R.drawable.favorite_yes);

                        if(isInserted==true) {
                            Toast.makeText(RecipeActivity.this, "" + Title + " has been add to your Favorites!!", Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(RecipeActivity.this , "Not saved to your Favorites!", Toast.LENGTH_SHORT).show();
                   // }
                //}
       // );

    }

    private void SupprimerBaseDonnées() {

        //btnImageFavorite.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                int deleteRows = baseRecette.deleteData(tvtitle.getText().toString());
                if (deleteRows > 0) {
                    btnImageFavorite.setImageResource(R.drawable.favorite_no);
                    Toast.makeText(RecipeActivity.this, "Recipe has been deleted from your favorites!!", Toast.LENGTH_LONG).show();
                    Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
                    startActivity(Recipes_list);
                    finish();
                } else
                    Toast.makeText(RecipeActivity.this, "The recipe does not exist in your favorites!!", Toast.LENGTH_LONG).show();

           // }
        //});
    }

    private byte[] imageViewToByte(ImageView image)
    {
        Bitmap bitmap=((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
        byte[] byteArray=stream.toByteArray();
        return  byteArray;

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

    public void deleteRecipe(final String DocumentId, final String Title){
        AlertDialog.Builder alertDeleteRecipe = new AlertDialog.Builder(this);
        alertDeleteRecipe.setTitle("Confirmation delete !");
        alertDeleteRecipe.setMessage("You are about to delete the recipe named : " + Title + ". Do you really want to proceed?");
        alertDeleteRecipe.setCancelable(false);
        alertDeleteRecipe.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.collection("Recipes").document(DocumentId).delete();
                Intent intentGridViewActivity = new Intent(RecipeActivity.this, GridViewActivity.class);
                startActivity(intentGridViewActivity);
                Toast.makeText(getApplicationContext(), "" + Title + " Has been deleted", Toast.LENGTH_SHORT).show();
            }
        });
        alertDeleteRecipe.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Nothing has changed!!!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDeleteRecipe.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.recipe_menu, menu);
        return super.onCreateOptionsMenu (menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                deleteRecipe(Document,Title);
                return true;
            case R.id.action_modify:
                Intent intentModifyViewActivity = new Intent(RecipeActivity.this, ModifyRecipeActivity.class);
                intentModifyViewActivity.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
                intentModifyViewActivity.putExtra("ImageId",image);
                intentModifyViewActivity.putExtra("Title",Title);
                intentModifyViewActivity.putExtra("Description",Description);
                intentModifyViewActivity.putExtra("Ingredient",Ingredient);
                intentModifyViewActivity.putExtra("Preparation",Preparation);
                intentModifyViewActivity.putExtra("DocumentId", Document);
                intentModifyViewActivity.putExtra("isFavorie", IsFavorie);
                startActivity(intentModifyViewActivity);
                finish();
                return true;
            case R.id.list_recipes:
                Intent listRecipes = new Intent (getApplicationContext (), GridViewActivity.class);
                startActivity (listRecipes);
                finish ();
                return true;
            case R.id.add_recipe:
                Intent addRecipe = new Intent (getApplicationContext (), AddRecipeActivity.class);
                startActivity (addRecipe);
                finish ();
                return true;
            case R.id.search_recipe:
                Intent searchActivity = new Intent (getApplicationContext (), SearchActivity.class);
                startActivity (searchActivity);
                finish ();
                return true;
            case R.id.user_favoris:
                Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
                startActivity(Recipes_list);
                finish();
                return true;
            case R.id.user_settings:
                Intent userSettings = new Intent (getApplicationContext (), MainActivity.class);
                startActivity (userSettings);
                finish ();
                return true;
            default:
                return super.onOptionsItemSelected (item);
        }




    }
}
