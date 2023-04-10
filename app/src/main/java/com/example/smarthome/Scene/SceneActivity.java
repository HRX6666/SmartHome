package com.example.smarthome.Scene;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.Database.Scene.Scene;
import com.example.smarthome.R;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SceneActivity extends AppCompatActivity {
    private List<Scene> sceneList=new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar scene_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sceneactivity);
        scene_back=findViewById(R.id.scene_back);

        initListener();
        initRecyclerView();
    }

    private void initRecyclerView() {
        initContent();
    }
    private void initContent(){
        sceneList.clear();
        sceneList= LitePal.findAll(Scene.class);


    }
    private void initListener(){
        scene_back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
