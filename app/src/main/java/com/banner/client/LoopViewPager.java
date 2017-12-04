package com.banner.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by 64860 on 2017/7/24.
 */

public class LoopViewPager extends ViewPager {

    private static final String TAG = LoopViewPager.class.getSimpleName();
    /**
     * params
     */
    private static final int DEFAULT_INTERVAL = 3000;
    private int mFlipInterval = DEFAULT_INTERVAL;
    private boolean mAutoStart = false;
    private boolean mRunning = false;
    private boolean mStarted = false;
    private boolean mVisible = false;
    private boolean mTouching = false;
    private boolean mUserPresent = true;

    public LoopViewPager(Context context) {
        super(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void showNext() {
        int kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        int next = getCurrentItem() + 1;
        int size = getChildCount() * BannerAdapter.NUMBER_OF_LOOPS / 2 + next;
        if (size != 0) {
            setCurrentItem(next % size, true);
        }

        onKeyDown(kEvent, null);
    }

    /**
     * 设置滑动时间间隔
     *
     * @param milliseconds time in milliseconds
     */
    public void setFlipInterval(int milliseconds) {
        mFlipInterval = milliseconds;
    }

    /**
     * 开始循环滑动
     */
    public void startFlipping() {
        mStarted = true;
        updateRunning();
    }

    /**
     * 停止滑动
     */
    public void stopFlipping() {
        mStarted = false;
        updateRunning();
    }

    /**
     * Internal method to start or stop dispatching flip {@link Message} based
     * on {@link #mRunning} and {@link #mVisible} state.
     */
    private void updateRunning() {
        updateRunning(true);
    }

    /**
     * Internal method to start or stop dispatching flip {@link Message} based
     * on {@link #mRunning} and {@link #mVisible} state.
     *
     * @param flipNow Determines whether or not to execute the animation now, in
     *                addition to queuing future flips. If omitted, defaults to
     *                true.
     */
    private void updateRunning(boolean flipNow) {
        boolean running = mVisible && mStarted && !mTouching && mUserPresent;
        if (running != mRunning) {
            if (running) {
                Message msg = mHandler.obtainMessage(FLIP_MSG);
                mHandler.sendMessageDelayed(msg, mFlipInterval);
            } else {
                mHandler.removeMessages(FLIP_MSG);
            }
            mRunning = running;
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mUserPresent = false;
                updateRunning();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mUserPresent = true;
                updateRunning(false);
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVisible = false;

        getContext().unregisterReceiver(mReceiver);

        updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == VISIBLE;
        updateRunning(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Listen for broadcasts related to user-presence
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        getContext().registerReceiver(mReceiver, filter);

        if (mAutoStart) {
            startFlipping();
        }
    }

    public boolean isFlipping() {
        return mStarted;
    }

    public void setAutoStart(boolean autoStart) {
        mAutoStart = autoStart;
    }

    public boolean isAutoStart() {
        return mAutoStart;
    }

    private final int FLIP_MSG = 1;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FLIP_MSG) {
                if (mRunning) {
                    showNext();
                    msg = obtainMessage(FLIP_MSG);
                    sendMessageDelayed(msg, mFlipInterval);
                }
            }
        }
    };

    /**
     * 触发点击事件时，停止滑动
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (mRunning) {
                mTouching = true;
                updateRunning();
            }
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {

        } else {
            if (mTouching) {
                mTouching = false;
                updateRunning();
            }
        }
        return super.dispatchTouchEvent(ev);

    }
}
