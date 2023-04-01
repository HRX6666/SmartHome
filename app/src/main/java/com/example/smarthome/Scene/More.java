package com.example.smarthome.Scene;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.smarthome.Database.AddModel;
import com.example.smarthome.Page_Samrt.Tesk;
import com.example.smarthome.R;

import org.json.JSONObject;
import org.litepal.LitePal;

public class More extends AppCompatActivity {
    RelativeLayout select_condition,select_tesk;
    Button create;
    EditText model_name;
    String name_ml;
    private JSONObject registerInfo;  //注册返回信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        inite();
        //1.进入后先遍历删除所有Temp数据，之后再创建一个新的temp来储存
        //findfirst来找到当前temp
        //2.智能设备作为条件可以先弄灯的，空调那些可以先不显示
        //灯的就开关嘛
        //3.编写适配器，暂时只显示名称图标和开关
        //4.还有电器的自定义命名
        //

    }

    private void inite() {
        select_condition =findViewById(R.id.select_condition);
        select_tesk=findViewById(R.id.select_tesk);
        create=findViewById(R.id.create);
        model_name=findViewById(R.id.model_name);
        select_tesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this, MissionActivity.class);
                startActivity(intent);
            }
        });
        select_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(More.this, ConditionActivity.class);
                startActivity(intent1);

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
                name_ml=model_name.getText().toString();    //获取输入框值
                //判断输入框中是否有值
                if(TextUtils.isEmpty(name_ml)){
                    Toast.makeText(More.this,"请输入自定义场景名称",Toast.LENGTH_SHORT).show();
                    return;
                }
                AddModel addModel=new AddModel();
                addModel.setModel(name_ml);
                addModel.save();
                finish();


            }
        });
    }


}