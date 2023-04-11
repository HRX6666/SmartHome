package com.example.smarthome.Scene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.smarthome.Activity.BottomSmartHome;
import com.example.smarthome.Activity.FirstActivity;
import com.example.smarthome.Adapter.ConAndMissAdaptor;
import com.example.smarthome.Adapter.SceneAdaptor;
import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.Scene.C_Time;
import com.example.smarthome.Database.Scene.Condition;
import com.example.smarthome.Database.Scene.Mission;
import com.example.smarthome.Database.Scene.S_Device;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Scene.Temp;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Samrt.Tesk;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class More extends AppCompatActivity {
    public static final String CONTROLLER_LONG_ADDRESS="70E46125004B1200";
    RelativeLayout select_condition,select_tesk;
    Button create;
    EditText model_name;
    String name_ml;
    private String  id;
    private List<C_Time> c_timeList=new ArrayList<>();
    private  List<Scene> sceneList=new ArrayList<>();
    private List<Map<String,String>> conditionList=new ArrayList<>();
    private List<Map<String,String>> missionList=new ArrayList<>();
    private List<Condition> mConditionList=new ArrayList<>();
    private List<Mission> mMissionList=new ArrayList<>();
//TODO 完成条件和任务的recyclerView的初始化
    //TODO 还有smartFragment的device显示
    //TODO 时间发送指令那个，只能发送一个捏
    private JSONObject registerInfo;//注册返回信息
    private RecyclerView recy_condition,recy_mission;
    private ClientMQTT clientMQTT;
    private String hexTime;
    private String time;
    private String validData;
    private String innerData;//内嵌有效数据
    private String innerDataLength;//内嵌有效数据长度
    private String  validDataLength;//有效数据长度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        //
        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(More.this);
        init();
        /*
        1.进入后先遍历删除所有Temp数据，之后再创建一个新的temp来储存
        findFirst来找到当前temp
        2.智能设备作为条件可以先弄灯的，空调那些可以先不显示
        灯的就开关嘛
        3.编写适配器，暂时只显示名称图标和开关
        4.还有电器的自定义命名
         */
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        List<Scene> sceneList=new ArrayList<>();
        if(id!=null)
            sceneList=LitePal.where("id = ?",id).find(Scene.class);
        if(sceneList.isEmpty())
            time=null;
        else{
            time=sceneList.get(0).getTime();
            model_name.setText(sceneList.get(0).getName());//TODO 看看显示没有
        }
        //TODO 这个界面记得RecyclerView，要检测传进来的时间是否为空，为空就查询场景，初始化recyclerView,记得通过Adaptor的inputTime传入时间

        //TODO 看看能不能显示出来
        initRecyclerViewCon();
        initRecyclerViewMiss();


    }
    //@TODO FindDevices好像由于刷新太快，导致用户向下滑动到一半就一朝回到解放前，又到了最顶部
//    private void initContentList(){
//        //时间为空就查询temp
//        int temp_id;
//        if(time==null){
//            Temp temp=LitePal.findLast(Temp.class);
//            if(temp!=null){
//                temp_id=temp.getId();
//                List<Condition> conditionList1=LitePal.where("temp_id = ?",temp_id+"").find(Condition.class);
//
//            }
//
//
//        }
//
//
//    }
    private void init() {
        select_condition =findViewById(R.id.select_condition);
        select_tesk=findViewById(R.id.select_tesk);
        create=findViewById(R.id.create);
        model_name=findViewById(R.id.model_name);
        recy_mission=findViewById(R.id.recy_mission);
        recy_condition=findViewById(R.id.recy_condition);

        select_tesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this, MissionActivity.class);
                startActivity(intent);
            }
        });
        select_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(More.this, ConditionActivity.class);
                startActivity(intent1);

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                String time1=simpleDateFormat.format(date);
                name_ml=model_name.getText().toString();    //获取输入框值
                //判断输入框中是否有值
                if(TextUtils.isEmpty(name_ml)){
                    Toast.makeText(More.this,"请输入自定义场景名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(time==null){
                    Temp temp=LitePal.findFirst(Temp.class);
                    temp.setTime(time1);
                    Scene scene=new Scene();
                    scene.setTime(time1);
                    scene.setIsClick(temp.getIsClick());
                    if(!temp.getConditionList().isEmpty()){
                        scene.setConditionList(temp.getConditionList());
                    }
                    if(!temp.getMissionList().isEmpty())
                        scene.setMissionList(temp.getMissionList());
                    List<S_Device> s_deviceList=new ArrayList<>();
                    s_deviceList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(S_Device.class);
                    List<Condition> conditionList=new ArrayList<>();
                    List<Mission> missionList=new ArrayList<>();
                    conditionList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(Condition.class);
                    missionList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(Mission.class);
                    if(!conditionList.isEmpty())
                        c_timeList=LitePal.where("condition_id = ?",String.valueOf(conditionList.get(0).getId())).find(C_Time.class);
//                    for(C_Time c_time:c_timeList){
//
//                    }

                    for(Condition condition:temp.getConditionList()){
                        Condition condition1=new Condition();
                        condition1=condition;
                        conditionList.add(condition1);
                        condition1.setScene(scene);
                        condition1.save();
                    }
                    for(Mission mission:temp.getMissionList()){
                        Mission mission1=mission;
                        missionList.add(mission1);
                        mission1.setScene(scene);
                        mission1.save();

                    }

                    TransferDataFromTempToScene(scene,temp);
                    //创建场景
                    String input = name_ml; // 中文输入

                     //将时间转化为16进制
                    if(!c_timeList.isEmpty()){
                        String transferTime=c_timeList.get(0).getTime();
                        hexTime=TimePointToHEX(transferTime);
                    }

                     //将字符转化为16进制
                    String name=NameToHEX(input);
                    /**
                     * @description
                     * 指令
                     */
                    //场景名长度

                    validData=getValidData("01",name,innerData);//00是内嵌数据长度
                    //创建场景
                    validDataLength=getValidDataLength(validData);
                    clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,"0x"+validDataLength+"");
                    if(hexTime!=null){
                        innerData=getInnerDataForTime(More.CONTROLLER_LONG_ADDRESS,"00",hexTime);
                        validData=getValidData("03",name,innerData);
                        //发送时间点条件
                        validDataLength=getValidDataLength(validData);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,"0x"+validDataLength);
                                finish();
                            }
                        },100);
                    }
                    //发送设备执行任务
                    if(!s_deviceList.isEmpty()){
                        for(S_Device s_device:s_deviceList){
                            String innerValidData=null;//内嵌有效数据数据
                            String lightModel=s_device.getLight_model();
                            int brightness=-1;
                            brightness=s_device.getBrightness();
                            String target_short_address=s_device.getTarget_short_address();
                            if(brightness==-1){
                            }else {
                                for(int i=0;i<s_device.getLightList().size();i++){
                                    int light=s_device.getLightList().get(i);
                                    innerValidData="00"+"0"+light+"0"+brightness;
                                    innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                    validData=getValidData("05",name,innerData);
                                    validDataLength=getValidDataLength(validData);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                                            finish();
                                        }
                                    },100);


                                }
                            }
                            //FIXME  这玩意不知道有用没
                            if(s_device.getLight_model()!=null)
                                if(s_device.getLight_model().equals("3")){//呼吸灯指令
                                innerValidData="00"+"06";
                                innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                validData=getValidData("05",name,innerData);
                                validDataLength=getValidDataLength(validData);
                                new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                                            finish();
                                        }
                                    },100);


                            }


                        }

                    }

                    //TODO S_Device里面的判断是执行还是条件的还没赋值
                }
                //TODO 别忘了还有指令发送
                //TODO 考虑到直接通过get再set可能直接set了个null，还是直接new新的
                finish();
            }
        });
    }

    /**
     * @description 生成有效数据
     * @param operation 场景操作选择位
     * @param name      场景名(16进制)
     * @param innerData 内嵌有效数据数据
     * @return validData 有效数据内容
     */
    private String getValidData(String operation,String name,String innerData){
        String validData;
        String nameLength;
        String innerDataLength;
        nameLength=Integer.toHexString(name.length()/2);
        if(nameLength.length()==1)
            nameLength="0"+nameLength;
        if(innerData==null)
            innerDataLength="00";
        else
            innerDataLength=Integer.toHexString(innerData.length()/2);
        if(innerDataLength.length()==1)
            innerDataLength="0"+innerDataLength;
        if(innerData!=null)
            return validData="0x"+operation+nameLength+name+innerDataLength+innerData;
        else
            return validData="0x"+operation+nameLength+name+innerDataLength;

    }

    /**
     * @description 获得有效数据长度,有"0x"的那种偶
     * @param validData 有效数据
     * @return 有效数据长度
     */
    private String getValidDataLength(String validData){
        String validDataLength=Integer.toHexString(validData.length()/2-1);
        if(validDataLength.length()==1)
            validDataLength="0"+validDataLength;
       return validDataLength;
    }

    /**
     * @description 获得内嵌有效数据数据
     * @return 内嵌有效数据数据
     */
    private String getInnerDataForSensor(){
        String innerData="";
        return innerData;
    }

    /**
     *
     * @param controller_long_address
     * @param judge 时间点： 00 时间段： 01
     * @param time 16进制的时间
     * @return 时间点的内嵌有效数据
     */
    private String getInnerDataForTime(String controller_long_address,String judge,String time){
        String innerData=controller_long_address+judge+time;
        return innerData;
    }
    private String getInnerDataForTime(String controller_long_address,String judge,String timeStart,String timeEnd){
        String innerData="";

        return innerData;
    }

    /**
     *
     * @param target_short_address
     * @param device_type
     * @param innerValidData
     * @return 内嵌有效数据数据
     */
    private String getInnerDataForCommand(String target_short_address,String device_type,String innerValidData){
        String innerValidDataLength=Integer.toHexString(innerValidData.length()/2);
        String innerData=target_short_address+device_type+innerValidDataLength+innerValidData;
        return innerData;
    }
    /**
     * @description
     * 将时间转化为16进制
     */
    private String TimePointToHEX(String time){

        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        int timeInMinutes = hour * 60 + minute;
        String hexString = Integer.toHexString(timeInMinutes);
        if (hexString.length() == 3) {
            hexString = "0" + hexString;
        }
        String HEXTime=hexString.toUpperCase();
        return HEXTime;
    }
    /**
     * @description
     * 将字符转化为16进制
     */
    private String NameToHEX(String input){
        // 将中文字符串转换为字节数组
        byte[] bytes = input.getBytes();
        // 将字节数组转换为16进制字符串
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        // 输出16进制字符串
        String name=hex.toString();
        return name;
    }
    private void TransferDataFromTempToScene(Scene scene,Temp temp){
        List<C_Time> c_timeList=new ArrayList<>();//多个时间点
        List<Condition> conditionList=new ArrayList<>();//条件
        List<Mission> missionList=new ArrayList<>();//任务
        List<S_Device> s_deviceList=new ArrayList<>();
        String isClick;
        List<C_Time> c_timeList1=new ArrayList<>();//多个时间点
        List<Condition> conditionList1=new ArrayList<>();//条件
        List<Mission> missionList1=new ArrayList<>();//任务
        List<S_Device> s_deviceList1=new ArrayList<>();

        isClick=temp.getIsClick();
        //TODO 每一个c_time还是要通过condition来寻找，因为recycler展示的就是conditionList不是c_timeList，传入的参数有scene_id和创建时间
        //TODO 展示的列表用Map，毕竟也就显示一个标题，
        c_timeList1=LitePal.where("temp_id = ?",temp.getId()+"").find(C_Time.class);
        conditionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Condition.class);
        s_deviceList1=LitePal.where("temp_id = ?",temp.getId()+"").find(S_Device.class);
        missionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Mission.class);
        c_timeList=c_timeList1;
        conditionList=conditionList1;
        s_deviceList=s_deviceList1;
        missionList=missionList1;
        scene.setMissionList(missionList);
        scene.setConditionList(conditionList);
        scene.setIsClick(isClick);
        scene.setS_deviceList(s_deviceList);
        scene.setCTimeList(c_timeList);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time1=simpleDateFormat.format(date);
        scene.setTime(time1);
        scene.save();
    }
    class MyRunnable implements Runnable{


        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //FIXME 没有加定时关闭链接，不关闭会导致之后的设备直接入网
    }
    private void initRecyclerViewCon(){
        if(time!=null){
            mConditionList.clear();
            mConditionList=LitePal.where("scene_id = ?",id).find(Condition.class);
            LinearLayoutManager layoutManager=new LinearLayoutManager(More.this);
            recy_condition.setLayoutManager(layoutManager);
            ConAndMissAdaptor conAndMissAdaptor=new ConAndMissAdaptor(mConditionList);
            recy_condition.setAdapter(conAndMissAdaptor);
            conAndMissAdaptor.notifyDataSetChanged();
        }

    }
    private void initRecyclerViewMiss(){
        if(time!=null){
            mMissionList.clear();
            mMissionList=LitePal.where("scene_id = ?",id).find(Mission.class);
            LinearLayoutManager layoutManager=new LinearLayoutManager(More.this);
            recy_mission.setLayoutManager(layoutManager);
            ConAndMissAdaptor conAndMissAdaptor=new ConAndMissAdaptor(mMissionList,1);
            recy_mission.setAdapter(conAndMissAdaptor);
            conAndMissAdaptor.notifyDataSetChanged();
        }
    }
}
