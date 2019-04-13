package com.example.andrespiraquive.recettes.Data.Network;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseService implements IFirebaseService{

    private FirebaseFirestore mDb;

    public FirebaseService(){
    }

    public FirebaseFirestore StartFireBaseService(){
        mDb =  FirebaseFirestore.getInstance ();
        return mDb;
    }

}

