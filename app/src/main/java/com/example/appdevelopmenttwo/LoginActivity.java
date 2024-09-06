package com.example.appdevelopmenttwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


import com.example.appdevelopmenttwo.bean.Student;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.example.appdevelopmenttwo.util.WarningUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginActivity extends BaseActivity {
    public static final int REQUEST_CODE_REGISTER = 1;
    private TextInputEditText etName;
    private TextInputEditText etPassword;
    private SQLiteOH sqLiteOH;
    private CheckBox cb_remember;
    private TextInputLayout userInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        sqLiteOH = new SQLiteOH(this);
    }

    private void initData() {
        SharedPreferences spf = getSharedPreferences("spRecord",MODE_PRIVATE);
        String name = spf.getString("name", "");
        String password = spf.getString("password", "");
        boolean isRemember = spf.getBoolean("isRemember", false);

        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name");
        String password1 = intent.getStringExtra("password");
        if(!TextUtils.isEmpty(name1)) {
            etName.setText(name1);
            etPassword.setText(password1);
        } else if(isRemember){
            etName.setText(name);
            etPassword.setText(password);
            cb_remember.setChecked(true);
        }
    }

    private void initView() {
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        cb_remember = findViewById(R.id.cb_remember);
        userInputLayout = findViewById(R.id.userInputLayout);
        userInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 文本变化前调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 文本发生变化时调用
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 文本发生变化后调用
                if(userInputLayout.getEditText().getText().toString().trim().length()>10){
                    userInputLayout.setError("账号长度超出限制");
                }else{
                    userInputLayout.setError(null);
                }
            }
        });
    }

    public void login(View view) {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            WarningUtil.warning(this,"账号不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            WarningUtil.warning(this,"密码不能为空");
            return;
        }
        List<Student> students= sqLiteOH.queryAllFromDb();
        for (Student stu: students) {
            if(name.equals(stu.getName()) && password.equals(stu.getPassword())){
                if(cb_remember.isChecked()){
                    SharedPreferences spf = getSharedPreferences("spRecord",MODE_PRIVATE);
                    SharedPreferences.Editor edit = spf.edit();
                    edit.putString("name",name)
                            .putString("password",password)
                            .putBoolean("isRemember",true)
                            .apply();
                }else{
                    SharedPreferences spf = getSharedPreferences("spRecord",MODE_PRIVATE);
                    SharedPreferences.Editor edit = spf.edit();
                    edit.putBoolean("isRemember",false).apply();
                }
                ToastUtil.toastShort(this,"登录成功");
                ActivityCollector.finishAll(); // 销毁所有活动
                Intent intent = new Intent(this, VPFragmentBotNavTabActivity.class);
                startActivity(intent);
            } else {
                WarningUtil.warning(this,"密码或账号错误");
            }
        }
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    public void jumpToSample2(View view) {
        Intent intent = new Intent(LoginActivity.this, VPFragmentBotNavTabActivity.class);
        startActivity(intent);
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_REGISTER && resultCode == RegisterActivity.RESULT_CODE_REGISTER && data != null){
            Bundle extras = data.getExtras();
            String name = extras.getString("name","");
            String password = extras.getString("password","");

            etName.setText(name);
            etPassword.setText(password);
        }
    }*/
}
