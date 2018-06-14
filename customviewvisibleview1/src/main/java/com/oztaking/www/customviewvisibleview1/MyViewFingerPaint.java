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

    /**
     * 说明：起始点的坐标需要设置全局变量，否则会出现起点坐标不动的问题；会出现放射线；
     */
    public float controlX ;
    public float controlY ;


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
//         float controlX = 0f;
//         float controlY = 0f;


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                controlX = event.getX();
                controlY = event.getY();
                mPath.moveTo(controlX, controlY);

                break;

            case MotionEvent.ACTION_MOVE:
                float dx = event.getX();
                float dy = event.getY();

                float endX = (controlX + dx) / 2;
                float endY = (controlY + dy) /2;

                mPath.quadTo(controlX,controlY,endX,endY);


//                mPath.lineTo(dx, dy);


                break;
            default:
                break;

        }

        controlX = event.getX();
        controlY = event.getY();
        invalidate(); //不断的重绘；

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
