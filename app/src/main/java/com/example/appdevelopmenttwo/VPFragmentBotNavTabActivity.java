package com.example.appdevelopmenttwo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.example.appdevelopmenttwo.adaptor.VPBotNavAdaptor;
import com.example.appdevelopmenttwo.fragment.AddItemFragment;
import com.example.appdevelopmenttwo.fragment.VPMainFragment;
import com.example.appdevelopmenttwo.fragment.VPMineFragment;
import com.example.appdevelopmenttwo.fragment.VPTargetFragment;
import com.example.appdevelopmenttwo.html.WebViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class VPFragmentBotNavTabActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private VPBotNavAdaptor vpBotNavAdaptor;
    private List<Fragment> mFragmentList;
    private Toolbar toolbar;
    private TextView title;
    private int sTheme;

    private IntentFilter intentFilter;
    private VPFragmentBotNavTabActivity.myReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private LayoutInflater inflater;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sTheme = R.style.MDNotActionBar;
        setContentView(R.layout.activity_vpfragment_bot_nav_tab);

//        inflater = LayoutInflater.from(this);
        inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.tabs_layout,null);
        tabLayout = view.findViewById(R.id.tab_layout);

        initReceiver();
        initData();
        initEvent(view);

        viewPager = findViewById(R.id.vp_BNT);
        bottomNavigationView = findViewById(R.id.bot_nav_tab_menu);
        vpBotNavAdaptor = new VPBotNavAdaptor(getSupportFragmentManager(),mFragmentList);
        viewPager.setAdapter(vpBotNavAdaptor);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(VPFragmentBotNavTabActivity.this);
                Intent intent = new Intent("cancelFragment");
                localBroadcastManager.sendBroadcast(intent);
                onViewPagerSelected(position);
//                ToastUtil.newToast(VPFragmentBotNavTabActivity.this,"this is"+(position+1)+"page");
                onPrepareOptionsMenu(toolbar.getMenu());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_home:
                    viewPager.setCurrentItem(0);
                    title.setText("时间规划");
//                        toolbar.setVisibility(View.VISIBLE);
                    break;
                case R.id.menu_target:
                    viewPager.setCurrentItem(1);
                    title.setText("刻录目标");
                    break;
                case R.id.menu_mine:
                    viewPager.setCurrentItem(2);
                    title.setText("个人中心");
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void onViewPagerSelected(int position) {
        switch (position){
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.menu_target);
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.menu_mine);
                break;
            default:
                break;

        }
    }

    private void initData() {
        mFragmentList = new ArrayList<>();

        VPMainFragment vpMainFragment = VPMainFragment.newInstance("发现","");
        VPTargetFragment vpTargetFragment = VPTargetFragment.newInstance("","");
        VPMineFragment vpMineFragment = VPMineFragment.newInstance("我的","");

        mFragmentList.add(vpMainFragment);
        mFragmentList.add(vpTargetFragment);
        mFragmentList.add(vpMineFragment);
    }


    private void initEvent(View view) {
        title = findViewById(R.id.title);
        //点击左边返回按钮监听事件
        toolbar = findViewById(R.id.tool_bar);
//        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_add:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fcv, new AddItemFragment())
                            .commit();
//                    NoteDbOpenHelper mNoteDbOpenHelper = new NoteDbOpenHelper(VPFragmentBotNavTabActivity.this);
//                    AlertDialog dialog = new MaterialAlertDialogBuilder(VPFragmentBotNavTabActivity.this)
//                            .setTitle("添加")
//                            .setIcon(R.drawable.icon_add_item)
////                                .setView(view)
//                            .setView(R.layout.edit_text)
//                            .setPositiveButton(
//                                    "确认",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
////                                                TextInputEditText etTitle  = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().findViewById(R.id.et_title);
////                                                TextInputEditText etDeadLine  = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().findViewById(R.id.et_deadLine);
//                                            TextInputEditText etTitle = ((AlertDialog) dialog).findViewById(R.id.et_title);
//                                            TextInputEditText etDeadLine = ((AlertDialog) dialog).findViewById(R.id.et_deadLine);
//                                            etDeadLine.setOnClickListener(view -> {
//                                                MaterialDatePicker<Long> datePicker =MaterialDatePicker.Builder.datePicker()
//                                                        .setTitleText("请选择日期")
//                                                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                                                        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
//                                                        .build();
//                                                datePicker.show(getSupportFragmentManager(),datePicker.toString());
//
//                                                datePicker.addOnPositiveButtonClickListener(selection -> {
//                                                    Calendar calendar = Calendar.getInstance(Locale.CHINA);
//                                                    calendar.setTimeInMillis(selection);
//                                                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//                                                    String formattedDate  = format.format(calendar.getTime());
//                                                    etDeadLine.setText(formattedDate);
//                                                });
//                                            });
//
//                                            String title = etTitle.getText().toString().trim();
//                                            String deadLine = etDeadLine.getText().toString().trim();
//                                            if (TextUtils.isEmpty(title)) {
//                                                ToastUtil.toastShort(VPFragmentBotNavTabActivity.this, "目标不能为空！");
//                                                return;
//                                            }
//                                            if (TextUtils.isEmpty(deadLine)) {
//                                                ToastUtil.toastShort(VPFragmentBotNavTabActivity.this, "期限不能为空！");
//                                                return;
//                                            }
//                                            Note note = new Note();
//
//                                            note.setTitle(title);
//                                            note.setDeadLine(deadLine);
//                                            note.setCreatedTime(getCurrentTimeFormat());
//                                            note.setCountDown(deadLine);
//                                            long row = mNoteDbOpenHelper.insertData(note);
//                                            if (row != -1) {
//                                                ToastUtil.toastShort(VPFragmentBotNavTabActivity.this,"添加成功！");
//                                                localBroadcastManager = LocalBroadcastManager.getInstance(VPFragmentBotNavTabActivity.this);
//                                                Intent intent = new Intent("update");
//                                                localBroadcastManager.sendBroadcast(intent);
//                                            }else {
//                                                ToastUtil.toastShort(VPFragmentBotNavTabActivity.this,"添加失败！");
//                                            }
//                                        }
//
//                                        private String getCurrentTimeFormat() {
//                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
//                                            Date date = new Date();
//                                            return simpleDateFormat.format(date);
//                                        }
//                                    })
//                            .setNegativeButton("取消", null)
//                            .show();
                    break;
                case R.id.action_import:
                    Intent intent = new Intent(VPFragmentBotNavTabActivity.this, WebViewActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        });
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        switch (viewPager.getCurrentItem()) {
            case 0:
                menu.findItem(R.id.action_import).setVisible(true);
                menu.findItem(R.id.action_add).setVisible(false);
                break;
            case 1:
                menu.findItem(R.id.action_import).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(true);
                break;
            case 2:
                menu.findItem(R.id.action_import).setVisible(false);
                menu.findItem(R.id.action_add).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initReceiver() {
        localReceiver = new VPFragmentBotNavTabActivity.myReceiver();
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