package com.m520it.neteasynews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/18  21:03
 * @desc ${TODD}
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
