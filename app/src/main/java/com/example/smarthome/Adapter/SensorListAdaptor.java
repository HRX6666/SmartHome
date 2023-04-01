package com.example.smarthome.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;

import java.util.List;
import java.util.Map;

public class SensorListAdaptor  extends RecyclerView.Adapter<SensorListAdaptor.ViewHolder>{
    private SensorListAdaptor.OnItemClickListener mItemClickListener;
    private List<Map<String,String>> mSensorList;

    public SensorListAdaptor(List<Map<String,String>> mSensorList){
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
                Toast.makeText(v.getContext(),"进入sensor界面",Toast.LENGTH_SHORT ).show();
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
