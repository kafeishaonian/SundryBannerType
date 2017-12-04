package com.banner.client.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Hongmingwei on 2017/12/4.
 * Email: 648600445@qq.com
 */

public class NonPageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setScaleX(0.999f);
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}
