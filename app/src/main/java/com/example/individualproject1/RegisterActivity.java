package com.example.individualproject1;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText UsernameET, FirstnameET, LastnameET, EmailET, PasswordET, BirthdateET;
    Button ButtonCreateAccount;
    TextView textView;
    DBHelper myDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsernameET = (EditText) findViewById(R.id.EditTextUsername);
        FirstnameET = (EditText) findViewById(R.id.EditTextFirstName);
        LastnameET = (EditText) findViewById(R.id.EditTextLastName); //Declare all the edit texts
        EmailET = (EditText) findViewById(R.id.EditTextEmail);
        PasswordET = (EditText) findViewById(R.id.EditTextPassword);
        BirthdateET = (EditText) findViewById(R.id.EditTextBirthdate);
        textView = (TextView) findViewById((R.id.textView));
        myDB = new DBHelper(this); //New DBHelper  to interact with DB

    }

    public void CreateAccount(View view) { //This method is called when register button clicked
        if(validateRegistrationInput(UsernameET,FirstnameET, LastnameET,EmailET,PasswordET,BirthdateET)) { //Validate the contents of each edit text
            boolean insertedSuccess = myDB.insertValues(UsernameET.getText().toString(), FirstnameET.getText().toString(), LastnameET.getText().toString(), EmailET.getText().toString(), BirthdateET.getText().toString(), PasswordET.getText().toString()); //Call insertValues from DBHelper and assign its output to bool to test is successful
            if (insertedSuccess) { //If the insertValues function returned true it succeeded
                Toast.makeText(RegisterActivity.this, "Account Created Successfully, Please Login", Toast.LENGTH_LONG).show(); //Make toast to notify if successful
                Intent SendToLoginIntent = new Intent(this, LoginActivity.class); //New intent to start login activity after registration
                SendToLoginIntent.putExtra("EXTRA_USER_NAME", UsernameET.getText().toString()); //Add an extra to send username to login activity. This is done so I can populate username field on login page (for convenience)
                startActivity(SendToLoginIntent); //Start activity
            } else {
                Toast.makeText(RegisterActivity.this, "Data insertion failed", Toast.LENGTH_SHORT).show(); //Make toast to notify if failure
            }
        }
    }

    private int isValidEmail(EditText emailET){ //Verifies email using pattern matcher.
        String emailAddress = emailET.getText().toString();
        if(!emailAddress.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){ //If not empty and is an email address
            return 0;
        } else {
            emailET.setError("Invalid email address");
            return 1;
        }
    }

    private int isValidLength(EditText editText) { //Simple method to check if an EditText is the proper length, this will also rule out empty fields
        int inpLength = editText.getText().toString().length();
        if (inpLength < 3 || inpLength > 30){
            editText.setError("Must be between 3 - 30 characters");
            return 1;
        } else return 0;

    }

    private boolean validateRegistrationInput(EditText usernameET, EditText firstnameET, EditText lastnameET, EditText emailET, EditText birthdateET, EditText passwordET) { //Function to validate all the input and return bool
        int errorCount = 0;
        errorCount +=isValidLength(usernameET); //Check all of the editTexts lengths
        errorCount +=isValidLength(firstnameET);
        errorCount +=isValidLength(lastnameET);
        errorCount +=isValidLength(emailET);
        errorCount +=isValidLength(birthdateET);
        errorCount +=isValidLength(passwordET);
        errorCount += isValidEmail(emailET);

        if(myDB.checkUsernameTaken(usernameET.getText().toString())) { //Call checkUsernameTaken from DBHelper class
            usernameET.setError("Username is already taken");
            errorCount++;
        }

        if(errorCount > 0) { //If any errors found return false
            return false;
        } else {
            return true;
        }
    }

}
