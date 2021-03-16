package com.example.smartfarming.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.smartfarming.R;
import com.example.smartfarming.fragment.MainContentAdapter;

import org.xutils.view.annotation.ViewInject;

public class NewMainActivity extends AppCompatActivity {

    BottomNavigationBar mBottomNavigationBar;
    ViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_main);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //用来初始化界面,绑定控件
        initView();
        //用于初始化一些点击事件
        initListener();
    }


    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //在滑动过程中的监听事件
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //滑动到新页面会调用
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
            }
            //状态改变时会调用,0表示什么都不做,1表示开始滑动,2代表结束滑动
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //bottomNavigationBar的点击监听
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mViewPager.setCurrentItem(position);
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void initView() {
        mBottomNavigationBar = findViewById(R.id.bottom_main);
        mViewPager = findViewById(R.id.viewPager_main);

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(supportFragmentManager);
        mViewPager.setAdapter(mainContentAdapter);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.black)
                .setInActiveColor(R.color.purple_700)
                .setFirstSelectedPosition(0);

        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home_pres, "首页")
                        .setInactiveIcon(ContextCompat.getDrawable(NewMainActivity.this, R.drawable.home_nor)))
                .addItem(new BottomNavigationItem(R.drawable.map_pres, "地图")
                        .setInactiveIcon(ContextCompat.getDrawable(NewMainActivity.this, R.drawable.map_nor)))
                .addItem(new BottomNavigationItem(R.drawable.service_pres, "发现")
                        .setInactiveIcon(ContextCompat.getDrawable(NewMainActivity.this, R.drawable.service_nor)))
                .addItem(new BottomNavigationItem(R.drawable.mine_pres, "关于")
                        .setInactiveIcon(ContextCompat.getDrawable(NewMainActivity.this, R.drawable.mine_nor)))
                .initialise();
    }
}