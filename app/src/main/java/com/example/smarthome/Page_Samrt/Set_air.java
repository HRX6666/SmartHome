package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smarthome.Adapter.AddModelAdapter2;
import com.example.smarthome.Adapter.AirListAdaptor;
import com.example.smarthome.Adapter.CurtainListAdaptor;
import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.View.CustomizeGoodsAddView;
import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Set_air extends AppCompatActivity {
    public static final String TIME="time";
    RecyclerView recyclerView;
    private AirListAdaptor airListAdaptor;
    private List<Map<String,String>> mAirList;
    private List<Integer> positionList;//储存选择的电器
    private Condition condition;
    private MaterialButton create;
    private CustomizeGoodsAddView customizeGoodsAddView;
    private ImageView wind_min;
    private ImageView wind_mid;
    private ImageView wind_max;
    private ImageView set_warm;
    private ImageView set_cold;
    private ImageView set_wind;
    private int flag=-1;//判断是否是创建新的条件
    private int min=-1;
    private int mid=-1;
    private int max=-1;
    private int warm=-1;
    private int cold=-1;
    private int wind=-1;
    private int count=-1;
    private String timeIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_air);
        create=findViewById(R.id.create_home);
        //判断是否为再次编辑
        Intent intent=getIntent();
        timeIn=intent.getStringExtra(Set_air.TIME);
        if(timeIn==null)
            flag=0;//新的
        else
            flag=1;
        clickListenerInit();
        recyclerView();

    }

    private void recyclerView() {
        recyclerView=findViewById(R.id.select_air);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        airListAdaptor=new AirListAdaptor(mAirList);
        airListAdaptor.setOnItemClickListener(new AirListAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for(int i=0;i<positionList.size();i++){
                    if(positionList.get(i)==position){
                        count=i;
                        positionList.remove(i);
                    }
                }
                if(count==-1)
                {
                    positionList.add(position);//选择多项设备通过字符串储存选择的位置，那要是选择两遍呢?遍历，有就删除，没有就添加

                }


            }
        });
        recyclerView.setAdapter(airListAdaptor);
        airListAdaptor.notifyDataSetChanged();
    }


    private void clickListenerInit(){
        customizeGoodsAddView=findViewById(R.id.select_temp);
        customizeGoodsAddView.setMaxValue(30);
        customizeGoodsAddView.setMinValue(16);
//        customizeGoodsAddView.setValue(temperature);//用来设置初始温度
        customizeGoodsAddView.setValue(16);
        customizeGoodsAddView.setOnValueChangeListene(new CustomizeGoodsAddView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value) {
                if(value>30){
                    customizeGoodsAddView.setValue(30);

                }else if(value<16){
                    customizeGoodsAddView.setValue(16);
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                if(flag==0)   //flag为0就新建Condition
                {
                    Temp temp=LitePal.findLast(Temp.class);
                    condition=new Condition();
                    condition.setTime(time);
                    condition.setTemp(temp);
                    condition.save();

                }else
                {
                    List<Condition> conditionList=LitePal.where("time = ?",Set_air.TIME).find(Condition.class);
                    condition=conditionList.get(0);
                }
                if(positionList!=null){
                    if(mid==-1&&min==-1&&max==-1)
                        Toast.makeText(Set_air.this,"请选择风速",Toast.LENGTH_SHORT).show();
                    else {
                        if(cold==-1&&warm==-1&&wind==-1){
                            Toast.makeText(Set_air.this,"请选择模式",Toast.LENGTH_SHORT).show();
                        }else {
                            if(!positionList.isEmpty())
                            {//如果没有设备就“请添加执行的设备”
                                //好像忘了temp.setCondition
                                Temp temp= LitePal.findLast(Temp.class);
                                for (int i = 0; i < positionList.size(); i++) {
                                    int n=positionList.get(i);
                                    String target_long_address=mAirList.get(n).get("target_long_address");
                                    S_Device s_device=new S_Device();
                                    if(warm!=-1)
                                        s_device.setAir_model("1");
                                    if(cold!=-1)
                                        s_device.setAir_model("2");
                                    if(wind!=-1)
                                        s_device.setAir_model("3");
                                    if(min!=-1)
                                        s_device.setWind("1");
                                    if(mid!=-1)
                                        s_device.setWind("2");
                                    if(max!=-1)
                                        s_device.setAir_model("3");
                                    if(flag==0)
                                    {

                                        s_device.setCondition(condition);
                                        s_device.save();
                                    }
                                    else
                                        s_device.updateAll("target_long_address = ?",target_long_address);
                                    finish();
                                }
                            }else
                                Toast.makeText(Set_air.this,"请选择电器！",Toast.LENGTH_SHORT).show();
                        }
                    }

                }else
                    Toast.makeText(Set_air.this,"请添加电器！",Toast.LENGTH_SHORT).show();
            }
        });
        wind_min=findViewById(R.id.ib_wind_min);
        wind_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(mid==-1&&max==1)
                    min=1;
                else
                {
                    min=1;
                    max=-1;
                    mid=-1;
                }

            }
        });
        wind_mid=findViewById(R.id.ib_wind_mid);
        wind_mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(min==-1&&max==1)
                    mid=1;
                else
                {
                    mid=1;
                    max=-1;
                    min=-1;
                }
            }
        });
        wind_max=findViewById(R.id.ib_wind_max);
        wind_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(mid==-1&&min==1)
                    max=1;
                else
                {
                    max=1;
                    min=-1;
                    mid=-1;
                }
            }
        });
        set_warm=findViewById(R.id.ib_set_warm);
        set_warm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(cold==-1&&wind==1)
                    warm=1;
                else
                {
                    warm=1;
                    cold=-1;
                    wind=-1;
                }
            }
        });
        set_cold=findViewById(R.id.ib_set_clod);
        set_cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(warm==-1&&wind==1)
                    cold=1;
                else
                {
                    cold=1;
                    warm=-1;
                    wind=-1;
                }
            }
        });
        set_wind=findViewById(R.id.ib_set_wind);
        set_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add your code here
                if(cold==-1&&warm==1)
                    wind=1;
                else
                {
                    wind=1;
                    cold=-1;
                    warm=-1;
                }
            }
        });
    }

}