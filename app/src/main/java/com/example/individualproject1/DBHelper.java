package com.example.individualproject1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="User.db";
    public static final String TBL_NAME="user_table";

    public DBHelper(@Nullable Context context) { //Constructor for DBhelper
        super(context, DB_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase(); //This should create a database on local storage, only if the database does not already exist.
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { //OnCreate makes the user table if it does not already exist. This is called whenever a new DBHelper is created
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TBL_NAME + "(username VARCHAR(32) PRIMARY KEY, firstname VARCHAR(32), lastname VARCHAR(32), email VARCHAR(32), dateofbirth DATE, password VARCHAR(32));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //Drops the table and calls OnCreate
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user_t;");
        onCreate(sqLiteDatabase);
    }

    public boolean insertValues(String username, String firstname, String lastname, String email, String dateofbirth, String password){ //InsertValues inserts a new user into the database.
        SQLiteDatabase db = this.getWritableDatabase(); //Get DB                                                                            //This function does not perform a check to see if user already exists and data is valid, so data must be verified beforehand
        ContentValues cv = new ContentValues(); //Create a contentvalues to hold user data
        cv.put("username", username);
        cv.put("firstname",firstname);
        cv.put("lastname",lastname);
        cv.put("email", email);
        cv.put("dateofbirth",dateofbirth);
        cv.put("password", password);
        long result = db.insert(TBL_NAME, null,cv); //Insert data and get the returned long so we can verify if the data entry was successful
        if (result == -1) {return false;} //Data entry unsuccessful
        else {return true;} //Data entry successful
    }

    public String checkForUser(String username, String password){ //CheckForUser checks for a row in the DB that matches its arguments.
        SQLiteDatabase db = this.getWritableDatabase(); //Get DB
        String[] cols = {"firstname"}; //Select firstname column
        String[] selectionArgs = {username.toUpperCase(), password}; //This indicated what to replace the '?' character with in the selection string. Using username.toUpperCase to do a case insensitive SQL Query
        String selection = "upper(username) =? and password =?"; //? are filled in according to the selectionArgs array. Using upper() to perform case insensitive query. This works by making both username in SQL upper and username to search for upper
        Cursor cs = db.query(TBL_NAME, cols, selection, selectionArgs, null, null, null); //Query the DB for columns username and password
        String name;
        if (cs.getCount() == 0){
            return name = ""; //Return if nothing found
        } //if no rows were found (cs.getCount() is 0), name needs to be set to "" so undeclared string is not returned.
        cs.moveToFirst();
        name = cs.getString(cs.getColumnIndex("firstname")); //Return string username so I can display "Welcome, *firstname*!"
        cs.close();
        return name;
    }

    public boolean checkUsernameTaken(String username){ //Function to check if a username is taken
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT username FROM " + TBL_NAME + " WHERE upper(username) =?"; //SQL Query. Using upper() to perform case insensitive query.
        String[] selectionArgs = {username.toUpperCase()}; //Search for upper(username) where equal to username.toUpperCase()
        Cursor cs = db.rawQuery(select, selectionArgs);
        if (cs.getCount() > 0) { //If a match is found count will be > 0
            cs.close();
            return true;
        } else {
            cs.close();
            return false;
        }
    }
}
