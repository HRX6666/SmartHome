package com.example.smarthome.Page_Huiju;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.Page_Home.FindDevices;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;
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
import android.widget.ImageView;

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
    RecyclerView huiju,mListHeader;
    ExtendHeadAdapter addadapter;
    AddHomeAdapter addHomeAdapter;
    ObjectAnimator objectAnimator;
    View sun_anim;
    CardView ruwang;
    ImageView sun,moon;
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
                        break;
                    case R.id.roomemanagement: Intent intent1=new Intent(getActivity(),AddHome.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
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
        huiju.setAdapter(new MyAdapter());
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
                } else {
                    // 向右滑动
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
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart, parent, false);
            return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
            avatarImageView.setImageResource(list.get(position));
        }
        @Override
        public int getItemCount() {
            return list.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView avatarImageView;
            MyViewHolder(View itemView) {
                super(itemView);
                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
            }
        }
    }
}