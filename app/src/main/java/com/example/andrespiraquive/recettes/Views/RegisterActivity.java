package com.example.andrespiraquive.recettes.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Data.Network.FirebaseAuthRecipes;
import com.example.andrespiraquive.recettes.Presenter.RegisterPresenter;
import com.example.andrespiraquive.recettes.R;
import com.google.firebase.FirebaseApp;

public class RegisterActivity extends AppCompatActivity {
    private EditText emaill,passwordd;
    private Button registerButton,loginButton;
    private FirebaseAuthRecipes mAuth = null;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = new FirebaseAuthRecipes ();
        FirebaseApp.initializeApp(this);

        emaill = findViewById(R.id.uyeEmail);
        passwordd = findViewById(R.id.password);
        registerButton = findViewById(R.id.newMemberButton);
        loginButton = findViewById(R.id.alreadyMemberButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaill.getText().toString();
                String password = passwordd.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }

                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }

                mRegisterPresenter = new RegisterPresenter (RegisterActivity.this, mAuth);
                mRegisterPresenter.createUserWithEmailAndPassword(email,password);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        if(mAuth.StartAuthRecipes ().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}
