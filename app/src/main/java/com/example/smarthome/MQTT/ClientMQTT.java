package com.example.smarthome.MQTT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.smarthome.Json.JsonString;
import com.example.smarthome.Json.ParseJson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.fusesource.hawtdispatch.Dispatch;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.codec.MessageSupport;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientMQTT {
    private ClientMQTT mqttClient;
    private ScheduledExecutorService scheduler;
    private MQTT mqtt;
    private MqttClient client;
    private Handler handler;
    private String topic_subscribe;
    MqttConnectOptions options;
    int qos = 2;
    private String topic="light";
    private static final String userName = "ESP32-C3-username";
    private static final String password = "ESP32-C3-password";
    private String device_name="vivo";
    public  static final String serverURI="tcp://broker.emqx.io:1883";
//       public  static final String serverURI="tcp://192.168.112.217:1883";
    private String device_id=MqttClient.generateClientId();

    private String topicName;
    private final MemoryPersistence memoryPersistence=new MemoryPersistence();
    public ClientMQTT( String topicName) {
        this.topicName = topicName;
    }

    public String getdevice_id() {
        return device_id;
    }

    public void setdevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
    public void Mqtt_innit() throws MqttException {
        client=new MqttClient(serverURI,device_id,memoryPersistence);
        options=new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        //设置连接的用户名
        options.setUserName(userName);
        //设置连接的密码
        options.setPassword(ClientMQTT.password.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("connectionLost----------");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String jsonData=new String(message.getPayload());
                ParseJson parseJson=new ParseJson();
                parseJson.ParseJsonData(jsonData);
                System.out.println("messageArrived----------");
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete----------");
            }
        });


    }
    public void Mqtt_connect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!(client.isConnected()))
                    {

                        options.setUserName(userName);
                        //设置连接的密码
                        options.setPassword(ClientMQTT.password.toCharArray());
                        client.connect(options);
//                        client.subscribe("ESP32toAPP");//订阅、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、、
                        Message message=new Message();
                        message.what=31;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Message message=new Message();
                    message.what=30;
                    handler.sendMessage(message);
                    //666
                }
            }
        }).start();


    }
    public void startReconnect(Context context){
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!client.isConnected()) {
                    Mqtt_connect();
                }
            }
        }, 0*1000, 10 * 1000, TimeUnit.MILLISECONDS);
        handler = new Handler(Looper.myLooper()) {
            @SuppressLint("SetTextI18n")
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1: //开机校验更新回传
                        break;
                    case 2:  // 反馈回传

                        break;
                    case 3:  //MQTT 收到消息回传   UTF8Buffer msg=new UTF8Buffer(object.toString());

                        System.out.println(msg.obj.toString());   // 显示MQTT数据
                        break;
                    case 30:  //连接失败
                        Toast.makeText(context,"连接失败" ,Toast.LENGTH_SHORT).show();
                        break;
                    case 31:   //连接成功
                        Toast.makeText(context,"连接成功" ,Toast.LENGTH_SHORT).show();
                        try {
                            client.subscribe("ESP32toAPP",0);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        };

    }
    public void Subscribe(){
        try {
            client.subscribe("ESP32toAPP",0);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    public void unSubscribe(){
        try {
            client.unsubscribe("ESP32toAPP");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
    //这边的map改成List<Map<String,String>>，毕竟设备不只是一个
    public void publishMessagePlusWithMap( Map<String,String> map,String target_data){
        if (client == null || !client.isConnected()) {

            return;
        }
        MqttMessage message = new MqttMessage();
        JsonString jsonString=new JsonString("2023-02-19T08:30:00Z",device_id,map.get("misc"),map.get("target_short_address"),map.get("device_type"),target_data,"0x02");
        message.setPayload(jsonString.toString().getBytes());
        try {
            client.publish("APPtoESP32",message);//上传信息
        } catch (MqttException e) {

            e.printStackTrace();
        }
    }
    public void publishMessagePlusWithMap(List<Map<String,String>> deviceList){

        Map<String,String> map=new HashMap<>();
        for (int i = 0; i <deviceList.size(); i++) {
            map=deviceList.get(i);

            if (client == null || !client.isConnected()) {

                return;
            }
            MqttMessage message = new MqttMessage();
            JsonString jsonString=new JsonString("2023-02-19T08:30:00Z","1.2.3",device_id,map.get("misc"),map.get("target_short_address"),map.get("target_command"),map.get("target_data"));
            message.setPayload(jsonString.toString().getBytes());
            try {
                client.publish("APPtoESP32",message);//上传信息
            } catch (MqttException e) {

                e.printStackTrace();
            }
        }}
    public void publishMessagePlus(String misc,String target_short_address,String device_type,String valid_data,String valid_data_length)
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ISO_DATE_TIME;
        String timestamp=dateTimeFormatter.format(localDateTime);

        if (client == null || !client.isConnected()) {
            return;
        }
        MqttMessage message = new MqttMessage();
        JsonString jsonString=new JsonString(timestamp,device_id,misc,target_short_address,device_type,valid_data,valid_data_length);
        message.setPayload(jsonString.toString().getBytes());
        try {
            client.publish("APPtoESP32",message);//上传信息
        } catch (MqttException e) {

            e.printStackTrace();
        }
    }
    public void closeContact() throws MqttException {
        client.close();
    }

}
