package com.example.assignment_2.Context;

import java.util.ArrayList;

public class ArrayLoaiChi {
    private ArrayList<LoaiChi> listLoaiChi;

    public ArrayLoaiChi(ArrayList<LoaiChi> listLoaiChi) {
        this.listLoaiChi = listLoaiChi;
    }

    public ArrayLoaiChi() {
    }

    public void add(LoaiChi lc){
        listLoaiChi.add(lc);
    }

    public void remove(int position){
        listLoaiChi.remove(position);
    }

    public void update(int position,LoaiChi lc){
        listLoaiChi.set(position,lc);
    }

    public ArrayList<LoaiChi> getListLoaiChi() {
        return listLoaiChi;
    }

    public void setListLoaiChi(ArrayList<LoaiChi> listLoaiChi) {
        this.listLoaiChi = listLoaiChi;
    }
}
