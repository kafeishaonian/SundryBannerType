package com.banner.client.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by zbwx on 2017/11/28.
 * viewpager切换动画
 */
public class GalleyTransFormer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9f;
    private static final float MAX_SCALE = 1f;

    @Override
    public void transformPage(View view, float position) {

        if (position < -1) {
            position = -1;
        } else if (position > 1) {
            position = 1;
        }

        float tempScale = position < 0 ? 1 + position : 1 - position;

        float slope = (MAX_SCALE - MIN_SCALE) / 1;
        //一个公式
        float scaleValue = MIN_SCALE + tempScale * slope;
        view.setScaleX(scaleValue);
        view.setScaleY(scaleValue);
    }
}
