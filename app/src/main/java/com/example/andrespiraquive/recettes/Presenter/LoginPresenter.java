package com.example.andrespiraquive.recettes.Presenter;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Data.Network.FirebaseAuthRecipes;
import com.example.andrespiraquive.recettes.R;
import com.example.andrespiraquive.recettes.Views.GridViewActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresenter extends ViewModel {

    private FirebaseAuthRecipes mAuth;
    private Context mContex;

    public LoginPresenter(Context context, FirebaseAuthRecipes auth) {
        mContex = context;
        mAuth = auth;
    }

    public void AuthenticateUser(String email, final String password, final Button loginButton) {
        mAuth.StartAuthRecipes ().signInWithEmailAndPassword (email, password)
                .addOnCompleteListener ((Activity) mContex, new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful ()) {
                            if (password.length () < 6) {
                                Toast.makeText (mContex, mContex.getString (R.string.minimum_password), Toast.LENGTH_LONG).show ();
                            } else {
                                Toast.makeText (mContex, mContex.getString (R.string.auth_failed), Toast.LENGTH_LONG).show ();
                            }
                        } else {
                            Intent intent = new Intent (mContex, GridViewActivity.class);
                            mContex.startActivity (intent);
                            ((Activity) mContex).finish ();
                        }
                    }
                });
    }

    public void AuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential (account.getIdToken (), null);
        mAuth.StartAuthRecipes ().signInWithCredential (credential).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful ()) {
                    Intent intent = new Intent (mContex, GridViewActivity.class);
                    mContex.startActivity (intent);
                    ((Activity) mContex).finish ();
                } else {
                    Toast.makeText (mContex.getApplicationContext (), "Auth Error", Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }
}
