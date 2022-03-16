package com.example.myfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABSE_NAME = "Products.db";
    private static final int  DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "productsList";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "product_name";
    private static final String COLUMN_CATEGORY = "product_category";
    private static final String COLUMN_AMOUNT = "product_amount";
    private static final String COLUMN_UNIT = "product_unit";
    private static final String COLUMN_DATEOFEXPIRY = "product_date_of_expiry";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABSE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " + COLUMN_CATEGORY + " TEXT, " +
                COLUMN_AMOUNT + " TEXT, " + COLUMN_UNIT + " TEXT, " +
                COLUMN_DATEOFEXPIRY + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addProduct(String name, String category, String amount, String unit, String dateOfExpiry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_UNIT, unit);
        cv.put(COLUMN_DATEOFEXPIRY, dateOfExpiry);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to add a new product!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "New product has been added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String name, String category, String amount, String unit, String dateOfExpiry){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_CATEGORY, category);
        cv.put(COLUMN_AMOUNT, amount);
        cv.put(COLUMN_UNIT, unit);
        cv.put(COLUMN_DATEOFEXPIRY, dateOfExpiry);

        long result = db.update(TABLE_NAME, cv,  "_id=?", new String[] {row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteData(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}
