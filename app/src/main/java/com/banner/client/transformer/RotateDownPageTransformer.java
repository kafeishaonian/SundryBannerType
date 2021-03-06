package com.banner.client.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 旋转下
 * Created by Hongmingwei on 2017/12/4.
 * Email: 648600445@qq.com
 */

public class RotateDownPageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    public RotateDownPageTransformer()
    {
    }

    public RotateDownPageTransformer(float maxRotate)
    {
        this(maxRotate, NonPageTransformer.INSTANCE);
    }

    public RotateDownPageTransformer(ViewPager.PageTransformer pageTransformer)
    {
        this(DEFAULT_MAX_ROTATE, pageTransformer);
    }

    public RotateDownPageTransformer(float maxRotate, ViewPager.PageTransformer pageTransformer)
    {
        mPageTransformer = pageTransformer;
        mMaxRotate = maxRotate;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void pageTransformer(View page, float position) {
        if (position < -1){
            page.setRotation(mMaxRotate * -1);
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight());
        } else if (position <= 1){
            if (position < 0){
                page.setPivotX(page.getWidth() * (DEFAULT_CENTER + DEFAULT_CENTER * (-position)));
                page.setPivotY(page.getHeight());
                page.setRotation(mMaxRotate * position);
            } else {
                page.setPivotX(page.getWidth() * DEFAULT_CENTER * (1 - position));
                page.setPivotY(page.getHeight());
                page.setRotation(mMaxRotate * position);
            }
        } else {
            page.setRotation(mMaxRotate);
            page.setPivotX(page.getWidth() * 0);
            page.setPivotY(page.getHeight());
        }
    }
}
