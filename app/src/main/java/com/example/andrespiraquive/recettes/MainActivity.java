package com.example.andrespiraquive.recettes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String CLASS_NAME = MainActivity.class.getName();

    private TextView mTextMessage;

  

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(CLASS_NAME, "onCreate");

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(CLASS_NAME, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(CLASS_NAME, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(CLASS_NAME, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(CLASS_NAME, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(CLASS_NAME, "onRestart");
    }
}
