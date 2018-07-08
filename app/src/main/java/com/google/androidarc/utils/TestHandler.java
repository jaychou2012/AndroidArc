package com.google.androidarc.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class TestHandler {

    public void onClickStudent(View view) {
        showToast(view.getContext(), "点击");
    }

    public void onClickView(View view) {
        showToast(view.getContext(), "点击2");
    }

    public void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
