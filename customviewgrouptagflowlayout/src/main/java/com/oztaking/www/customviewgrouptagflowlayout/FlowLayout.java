package com.oztaking.www.customviewgrouptagflowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @function:
 */

public class FlowLayout extends ViewGroup {


    //构造方法一定要写是两个参数的，否则程序anr;
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        //        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
//        return super.generateLayoutParams(p);
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
//        return super.generateDefaultLayoutParams();
            return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    /**
     * 首先得到其父容器传入的测量模式和宽高的计算值，然后遍历所有的childView，
     * 使用measureChild方法对所有的childView进行测量。
     * 然后根据所有childView的测量得出的宽和高得到该ViewGroup如果设置为wrap_content时的宽和高。
     * 最后根据模式，如果是MeasureSpec.EXACTLY则直接使用父ViewGroup传入的宽和高，否则设置为自己计算的宽和高。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //记录当前的行的框和高；
        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();


        //记录viewGroup的整体的宽高，如果是wrap_content
        int width = 0;
        int height = 0;

        for (int i = 0; i < cCount; i++) {
            //记录当前控件的width/height
            View child = getChildAt(i);
            //测量每个child的宽高；
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            //记录当前view的实际占据的宽/高
            int cWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int cHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            //flowLayot布局中的宽高的定义
            if (lineWidth + cWidth > widthSize) {
                width = Math.max(cWidth, lineWidth);
                lineWidth = cWidth;//重新开始新行，开始记录；
                //叠加高度；
                height += lineHeight;
                //开启下一行，记录下一行的高度
                lineHeight = cHeight;
            } else { //如果当前行的宽度没有超过布局的总宽度；
                lineWidth += cWidth;
                lineHeight = Math.max(cHeight, lineHeight);
            }

            //如果是最后一个view，则将记录的最大宽度width和linewidth作比较；
            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }

            setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width,
                    (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
        }


    }

    //存储了所有的view；
    private List<List<View>> mAllViews = new ArrayList<List<View>>();

    //记录每行的最大高度；
    private List<Integer> mLineHeight = new ArrayList<Integer>();


    /**
     * 完成对所有childView的位置以及大小的指定
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();


        //**********填充mAllViews，mLineHeight集合*****************
        //布局中的总宽度；
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        //单行的view；
        List<View> lineViews = new ArrayList<View>();

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //一个child实际占据的宽度；
            int childTotalWidth = childWidth + lp.leftMargin + lp.rightMargin;
            int childTotalHeith = childHeight + lp.topMargin + lp.bottomMargin;

            //布局的时候如果需要换行；
            if (childTotalWidth + lineWidth > width) {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<View>();

            } else {
                //不需要换行
                lineWidth += childTotalWidth;
                lineHeight = Math.max(lineHeight, childTotalHeith);
                lineViews.add(child);
            }


        }

        //记录最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //****************设置结束******************************

        int left = 0;
        int top = 0;

        //总行数应该是AllVie的大小；
//        int lineNum = mLineHeight.size();
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {

            //每行所有的view
            lineViews = mAllViews.get(i);
            //当前行的view的个数；
            int lineViewsNum = lineViews.size();
            //当前行的最大高度
            lineHeight = mLineHeight.get(i);

            //逐个遍历当前行的每个view；一行一行的放置；
            for (int j = 0; j < lineViewsNum; j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE || child.getVisibility() == View.INVISIBLE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                //计算childView的宽高
                int lc = left + lp.leftMargin;
                int rc = lc + child.getMeasuredWidth();
                int tc = top + lp.topMargin;
                int bc = tc + child.getMeasuredHeight();

                //放置一个view；
                child.layout(lc, tc, rc, bc);
                //增加left的宽度；
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            }
            //开始放置下一行；
            left = 0;
            top += lineHeight;
        }
    }


}
