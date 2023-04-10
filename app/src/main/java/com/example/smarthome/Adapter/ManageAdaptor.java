package com.example.smarthome.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.FindDeviceAdapter;
import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Samrt.AdjustTheAirCondition;
import com.example.smarthome.Page_Samrt.AdjustTheLights;
import com.example.smarthome.Page_Samrt.AdjustTheMusic;
import com.example.smarthome.Page_Samrt.AdustTheCurtain;
import com.example.smarthome.R;
import com.example.smarthome.View.FButton;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManageAdaptor extends RecyclerView.Adapter<ManageAdaptor.ViewHolder>{ //那三个类弃用了，只用device类
    private ManageAdaptor.OnItemClickListener mItemClickListener;
    private List<Map<String,String>> mDeviceList;
    private Context context;
    private ClientMQTT clientMQTT;
    private  String source_short_address;
    private int flag;//通过函数传入的参数，来判断是离线的recyclerView用的Adaptor还是在线的recyclerView
    public static final String CONTROLLER_LONG_ADDRESS = "controller_long_address";
    public static final String TARGET_LONG_ADDRESS = "target_long_address";
    public static final String TARGET_SHORT_ADDRESS = "target_short_address";
    public static final String NETWORK_FLAG = "network_flag";
    public static final String DEVICE_TYPE = "device_type";
    public static final String WETNESS = "wetness";
    public static final String AIR_TEMP = "air_temp";
    public ManageAdaptor(List<Map<String,String>> deviceList){
        mDeviceList=deviceList;
    }
    public void InputFlag(int flag){
        this.flag=flag;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView showCategory;
        View deviceView;
        ImageView imageView;
        public ViewHolder(@NonNull View view){//通过转化为view的布局获得控件实例
            super(view);
            deviceView=view;
            showCategory=view.findViewById(R.id.add_tv);
            imageView=view.findViewById(R.id.add_im);

        }
    }


    @NonNull
    @Override
    public ManageAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_smart,parent,false);
        //可以在不同的recyclerView初始化那边通过这里设计的一个函数传入一个参数flag，帮助这里分在线和离线分别显示

        final ManageAdaptor.ViewHolder holder=new ManageAdaptor.ViewHolder(view);

        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(parent.getContext());
///////////////////////这个要判断传到哪个adjust上的
        holder.deviceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder (parent.getContext());
                dialog.setTitle("是否删除电器？");//对话框标题
                dialog.setMessage("Something important.");//对话框显示的信息
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.
                        OnClickListener() {//对话框中的按钮 OK
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                String source_short_address_1=mDeviceList.get(holder.getAdapterPosition()).get("target_short_address");
                String source_long_address=mDeviceList.get(holder.getAdapterPosition()).get("target_long_address");
                source_short_address="0x"+source_short_address_1;
                clientMQTT.publishMessagePlus(null,"0x0000","0xFF", "0x0004"+source_long_address,"0x0A");
                LitePal.deleteAll("device","target_long_address = ?",source_long_address);
                mDeviceList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.
                        OnClickListener() {//对话框中的按钮 Cancle
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();//显示对话框

                return false;

            }
        });
        holder.deviceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据devicetype分类
                int position= holder.getAdapterPosition();//获得点击的位置
                String controller_long_address=mDeviceList.get(position).get("controller_long_address");//获得List中的第position个map中的特定数据，之前一个map中传入了一个item的所有数据
                String target_short_address=mDeviceList.get(position).get("target_short_address");
                String target_long_address=mDeviceList.get(position).get("target_long_address");
                String network_flag=mDeviceList.get(position).get("network_flag");
                String device_type=mDeviceList.get(position).get("device_type");
                switch (device_type) {
                    case "01":
                        Intent intent=new Intent(parent.getContext(), AdjustTheLights.class);
                        intent.putExtra(ManageAdaptor.CONTROLLER_LONG_ADDRESS,controller_long_address);
                        intent.putExtra(ManageAdaptor.TARGET_LONG_ADDRESS,target_long_address);
                        intent.putExtra(ManageAdaptor.TARGET_SHORT_ADDRESS,target_short_address);
                        intent.putExtra(ManageAdaptor.NETWORK_FLAG,network_flag);
                        intent.putExtra(ManageAdaptor.DEVICE_TYPE,device_type);
                        parent.getContext().startActivity(intent);
                        break;
                    case "02":
                        Intent intent1=new Intent(parent.getContext(), AdustTheCurtain.class);
                        String wetness=mDeviceList.get(position).get("wetness");
                        String air_temp=mDeviceList.get(position).get("air_temp");
                        intent1.putExtra(ManageAdaptor.AIR_TEMP,air_temp);
                        intent1.putExtra(ManageAdaptor.WETNESS,wetness);
                        intent1.putExtra(ManageAdaptor.CONTROLLER_LONG_ADDRESS,controller_long_address);
                        intent1.putExtra(ManageAdaptor.TARGET_LONG_ADDRESS,target_long_address);
                        intent1.putExtra(ManageAdaptor.TARGET_SHORT_ADDRESS,target_short_address);
                        intent1.putExtra(ManageAdaptor.NETWORK_FLAG,network_flag);
                        intent1.putExtra(ManageAdaptor.DEVICE_TYPE,device_type);
                        parent.getContext().startActivity(intent1);
                        break;
                    case "03":
                        Intent intent2=new Intent(parent.getContext(), AdjustTheAirCondition.class);
                        intent2.putExtra(ManageAdaptor.CONTROLLER_LONG_ADDRESS,controller_long_address);
                        intent2.putExtra(ManageAdaptor.TARGET_LONG_ADDRESS,target_long_address);
                        intent2.putExtra(ManageAdaptor.TARGET_SHORT_ADDRESS,target_short_address);
                        intent2.putExtra(ManageAdaptor.NETWORK_FLAG,network_flag);
                        intent2.putExtra(ManageAdaptor.DEVICE_TYPE,device_type);
                        parent.getContext().startActivity(intent2);
                        break;
                    case "04":
                        Intent intent3=new Intent(parent.getContext(), AdustTheCurtain.class);
                        intent3.putExtra(ManageAdaptor.CONTROLLER_LONG_ADDRESS,controller_long_address);
                        intent3.putExtra(ManageAdaptor.TARGET_LONG_ADDRESS,target_long_address);
                        intent3.putExtra(ManageAdaptor.TARGET_SHORT_ADDRESS,target_short_address);
                        intent3.putExtra(ManageAdaptor.NETWORK_FLAG,network_flag);
                        intent3.putExtra(ManageAdaptor.DEVICE_TYPE,device_type);
                        parent.getContext().startActivity(intent3);
                        break;
                    case "05":
                        Intent intent4=new Intent(parent.getContext(), AdjustTheMusic.class);
                        intent4.putExtra(ManageAdaptor.CONTROLLER_LONG_ADDRESS,controller_long_address);
                        intent4.putExtra(ManageAdaptor.TARGET_LONG_ADDRESS,target_long_address);
                        intent4.putExtra(ManageAdaptor.TARGET_SHORT_ADDRESS,target_short_address);
                        intent4.putExtra(ManageAdaptor.NETWORK_FLAG,network_flag);
                        intent4.putExtra(ManageAdaptor.DEVICE_TYPE,device_type);
                        parent.getContext().startActivity(intent4);
                        break;
                }
               
   
            }
        });
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ManageAdaptor.ViewHolder holder, int position) {
        //直接
        String category=mDeviceList.get(holder.getAdapterPosition()).get("device_type");//在解析完中控传过来的数据，数据早已存入数据库了，现在的工作只是Update就可以，保存是否组网，没组网就显示这个设备，传入的list、应该在外面先判断一下再传入未租网的list，
        String source_long_address=mDeviceList.get(holder.getAdapterPosition()).get("target_long_address");
        String network_flag=mDeviceList.get(holder.getAdapterPosition()).get("network_flag");
//        switch (network_flag){
//            case "00":holder.isOnline.setText("离线");
//            holder.manage_light_open.setEnabled(false);
//            holder.manage_light_close.setEnabled(false);
//            holder.manage_light_open.setBackgroundResource(R.drawable.gray_bt);
//            holder.manage_light_close.setBackgroundResource(R.drawable.gray_bt);
//            break;//////////////加入一个入网判断，判断是否入网，否则直接推出network显示在线，应该再次获取终端信息
//            case "01":holder.isOnline.setText("在线");
//            holder.manage_light_open.setEnabled(true);
//            holder.manage_light_close.setEnabled(true);
//            break;
//        }

        if (holder.imageView != null) {

        switch (Objects.requireNonNull(category)) {
            case "01":
                holder.imageView.setImageResource(R.drawable.lights_smart);
                category = "电灯泡";
                break;
            case "02":
                holder.imageView.setImageResource(R.drawable.air_condition_smart);
                category = "空调";
                break;
            case "03":
                holder.imageView.setImageResource(R.drawable.curtain_smart);
                category = "窗帘";
                break;
            case "04":
                holder.imageView.setImageResource(R.drawable.lock_smart);
                category = "门锁";
                break;
            case "05":
                holder.imageView.setImageResource(R.drawable.music);
                category = "音响";
                break;
        }

        }

        holder.showCategory.setText(source_long_address);

//        holder.delete_device.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String source_short_address_1=mDeviceList.get(holder.getAdapterPosition()).get("target_short_address");
//                source_short_address="0x"+source_short_address_1;
//                clientMQTT.publishMessagePlus(null,"0x00","0xFF", "0x0004"+source_long_address,"0x02");
//                LitePal.deleteAll("target_long_address = ?",source_short_address_1);
//                mDeviceList.remove(holder.getAdapterPosition());
//                notifyDataSetChanged();
//            }
//        });
//        holder.manage_light_open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Device device=new Device();
////                device.setFlag(1);
////                device.updateAll("target_long_address = ?",source_long_address);
//                //向中控发送APP同意入网信息
//                String source_short_address_1=mDeviceList.get(holder.getAdapterPosition()).get("target_short_address");
//                source_short_address="0x"+source_short_address_1;
//                clientMQTT.publishMessagePlus(null,source_short_address,"0x01", "0x0401","0x02");
//            }
//        });
//        holder.manage_light_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //发送拒绝指令
////                Device device=new Device();
////                device.setFlag(1);
////                device.updateAll("target_long_address = ?",source_long_address);
//                //向中控发送APP同意入网信息
//                String source_short_address_1=mDeviceList.get(holder.getAdapterPosition()).get("target_short_address");
//                source_short_address="0x"+source_short_address_1;
//                clientMQTT.publishMessagePlus(null,source_short_address,"0x01", "0x0400","0x02");
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();/////////
    }
    // 定义 Item 点击事件的监听器接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 定义 Item 长按事件的监听器接口
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    // 设置 Item 点击事件监听器
    public void setOnItemClickListener(ManageAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }


}
