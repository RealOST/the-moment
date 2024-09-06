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

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AnalogClock;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appdevelopmenttwo.LessonDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.adaptor.ScheduleAdapter;
import com.example.appdevelopmenttwo.bean.DayWeatherBean;
import com.example.appdevelopmenttwo.bean.Lesson;
import com.example.appdevelopmenttwo.bean.WeatherBean;
import com.example.appdevelopmenttwo.util.DisplayUtils;
import com.example.appdevelopmenttwo.util.NetUtil;
import com.example.appdevelopmenttwo.util.RecyclerViewDivider;
import com.example.appdevelopmenttwo.util.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class VPMainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isWeather = false;

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

    private IntentFilter intentFilter;
    private VPMainFragment.myReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;

    private TextView tvWeek, tvDayOfWeek, tvTemLowHigh, tvWin, tvDate;
    private ImageView ivWeather;
    private DayWeatherBean todayWeather;

    public VPMainFragment() {
        // Required empty public constructor
    }


    public static VPMainFragment newInstance(String param1, String param2) {
        VPMainFragment fragment = new VPMainFragment();
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
        this.view = inflater.inflate(R.layout.fragment_v_p_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        System.out.println("initView");
        initView();
        initFont();
        initEvent();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initEvent() {
        lessonDbOpenHelper = new LessonDbOpenHelper(view.getContext());
        lessons = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(view.getContext(), lessons);
        mRecyclerView.setAdapter(scheduleAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        refreshDataFromDB();
        //TODO 自定义模拟时钟控件，获取当前指针
        RecyclerViewDivider recyclerViewDivider = new RecyclerViewDivider(requireContext());
        recyclerViewDivider.setHorizontaloffset(DisplayUtils.dp2px(25), -DisplayUtils.dp2px(25));
//        recyclerViewDivider.setDividerColor(Color.GRAY);
        recyclerViewDivider.setDividerHeight(DisplayUtils.dp2px( 1.5f));
        mRecyclerView.addItemDecoration(recyclerViewDivider);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refreshDataFromDB(){
//        for (int i = 0; i < 5; i++) {
//            Lesson lesson = new Lesson();
//            lesson.setStartTime("12:00");
//            lesson.setName("马克思主义基本原理");
//            lesson.setLocation("宿舍睡大觉");
//            lessons.add(lesson);
//        }
        lessons.clear();
        List<Lesson> temps = lessonDbOpenHelper.queryAllFromDb();
        for (Lesson l: temps) {
            Log.e("TAG", "refreshDataFromDB: judgeLesson");
            judgeLesson(l);
        }
//        lessons.forEach(System.out::println);

        temps = lessonDbOpenHelper.queryFromDbByUuid("1");
        for (Lesson l: temps) {
            System.out.println("数据为："+l.toString());
        }
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
        scheduleAdapter.refreshData(lessons);
    }

    /**
     * 判断课程信息是否符合需求
     * @param l
     */
    private void judgeLesson(Lesson l) {
//        HashSet<Integer> hashSet = hasWeek(l);
//        if (hashSet.contains(12)) {}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Calendar calendar = Calendar.getInstance();
//            Log.e("TAG", "judgeLesson: CHINA");
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            //DAY_OF_WEEK是从星期日开始的
            if (l.getDay()+1== calendar.get(Calendar.DAY_OF_WEEK)){
//                Log.e("TAG", "judgeLesson: DAY_OF_WEEK");
                if (!l.getName().isEmpty()) {
                    lessons.add(l);
//                    Log.e("TAG", "refreshDataFromDB: add");
                }
            }
        }
    }

    private HashSet<Integer> hasWeek(Lesson lesson) {
        String[] each = lesson.getWeek().split(",");
        HashSet<Integer> result = new HashSet<Integer>();
        for (String e : each) {
            String[] to = e.split("-");
            if (to.length>1){
                for (int i = Integer.parseInt(to[0]); i<=Integer.parseInt(to[1]); i++){
                    result.add(i);
                }
            }
        }
        return result;
    }

    private void initFont() {
//        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "mao_ken_shi_jin_hei2.ttf");
//        tv_note.setTypeface(typeface);
    }

    private void initView() {
        mRecyclerView = view.findViewById(R.id.schedule);

        localReceiver = new VPMainFragment.myReceiver();
        //本地广播管理器获取实例
        localBroadcastManager = LocalBroadcastManager.getInstance(view.getContext());
        //      意图过滤器
        intentFilter = new IntentFilter();
//      添加要监听的广播action
        intentFilter.addAction("update");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

        if (!isWeather) {
            isWeather = true;
            getWeatherOfCity("韶关");
            tvWeek = view.findViewById(R.id.tv_week);
            tvDate = view.findViewById(R.id.tv_date);
            tvDayOfWeek = view.findViewById(R.id.tv_dayOfWeek);
            ivWeather = view.findViewById(R.id.iv_weather);
        }
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

    public static void setViewAndChildrenEnabled(View view, boolean isEnabled) {
        view.setEnabled(isEnabled);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; ++i) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, isEnabled);
            }
        }
    }

    private Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String weather = (String) msg.obj;
                Log.d("fan", "--主线程收到了天气数据-weather---" + weather);
                if (TextUtils.isEmpty(weather)) {
                    Toast.makeText(view.getContext(), "天气数据为空！", Toast.LENGTH_LONG).show();
                    return;
                }

                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(weather, WeatherBean.class);
                if (weatherBean != null) {
                    Log.d("fan", "--解析后的数据-weather---" + weatherBean.toString());
                }

                updateUiOfWeather(weatherBean);

                Toast.makeText(view.getContext(), "更新天气~", Toast.LENGTH_SHORT).show();

            }

        }
    };

    private void getWeatherOfCity(String selectedCity) {
        // 开启子线程，请求网络
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 请求网络
                String weatherOfCity = NetUtil.getWeatherOfCity(selectedCity);
                // 使用handler将数据传递给主线程
                Message message = Message.obtain();
                message.what = 0;
                message.obj = weatherOfCity;
                mHandler.sendMessage(message);

            }
        }).start();

    }

    private void updateUiOfWeather(WeatherBean weatherBean) {
        if (weatherBean == null) {
            return;
        }

        List<DayWeatherBean> dayWeathers = weatherBean.getDayWeathers();
        todayWeather = dayWeathers.get(0);
        if (todayWeather == null) {
            return;
        }

        String[] Date = todayWeather.getDate().split("-");
        tvDate.setText(Date[1]+"月"+Date[2]+"日");
        tvDayOfWeek.setText(todayWeather.getWeek());
        ivWeather.setImageResource(getImgResOfWeather(todayWeather.getWeaImg()));

        dayWeathers.remove(0); // 去掉当天的天气
    }

    private int getImgResOfWeather(String weaStr) {
        // xue、lei、shachen、wu、bingbao、yun、yu、yin、qing
        int result = 0;
        switch (weaStr) {
            case "qing":
                result = R.drawable.qing;
                break;
            case "yin":
                result = R.drawable.yin;
                break;
            case "yu":
                result = R.drawable.rain;
                break;
            case "yun":
                result = R.drawable.duoyun;
                break;
            case "bingbao":
                result = R.drawable.biz_plugin_weather_leizhenyubingbao;
                break;
            case "wu":
                result = R.drawable.biz_plugin_weather_wu;
                break;
            case "shachen":
                result = R.drawable.biz_plugin_weather_shachenbao;
                break;
            case "lei":
                result = R.drawable.lei;
                break;
            case "xue":
                result = R.drawable.xue;
                break;
            default:
                result = R.drawable.qing;
                break;
        }
        return result;
    }
}