package com.m520it.neteasynews.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.m520it.neteasynews.R;
import com.m520it.neteasynews.callback.OnRingClickListener;

/**
 * @author Cwugs.Chen.
 * @time 2016/8/12  14:51
 * @desc ${TODD}
 */
public class RingImageView extends View {
    private Paint fontPaint;
    private Paint progressPaint;
    private Paint innerCirclePaint;

    //设置字宽，内边距， 进度条宽度

    private static final int innerWidth = 5;
    private static final int progressWidth = 5;
    private static final String text = "跳过";

    private int pDegress = 360;

    public void setListener(OnRingClickListener listener) {
        this.listener = listener;
    }

    //监听器
    private OnRingClickListener listener;

    //内圆半径， 进度条半径
    private int innerCircleRadius;
    private int progressRadius;
    private int fontWidth;

    public RingImageView(Context context) {
        super(context);
    }

    public RingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //获取属性, attributes使用完成要回收
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RingImageView);
        int fontSize = attributes.getDimensionPixelSize(R.styleable.RingImageView_fontSize, 20);
        int fontColor = attributes.getColor(R.styleable.RingImageView_fontColor, Color.BLACK);
        int circleColor = attributes.getColor(R.styleable.RingImageView_circleColor, Color.WHITE);
        int ringColor = attributes.getColor(R.styleable.RingImageView_ringColor, Color.WHITE);


        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setColor(fontColor);
        fontPaint.setTextSize(fontSize);
        fontWidth = (int) fontPaint.measureText(text);


        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setColor(circleColor);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(ringColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);

        innerCircleRadius = (fontWidth + innerWidth * 2) / 2;
        progressRadius = innerCircleRadius;

        //回收自定义属性
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(progressRadius * 2 + 10, progressRadius * 2 + 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawOval(new RectF(progressWidth, progressWidth, innerCircleRadius * 2 + progressWidth, innerCircleRadius * 2 + progressWidth), innerCirclePaint);

        //写字
        canvas.drawText(text, innerWidth + progressWidth, progressRadius + 5 - ((fontPaint.descent() + fontPaint.ascent()) / 2), fontPaint);


        canvas.save();
        //LogUtils.logI("dpDegress", pDegress+"");
        canvas.rotate(-90, progressRadius + 5, progressRadius + 5);
        canvas.drawArc(new RectF(5, 5, progressRadius * 2 + 5, progressRadius * 2 + 5), 0, pDegress, false, progressPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case  MotionEvent.ACTION_DOWN:
                setAlpha(0.2f);
                break;
            case MotionEvent.ACTION_UP:
                setAlpha(1.0f);
                listener.onClick(this);
                break;
        }
        return true;
    }

    //设置广告条的进度设置

    /**
     * 时间均以毫秒计算
     * @param totalTime  广告停留的时长
     * @param itemTime   间隔刷新ui的时间
     */
    public void setProgress(int totalTime, int itemTime) {
        pDegress -= 360 / (totalTime*1000/itemTime);
        //刷新ui
       // LogUtils.logI("pDegress", pDegress+"");
        invalidate();
    }
}
