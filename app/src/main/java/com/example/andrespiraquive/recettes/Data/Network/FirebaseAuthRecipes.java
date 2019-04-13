package com.example.andrespiraquive.recettes.Data.Network;

import android.widget.Button;
import android.widget.EditText;

import com.example.andrespiraquive.recettes.R;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthRecipes implements IFirebaseAuthRecipes {
    private FirebaseAuth mFirebaseAuth;

    public FirebaseAuthRecipes(){
    }
    public FirebaseAuth StartAuthRecipes(){
        mFirebaseAuth =  FirebaseAuth.getInstance();
        return mFirebaseAuth;
    }
}
