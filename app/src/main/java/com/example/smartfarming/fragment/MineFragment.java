package com.example.smartfarming.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartfarming.R;


public class MineFragment extends BaseFragment {

    @Override
    protected View onSubViewLoaded(LayoutInflater inflater, ViewGroup container) {
        //false就是不把当前布局添加到parent,指定父布局的参数是为了指定一个容器的大小,使属性有效,给一个参考
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_mine,container,false);
        return view;

    }
}