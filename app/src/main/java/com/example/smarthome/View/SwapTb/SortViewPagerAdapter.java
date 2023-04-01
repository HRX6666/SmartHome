package com.example.smarthome.View.SwapTb;

import java.util.List;
import android.view.View;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SortViewPagerAdapter extends PagerAdapter {

    private List<View> mLists;

    public SortViewPagerAdapter(List<View> pLists) {
        this.mLists = pLists;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mLists.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(mLists.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(mLists.get(position));
        return mLists.get(position);
    }

}
