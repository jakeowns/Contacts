package com.example.jake.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jake on 11/21/2016.
 */

public class ContactDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "JakesContacts.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactContract.ContactEntry.TABLE_NAME + " (" +
                    ContactContract.ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactContract.ContactEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactContract.ContactEntry.COLUMN_PHONE + TEXT_TYPE + COMMA_SEP +
                    ContactContract.ContactEntry.COLUMN_EMAIL + TEXT_TYPE + COMMA_SEP +
                    ContactContract.ContactEntry.COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP +
                    ContactContract.ContactEntry.COLUMN_NOTES + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactContract.ContactEntry.TABLE_NAME;
    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + ContactContract.ContactEntry.TABLE_NAME, null);
        return c;
    }
    public Cursor getContact(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + ContactContract.ContactEntry.TABLE_NAME +
            " where " + ContactContract.ContactEntry._ID + "=" + id, null);
        return c;
    }
    public void updateContact(String id, ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(ContactContract.ContactEntry.TABLE_NAME, values,
                ContactContract.ContactEntry._ID + "=?",
                new String[]{ id });
    }
    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ContactContract.ContactEntry.TABLE_NAME,
                ContactContract.ContactEntry._ID + "=?",
                new String[]{ id });
    }
    public void insertContact(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values);
    }
}
