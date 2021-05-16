package com.example.assignment_2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment_2.Database.LoaiThuContract.*;
import androidx.annotation.Nullable;

public class DataLoaiThu extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "loaithudatabase.db";
    public static final int DATABASE_VERSION = 1;

    public DataLoaiThu(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOAI_THU_TABLE = "CREATE TABLE " +
                loaiThuEntry.TABLE_NAME + " (" +
                loaiThuEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                loaiThuEntry.COLUMN_NAME + " STRING NOT NULL," +
                loaiThuEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_LOAI_THU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
