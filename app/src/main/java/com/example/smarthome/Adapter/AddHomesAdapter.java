package com.example.smarthome.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.AddHomes;
import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Database.Home;
import com.example.smarthome.Page_Huiju.AddHome;
import com.example.smarthome.Page_Huiju.HuijuFrament;

import com.example.smarthome.R;

import java.util.List;

public class AddHomesAdapter extends RecyclerView.Adapter<AddHomesAdapter.ViewHolder> {
    private Context mContext; // 声明一个上下文对象
    private List<AddHomes> list; // 声明一个信息列表w
    OnItemClickListener onItemClickListener;//声明接口对象
    RecyclerAdapter.OnItemLongClickListener onItemLongClickListener;
    public void set0nItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;//传递接口
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,int position);//设置接口
    }

    public AddHomesAdapter(List<AddHomes> homesList) {
        list = homesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext().getApplicationContext(), AddHome.class);
                String  home = viewHolder.h_home.getText().toString().trim();
                intent.putExtra("home", home);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                parent.getContext().startActivity(intent);
//                关于Activity跳转，Context中有一个startActivity方法，
//                Activity继承自Context，重载了startActivity方法。
//                如果使用Activity的startActivity方法，不会有任何限制，
//                而如果使用Context的startActivity方法的話，就需要开启一个新的的task，
//                遇到这个异常，是因为使用了Context的startActivity方法。解决办法是，加一个flag。
            }
        });

        return viewHolder;
    }
    public  interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }

    @Override
    public void onBindViewHolder(@NonNull AddHomesAdapter.ViewHolder holder, int position) {
        AddHomes addHomes=list.get(position);
        holder.h_home.setText(addHomes.getHome());
//        ImageView imageView= (ImageView) holder.view;
        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.OnItemClick(v,position);
        });

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView h_home;
        View view;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            h_home=(TextView) itemView.findViewById(R.id.tv_name);
            view=itemView;

        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}