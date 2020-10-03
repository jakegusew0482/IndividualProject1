package com.example.individualproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button loginButton, registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.ButtonLogin);
        registerButton = (Button) findViewById(R.id.ButtonRegister);
        setContentView(R.layout.activity_main);
    }

    public void register(View view){ //When register button is clicked call this method
        Intent register = new Intent(this,RegisterActivity.class); //New register intent
        startActivity(register); //Start register activity
    }

    public void login(View view){ //When login button is clicked call this method
        Intent login = new Intent( this,LoginActivity.class); //New login intent
        startActivity(login); //Start login activity
    }
}