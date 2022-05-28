package com.wang.delivery.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.wang.delivery.Adapter.Tabadapter;
import com.wang.delivery.Fragment.FragmentChoGiao;
import com.wang.delivery.Fragment.FragmentChoNhan;
import com.wang.delivery.Fragment.FragmentDaGiao;
import com.wang.delivery.Fragment.FragmentGiaoThanhCong;
import com.wang.delivery.R;

public class QuanLyDonHangActivity extends AppCompatActivity {

    private Tabadapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView back;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.back);

        adapter = new Tabadapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentChoGiao(), "Đơn Chờ Cửa Hàng");
        adapter.addFragment(new FragmentDaGiao(), "Đơn Đang Giao");
        adapter.addFragment(new FragmentGiaoThanhCong(), "Đơn Giao Thành Công");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TrangCaNhanActivity.class));
                finish();
            }
        });
    }
}