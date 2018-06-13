package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * @function:
 */

public class MyViewDrawText  extends View{
    private Paint mPaint;

    public MyViewDrawText(Context context) {
        super(context);
        mPaint = new Paint();

    }

    public MyViewDrawText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int baseX = 10;
        int baseY = 200;
        String s = "OzTaking";

        mPaint.setColor(Color.RED);
        canvas.drawLine(baseX,baseY,600,baseY,mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(100);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.setTextAlign(Paint.Align.LEFT);
//        mPaint.setTextAlign(Paint.Align.RIGHT);

        canvas.drawText(s,baseX,baseY,mPaint);

        Paint.FontMetricsInt metricsInt = mPaint.getFontMetricsInt();
        int ascent = baseY + metricsInt.ascent;
        int descent = baseY + metricsInt.descent;
        int top = baseY + metricsInt.top;
        int bottom = baseY + metricsInt.bottom;

        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.RED);
        canvas.drawLine(baseX,ascent,600,ascent,mPaint);
        mPaint.setColor(Color.CYAN);
        canvas.drawLine(baseX,descent,600,descent,mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawLine(baseX,top,600,top,mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(baseX,bottom,600,bottom,mPaint);

        //画出所占的区域
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(5);
        int width = (int) mPaint.measureText(s);
        canvas.drawRect(baseX,top,baseX + width,bottom,mPaint);

        mPaint.setColor(Color.GREEN);
        Rect rect = new Rect();
        mPaint.getTextBounds(s,0,s.length(),rect);
        rect.top = rect.top + baseY;
        rect.bottom = rect.bottom + baseY;
        Toast.makeText(getContext(),rect.toString(),Toast.LENGTH_SHORT).show();
        canvas.drawRect(rect,mPaint);

    }
}
