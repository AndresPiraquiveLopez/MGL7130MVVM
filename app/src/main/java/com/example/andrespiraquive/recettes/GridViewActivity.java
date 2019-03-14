package com.example.andrespiraquive.recettes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private static final String IMAGE_KEY = "image";
    private static final String TITLE_KEY = "title";
    private static final String NOTE_KEY = "note";
    private static final String DESCRIPTION_KEY = "description";
    private static final String INGREDIENTS_KEY = "ingredients";
    private static final String PREPARATIONS_KEY = "preparations";
    private static final String POSITION_KEY = "position";

    private RecyclerView recyclerView;

    private GridViewAdapter adapter;

    List<Recipes> lsRecipe;

    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        db = FirebaseFirestore.getInstance();
        lsRecipe = new ArrayList<>();

        //Data from firestore
        db.collection("Recipes")
                .orderBy (TITLE_KEY)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lsRecipe.add(new Recipes(document.get(IMAGE_KEY).toString(),
                                        document.get(TITLE_KEY).toString(), document.get(INGREDIENTS_KEY).toString(),
                                        document.get(DESCRIPTION_KEY).toString(), document.get(PREPARATIONS_KEY).toString(),
                                        (double) document.get(NOTE_KEY), document.get(POSITION_KEY).toString(),document.getId ()));
                            }
                            RecyclerView myrv = findViewById(R.id.recycle_view_id);
                            GridViewAdapter myAdapter = new GridViewAdapter(lsRecipe, getApplicationContext());
                            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                if(getResources().getConfiguration().screenWidthDp >= 600){
                                    myrv.setLayoutManager(new GridLayoutManager (getApplicationContext(), 3));
                                }
                                else{
                                    myrv.setLayoutManager(new GridLayoutManager (getApplicationContext(), 2));
                                }
                            }
                            else
                            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                                if(getResources().getConfiguration().screenWidthDp >= 921){
                                    myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
                                }
                                else{
                                    myrv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
                                }
                            }
                            myrv.setAdapter(myAdapter);
                        }else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
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
