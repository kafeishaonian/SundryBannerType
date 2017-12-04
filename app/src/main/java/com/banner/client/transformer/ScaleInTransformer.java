package com.banner.client.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ScaleInTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private float mMinScale = DEFAULT_MIN_SCALE;

    public ScaleInTransformer() {

    }

    public ScaleInTransformer(float minScale) {
        this(minScale, NonPageTransformer.INSTANCE);
    }

    public ScaleInTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_SCALE, pageTransformer);
    }


    public ScaleInTransformer(float minScale, ViewPager.PageTransformer pageTransformer) {
        mMinScale = minScale;
        mPageTransformer = pageTransformer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void pageTransformer(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        page.setPivotY(pageHeight / 2);
        page.setPivotX(pageWidth / 2);
        if (position < -1) {
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
            page.setPivotX(pageWidth);
        } else if (position <= 1) {
            if (position < 0) {
                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position)));
            } else {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }
        } else {
            page.setPivotX(0);
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
        }
    }
}