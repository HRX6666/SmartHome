package com.example.smarthome.animation;

import android.animation.TimeInterpolator;

public class SunAnimatorMoverment implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        float factor = (float) 0.4;

        return (float) ((float)Math.pow(2, -10 * input) * Math.sin((input - factor / 4) * (2 *Math.PI) / factor) + 1);
    }
}
