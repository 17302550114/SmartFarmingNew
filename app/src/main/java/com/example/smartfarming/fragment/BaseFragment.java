package com.example.smartfarming.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartfarming.R;


public abstract class BaseFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = onSubViewLoaded(inflater,container);
        //fragment的状态进行保存,包括横竖屏切换
        setRetainInstance(true);
        return mView;
    }
    protected abstract View onSubViewLoaded(LayoutInflater inflater, ViewGroup container);

}