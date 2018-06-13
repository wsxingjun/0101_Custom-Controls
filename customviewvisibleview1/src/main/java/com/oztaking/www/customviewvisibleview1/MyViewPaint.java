package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @function: 《自定义控件三部曲之绘图篇（一）：概述及基本几何图形绘制》
 *
 * 参考文章：
 * https://blog.csdn.net/u014702653/article/details/80342114
 * https://blog.csdn.net/u014702653/article/details/80341070
 *
 *
 */

public class MyViewPaint extends View{
    Context mContext;
    Paint mPaint ;
    public MyViewPaint(Context context) {
        super(context);
        mContext = context;
        mPaint = new Paint();
    }

    public MyViewPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPaint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
//        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(10,15,15, Color.BLUE);


        canvas.drawRGB(255,255,255);


        //画圆
        canvas.drawCircle(200,200,150,mPaint);
        //画直线
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(0, 300, 500,300 , mPaint);
        //画多条直线
        mPaint.setColor(Color.GREEN);
        float []pts={10,10,100,100,200,200,400,400};
        canvas.drawLines(pts, mPaint);
        //画点
        mPaint.setColor(Color.RED);
        canvas.drawPoint(600,600,mPaint);
        //画多个点
        float [] pts1 = new float[]{100,600,150,600,180,600,200,700};
        canvas.drawPoints(pts1,0,8,mPaint);
        //画矩形
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(10, 400, 100, 450, mPaint);//直接构造

        RectF rect = new RectF(120, 400, 210, 450);
        canvas.drawRect(rect, mPaint);//使用RectF构造

        Rect rect2 =  new Rect(230, 400, 320, 450);
        canvas.drawRect(rect2, mPaint);//使用Rect构造

        //画圆角矩形
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(120, 500, 210, 700);
        canvas.drawRoundRect(rectF,10,60,mPaint);

        //画椭圆
        RectF rectF1 = new RectF(100, 750, 200, 1000);
        canvas.drawRect(rectF1,mPaint);

        mPaint.setColor(Color.DKGRAY);
        canvas.drawOval(rectF1,mPaint);


        //画弧
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF2 = new RectF(300, 400, 500, 600);
        canvas.drawArc(rectF2,90,180,true,mPaint);

        RectF rectF3 = new RectF(300, 550, 500, 750);
        canvas.drawArc(rectF3,90,270,false,mPaint);



    }
}
