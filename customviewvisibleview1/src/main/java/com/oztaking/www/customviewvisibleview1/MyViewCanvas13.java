package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @function
 */

public class MyViewCanvas13 extends View{

    private Paint mPaint;
    private Path mPath;
    private Canvas mBitmapCanvas;
    private Bitmap mBitmap;


    public MyViewCanvas13(Context context) {
        super(context);
        mPaint  = new Paint();
        mPaint  = new Paint();

        //新建了一张空白的图片；
        mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
//        mBitmap = BitmapFactory.decodeResource(this.getContext().getResources(),R.mipmap.ic_launcher);
        //以空白的图片为例，生成了一个canvas


        mBitmapCanvas = new Canvas(mBitmap);
    }

    public MyViewCanvas13(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setTextSize(50);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mBitmapCanvas.translate(100,100);
        mBitmapCanvas.drawText("中国欢迎你",0,100,mPaint);

        canvas.drawColor(Color.CYAN);
        canvas.drawBitmap(mBitmap,0,0,mPaint);
    }
}
