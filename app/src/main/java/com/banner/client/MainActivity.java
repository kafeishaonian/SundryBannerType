package com.banner.client;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.banner.client.transformer.AlphaPageTransformer;
import com.banner.client.transformer.DepthPageTransformer;
import com.banner.client.transformer.GalleyTransFormer;
import com.banner.client.transformer.RotateDownPageTransformer;
import com.banner.client.transformer.RotateYTransformer;
import com.banner.client.transformer.ScaleInTransformer;
import com.banner.client.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * TAG
     */
    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * View
     */
    private LoopViewPager mGalleryView;
    private LinearLayout mPointContainerLinearLayout;


    /**
     * params
     */
    private BannerAdapter mAdapter;
    private List<String> topList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startRequest();
    }



    private void initView() {
        mGalleryView = (LoopViewPager) findViewById(R.id.gallery_banner_layout);
        mPointContainerLinearLayout = (LinearLayout) findViewById(R.id.gallery_point_container);

        mAdapter = new BannerAdapter(this);
        mGalleryView.setPageMargin(5);
        mGalleryView.setOffscreenPageLimit(3);
        mGalleryView.setAdapter(mAdapter);
        mGalleryView.setAutoStart(true);
//        mGalleryView.setPageTransformer(true, new RotateYTransformer());
//        mGalleryView.setPageTransformer(true, new RotateDownPageTransformer());
//        mGalleryView.setPageTransformer(true, new ScaleInTransformer());
//        mGalleryView.setPageTransformer(true, new GalleyTransFormer());
//        mGalleryView.setPageTransformer(true, new AlphaPageTransformer());
//        mGalleryView.setPageTransformer(true, new DepthPageTransformer());
        mGalleryView.setPageTransformer(true, new ZoomOutPageTransformer());
    }


    private void startRequest() {
        topList.clear();
        topList.add("https://chseawebres.cdn.zhhqice.cn/image/2017/08/25/upload2014082204.jpg");
        topList.add("https://chseawebres.cdn.zhhqice.cn/image/2017/05/26/upload%E5%81%87%E6%9C%9F%E5%85%AC%E5%91%8A.png");
        topList.add("https://chseawebres.cdn.zhhqice.cn/image/2017/08/01/upload7c34f82de6a5c13a7730a09ce257ce6e.jpg");
        addViewData(topList);
    }

    @Override
    public void onResume() {
        super.onResume();
        mGalleryView.addOnPageChangeListener(listener);
        if (mGalleryView != null) {
            mGalleryView.startFlipping();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mGalleryView.removeOnPageChangeListener(listener);
        if (mGalleryView != null) {
            mGalleryView.stopFlipping();
        }
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (imageViewList.size() <= 0) {
                return;
            }

            boolean nearLeftEdge = (position <= imageViewList.size());
            boolean nearRightEdge = (position >= mAdapter.getCount() - imageViewList.size());
            if (nearLeftEdge || nearRightEdge) {
                mGalleryView.setCurrentItem(mAdapter.getCenterPosition(0), false);
            }

            // dot 索引
            int pos = position % imageViewList.size();
            changeImageView(imageViewList.get(pos));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // 循环导点图
    public void changeImageView(View v) {
        try {
            for (int i = 0; i < imageViewList.size(); i++) {
                imageViewList.get(i).setSelected(false);
            }
            v.setSelected(true);
        } catch (Exception e) {
            Log.e(TAG, "推荐页焦点图切换发生异常!!!", e);
        }
    }


    /**
     * 设置数据展示view
     */
    // 在外部listview实现控制自动滑动
    public void addViewData(final List<String> videoList) {
        mAdapter.addAll(videoList);
        buildPointContainerLayout(this, videoList);
        if (imageViewList.size() > 0) {
            changeImageView(imageViewList.get(0));
        }

        mGalleryView.setCurrentItem(mAdapter.getCenterPosition(0));
    }

    /**
     * 添加导航点indicator
     *
     * @param context
     * @param videolist
     */
    private void buildPointContainerLayout(Context context, List<String> videolist) {
        if (null != videolist && videolist.size() > 0) {
            android.widget.LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageViewList.clear();
            mPointContainerLinearLayout.removeAllViews();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.bottomMargin = getResources().getDimensionPixelSize(
                    R.dimen.banner_maginbottom);
            int size = videolist.size();
            int imageMarginSize = getResources().getDimensionPixelSize(R.dimen.banner_image_magin);
            for (int i = 0; i < size; i++) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(R.drawable.gallery_switch_selector);
                imageView.setPadding(imageMarginSize, 0, 0, 0);
                imageView.setId(i);
                imageViewList.add(imageView);
                mPointContainerLinearLayout.addView(imageView);
                Log.d(TAG, "buildPointContainerLayout ==========  " + i);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
