package com.example.smartfarming.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartfarming.R;


public class MainContentAdapter extends FragmentPagerAdapter {

    public MainContentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    //获取position位置的item
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FragmentCreator.getFragment(position);
    }

    //获取共有多少个fragment
    @Override
    public int getCount() {
        return FragmentCreator.INDEX_NUM;
    }
}