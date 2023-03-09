package com.example.smarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.FindDeviceAdapter;
import com.example.smarthome.Database.Device;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;
import com.example.smarthome.View.FButton;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManageAdaptor extends RecyclerView.Adapter<ManageAdaptor.ViewHolder>{ //那三个类弃用了，只用device类
    private List<Map<String,String>> mDeviceList;
    private Context context;
    private ClientMQTT clientMQTT;
    private static View deviceView;
    private  String source_short_address;
    public ManageAdaptor(List<Map<String,String>> deviceList){
        mDeviceList=deviceList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView showCategory;
        Button manage_light_open;
        Button manage_light_close;
        Button delete_device;
        private ImageView imageView;
        public ViewHolder(@NonNull View view){//通过转化为view的布局获得控件实例
            super(view);
//            deviceView=view;
            showCategory=view.findViewById(R.id.manage_category_display);
            manage_light_close=view.findViewById(R.id.manage_light_close);
            manage_light_open=view.findViewById(R.id.manage_light_open);
            imageView=view.findViewById(R.id.manage_image2);
            delete_device=view.findViewById(R.id.delete_device);

        }
    }


    @NonNull
    @Override
    public ManageAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.managelist,parent,false);
        final ManageAdaptor.ViewHolder holder=new ManageAdaptor.ViewHolder(view);

        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(parent.getContext());
        return holder;/////////////////////////
    }


    @Override
    public void onBindViewHolder(@NonNull ManageAdaptor.ViewHolder holder, int position) {
        //直接
        String category=mDeviceList.get(holder.getAdapterPosition()).get("device_type");//在解析完中控传过来的数据，数据早已存入数据库了，现在的工作只是Update就可以，保存是否组网，没组网就显示这个设备，传入的list、应该在外面先判断一下再传入未租网的list，
        String source_long_address=mDeviceList.get(holder.getAdapterPosition()).get("target_long_address");
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

        holder.showCategory.setText(category);

        holder.delete_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String source_short_address_1=mDeviceList.get(holder.getAdapterPosition()).get("target_short_address");
                source_short_address="0x"+source_short_address_1;
                clientMQTT.publishMessagePlus(null,"0x00","0xFF", "0x0004"+source_long_address,"0x02");
                Device device1=new Device();
                device1.setToDefault("flag");
                device1.updateAll("target_long_address = ?",source_long_address);
                mDeviceList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
        holder.manage_light_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Device device=new Device();
//                device.setFlag(1);
//                device.updateAll("target_long_address = ?",source_long_address);
                //向中控发送APP同意入网信息
                String source_short_address_1=mDeviceList.get(holder.getAdapterPosition()).get("target_short_address");
                source_short_address="0x"+source_short_address_1;
                clientMQTT.publishMessagePlus(null,source_short_address,"0x01", "0x0101","0x02");
            }
        });
        holder.manage_light_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送拒绝指令
//                Device device=new Device();
//                device.setFlag(1);
//                device.updateAll("target_long_address = ?",source_long_address);
                //向中控发送APP同意入网信息
                String source_short_address_1=mDeviceList.get(holder.getAdapterPosition()).get("target_short_address");
                source_short_address="0x"+source_short_address_1;
                clientMQTT.publishMessagePlus(null,source_short_address,"0x01", "0x0100","0x02");

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();/////////
    }
}
