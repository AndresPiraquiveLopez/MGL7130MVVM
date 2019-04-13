package com.example.andrespiraquive.recettes.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.AddRecipeActivity;
import com.example.andrespiraquive.recettes.Data.Database.DataBase;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.Presenter.RecipePresenter;
import com.example.andrespiraquive.recettes.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class RecipeActivity extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvingredient, tvpreparation, tvposition;
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
    private String Position;
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
        tvposition = findViewById(R.id.position_id);
        recipePresenter = new RecipePresenter ();

        //Recieve data
        Intent intent = getIntent();
        IsFavorie = intent.getExtras().getBoolean("isFavorie");
        Document = intent.getExtras().getString("Document");

        if (Document == null) {
            Id = intent.getExtras().getInt("Id");
            Title = intent.getExtras().getString("Title");
            Note = intent.getExtras().getDouble("Note");
            Description = intent.getExtras().getString("Description");
            Ingredient = intent.getExtras().getString("Ingredient");
            Preparation = intent.getExtras().getString("Preparation");
            Position = intent.getExtras().getString("Position");

            Cursor recipeFavorite = baseRecette.geDataById(Id); //Get photo from SQLLITE
            if(recipeFavorite.getCount() == 1){
                recipeFavorite.moveToFirst();
                ImageId = recipeFavorite.getBlob(5);
                img.setImageBitmap(BitmapFactory.decodeByteArray(ImageId, 0, ImageId.length));
            }
            //settings values from intent
            tvtitle.setText(Title);
            note.setRating((float) Note);
            tvdescription.setText(Description);
            tvingredient.setText(Ingredient);
            tvpreparation.setText(Preparation);
            tvposition.setText(Position);

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
            recipePresenter.getRecipes(new RecipePresenter.FirestoreGetRecipeCallback() { //From Firestore
                @Override
                public void onCallback(Recipes mRecipes) {
                    image = mRecipes.getImage();
                    Title = mRecipes.getTitle();
                    Note = mRecipes.getNote();
                    Description = mRecipes.getDescription();
                    Ingredient = mRecipes.getIngredients();
                    Preparation = mRecipes.getPreparations();
                    Position = mRecipes.getPosition();

                    //settings values
                    Picasso.get()
                            .load(mRecipes.getImage())
                            .into(img);
                    tvtitle.setText(Title);
                    note.setRating((float) Note);
                    tvdescription.setText(Description);
                    tvingredient.setText(Ingredient);
                    tvpreparation.setText(Preparation);
                    tvposition.setText(Position);

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

    public void addListenerOnRatingBar(final String DocumentId) {

        ratingBar = (RatingBar) findViewById (R.id.recipe_details_raiting_id);

        ratingBar.setOnRatingBarChangeListener (new RatingBar.OnRatingBarChangeListener () {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                recipePresenter.addRating(new RecipePresenter.FirestoreRatingRecipeCallback() {
                    @Override
                    public void onCallback(Boolean deleted) {

                    }
                }, getApplicationContext(), rating, DocumentId);
            }
        });
    }


    public void FavoriteManager(){
        btnImageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsFavorie) {
                    //AjoutBaseDonnees();
                    recipePresenter.AjoutBaseDonnees(getApplicationContext(), tvtitle.getText().toString(),
                            tvingredient.getText().toString(),
                            tvpreparation.getText().toString(),tvdescription.getText().toString(),
                            recipePresenter.imageViewToByte(img), String.valueOf (note.getRating()), tvposition.getText().toString());
                    btnImageFavorite.setImageResource(R.drawable.favorite_yes);
                }
                else {
                    recipePresenter.SupprimerBaseDonnées(getApplicationContext(), tvtitle.getText().toString());
                    btnImageFavorite.setImageResource(R.drawable.favorite_no);
                    Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
                    startActivity(Recipes_list);
                    finish();
                }
            }
        });
    }

    public void deleteRecipe(final String DocumentId, final String Title){
        AlertDialog.Builder alertDeleteRecipe = new AlertDialog.Builder(this);
        alertDeleteRecipe.setTitle("Confirmation delete !");
        alertDeleteRecipe.setMessage("You are about to delete the recipe named : " + Title + ". Do you really want to proceed?");
        alertDeleteRecipe.setCancelable(false);
        alertDeleteRecipe.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                recipePresenter.deleteRecipe(new RecipePresenter.FirestoreDeleteRecipeCallback() {
                    @Override
                    public void onCallback(Boolean deleted) {
                        Intent intentGridViewActivity = new Intent(RecipeActivity.this, GridViewActivity.class);
                        startActivity(intentGridViewActivity);
                        Toast.makeText(getApplicationContext(), "" + Title + " Has been deleted", Toast.LENGTH_SHORT).show();
                    }
                }, DocumentId);
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
                    deleteRecipe(Document, Title);
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
                intentModifyViewActivity.putExtra("Position", Position);
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
