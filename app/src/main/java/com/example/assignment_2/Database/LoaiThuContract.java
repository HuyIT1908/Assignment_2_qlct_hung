package com.example.assignment_2.Database;

import android.provider.BaseColumns;

public class LoaiThuContract {

    private LoaiThuContract(){

    }

    public static final class loaiThuEntry implements BaseColumns{
        public static final String TABLE_NAME = "loaiThuList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
