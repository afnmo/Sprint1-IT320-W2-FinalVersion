package com.example.navigationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBName = "regstration_db";

    public DBHelper(@Nullable Context context) {
        super(context, "regstration_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDataBase) {

        MyDataBase.execSQL("create Table allUsers(userName TEXT primary Key, email Text, password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDataBase, int i, int i1) {
        MyDataBase.execSQL("drop Table if exists allUsers");
    }

    public boolean insertData(String userName, String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",userName);
        contentValues.put("email",email);
        contentValues.put("password",password);

        long result = MyDB.insert("allUsers", null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkPassWord(String userName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from allUsers where userName = ?", new String[]{userName});

        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from allUsers where userName= ? And password= ?", new String[] {username,password});
        if(cursor.getCount()>0) return true;
        return false;
    }

}
