package com.example.andrespiraquive.recettes.Services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.andrespiraquive.recettes.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.andrespiraquive.recettes.Applications.App.CHANNEL_ID;

public class RecipeService extends IntentService {

    public RecipeService() {
        super ("RecipesService");
    }

    @Override
    public void onCreate() {
        super.onCreate ();
        Log.d ("TAG", "Service Created...");
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d ("TAG", "Service Starting...");
        return super.onStartCommand (intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        FirebaseFirestore db = FirebaseFirestore.getInstance ();

        CollectionReference myref = db.collection ("Recipes");
        myref.addSnapshotListener (new EventListener<QuerySnapshot> () {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText (getApplicationContext (), "Error while loading!", Toast.LENGTH_SHORT).show ();
                    Log.d ("TAG", e.toString ());
                    return;
                }

                Log.d ("TAG", "SIZE QUERY = " + queryDocumentSnapshots.size ());
                for (DocumentChange documentSnapshot : queryDocumentSnapshots.getDocumentChanges ()) {
                    switch (documentSnapshot.getType ()) {
                        case ADDED:
                            String title = documentSnapshot.getDocument ().get ("title").toString ();
                            Notification nb = new NotificationCompat.Builder (getApplicationContext (), CHANNEL_ID)
                                    .setSmallIcon (R.mipmap.icone_recipes)
                                    .setContentTitle ("New Recipes")
                                    .setContentText (title)
                                    .setPriority (NotificationCompat.PRIORITY_DEFAULT)
                                    .build ();

                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from (getApplicationContext ());
                            notificationManagerCompat.notify (1, nb);
                            Log.d ("TAG", "New recipe: " + documentSnapshot.getDocument ().getData ());
                            break;
                        case MODIFIED:
                            Log.d ("TAG", "Modified recipe: " + documentSnapshot.getDocument ().getData ());
                            break;
                        case REMOVED:
                            Log.d ("TAG", "Removed recipe: " + documentSnapshot.getDocument ().getData ());
                            break;
                    }
                }

            }
        });
    }

    @Override
    public void onDestroy() {
        Log.d ("TAG", "Service Destroyed...");
        super.onDestroy ();
    }
}
