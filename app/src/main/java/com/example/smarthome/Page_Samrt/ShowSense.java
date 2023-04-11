package com.example.smarthome.Page_Samrt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.smarthome.R;
import com.example.smarthome.View.SelfTextView;

public class ShowSense extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sense);
        final SelfTextView tv1 = ((SelfTextView) findViewById(R.id.tv1));
        tv1.setmTitleText(1.15f, 2);
        tv1.setDuration(2000);
        tv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv1.setmTitleText(1.15f, 2);
                tv1.requestLayout();
            }

        });
        final TextView refresh = (TextView) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setmTitleText(1.15f, 2);
                tv1.requestLayout();

                refresh.startAnimation(AnimationUtils.loadAnimation(ShowSense.this, R.anim.btn_anim));
            }
        });
    }


}