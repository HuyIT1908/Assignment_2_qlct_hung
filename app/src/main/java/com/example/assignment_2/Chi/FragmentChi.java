package com.example.assignment_2.Chi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.assignment_2.Adapter.MyAdapterPagerChi;
import com.example.assignment_2.Context.LoaiChi;
import com.example.assignment_2.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FragmentChi extends Fragment {
    private View mRootView;
    private ViewPager viewPager;
    private FragmentLoaiChi fragmentLoaiChi;
    private FragmentKhoanChi fragmentKhoanChi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_chi, container, false);
        viewPager = (ViewPager) mRootView.findViewById(R.id.vp_chi);
        fragmentLoaiChi = new FragmentLoaiChi();
        fragmentKhoanChi = new FragmentKhoanChi(fragmentLoaiChi);
        viewPager.setAdapter(new MyAdapterPagerChi(getActivity().getSupportFragmentManager(),fragmentLoaiChi,fragmentKhoanChi));
        TabLayout tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout_chi);
        tabLayout.setupWithViewPager(viewPager);
        return mRootView;
    }

}
