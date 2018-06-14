package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 * 自定义控件三部曲之绘图篇(七)——Paint之函数大汇总
 * @function
 */

public class MyViewPaintAll extends View {

    private Paint mPaint;
    private Path mPath;

    public MyViewPaintAll(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);

        mPath = new Path();

    }

    public MyViewPaintAll(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //【1】线冒
//        drawCap(canvas);
        //【2】线的结合处
//        drawStrokeJoin(canvas);
        //【3】圆角拐角
//        drawConnerPathEffect(canvas);
        //【4】虚线
//         drawDashEffect(canvas);
        //【5】
//        drawDiscretePathEffect(canvas);
        //【6】印章路径
//        drawPathDashEffect(canvas);
        //【7】印章路径的多种特效；
//        drawPathDashEffectMulti(canvas);

        //【8】多种特效的合并
//        MultiEffect(canvas);

        //【9】亚像素
        subTextPixel(canvas);
    }

    /**
     * 线冒
     */
    private void drawCap(Canvas canvas) {
        //无线冒
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100, 100, 300, 100, mPaint);
        //圆线冒
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100, 200, 300, 200, mPaint);
        //方形线冒
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100, 300, 300, 300, mPaint);

        //对比标准线
        mPaint.reset();
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.RED);
        canvas.drawLine(100, 50, 100, 500, mPaint);
    }


    /**
     * 线的结合处
     */
    private void drawStrokeJoin(Canvas canvas) {
        mPaint.reset();
        mPaint.setStrokeWidth(40);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);


        mPath.moveTo(100, 100);
        mPath.lineTo(450, 100);
        mPath.lineTo(100, 300);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(mPath, mPaint);

        mPath.moveTo(100, 400);
        mPath.lineTo(450, 400);
        mPath.lineTo(100, 600);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(mPath, mPaint);

        mPath.moveTo(100, 700);
        mPath.lineTo(450, 700);
        mPath.lineTo(100, 900);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(mPath, mPaint);
    }

    //    线的圆形拐角
    private void drawConnerPathEffect(Canvas canvas) {
        mPaint.reset();
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath.reset();
        mPath.moveTo(100, 600);
        mPath.lineTo(300, 100);
        mPath.lineTo(500, 600);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(mPath, mPaint);


        mPaint.setColor(Color.YELLOW);
        mPaint.setPathEffect(new CornerPathEffect(200));
        canvas.drawPath(mPath, mPaint);

    }

    /**
     * 虚线效果
     * @param canvas
     */
    private void drawDashEffect(Canvas canvas){
        mPaint.reset();
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath.reset();
        mPath.moveTo(100, 600);
        mPath.lineTo(300, 100);
        mPath.lineTo(500, 600);
        canvas.drawPath(mPath, mPaint);


        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(new DashPathEffect(new float[]{20,5,50,10},100));
        canvas.translate(0,30);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.CYAN);
        mPaint.setPathEffect(new DashPathEffect(new float[]{20,5,50,10},15));
        canvas.translate(0,30);
        canvas.drawPath(mPath, mPaint);


    }

    /**
     * 离散路径效果
     * @param canvas
     */
    private void drawDiscretePathEffect(Canvas canvas){
        mPaint.reset();
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath.reset();
        mPath.moveTo(0, 100);

        for (int i=0; i<40; i++){
            mPath.lineTo(i*35,(float)(Math.random()*150));
        }
        //原生图像
        canvas.drawPath(mPath, mPaint);
        //
        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(new DiscretePathEffect(2,5));
        canvas.translate(0,200);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.CYAN);
        mPaint.setPathEffect(new DiscretePathEffect(1,15));
        canvas.translate(0,200);
        canvas.drawPath(mPath, mPaint);

    }

    /**
     *  印章路径
     */

    private void drawPathDashEffect(Canvas canvas){
        mPaint.reset();
        mPath.reset();
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath.moveTo(100,600);
        mPath.lineTo(400,100);
        mPath.lineTo(700,900);
        canvas.drawPath(mPath,mPaint);

        Path stampPath = new Path();
        //绘制了一个三角形
        stampPath.moveTo(0,20);
        stampPath.lineTo(10,0);
        stampPath.lineTo(20,20);
        stampPath.close();

        //圆上画了一个三角形
        stampPath.addCircle(0,0,3, Path.Direction.CCW);
        canvas.drawPath(stampPath,mPaint);

        canvas.translate(0,200);
        mPaint.setPathEffect(
                new PathDashPathEffect(stampPath,50,0,PathDashPathEffect.Style.TRANSLATE));
        canvas.drawPath(mPath,mPaint);


    }

    /**
     *  印章路径,多种路径对比
     */

    private void drawPathDashEffectMulti(Canvas canvas){
        mPaint.reset();
        mPath.reset();
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        Path stampPath = new Path();
        //绘制了一个三角形
        stampPath.moveTo(0,20);
        stampPath.lineTo(10,0);
        stampPath.lineTo(20,20);
        stampPath.close();

        //圆上画了一个三角形
        stampPath.addCircle(0,0,3, Path.Direction.CCW);
        canvas.drawPath(stampPath,mPaint);

//        canvas.translate(0,200);
//        mPaint.setPathEffect(
//                new PathDashPathEffect(stampPath,50,0,PathDashPathEffect.Style.TRANSLATE));
//        canvas.drawPath(mPath,mPaint);

        mPath.moveTo(0,0);
        for (int i=0; i<40; i++){
            mPath.lineTo(i*35, (float) (Math.random() * 150));
        }

        canvas.drawPath(mPath,mPaint);

        canvas.translate(0,200);
        mPaint.setPathEffect(new PathDashPathEffect(stampPath,35,0,
                PathDashPathEffect.Style.MORPH));
        canvas.drawPath(mPath,mPaint);

        canvas.translate(0,200);
        mPaint.setPathEffect(new PathDashPathEffect(stampPath,35,0,
                PathDashPathEffect.Style.ROTATE));
        canvas.drawPath(mPath,mPaint);

        canvas.translate(0,200);
        mPaint.setPathEffect(new PathDashPathEffect(stampPath,35,0,
                PathDashPathEffect.Style.TRANSLATE));
        canvas.drawPath(mPath,mPaint);

    }

    /**
     * 特效合并
     */

    private void MultiEffect(Canvas canvas){
        mPaint.reset();
        mPath.reset();

        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);

        //
        mPath.moveTo(10,10);
        for (int i=0; i<40; i++){
            mPath.lineTo(i*35, (float) (Math.random() * 150));
        }
//        普通线
        canvas.drawPath(mPath,mPaint);

        //圆角弧线
        canvas.translate(0,200);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(100);
        mPaint.setPathEffect(cornerPathEffect);
        canvas.drawPath(mPath,mPaint);

        //虚线特效
        canvas.translate(0,200);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{2, 5, 10, 10}, 0);
        mPaint.setPathEffect(dashPathEffect);
        canvas.drawPath(mPath,mPaint);

        //合成特效；composePathEffect
        //于原始路径，得到路径B，然后再在路径B的基础上应用虚线特效得到最终的效果D;
        canvas.translate(0,200);
        ComposePathEffect composePathEffect = new ComposePathEffect(dashPathEffect,
                cornerPathEffect);
        mPaint.setPathEffect(composePathEffect);
        canvas.drawPath(mPath,mPaint);


        //合成特效：SumpathEffect
        //先将圆角特效应用于原始路径A得到路径B,然后将虚线特效应依然应用于原始路径，得到路径C.然后将路径B和路径C合并（
        canvas.translate(0,200);
        SumPathEffect sumPathEffect = new SumPathEffect(dashPathEffect, cornerPathEffect);
        mPaint.setPathEffect(sumPathEffect);
        canvas.drawPath(mPath,mPaint);


    }

    /**
     * 亚像素
     */

    private void subTextPixel(Canvas canvas){
        mPaint.reset();
        mPath.reset();
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setSubpixelText(false);
        mPaint.setTextSize(100);
        mPaint.setTextScaleX(5);
        canvas.drawText("抱抱",50,150,mPaint);

        canvas.translate(0,100);
        mPaint.setSubpixelText(true);
        mPaint.setTextScaleX(5);
        mPaint.setTextSize(100);
        canvas.drawText("抱抱",50,150,mPaint);



    }


}
