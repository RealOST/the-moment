package com.example.appdevelopmenttwo.adaptor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdevelopmenttwo.LessonDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.ScheduleActivity;
import com.example.appdevelopmenttwo.bean.Lesson;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    protected List<Lesson> lessons;
    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected LessonDbOpenHelper mLessonDbOpenHelper;

    public ScheduleAdapter(Context mContext, List<Lesson> lessons) {
        this.lessons = lessons;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        mLessonDbOpenHelper = new LessonDbOpenHelper(mContext);
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_lesson_layout, parent, false);
        ScheduleViewHolder scheduleViewHolder = new ScheduleViewHolder(view);
        return scheduleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.time_start.setText(lesson.getStartTime());
        holder.time_end.setText(lesson.getEndTime());
        holder.lesson_name.setText(lesson.getName());
        holder.lesson_name.requestFocus();
        holder.tip.requestFocus();
        holder.tip.setText(lesson.getLocation());
        if (!TextUtils.isEmpty(holder.lesson_name.getText())) {
            Drawable icon = mContext.getResources().getDrawable(R.drawable.class_time);
            //setBounds(left,top,right,bottom)里的参数从左到右分别是
            //drawable的左边到textview左边缘+padding的距离，drawable的上边离textview上边缘+padding的距离
            //drawable的右边边离textview左边缘+padding的距离，drawable的下边离textview上边缘+padding的距离
            //所以right-left = drawable的宽，top - bottom = drawable的高
            icon.setBounds(0, 0, 70, 70);
            holder.lesson_name.setCompoundDrawables(null, null, icon, null);
        }

        holder.svContainer.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ScheduleActivity.class);
//            SharedPreferences spf = mContext.getSharedPreferences("schedule",mContext.MODE_PRIVATE);
//            SharedPreferences.Editor editor = spf.edit();
//            Gson gson = new Gson();
//            String data = gson.toJson(lessons);
//            editor.putString("lessons",data).apply();
            Bundle bundle = new Bundle();
            bundle.putSerializable("lesson", (Serializable) lessons);
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lessons == null? 0: lessons.size();
    }

    public void refreshData(List<Lesson> lessons) {
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView time_start;
        TextView time_end;
        TextView lesson_name;
        TextView tip;
        ViewGroup svContainer;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            time_start = itemView.findViewById(R.id.time_start);
            time_end = itemView.findViewById(R.id.time_end);
            lesson_name = itemView.findViewById(R.id.lesson_name);
            tip = itemView.findViewById(R.id.tip);
            svContainer = itemView.findViewById(R.id.schedule_lesson_container);
        }
    }
}
