package com.google.androidarc.adapter;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.androidarc.R;
import com.google.androidarc.entity.Student;

public class PagedAdapter extends PagedListAdapter<Student, PagedAdapter.RecyclerHolder> {
    private PagedList<Student> students;
    private Context context;

    public PagedAdapter(@NonNull DiffUtil.ItemCallback<Student> diffCallback, Context context, PagedList<Student> list) {
        super(diffCallback);
        this.context = context;
        this.students = list;
    }

    public PagedAdapter(@NonNull AsyncDifferConfig<Student> config) {
        super(config);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_text, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.textView.setText(students.get(position).getName());
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
