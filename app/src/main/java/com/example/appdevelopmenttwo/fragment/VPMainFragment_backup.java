/*
package com.example.appdevelopmenttwo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdevelopmenttwo.LessonDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.adaptor.ScheduleAdapter;
import com.example.appdevelopmenttwo.bean.Lesson;
import com.example.appdevelopmenttwo.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class VPMainFragment_backup extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    private TextView
    private TextView tv_note;
    private AnalogClock analogClock;
    private LessonDbOpenHelper lessonDbOpenHelper;
    private List<Lesson> lessons;
    private RecyclerView mRecyclerView;
    private ScheduleAdapter scheduleAdapter;

    private View view;
    private TextView tv1_time1;
    private TextView tv1_time2;
    private TextView tv2_time1;
    private TextView tv2_time2;
    private TextView tv3_time1;
    private TextView tv3_time2;
    private TextView tv4_time1;
    private TextView tv4_time2;
    private TextView fragMain_tv1;
    private TextView fragMain_tv2;
    private TextView fragMain_tv3;
    private TextView fragMain_tv4;
    private TextView tip_1;
    private TextView tip_2;
    private TextView tip_3;
    private TextView tip_4;
    private ImageView tv1_icon;
    private ImageView tv2_icon;
    private ImageView tv3_icon;
    private ImageView tv4_icon;

    private IntentFilter intentFilter;
    private VPMainFragment_backup.myReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    public VPMainFragment_backup() {
        // Required empty public constructor
    }


    public static VPMainFragment_backup newInstance(String param1, String param2) {
        VPMainFragment_backup fragment = new VPMainFragment_backup();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_v_p_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.view = view;
        initView();
        initFont();
        initEvent();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initEvent() {
        lessonDbOpenHelper = new LessonDbOpenHelper(view.getContext());
        lessons = new ArrayList<>();
//        scheduleAdapter = new ScheduleAdapter(view.getContext(), lessons);
//        mRecyclerView.setAdapter(scheduleAdapter);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
//        mRecyclerView.setLayoutManager(linearLayoutManager);
        refreshDataFromDB();
        //TODO 自定义模拟时钟控件，获取当前指针
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refreshDataFromDB(){
        lessons = lessonDbOpenHelper.queryAllFromDb();
        for (Lesson l: lessons) {
            judgeLesson(l);
        }
    }

    */
/**
     * 判断课程信息是否符合需求
     * @param l
     *//*

    private void judgeLesson(Lesson l) {
//        HashSet<String> hashSet = hasWeek(l);
//        if (hashSet.contains(12)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Calendar calendar = Calendar.getInstance();
//            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            //DAY_OF_WEEK是从星期日开始的
                if (l.getDay()%7+1== calendar.get(Calendar.DAY_OF_WEEK)){
//                    if (!l.getName().isEmpty()) {
//                        if (calendar.get(Calendar.AM_PM)==0)
                        switch (l.getTiming()) {
                            case 1:
                                tv1_time1.setText(l.getStartTime());
                                tv1_time2.setText(l.getEndTime());
                                fragMain_tv1.setText(l.getName());
                                tip_1.setText(l.getLocation());
                                Drawable icon = getResources().getDrawable(R.drawable.sleep);
                                //setBounds(left,top,right,bottom)里的参数从左到右分别是
                                //drawable的左边到textview左边缘+padding的距离，drawable的上边离textview上边缘+padding的距离
                                //drawable的右边边离textview左边缘+padding的距离，drawable的下边离textview上边缘+padding的距离
                                //所以right-left = drawable的宽，top - bottom = drawable的高
                                icon.setBounds(10,0,80,90);
                                fragMain_tv1.setCompoundDrawables(null, null, icon, null);
                                if (l.getName().isEmpty()){
                                    fragMain_tv1.setText("空闲");
                                }
                                break;
                            case 2:
                                tv2_time1.setText(l.getStartTime());
                                tv2_time2.setText(l.getEndTime());
                                fragMain_tv2.setText(l.getName());
                                tip_2.setText(l.getLocation());
                                tv2_icon.setImageResource(R.drawable.class_time);
                                if (l.getName().isEmpty()){
                                    fragMain_tv2.setText("空闲");
                                    tv2_icon.setImageDrawable(null);
                                }
                                break;
                            case 3:
                                tv3_time1.setText(l.getStartTime());
                                tv3_time2.setText(l.getEndTime());
                                fragMain_tv3.setText(l.getName());
                                tip_3.setText(l.getLocation());
                                    tv3_icon.setImageResource(R.drawable.class_time);
                                if (l.getName().isEmpty()){
                                    fragMain_tv3.setText("空闲");
                                    tv3_icon.setImageDrawable(null);
                                }
                                break;
                            case 4:
                                tv4_time1.setText(l.getStartTime());
                                tv4_time2.setText(l.getEndTime());
                                fragMain_tv4.setText(l.getName());
                                tip_4.setText(l.getLocation());
                                    tv4_icon.setImageResource(R.drawable.class_time);
                                if (l.getName().isEmpty()){
                                    fragMain_tv4.setText("空闲");
                                    tv4_icon.setImageDrawable(null);
                                }
                                break;
                            case 5:
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + l.getTiming());
                        }
//                    }
                }
            }
//        }
    }

    private HashSet<String> hasWeek(Lesson lesson) {
        String[] each = lesson.getWeek().split(",");
        for (String e : each) {
            String[] to = e.split("-");
        }
        return null;
    }

    private void initFont() {
//        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "mao_ken_shi_jin_hei2.ttf");
//        tv_note.setTypeface(typeface);

    }

    private void initView() {
//        mRecyclerView = view.findViewById(R.id.rv);

        tv_note = view.findViewById(R.id.tv_note);
        analogClock = view.findViewById(R.id.analogClock);

        tv1_time1 = view.findViewById(R.id.lesson_time1);
        tv1_time2 = view.findViewById(R.id.lesson_time2);
        tv2_time1 = view.findViewById(R.id.tv2_time1);
        tv2_time2 = view.findViewById(R.id.tv2_time2);
        tv3_time1 = view.findViewById(R.id.tv3_time1);
        tv3_time2 = view.findViewById(R.id.tv3_time2);
        tv4_time1 = view.findViewById(R.id.tv4_time1);
        tv4_time2 = view.findViewById(R.id.tv4_time2);
        fragMain_tv1 = view.findViewById(R.id.fragMain_tv);
        fragMain_tv2 = view.findViewById(R.id.fragMain_tv2);
        fragMain_tv3 = view.findViewById(R.id.fragMain_tv3);
        fragMain_tv4 = view.findViewById(R.id.fragMain_tv4);
        tip_1 = view.findViewById(R.id.tip_1);
        tip_2 = view.findViewById(R.id.tip_2);
        tip_3 = view.findViewById(R.id.tip_3);
        tip_4 = view.findViewById(R.id.tip_4);
        tv1_icon = view.findViewById(R.id.tv_icon);
        tv2_icon = view.findViewById(R.id.tv2_icon);
        tv3_icon = view.findViewById(R.id.tv3_icon);
        tv4_icon = view.findViewById(R.id.tv4_icon);

        localReceiver = new VPMainFragment_backup.myReceiver();
        //本地广播管理器获取实例
        localBroadcastManager = LocalBroadcastManager.getInstance(view.getContext());
        //      意图过滤器
        intentFilter = new IntentFilter();
//      添加要监听的广播action
        intentFilter.addAction("update");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    class myReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("add".equals(action)) {
                refreshDataFromDB();
                ToastUtil.toastShort(view.getContext(),"导入完成");
            }
        }
    }
}*/
