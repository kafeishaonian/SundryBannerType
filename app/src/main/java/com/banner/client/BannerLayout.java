package com.banner.client;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 比例布局
 * Created by 64860 on 2017/7/24.
 */

public class BannerLayout extends RelativeLayout{

    public BannerLayout(Context context) {
        super(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int halfChildWidthSize = getMeasuredWidth();
        int childHeightSize = (300 * halfChildWidthSize) / 720;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(halfChildWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
