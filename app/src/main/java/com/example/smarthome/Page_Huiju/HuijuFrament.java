package com.example.smarthome.Page_Huiju;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smarthome.MQTT.ClientMQTT;
import com.example.smarthome.R;

import org.eclipse.paho.client.mqttv3.MqttException;


import android.content.Intent;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import com.example.smarthome.View.CircleWelComeView;
import com.example.smarthome.View.SeekPage.CardConfig;
import com.example.smarthome.View.SeekPage.CardItemTouchHelperCallback;
import com.example.smarthome.View.SeekPage.CardLayoutManager;
import com.example.smarthome.View.SeekPage.OnSwipeListener;
import com.example.smarthome.View.SeekPage.ViewDrag;
import com.example.smarthome.View.pullextend.ExtendListHeader;
import com.example.smarthome.View.pullextend.PullExtendLayout;

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
    View sun_anim;
//    List<String> mDatas = new ArrayList<>();
private List<Integer> list = new ArrayList<>();
private List<String> list1 = new ArrayList<>();
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
        final ViewDrag viewDrag = (ViewDrag) getActivity().findViewById(R.id.add_view);
        sun_anim=getActivity().findViewById(R.anim.sun_anim);
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
//        huiju_recyclerView();
//        huiju_recyclerView();
        initData();
        viewDrag.setAdapter(new ViewDrag.DragCardAdapter<String>(list1,5) {

            @Override
            public View getView(int i, String str, View conventView, ViewGroup viewGroup) {
                if(conventView==null){
                    conventView=getLayoutInflater().inflate(R.layout.item_smart,viewGroup,false);
                }
                TextView tv=conventView.findViewById(R.id.tv_name);
                tv.setText(str);
                return conventView;
            }
        });

    }

    private void initview() {
        toolbar1=getActivity().findViewById(R.id.huiju_tb);

        mPullNewHeader=getActivity().findViewById(R.id.extend_header);
        mPullExtendLayout=getActivity().findViewById(R.id.pull_extend);
        mListHeader=mPullNewHeader.getRecyclerView();

//        mListHeader.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
    }


//    private void huiju_recyclerView() {
//        huiju=(RecyclerView)getActivity().findViewById(R.id.add_view);
//        huiju.setItemAnimator(new DefaultItemAnimator());
//        huiju.setAdapter(new MyAdapter());
//        CardItemTouchHelperCallback callback=new CardItemTouchHelperCallback(huiju.getAdapter(),list);
//        callback.setOnSwipedListener(new OnSwipeListener<Integer>() {
//            @Override
//            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
//                viewHolder.itemView.setAlpha(1 - Math.abs(ratio) * 0.2f);
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, Integer integer, int direction) {
//                if (direction == CardConfig.SWIPED_LEFT) {
//                    // 向左滑动
//                } else {
//                    // 向右滑动
//                }
//            }
//            @Override
//            public void onSwipedClear() {
//// 最后一张也滑动结束，表示内容已空
//                huiju.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        initData();
//                        huiju.getAdapter().notifyDataSetChanged();
//                    }
//                });
//
//            }
//        });
//        final ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        final CardLayoutManager cardLayoutManager = new CardLayoutManager(huiju, touchHelper);
//        huiju.setLayoutManager(cardLayoutManager);
//        touchHelper.attachToRecyclerView(huiju);
//    }
    private void initData() {
        for (int i = 0; i < 30; i++) {
            list1.add("这是第"+i+"项");
        }

    }



//    private class MyAdapter extends RecyclerView.Adapter {
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_smart, parent, false);
//            return new MyViewHolder(view);
//        }
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//            ImageView avatarImageView = ((MyViewHolder) holder).avatarImageView;
//            avatarImageView.setImageResource(list.get(position));
//        }
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            ImageView avatarImageView;
//            MyViewHolder(View itemView) {
//                super(itemView);
//                avatarImageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
//            }
//        }
//    }
}