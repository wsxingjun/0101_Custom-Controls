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
 * @function:
 */

public class TagView extends FrameLayout implements Checkable {

    public TagView(@NonNull Context context) {
        super(context);
    }

    public TagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }
}
