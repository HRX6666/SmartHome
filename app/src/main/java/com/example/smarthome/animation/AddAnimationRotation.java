package com.example.smarthome.animation;

import android.animation.TimeInterpolator;

public class AddAnimationRotation implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        float factor= (float) 0.0001;
        //input的取值范围是0-1，且input的变动时匀速变动
        //根据你动画的变化速度需要返回fraction，返回的值即为动画完成的进度
        //插值器input与估值器中fraction的关系
        //input通过一定的方法转变为一个0-1的数值，该数值即为估值器中的fraction，即getInterpolation返回的值就是估值器中的fraction
        return (float) ((Math.pow(2, -10 * input) * Math.sin((input- factor / 4) * (2 * Math.PI) / factor) + 1));
    }
}