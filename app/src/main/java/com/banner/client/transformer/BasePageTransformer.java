package com.banner.client.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Hongmingwei on 2017/12/4.
 * Email: 648600445@qq.com
 */

public abstract class BasePageTransformer implements ViewPager.PageTransformer {

    protected ViewPager.PageTransformer mPageTransformer = NonPageTransformer.INSTANCE;

    public static final float DEFAULT_CENTER = 0.5f;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View page, float position) {
        if (mPageTransformer != null){
            mPageTransformer.transformPage(page, position);
        }
        pageTransformer(page, position);
    }

    protected abstract void pageTransformer(View page, float position);
}
