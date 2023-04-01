package com.example.smarthome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;
import java.util.Map;

public class AirListAdaptor extends RecyclerView.Adapter<AirListAdaptor.ViewHolder>{
    private AirListAdaptor.OnItemClickListener mItemClickListener;
    private List<Map<String,String>> mAirList;

    public AirListAdaptor(List<Map<String,String>> mAirList){
        this.mAirList=mAirList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView air_image;
        TextView air_name;
        View airView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            airView=itemView;
            airView.setOnClickListener(this);
            air_image=airView.findViewById(R.id.air_image);
            air_name=airView.findViewById(R.id.air_name);
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
    public AirListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_airlist,parent,false);
        final AirListAdaptor.ViewHolder holder=new AirListAdaptor.ViewHolder(view);
        //如果用户编辑之前已经选择的条件或任务，就要让之前选择的电器变为选中状态
//        holder.constraintLayout.setBackgroundColor(0xFF03A9F4);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AirListAdaptor.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mAirList.size();
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
    public void setOnItemClickListener(AirListAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }
}
