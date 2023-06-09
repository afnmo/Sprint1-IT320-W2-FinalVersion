package com.example.navigationdrawer;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private String renterName;
//    database name
    public static final String DBNAME = "bridella.db";

//    table 1
    public static final String ALL_USERS_TABLE = "allUsers";

//    table 2
    public static final String DRESS_TABLE = "ITEM";

//    table 3
    public static final String RENT_TABLE = "RENT";


//    table 1 columns
    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

//    table 2 columns
    public static final String COLUMN_DRESS_NAME = "dressName";
    public static final String COLUMN_IMG = "image";
    public static final String COLUMN_ID = "itemID";
    public static final String COLUMN_USERNAME_FK = "userName";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_PHONE = "phoneNo";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_IS_RENTED = "isRented";

//    table 3 columns

    public static final String COLUMN_RENT_ID = "rentID";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_OCCASION_DATE = "occasionDate";
    public static final String COLUMN_PICKUP_DATE = "pickupDate";
    public static final String COLUMN_RENT_NAME = "renterName";
//    public static final String COLUMN_PHONE = "phoneNo";
//    public static final String COLUMN_USERNAME_FK = "userName";
    public static final String COLUMN_ITEM_ID_FK = "itemID";


//    create table 1 statement:
    private static final String CREATE_TABLE_USER_STATEMENT = "CREATE TABLE " + ALL_USERS_TABLE + "("
        + COLUMN_USERNAME + " TEXT primary Key, "
        + COLUMN_EMAIL + " Text, "
        + COLUMN_PASSWORD + " Text)";


//    create table 2 statement:
    private static final String CREATE_TABLE_DRESS_STATEMENT = "CREATE TABLE " + DRESS_TABLE + "("
        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_DRESS_NAME + " TEXT, "
        + COLUMN_IMG + " INT, "
        + COLUMN_DESCRIPTION + " TEXT, "
        + COLUMN_PRICE + " INT, "
        + COLUMN_SIZE + " TEXT, "
        + COLUMN_PHONE + " TEXT, "
        + COLUMN_CITY + " TEXT, "
        + COLUMN_IS_RENTED + " TEXT, "
        + COLUMN_USERNAME_FK + " TEXT NOT NULL, "
        + "FOREIGN KEY (" + COLUMN_USERNAME_FK + ") REFERENCES " + ALL_USERS_TABLE + "(" + COLUMN_USERNAME + ")"
        + ")";


//    create table 3 statement:
    private static final String CREATE_TABLE_RENT_STATEMENT = "CREATE TABLE " + RENT_TABLE + "("
        + COLUMN_RENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_DAYS + " INT, "
        + COLUMN_OCCASION_DATE + " DATE, "
        + COLUMN_PICKUP_DATE + " DATE, "
        + COLUMN_RENT_NAME + " TEXT, "
        + COLUMN_PHONE + " TEXT, "
        + COLUMN_ITEM_ID_FK + " TEXT, "
        + COLUMN_USERNAME_FK + " TEXT NOT NULL, "
        + "FOREIGN KEY (" + COLUMN_USERNAME_FK + ") REFERENCES " + ALL_USERS_TABLE + "(" + COLUMN_USERNAME + "), "
        + "FOREIGN KEY (" + COLUMN_ITEM_ID_FK + ") REFERENCES " + DRESS_TABLE + "(" + COLUMN_ID + ")"
        + ")";


    private SharedPreferences sharedPreferences;

    public DatabaseHelper(@Nullable Context context) {

        super(context, DBNAME, null, 1);
        sharedPreferences = context.getSharedPreferences("my_app_preferences", Context.MODE_PRIVATE);
    }

    //    called the first time the DB is accessed. here the code to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String createTableStatement = "CREATE TABLE " + DRESS_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DRESS_NAME + " TEXT, " + COLUMN_IMG + " INT)";
//        db.execSQL(createTableStatement);
        // Create table 1
        db.execSQL(CREATE_TABLE_USER_STATEMENT);

        // Create table 2
        db.execSQL(CREATE_TABLE_DRESS_STATEMENT);

        db.execSQL(CREATE_TABLE_RENT_STATEMENT);
    }


    //    called if the database version number changes. prevents prev users apps from breaking when you change the DB design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists " + ALL_USERS_TABLE);
    }

    public boolean addOne(DressModel cModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DRESS_NAME, cModel.getName());
        contentValues.put(COLUMN_DESCRIPTION, cModel.getDescription());
        contentValues.put(COLUMN_PRICE, cModel.getPrice());
        contentValues.put(COLUMN_SIZE, cModel.getSize());
        contentValues.put(COLUMN_PHONE, cModel.getPhoneNo());
        contentValues.put(COLUMN_CITY, cModel.getCity());
        contentValues.put(COLUMN_IS_RENTED, cModel.isRented());


        // Convert bitmap to byte array for storing in database
        Bitmap imageBitmap = cModel.getImage();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();

        contentValues.put(COLUMN_IMG, imageByteArray);
        // Add the foreign key value
        contentValues.put(COLUMN_USERNAME_FK, getUsername());


        long insert = db.insert(DRESS_TABLE, null, contentValues);

        if(insert == -1){
            return false;
        } else {
            return true;
        }
    }

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
        String queryString = "SELECT * FROM " + DRESS_TABLE + " WHERE " + COLUMN_IS_RENTED + " = " + 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        System.out.println("cursor.moveToFirst() in getALL() = " + cursor.moveToFirst());
        if(cursor.moveToFirst()){
            System.out.println("ENTERED cursor.moveToFirst()");
            do {
                int dressId = cursor.getInt(0);
                String dressName = cursor.getString(1);
                byte[] imageByteArray = cursor.getBlob(2);
                // Convert byte array to bitmap for displaying
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);

                String description = cursor.getString(3);
                int price = cursor.getInt(4);
                String size = cursor.getString(5);
                String phone = cursor.getString(6);
                String city = cursor.getString(7);
                boolean isRented = Boolean.parseBoolean(cursor.getString(8));

                DressModel dress = new DressModel(dressId, dressName, imageBitmap, description, price, size, phone, city, isRented);
                dressList.add(dress);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dressList;
    }

    public List<DressModel> getUserOwnItems() {

        List<DressModel> dressList = new ArrayList<>();
        String queryString = "SELECT * FROM " + DRESS_TABLE + " WHERE " + COLUMN_IS_RENTED + " = " + 0 + " AND " + COLUMN_USERNAME + " = '" + getUsername() + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        System.out.println("cursor.moveToFirst() in getALL() = " + cursor.moveToFirst());
        if(cursor.moveToFirst()){
            System.out.println("ENTERED cursor.moveToFirst()");
            do {
                int dressId = cursor.getInt(0);
                String dressName = cursor.getString(1);
                byte[] imageByteArray = cursor.getBlob(2);
                // Convert byte array to bitmap for displaying
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);

                String description = cursor.getString(3);
                int price = cursor.getInt(4);
                String size = cursor.getString(5);
                String phone = cursor.getString(6);
                String city = cursor.getString(7);
                boolean isRented = Boolean.parseBoolean(cursor.getString(8));

                DressModel dress = new DressModel(dressId, dressName, imageBitmap, description, price, size, phone, city, isRented);
                dressList.add(dress);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return dressList;
    }

//    user

    public boolean insertData(String userName, String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME,userName);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_PASSWORD,password);

        long result = MyDB.insert(ALL_USERS_TABLE, null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkPassWord(String userName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + ALL_USERS_TABLE + " where " + COLUMN_USERNAME + " = ?", new String[]{userName});

        if(cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + ALL_USERS_TABLE + " where " + COLUMN_USERNAME + " = ? And " + COLUMN_PASSWORD + " = ?", new String[] {username,password});
        if(cursor.getCount()>0) return true;
        return false;
    }


    public void saveUsername(String username) {
        renterName = username;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("current_username", username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString("current_username", "");
    }




    public boolean addRentedItem(RentedItems rentedItems, int rentedItemID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DAYS, rentedItems.getDays());
        contentValues.put(COLUMN_OCCASION_DATE, rentedItems.getOccDate());
        contentValues.put(COLUMN_PICKUP_DATE, rentedItems.getPickDate());
        contentValues.put(COLUMN_RENT_NAME, rentedItems.getName());
        contentValues.put(COLUMN_PHONE, rentedItems.getPhone());
        contentValues.put(COLUMN_ITEM_ID_FK, rentedItemID);
        contentValues.put(COLUMN_USERNAME_FK, getUsername());

        long insert = db.insert(RENT_TABLE, null, contentValues);

        if(insert == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean updateRentedColumnToTrue(int rentedItemID) {
        String queryString = "UPDATE " + DRESS_TABLE + " SET " + COLUMN_IS_RENTED + " = " + 1 + " WHERE " + COLUMN_ID + " = " + rentedItemID;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);
        db.close();
        return true;
    }


    public List<DressModel> getAllRentedItems() {
        List<DressModel> rentedList = new ArrayList<>();
        String queryString = "SELECT " + DRESS_TABLE + ".* FROM " + DRESS_TABLE + " JOIN " + RENT_TABLE
                + " ON " + DRESS_TABLE + "." + COLUMN_ID + " = " + RENT_TABLE + "." + COLUMN_ITEM_ID_FK
                + " WHERE " + RENT_TABLE + "." + COLUMN_USERNAME_FK + " = '" + getUsername() + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int dressId = cursor.getInt(0);
                String dressName = cursor.getString(1);
                byte[] imageByteArray = cursor.getBlob(2);
                // Convert byte array to bitmap for displaying
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);

                String description = cursor.getString(3);
                int price = cursor.getInt(4);
                String size = cursor.getString(5);
                String phone = cursor.getString(6);
                String city = cursor.getString(7);
                boolean isRented = Boolean.parseBoolean(cursor.getString(8));

                DressModel dress = new DressModel(dressId, dressName, imageBitmap, description, price, size, phone, city, isRented);
                rentedList.add(dress);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return rentedList;
    }


//    public List<DressModel> getAllTest() {
//        List<DressModel> rentedList = new ArrayList<>();
//        System.out.println("getUsername(): " + getUsername());
//        String queryString = "SELECT " + DRESS_TABLE + ".* FROM " + DRESS_TABLE + " JOIN " + RENT_TABLE
//                + " ON " + DRESS_TABLE + "." + COLUMN_ID + " = " + RENT_TABLE + "." + COLUMN_ITEM_ID_FK
//                + " WHERE " + RENT_TABLE + "." + COLUMN_USERNAME_FK + " != '" + getUsername() + "'";
//
//        System.out.println("queryString: " + queryString);
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(queryString, null);
//        System.out.println("cursor.moveToFirst(): " + cursor.moveToFirst());
//
//        if(cursor.moveToFirst()){
//            System.out.println("Hello, cursor.moveToFirst() entered");
//            do {
//                int dressId = cursor.getInt(0);
//                String dressName = cursor.getString(1);
//                byte[] imageByteArray = cursor.getBlob(2);
//                // Convert byte array to bitmap for displaying
//                Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
//
//                String description = cursor.getString(3);
//                int price = cursor.getInt(4);
//                String size = cursor.getString(5);
//                String phone = cursor.getString(6);
//                String city = cursor.getString(7);
//
//                DressModel dress = new DressModel(dressId, dressName, imageBitmap, description, price, size, phone, city);
//                rentedList.add(dress);
//            } while(cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//
//        return rentedList;
//    }

    public int getItemID() {
        String queryString = "SELECT " + COLUMN_ITEM_ID_FK + " FROM " + RENT_TABLE + " WHERE " + COLUMN_USERNAME_FK + " = " + getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int id = -1;
        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(0);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return id;
    }

    public boolean returnItem(DressModel cModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + DRESS_TABLE + " SET " + COLUMN_IS_RENTED + " = " + 0 + " WHERE " + COLUMN_ID + " = " + cModel.getID();
        db.execSQL(queryString);
        String whereClause = COLUMN_ITEM_ID_FK + "=? AND " + COLUMN_USERNAME_FK + "=?";
        String[] whereArgs = new String[] { String.valueOf(cModel.getID()), getUsername() };
        int rowsDeleted = db.delete(RENT_TABLE, whereClause, whereArgs);
        db.close();
        return rowsDeleted > 0;
    }

//    public boolean returnItem(DressModel  cModel) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String queryString = "Delete From " + RENT_TABLE + " WHERE " + COLUMN_ITEM_ID_FK + " = " + cModel.getID();
//        Cursor cursor = db.rawQuery(queryString, null);
//        if (cursor.moveToFirst()) {
//            return true;
//        } else {
//            // nothing happens. no one is added.
//            return false;
//        }
//        //close
//    }

//        call getAllRentedItems()
//        search for itemID with userName equals to getUsername()
//        exist?

//    search for itemID with userName equals to getUsername() in getAllRentedItems() then do not retreieve this itemID from the getAll()
}
