package com.google.androidarc.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.androidarc.R;
import com.google.androidarc.base.BaseActivity;
import com.google.androidarc.database.AppDatabase;
import com.google.androidarc.database.User;
import com.google.androidarc.entity.Student;
import com.google.androidarc.fragment.MainFragment;
import com.google.androidarc.utils.ArcListener;
import com.google.androidarc.utils.ArcObserver;
import com.google.androidarc.utils.ArcUtils;
import com.google.androidarc.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements ArcListener<User> {
    @BindView(R.id.tv_info)
    TextView tv_info;
    private MainFragment mainFragment;
    private List<Student> list = new ArrayList<>();
    private List<Student> lists = new ArrayList<>();
    private List<Student> arrayList = new ArrayList<>();
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        iniView();
    }

    private void iniView() {
        mainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow();
        User user = new User();
        user.setUid((int) System.currentTimeMillis());
        user.setFirstName("Stu");
        user.setLastName("dent");
        ArcUtils.insertAll(this, user, new ArcObserver<User>(this));
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                ArcUtils.arcToast(MainActivity.this, "接收刷新" + user.getFirstName() + " " + user.getLastName());
                tv_info.setText("" + user.getFirstName() + " " + user.getLastName());
            }
        };
        userViewModel.getUser().observe(this, observer);
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppDatabase.getInstance(this).close();
    }

    @Override
    public void complete(User user) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
