package com.codingman.www.customimageview.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.codingman.www.customimageview.R;

/**
 * @function
 */

public class CustomImageView extends View {


    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 1;

    //控件的宽高
    private  int mWidth = 0;
    private  int mHeight = 0;

    private Bitmap mBitmap;
    private int mImageScale;
    //控制整体布局
    private Rect mRect;
    private Paint mPaint;
    //对文本的约束
    private Rect mTextBound;
    //字体大小
    private int mTextSize;
    //字体颜色
    private int mTextcolor;
    //图片的介绍
    private String mTitle;


    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageView(Context context) {
        this(context,null);
    }

    /**
     * init the custom val
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CutomImageView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i=0; i < n; i++){
            int attr = typedArray.getIndex(i);
            switch(attr){
                case R.styleable.CutomImageView_image:
                    mBitmap = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.CutomImageView_imageScaleType:
                    mImageScale = typedArray.getInt(attr,0);
                    break;
                case R.styleable.CutomImageView_titleText:
                    mTitle = typedArray.getString(attr);
                    break;
                case R.styleable.CutomImageView_titleTextColor:
                    mTextcolor = typedArray.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CutomImageView_titleTextSize:
                    mTextSize = typedArray.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                                    16,getResources().getDisplayMetrics())
                    );
                    break;
                default:
                    break;
            }
        }

        typedArray.recycle();
        mRect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mTitle,0,mTitle.length(),mTextBound);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasue(widthMeasureSpec, heightMeasureSpec);



        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch(widthMode){
            case MeasureSpec.EXACTLY:
                mWidth = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                //计算图片的宽度；
                int desireByBimap = getPaddingLeft() + getPaddingRight() + mBitmap.getWidth();
                //计算字体决定的宽；
                int desireByText = getPaddingLeft() + getPaddingRight() + mTextBound.width();
                int compareWidth = Math.max(desireByBimap,desireByText);
                mWidth = Math.min(compareWidth,widthSize);

                break;
            default:
                break;

        }

        switch(heightMode){
            case MeasureSpec.EXACTLY:
                mHeight = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                int desireHeight = getPaddingTop() + getPaddingBottom() + mBitmap.getHeight()
                        + mTextBound.height();
                mHeight = Math.min(desireHeight,heightSize);
                break;
            default:
                break;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //边框
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mRect.top = getPaddingTop();
        mRect.bottom = mWidth - getPaddingBottom();
        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();


        mPaint.setColor(mTextcolor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextBound.width() > mWidth){
            TextPaint textPaint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(  //截取指定长度字符串,将长的字体多余的字体改为xxx；
                    mTitle,
                    textPaint,
                    (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        }else {
            //正常情况，将字体居中；
            canvas.drawText(mTitle,mWidth /2 -
                    mTextBound.width()*1.0f /2,mHeight - getPaddingBottom(),mPaint);

            //
            mRect.bottom -= mTextBound.height();

            if (mImageScale == IMAGE_SCALE_FITXY){
                canvas.drawBitmap(mBitmap,null,mRect,mPaint);
            }else {
                mRect.left = mWidth / 2 - mBitmap.getWidth() / 2;
                mRect.right = mWidth / 2 + mBitmap.getWidth() / 2;
                mRect.top = (mHeight - mTextBound.height()) / 2 - mBitmap.getHeight() /2;
                mRect.bottom = (mHeight - mTextBound.height())/2 + mBitmap.getHeight()/2;

                canvas.drawBitmap(mBitmap,null,mRect,mPaint);

            }


        }


    }
}
