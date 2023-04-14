package com.example.smarthome.Page_Huiju;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.smarthome.Activity.BottomSmartHome;
import com.example.smarthome.Activity.FirstActivity;
import com.example.smarthome.Adapter.RecyclerAdapter;
import com.example.smarthome.Database.Device;
import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Home.FindDevices;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;


import android.content.Intent;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

import com.example.smarthome.Adapter.AddHomeAdapter;
import com.example.smarthome.Adapter.ExtendHeadAdapter;
import com.example.smarthome.Helper.ExtendHeatHelper;
import com.example.smarthome.Scene.More;
import com.example.smarthome.View.CircleWelComeView;
import com.example.smarthome.View.SeekPage.CardConfig;
import com.example.smarthome.View.SeekPage.CardItemTouchHelperCallback;
import com.example.smarthome.View.SeekPage.CardLayoutManager;
import com.example.smarthome.View.SeekPage.OnSwipeListener;
import com.example.smarthome.View.pullextend.ExtendListHeader;
import com.example.smarthome.View.pullextend.PullExtendLayout;
import com.example.smarthome.animation.AddAnimationRotation;
import com.example.smarthome.animation.RuwangAnimationAlpha;
import com.example.smarthome.animation.SunAnimatorMoverment;

import java.util.ArrayList;
import java.util.List;

public class HuijuFrament extends Fragment {
    ExtendListHeader mPullNewHeader;
    PullExtendLayout mPullExtendLayout;
    Toolbar toolbar1;
    GridLayoutManager gridLayoutManager;
    private CircleWelComeView circleView;
    private static int count=0;
    private static int switch_i=0;//判断switch点击次数
    RecyclerView huiju,mListHeader;
    ExtendHeadAdapter addadapter;
    AddHomeAdapter addHomeAdapter;
    ObjectAnimator objectAnimator;
    View sun_anim;
    CardView ruwang;
    ImageView sun,moon;
    private List<Integer> sceneTdList=new ArrayList<>();
    private List<Scene> sceneList=new ArrayList<>();
    private List<Device> deviceList=new ArrayList<>();
    //    List<String> mDatas = new ArrayList<>();
    private List<Integer> list = new ArrayList<>();
    private ClientMQTT clientMQTT;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        clientMQTT=new ClientMQTT("light");
        try {
            clientMQTT.Mqtt_innit();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        clientMQTT.startReconnect(getContext());
        List<Device> deviceList1=new ArrayList<>();
        deviceList1=LitePal.findAll(Device.class);
        /**
         * @description 删除重复的数据
         */
        //TODO 测试是否成功删除
        if(deviceList1.size()!=0)
            for (int i = 0; i < deviceList1.size(); i++) {
                String target_long_address=deviceList1.get(i).getTarget_long_address();
                    for(int j=i+1;j<deviceList1.size();j++)
                    {
                        if(deviceList1.get(j).getTarget_long_address().equals(target_long_address))
                        {
                        int id=deviceList1.get(j).getId();
                        LitePal.deleteAll(Device.class,"id = ?",id+"");
                        deviceList1.remove(j);
                        }

                    }
            }

        sceneList.clear();
        sceneList= LitePal.findAll(Scene.class);
//        count=sceneList.size();
        deviceList=LitePal.where("flag = ? and isUpdate = ?","0","1").find(Device.class);






        return inflater.inflate(R.layout.huiju_fragment,container,false);

    }
    @SuppressLint("ResourceType")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        sun=getActivity().findViewById(R.id.sun);
        sun_anim=getActivity().findViewById(R.anim.sun_anim);
        moon=getActivity().findViewById(R.id.moon);
        ruwang=getActivity().findViewById(R.id.ruwang);
        toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.allhome:
                        Intent intent=new Intent(getActivity(),HomeManageActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.roomemanagement: Intent intent1=new Intent(getActivity(),AddHome.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
        //TODO 设备显示离线
        huiju_recyclerView();
        huiju_recyclerView();

        initData();

    }

    private void initview() {
        toolbar1=getActivity().findViewById(R.id.huiju_tb);
        mPullNewHeader=getActivity().findViewById(R.id.extend_header);
        mPullExtendLayout=getActivity().findViewById(R.id.pull_extend);
        mListHeader=mPullNewHeader.getRecyclerView();

//        mListHeader.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }
//    private void initdata() {
//        mDatas.add("窗帘");
//        mDatas.add("空调");
//        mDatas.add("灯光");
//        mDatas.add("智能门锁");
//        mDatas.add("音响");
//        mDatas.add(" ＋ ");
//        mListHeader.setAdapter(new ExtendHeadAdapter(mDatas).setItemClickListener(new CommonAdapter.ItemClickListener() {
//            @Override
//            public void onItemClicked(int position, View view) {
//                Toast.makeText(getActivity(),mDatas.get(position) + " 功能待实现",Toast.LENGTH_SHORT).show();
//            }
//        }));
//
//    }

    private void huiju_recyclerView() {
        huiju=(RecyclerView)getActivity().findViewById(R.id.add_view);
        huiju.setItemAnimator(new DefaultItemAnimator());
        List<Scene> scenes=new ArrayList<>();
        scenes.add(sceneList.get(0));
        MyAdapter myAdapter=new MyAdapter(scenes);
        myAdapter.setContext(getContext());
        huiju.setAdapter(myAdapter);
        CardItemTouchHelperCallback callback=new CardItemTouchHelperCallback(huiju.getAdapter(),list);
        callback.setOnSwipedListener(new OnSwipeListener<Integer>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer integer, int direction) {
                if (direction == CardConfig.SWIPED_LEFT) {
                    // 向左滑动
                    //TODO 倒叙添加
                    List<Scene> sceneList1=new ArrayList<>();
                    sceneList1.clear();
                    //9
                    //8
                    if(count+1<sceneList.size()){
                        count++;
                        sceneList1.add(sceneList.get(count));
                    }else if(count+1==sceneList.size()){
                        count=0;
                        sceneList1.add(sceneList.get(count));
                    }
                    myAdapter.setSceneList(sceneList1);
                    myAdapter.notifyDataSetChanged();
                    //要不每次只传入一个scene，
                } else {
                    // 向右滑动
                    List<Scene> sceneList1=new ArrayList<>();
                    if(count-1>=0){
                        count--;
                        sceneList1.add(sceneList.get(count));

                    }else if(count-1<0){
                        count=sceneList.size()-1;
                        sceneList1.add(sceneList.get(count));
                    }
                    myAdapter.setSceneList(sceneList1);
                    myAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onSwipedClear() {
// 最后一张也滑动结束，表示内容已空
                huiju.post(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        huiju.getAdapter().notifyDataSetChanged();
                    }
                });
//        huiju.setHasFixedSize(true);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
//       huiju.setLayoutManager(gridLayoutManager);
//        ArrayList<ExtendHeatHelper> addHomeHelpers = new ArrayList<>();
//        addHomeHelpers.add(new ExtendHeatHelper(R.drawable.drawing_room, "客厅"));
//        addHomeHelpers.add(new ExtendHeatHelper(R.drawable.toilet, "卫生间"));
//        addHomeHelpers.add(new ExtendHeatHelper(R.drawable.bedroom, "卧室"));
//        addHomeAdapter=new AddHomeAdapter(addHomeHelpers);
//        huiju.setAdapter(addHomeAdapter);

            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(huiju, touchHelper);
        huiju.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(huiju);
    }
    private void initData() {
        list.add(R.drawable.br);
        list.add(R.drawable.drwaing);
        list.add(R.drawable.toilet2);
    }



    private class MyAdapter extends RecyclerView.Adapter {
        List<Scene> sceneList=new ArrayList<>();
        Context context;
        public MyAdapter( List<Scene> sceneList){
            this.sceneList=sceneList;
        }
        public void setSceneList(List<Scene> sceneList){
            this.sceneList=sceneList;
        }
        public void setContext(Context context){
            this.context=context;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart, parent, false);
            final MyAdapter.MyViewHolder holder=new MyAdapter.MyViewHolder(view);
            return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).viewImage;
            int random=(int)(Math.random()*(2));
            list.clear();
            list.add(R.drawable.br);
            list.add(R.drawable.drwaing);
            list.add(R.drawable.toilet2);
            avatarImageView.setImageResource(list.get(random));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, More.class);
                    int id=sceneList.get(holder.getAdapterPosition()).getId();
                    intent.putExtra(Scene.ID,id);
                    context.startActivity(intent);
                }
            });
            ((MyViewHolder) holder).open_scene.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String name=NameToHEX(sceneList.get(holder.getAdapterPosition()).getName());
                    /**
                     * @description
                     * 指令
                     */
                    //场景名长度
                    String validData,validDataLength;
                    String innerData=null;

                    if(switch_i==0){
                        validData=getValidData("07",name,innerData);//00是内嵌数据长度
                        //创建场景
                        validDataLength=getValidDataLength(validData);
                        clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,"0x"+validDataLength+"");
                        switch_i=1;
                    }else {
                        validData=getValidData("06",name,innerData);//00是内嵌数据长度
                        //创建场景
                        validDataLength=getValidDataLength(validData);
                        clientMQTT.publishMessagePlus(null,"0x0000","0xFE",validData,"0x"+validDataLength+"");
                        switch_i=0;
                    }

                }
            });
            //TODO 场景好像化冻换不了
            ((MyViewHolder) holder).viewName.setText(sceneList.get(position).getName());
        }
        @Override
        public int getItemCount() {
            return sceneList.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView viewImage;
            TextView viewName;
            View itemView;
            Switch open_scene;
            MyViewHolder(View itemView1) {
                super(itemView1);
                itemView=itemView1;
                viewImage = (ImageView) itemView.findViewById(R.id.iv_avatar);//
                viewName=(TextView) itemView.findViewById(R.id.tv_name);
                open_scene=(Switch) itemView.findViewById(R.id.open_scene);
            }
        }
    }
    private String getValidData(String operation,String name,String innerData){
        String validData;
        String nameLength;
        String innerDataLength;
        nameLength=Integer.toHexString(name.length()/2);
        if(nameLength.length()==1)
            nameLength="0"+nameLength;
        if(innerData==null)
            innerDataLength="00";
        else
            innerDataLength=Integer.toHexString(innerData.length()/2);
        if(innerDataLength.length()==1)
            innerDataLength="0"+innerDataLength;
        if(innerData!=null)
            return (validData="0x"+operation+nameLength+name+innerDataLength+innerData);
        else
            return (validData="0x"+operation+nameLength+name+innerDataLength);

    }

    /**
     * @description 获得有效数据长度,有"0x"的那种偶
     * @param validData 有效数据
     * @return 有效数据长度
     */
    private String getValidDataLength(String validData){
        String validDataLength=Integer.toHexString(validData.length()/2-1);
        if(validDataLength.length()==1)
            validDataLength="0"+validDataLength;
        return validDataLength;
    }
    private String NameToHEX(String input){
        // 将中文字符串转换为字节数组
        byte[] bytes = input.getBytes();
        // 将字节数组转换为16进制字符串
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex.append(String.format("%02X", b));
        }
        // 输出16进制字符串
        String name=hex.toString();
        return name;
    }
}