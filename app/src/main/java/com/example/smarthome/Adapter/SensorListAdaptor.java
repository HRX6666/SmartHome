package com.example.smarthome.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.Database.Sensor;
import com.example.smarthome.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SensorListAdaptor  extends RecyclerView.Adapter<SensorListAdaptor.ViewHolder>{
    private SensorListAdaptor.OnItemClickListener mItemClickListener;
    private List<Sensor> mSensorList;

    public SensorListAdaptor(List<Sensor> mSensorList){
        this.mSensorList=mSensorList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView air_image;
        TextView air_name;
        View sensorView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorView=itemView;
            sensorView.setOnClickListener(this);
            air_image=sensorView.findViewById(R.id.sensor_image);
            air_name=sensorView.findViewById(R.id.sensor_name);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    @NonNull
    @Override
    public SensorListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_airlist,parent,false);
        final SensorListAdaptor.ViewHolder holder=new SensorListAdaptor.ViewHolder(view);

        //如果用户编辑之前已经选择的条件或任务，就要让之前选择的电器变为选中状态
//        holder.constraintLayout.setBackgroundColor(0xFF03A9F4);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SensorListAdaptor.ViewHolder holder, int position) {
        holder.sensorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                Date date=new Date(System.currentTimeMillis());
                Sensor sensor=new Sensor();
                String time1=simpleDateFormat.format(date);
                sensor.setTime(time1);
                sensor.update(mSensorList.get(holder.getAdapterPosition()).getId());
                String device_type=mSensorList.get(holder.getAdapterPosition()).getDevice_type();
              switch (device_type){
                  case ("04"):
                      Intent intent=new Intent();
                      intent.putExtra(Scene.TIME,time1);
                      holder.sensorView.getContext().startActivity(intent);
                      break;
                  case ("05"):

//                      Intent intent1=new Intent();
//                      intent1.putExtra(Scene.TIME,time1);
//                      holder.sensorView.getContext().startActivity(intent1);
                      break;
                  case ("06"):

//                      Intent intent2=new Intent();
//                      intent2.putExtra(Scene.TIME,time1);
//                      holder.sensorView.getContext().startActivity(intent2);
                      break;
//                  case ("07"):
//
//                      break;
//
//                  case ("08"):
//
//                      break;
              }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSensorList.size();
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
    public void setOnItemClickListener(SensorListAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }
    
}
