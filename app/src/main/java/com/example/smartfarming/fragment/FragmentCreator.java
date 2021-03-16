package com.example.smartfarming.fragment;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

public class FragmentCreator {
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();
    private static final int INDEX_MAIN_PAGE = 0;
    private static final int INDEX_TAKE_OUT = 1;
    private static final int INDEX_FIND = 2;
    private static final int INDEX_MINE = 3;
    //有几个fragment
    public static final int INDEX_NUM = 4;


    public static BaseFragment getFragment(int index){
        //先从缓存当中去取
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null){
            return baseFragment;
        }

        switch (index){
            case INDEX_MAIN_PAGE:
                //向上转型,根据需求来决定转型为什么样的,体现了多态
                baseFragment = new MainFragment();
                break;
            case INDEX_TAKE_OUT:
                baseFragment = new MapFragment();
                break;
            case INDEX_FIND:
                baseFragment = new FindFragment();
                break;
            case INDEX_MINE:
                baseFragment = new MineFragment();
        }
        return baseFragment;
    }

}
