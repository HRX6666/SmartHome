package com.example.smarthome.Adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Device;
import com.example.smarthome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LightListAdaptor extends RecyclerView.Adapter<LightListAdaptor.ViewHolder>{
    //    private static LightListAdaptor.OnItemClickListener mItemClickListener;
    private List<Device> mLightList=new ArrayList<>();

    private int layoutId;
    private List<? extends Object> data;
    public Context context;
    private OnItemClickListner onItemClickListner;//单击事件
    private OnItemLongClickListner onItemLongClickListner;//长按单击事件
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识

    //回调
//    private ItemSelectedCallBack mCallBack;

//    @Override
//    protected void convert(ViewHolder helper, AmountInfo item) {
//
//        if (mCallBack != null) {
//            mCallBack.convert(helper, helper.getLayoutPosition());
//        }
//
//    }
//    public void setItemSelectedCallBack(ItemSelectedCallBack CallBack) {
//        this.mCallBack = CallBack;
//    }

    public interface ItemSelectedCallBack {
        void convert(ViewHolder holder, int position);
    }

    public LightListAdaptor(List<Device> mlightList){
        this.mLightList=mlightList;
    }
    public LightListAdaptor(Context context, int layoutId,List<Device> mLightList) {
        this.layoutId = layoutId;
        this.mLightList = mLightList;
        this.context = context;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView light_image;
        TextView light_name;
        View lightView;
        ConstraintLayout constraintLayout;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lightView=itemView;
            light_image=lightView.findViewById(R.id.light_image);
            light_name=lightView.findViewById(R.id.light_name);
            constraintLayout=lightView.findViewById(R.id.constraintLayout);

        }
        public View getItemView() {
            return lightView;
        }
        public View getView(int id) {
            return lightView.findViewById(id);
        }

        public interface OnClickListener {
            void onClickListener(View v);
        }

    }
    @NonNull
    @Override
    public LightListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.scenelightlist,parent,false);
        final ViewHolder holder=new ViewHolder(view);
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
        //如果用户编辑之前已经选择的条件或任务，就要让之前选择的电器变为选中状态
//        holder.lightView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                holder.constraintLayout.setBackgroundResource(R.drawable.blackbackground);
////                if(holder.constraintLayout.getBackground().equals(ContextCompat.getDrawable(holder.constraintLayout.getContext(), R.drawable.blackbackground))) {
////                    holder.constraintLayout.setBackgroundColor(0xFFFFFFFF);
////                }
//            }
//        });

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull LightListAdaptor.ViewHolder holder, int position) {
        holder.light_image.setImageResource(R.drawable.open_lights);
        holder.light_name.setText(mLightList.get(position).getTarget_long_address());
        if (mCallBack != null)
            mCallBack.convert(holder, mLightList.get(position), position);
    }

    @Override
    public int getItemCount() {
        if(mLightList==null)
            return 0;
        else
            return mLightList.size();
    }

    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }

    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }


    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }

    CallBack mCallBack;

    public void setCallBack(CallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface CallBack {
        <T extends Object> void convert(ViewHolder holder, T bean, int position);
    }
}