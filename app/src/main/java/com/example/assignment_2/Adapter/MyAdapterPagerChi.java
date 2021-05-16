package com.example.assignment_2.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.assignment_2.Chi.FragmentKhoanChi;
import com.example.assignment_2.Chi.FragmentLoaiChi;

public class MyAdapterPagerChi extends FragmentStatePagerAdapter {
    private String listTab[]={"Loại Chi","Khoản Chi"};
    private FragmentLoaiChi mfragmentLC ;
    private FragmentKhoanChi mfragmentKC;
    public MyAdapterPagerChi(@NonNull FragmentManager fm,FragmentLoaiChi mfragmentLC,FragmentKhoanChi mfragmentKC) {
        super(fm);
        this.mfragmentKC = mfragmentKC;
        this.mfragmentLC = mfragmentLC;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return mfragmentLC;
        }else if (position==1){
            return mfragmentKC;
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
