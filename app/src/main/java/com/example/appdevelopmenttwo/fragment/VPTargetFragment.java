package com.example.appdevelopmenttwo.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.example.appdevelopmenttwo.NoteDbOpenHelper;
import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.adaptor.RVAdapter;
import com.example.appdevelopmenttwo.bean.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VPTargetFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private List<Note> mNotes;
    private RVAdapter rvAdapter;

    private NoteDbOpenHelper mNoteDbOpenHelper;

    private IntentFilter intentFilter;
    private myReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private View view;

    public VPTargetFragment() {
        // Required empty public constructor
    }

    public static VPTargetFragment newInstance(String param1, String param2) {
        VPTargetFragment fragment = new VPTargetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "onCreate: ");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_v_p_target, container, false);
        initView();
        initEvent();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initData(view);
        refreshDataFromDB();
        super.onViewCreated(view, savedInstanceState);
    }

    private void refreshDataFromDB() {
        mNotes = getDataFromDB();
        rvAdapter.refreshData(mNotes);
    }

    private void initEvent() {
        mNotes = new ArrayList<>();
        rvAdapter = new RVAdapter(view.getContext(), mNotes);
        mRecyclerView.setAdapter(rvAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

    }

    @Override
    public void onResume() {
        Log.e("TAG", "onResume: ");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e("TAG", "onPause: ");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("TAG", "onStop: ");

        super.onStop();
    }

    @Override
    public void onDestroy() {
        localBroadcastManager.unregisterReceiver(localReceiver);
        Log.e("TAG", "onDestroy: ");
        super.onDestroy();
    }

    private void initData(View view) {
        mNoteDbOpenHelper = new NoteDbOpenHelper(view.getContext());

        Note note = null;
        String format = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
/*        for (int i = 0; i < 20; i++) {
            note = new Note();
            note.setTitle("目标"+i);
            note.setDeadLine("目标日"+format);
            note.setCountDown("还剩"+i+"天");
//            note.setCreatedTime(new SimpleDateFormat("yyyy年MM月dd HH:mm:ss").format(new Date()));
            mNotes.add(note);
        }*/
//        CDTime cdTime = new CDTime();
//        cdTime.initEndTime("2022.11.19").calcTime().startRun();
//        note = new Note();
//        note.setCountDown("还剩"+cdTime.getmDay()+"天");
//        mNotes.add(note);
    }

    private List<Note> getDataFromDB() {
        return mNoteDbOpenHelper.queryAllFromDb();
    }

    private void initView() {
        mRecyclerView = view.findViewById(R.id.rv);

        localReceiver = new myReceiver();
        //本地广播管理器获取实例
        localBroadcastManager = LocalBroadcastManager.getInstance(view.getContext());
        //      意图过滤器
        intentFilter = new IntentFilter();
//      添加要监听的广播action
        intentFilter.addAction("update");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
    }

    public void addItem(View view) {
    }

    class myReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("update".equals(action)) {
                refreshDataFromDB();
            }
        }
    }
}