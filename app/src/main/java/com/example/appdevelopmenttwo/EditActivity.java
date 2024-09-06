package com.example.appdevelopmenttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.appdevelopmenttwo.bean.Note;
import com.example.appdevelopmenttwo.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private Note note;
    private EditText etTitle, etDeadLine;

    private NoteDbOpenHelper mNoteDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etTitle = findViewById(R.id.et_title);
        etDeadLine = findViewById(R.id.et_deadLine);

        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if (note != null) {
            etTitle.setText(note.getTitle());
            etDeadLine.setText(note.getDeadLine());
        }
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }

    public void save(View view) {
        String title = etTitle.getText().toString();
        String deadLine = etDeadLine.getText().toString();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }

        note.setTitle(title);
        note.setDeadLine(deadLine);
        note.setCountDown(getCurrentTimeFormat());
        long rowId = mNoteDbOpenHelper.updateData(note);
        if (rowId != -1) {
            ToastUtil.toastShort(this, "修改成功！");
            this.finish();
        }else{
            ToastUtil.toastShort(this, "修改失败！");
        }

    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日");
        Date date = new Date();
        return sdf.format(date);
    }
}