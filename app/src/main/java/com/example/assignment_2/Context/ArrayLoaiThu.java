package com.example.assignment_2.Context;

import java.util.ArrayList;

public class ArrayLoaiThu {
    private ArrayList<LoaiThu> listLoaiThu;

    public ArrayLoaiThu(ArrayList<LoaiThu> listLoaiThu) {
        this.listLoaiThu = listLoaiThu;
    }

    public ArrayList<LoaiThu> getListLoaiThu() {
        return listLoaiThu;
    }

    public void setListLoaiThu(ArrayList<LoaiThu> listLoaiThu) {
        this.listLoaiThu = listLoaiThu;
    }
}
