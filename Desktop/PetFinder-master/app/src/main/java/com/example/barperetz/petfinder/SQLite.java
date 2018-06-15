package com.example.barperetz.petfinder;

/**
 * Created by Bar on 6/14/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by obaro on 02/04/2015.
 */
public class SQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLitePetFinder.db";
    private static final int DATABASE_VERSION = 2;

    public static final String PET_TABLE_NAME = "petfound";
    public static final String PET_COLUMN_ID = "_id";
    public static final String PET_COLUMN_ADDRESS = "address";
    public static final String PET_COLUMN_TYPE = "type";
    public static final String PET_COLUMN_FEATURES = "features";
    public static final String PET_COLUMN_DESCRIPTION = "description";

    public SQLite(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + PET_TABLE_NAME +
                        "(" + PET_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        PET_COLUMN_ADDRESS + " TEXT, " +
                        PET_COLUMN_TYPE + " TEXT, " +
                        PET_COLUMN_FEATURES + " TEXT, " +
                        PET_COLUMN_DESCRIPTION + "TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PET_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertPet(String address, String type, String features, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PET_COLUMN_ADDRESS, address);
        contentValues.put(PET_COLUMN_TYPE, type);
        contentValues.put(PET_COLUMN_FEATURES, features);
        contentValues.put(PET_COLUMN_DESCRIPTION, description);

        db.insert(PET_TABLE_NAME, null, contentValues);
        return true;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PET_TABLE_NAME);
        return numRows;
    }

    public boolean updatePet(Integer id, String address, String type, String features, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PET_COLUMN_ADDRESS, address);
        contentValues.put(PET_COLUMN_TYPE, type);
        contentValues.put(PET_COLUMN_FEATURES, features);
        contentValues.put(PET_COLUMN_DESCRIPTION, description);
        db.update(PET_TABLE_NAME, contentValues, PET_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deletePet(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PET_TABLE_NAME,
                PET_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    public Cursor getPet(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery(new StringBuilder().append("SELECT * FROM ").append(PET_TABLE_NAME).append(" WHERE ").append(PET_COLUMN_ID).append("=?").toString(), new String[]{Integer.toString(id)});
        return res;
    }

    public Cursor getAllPets() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + PET_TABLE_NAME, null );
        return res;
    }
}