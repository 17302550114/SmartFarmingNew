package com.example.smartfarming.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.smartfarming.db.DatabaseHelper;
import com.example.smartfarming.utils.Constant;

import javax.xml.transform.sax.SAXResult;

public class UserDAO {

    private final DatabaseHelper helper;


    public UserDAO(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void insert() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into " + Constant.TABLE_NAME + "(_id, username, password) values(?,?,?)";
        db.execSQL(sql, new Object[]{1, "admin", "123456"});
        db.close();
    }

    public void delete() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from " + Constant.TABLE_NAME + " where _id = 1";
        db.execSQL(sql);
        db.close();
    }

    public void update() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update " + Constant.TABLE_NAME + " set password = 123456";
        db.execSQL(sql);
        db.close();
    }

    public void query() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from " + Constant.TABLE_NAME + "";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(1);
            System.out.println("name == " + string);
        }

        cursor.close();

        db.close();

    }


}
