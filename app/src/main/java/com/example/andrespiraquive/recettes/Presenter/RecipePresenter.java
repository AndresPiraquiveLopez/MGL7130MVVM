package com.example.andrespiraquive.recettes.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.andrespiraquive.recettes.Data.Network.FirebaseService;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class RecipePresenter {

    private Recipes mRecipes = new Recipes();

    public void getRecipes(final RecipePresenter.FirestoreCallback firestoreCallback, String DocumentId){

        final String IMAGE_KEY = "image";
        final String TITLE_KEY = "title";
        final String NOTE_KEY = "note";
        final String DESCRIPTION_KEY = "description";
        final String INGREDIENTS_KEY = "ingredients";
        final String PREPARATIONS_KEY = "preparations";
        final String POSITION_KEY = "position";
        final String COLLECTION_PATH = "Recipes";



        FirebaseService db = new FirebaseService();
        //db.StartFireBaseService();

        DocumentReference dr = db.StartFireBaseService().collection ("Recipes").document(DocumentId);
                dr.get().addOnCompleteListener (new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists ()) {
                                mRecipes = new Recipes (document.get (IMAGE_KEY).toString (),
                                        document.get (TITLE_KEY).toString (), document.get (INGREDIENTS_KEY).toString (),
                                        document.get (DESCRIPTION_KEY).toString (), document.get (PREPARATIONS_KEY).toString (),
                                        (double) document.get (NOTE_KEY), document.get (POSITION_KEY).toString (), document.getId ());
                            //Log.d("TAG", "mRecipes IN= " + mRecipes.size();
                            firestoreCallback.onCallback(mRecipes);

                        } else {
                            Log.d ("TAG", "Error getting documents: ", task.getException ());
                        }


                    }
                });
    }

    public interface  FirestoreCallback {

        void onCallback(Recipes mRecipes);
    }

}
