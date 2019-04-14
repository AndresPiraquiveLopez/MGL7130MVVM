package com.example.andrespiraquive.recettes.Presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.andrespiraquive.recettes.Models.Recipes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddRecipePresenter {

    private LocationManager locationManager;


    public AddRecipePresenter() {

    }

    public String getLocation(Context context) {
        String position = "";
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
            } catch (IOException e) {
                Log.d("TAG ", "e = " + e);
            }
            if (addresses != null) {
                position = addresses.get(0).getLocality() + ", " + addresses.get(0).getCountryName();
            } else
                Log.e("TAG", "No address find");

        } else {
            position = "International";
        }
        return position;
    }

    public void addNewRecipe(final FirestoreCallback firestoreCallback, String urlImageUpload, Recipes recetteAjouter) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference newRecipeRef = db.collection("Recipes").document();
        newRecipeRef.set(recetteAjouter)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firestoreCallback.onCallback(true);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        firestoreCallback.onCallback(false);
                    }
                });
    }


    public interface FirestoreCallback {

        void onCallback(Boolean addCompleted);
    }

}
