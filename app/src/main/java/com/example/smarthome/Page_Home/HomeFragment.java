package com.example.smarthome.Page_Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.Page_Huiju.ManageDevices;
import com.example.smarthome.R;
import com.example.smarthome.Scan.ScanActivity;
import com.example.smarthome.Scene.AddOrEditScene;
import com.example.smarthome.Test;

import org.litepal.LitePal;

import java.util.List;
//import com.example.smarthome.Scan.ScanActivity;

public class HomeFragment extends Fragment {
    TextView home_set_voice,home_scene,home_wifi,home_wangguan,home_set,enroll,home_test;
    private int i=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        home_set_voice=getActivity().findViewById(R.id.home_set_voice);
        home_scene=getActivity().findViewById(R.id.home_scene);
        home_wifi=getActivity().findViewById(R.id.home_wife);
        home_wangguan=getActivity().findViewById(R.id.home_wangguan);
        home_set=getActivity().findViewById(R.id.home_set);
        enroll=getActivity().findViewById(R.id.enroll);
        home_test=getActivity().findViewById(R.id.home_test);
        home_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getActivity(), FindDevices.class);
                startActivity(intent1);
            }
        });
        home_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1=new Intent(getActivity(), HomeScene.class);

                Intent intent1=new Intent(getActivity(), AddOrEditScene.class);
//                Intent intent1=new Intent(getActivity(),SetVoice.class);
                List<Temp> tempList=LitePal.findAll(Temp.class);

                if(!tempList.isEmpty())//如果暂存数据库不为空，就遍历清空Scene,device.....中与temp有关的数据
                {
                    for(Temp temp:tempList){
                        String temp_id=String.valueOf(tempList.get(i).getId());
                        LitePal.deleteAll(C_Time.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(S_Device.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(Scene.class,"temp_id=?",temp_id);
                        i++;
                    }
                }
                //最后记得删除t所有temp
                LitePal.deleteAll(Temp.class);
                startActivity(intent1);
            }
        });
        home_set_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), ManageDevices.class);
                startActivity(intent1);
            }
        });
        home_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), Set.class);
                startActivity(intent1);
            }
        });
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), enter.class);
                startActivity(intent1);
            }
        });

        home_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), ManageDevices.class);
                startActivity(intent1);
            }
        });
        home_wangguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), ScanActivity.class);
                startActivity(intent1);
            }
        });


    }

}
