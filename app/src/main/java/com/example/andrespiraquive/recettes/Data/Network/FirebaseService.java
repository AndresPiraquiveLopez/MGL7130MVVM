package com.example.andrespiraquive.recettes.Data.Network;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseService implements IFirebaseService{

    private FirebaseFirestore _db;

    public FirebaseService(){
    }

    public FirebaseFirestore StartFireBaseService(){
        _db =  FirebaseFirestore.getInstance ();
        return _db;
    }

}

