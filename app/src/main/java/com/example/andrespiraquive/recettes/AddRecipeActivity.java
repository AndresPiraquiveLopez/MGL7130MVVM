package com.example.andrespiraquive.recettes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.Models.Recipes;
import com.example.andrespiraquive.recettes.Presenter.AddRecipePresenter;
import com.example.andrespiraquive.recettes.Views.GridViewActivity;
import com.example.andrespiraquive.recettes.Views.MainActivity;
import com.example.andrespiraquive.recettes.Views.SearchActivity;
import com.example.andrespiraquive.recettes.Views.favorisActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRecipeActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private FirebaseFirestore db;
    private EditText editTitre;
    private EditText editIngredient;
    private EditText editDescription;
    private EditText editPreparation;
    private ImageView imageRecipe;
    private String currentPhotoPath;
    private Uri photoURI;
    private StorageReference mStorageRef;
    private String gpsPosition, position;
    private Uri downloadUri;
    private AddRecipePresenter addRecipePresenter;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        addRecipePresenter = new AddRecipePresenter();
        db = FirebaseFirestore.getInstance();

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        position = addRecipePresenter.getLocation(getApplicationContext());


        editTitre = findViewById(R.id.titre);
        editIngredient = findViewById(R.id.editIngredient);
        editDescription = findViewById(R.id.editDescription);
        editPreparation = findViewById(R.id.editPreparation);
        imageRecipe = findViewById(R.id.imageRecipe);



        final Button buttonSave = findViewById(R.id.saveRecipeButton);
        final Button buttonCancel = findViewById(R.id.cancelRecipeButton);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showProgessBar();
                // Code here executes on main thread after user presses button
                if (editTitre.getText().toString().isEmpty() || editDescription.getText().toString().isEmpty()
                        || editIngredient.getText().toString().isEmpty() || editPreparation.getText().toString().isEmpty()
                        || photoURI == null) {
                    Toast.makeText(AddRecipeActivity.this, "You must fill all the fields...", Toast.LENGTH_LONG).show();
                } else {
                    uploadImage();
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRecipeActivity.this, GridViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchPictureTakerAction();
            }


        });

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchPictureTakerAction() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            //Photo will go in this file
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.d("CAM_TAG", ex.toString());
            }
            // If the File was created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "com.example.andrespiraquive.recettes.fileprovider", photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            imageRecipe.setImageBitmap(imageBitmap);
        }
    }

    private String uploadImage() {
        UploadTask uploadTask;
        InputStream imageStream = null;

        try {
            imageStream = getContentResolver().openInputStream(photoURI);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bitmapimage = BitmapFactory.decodeStream(imageStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapimage.compress(Bitmap.CompressFormat.JPEG, 30, baos);
        byte[] bitmapData = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mStorageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference ref = mStorageRef.child(photoURI.getLastPathSegment());
        uploadTask = ref.putBytes(bitmapData);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri = task.getResult();
                    //addNewRecipe(downloadUri.toString());
                    Recipes recetteAjouter = new Recipes(downloadUri.toString(), editTitre.getText().toString(),
                            editIngredient.getText().toString(), editDescription.getText().toString(),
                            editPreparation.getText().toString(), 0.0, position, "");
                    addRecipePresenter.addNewRecipe(new AddRecipePresenter.FirestoreCallback() {
                        @Override
                        public void onCallback(Boolean addCompleted) {

                            if(addCompleted){
                                Toast.makeText(AddRecipeActivity.this, "Recipe Registered",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(AddRecipeActivity.this, GridViewActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(AddRecipeActivity.this, "ERROR : Recipe not created",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("TAG", "Error : Created the recipe");
                            }
                        }
                    }, downloadUri.toString(), recetteAjouter);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        return mStorageRef.getDownloadUrl().toString();
    }

    private void showProgessBar(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setMessage("Saving recipe...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_recipes:
                Intent listRecipes = new Intent(getApplicationContext(), GridViewActivity.class);
                startActivity(listRecipes);
                finish();
                return true;
            case R.id.add_recipe:
                Intent addRecipe = new Intent(getApplicationContext(), AddRecipeActivity.class);
                startActivity(addRecipe);
                finish();
                return true;
            case R.id.search_recipe:
                Intent searchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(searchActivity);
                finish();
                return true;
            case R.id.user_favoris:
                Intent Recipes_list = new Intent(getApplicationContext(), favorisActivity.class);
                startActivity(Recipes_list);
                finish();
                return true;
            case R.id.user_settings:
                Intent userSettings = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(userSettings);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
