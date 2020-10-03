package com.example.individualproject1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity { //Made this activity so that there is a visible change on successful login, also to demonstrate the use of Intent Extras.

    TextView TextViewWelcome;
    Bundle extras; //Used to store intent extras

    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_home);
        TextViewWelcome = (TextView)findViewById(R.id.TextViewWelcome_Home);

        extras = getIntent().getExtras(); //GetExtras from the intent
        if (extras != null){ //If intent extras were sent
            TextViewWelcome.setText("Welcome, " + extras.getString("firstname") + "."); //Use the firstname extra to create a welcome message
        } else TextViewWelcome.setText("Welcome"); //If intent extras were not sent

    }

    public void logout(View view) { //Logout button just returns to homepage
        Intent LogoutIntent = new Intent(this, MainActivity.class);
        startActivity(LogoutIntent);
    }
}
