package com.example.smarthome.Page_Samrt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.AddSmartAdapter;
import com.example.smarthome.Helper.AddSmartHelper;
import com.example.smarthome.R;

import java.util.ArrayList;

public class SmartFragment extends Fragment{
    RecyclerView addsmart;
    RecyclerView.Adapter adapter;
    AddSmartAdapter rvadapter;
    ImageView go_off,go_home,night,addroom;
    RelativeLayout relativeLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smart_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        addsmart=getActivity().findViewById(R.id.add_smart);
        super.onActivityCreated(savedInstanceState);
        go_off=getActivity().findViewById(R.id.go_off);
        go_home=getActivity().findViewById(R.id.go_home);
        night=getActivity().findViewById(R.id.night);
        addroom=getActivity().findViewById(R.id.add_home);
        relativeLayout=getActivity().findViewById(R.id.more_model);
        recyclerView();
        initlongcard();//长按卡片进入具体模式的设置
        initcard(); //点击卡片上的文字，向云端发送信号，开启模式设置
    }

    private void initcard() {
        go_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        relativeLayout.setClickable(true);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
            }
        });
    }

    private void initlongcard() {

        go_off.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent1=new Intent(getActivity(), GoOff.class);
                startActivity(intent1);

                return false;
            }
        });
        go_home.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent2=new Intent(getActivity(), GoHome.class);
                startActivity(intent2);

                return false;
            }
        });
        night.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent3=new Intent(getActivity(), Night.class);
                startActivity(intent3);

                return false;
            }
        });

    }

    private void recyclerView() {
        addsmart.setHasFixedSize(true);
        addsmart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<AddSmartHelper> addSmartHelpers = new ArrayList<>();
        addSmartHelpers.add(new AddSmartHelper(R.drawable.lights_smart, "灯光"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.air_condition_smart, "空调"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.curtain_smart, "窗帘"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.lock_smart, "智能门锁"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.music,"智能音响"));
        rvadapter = new AddSmartAdapter(addSmartHelpers);
        addsmart.setAdapter(rvadapter);
        rvadapter.setOnItemClickListener(new AddSmartAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(getActivity(), AdjustTheLights.class);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(), AdjustTheAirCondition.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getActivity(), AdustTheCurtain.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getActivity(), Monitoring.class);
                        startActivity(intent4);
                        break;
                }
            }
        });

    }



}
