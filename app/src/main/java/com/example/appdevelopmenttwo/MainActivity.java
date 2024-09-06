package com.example.appdevelopmenttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void jumpToSample1(View view) {
//        Intent intent = new Intent(MainActivity.this, FragmentDrawerActivity.class);
//        startActivity(intent);
//    }

    public void jumpToSample2(View view) {
        Intent intent = new Intent(MainActivity.this, VPFragmentBotNavTabActivity.class);
        startActivity(intent);
    }
}