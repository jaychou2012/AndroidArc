package com.google.androidarc.fragment;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
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
import com.google.androidarc.databinding.FragmentSecondBinding;
import com.google.androidarc.utils.TestLifeCycle;
import com.google.androidarc.viewmodel.UserViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.tv_text)
    TextView tv_text;
    private UserViewModel userViewModel;
    private FragmentSecondBinding binding;

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        ButterKnife.bind(this, binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLifecycle().addObserver(new TestLifeCycle());
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        tv_text.setOnClickListener(this);
        User user = new User();
        user.setFirstName("Stu");
        user.setLastName("dent");
        user.setUid((int) System.currentTimeMillis());
        userViewModel.getUser().setValue(user);
        map();
    }

    private void map() {
        LiveData<User> userList = userViewModel.getUser();
        LiveData<String> userName = Transformations.map(userList, new Function<User, String>() {

            @Override
            public String apply(User input) {
                return input.getFirstName();
            }
        });
        System.out.println("打印" + userName.getValue() + "  " + userList.getValue().getFirstName());
//        LiveData<String> usersId = userViewModel.getUser();
//        Transformations.switchMap(userViewModel.getUser(),
//                new Function<Student, LiveData<User>>() {
//                    @Override
//                    public LiveData<User> apply(Student input) {
//                        LiveData<User> userMutableLiveData = new MutableLiveData<>();
//                        User user = new User();
//                        user.setUid((int) System.currentTimeMillis());
//                        user.setFirstName(input.getName());
//                        userMutableLiveData.setValue(user);
//                        return userMutableLiveData;
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_text:
                User user = new User();
                user.setFirstName("Stu");
                user.setLastName("dent");
                user.setUid((int) System.currentTimeMillis());
                userViewModel.getUser().setValue(user);
                break;
        }
    }
}
