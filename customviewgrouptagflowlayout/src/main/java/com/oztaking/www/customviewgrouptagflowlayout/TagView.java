package com.oztaking.www.customviewgrouptagflowlayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * @function:childView哪来的isChecked(),setChecked()方法？ 首先，我们并非知道adapter#getView返回的是什么View，
 * 但是可以肯定的是，大部分View都是没有isChecked(),setChecked()方法的。
 * 但是我们需要有，怎么做？
 * 还记得我们setAdapter的时候，给getView外层包了一层TagView么，
 * 没错，就是TagView起到的作用：
 * TagView实现了Checkable接口，所以提供了问题一的方法。
 * ================================================
 * [这么做就能改变UI了？]
 * 我们继续看TagView这个类，这个类中我们复写了onCreateDrawableState，
 * 在里面添加了CHECK_STATE的支持。当我们调用setChecked方法的时候，
 * 我们会调用refreshDrawableState()来更新我们的UI。
 * 但是你可能又会问了，你这个是TagView支持了CHECKED状态，
 * 关它的子View什么事？我们的background可是设置在子View上的。
 * 没错，这个问题问的相当好，你还记得我们在setAdapter，
 * addView之前有一行非常核心的代码：
 * #mAdapter.getView().setDuplicateParentStateEnabled(true);
 * ，setDuplicateParentStateEnabled这个方法允许我们的CHECKED状态向下传递。
 */

public class TagView extends FrameLayout implements Checkable {
    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public TagView(@NonNull Context context) {

        super(context);
    }

    public View getTagView() {
        return getChildAt(0);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(states, CHECK_STATE);
        }

        return states;

    }

    /**
     * Change the checked state of the view
     *
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked() != checked) {
            this.isChecked = false;
            refreshDrawableState();
        }
    }

    /**
     * @return The current checked state of the view
     */
    @Override
    public boolean isChecked() {

        return isChecked;
    }

    /**
     * Change the checked state of the view to the inverse of its current state
     */
    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
