package com.example.smarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Device;
import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurtainListAdaptor extends RecyclerView.Adapter<CurtainListAdaptor.ViewHolder> {
    private CurtainListAdaptor.OnItemClickListner onItemClickListner;//单击事件
    private CurtainListAdaptor.OnItemLongClickListner onItemLongClickListner;//长按单击事件
    private int layoutId;
    public Context context;
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识
    private List<Map<String,String>> mCurtainList;
    private List<Device> curtainList=new ArrayList<>();

    public CurtainListAdaptor(List<Map<String,String>> mCurtainList){
        this.mCurtainList=mCurtainList;
    }
    public interface ItemSelectedCallBack {
        void convert(CurtainListAdaptor.ViewHolder holder, int position);
    }
    public CurtainListAdaptor(Context context, int layoutId, List<Device> curtainList) {
        this.layoutId = layoutId;
        this.curtainList = curtainList;
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
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

        }
        public View getItemView() {
            return curtainView;
        }
        public View getView(int id) {
            return curtainView.findViewById(id);
        }

        public interface OnClickListener {
            void onClickListener(View v);
        }

    }
    @NonNull
    @Override
    public CurtainListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scene_curtainlist,parent,false);
        final CurtainListAdaptor.ViewHolder holder=new CurtainListAdaptor.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner == null)
                    return;
                if (clickFlag) {
                    onItemClickListner.onItemClickListner(v, holder.getLayoutPosition());
                }
                clickFlag = true;
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListner == null)
                    return false;
                onItemLongClickListner.onItemLongClickListner(v, holder.getLayoutPosition());
                clickFlag = false;
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurtainListAdaptor.ViewHolder holder, int position) {
        holder.curtain_image.setImageResource(R.drawable.curtain_open);
        holder.curtain_name.setText(curtainList.get(position).getTarget_long_address());
        if (mCallBack != null)
            mCallBack.convert(holder, curtainList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(mCurtainList==null)
            return 0;
        else
            return mCurtainList.size();
    }
    // 定义 Item 点击事件的监听器接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListner(CurtainListAdaptor.OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }

    public void setOnItemLongClickListner(CurtainListAdaptor.OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }


    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }

    CurtainListAdaptor.CallBack mCallBack;

    public void setCallBack(CurtainListAdaptor.CallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface CallBack {
        <T extends Object> void convert(CurtainListAdaptor.ViewHolder holder, T bean, int position);
    }
}
