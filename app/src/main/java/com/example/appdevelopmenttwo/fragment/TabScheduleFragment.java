package com.example.appdevelopmenttwo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appdevelopmenttwo.LessonDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.adaptor.ScheduleBigAdapter;
import com.example.appdevelopmenttwo.bean.Lesson;
import com.example.appdevelopmenttwo.util.DisplayUtils;
import com.example.appdevelopmenttwo.util.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class TabScheduleFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int int_mParam1;
    private View view;
    private Context mContext;

    private LessonDbOpenHelper lessonDbOpenHelper;
    private List<Lesson> lessons;
    private RecyclerView mRecyclerView;
    private ScheduleBigAdapter scheduleBigAdapter;

    private IntentFilter intentFilter;
    private myReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    public TabScheduleFragment() {
    }

    public static TabScheduleFragment newInstance(String param1, String param2) {
        TabScheduleFragment fragment = new TabScheduleFragment();
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
        view = inflater.inflate(R.layout.fragment_tab_schedule, container, false);
        mContext = view.getContext();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDataFromDB();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initReceiver();
        initData();
    }

    private void initView() {
        lessonDbOpenHelper = new LessonDbOpenHelper(mContext);
//        lessons = lessonDbOpenHelper.queryAllFromDb();
        lessons = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.schedule_all);
        scheduleBigAdapter = new ScheduleBigAdapter(mContext,lessons);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setAdapter(scheduleBigAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        RecyclerViewDivider recyclerViewDivider = new RecyclerViewDivider(mContext);
        recyclerViewDivider.setHorizontaloffset(DisplayUtils.dp2px(25), -DisplayUtils.dp2px(25));
//        recyclerViewDivider.setDividerColor(Color.GRAY);
        recyclerViewDivider.setDividerHeight(DisplayUtils.dp2px( 1.5f));
        mRecyclerView.addItemDecoration(recyclerViewDivider);
    }

    private void initData() {
        refreshDataFromDB();
        scheduleBigAdapter.refreshData(lessons);
    }

    public void refreshDataFromDB(){
        lessons.clear();
        List<Lesson> temps = lessonDbOpenHelper.queryAllFromDb();
        for (Lesson l: temps) {
            Log.e("TAG", "refreshDataFromDB: judgeLesson");
            judgeLesson(l);
        }
//        lessons.forEach(System.out::println);

        temps = lessonDbOpenHelper.queryFromDbByUuid("1");
        temps.forEach(System.out::println);

        lessons.addAll(temps);
        TreeMap<String, Lesson> map = new TreeMap<>();
        for (Lesson l: lessons) {
            map.put(l.getStartTime(), l);
        }
        lessons.clear();
        for (String key: map.keySet()) {
            lessons.add(map.get(key));
//            System.out.println(map.get(key).toString());
        }
        scheduleBigAdapter.refreshData(lessons);
    }

    private void judgeLesson(Lesson l) {
//        HashSet<Integer> hashSet = hasWeek(l);
//        if (hashSet.contains(12)) {}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Calendar calendar = Calendar.getInstance();
//            Log.e("TAG", "judgeLesson: CHINA");
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            //DAY_OF_WEEK是从星期日开始的
            if (l.getDay()== int_mParam1){
//                Log.e("TAG", "judgeLesson: DAY_OF_WEEK");
                if (!l.getName().isEmpty()) {
                    lessons.add(l);
//                    Log.e("TAG", "refreshDataFromDB: add");
                }
            }
        }
    }

    class myReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("updateSchedule".equals(action)) {
                refreshDataFromDB();
            }
        }
    }

    private void initReceiver() {
        localReceiver = new myReceiver();
        //本地广播管理器获取实例
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        //      意图过滤器
        intentFilter = new IntentFilter();
//      添加要监听的广播action
        intentFilter.addAction("updateSchedule");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }
}