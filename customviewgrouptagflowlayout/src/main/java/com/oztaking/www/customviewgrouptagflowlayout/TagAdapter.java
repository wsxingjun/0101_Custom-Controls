package com.oztaking.www.customviewgrouptagflowlayout;


import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 可以看到很简单，这是一个抽象类，那么具体的View的展示需要大家通过复写getView,
 * 用法和ListView及其类似，同时我们提供了notifyDataChanged()的方法，
 * 当你的数据集发生变化的时候，你可以调用该方法，UI会自动刷新。
 * 当然，仅仅有了Adapter是不行的，我们需要添加相应的代码对其进行支持。
 *
 * @param <T>
 */


public abstract class TagAdapter<T> {
    private List<T> mTagDatas;
    private onDataChangedListener mOnDataChangedListener;

    public TagAdapter(List<T> datas) {
        this.mTagDatas = datas;
    }

    /**
     * 首先，该方法是将数组转化为list。有以下几点需要注意：
     * 　（1）该方法不适用于基本数据类型（byte,short,int,long,float,double,boolean）
     * 　（2）该方法将数组与列表链接起来，当更新其中之一时，另一个自动更新
     * 　（3）不支持add和remove方法
     *
     * @param datas
     */
    public TagAdapter(T[] datas) {
        this.mTagDatas = new ArrayList<>(Arrays.asList(datas));
    }

    static interface onDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(onDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);


}