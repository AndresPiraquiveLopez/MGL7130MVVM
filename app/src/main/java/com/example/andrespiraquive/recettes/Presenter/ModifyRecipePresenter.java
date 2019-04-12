package com.example.andrespiraquive.recettes.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.andrespiraquive.recettes.Models.Recipes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ModifyRecipePresenter {

    private FirebaseFirestore db;
    private boolean updated;

    public ModifyRecipePresenter() {
    }

    public boolean updateRecipe(final FirestoreCallback firestoreCallback, String DocumentId, String titre, String ingredients, String description, String preparation) {

        db = FirebaseFirestore.getInstance ();

        //Recipes recetteAjouter = new Recipes (titre.getText ().toString (),
        //ingredients.getText ().toString (), description.getText ().toString (),
        //preparation.getText ().toString ());

        DocumentReference newRecipeRef = db.collection ("Recipes").document (DocumentId);
        newRecipeRef.update ("title", titre, "ingredients", ingredients, "description", description, "preparations", preparation)
                .addOnSuccessListener (new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //updated = true;
                        firestoreCallback.onCallback(true);
                    }
                })
                .addOnFailureListener (new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //updated = false;
                        firestoreCallback.onCallback(false);
                        Log.d ("TAG", e.toString ());
                    }
                });
        return updated;
    }

    public interface  FirestoreCallback {

        void onCallback(Boolean updated);
    }

}
