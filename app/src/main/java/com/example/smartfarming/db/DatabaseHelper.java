package com.example.smartfarming.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.smartfarming.utils.Constant;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("我在创建表...");
        String sql = "create table "+Constant.TABLE_NAME+"(_id integer, username varchar, password varchar)";
        db.execSQL(sql);
        System.out.println("我创建好了 ...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
