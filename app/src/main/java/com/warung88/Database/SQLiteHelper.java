package com.warung88.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class SQLiteHelper extends SQLiteOpenHelper {


    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) { }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }


    //Query Data
    //----------------------------------------------------------------------
    public  void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    //Insert
    //--------------------------------------------------------------------
    public  void insertData(String name, String quantity,String price){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO CART(NAME, QUANTITY, PRICE) VALUES (?, ?, ?)";
        SQLiteStatement statement =
                database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, name);
        statement.bindString(2, quantity);
        statement.bindString(3, price);
        statement.executeInsert();
    }


    //Update
    //----------------------------------------------------------------------
    public void updateData(String name, String quantity, String price, int id){
        SQLiteDatabase database= getWritableDatabase();
        String sql= "UPDATE CART SET name=?, quantity=?, price=? WHERE id = ?";
        SQLiteStatement statement =
                database.compileStatement(sql);
        statement.bindString(1, name);
        statement.bindString(2, quantity);
        statement.bindString(3, price);
        statement.executeInsert();
        database.close();
    }


    //Delete All Data
    //----------------------------------------------------------------------
    public void deleteAllData(){
        SQLiteDatabase database = getWritableDatabase();
        String sql= "DELETE FROM CART";
        SQLiteStatement statement =
                database.compileStatement(sql);
        statement.clearBindings();
        statement.execute();
        database.close();
    }


    //Delete Data
    //----------------------------------------------------------------------
    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM CART WHERE id=?";
        SQLiteStatement statement =
                database.compileStatement(sql);
        statement.clearBindings();;
        statement.bindDouble(1,(double) id);
        statement.execute();
        database.close();
    }



    //Get Data
    //----------------------------------------------------------------------
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }






}