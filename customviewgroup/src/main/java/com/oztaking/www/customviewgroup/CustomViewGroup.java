package com.oztaking.www.customviewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @function:
 */

public class CustomViewGroup extends ViewGroup {


    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //决定ViewGroup的LayoutParams,只需要ViewGroup能够支持margin即可，直接使用系统的MarginLayoutParams

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
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
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    //    @Override
    //    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    //    {
    //        /**
    //         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
    //         */
    //        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    //        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    //        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
    //        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
    //
    //
    //        // 计算出所有的childView的宽和高
    //        measureChildren(widthMeasureSpec, heightMeasureSpec);
    //        /**
    //         * 记录如果是wrap_content是设置的宽和高
    //         */
    //        int width = 0;
    //        int height = 0;
    //
    //        int cCount = getChildCount();
    //
    //        int cWidth = 0;
    //        int cHeight = 0;
    //        MarginLayoutParams cParams = null;
    //
    //        // 用于计算左边两个childView的高度
    //        int lHeight = 0;
    //        // 用于计算右边两个childView的高度，最终高度取二者之间大值
    //        int rHeight = 0;
    //
    //        // 用于计算上边两个childView的宽度
    //        int tWidth = 0;
    //        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
    //        int bWidth = 0;
    //
    //        /**
    //         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
    //         */
    //        for (int i = 0; i < cCount; i++)
    //        {
    //            View childView = getChildAt(i);
    //            cWidth = childView.getMeasuredWidth();
    //            cHeight = childView.getMeasuredHeight();
    //            cParams = (MarginLayoutParams) childView.getLayoutParams();
    //
    //            // 上面两个childView
    //            if (i == 0 || i == 1)
    //            {
    //                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
    //            }
    //
    //            if (i == 2 || i == 3)
    //            {
    //                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
    //            }
    //
    //            if (i == 0 || i == 2)
    //            {
    //                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
    //            }
    //
    //            if (i == 1 || i == 3)
    //            {
    //                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
    //            }
    //
    //        }
    //
    //        width = Math.max(tWidth, bWidth);
    //        height = Math.max(lHeight, rHeight);
    //
    //        /**
    //         * 如果是wrap_content设置为我们计算的值
    //         * 否则：直接设置为父容器计算的值
    //         */
    //        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
    //                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
    //                : height);
    //    }


    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     * 主要是针对：wrap_content设置的计算；
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获取该ViewGroup父容器为其设置的计算模式和尺寸，大多情况下，只要不是wrap_content，父容器都能正确的计算其尺寸。
         * 所以我们自己需要计算如果设置为wrap_content时的宽和高，如何计算呢？那就是通过其childView的宽和高来进行计算。
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        /**
         * 通过ViewGroup的measureChildren方法为其所有的孩子设置宽和高，
         * 此行执行完成后，childView的宽和高都已经正确的计算过了
         */
        //计算出所有的childView的宽高
        //        【BUG】参数使用的应该是widthMeasureSpec，非widthSize
        //        measureChildren(widthSize, heightSize);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //对于wrap_content：则宽高为设置的宽高
        int width = 0;
        int height = 0;
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;

        MarginLayoutParams childrenParams = null;

        //计算左边的child的高度；计算右边child的高度
        int lHeight = 0;
        int rHeight = 0;

        //计算下面的child的宽度，最终的宽度取二者最大值
        int tWidth = 0;
        int bWidth = 0;

        /**
         * 根据childView的宽和高，以及margin，计算ViewGroup在wrap_content时的宽和高
         */
        //根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            //【bug】:不能调用childView的width；应该是measureWidth；
            //            childrenWidth = childView.getWidth();
            //            childrenHeight = childView.getHeight();
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            childrenParams = (MarginLayoutParams) childView.getLayoutParams();

            //上面两个childView
            if (i == 0 || i == 1) {
                tWidth += cWidth + childrenParams.leftMargin + childrenParams
                        .rightMargin;
            }
            if (i == 2 || i == 3) { //下面两个childView
                bWidth += cWidth + childrenParams.leftMargin + childrenParams
                        .rightMargin;
            }
            if (i == 0 || i == 2) { //左边两个childView
                lHeight += cHeight + childrenParams.topMargin + childrenParams
                        .bottomMargin;
            }

            if (i == 1 || i == 3) {//右边两个childView
                rHeight += cHeight + childrenParams.topMargin + childrenParams
                        .bottomMargin;
            }

        }


        //取宽度/高度的最大值；
        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则，直接设置为父容器计算的值
         * 如果宽高属性值为wrap_content，则设置为43-71行中计算的值，否则为其父容器传入的宽和高
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);

    }


    /**
     * 代码比较容易懂：遍历所有的childView，根据childView的宽和高以及margin，
     * 然后分别将0，1，2，3位置的childView依次设置到左上、右上、左下、右下的位置。
     * 如果是第一个View(index=0) ：则childView.layout(cl, ct, cr, cb);
     * cl为childView的leftMargin , ct 为topMargin , cr 为cl+ cWidth , cb为 ct + cHeight
     * 如果是第二个View(index=1) ：则childView.layout(cl, ct, cr, cb);
     * cl为getWidth() - cWidth - cParams.leftMargin- cParams.rightMargin;
     * ct 为topMargin , cr 为cl+ cWidth , cb为 ct + cHeight
     * 剩下两个类似~
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childrenCount = getChildCount();
        int childViewWidth = 0;
        int childViewHeight = 0;
        MarginLayoutParams params = null;

        //遍历所有的childView 根据children宽高和margin进行布局；
        for (int i = 0; i < childrenCount; i++) {
            View childView = getChildAt(i);
            //            childViewWidth = childView.getWidth();
            //            childViewHeight = childView.getHeight();

            childViewWidth = childView.getMeasuredWidth();
            childViewHeight = childView.getMeasuredHeight();
            params = (MarginLayoutParams) childView.getLayoutParams();

            int childrenLeft = 0;
            int childrenRight = 0;
            int childrenTop = 0;
            int childrenBottom = 0;

            switch (i) {
                case 0:
                    childrenLeft = params.leftMargin;
                    childrenTop = params.topMargin;
                    break;
                case 1:
                    childrenLeft = getWidth() - childViewWidth - params.leftMargin - params
                            .rightMargin;
                    childrenTop = params.topMargin;
                    break;

                case 2:
                    childrenLeft = params.leftMargin;
                    childrenTop = getHeight() - childViewHeight - params.topMargin - params
                            .bottomMargin;
                    break;

                case 3:
                    childrenLeft = getWidth() - childViewWidth - params.leftMargin - params
                            .rightMargin;
                    childrenTop = getHeight() - childViewHeight - params.topMargin - params
                            .bottomMargin;
                    break;


                default:
                    break;


            }

            childrenRight = childrenLeft + childViewWidth;
            childrenBottom = childrenTop + childViewHeight;

            //根据设置childrenView参数，进行重新layout；
            childView.layout(childrenLeft, childrenTop, childrenRight, childrenBottom);
        }

    }


}
