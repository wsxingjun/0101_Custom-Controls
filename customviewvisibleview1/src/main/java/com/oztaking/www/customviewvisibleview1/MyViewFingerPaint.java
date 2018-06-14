package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @function
 */

public class MyViewFingerPaint extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();


    public MyViewFingerPaint(Context context) {
        super(context);

    }

    public MyViewFingerPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        //记录初始位置的坐标；
        int x = 0;
        int y = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                mPath.moveTo(x, y);

                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getX();
                int dy = (int) event.getY();
                mPath.lineTo(dx, dy);
                invalidate(); //不断的重绘；
//                x = dx + x;
//                y = dy + y;
                break;
            default:
                break;

        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(mPath,mPaint);
    }

    public void resetCanvas(){
        mPath.reset();
        invalidate();
    }
}
