package com.google.androidarc.ui;

import android.os.Bundle;

import com.google.androidarc.R;
import com.google.androidarc.base.BaseActivity;

import butterknife.ButterKnife;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }
}
