package com.banner.client.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateUpPageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    public RotateUpPageTransformer() {
    }

    public RotateUpPageTransformer(float maxRotate) {
        this(maxRotate, NonPageTransformer.INSTANCE);
    }

    public RotateUpPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MAX_ROTATE, pageTransformer);
    }

    public RotateUpPageTransformer(float maxRotate, ViewPager.PageTransformer pageTransformer) {
        mMaxRotate = maxRotate;
        mPageTransformer = pageTransformer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void pageTransformer(View page, float position) {
        if (position < -1) {
            page.setRotation(mMaxRotate);
            page.setPivotX(page.getWidth());
            page.setPivotY(0);

        } else if (position <= 1) {
            if (position < 0) {
                page.setPivotX(page.getWidth() * (0.5f + 0.5f * (-position)));
                page.setPivotY(0);
                page.setRotation(-mMaxRotate * position);
            } else {
                page.setPivotX(page.getWidth() * 0.5f * (1 - position));
                page.setPivotY(0);
                page.setRotation(-mMaxRotate * position);
            }
        } else {
            page.setRotation(-mMaxRotate);
            page.setPivotX(0);
            page.setPivotY(0);
        }
    }
}