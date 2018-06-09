package com.oztaking.www.customviewgrouptagflowlayout;

import android.content.Context;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @function:
 */

/**
 * 可以看到当你调用setAdapter进来，首先我们会注册mTagAdapter.setOnDataChangedListener这个回调，
 * 主要是用于响应notifyDataSetChanged()。然后进入changeAdapter方法，
 * 在这里首先移除所有的子View，然后根据mAdapter.getView的返回，开始逐个构造子View，然后进行添加。
 * 这里注意下：我们的上述的代码，对mAdapter.getView返回的View，外围报了一层TagView，
 * 这里暂时不要去想，我们后面会细说。
 */

public class TagFlowLayout extends FlowLayout implements TagAdapter.onDataChangedListener{

    private TagAdapter mTagAdapter;
    private MotionEvent mMotionEvent;
    private onTagClickListener mOnTagClickListener;

    public TagFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(TagAdapter adapter){
        mTagAdapter = adapter;
        mTagAdapter.setOnDataChangedListener(this);
        changeAdapter();

    }

    private void changeAdapter() {
        removeAllViews();
        TagAdapter adapter = mTagAdapter;
        TagView tagViewContainer = null;

        int count = adapter.getCount();
        for (int i=0; i<count; i++){
            View tagView = adapter.getView(this, i, adapter.getItem(i));
            //外层包了TagView
            tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            tagViewContainer.addView(tagView);
            addView(tagViewContainer);
        }
    }


    @Override
    public void onChanged() {
        changeAdapter();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            mMotionEvent = MotionEvent.obtain(event);
        }

        return super.onTouchEvent(event);
    }

    /**
     * 如果设置了setOnTagClickListener，
     * 我们显示的设置了父ViewsetClickable(true)。
     * 以防万一父View不具备消费事件的能力。
     */
    public interface onTagClickListener{
        boolean onTagClick(View view,int position,FlowLayout parent);
    }

    public void setOnTagClickListener(onTagClickListener onTagClickListener){
        mOnTagClickListener = onTagClickListener;
        if (onTagClickListener != null){
            setClickable(true);
        }
    }

    /**
     * 巧妙的利用了performClick这个回调，来确定的确是触发了click事件，
     * 而不是自己去判断什么算click的条件。
     * 但是呢，我们的performClick没有提供MotionEvent的参数，不过不要紧，
     * 我们都清楚click的事件发生在ACTION_UP之后，
     * 所以我们提供一个变量去记录最后一次触发ACTION_UP的mMotionEvent。
     * 我们在performClick里面，根据mMotionEvent，去查找是否落在某个子View身上，
     * 如果落在，那么就确定点击在它身上了，直接回调即可，关于接口的定义如下
     * @return
     */
    @Override
    public boolean performClick() {
        if (mMotionEvent == null){
            return super.performClick();
        }

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();

        mMotionEvent = null;

        TagView child = findChild(x,y);
        int pos = findPosByView(child);

        if (child != null){
            doSelect(child,pos);
            if (mOnTagClickListener != null){
                return mOnTagClickListener.onTagClick(child.getTagView(),pos,this);
            }
        }
        return super.performClick();
    }

    private TagView findChild(int x, int y){
        final int cCount = getChildCount();
        for (int i=0; i<cCount; i++){
            TagView v = (TagView) getChildAt(i);
            if (v.getVisibility() == View.GONE){
                continue;
            }
            Rect rect = new Rect();
            v.getHitRect(rect);
            if (rect.contains(x,y)){
                return v;
            }
        }
        return  null;
    }
}
