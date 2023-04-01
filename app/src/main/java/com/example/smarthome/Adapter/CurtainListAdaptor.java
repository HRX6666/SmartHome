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

public class CurtainListAdaptor extends RecyclerView.Adapter<CurtainListAdaptor.ViewHolder> {
    private List<Map<String,String>> mCurtainList;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    public CurtainListAdaptor(List<Map<String,String>> mCurtainList){
        this.mCurtainList=mCurtainList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView curtain_image;
        TextView curtain_name;
        ConstraintLayout constraintLayout;
        View curtainView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            curtainView =itemView;
            constraintLayout= curtainView.findViewById(R.id.constraintLayout);
            curtain_image= curtainView.findViewById(R.id.curtain_image);
            curtain_name= curtainView.findViewById(R.id.curtain_name);
            curtainView.setOnClickListener(this);

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
    public CurtainListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_curtainlist,parent,false);
        final CurtainListAdaptor.ViewHolder holder=new CurtainListAdaptor.ViewHolder(view);
        //如果用户编辑之前已经选择的条件或任务，就要让之前选择的电器变为选中状态
//        holder.constraintLayout.setBackgroundColor(0xFF03A9F4);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurtainListAdaptor.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mCurtainList.size();
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
    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    // 设置 Item 长按事件监听器
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mItemLongClickListener = listener;
    }
}
