package com.example.appdevelopmenttwo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.appdevelopmenttwo.R;
import com.example.appdevelopmenttwo.adaptor.FStateVpTitleAdaptor;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class VPHomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList;

    private FStateVpTitleAdaptor fStateVpTitleAdaptor;

    public VPHomeFragment() {
        // Required empty public constructor
    }

    public static VPHomeFragment newInstance(String param1, String param2) {
        VPHomeFragment fragment = new VPHomeFragment();
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
        return inflater.inflate(R.layout.fragment_v_p_home2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.home_vp);

        initData();

//        fStateVpTitleAdaptor = new FStateVpTitleAdaptor(getChildFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(fStateVpTitleAdaptor);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initData() {
        fragmentList = new ArrayList<>();

        VPFragment vpFragment1 = VPFragment.newInstance("推荐","");
        VPFragment vpFragment2 = VPFragment.newInstance("关注","");
        VPFragment vpFragment3 = VPFragment.newInstance("C++","");
        VPFragment vpFragment4 = VPFragment.newInstance("Java","");
        VPFragment vpFragment5 = VPFragment.newInstance("Python","");
        VPFragment vpFragment6 = VPFragment.newInstance("PHP","");
        VPFragment vpFragment7 = VPFragment.newInstance("Hare","");

        fragmentList.add(vpFragment1);
        fragmentList.add(vpFragment2);
        fragmentList.add(vpFragment3);
        fragmentList.add(vpFragment4);
        fragmentList.add(vpFragment5);
        fragmentList.add(vpFragment6);
        fragmentList.add(vpFragment7);

        titleList = new ArrayList<>();

        titleList.add("推荐");
        titleList.add("关注");
        titleList.add("C++");
        titleList.add("Java");
        titleList.add("Python");
        titleList.add("PHP");
        titleList.add("Hare");
    }
}