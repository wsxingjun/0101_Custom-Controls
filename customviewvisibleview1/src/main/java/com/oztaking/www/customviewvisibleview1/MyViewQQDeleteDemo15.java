package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @function
 */

public class MyViewQQDeleteDemo15 extends FrameLayout {

    private Paint mPaint;
    private Path mPath;


    private Ponit mCurPoint, mStartPoint;

    private final int DEFAULT_RADIUS = 20;
    private int mRadius = DEFAULT_RADIUS;

    private boolean mTouch;

    private TextView mTextView;
    private ImageView mImageView;
    private boolean isAnimStart;


    public MyViewQQDeleteDemo15(Context context) {
        super(context);
        initView();

    }


    public MyViewQQDeleteDemo15(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    private void initView() {
        mStartPoint = new Ponit(100, 100);
        mCurPoint = new Ponit();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();

        mTextView = createTextView();
        mImageView = createImageView();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //新建图层
        canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), mPaint, Canvas.ALL_SAVE_FLAG);
        //图层画圆

        //手没有接触或者动画开始的时候
        if (!mTouch || isAnimStart) {

            //绘制textView在起始位置；
            mTextView.setX((mStartPoint.x - mTextView.getWidth() / 2));
            mTextView.setY(mStartPoint.y - mTextView.getHeight() / 2);

        }else {
            //如果检测到手指的触摸，画出当前的textview；
            calculatePath();
            canvas.drawCircle(mCurPoint.x, mCurPoint.y, mRadius, mPaint);
            canvas.drawCircle(mStartPoint.x, mStartPoint.y, mRadius, mPaint);

            canvas.drawPath(mPath, mPaint);

            //将textView的中心位置放到手的位置；
            mTextView.setX((mCurPoint.x - mTextView.getWidth() / 2));
            mTextView.setY(mCurPoint.y - mTextView.getHeight() / 2);

        }
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    private TextView createTextView() {
        TextView textView = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setPadding(10, 10, 10, 10);
        textView.setTextSize(20);
        textView.setTextColor(Color.CYAN);
        textView.setBackgroundResource(R.drawable.textbg);
        textView.setText("拖我");
        addView(textView);
        return textView;
    }

    private ImageView createImageView(){
        ImageView imag = new ImageView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        imag.setLayoutParams(params);
        imag.setImageResource(R.drawable.imagebobo);
        imag.setVisibility(INVISIBLE);
        addView(imag);
        return imag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                boolean isTextViewTouch = isTouchRect(mTextView, event);
                if (isTextViewTouch) {
                    mTouch = true;
                }

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                mTouch = false;
                break;
        }

        mCurPoint.setX(event.getX(), event.getY());
        postInvalidate();
        return true;
    }

    private boolean isTouchRect(TextView textView, MotionEvent event) {
        Rect rect = new Rect();
        int[] location = new int[2];
        //获取当前控件所在屏幕的位置，传进去一个location的数组，在执行以后会把left,top值赋给location[0]和location[1]
        textView.getLocationOnScreen(location);

        rect.left = location[0];
        rect.top = location[1];
        rect.right = rect.left + textView.getWidth();
        rect.bottom = rect.top + textView.getHeight();

        if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
            return true;
        }
        return false;
    }

    //计算贝塞尔的路径
    private void calculatePath() {
        float x = mCurPoint.x;
        float y = mCurPoint.y;

        float startX = mStartPoint.x;
        float startY = mStartPoint.y;

        //根据距离变化圆的半径；
        float distance = (float) Math.sqrt(Math.pow(y-startY,2) + Math.pow(x - startX,2));
        mRadius = (int) (DEFAULT_RADIUS - distance/5);
        if (mRadius < 9){
            isAnimStart = true;
            mImageView.setX(mCurPoint.x - mTextView.getWidth()/2);
            mImageView.setY(mCurPoint.y - mTextView.getHeight()/2);
            mImageView.setVisibility(VISIBLE);
            //直接强转，然后开始动画；
            ((AnimationDrawable)mImageView.getDrawable()).start();
            mTextView.setVisibility(GONE);
//            mRadius = 9;

        }

        //根据角度计算出四边形的四个点；
        float dx = x - startX;
        float dy = y - startY;

        double a = Math.atan(dy / dx);
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

        float anchorX = (startX + x) / 2;
        float anchorY = (startY + y) / 2;

        //连线成为一个封闭的图形；
        mPath.reset();
        mPath.moveTo(x0, y0);
        mPath.quadTo(anchorX, anchorY, x1, y1);
        mPath.lineTo(x2, y2);
        mPath.quadTo(anchorX, anchorY, x3, y3);
        mPath.lineTo(x0, y0);
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

        public void setX(float x, float y) {
            this.x = x;
            this.y = y;
        }


    }
}
