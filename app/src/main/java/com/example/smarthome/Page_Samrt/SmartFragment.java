package com.example.smarthome.Page_Samrt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.AddModelAdapter2;
import com.example.smarthome.Adapter.AddSmartAdapter;
import com.example.smarthome.Adapter.ManageAdaptor;
import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.Helper.AddSmartHelper;
import com.example.smarthome.Page_Huiju.ManageDevices;
import com.example.smarthome.R;
import com.example.smarthome.Scene.More;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartFragment extends Fragment{
    private List<Map<String,String>> deviceList;
    private List<Device> devicelist;
    String name_m;
    RecyclerView addsmart,addmedel;
    AddSmartAdapter rvadapter;
    AddModelAdapter2 addModelAdapter2;
    private int i=0;
//    AddModel addModel=new AddModel(addmedel);
    ImageView add;
    List<AddModel> list = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smart_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        addsmart=getActivity().findViewById(R.id.add_smart);
        addmedel=getActivity().findViewById(R.id.add_medal);
        super.onActivityCreated(savedInstanceState);
        add=getActivity().findViewById(R.id.add_home);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), More.class);
                List<Temp> tempList=LitePal.findAll(Temp.class);

                if(!tempList.isEmpty())//如果暂存数据库不为空，就遍历清空Scene,device.....中与temp有关的数据
                {
                    for(Temp temp:tempList){
                        String temp_id=String.valueOf(tempList.get(i).getId());
                        LitePal.deleteAll(C_Time.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(S_Device.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(Scene.class,"temp_id=?",temp_id);
                        LitePal.deleteAll(Condition.class,"temp_id=?",temp_id);
                        i++;
                    }
                }
                //最后记得删除t所有temp
                LitePal.deleteAll(Temp.class);
                Temp temp=new Temp();
                temp.save();
                   startActivity(intent3);
            }
        });
        initRecyclerViewOnline();
//        recyclerView();
////        recyclerView2();
//        recyclerView3();
    }

    private void recyclerView3() {
        addmedel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        List<AddModel> all = LitePal.findAll(AddModel.class);
        addModelAdapter2= new AddModelAdapter2(all);
        addmedel.setAdapter(addModelAdapter2);

//        addModelAdapter2.set0nItemClickListener((view, position) -> {
////            currnentPlayPosition = position;
//            AddMedalHelper addModel = addMedalHelpers.get(position);
//
//        });
    }


    //private void recyclerView2() {
//    addmedel.setHasFixedSize(true);
//    addmedel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//    ArrayList<AddMedalHelper> addMedalHelpers = new ArrayList<>();
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.leave_home, "离家模式"));
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.back_home, "回家模式"));
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.night, "夜间模式"));
//    addMedalHelpers .add(new AddMedalHelper(R.drawable.more, "更多模式"));
//    medalAdapter = new AddMedalAdapter(addMedalHelpers);
//    addmedel.setAdapter(medalAdapter);
//    medalAdapter.setOnItemLongClickListener(new AddMedalAdapter.OnItemLongClickListener() {
//        @Override
//        public void onItemLongClick(View view, int position) {
//            switch (position) {
//                case 0:
//                    Intent intent0 = new Intent(getActivity(), GoOff.class);
//                    startActivity(intent0);
//                    break;
//                case 1:
//                    Intent intent1 = new Intent(getActivity(), GoHome.class);
//                    startActivity(intent1);
//                    break;
//                case 2:
//                    Intent intent2 = new Intent(getActivity(), Night.class);
//                    startActivity(intent2);
//                    break;
//            }
//        }
//    });
//    medalAdapter.setOnItemClickListener(new AddMedalAdapter.OnItemClickListener() {
//        @Override
//        public void OnItemClickListener(View view, int position) {
//            switch (position){
//                case 0:
//                    break;
//                case 1:
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    Intent intent3 = new Intent(getActivity(), More.class);
//                    startActivity(intent3);
//                    break;
//            }
//        }
//    });
//
//
//}
    private void recyclerView() {
        addsmart.setHasFixedSize(true);
        addsmart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //ManageDEVICES.CLASS

        ArrayList<AddSmartHelper> addSmartHelpers = new ArrayList<>();
        addSmartHelpers.add(new AddSmartHelper(R.drawable.lights_smart, "灯光"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.air_condition_smart, "空调"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.curtain_smart, "窗帘"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.little_mentor, "门锁"));
        addSmartHelpers.add(new AddSmartHelper(R.drawable.music_smart,"音响"));
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
                    case 4:
                        Intent intent5 = new Intent(getActivity(), AdjustTheMusic.class);
                        startActivity(intent5);///Fest文件添加
                        break;
                }
            }
        });

    }
    private void initRecyclerViewOnline(){
        initContent();
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        addsmart.setLayoutManager(linearLayout);
        ManageAdaptor manageAdaptor = new ManageAdaptor(deviceList);
        manageAdaptor.InputFlag(1);
        addsmart.setAdapter(manageAdaptor);
        manageAdaptor.notifyDataSetChanged();
    }
    private void initContent()
    {
        deviceList.clear();
        devicelist= LitePal.order("device_type desc").where("flag= ? and network_flag = ?","1","1").find(Device.class);
        for(Device devices:devicelist) {
            int count=0;
            String target_long_address = devices.getTarget_long_address();
            int flag = devices.getFlag();
            String source_command = devices.getDevice_type();
            String target_short_adress=devices.getTarget_short_address();
            String network_flag=devices.getNetwork_flag();
            String controller_long_address=devices.getController_long_address();
            Map<String, String> map = new HashMap<>();
            map.put("device_type", source_command);
            map.put("network_flag",network_flag);
            map.put("target_short_address",target_short_adress);
            map.put("target_long_address", target_long_address);
            map.put("flag", String.valueOf(flag));
            map.put("controller_long_address",controller_long_address);
            for(Map<String,String> map2:deviceList) {
                String target_long_address2=map2.get("target_long_address");
                if(target_long_address2.equals(target_long_address))
                    count++;
            }
            if(count==0)
                deviceList.add(map);

        }
    }
    }
