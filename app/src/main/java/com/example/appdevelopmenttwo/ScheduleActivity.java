package com.example.appdevelopmenttwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.example.appdevelopmenttwo.adaptor.FStateVpTitleAdaptor;
import com.example.appdevelopmenttwo.fragment.AddLessonFragment;
import com.example.appdevelopmenttwo.fragment.TabScheduleFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private MaterialToolbar materialToolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<TabScheduleFragment> fragmentList;
    private List<String> titleList;
    private FStateVpTitleAdaptor fStateVpTitleAdaptor;

    private IntentFilter intentFilter;
    private ScheduleActivity.myReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private static int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        initView();
        initReceiver();
        initEvent();
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.home_vp);
        fStateVpTitleAdaptor = new FStateVpTitleAdaptor(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(fStateVpTitleAdaptor);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                fragmentList.get(position+1).refreshDataFromDB();
                mPosition = position+1;
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(ScheduleActivity.this);
                Intent intent = new Intent("cancelFragment");
                localBroadcastManager.sendBroadcast(intent);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        materialToolbar = findViewById(R.id.material_toolbar);
        materialToolbar.setNavigationOnClickListener(v -> finish());

        fragmentList = new ArrayList<>();

        TabScheduleFragment vpFragment1 = TabScheduleFragment.newInstance("1","");
        TabScheduleFragment vpFragment2 = TabScheduleFragment.newInstance("2","");
        TabScheduleFragment vpFragment3 = TabScheduleFragment.newInstance("3","");
        TabScheduleFragment vpFragment4 = TabScheduleFragment.newInstance("4","");
        TabScheduleFragment vpFragment5 = TabScheduleFragment.newInstance("5","");
        TabScheduleFragment vpFragment6 = TabScheduleFragment.newInstance("6","");
        TabScheduleFragment vpFragment7 = TabScheduleFragment.newInstance("7","");

        fragmentList.add(vpFragment1);
        fragmentList.add(vpFragment2);
        fragmentList.add(vpFragment3);
        fragmentList.add(vpFragment4);
        fragmentList.add(vpFragment5);
        fragmentList.add(vpFragment6);
        fragmentList.add(vpFragment7);

        titleList = new ArrayList<>();

        titleList.add("周一");
        titleList.add("周二");
        titleList.add("周三");
        titleList.add("周四");
        titleList.add("周五");
        titleList.add("周六");
        titleList.add("周日");
    }

    private void initEvent() {
        materialToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_add:
                    AddLessonFragment addLessonFragment = AddLessonFragment.newInstance(String.valueOf(mPosition),"");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fcv, addLessonFragment)
                            .commit();
                    break;
                default:
                    break;
            }
            return false;
        });
    }

    private void initReceiver() {
        localReceiver = new ScheduleActivity.myReceiver();
        //本地广播管理器获取实例
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        //      意图过滤器
        intentFilter = new IntentFilter();
//      添加要监听的广播action
        intentFilter.addAction("cancelFragment");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    private void removeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fcv);
        if (fragment!= null) {
            fragmentTransaction.remove(fragment).commit();
        }else {
//            ToastUtil.toastShort(this,"销毁失败");
        }
    }
    class myReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("cancelFragment".equals(action)) {
                removeFragment();
            }
        }
    }
}