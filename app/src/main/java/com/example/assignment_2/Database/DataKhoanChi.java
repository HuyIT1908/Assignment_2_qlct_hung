package com.example.assignment_2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.assignment_2.Context.KhoanChi;
import com.example.assignment_2.Database.KhoanChiContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataKhoanChi extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "khoanchidatabase.db";
    public static final int DATABASE_VERSION = 1;

    public DataKhoanChi(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_KHOAN_CHI_TABLE = "CREATE TABLE " +
                khoanChiEntry.TABLE_NAME + " (" +
                khoanChiEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                khoanChiEntry.COLUMN_CATEGORIES + " STRING ," +
                khoanChiEntry.COLUMN_CONTENT + " STRING ," +
                khoanChiEntry.COLUMN_MONEY + " STRING ," +
                khoanChiEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";
        db.execSQL(SQL_CREATE_KHOAN_CHI_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void add(KhoanChi khoanChi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(khoanChiEntry.COLUMN_CATEGORIES,khoanChi.getLoaiChi());
        values.put(khoanChiEntry.COLUMN_CONTENT,khoanChi.getNoiDungChi());
        values.put(khoanChiEntry.COLUMN_MONEY,khoanChi.getSoTienChi());
        db.close();
    }
    public ArrayList<KhoanChi> getAll(){
        ArrayList<KhoanChi> listKC = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(
                KhoanChiContract.khoanChiEntry.TABLE_NAME ,
                null,
                null,
                null,
                null,
                null,
                KhoanChiContract.khoanChiEntry.COLUMN_TIMESTAMP + " DESC "
        );
        if (cursor.moveToFirst()){
            do {
                KhoanChi kc = new KhoanChi();
                kc.setLoaiChi(cursor.getString(cursor.getColumnIndex(khoanChiEntry.COLUMN_CATEGORIES)));
                kc.setNoiDungChi(cursor.getString(cursor.getColumnIndex(khoanChiEntry.COLUMN_CONTENT)));
                kc.setSoTienChi(cursor.getString(cursor.getColumnIndex(khoanChiEntry.COLUMN_MONEY)));
                listKC.add(kc);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listKC;
    }
    public int update (KhoanChi khoanChi, int Id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(khoanChiEntry.COLUMN_CATEGORIES,khoanChi.getLoaiChi());
        values.put(khoanChiEntry.COLUMN_CONTENT,khoanChi.getNoiDungChi());
        values.put(khoanChiEntry.COLUMN_MONEY,khoanChi.getSoTienChi());
        int resoult = db.update(khoanChiEntry.TABLE_NAME,values, khoanChiEntry._ID+" =?",new String[]{String.valueOf(Id)});
        db.close();
        return resoult;
    }

    public int delete (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(khoanChiEntry.TABLE_NAME,khoanChiEntry._ID+" =?",new String[]{String.valueOf(id)});
    }
}
