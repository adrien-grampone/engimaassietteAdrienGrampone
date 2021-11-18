package com.example.enigmassietteadriengrampone;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME = "restaurant.db";
    public static  final String TABLE_NAME = "critique_table";
    public static  final String COL_1 = "ID";
    public static  final String COL_2 = "nom";
    public static  final String COL_3 = "datetime";
    public static  final String COL_4 = "note_decoration";
    public static  final String COL_5 = "note_nourriture";
    public static  final String COL_6 = "note_service";
    public static  final String COL_7 = "description";


    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, datetime TEXT, note_decoration TEXT, note_nourriture TEXT, note_service TEXT, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nom, String datetime, String noteDeco, String noteNourriture, String noteService, String description)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,nom);
        contentValues.put(COL_3,datetime);
        contentValues.put(COL_4,noteDeco);
        contentValues.put(COL_5,noteNourriture);
        contentValues.put(COL_6,noteService);
        contentValues.put(COL_7,description);
        long result= db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

    public Cursor getDataById(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME+" where ID = ?", new String[] {id});
        return res;
    }

    public boolean ModifierDonnees(String id, String nom, String datetime, String noteDeco, String noteNourriture, String noteService, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,nom);
        contentValues.put(COL_3,datetime);
        contentValues.put(COL_4,noteDeco);
        contentValues.put(COL_5,noteNourriture);
        contentValues.put(COL_6,noteService);
        contentValues.put(COL_7,description);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;

    }

    public Integer SupprimerDonnee(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[] {id});
    }
}
