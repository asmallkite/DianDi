package com.kite.diandi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 10648 on 2017/2/27 0027.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_ZHIHU  = "create table if not exists Zhihu(" +
            "id integer primary key autoincrement," +
            "zhihu_id integer not null," +
            "zhihu_news text," +
            "zhihu_time real," +
            "zhihu_content text," +
            "bookmark integer default 0" +
            ")";

    private static final String CREATE_GUOKR = "create table if not exists Guokr(" +
            "id integer primary key autoincrement," +
            "guokr_id integer not null," +
            "guokr_news text," +
            "guokr_time real," +
            "guokr_content text," +
            "bookmark integer default 0" +
            ")";

    private static final String CREATE_DOUBAN = "create table if not exists Douban(" +
             "id integer primary key autoincrement," +
             "douban_id integer not null," +
             "douban_news text," +
             "douban_time real," +
             "douban_content text" +
             "bookmark integer default 0)";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ZHIHU);

        db.execSQL(CREATE_GUOKR);

        db.execSQL(CREATE_DOUBAN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
