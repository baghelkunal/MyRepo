package com.example.login.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.login.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal on 19-Mar-17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NAME = "UserManage.db";

    // user table name
    private static final String TABLE_USER = "user";

    // user tbl col names
    private static final String COLOUMN_USER_ID = "user_id";
    private static final String COLOUMN_USER_NAME = "user_name";
    private static final String COLOUMN_USER_EMAIL = "user_email";
    private static final String COLOUMN_USER_PASSWORD = "user_password";

    // create tbl sql query
    private String CREATE_USER_TBL = "CREATE TABLE " + TABLE_USER + "(" + COLOUMN_USER_ID + " INTEGER " + "PRIMARY KEY AUTOINCREMENT,"
            + COLOUMN_USER_NAME + " TEXT," + COLOUMN_USER_EMAIL + " TEXT," + COLOUMN_USER_PASSWORD + " TEXT" + ");";

    // drop tbl if needed
    private String DROP_USER_TBL = "drop table if exists " + TABLE_USER;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TBL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TBL);

        // create tbl again
        onCreate(db);

    }


    /**
     * * This method is to create user record
    */
    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLOUMN_USER_NAME, user.getName());
        values.put(COLOUMN_USER_EMAIL, user.getEmail());
        values.put(COLOUMN_USER_PASSWORD, user.getPassword());

        // inserting row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     */
    public List<User> getAllUser(){
        // array of columns to fetch
        String[] columns = {
                COLOUMN_USER_ID,
                COLOUMN_USER_NAME,
                COLOUMN_USER_EMAIL,
                COLOUMN_USER_PASSWORD
        };

        // sorting orders
        String sortOrder =
                COLOUMN_USER_NAME + " ASC";

        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();


        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, columns, null, null, null, null, sortOrder);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLOUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLOUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLOUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLOUMN_USER_PASSWORD)));

                // add user to list
                userList.add(user);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;


    }


    /**
    * This method to update user record
    */
    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLOUMN_USER_NAME, user.getName());
        values.put(COLOUMN_USER_EMAIL, user.getEmail());
        values.put(COLOUMN_USER_PASSWORD, user.getPassword());

        db.update(TABLE_USER, values, COLOUMN_USER_ID + " = ?", new String[]{
           String.valueOf(user.getId())
        });

        db.close();

    }


    /**
     * This method is to delete user record
     * */
    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        // delete user record by id
        db.delete(TABLE_USER, COLOUMN_USER_ID + " = ?", new String[]{
                String.valueOf(user.getId())
        });

        db.close();
    }


    /**
    * This method to check user exist or not
     * return true/false
    * */
    public boolean checkUserExists(String email){

        // array of col to fetch
        String[] columns = {
            COLOUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection  = COLOUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user tbl with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'kunalbaghel@gmail.com';
         */
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount>0){
            return true;
        }

        return false;
    }

    public boolean checkUserExists(String email, String password){
        // array of col to fetch
        String[] columns = {
                COLOUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection  = COLOUMN_USER_EMAIL + " = ?" + " and " + COLOUMN_USER_PASSWORD + " = ?" ;

        // selection argument
        String[] selectionArgs = {email, password};

        // query user tbl with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'kunalbaghel@gmail.com' and user_password = '123';
         */
        Cursor cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount>0){
            return true;
        }

        return false;

    }



}
