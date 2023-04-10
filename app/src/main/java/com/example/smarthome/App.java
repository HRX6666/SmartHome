package com.example.smarthome;

import android.animation.Animator;
import android.app.Application;
import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.smarthome.View.CircularAnim;

/**
 * @author oukanggui
 * @date 2019-07-28
 * 描述
 */
public class App extends Application {
    private static Context sInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        CircularAnim.init(700, 618, R.color.colorPrimary);
        // optional. set the default duration OnAnimatorDeployListener.
        CircularAnim.initDefaultDeployAnimators(
                new CircularAnim.OnAnimatorDeployListener() {
                    @Override
                    public void deployAnimator(Animator animator) {
                        animator.setInterpolator(new AccelerateInterpolator());
                    }
                },
                new CircularAnim.OnAnimatorDeployListener() {
                    @Override
                    public void deployAnimator(Animator animator) {
                        animator.setInterpolator(new DecelerateInterpolator());
                    }
                },
                null,
                null);
    }

    public static Context getContext(){
        return sInstance;
    }
}