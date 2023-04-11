package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.View.StepSeekBar;
import com.example.smarthome.View.SwapTb.SortViewPagerAdapter;
import com.example.smarthome.View.SwapTb.SpringView;
import com.example.smarthome.View.SwapTb.ViewPagerIndicator;
import com.github.iielse.switchbutton.SwitchView;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.ArrayList;
import java.util.List;


public class AdjustTheLights extends AppCompatActivity {
    public static final String CONTROLLER_LONG_ADDRESS = "controller_long_address";
    public static final String TARGET_LONG_ADDRESS = "target_long_address";
    public static final String TARGET_SHORT_ADDRESS = "target_short_address";
    public static final String NETWORK_FLAG = "network_flag";
    public static final String DEVICE_TYPE = "device_type";
    Toolbar lights_tb;
    private    ClientMQTT clientMQTT;
    private Button open_light;
    private Button shut_light;
    private Spinner spinner_model;
    private Spinner spinner_home;
    private String target_short_address;
    private StepSeekBar stepSeekBar;
    private ViewPager viewPager;
    private ViewPagerIndicator indicator;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mList;
    private List<String> mDatas;
    private int itemCount = 4;
    SwitchView light_breathe;
    SpringView springView;


    View view1;
    View view2;
    View view3;
    View view4;

    int currentPosition = 0;
    int endPosition;
    int beginPosition;

    TextView[] tvs = new TextView[4];
    private TextView tab_new, tab_hot, tab_free, tab_member;
    private float item_width;
    private int screenWidth;

    int indicatorColorId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_the_lights);
        viewPager = (ViewPager) findViewById(R.id.vp);

        LayoutInflater inflater = LayoutInflater.from(this);
        view1 = inflater.inflate(R.layout.vp1, null);
        light_breathe=view1.findViewById(R.id.v_switch_1);
        indicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        light_breathe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view1.getContext(),"点击",Toast.LENGTH_SHORT).show();
                Toast.makeText(AdjustTheLights.this,"点击",Toast.LENGTH_SHORT).show();
            }
        });
        light_breathe.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                Toast.makeText(view1.getContext(),"点击",Toast.LENGTH_SHORT).show();
                Toast.makeText(AdjustTheLights.this,"点击",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void toggleToOff(SwitchView view) {

            }
        });
        mList = new ArrayList<Fragment>();
        for (int i = 0; i < itemCount; i++) {
            Fragment fragment = new MeFragment();
            mList.add(fragment);
        }

        mDatas = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            mDatas.add("灯" + i);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };

        viewPager.setAdapter(mAdapter);
        //将viewpager与indicator绑定
        indicator.setDatas(mDatas);
        indicator.setViewPager(viewPager);
//        initattrs();
//        init();

//        lights_tb=findViewById(R.id.lights_tb);
//        open_light=findViewById(R.id.trail_open);
//        shut_light=findViewById(R.id.trail_shut);
//        spinner_model=findViewById(R.id.lights_choose_model);
//        spinner_home=findViewById(R.id.lights_choose_home);
//        stepSeekBar=(StepSeekBar)findViewById(R.id.brightness);
//        lights_tb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        initmodel();
//        inithome();
//        clientMQTT=new ClientMQTT("light");
//        try {
//            clientMQTT.Mqtt_innit();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//        clientMQTT.startReconnect(AdjustTheLights.this);
//        stepSeekBar.setOnCursorChangeListener(new StepSeekBar.OnCursorChangeListener() {
//            @Override//游标监听事件
//            public void onCursorChanged(int location, String textMark) {
//
//            }
//        });
//        seekBar_bright.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
////                clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4A69","0x01", "0x01");
////                Toast.makeText(AdjustTheLights.this, , Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(AdjustTheLights.this, "start!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(AdjustTheLights.this, "finish!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        open_light.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                clientMQTT.startReconnect(AdjustTheLights.this);
//                clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x01", "0x0101");
//                Toast.makeText(AdjustTheLights.this, "开灯!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        shut_light.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                clientMQTT.startReconnect(AdjustTheLights.this);
//                clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x01", "0x0100");
////                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
////                LocalDateTime localDateTime = LocalDateTime.now();
////                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
////                String format = DateUtil.format(date, df);
////                System.err.println(format);
//
//                Toast.makeText(AdjustTheLights.this, "关灯!", Toast.LENGTH_SHORT).show();
//            }
//        });

//        spinner_model.setPopupBackgroundResource(R.drawable.bg);//下拉背景,可以优化一下

//        spinner_home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                String[] spinner_arr= getResources().getStringArray(R.array.choose_lights_model);
////                String choose_model=spinner_arr[position];
//                //获得目标地址
//                switch (position){
//                    case 1:;
//                    case 2:;
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        spinner_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                String[] spinner_arr= getResources().getStringArray(R.array.choose_lights_model);
//                String choose_model=spinner_arr[position];
////                Toast.makeText(AdjustTheLights.this, choose_model, Toast.LENGTH_SHORT).show();
////                Toast.makeText(AdjustTheLights.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
//                //灯控模式
//                switch (position){//不同房间类型和全屋进入应该是有不同短地址来区分，通过判断选择哪个屋子执行，在根据此来发送不同的短地址
//                    case 1:clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x01", "0x0105");
//                    case 2:clientMQTT.publishMessagePlus("2023-02-19T08:30:00Z","1.2.3",null,"0x4AA5","0x01", "0x0106");;
//
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

//    private void initmodel() {
//        ArrayAdapter starAdapter_model= ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_lights_model, android.R.layout.simple_spinner_item);
//        Spinner sp_dropdown=findViewById(R.id.lights_choose_model);
//        starAdapter_model.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_dropdown.setPrompt("请选择模式");
//        sp_dropdown.setAdapter(starAdapter_model);
//        sp_dropdown.setSelection(0);
//        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

//    private void inithome() {
//        ArrayAdapter starAdapter_home=ArrayAdapter.createFromResource(getApplicationContext(),R.array.choose_home, android.R.layout.simple_spinner_item);
//        Spinner sp_dropdown=findViewById(R.id.lights_choose_home);
//        sp_dropdown.setPrompt("请选择房间");
//        sp_dropdown.setAdapter(starAdapter_home);
//        sp_dropdown.setSelection(0);
//        sp_dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }
//private void init() {
//    if (indicatorColorsId != 0) {
//        indicatorColorArray = getResources().getIntArray(indicatorColorsId);
//    }
//    LayoutInflater inflater = LayoutInflater.from(this);
//    view1 = inflater.inflate(R.layout.vp1, null);
//    view2 = inflater.inflate(R.layout.vp1, null);
//    view3 = inflater.inflate(R.layout.vp1, null);
//    view4 = inflater.inflate(R.layout.vp1, null);
//    springView = (SpringView) findViewById(R.id.sot_springview);
//    springView.setIndicatorColor(getResources().getColor(indicatorColorId));
//    viewPager = (ViewPager) findViewById(R.id.vPager);
//    List<View> views = new ArrayList<View>();
//    views.add(view1);
//    views.add(view2);
//    views.add(view3);
//    views.add(view4);
//    viewPager.setOnPageChangeListener((ViewPager.OnPageChangeListener) this);
//    viewPager.setAdapter(new SortViewPagerAdapter(views));
//    tab_hot = (TextView) findViewById(R.id.tv_tab_1);
//    tab_new = (TextView) findViewById(R.id.tv_tab_2);
//    tab_free = (TextView) findViewById(R.id.tv_tab_3);
//    tab_member = (TextView) findViewById(R.id.tv_tab_4);
//    tvs[0] = tab_hot;
//    tvs[1] = tab_new;
//    tvs[2] = tab_free;
//    tvs[3] = tab_member;
//    tab_hot.setOnClickListener((View.OnClickListener) this);
//    tab_new.setOnClickListener((View.OnClickListener) this);
//    tab_free.setOnClickListener((View.OnClickListener) this);
//    tab_member.setOnClickListener((View.OnClickListener) this);
//    screenWidth = getResources().getDisplayMetrics().widthPixels;
//    item_width = screenWidth / 12 - 2.0f;
//    updateTextColor();
//    viewPager.setCurrentItem(0);
//    tab_hot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//        @Override
//        public void onLayoutChange(View v, int left, int top, int right,
//                                   int bottom, int oldLeft, int oldTop, int oldRight,
//                                   int oldBottom) {
//            createPoints();
//            tab_hot.removeOnLayoutChangeListener(this);
//        }
//    });
//}
//
//    private float radiusMax;
//    private float radiusMin;
//    private float radiusOffset;
//    private float acceleration = 0.5f;
//    private float headMoveOffset = 0.6f;
//    private float footMoveOffset = 1 - headMoveOffset;
//    private ViewPager.OnPageChangeListener delegateListener;
//    private int indicatorColorsId;
//    private static final int INDICATOR_ANIM_DURATION = 3000;
//    private ObjectAnimator indicatorColorAnim;
//    private int[] indicatorColorArray;
//
//    public void onPageScrollStateChanged(int arg0) {
//
//    }
//
//    public void onPageScrolled(int position, float offset, int offsetPixels) {
//        if (currentPosition == position) {
//            endPosition = (int) (item_width * currentPosition + (int) (item_width * offset));
//        }
//        if (currentPosition == position + 1) {
//            endPosition = (int) (item_width * currentPosition - (int) (item_width * (1 - offset)));
//        }
//        // Animation mAnimation = new TranslateAnimation(beginPosition,
//        // endPosition, 0, 0);
//        // mAnimation.setFillAfter(true);
//        // mAnimation.setDuration(0);
//        // buttomLine.startAnimation(mAnimation);
//        beginPosition = endPosition;
//
//        if (position < tvs.length - 1) {
//            // radius
//            float radiusOffsetHead = 0.5f;
//            if (offset < radiusOffsetHead) {
//                springView.getHeadPoint().setRadius(radiusMin);
//            } else {
//                springView.getHeadPoint().setRadius(
//                        ((offset - radiusOffsetHead) / (1 - radiusOffsetHead)
//                                * radiusOffset + radiusMin));
//            }
//            float radiusOffsetFoot = 0.5f;
//            if (offset < radiusOffsetFoot) {
//                springView.getFootPoint().setRadius(
//                        (1 - offset / radiusOffsetFoot) * radiusOffset
//                                + radiusMin);
//            } else {
//                springView.getFootPoint().setRadius(radiusMin);
//            }
//
//            // x
//            float headX = 1f;
//            if (offset < headMoveOffset) {
//                float positionOffsetTemp = offset / headMoveOffset;
//                headX = (float) ((Math.atan(positionOffsetTemp * acceleration
//                        * 2 - acceleration) + (Math.atan(acceleration))) / (2 * (Math
//                        .atan(acceleration))));
//            }
//            springView.getHeadPoint().setX(
//                    getTabX(position) - headX * getPositionDistance(position));
//            float footX = 0f;
//            if (offset > footMoveOffset) {
//                float positionOffsetTemp = (offset - footMoveOffset)
//                        / (1 - footMoveOffset);
//                footX = (float) ((Math.atan(positionOffsetTemp * acceleration
//                        * 2 - acceleration) + (Math.atan(acceleration))) / (2 * (Math
//                        .atan(acceleration))));
//            }
//            springView.getFootPoint().setX(
//                    getTabX(position) - footX * getPositionDistance(position));
//
//            // reset radius
//            if (offset == 0) {
//                springView.getHeadPoint().setRadius(radiusMax);
//                springView.getFootPoint().setRadius(radiusMax);
//            }
//        } else {
//            springView.getHeadPoint().setX(getTabX(position));
//            springView.getFootPoint().setX(getTabX(position));
//            springView.getHeadPoint().setRadius(radiusMax);
//            springView.getFootPoint().setRadius(radiusMax);
//        }
//
//        if (indicatorColorsId != 0) {
//            float length = (position + offset)
//                    / viewPager.getAdapter().getCount();
//            int progress = (int) (length * INDICATOR_ANIM_DURATION);
//            seek(progress);
//        }
//
//        springView.postInvalidate();
//        if (delegateListener != null) {
//            delegateListener.onPageScrolled(position, offset, offsetPixels);
//        }
//    }
//
//    public void onPageSelected(int position) {
//        currentPosition = position;
//        beginPosition = (int) (position * item_width);
//        updateTextColor();
//    }
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.tv_tab_1:
//
//                currentPosition = 0;
//                break;
//            case R.id.tv_tab_2:
//
//                currentPosition = 1;
//                break;
//            case R.id.tv_tab_3:
//                currentPosition = 2;
//
//                break;
//            case R.id.tv_tab_4:
//                currentPosition = 3;
//
//                break;
//
//        }
//
//        updateTextColor();
//        viewPager.setCurrentItem(currentPosition);
//    }
//
//    public void updateTextColor() {
//        for (int i = 0; i < tvs.length; i++) {
//
//            if (i == currentPosition) {
//                tvs[i].setTextColor(Color.WHITE);
//            } else {
//                tvs[i].setTextColor(Color.BLACK);
//            }
//        }
//
//    }
//
//    private void createPoints() {
//        View view = tvs[viewPager.getCurrentItem()];
//        springView.getHeadPoint().setX(view.getX() + view.getWidth() / 2);
//        springView.getHeadPoint().setY(view.getY() + view.getHeight() / 2);
//        springView.getFootPoint().setX(view.getX() + view.getWidth() / 2);
//        springView.getFootPoint().setY(view.getY() + view.getHeight() / 2);
//        springView.animCreate();
//    }
//
//    public float getTabX(int position) {
//        return tvs[position].getX() + tvs[position].getWidth() / 2;
//    }
//
//    public float getPositionDistance(int position) {
//        // TODO Auto-generated method stub
//        float tarX = tvs[position + 1].getX();
//        float oriX = tvs[position].getX();
//        return oriX - tarX;
//    }
//
//    public void seek(int seekTime) {
//        // TODO Auto-generated method stub
//        if (indicatorColorAnim == null) {
//            createIndicatorColorAnim();
//        }
//        indicatorColorAnim.setCurrentPlayTime(seekTime);
//    }
//
//    private void createIndicatorColorAnim() {
//        indicatorColorAnim = ObjectAnimator.ofInt(springView, "indicatorColor",
//                indicatorColorArray);
//        indicatorColorAnim.setEvaluator(new ArgbEvaluator());
//        indicatorColorAnim.setDuration(INDICATOR_ANIM_DURATION);
//    }
//
//    private void initattrs() {
//        // TODO Auto-generated method stub
//        indicatorColorId = R.color.foot_text_press;
//        radiusMax = getResources().getDimension(R.dimen.si_default_radius_max);
//        radiusMin = getResources().getDimension(R.dimen.si_default_radius_min);
//        if (indicatorColorsId != 0) {
//            indicatorColorArray = getResources().getIntArray(indicatorColorsId);
//        }
//        radiusOffset = radiusMax - radiusMin;
//    }

}