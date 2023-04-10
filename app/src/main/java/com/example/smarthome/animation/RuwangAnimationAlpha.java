package com.example.smarthome.animation;

import android.animation.TimeInterpolator;

public class RuwangAnimationAlpha implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        float cycles = (float) 0.4;
        return (float) Math.sin(2 * cycles *Math.PI * input);
    }
}
