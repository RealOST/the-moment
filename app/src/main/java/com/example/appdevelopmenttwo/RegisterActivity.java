package com.example.appdevelopmenttwo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.widget.Toolbar;

import com.example.appdevelopmenttwo.bean.Lesson;
import com.example.appdevelopmenttwo.bean.Student;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.example.appdevelopmenttwo.util.WarningUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity {
    public static final int RESULT_CODE_REGISTER = 0;
    private CheckBox cbTerm;
    private TextInputEditText etName,etPassword, etConfirm;
    private Toolbar toolbar;

    private SQLiteOH sqLiteOH;
    private LessonDbOpenHelper lessonDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        sqLiteOH = new SQLiteOH(this);
        lessonDbOpenHelper = new LessonDbOpenHelper(this);
    }

    private void initView() {
        cbTerm = findViewById(R.id.terms);
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        etConfirm = findViewById(R.id.et_confirm);

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void register(View view) {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm = etConfirm.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            WarningUtil.warning(this,"账号不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            WarningUtil.warning(this,"密码不能为空");
            return;
        }
        if(TextUtils.isEmpty(confirm)){
            WarningUtil.warning(this,"请确认密码");
            return;
        }

        if(!password.equals(confirm)){
            WarningUtil.warning(this,"两次密码不一致");
            return;
        }

        Student student = new Student();
        student.setName(name);
        student.setPassword(password);
        String uuid = "uuid";
        student.setUuid(uuid);

        long rowId = sqLiteOH.insertData(student);
        if(rowId < 0) {
            ToastUtil.toastShort(this,"Error");
            return;
        }

        if(cbTerm.isChecked()){
            ToastUtil.toastShort(this,"注册成功");

            initSchedule();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("password",password);
            startActivity(intent);
            finish();
        }else{
            WarningUtil.warning(this,"请阅读用户协议");
        }

//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("name",name);
//        bundle.putString("password",password);
//        intent.putExtras(bundle);
//        setResult(RESULT_CODE_REGISTER,intent);//新的activity传回给前面的activity数据
    }

    private void initSchedule() {
        List<Lesson> schedule = new ArrayList<>();
        String key = "1";

        Lesson lesson1 = new Lesson();
        lesson1.setUuid(key);
        lesson1.setStartTime("07:20");
        lesson1.setEndTime("08:00");
        lesson1.setName("吃早饭");
        lesson1.setLocation("一顿营养全面而丰富的早餐，让你一整天活力十足~");
        Lesson lesson2 = new Lesson();
        lesson2.setUuid(key);
        lesson2.setStartTime("08:30");
        lesson2.setEndTime("09:00");
        lesson2.setName("避免剧烈运动");
        lesson2.setLocation("早上人体免疫系统最弱，不要做剧烈运动~");
        Lesson lesson3 = new Lesson();
        lesson3.setUuid(key);
        lesson3.setStartTime("09:00");
        lesson3.setEndTime("10:00");
        lesson3.setName("做困难工作");
        lesson3.setLocation("这个时间段，人脑最清晰，应该用来做最有难度的事~");
        Lesson lesson4 = new Lesson();
        lesson4.setUuid(key);
        lesson4.setStartTime("12:00");
        lesson4.setEndTime("12:30");
        lesson4.setName("午餐");
        lesson4.setLocation("丰富的午餐为你的身体增添能量，保证你身体的能量所需~");
        Lesson lesson5 = new Lesson();
        lesson5.setUuid(key);
        lesson5.setStartTime("13:00");
        lesson5.setEndTime("14:00");
        lesson5.setName("午休");
        lesson5.setLocation("一顿营养全面而丰富的早餐，让你一整天活力十足~");
        Lesson lesson6 = new Lesson();
        lesson6.setUuid(key);
        lesson6.setStartTime("14:00");
        lesson6.setEndTime("16:00，");
        lesson6.setName("做创意性工作");
        lesson6.setLocation("午后是人类思维最活跃的时间，非常适合做一些创意性的工作~");
        Lesson lesson7 = new Lesson();
        lesson7.setUuid(key);
        lesson7.setStartTime("16:00");
        lesson7.setEndTime("18:00");
        lesson7.setName("做细致性工作");
        lesson7.setLocation("这阶段，身体和大脑都处于一天的巅峰状态，这时候我们应该做细致而密集的工作~");
        Lesson lesson8 = new Lesson();
        lesson8.setUuid(key);
        lesson8.setStartTime("18:00");
        lesson8.setEndTime("19:00");
        lesson8.setName("吃晚饭");
        lesson8.setLocation("晚餐要多吃一些比较清淡易消化的膳食~");
        schedule.add(lesson1);
        schedule.add(lesson2);
        schedule.add(lesson3);
        schedule.add(lesson4);
        schedule.add(lesson5);
        schedule.add(lesson6);
        schedule.add(lesson7);
        schedule.add(lesson8);

        for (Lesson lesson : schedule) {
//            System.out.println(lesson.toString());
            lessonDbOpenHelper.insertData(lesson);
        }
    }
}