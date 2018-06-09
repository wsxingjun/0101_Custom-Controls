package com.oztaking.www.customviewprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * @function:
 */

public class HorizontalProgressBarWithNum extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_COLOR = 0XFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACHED_COLOR = 0xFFd3d6da;
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 2;
    private static final int DEFAULT_SIZE_TEXT_OFFSET = 10;

    protected int mUnreached_color = DEFAULT_COLOR_UNREACHED_COLOR;
    protected int mReached_color = DEFAULT_TEXT_COLOR;
    protected int mReached_bar_height = DEFAULT_HEIGHT_REACHED_PROGRESS_BAR;
    protected int mUnreached_bar_height = DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR;
    protected int mTextOffset = DEFAULT_SIZE_TEXT_OFFSET;

    protected int mRealWidth;

    protected boolean mIfDrawText = true;

    protected static final int VISIBLE = 0;

    private Paint mPaint;

    /**
     * color of progress number
     */
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    /**
     * size of text (sp)
     */
    protected int mTextSize = sp2px(DEFAULT_TEXT_SIZE);


    public HorizontalProgressBarWithNum(Context context) {
        //        super(context);
        this(context, null);
    }

    public HorizontalProgressBarWithNum(Context context, AttributeSet attrs) {
        //        super(context, attrs);
        this(context, attrs, 0);
    }

    public HorizontalProgressBarWithNum(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(true);
        obtainStyledAttributes(attrs);

        mPaint = new Paint();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);

    }

    //获取设置的属性
    private void obtainStyledAttributes(AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable
                .HorizontalProgressBarWithNum);

        mTextColor = typedArray.getColor(R.styleable.HorizontalProgressBarWithNum_progress_text_color, DEFAULT_TEXT_COLOR);
        mTextSize = typedArray.getInt(R.styleable.HorizontalProgressBarWithNum_progress_text_size, mTextSize);

        mReached_color = typedArray.getColor(R.styleable.HorizontalProgressBarWithNum_progress_reached_color,mTextColor);

        mUnreached_color = typedArray.getColor(R.styleable.HorizontalProgressBarWithNum_progress_unreached_color, mTextColor);

        mReached_bar_height = (int) typedArray.getDimension(R.styleable.HorizontalProgressBarWithNum_progress_reached_bar_height,
                        mReached_bar_height);
        mUnreached_bar_height = (int) typedArray.getDimension(R.styleable.HorizontalProgressBarWithNum_progress_unreached_bar_height,
                        mUnreached_bar_height);
        mTextOffset = (int) typedArray.getDimension(R.styleable.HorizontalProgressBarWithNum_progress_text_offset,
                        mTextOffset);
        int textVisible = typedArray.getInt(R.styleable.HorizontalProgressBarWithNum_progress_text_visibility,VISIBLE);
        if (textVisible != VISIBLE){
            mIfDrawText = false;
        }
        typedArray.recycle();
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY){
            float textHeight = (mPaint.descent() + mPaint.ascent());//获取绘制文字的高度：mPaint.descent() + mPaint.ascent()
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() +
                                Math.max(Math.max(mReached_bar_height,mUnreached_bar_height),Math.abs(textHeight)));
            MeasureSpec.makeMeasureSpec(exceptHeight,MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        //画笔平移到指定paddingLeft， getHeight() / 2位置，注意以后坐标都为以此为0
        canvas.translate(getPaddingLeft(),getHeight()/2);
        boolean noNeedBg = false;

        //当前进度和总值的比例
        float radio = getProgress() * 1.0f /getMax();

        //已到达的宽度
        float progressPosX = (int)(mRealWidth * radio);

        //绘制文本
        String text = getProgress() + "%";

        //获取字体的宽度和高度
        int textWidth = (int) mPaint.measureText(text);
        int textHeight = (int) ((mPaint.descent() + mPaint.ascent()) / 2);

        //如果到达最后，则未到达的进度条不需要绘制
        if (progressPosX + textWidth > mRealWidth){
            progressPosX = mRealWidth - textWidth;
            noNeedBg = true;
        }

        //绘制已经到达的进度
        float endX = progressPosX - mTextOffset / 2;
        if (endX > 0){
            mPaint.setColor(mReached_color);
            mPaint.setStrokeWidth(mReached_bar_height);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //绘制文本
        if (mIfDrawText){
            mPaint.setColor(mTextColor);
            canvas.drawText(text,progressPosX,-textHeight,mPaint);
        }

        if (!noNeedBg){
            float start = progressPosX + mTextOffset /2 + textWidth;
            mPaint.setColor(mUnreached_color);
            mPaint.setStrokeWidth(mUnreached_bar_height);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }

        canvas.restore();
    }

    /**
     * 在控件大小发生改变时调用。所以这里初始化会被调用一次
     * 作用：获取控件的宽和高度
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();
    }

    protected int sp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    protected int px2dp(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }


}
