package com.yoti.mobile.android.sample.sdk.webview.ui;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.util.AttributeSet;

public class SquarePercentLayout extends PercentRelativeLayout {
    public SquarePercentLayout(Context context) {
        super(context);
    }

    public SquarePercentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquarePercentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int size =  w > h ? h : w;
        setMeasuredDimension(size, size);
    }
}
