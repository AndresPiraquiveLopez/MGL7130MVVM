package com.example.andrespiraquive.recettes.Data.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public  static final String DATABASE_NAME="RecipesTP2.db";
    public  static final String Table_Name="Rece_Table";
    public  static final String col_1="ID";
    public  static final String col_2="Titre";
    static final String col_3="Ingredient";
    public  static final String col_4="Preparation";
    public  static final String col_5="Description";
    public static  final String col_6="Image";
    public static  final String col_7="Note";
    public static  final String col_8="Position";



    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Table_Name + "(ID integer primary key autoincrement, Titre TEXT UNIQUE, Ingredient TEXT, Preparation TEXT, Description TEXT, Image BLOB, Note TEXT, Position TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);

    }

    /**
     *  Insertion des données dans la base de données
     * @param titre
     * @param ingredient
     * @param preparation

     *
     * @return
     */
    public boolean insertData( String titre, String ingredient, String preparation, String description, byte[]image, String note, String position){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues= new ContentValues();
        contentValues.put(col_2,titre);
        contentValues.put(col_3,ingredient);
        contentValues.put(col_4,preparation);
        contentValues.put(col_5,description);
        contentValues.put(col_6,image);
        contentValues.put(col_7,note);
        contentValues.put(col_8,position);
        long result=db.insert( Table_Name, null,contentValues );
        if(result==-1)
            return false;
        else
            return true;



    }

    /**
     * effectue une lecture entière de la base de données
     *
     * @return
     */
    public Cursor getAllData(){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Table_Name, null);
        return res;

    }
    public Cursor geDataById(int id){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + Table_Name + " where ID = " + id, null);
        return res;

    }

    public Integer  deleteData(String Titre)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        return  db.delete(Table_Name, "Titre = ?", new String[] {Titre});

    }
}

