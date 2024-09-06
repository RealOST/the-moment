package com.example.appdevelopmenttwo.fragment;

import static com.google.android.material.timepicker.TimeFormat.CLOCK_12H;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdevelopmenttwo.LessonDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.ScheduleActivity;
import com.example.appdevelopmenttwo.bean.Lesson;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddLessonFragment extends Fragment {

    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int int_mParam1;

    private TextInputEditText et_title;
    private TextInputEditText et_location;
    private TextInputEditText et_startTime;
    private TextInputEditText et_endTime;
    private MaterialButton btn_cancel;
    private MaterialButton btn_confirm;
    private View view;

    private LessonDbOpenHelper lessonDbOpenHelper;


    public AddLessonFragment() {
    }

    public static AddLessonFragment newInstance(String param1, String param2) {
        AddLessonFragment fragment = new AddLessonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            int_mParam1 = Integer.parseInt(mParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_lesson, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        lessonDbOpenHelper = new LessonDbOpenHelper(getActivity());
        initView();
        initEvent();

    }

    private void initEvent() {

        et_startTime.setOnClickListener(view1 -> {
            MaterialTimePicker.Builder materialTimePickerBuilder = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(0)
                    .setMinute(0)
                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK);
            MaterialTimePicker timePicker = materialTimePickerBuilder.build();
            timePicker.show(requireFragmentManager(), "fragment_tag");
            timePicker.addOnPositiveButtonClickListener(dialog -> {
                String hour = String.valueOf(timePicker.getHour());
                String minute = String.valueOf(timePicker.getMinute());
                if (hour.equals("0")) {
                    hour = "00";
                }
                if (minute.equals("0")) {
                    minute = "00";
                }
                String time = hour + ":" + minute;
                et_startTime.setText(time);
            });
        });

        et_endTime.setOnClickListener(view1 -> {
            MaterialTimePicker.Builder materialTimePickerBuilder = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(0)
                    .setMinute(0)
                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK);
            MaterialTimePicker timePicker = materialTimePickerBuilder.build();
            timePicker.show(requireFragmentManager(), "fragment_tag");
            timePicker.addOnPositiveButtonClickListener(dialog -> {
                String hour = String.valueOf(timePicker.getHour());
                String minute = String.valueOf(timePicker.getMinute());
                if (hour.equals("0")) {
                    hour = "00";
                }
                if (minute.equals("0")) {
                    minute = "00";
                }
                String time = hour + ":" + minute;
                et_endTime.setText(time);
            });
        });

        btn_cancel.setOnClickListener(view1 -> {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
            Intent intent = new Intent("cancelFragment");
            localBroadcastManager.sendBroadcast(intent);
        });
        btn_confirm.setOnClickListener(view1 -> {
            String title = et_title.getText().toString().trim();
            String location = et_location.getText().toString().trim();
            String startTime = et_startTime.getText().toString().trim();
            String endTime = et_endTime.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                ToastUtil.toastShort(getActivity(), "事件不能为空！");
                return;
            }
            if (TextUtils.isEmpty(startTime)) {
                ToastUtil.toastShort(getActivity(), "起始时间不能为空！");
                return;
            }
            if (TextUtils.isEmpty(endTime)) {
                ToastUtil.toastShort(getActivity(), "结束时间不能为空！");
                return;
            }
            if (Integer.parseInt(startTime.split(":")[0])>Integer.parseInt(endTime.split(":")[0])&&Integer.parseInt(startTime.split(":")[1])>Integer.parseInt(endTime.split(":")[1])){
                ToastUtil.toastShort(getActivity(), "起始时间不能大于结束时间");
                return;
            }
            Lesson lesson = new Lesson();
            lesson.setUuid("uuid");
            lesson.setStartTime(startTime);
            lesson.setEndTime(endTime);
            lesson.setName(title);
            lesson.setDay(int_mParam1);
            lesson.setLocation(location);
            long row = lessonDbOpenHelper.insertData(lesson);
            if (row != -1) {
                ToastUtil.toastShort(getActivity(), "添加成功！");
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
                Intent intent1 = new Intent("updateSchedule");
                localBroadcastManager.sendBroadcast(intent1);
                Intent intent2 = new Intent("cancelFragment");
                localBroadcastManager.sendBroadcast(intent2);
            } else {
                ToastUtil.toastShort(getActivity(), "添加失败！");
            }
        });
    }

    private void initView() {
        et_title = view.findViewById(R.id.et_title);
        et_location = view.findViewById(R.id.et_location);
        et_startTime = view.findViewById(R.id.et_startTime);
        et_endTime = view.findViewById(R.id.et_endTime);

        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_confirm = view.findViewById(R.id.btn_confirm);
    }



}