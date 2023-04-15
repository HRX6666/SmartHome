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
import com.example.smarthome.Adapter.MissionAdaptor;
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
    Button create,delete_scene;
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
    private int a;
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
        Intent intent=getIntent();
        a=intent.getIntExtra(Scene.ID,-1);
        init();
        /*
        1.进入后先遍历删除所有Temp数据，之后再创建一个新的temp来储存
        findFirst来找到当前temp
        2.智能设备作为条件可以先弄灯的，空调那些可以先不显示
        灯的就开关嘛
        3.编写适配器，暂时只显示名称图标和开关
        4.还有电器的自定义命名
         */

        List<Scene> sceneList=new ArrayList<>();
        id=String.valueOf(a);
        if(!id.equals("-1"))
            sceneList=LitePal.where("id = ?",id).find(Scene.class,true);
        if(sceneList.isEmpty())
            time=null;
        else{
            time=sceneList.get(0).getTime();
            model_name.setText(sceneList.get(0).getName());//TODO 看看显示没有
            Temp temp=LitePal.findLast(Temp.class);
            TransferDataFromSceneToTemp(temp,sceneList.get(0));
        }
        //TODO 这个界面记得RecyclerView，要检测传进来的时间是否为空，为空就查询场景，初始化recyclerView,记得通过Adaptor的inputTime传入时间

        //TODO 看看能不能显示出来
        initRecyclerViewCon();
        initRecyclerViewMiss();


    }

    private void init() {
        select_condition =findViewById(R.id.select_condition);
        select_tesk=findViewById(R.id.select_tesk);
        create=findViewById(R.id.create);
        model_name=findViewById(R.id.model_name);
        recy_mission=findViewById(R.id.recy_mission);
        recy_condition=findViewById(R.id.recy_condition);
        delete_scene=findViewById(R.id.delete_scene);
        if(a==-1){
            delete_scene.setVisibility(View.INVISIBLE);
            delete_scene.setClickable(false);
        }
        else {
            delete_scene.setVisibility(View.VISIBLE);
            delete_scene.setClickable(true);
        }
        delete_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Scene.class,"id = ?",a+"");
                finish();
            }
        });
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
                    Temp temp=LitePal.findFirst(Temp.class,true);
                    temp.setTime(time1);
                    Scene scene=new Scene();
                    scene.setTime(time1);
                    scene.setName(name_ml);
                    scene.setIsClick(temp.getIsClick());
                    if(!temp.getConditionList().isEmpty()){
                        scene.setConditionList(temp.getConditionList());
                    }
//                    if(!temp.getMissionList().isEmpty())
//                        scene.setMissionList(temp.getMissionList());
                    List<S_Device> s_deviceList=new ArrayList<>();
                    s_deviceList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(S_Device.class,true);
                    List<Condition> conditionList=new ArrayList<>();
                    List<Mission> missionList=new ArrayList<>();
                    conditionList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(Condition.class,true);
                    missionList=LitePal.where("temp_id = ?",String.valueOf(temp.getId())).find(Mission.class,true);
                    if(!conditionList.isEmpty())
                        c_timeList=LitePal.where("condition_id = ?",String.valueOf(conditionList.get(0).getId())).find(C_Time.class,true);
//TODO 发送时间条件的那个还没弄好
//                    for(Condition condition:temp.getConditionList()){
//                        Condition condition1=new Condition();
//                        condition1=condition;
//                        conditionList.add(condition1);
//                        condition1.setScene(scene);
//                        condition1.save();
//                    }
//                    for(Mission mission:temp.getMissionList()){
//                        Mission mission1=mission;
//                        missionList.add(mission1);
//                        mission1.setScene(scene);
//                        mission1.save();
//
//                    }
                    List<C_Time> c_timeList=new ArrayList<>();//多个时间点
                    String isClick;
                    List<C_Time> c_timeList1=new ArrayList<>();//多个时间点
                    List<Condition> conditionList1=new ArrayList<>();//条件
                    List<Mission> missionList1=new ArrayList<>();//任务
                    List<S_Device> s_deviceList1=new ArrayList<>();

                    isClick=temp.getIsClick();
                    //TODO 每一个c_time还是要通过condition来寻找，因为recycler展示的就是conditionList不是c_timeList，传入的参数有scene_id和创建时间
                    //TODO 展示的列表用Map，毕竟也就显示一个标题，
                    c_timeList1=LitePal.where("temp_id = ?",temp.getId()+"").find(C_Time.class,true);
                    conditionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Condition.class,true);
                    s_deviceList1=LitePal.where("temp_id = ?",temp.getId()+"").find(S_Device.class,true);
                    missionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Mission.class,true);
                    c_timeList=c_timeList1;
                    s_deviceList=s_deviceList1;


                    for(int i=0;i<conditionList1.size();i++){
                        Condition condition=new Condition();
                        condition=conditionList1.get(i);
                        if(condition.getJudge()==3){
                            C_Time c_time=new C_Time();
                            C_Time c_time1=new C_Time();
                            c_time1=conditionList.get(i).getC_time();
                            c_time=c_time1;
                            c_time.setCondition(condition);
                            condition.setC_time(c_time);
                            c_time.setScene(scene);
                            c_time.setCondition(condition);
                            c_time.save();
                        }
                        condition.setScene(scene);
                        scene.getConditionList().add(condition);
                        condition.save();
                        scene.save();
                    }
                    //TODO 其实完全可以直接setNull来去外键ku
                    for(int j=0;j<missionList1.size();j++){
                        Mission mission1 = new Mission();
                        mission1=missionList1.get(j);
                        if(mission1.getJudge()==3){
                            List<S_Device> s_deviceList2=LitePal.where("mission_id = ?",missionList1.get(j).getId()+"").find(S_Device.class);
                            for(int i=0;i<s_deviceList2.size();i++){
                                S_Device s_device=new S_Device();
                                s_device=s_deviceList2.get(i);
                                s_device.setMission(mission1);
                                s_device.setScene(scene);
                                mission1.setTemp(null);
                                mission1.getS_deviceList().add(s_device);
                                s_device.save();
                            }
                        }
                        scene.getMissionList().add(mission1);
                        mission1.setScene(scene);
                        mission1.save();
                    }

                    scene.setTime(time1);
                    scene.setIsOpen(0);
                    scene.save();
                    //

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
                   clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength+"");
                    if(hexTime!=null){
                        innerData=getInnerDataForTime(More.CONTROLLER_LONG_ADDRESS,"00",hexTime);
                        validData=getValidData("03",name,innerData);
                        //发送时间点条件
                        validDataLength=getValidDataLength(validData);
                        clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
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
                                    innerValidData="0"+light+"0"+brightness;
                                    innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                    validData=getValidData("05",name,innerData);
                                    validDataLength=getValidDataLength(validData);
                                    clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);
                                }
                            }
                            if(s_device.getLight_model()!=null)
                                if(s_device.getLight_model().equals("3")){//呼吸灯指令
                                innerValidData="00"+"06";
                                innerData=getInnerDataForCommand(target_short_address,s_device.getDevice_type(),innerValidData);
                                validData=getValidData("05",name,innerData);
                                validDataLength=getValidDataLength(validData);
                                clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,validDataLength);



                            }


                        }

                    }

                    //TODO S_Device里面的判断是执行还是条件的还没赋值
                }else {

                }
//                List<Temp> tempList=new ArrayList<>();
//                tempList=LitePal.findAll(Temp.class);
//                if(!tempList.isEmpty())//如果暂存数据库不为空，就遍历清空Scene,device.....中与temp有关的数据
//                {
//                    //TODO 刚进入界面的那个pull服务器存的用户已连接电器数据，要加定时关闭
//                    for (int j = 0; j < tempList.size(); j++) {
//                        String temp_id=String.valueOf(tempList.get(j).getId());
//                        LitePal.deleteAll(C_Time.class,"temp_id=?",temp_id);
//                        LitePal.deleteAll(S_Device.class,"temp_id=?",temp_id);
//                        LitePal.deleteAll(Mission.class,"temp_id=?",temp_id);
//                        LitePal.deleteAll(Condition.class,"temp_id=?",temp_id);
//                        j++;
//                    }
//                    tempList.clear();
//                }
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
       return "0x"+validDataLength;
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
        if(innerValidDataLength.length()==1)
            innerValidDataLength="0"+innerValidDataLength;
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
    private void TransferDataFromSceneToTemp(Temp temp,Scene scene){
        List<C_Time> c_timeList=new ArrayList<>();//多个时间点
        List<Condition> conditionList=new ArrayList<>();//条件
        List<Mission> missionList=new ArrayList<>();//任务
        List<S_Device> s_deviceList=new ArrayList<>();
        String isClick;
        List<C_Time> c_timeList1=new ArrayList<>();//多个时间点
        List<Condition> conditionList1=new ArrayList<>();//条件
        List<Mission> missionList1=new ArrayList<>();//任务
        List<S_Device> s_deviceList1=new ArrayList<>();

        isClick=scene.getIsClick();
        //TODO 每一个c_time还是要通过condition来寻找，因为recycler展示的就是conditionList不是c_timeList，传入的参数有scene_id和创建时间
        //TODO 展示的列表用Map，毕竟也就显示一个标题，
        c_timeList1=LitePal.where("scene_id = ?",scene.getId()+"").find(C_Time.class,true);
        conditionList1=LitePal.where("scene_id = ?",scene.getId()+"").find(Condition.class,true);
        s_deviceList1=LitePal.where("scene_id = ?",scene.getId()+"").find(S_Device.class,true);
        missionList1=LitePal.where("scene_id = ?",scene.getId()+"").find(Mission.class,true);
        c_timeList=c_timeList1;
        conditionList=conditionList1;
        s_deviceList=s_deviceList1;
        missionList=missionList1;
        for(int i=0;i<conditionList.size();i++){
            Condition condition=new Condition();
            condition=conditionList.get(i);
            temp.getConditionList().add(condition);
            condition.save();
        }
        temp.setMissionList(missionList);

        temp.setIsClick(isClick);
        temp.setS_deviceList(s_deviceList);
        temp.setC_timeList(c_timeList);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time1=simpleDateFormat.format(date);
        temp.setTime(time1);
        temp.save();
        int a=0;
    }
    private void TransferDataFromTempToScene(Scene scene,Temp temp,String name){
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
        c_timeList1=LitePal.where("temp_id = ?",temp.getId()+"").find(C_Time.class,true);
        conditionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Condition.class,true);
        s_deviceList1=LitePal.where("temp_id = ?",temp.getId()+"").find(S_Device.class,true);
        missionList1=LitePal.where("temp_id = ?",temp.getId()+"").find(Mission.class,true);
        c_timeList=c_timeList1;
        conditionList=conditionList1;
        s_deviceList=s_deviceList1;
        missionList=missionList1;

        for(int i=0;i<conditionList.size();i++){
            Condition condition=new Condition();
            condition=conditionList.get(i);
            if(condition.getJudge()==3){
                C_Time c_time=new C_Time();
                c_time=condition.getC_time();
                c_time.setCondition(condition);
                condition.setC_time(c_time);
                c_time.setScene(scene);
                c_time.setCondition(condition);
                c_time.save();
            }
            condition.setScene(scene);
            scene.getConditionList().add(condition);
            condition.save();
            scene.save();
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time1=simpleDateFormat.format(date);
        scene.setTime(time1);
        scene.setIsOpen(0);
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
            mMissionList=LitePal.where("scene_id = ?",id).find(Mission.class,true);
            LinearLayoutManager layoutManager=new LinearLayoutManager(More.this);
            recy_mission.setLayoutManager(layoutManager);
            MissionAdaptor missionAdaptor=new MissionAdaptor(mMissionList);
            missionAdaptor.setContext(More.this);
            recy_mission.setAdapter(missionAdaptor);
            missionAdaptor.notifyDataSetChanged();
        }
    }
}
