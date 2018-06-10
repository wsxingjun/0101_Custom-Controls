package com.oztaking.www.customviewclipimageborderview.zoomImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.oztaking.www.customviewclipimageborderview.R;
import com.oztaking.www.customviewclipimageborderview.view.ClipImageBorderView;

/**
 * @function:
 */

public class ClipImageLayout extends RelativeLayout {

    private ZoomImage mZoomImageView;
//    private ClipZoomImageView mZoomImageView;
    private ClipImageBorderView mClipImageView;

    /**
     * 测试:直接写死了大小，真正使用过程中，可以提取为自定义属性
     */
    private int mHorizontalPadding = 50;

    public ClipImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mZoomImageView = new ZoomImage(context);
//        mZoomImageView = new ClipZoomImageView(context);
        mClipImageView = new ClipImageBorderView(context);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);

//        这里测试，直接写死了图片，真正使用过程中，可以提取为自定义属性
        mZoomImageView.setImageDrawable(getResources().getDrawable(
                R.drawable.a));

        this.addView(mZoomImageView,lp);
        this.addView(mClipImageView,lp);



        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
                        .getDisplayMetrics());
        mZoomImageView.setHorizontalPadding(mHorizontalPadding);
        mClipImageView.setHorizontalPadding(mHorizontalPadding);




    }

    //对外公布设置mHorizontalPadding的方法；
    public void setHorizontalPadding(int horizontalPadding){
        this.mHorizontalPadding = horizontalPadding;
    }

//    裁切图片
    public Bitmap clip(){
        return mZoomImageView.clip();
    }


}
