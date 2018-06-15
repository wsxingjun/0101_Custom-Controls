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
            calculatePath();
            canvas.drawCircle(mCurPoint.x,mCurPoint.y,mRadius,mPaint);
            canvas.drawPath(mPath,mPaint);
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

    //计算贝塞尔的路径
    private void calculatePath(){
        float x = mCurPoint.x;
        float y = mCurPoint.y;

        float startX = mStartPoint.x;
        float startY = mStartPoint.y;

        //根据角度计算出四边形的四个点；
        float dx = x - startX;
        float dy = y - startY;

        double a = Math.atan(dy/dx);
        float offsetX = (float) (mRadius * Math.sin(a));
        float offsetY = (float) (mRadius * Math.cos(a));

        float x0 = startX + offsetX;
        float y0 = startY - offsetY;

        float x1 = x + offsetX;
        float y1 = y - offsetY;

        float x2 = x - offsetX;
        float y2 = y + offsetY;

        float x3 = startX - offsetX;
        float y3 = startY + offsetY;

        float anchorX = (startX + x)/2;
        float anchorY = (startY + y)/2;

        //连线成为一个封闭的图形；
        mPath.reset();
        mPath.moveTo(x0,y0);
        mPath.quadTo(anchorX,anchorY,x1,y1);
        mPath.lineTo(x2,y2);
        mPath.quadTo(anchorX,anchorY,x3,y3);
        mPath.lineTo(x0,y0);
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
