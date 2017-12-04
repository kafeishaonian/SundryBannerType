package com.banner.client;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 64860 on 2017/7/25.
 */

public class BannerAdapter extends PagerAdapter {
    /**
     * TAG
     */
    private static final String TAG = BannerAdapter.class.getSimpleName();
    /**
     * params
     */
    public static final int NUMBER_OF_LOOPS = 10000;
    private WeakReference<Context> mContext;
    private LayoutInflater mInflater;
    private List<String> listModels;

    public BannerAdapter(Context context){
        mContext = new WeakReference<Context>(context);
        mInflater = (LayoutInflater) mContext.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listModels = new ArrayList<>();
    }

    public void addAll(List<String> lists){
        listModels.clear();
        listModels.addAll(lists);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return listModels.size() * NUMBER_OF_LOOPS;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final String podcast = getValues(position);
        View view = mInflater.inflate(R.layout.listitem_banner_facer, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.focus_item_img);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(container.getContext()).load(podcast).placeholder(R.mipmap.ic_launcher).into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getCenterPosition(int position){
        return listModels.size() > 0 ? listModels.size() * NUMBER_OF_LOOPS / 2 + position : 0;
    }

    public String getValues(int position){
        return listModels.size() > 0 ? listModels.get(position % listModels.size()) :null;
    }

    private void startActivity(){
            Intent intent = new Intent(mContext.get(), MainActivity.class);
            mContext.get().startActivity(intent);
    }
}
