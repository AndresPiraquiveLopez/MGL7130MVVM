package com.example.andrespiraquive.recettes.Views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    TextView textView;
    Button btnDeleteUser, btnLogout;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.userLoginNameView);
        btnDeleteUser = (Button) findViewById(R.id.kullaniciSil);
        btnLogout = (Button) findViewById(R.id.cikis_yap);

        firebaseAuth = FirebaseAuth.getInstance();

        authenticationWorldRecipes();

    }
    private void authenticationWorldRecipes(){
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        textView.setText(user.getEmail());

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "User deleted", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_recipe) {
            Intent addRecipe = new Intent(getApplicationContext(), AddRecipeActivity.class);
            startActivity(addRecipe);
            finish();
        }
        if (item.getItemId() == R.id.search_recipe) {
            Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(searchActivity);
            finish();
        }
        if (item.getItemId() == R.id.user_favoris) {
            Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
            startActivity(Recipes_list);
            finish();
        }
        if (item.getItemId() == R.id.user_settings) {
            Intent searchActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(searchActivity);
            finish();
        }
        if (item.getItemId() == R.id.list_recipes) {
            Intent recipeActivity = new Intent(getApplicationContext(), GridViewActivity.class);
            startActivity(recipeActivity);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
