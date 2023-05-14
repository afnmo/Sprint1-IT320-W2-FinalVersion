package com.example.navigationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DRESS_TABLE = "DRESS_TABLE";
    public static final String COLUMN_DRESS_NAME = "DRESS_NAME";
    public static final String COLUMN_IMG = "IMAGE";
    public static final String COLUMN_ID = "ID";

    List<DressModel> dressModelList = new ArrayList<>();

    public DatabaseHelper(@Nullable Context context) {
        super(context, "dress.db", null, 1);
    }

    //    called the first time the DB is accessed. here the code to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + DRESS_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DRESS_NAME + " TEXT, " + COLUMN_IMG + " INT)";
        db.execSQL(createTableStatement);
    }


    //    called if the database version number changes. prevents prev users apps from breaking when you change the DB design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(DressModel cModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DRESS_NAME, cModel.getName());

        // Convert bitmap to byte array for storing in database
        Bitmap imageBitmap = cModel.getImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();

        contentValues.put(COLUMN_IMG, imageByteArray);

        long insert = db.insert(DRESS_TABLE, null, contentValues);

        if(insert == -1){
            return false;
        } else {
            return true;
        }
    }


//    public boolean DeleteOne(DressModel cModel){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString= "Delete From " + DRESS_TABLE + " WHERE " + COLUMN_ID + " = " + cModel.getID() ;
//        Cursor cursor = db.rawQuery(queryString, null);
//        if(cursor.moveToFirst()){
//            return true;
//        } else{
//            // nothing happens. no one is added.
//            return false;
//        }
//        //close
//    }


    public boolean DeleteOne(DressModel cModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(cModel.getID()) };
        int rowsDeleted = db.delete(DRESS_TABLE, whereClause, whereArgs);
        db.close();
        return rowsDeleted > 0;
    }


    public List<DressModel> getAll() {
        List<DressModel> dressList = new ArrayList<>();
        String queryString = "SELECT * FROM " + DRESS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int dressId = cursor.getInt(0);
                String dressName = cursor.getString(1);
                byte[] imageByteArray = cursor.getBlob(2);

                // Convert byte array to bitmap for displaying
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);

                DressModel dress = new DressModel(dressId, dressName, imageBitmap);
                dressList.add(dress);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dressList;
    }

//    --------------------------------

}
