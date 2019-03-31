package com.example.andrespiraquive.recettes.ViewModels;

import android.content.Context;
import android.arch.lifecycle.ViewModel;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.andrespiraquive.recettes.Data.Network.FirebaseService;
import com.example.andrespiraquive.recettes.GridViewAdapter;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GridViewModel extends ViewModel{
    private static final String IMAGE_KEY = "image";
    private static final String TITLE_KEY = "title";
    private static final String NOTE_KEY = "note";
    private static final String DESCRIPTION_KEY = "description";
    private static final String INGREDIENTS_KEY = "ingredients";
    private static final String PREPARATIONS_KEY = "preparations";
    private static final String POSITION_KEY = "position";
    private static final String COLLECTION_PATH = "Recipes";

    private List<Recipes> mRecipes;
    private FirebaseService db = new FirebaseService ();

    public List<Recipes> getRecipes(){

       mRecipes = new ArrayList<> ();

        //Data from firestore
        db.StartFireBaseService ().collection (COLLECTION_PATH)
                .orderBy (TITLE_KEY)
                .get ()
                .addOnCompleteListener (new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful ()) {
                            for (QueryDocumentSnapshot document : task.getResult ()) {
                                mRecipes.add (new Recipes (document.get (IMAGE_KEY).toString (),
                                        document.get (TITLE_KEY).toString (), document.get (INGREDIENTS_KEY).toString (),
                                        document.get (DESCRIPTION_KEY).toString (), document.get (PREPARATIONS_KEY).toString (),
                                        (double) document.get (NOTE_KEY), document.get (POSITION_KEY).toString (), document.getId ()));
                            }
                        } else {
                            Log.d ("TAG", "Error getting documents: ", task.getException ());
                        }
                    }
                });
        return mRecipes;
    }
}
