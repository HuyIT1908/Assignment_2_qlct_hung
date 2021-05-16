package com.example.assignment_2.Database;

import android.provider.BaseColumns;

public class LoaiChiContract {

    private LoaiChiContract(){

    }

    public static final class loaiChiEntry implements BaseColumns{
        public static final String TABLE_NAME = "loaiChiList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
