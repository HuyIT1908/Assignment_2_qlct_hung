package com.example.assignment_2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment_2.Database.LoaiChiContract.*;

import androidx.annotation.Nullable;

public class DataLoaiChi extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "loaichidatabase.db";
    public static final int DATABASE_VERSION = 1;

    public DataLoaiChi(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOAI_CHI_TABLE = "CREATE TABLE " +
                loaiChiEntry.TABLE_NAME + " (" +
                loaiChiEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                loaiChiEntry.COLUMN_NAME + " STRING NOT NULL," +
                loaiChiEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_LOAI_CHI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
