package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @function:
 */

public class MyViewColorMatrix08 extends View {



    private Paint mPaint;
    private Path mPath;

    public MyViewColorMatrix08(Context context) {
        super(context);
        mPaint = new Paint();
        mPath = new Path();

    }

    public MyViewColorMatrix08(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawColorMatrix(canvas);

    }

    private void drawColorMatrix(Canvas canvas){
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);

        mPaint.setARGB(255, 200, 100, 100);
        canvas.drawRect(0, 0, 300, 500, mPaint);

        canvas.translate(300, 0);
        ColorMatrix colorMatrix = new ColorMatrix(
                new float[]{
                        0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0

                });

        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawRect(0,0,300,500,mPaint);

    }
}
