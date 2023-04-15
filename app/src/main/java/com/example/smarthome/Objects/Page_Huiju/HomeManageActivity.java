package com.example.smarthome.Objects.Page_Huiju;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Adapter.AddHomesAdapter;
import com.example.smarthome.Database.AddHomes;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class HomeManageActivity extends AppCompatActivity {
    Toolbar manageHome_back;
    RecyclerView recyclerView_home;
    private List<AddHomes> addHomesList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homemanage);
        recyclerView_home=findViewById(R.id.recyclerView_home);
        manageHome_back=findViewById(R.id.manageHome_back);
        manageHome_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }
    //TODO 先弄场景条件展示
    private void init() {
        addHomesList= LitePal.findAll(AddHomes.class);
        if(addHomesList.size()!=0)
        {
            AddHomesAdapter addHomesAdapter=new AddHomesAdapter(addHomesList);
            LinearLayoutManager layoutManager=new LinearLayoutManager(HomeManageActivity.this);
            addHomesAdapter.setContext(HomeManageActivity.this);
            recyclerView_home.setLayoutManager(layoutManager);
            recyclerView_home.setAdapter(addHomesAdapter);
            addHomesAdapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

}
