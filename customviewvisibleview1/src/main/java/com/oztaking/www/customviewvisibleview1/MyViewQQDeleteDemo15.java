package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @function
 */

public class MyViewQQDeleteDemo15 extends FrameLayout{

    private Paint mPaint;
    private Path mPath;


    private Ponit mCurPoint,mStartPoint;
    private int mRadius = 20;

    private boolean mTouch;


    public MyViewQQDeleteDemo15(Context context) {
        super(context);
        initView();

    }


    public MyViewQQDeleteDemo15(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        mStartPoint = new Ponit(100,100);
        mCurPoint = new Ponit();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //新建图层
        canvas.saveLayer(new RectF(0,0,getWidth(),getHeight()),mPaint,Canvas.ALL_SAVE_FLAG);
        //图层画圆
        canvas.drawCircle(mStartPoint.x,mStartPoint.y,mRadius,mPaint);
        //如果检测到手指的触摸，画出当前的圆
        if (mTouch){
            canvas.drawCircle(mCurPoint.x,mCurPoint.y,mRadius,mPaint);
        }
        canvas.restore();


        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTouch = true;
            break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                mTouch = false;
                break;

        }

        mCurPoint.setX(event.getX(),event.getY());
        postInvalidate();
        return true;
    }

    private class Ponit {
        private float x;
        private float y;

        public Ponit() {
        }

        public Ponit(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void setX(float x,float y) {
            this.x = x;
            this.y = y;
        }


    }
}
