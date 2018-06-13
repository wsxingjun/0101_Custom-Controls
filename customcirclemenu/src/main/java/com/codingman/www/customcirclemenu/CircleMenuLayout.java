package com.codingman.www.customcirclemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * @function
 */

public class CircleMenuLayout extends ViewGroup {


    //容器内的childitem的默认尺寸；
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1 / 4f;
    //菜单中心的child默认尺寸；
    private float RADIO_DEFAULT_CENTER_DIMENSION = 1 / 3f;
    //容器内边距，忽略padding属性，如需边距使用该变量；
    private static final float RADIO_PADDING_LAYOUT = 1 / 2f;
    //当每秒移动角度达到该值时，认为是快速移动
    private static final int FLINGABLE_VALUE = 300;
    //如果移动角度达到该值，则屏蔽点击
    private static final int NOCLICK_VALUE = 3;


    private int mRadius;
    //当每秒移动角度达到该值时，认为是快速移动
    private int mFlingableValue = FLINGABLE_VALUE;
    //该容器的内边距,无视padding属性，如需边距请用该变量
    private float mPadding;
    //    布局时的开始角度
    private double mStartAngle = 0;
    //    菜单项的文本
    private String[] mItemTexts;
    //    菜单项的图标
    private int[] mItemImgs;
    //    菜单的个数
    private int mMenuItemCount;


    //    检测按下到抬起时旋转的角度
    private float mTmpAngle;
    //    检测按下到抬起时使用的时间
    private long mDownTime;
    //    判断是否正在自动滚动
    private boolean isFling;


    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //padding 值设置为0；
        setPadding(0, 0, 0, 0);
    }

//    设置布局的宽高，计算menu item宽高


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resWidth = 0;
        int resHeight = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //*****计算整个布局的宽高***************
        //非MeasureSpec.EXACTLY
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            // 主要设置为背景图的高度
            resWidth = getSuggestedMinimumWidth();
            resHeight = getSuggestedMinimumHeight();

            // 如果未设置背景图片，则设置为屏幕宽高的默认值
            if (resWidth == 0) {
                resWidth = getDefaultWidth();
            } else {
                resWidth = resWidth;
            }

            if (resHeight == 0) {
                resHeight = getDefaultWidth();
            } else {
                resHeight = resHeight;
            }

        } else { //如果设置为MeasureSpec.EXACTLY
            resWidth = resHeight = Math.min(widthSize, heightSize);
        }
        //设置尺寸；
        setMeasuredDimension(resWidth, resHeight);


        //***************计算每个子item的宽高
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
// menu item数量
        final int count = getChildCount();

        //menu item尺寸 占据测量总布局的1/3；
        int ChildSize = (int) (mRadius * RADIO_DEFAULT_CENTER_DIMENSION);
// menu item测量模式
        int childMode = MeasureSpec.EXACTLY;

        for(int i=0; i<count; i++){
            final View child = getChildAt(i);
            if (child.getVisibility() == INVISIBLE ||child.getVisibility() == GONE){
                continue;
            }
















        }



    }

    //获得此layout的默认尺寸
    private int getDefaultWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return Math.min(metrics.widthPixels, metrics.heightPixels);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


    }
}
