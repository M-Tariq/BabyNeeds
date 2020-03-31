package com.example.babyneeds.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.babyneeds.model.Item;
import com.example.babyneeds.util.Constants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    private final Context context;

    public DatabaseHandler(@Nullable Context context) {

        super(context, Constants.DB_NAME, null, Constants.DB_VERSION); //create database
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        String CREATE_BABY_TABLE="CREATE TABLE "+Constants.TABLE_NAME+"("
                +Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                +Constants.KEY_NAME + " TEXT, "
                +Constants.KEY_QTY + " INTEGER, "
                +Constants.KEY_SIZE + " INTEGER, "
                +Constants.KEY_COLOR + " TEXT, "
                +Constants.KEY_DATE + " LONG);";
        db.execSQL(CREATE_BABY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.DB_NAME);
        onCreate(db);
    }
    //CRUD methods
    public long addItem(Item item){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.KEY_NAME, item.getItemName());
        contentValues.put(Constants.KEY_COLOR, item.getItemColor());
        contentValues.put(Constants.KEY_QTY, item.getItemQuantity());
        contentValues.put(Constants.KEY_SIZE, item.getItemSize());
        contentValues.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis()); //current time stamp
        return sqLiteDatabase.insert(Constants.TABLE_NAME, null, contentValues);
    }
    public Item getItem(int id){
        Item item=new Item();
        SQLiteDatabase db=this.getReadableDatabase();
        //sqlitedatabase.query();
        Cursor cursor=db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.KEY_NAME, Constants.KEY_SIZE,
                        Constants.KEY_QTY, Constants.KEY_COLOR, Constants.KEY_DATE},
                Constants.KEY_ID+"=?", new String[]{String.valueOf(id)},
                null, null, null, null );
        if(cursor!=null){
            if(cursor.moveToNext()){
                item.setId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
                item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));

                DateFormat dateFormat=DateFormat.getDateInstance();
                String date=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))).getTime());
                item.setDateItemAdded(date);
               // item.setDateItemAdded(dateFormat(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE))));
            }
        }
        if(item!=null){
            return item;
        }else{
            return null;
        }
    }

    public List<Item> getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID,
                        Constants.KEY_NAME,
                        Constants.KEY_COLOR,
                        Constants.KEY_QTY,
                        Constants.KEY_SIZE,
                        Constants.KEY_DATE},
                null, null, null, null,
                Constants.KEY_DATE + " DESC", null);


        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_NAME)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY)));
                item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));
                //convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE)))
                        .getTime()); // Feb 23, 2020
                item.setDateItemAdded(formattedDate);

                //Add to arraylist
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        return itemList;

    }

    public int updateItem(Item item){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Constants.KEY_NAME, item.getItemName());
        contentValues.put(Constants.KEY_COLOR, item.getItemColor());
        contentValues.put(Constants.KEY_SIZE, item.getItemSize());
        contentValues.put(Constants.KEY_QTY, item.getItemQuantity());
        contentValues.put(Constants.KEY_DATE, java.lang.System.currentTimeMillis()); //current time stamp
       // db.insert(Constants.TABLE_NAME, null, contentValues);
       return db.update(Constants.TABLE_NAME, contentValues, Constants.KEY_ID+"=?", new String[]{String.valueOf(item.getId())});
    }
    public int deleteItem(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(Constants.TABLE_NAME, Constants.KEY_ID+"=?", new String[]{String.valueOf(id)});
    }
    public int getCount(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Constants.TABLE_NAME, null );
        return cursor.getCount();
    }
}
