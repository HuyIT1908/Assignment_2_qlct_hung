package com.example.assignment_2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.assignment_2.Database.KhoanThuContract.*;
import androidx.annotation.Nullable;

public class DataKhoanThu extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "khoanthudatabase.db";
    public static final int DATABASE_VERSION = 1;

    public DataKhoanThu(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_KHOAN_THU_TABLE = "CREATE TABLE " +
                khoanThuEntry.TABLE_NAME + " (" +
                khoanThuEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                khoanThuEntry.COLUMN_CATEGORIES + " STRING ," +
                khoanThuEntry.COLUMN_CONTENT + " STRING ," +
                khoanThuEntry.COLUMN_MONEY + " STRING ," +
                khoanThuEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_KHOAN_THU_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
