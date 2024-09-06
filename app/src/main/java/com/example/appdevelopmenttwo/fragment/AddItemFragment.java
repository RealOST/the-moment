package com.example.appdevelopmenttwo.fragment;

import android.content.Intent;
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

import com.example.appdevelopmenttwo.NoteDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.bean.Note;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private MaterialButton btn_cancel;
    private MaterialButton btn_confirm;
    private TextInputEditText etTitle;
    private TextInputEditText etDeadLine;
    private NoteDbOpenHelper mNoteDbOpenHelper;

    public AddItemFragment() {
        // Required empty public constructor
    }

    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initView();

        etDeadLine.setOnClickListener(view1 -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("请选择日期")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                    .build();
            datePicker.show(getActivity().getSupportFragmentManager(), datePicker.toString());

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Calendar calendar = Calendar.getInstance(Locale.CHINA);
                calendar.setTimeInMillis(selection);
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = format.format(calendar.getTime());
                etDeadLine.setText(formattedDate);
            });
        });
        btn_cancel.setOnClickListener(view1 -> {
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
            Intent intent = new Intent("cancelFragment");
            localBroadcastManager.sendBroadcast(intent);
        });
        btn_confirm.setOnClickListener(view1 -> {
            String title = etTitle.getText().toString().trim();
            String deadLine = etDeadLine.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                ToastUtil.toastShort(getActivity(), "目标不能为空！");
                return;
            }
            if (TextUtils.isEmpty(deadLine)) {
                ToastUtil.toastShort(getActivity(), "期限不能为空！");
                return;
            }
            Note note = new Note();

            note.setTitle(title);
            note.setDeadLine(deadLine);
            note.setCreatedTime(getCurrentTimeFormat());
            note.setCountDown(deadLine);
            long row = mNoteDbOpenHelper.insertData(note);
            if (row != -1) {
                ToastUtil.toastShort(getActivity(), "添加成功！");
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
                Intent intent1 = new Intent("update");
                localBroadcastManager.sendBroadcast(intent1);
                Intent intent2 = new Intent("cancelFragment");
                localBroadcastManager.sendBroadcast(intent2);
            } else {
                ToastUtil.toastShort(getActivity(), "添加失败！");
            }
        });
    }

    private void initView() {
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        etTitle = view.findViewById(R.id.et_title);
        etDeadLine = view.findViewById(R.id.et_deadLine);

        mNoteDbOpenHelper = new NoteDbOpenHelper(getActivity());
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}