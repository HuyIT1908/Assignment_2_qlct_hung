package com.example.assignment_2.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.assignment_2.Thu.FragmentKhoanThu;
import com.example.assignment_2.Thu.FragmentLoaiThu;

public class MyAdapterPagerThu extends FragmentStatePagerAdapter {
    private String listTab[]={"Loại Thu","Khoản Thu"};
    private FragmentLoaiThu mfragmentLT ;
    private FragmentKhoanThu mfragmentKT;
    public MyAdapterPagerThu(@NonNull FragmentManager fm, FragmentKhoanThu fragmentKT,FragmentLoaiThu fragmentLT) {
        super(fm);
        this.mfragmentKT = fragmentKT;
        this.mfragmentLT = fragmentLT;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return mfragmentLT;
        }else if (position==1){
            return  mfragmentKT;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
