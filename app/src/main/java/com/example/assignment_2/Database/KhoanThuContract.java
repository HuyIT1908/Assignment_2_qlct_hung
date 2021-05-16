package com.example.assignment_2.Database;

import android.provider.BaseColumns;

public class KhoanThuContract {

    private KhoanThuContract(){

    }

    public static final class khoanThuEntry implements BaseColumns{
        public static final String TABLE_NAME = "khoanThuList";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_MONEY = "money";
        public static final String COLUMN_CATEGORIES = "category";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
