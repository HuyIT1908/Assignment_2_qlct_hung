package com.example.assignment_2.Database;

import android.provider.BaseColumns;

public class KhoanChiContract {
    public static final String DATABASE_NAME = "khoanchidatabase.db";
    public static final int DATABASE_VERSION = 1;

    private KhoanChiContract(){

    }

    public static final class khoanChiEntry implements BaseColumns{
        public static final String TABLE_NAME = "khoanChiList";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_CATEGORIES = "category";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
