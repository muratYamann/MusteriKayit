package com.example.emrah.musterikayit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static String TAG ="db";
    public static final String DATABASE_NAME = "MyDBNameKasa.db";
    public static final String CONTACTS_TABLE_NAME = "kasa";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "tc";
    public static final String CONTACTS_COLUMN_CITY = "hesap";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUM_DATE ="date";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table kasa " +
                        "(id text primary key, name text,phone text,email text, tc text,hesap text, date text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS kasa");
        onCreate(db);
    }

    public boolean insertContact  (String id,String name, String phone, String email, String tc,String hesap,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id",id);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("tc", tc);
        contentValues.put("hesap", hesap);
        contentValues.put("date",date);

        db.insert("kasa", null, contentValues);
        return true;
    }

    public boolean insertContactDate  (String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date",date);
        db.insert("kasa", null, contentValues);
        return true;
    }


    public boolean updateContact (String id, String name, String phone, String email, String tc,String hesap,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("tc", tc);
        contentValues.put("hesap", hesap);
        contentValues.put("date",date);
        db.update("kasa", contentValues, "id = ? ", new String[] { (id) } );
        return true;
    }
    public boolean updateContact (String id,String hesap,String date){

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues;
        contentValues =new ContentValues();
        contentValues.put("hesap",hesap);
        contentValues.put("date",date);
        db.update("kasa",contentValues,"id = ?",new String[]{(id)});

        Log.d(TAG, "updateKasa: "+contentValues.toString());
        return true;
    }

    public Integer deleteContact (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "deleteContact: "+id );
        return db.delete("kasa", "id = ? ", new String[] { (id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from kasa", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }


    public ArrayList<String> getALLDate()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from kasa", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
              array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUM_DATE)));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from kasa where id = "+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    
    public ArrayList<String> getPersonId()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from kasa", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }


}
