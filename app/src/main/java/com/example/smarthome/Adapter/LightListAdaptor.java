package com.example.smarthome.Adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;

import java.util.List;
import java.util.Map;

public class LightListAdaptor extends RecyclerView.Adapter<LightListAdaptor.ViewHolder>{
    private LightListAdaptor.OnItemClickListener mItemClickListener;
    private List<Map<String,String>> mLightList;

    public LightListAdaptor(List<Map<String,String>> mLightList){
        this.mLightList=mLightList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView light_image;
        TextView light_name;
        View lightView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lightView=itemView;
            lightView.setOnClickListener(this);
            light_image=lightView.findViewById(R.id.light_image);
            light_name=lightView.findViewById(R.id.light_name);
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
    public LightListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scenelightlist,parent,false);
        final LightListAdaptor.ViewHolder holder=new LightListAdaptor.ViewHolder(view);
        //如果用户编辑之前已经选择的条件或任务，就要让之前选择的电器变为选中状态
//        holder.constraintLayout.setBackgroundColor(0xFF03A9F4);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull LightListAdaptor.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mLightList.size();
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
    public void setOnItemClickListener(LightListAdaptor.OnItemClickListener listener) {
        mItemClickListener = listener;
    }
}

