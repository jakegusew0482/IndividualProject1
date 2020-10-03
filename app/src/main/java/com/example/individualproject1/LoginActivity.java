package com.example.individualproject1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    EditText UsernameET, PasswordET;
    DBHelper myDB;
    String firstname;
    Bundle extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameET = (EditText)   findViewById(R.id.EditTextUsername_Login);
        PasswordET = (EditText)   findViewById(R.id.EditTextPassword_Login);
        myDB = new DBHelper(this);

        extras = getIntent().getExtras(); //Get extras, this will only be not null when this activity is started from registration, and a username is sent through extras.
        if (extras != null){ //If extras not null
            UsernameET.setText(extras.getString("EXTRA_USER_NAME")); //Get the username and put it in the username edittext after a user registers.
        }

    }
    public void tryLogin(View view){ //Called when login is clicked
        firstname = myDB.checkForUser(UsernameET.getText().toString(),PasswordET.getText().toString()); //Call the login function in DBHelper which returns a string (firstname).
        if (firstname == "") { //If the string returned from DBHelper is empty, no matching user/password combo was found
            Toast.makeText(LoginActivity.this, "Incorrect Login", Toast.LENGTH_SHORT).show(); //Notify user incorrect login
        } else {
            Toast.makeText(LoginActivity.this, "Logged In. Welcome, " + firstname + ".", Toast.LENGTH_SHORT).show(); //Notify user correct login
            Intent HomeIntent = new Intent(LoginActivity.this,HomeActivity.class); //Intent to start home page activity
            HomeIntent.putExtra("firstname", firstname); //Add an extra to the intent and pass the users firstname. I added this so I can display welcome *firstname* on the home page.
            startActivity(HomeIntent); //Start activity
        }
    }
}
