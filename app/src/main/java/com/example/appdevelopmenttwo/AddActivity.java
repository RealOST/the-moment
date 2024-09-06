package com.example.appdevelopmenttwo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdevelopmenttwo.bean.Note;
import com.example.appdevelopmenttwo.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle, etDeadLine;

    private NoteDbOpenHelper mNoteDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.et_title);
        etDeadLine = findViewById(R.id.et_deadLine);
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);

        etDeadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void add(View view) {
        String title = etTitle.getText().toString();
        String deadLine = etDeadLine.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "目标不能为空！");
            return;
        }
        if (TextUtils.isEmpty(deadLine)) {
            ToastUtil.toastShort(this, "期限不能为空！");
            return;
        }
        Note note = new Note();

        note.setTitle(title);
        note.setDeadLine(deadLine);
        note.setCreatedTime(getCurrentTimeFormat());
        note.setCountDown(deadLine);
        long row = mNoteDbOpenHelper.insertData(note);
        if (row != -1) {
            ToastUtil.toastShort(this,"添加成功！");
            this.finish();
        }else {
            ToastUtil.toastShort(this,"添加失败！");
        }
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}