package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @function
 */

public class MyView extends View{

        private Path mPath = new Path();
        private float mPreX,mPreY;

        public MyView(Context context) {
            super(context);
        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:{
                    mPath.moveTo(event.getX(),event.getY());
                    mPreX = event.getX();
                    mPreY = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_MOVE:{
                    float endX = (mPreX+event.getX())/2;
                    float endY = (mPreY+event.getY())/2;
                    mPath.quadTo(mPreX,mPreY,endX,endY);
                    mPreX = event.getX();
                    mPreY =event.getY();
                    invalidate();
                }
                break;
                default:
                    break;
            }
            return super.onTouchEvent(event);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(2);

            canvas.drawPath(mPath,paint);
        }

        public void reset(){
            mPath.reset();
            postInvalidate();
        }
    }



