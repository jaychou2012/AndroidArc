package com.google.androidarc.ui;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.arch.paging.PositionalDataSource;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.google.androidarc.R;
import com.google.androidarc.adapter.PagedAdapter;
import com.google.androidarc.base.BaseActivity;
import com.google.androidarc.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    private LinearLayoutManager linearLayoutManager;
    private PagedList<Student> list;
    private List<Student> lists;
    private PagedListAdapter adapter;
    private int pageSize = 20;
    private PositionalDataSource dataSource;
    private DiffUtil.ItemCallback<Student> itemCallback;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        lists = new ArrayList<>();
        dataSource = new PositionalDataSource() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
                System.out.println("打印:loadInitial");
                for (int i = 0; i < 10; i++) {
                    Student student = new Student();
                    student.setName("名字" + i);
                    lists.add(student);
                }
                callback.onResult(lists, 0);
            }

            @Override
            public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback callback) {
                System.out.println("打印:loadRange");
                for (int i = 0; i < 10; i++) {
                    Student student = new Student();
                    student.setName("名字" + i);
                    lists.add(student);
                }
                callback.onResult(lists);
            }
        };

        PagedList.Config config = new PagedList.Config.Builder().setPageSize(pageSize).setPrefetchDistance(pageSize)
                .setEnablePlaceholders(false).setInitialLoadSizeHint(pageSize * 3).build();

        list = new PagedList.Builder(dataSource, config).setFetchExecutor(new PageExcutor())
                .setNotifyExecutor(new PageNotifyExcutor())
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        System.out.println("打印:onZeroItemsLoaded");
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        System.out.println("打印:onItemAtFrontLoaded");
                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Object itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                        System.out.println("打印:onItemAtEndLoaded");
                    }
                })
                .build();

        itemCallback = new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(Student oldItem, Student newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(Student oldItem, Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName());
            }
        };
        adapter = new PagedAdapter(itemCallback, this, list);
        rv.setAdapter(adapter);
    }

    class PageExcutor implements Executor {

        public PageExcutor() {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("info", "PageExcutor run");
                }
            });
        }

        @Override
        public void execute(@NonNull Runnable command) {
            command.run();
        }
    }

    class PageNotifyExcutor implements Executor {

        public PageNotifyExcutor() {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("info", "PageNotifyExcutor run");
                }
            });
        }

        @Override
        public void execute(@NonNull Runnable command) {
            command.run();
        }
    }
}
