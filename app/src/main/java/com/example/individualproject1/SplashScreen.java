package com.example.individualproject1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent splashIntent = new Intent(this, MainActivity.class); //New intent to start main activity
        startActivity(splashIntent);
        finish();
    }
}
