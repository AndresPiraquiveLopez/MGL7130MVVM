package com.example.andrespiraquive.recettes.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Data.Database.DataBase;
import com.example.andrespiraquive.recettes.Data.Network.FirebaseService;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.ByteArrayOutputStream;

public class RecipePresenter {

    private Recipes mRecipes = new Recipes ();
    private DataBase baseRecette;
    FirebaseService db = new FirebaseService ();


    public void getRecipes(final FirestoreGetRecipeCallback firestoreCallback, String DocumentId) {

        final String IMAGE_KEY = "image";
        final String TITLE_KEY = "title";
        final String NOTE_KEY = "note";
        final String DESCRIPTION_KEY = "description";
        final String INGREDIENTS_KEY = "ingredients";
        final String PREPARATIONS_KEY = "preparations";
        final String POSITION_KEY = "position";
        final String COLLECTION_PATH = "Recipes";

        DocumentReference dr = db.StartFireBaseService ().collection ("Recipes").document (DocumentId);
        dr.get ().addOnCompleteListener (new OnCompleteListener<DocumentSnapshot> () {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult ();
                if (document.exists ()) {
                    mRecipes = new Recipes (document.get (IMAGE_KEY).toString (),
                            document.get (TITLE_KEY).toString (), document.get (INGREDIENTS_KEY).toString (),
                            document.get (DESCRIPTION_KEY).toString (), document.get (PREPARATIONS_KEY).toString (),
                            (double) document.get (NOTE_KEY), document.get (POSITION_KEY).toString (), document.getId ());
                    //Log.d("TAG", "mRecipes IN= " + mRecipes.size();
                    firestoreCallback.onCallback (mRecipes);

                } else {
                    Log.d ("TAG", "Error getting documents: ", task.getException ());
                }


            }
        });
    }

    public void deleteRecipe(final FirestoreDeleteRecipeCallback firestoreCallback, final String DocumentId) {


        db.StartFireBaseService ().collection ("Recipes").document (DocumentId).delete ()
                .addOnCompleteListener (new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firestoreCallback.onCallback (true);
                    }
                });


    }

    public void addRating(final FirestoreRatingRecipeCallback firestoreCallback, final Context context, final float rating, String DocumentId) {
        DocumentReference dr = db.StartFireBaseService ().collection ("Recipes").document (DocumentId);
        dr.update ("note", rating)
                .addOnSuccessListener (new OnSuccessListener<Void> () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText (context, "Updated Successfully",
                                Toast.LENGTH_SHORT).show ();
                        firestoreCallback.onCallback (true);
                    }
                });
    }

    public void AjoutBaseDonnees(Context context, String titre, String ingredient, String preparation, String description, byte[] image, String note, String position) {
        baseRecette = new DataBase (context);
        boolean isInserted = baseRecette.insertData (titre, ingredient, preparation, description, image, note, position);
        if (isInserted == true) {
            Toast.makeText (context, "" + titre + " has been add to your Favorites!!", Toast.LENGTH_SHORT).show ();
        } else
            Toast.makeText (context, "The recipes already exist in your favorites!", Toast.LENGTH_SHORT).show ();
    }

    public void SupprimerBaseDonnées(Context context, String titre) {
        baseRecette = new DataBase (context);
        int deleteRows = baseRecette.deleteData (titre);
        if (deleteRows > 0) {
            Toast.makeText (context, "Recipe has been deleted from your favorites!!", Toast.LENGTH_LONG).show ();

        } else
            Toast.makeText (context, "The recipe does not exist in your favorites!!", Toast.LENGTH_LONG).show ();
    }

    public byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable ()).getBitmap ();
        ByteArrayOutputStream stream = new ByteArrayOutputStream ();
        bitmap.compress (Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray ();
        return byteArray;

    }

    public interface FirestoreGetRecipeCallback {

        void onCallback(Recipes mRecipes);

    }

    public interface FirestoreDeleteRecipeCallback {

        void onCallback(Boolean deleted);

    }

    public interface FirestoreRatingRecipeCallback {

        void onCallback(Boolean deleted);

    }

}
