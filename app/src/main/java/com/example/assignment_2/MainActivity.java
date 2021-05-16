package com.example.assignment_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.assignment_2.Chi.FragmentChi;
import com.example.assignment_2.ThongKe.FragmentThongKe;
import com.example.assignment_2.Thu.FragmentThu;
import com.google.android.material.navigation.NavigationView;;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener
                    , new FragmentThongKe()).commit();
            navigationView.setCheckedItem(R.id.nav_thong_ke);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_thu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new FragmentThu()).commit();
                break;
            case R.id.nav_chi:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new FragmentChi()).commit();
                break;
            case R.id.nav_thong_ke:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new FragmentThongKe()).commit();
                break;
            case R.id.nav_gioi_thieu:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_contener, new FragmentAbout()).commit();
                break;
            case R.id.nav_thoat:
                finish();
                System.exit(0);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}