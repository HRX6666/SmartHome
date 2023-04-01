package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.example.smarthome.Adapter.AirListAdaptor;
import com.example.smarthome.Adapter.LightListAdaptor;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.R;
import com.example.smarthome.View.StepSeekBar;
import com.github.iielse.switchbutton.SwitchView;
import com.google.android.material.button.MaterialButton;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Set_lights extends AppCompatActivity {
    public static final String TIME="time";
    RecyclerView recyclerView;
    private Condition condition;
    private StepSeekBar brightness;
    private SwitchView light_normal;
    private SwitchView light_sleep;
    private SwitchView light_breathe;
    private SwitchView light_else;
    private MaterialButton create;
    private LightListAdaptor lightListAdaptor;
    private List<Map<String, String>> mLightList;
    private List<Integer> positionList;//储存选择的电器
    private int count = -1;
    private int bright=-1;
    //model只能选一个
    private int normal=-1;
    private int sleep=-1;
    private int breathe=-1;
    private int other=-1;
    private int flag=-1;//判断是否是创建新的条件
    private String timeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_lights);
        brightness = findViewById(R.id.brightness);
        light_normal = findViewById(R.id.light_normal);
        light_sleep = findViewById(R.id.light_sleep);
        light_breathe = findViewById(R.id.light_breathe);
        light_else = findViewById(R.id.light_else);
        create=findViewById(R.id.create_home);
        clickListenerInit();
        recyclerView();
        //判断是否为再次编辑
        Intent intent=getIntent();
        timeIn=intent.getStringExtra(Set_lights.TIME);
        if(timeIn==null)
            flag=0;//新的
        else
            flag=1;
    }

    private void recyclerView() {
        recyclerView = findViewById(R.id.select_light);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lightListAdaptor = new LightListAdaptor(mLightList);
        lightListAdaptor.setOnItemClickListener(new LightListAdaptor.OnItemClickListener() {
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
        recyclerView.setAdapter(lightListAdaptor);
        lightListAdaptor.notifyDataSetChanged();
    }

    private void clickListenerInit()    {
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String time = simpleDateFormat.format(date);
                if (flag == 0)   //flag为0就新建Condition
                {
                    Temp temp = LitePal.findLast(Temp.class);
                    condition = new Condition();
                    condition.setTime(time);
                    condition.setTemp(temp);
                    condition.save();

                } else {
                    List<Condition> conditionList = LitePal.where("time = ?", Set_curtain.TIME).find(Condition.class);
                    condition = conditionList.get(0);
                }
                if (!positionList.isEmpty()) {
                    Temp temp = LitePal.findLast(Temp.class);
                    for (int i = 0; i < positionList.size(); i++) {
                        int n = positionList.get(i);
                        String target_long_address = mLightList.get(n).get("target_long_address");
                        S_Device s_device = new S_Device();
                        if (normal != -1)
                            s_device.setLight_model("1");
                        else if (sleep != -1)
                            s_device.setLight_model("1");
                        if (breathe != -1)
                            s_device.setLight_model("1");
                        if (other != -1)
                            s_device.setLight_model("1");
                        s_device.setTarget_long_address(target_long_address);
                        s_device.setCategory("1");
                        if (flag == 0) {
                            s_device.setCondition(condition);
                            s_device.save();
                        } else
                            s_device.updateAll("target_long_address = ?", target_long_address);
                        finish();
                    }

                    //不为0就通过时间搜索
                }
            }
        });


    brightness.setOnCursorChangeListener(new StepSeekBar.OnCursorChangeListener() {

            @Override
            public void onCursorChanged(int location, String textMark) {
            bright=location;
            //只是保存信息，不发送
            }
        });
        light_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(normal==-1)
            {
                normal=1;
                sleep=-1;
                breathe=-1;
                other=-1;
                light_sleep.setOpened(false);//应该是让
                light_breathe.setOpened(false);
                light_else.setOpened(false);
            }

            else
                normal=-1;
            }
        });
        light_breathe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(breathe==-1)
                {
                    breathe=1;
                    normal=1;
                    sleep=-1;
                    other=-1;
                    light_sleep.setOpened(false);//应该是让
                    light_normal.setOpened(false);
                    light_else.setOpened(false);
                }

                else
                    breathe=-1;
            }
        });
        light_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sleep==-1)
                {
                    normal=1;
                    breathe=-1;
                    other=-1;
                    sleep=1;
                    light_breathe.setOpened(false);//应该是让
                    light_normal.setOpened(false);
                    light_else.setOpened(false);
                }

                else
                    sleep=-1;
            }
        });
        light_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(other==-1)
                {
                    normal=1;
                    sleep=-1;
                    breathe=-1;
                    other=1;
                    light_breathe.setOpened(false);//应该是让
                    light_normal.setOpened(false);
                    light_sleep.setOpened(false);
                }

                else
                    other=-1;
            }
        });



    }
}