package com.google.androidarc.ui;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.androidarc.R;
import com.google.androidarc.base.BaseActivity;
import com.google.androidarc.database.User;
import com.google.androidarc.databinding.ActivityTestBinding;
import com.google.androidarc.utils.ArcListener;
import com.google.androidarc.utils.ArcObserver;
import com.google.androidarc.utils.ArcUtils;
import com.google.androidarc.utils.TestHandler;
import com.google.androidarc.utils.TestLifeCycle;
import com.google.androidarc.utils.TestWork;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class TestActivity extends BaseActivity implements ArcListener<List<User>> {
    private ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.setHandler(new TestHandler());
        getLifecycle().addObserver(new TestLifeCycle());
        ArcUtils.getAll(this, new ArcObserver<List<User>>(this));
        worker();
    }

    private void worker() {
        //数据传输
        Data data = new Data.Builder().putString("string", "text").build();

        //设置任务约束
        Constraints constraints = new Constraints.Builder()
//                .setRequiresDeviceIdle(true)
//                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        //运行一次任务
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(TestWork.class).setInitialDelay(5, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .setInputData(data).addTag("tag").build();
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);

        //周期定期执行任务
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(TestWork.class, 5000, TimeUnit.MILLISECONDS).build();
        WorkManager.getInstance().enqueue(periodicWorkRequest);

        //链式执行任务
        WorkContinuation chain1 = WorkManager.getInstance()
                .beginWith(oneTimeWorkRequest)
                .then(oneTimeWorkRequest);
        WorkContinuation chain2 = WorkManager.getInstance()
                .beginWith(oneTimeWorkRequest)
                .then(oneTimeWorkRequest);

        WorkContinuation chain3 = WorkContinuation
                .combine(chain1, chain2)
                .then(oneTimeWorkRequest);
        chain3.enqueue();

        //输出任务结果
        WorkManager.getInstance().getStatusesByTag("tag").observe(this, new Observer<List<WorkStatus>>() {
            @Override
            public void onChanged(@Nullable List<WorkStatus> workStatuses) {
                if (workStatuses != null && workStatuses.get(0).getState().isFinished()) {
                    String result = workStatuses.get(0).getOutputData().getString("string", "");
                    System.out.println("输出：" + result);
                }
            }
        });
    }

    @Override
    public void complete(List<User> users) {
        binding.setUser(users.get(0));
    }

    @Override
    public void onError(Throwable e) {

    }
}
