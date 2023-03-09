package com.example.smarthome.Page_Huiju;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Samrt.AdustTheCurtain;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.AddHomeAdapter;
import com.example.smarthome.Adapter.ExtendHeadAdapter;
import com.example.smarthome.Adapter.base.CommonAdapter;
import com.example.smarthome.Helper.ExtendHeatHelper;
import com.example.smarthome.R;
import com.example.smarthome.View.CircleWelComeView;
import com.example.smarthome.View.pullextend.ExtendListHeader;
import com.example.smarthome.View.pullextend.PullExtendLayout;

import java.util.ArrayList;
import java.util.List;

public class HuijuFrament extends Fragment {
    ExtendListHeader mPullNewHeader;
    PullExtendLayout mPullExtendLayout;
    Toolbar toolbar1;
    private CircleWelComeView circleView;
    RecyclerView huiju,mListHeader;
    ExtendHeadAdapter addadapter;
    AddHomeAdapter addHomeAdapter;
    List<String> mDatas = new ArrayList<>();
    //    private ClientMQTT clientMQTT;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        clientMQTT=new ClientMQTT("light");
//        try {
//            clientMQTT.Mqtt_innit();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//      //
//        clientMQTT.publishMessagePlus(null,"0x0000","0xFF", "0x0002","0x02");
//////
        return inflater.inflate(R.layout.huiju_fragment,container,false);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();

        toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.allhome:
                        break;
                    case R.id.roomemanagement: Intent intent1=new Intent(getActivity(),AddHome.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
        huiju_recyclerView();
        initdata();


    }

    private void initview() {
        toolbar1=getActivity().findViewById(R.id.huiju_tb);
        mPullNewHeader=getActivity().findViewById(R.id.extend_header);
        mPullExtendLayout=getActivity().findViewById(R.id.pull_extend);
        mListHeader=mPullNewHeader.getRecyclerView();
        huiju=getActivity().findViewById(R.id.add_view);
        huiju.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mListHeader.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
    private void initdata() {
        mDatas.add("窗帘");
        mDatas.add("空调");
        mDatas.add("灯光");
        mDatas.add("智能门锁");
        mDatas.add("音响");
        mDatas.add(" ＋ ");
        mListHeader.setAdapter(new ExtendHeadAdapter(mDatas).setItemClickListener(new CommonAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(int position, View view) {
                Toast.makeText(getActivity(),mDatas.get(position) + " 功能待实现",Toast.LENGTH_SHORT).show();
            }
        }));

    }



    private void huiju_recyclerView() {
        huiju.setHasFixedSize(true);
        huiju.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<ExtendHeatHelper> addHomeHelpers = new ArrayList<>();
        addHomeHelpers.add(new ExtendHeatHelper(R.drawable.drawing_room, "客厅"));
        addHomeHelpers.add(new ExtendHeatHelper(R.drawable.toilet, "卫生间"));
        addHomeHelpers.add(new ExtendHeatHelper(R.drawable.bedroom, "卧室"));
        addHomeAdapter=new AddHomeAdapter(addHomeHelpers);
        huiju.setAdapter(addHomeAdapter);

    }


}