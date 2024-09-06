package com.example.appdevelopmenttwo.adaptor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.appdevelopmenttwo.LessonDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.ScheduleActivity;
import com.example.appdevelopmenttwo.bean.Lesson;
import com.example.appdevelopmenttwo.bean.Note;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScheduleBigAdapter extends ScheduleAdapter {
    private LessonDbOpenHelper lessonDbOpenHelper;

    public ScheduleBigAdapter(Context mContext, List<Lesson> lessons) {
        super(mContext, lessons);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        lessonDbOpenHelper = new LessonDbOpenHelper(mContext);

        Lesson lesson = lessons.get(position);
        holder.time_start.setText(lesson.getStartTime());
        holder.time_end.setText(lesson.getEndTime());
        holder.lesson_name.setText(lesson.getName());
        holder.lesson_name.requestFocus();
        holder.tip.setText(lesson.getLocation());
        if (!TextUtils.isEmpty(holder.tip.getText())) {
            Drawable icon = mContext.getResources().getDrawable(R.drawable.class_time);
            //setBounds(left,top,right,bottom)里的参数从左到右分别是
            //drawable的左边到textview左边缘+padding的距离，drawable的上边离textview上边缘+padding的距离
            //drawable的右边边离textview左边缘+padding的距离，drawable的下边离textview上边缘+padding的距离
            //所以right-left = drawable的宽，top - bottom = drawable的高
            icon.setBounds(0, 0, 70, 70);
            holder.lesson_name.setCompoundDrawables(null, null, icon, null);
        }

        holder.svContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                View v = mLayoutInflater.inflate(R.layout.list_item_dialog_layout, null);
                TextView tvDelete = v.findViewById(R.id.tv_delete);
                TextView tvEdit =v.findViewById(R.id.tv_edit);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int row = lessonDbOpenHelper.deleteFromDbById(lesson.getId());
                        if (row > 0) {
                            removeData(position);
                        }
                        dialog.dismiss();
                    }
                });

                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        AlertDialog dialog = new MaterialAlertDialogBuilder(view.getContext())
                                .setTitle("编辑")
                                .setIcon(R.drawable.ic_baseline_construction_24)
                                .setView(R.layout.fragment_add_lesson_dialog)
                                .setPositiveButton(
                                        "确认",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                TextInputEditText et_title = ((AlertDialog) dialog).findViewById(R.id.et_title);
                                                TextInputEditText et_location = ((AlertDialog) dialog).findViewById(R.id.et_deadLine);
                                                TextInputEditText et_startTime = ((AlertDialog) dialog).findViewById(R.id.et_title);
                                                TextInputEditText et_endTime = ((AlertDialog) dialog).findViewById(R.id.et_deadLine);
                                                String title = et_title.getText().toString().trim();
                                                String location = et_location.getText().toString().trim();
                                                String startTime = et_startTime.getText().toString().trim();
                                                String endTime = et_endTime.getText().toString().trim();
                                                if (TextUtils.isEmpty(title)) {
                                                    ToastUtil.toastShort(mContext, "事件不能为空！");
                                                    return;
                                                }
                                                if (TextUtils.isEmpty(startTime)) {
                                                    ToastUtil.toastShort(mContext, "起始时间不能为空！");
                                                    return;
                                                }
                                                if (TextUtils.isEmpty(endTime)) {
                                                    ToastUtil.toastShort(mContext, "结束时间不能为空！");
                                                    return;
                                                }
                                                if (Integer.parseInt(startTime.split(":")[0])>Integer.parseInt(endTime.split(":")[0])&&Integer.parseInt(startTime.split(":")[1])>Integer.parseInt(endTime.split(":")[1])){
                                                    ToastUtil.toastShort(mContext, "起始时间不能大于结束时间");
                                                    return;
                                                }
                                                Lesson lesson = new Lesson();
                                                lesson.setUuid("uuid");
                                                lesson.setStartTime(startTime);
                                                lesson.setEndTime(endTime);
                                                lesson.setName(title);
                                                lesson.setDay(lesson.getDay());
                                                lesson.setLocation(location);
                                                long row = lessonDbOpenHelper.updateData(lesson);
                                                if (row != -1) {
                                                    ToastUtil.toastShort(mContext, "修改成功！");
                                                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
                                                    Intent intent1 = new Intent("updateSchedule");
                                                    localBroadcastManager.sendBroadcast(intent1);
                                                    Intent intent2 = new Intent("cancelFragment");
                                                    localBroadcastManager.sendBroadcast(intent2);
                                                } else {
                                                    ToastUtil.toastShort(mContext, "修改失败！");
                                                }
                                            }

                                            private String getCurrentTimeFormat() {
                                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                                Date date = new Date();
                                                return simpleDateFormat.format(date);
                                            }
                                        })
                                .setNegativeButton("取消", null)
                                .show();
                    }
                });
                dialog.setContentView(v);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                return true;
            }
        });
    }

    public void removeData(int pos) {
        lessons.remove(pos);
        notifyItemRemoved(pos);
    }
}
