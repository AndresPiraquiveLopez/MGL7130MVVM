package com.example.andrespiraquive.recettes.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.andrespiraquive.recettes.Data.Network.FirebaseService;
import com.example.andrespiraquive.recettes.Models.Recipes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class GridViewModel extends ViewModel{


    private List<Recipes> mRecipes = new ArrayList<>();

    public GridViewModel() {
    }

    public void getAllRecipes(final FirestoreCallback firestoreCallback){

        final String IMAGE_KEY = "image";
        final String TITLE_KEY = "title";
        final String NOTE_KEY = "note";
        final String DESCRIPTION_KEY = "description";
        final String INGREDIENTS_KEY = "ingredients";
        final String PREPARATIONS_KEY = "preparations";
        final String POSITION_KEY = "position";
        final String COLLECTION_PATH = "Recipes";



        FirebaseService db = new FirebaseService();
        db.StartFireBaseService();

        db.StartFireBaseService().collection (COLLECTION_PATH)
                .orderBy (TITLE_KEY)
                .get ()
                .addOnCompleteListener (new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful ()) {
                            for (QueryDocumentSnapshot document : task.getResult ()) {
                                mRecipes.add (new Recipes (document.get (IMAGE_KEY).toString (),
                                        document.get (TITLE_KEY).toString (), document.get (INGREDIENTS_KEY).toString (),
                                        document.get (DESCRIPTION_KEY).toString (), document.get (PREPARATIONS_KEY).toString (),
                                        (double) document.get (NOTE_KEY), document.get (POSITION_KEY).toString (), document.getId ()));
                            }
                            firestoreCallback.onCallback(mRecipes);

                        } else {
                            Log.d ("TAG", "Error getting documents: ", task.getException ());
                        }


                    }
                });
    }

    public interface  FirestoreCallback {

        void onCallback(List<Recipes> listRecipes);
    }

}
