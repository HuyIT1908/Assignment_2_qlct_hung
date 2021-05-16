package com.example.assignment_2.Thu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.assignment_2.Adapter.MyAdapterPagerThu;
import com.example.assignment_2.R;
import com.google.android.material.tabs.TabLayout;

public class FragmentThu extends Fragment  {
    private View mRootView;
    private ViewPager viewPager;
    private FragmentLoaiThu fragmentLoaiThu;
    private FragmentKhoanThu fragmentKhoanThu;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_thu, container, false);
        viewPager = (ViewPager) mRootView.findViewById(R.id.vp_demo);
        fragmentLoaiThu = new FragmentLoaiThu();
        fragmentKhoanThu = new FragmentKhoanThu(fragmentLoaiThu);
        viewPager.setAdapter(new MyAdapterPagerThu(getActivity().getSupportFragmentManager(),fragmentKhoanThu,fragmentLoaiThu));
        TabLayout tabLayout = (TabLayout) mRootView.findViewById(R.id.tab_layout_thu);
        tabLayout.setupWithViewPager(viewPager);
        return mRootView;

    }
}
