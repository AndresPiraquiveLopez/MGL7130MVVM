package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.andrespiraquive.recettes.Models.RecipeResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private static final String TITRE_KEY = "titre";
    private static final String DESCRIPTION_KEY = "description";

    private RecyclerView recyclerView;

    private GridViewAdapter adapter;

    List<RecipeResponse> lsRecipe;

    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        db = FirebaseFirestore.getInstance();
        lsRecipe = new ArrayList<>();

        //Data from firestore
        db.collection("Recipes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,document.get(TITRE_KEY).toString(),(float) 2, document.get(DESCRIPTION_KEY).toString()));
                            }
                            RecyclerView myrv = findViewById(R.id.recycle_view_id);
                            GridViewAdapter myAdapter = new GridViewAdapter(lsRecipe, getApplicationContext());
                            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                            }
                            else
                            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                                myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
                            }
                            myrv.setAdapter(myAdapter);
                        }else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });


        /**
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 1"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 2",(float) 2, "Difficult"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 3",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 4", (float)1, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 5",(float) 4, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 6",(float) 2, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 7",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 8",(float) 1, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 1",(float) 4, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 2",(float) 2, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 3",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 4",(float) 1, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 5",(float) 4, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 6",(float) 2, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 7",(float) 3, "Traditional"));
        lsRecipe.add(new RecipeResponse(R.drawable.add_photo_512,"Recipe 8",(float) 1, "Traditional"));
*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.list_recipes) {
            Intent listRecipes = new Intent(getApplicationContext(), GridViewActivity.class);
            startActivity(listRecipes);
            finish();
        }

        if(item.getItemId()==R.id.add_recipe){
            Intent addRecipe = new Intent(getApplicationContext(),AddRecipe.class);
            startActivity(addRecipe);
            finish();
        }
        if(item.getItemId()==R.id.search_recipe){
            Intent searchActivity = new Intent(getApplicationContext(),SearchActivity.class);
            startActivity(searchActivity);
            finish();
        }
        if(item.getItemId()==R.id.user_settings){
            Intent searchActivity = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(searchActivity);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
