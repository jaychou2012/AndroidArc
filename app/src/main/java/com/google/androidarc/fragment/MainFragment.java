package com.google.androidarc.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.androidarc.R;
import com.google.androidarc.database.User;
import com.google.androidarc.ui.NavigationActivity;
import com.google.androidarc.utils.TestLifeCycle;
import com.google.androidarc.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.tv_main)
    TextView tv_main;
    private UserViewModel userViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_main.setOnClickListener(this);
        getLifecycle().addObserver(new TestLifeCycle());
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        Observer<User> observer = new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                tv_main.setText("" + user.getFirstName() + " " + user.getLastName());
            }
        };
        userViewModel.getUser().observe(this, observer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main:
                getFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.container, SecondFragment.newInstance()).commit();
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
