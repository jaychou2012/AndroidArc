package com.google.androidarc.utils;

import android.support.annotation.NonNull;

import androidx.work.Data;
import androidx.work.Worker;

public class TestWork extends Worker {

    @NonNull
    @Override
    public WorkerResult doWork() {
        System.out.println("执行" + getInputData().getString("string", ""));
        Data data = new Data.Builder().putString("string", "执行" + getInputData().getString("string", "")).build();
        setOutputData(data);
        return WorkerResult.SUCCESS;
    }
}
