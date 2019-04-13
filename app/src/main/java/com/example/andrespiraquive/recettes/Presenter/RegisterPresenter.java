package com.example.andrespiraquive.recettes.Presenter;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Data.Network.FirebaseAuthRecipes;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterPresenter extends ViewModel {
    private Context mContex;
    private FirebaseAuthRecipes mAuth;

    public RegisterPresenter(Context context, FirebaseAuthRecipes auth) {
        mContex = context;
        mAuth = auth;
    }

    public void createUserWithEmailAndPassword(String email, String password) {
        mAuth.StartAuthRecipes ().createUserWithEmailAndPassword (email, password)
                .addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful ()) {
                            mContex.startActivity (new Intent (mContex.getApplicationContext (), MainActivity.class));
                            ((Activity) mContex).finish ();
                        } else {
                            Toast.makeText (mContex.getApplicationContext (), "E-mail or password is wrong", Toast.LENGTH_SHORT).show ();
                        }
                    }
                });
    }
}
