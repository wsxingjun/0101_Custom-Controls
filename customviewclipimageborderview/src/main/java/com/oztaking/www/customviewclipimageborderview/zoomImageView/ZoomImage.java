package com.oztaking.www.gesture;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Toast;

/**
 * @function:
 */

public class ZoomImage extends AppCompatImageView implements ScaleGestureDetector
        .OnScaleGestureListener,
        View.OnTouchListener, OnGlobalLayoutListener {

    private static final float SCALE_MAX = 4.0f; //??
    private static final float SCALE_MID = 2.0f; //??


    //    初始化时的缩放比例，如果图片宽或高大于屏幕，此值将小于0
    private float initScale = 1.0f;
    //    存放矩阵的9个值
    private final float[] matrixValues = new float[9];
    //缩放的手势检测
    private ScaleGestureDetector mScaleGestureDetetor = null;
    //双击手势监测
    private GestureDetector mGestureDetector = null;


    private final Matrix mScaleMatrix = new Matrix();

    private int mTouchSlop;

    private int lastPointCount;
    private boolean isCanDrag = false;
    private float mLastX = 0;
    private float mLastY = 0;

    private boolean isCheckLeftAndRight = true;
    private boolean isCheckTopAndBottom = true;
    private boolean isAutoScale = false;


    private boolean once = true;


    public ZoomImage(Context context) {
        this(context, null);
    }

    public ZoomImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        mGestureDetector = new GestureDetector(context, new GestureDetector
                .SimpleOnGestureListener() {

            /**
             * 双击尺寸的变化规则：
             * 根据当前的缩放值，如果是小于2的，我们双击直接到变为原图的2倍；
             * 如果是2,4之间的，我们双击直接为原图的4倍；其他状态也就是4倍，
             * 双击后还原到最初的尺寸
             * [说明]双击之后需要一个动画，否则给用户的感觉太突兀，因此创建了一个新类：
             * AutoScaleRunnable：专门处理双击的缩放事件；
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                //当双击的时候，首先判断是否正在自动缩放，如果在，直接retrun ;
                if (isAutoScale == true) {
                    return true;
                }

                float x = e.getX();
                float y = e.getY();

                //如果当然是scale小于2，则通过view.发送一个Runnable进行执行；其他类似；
                if (getScale() < SCALE_MID) {
                    ZoomImage.this.postDelayed(new AutoScaleRunnable(SCALE_MID, x, y), 16);
                    isAutoScale = true;
                }
//                else if (getScale() >= SCALE_MID && getScale() < SCALE_MAX) {
//                    ZoomImage.this.postDelayed(new AutoScaleRunnable(SCALE_MAX, x, y), 16);
//                    isAutoScale = true;
//                }

                else {
                    ZoomImage.this.postDelayed(new AutoScaleRunnable(initScale, x, y), 16);
                    isAutoScale = true;
                }

                return true;
            }
        });

        // 自定义View处理touch事件的时候，有的时候需要判断用户是否真的存在movie，
        // 系统提供了这样的方法。表示滑动的时候，手的移动要大于这个返回的距离值才开始移动控件。
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        super.setScaleType(ScaleType.MATRIX);

        mScaleGestureDetetor = new ScaleGestureDetector(context, this);
        this.setOnTouchListener(this);
    }

    /**
     * 在onScale的回调中对图片进行缩放的控制，首先进行缩放范围的判断，然后设置mScaleMatrix的scale值
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();
        if (getDrawable() == null) {
            return true;
        }

        //控制缩放的范围
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)
                || (scale > initScale && scaleFactor < 1.0f)) {
            //最大值最小值的判断
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
            }

            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
            }

            // 设置缩放比例
            //以屏幕的中心进行缩放
            //            mScaleMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2,
            // getHeight() / 2);
            //以获得焦点的位置进行缩放
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector
                    .getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

        }

        return true;
    }

    public float getScale() {
        mScaleMatrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /**
     * OnTouchListener的MotionEvent交给ScaleGestureDetector进行处理
     * <p>
     * 首先我们拿到触摸点的数量，然后求出多个触摸点的平均值，
     * 设置给我们的mLastX , mLastY ， 然后在移动的时候，得到dx ,dy
     * 进行范围检查以后，调用mScaleMatrix.postTranslate进行设置偏移量，
     * 当然了，设置完成以后，还需要再次校验一下，不能把图片移动的与屏幕边界出现白边，
     * 校验完成后，调用setImageMatrix.
     * 这里：需要注意一下，我们没有复写ACTION_DOWM，是因为，
     * ACTION_DOWN在多点触控的情况下，只要有一个手指按下状态，
     * 其他手指按下不会再次触发ACTION_DOWN，但是多个手指以后，
     * 触摸点的平均值会发生很大变化，所以我们没有用到ACTION_DOWN。
     * 每当触摸点的数量变化，我们就会更新当前的mLastX,mLastY.
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //将双击事件传递过来；
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }

        mScaleGestureDetetor.onTouchEvent(event);

        float x = 0, y = 0;
        //取到触摸点的个数；
        final int pointCount = event.getPointerCount();
        //取到多个触摸点的x与y均值；
        for (int i = 0; i < pointCount; i++) {
            x += event.getX(i);
            y += event.getY(i);

        }

        x = x / pointCount;
        y = y / pointCount;

        //每当触摸点发生变化的时候，重置mLastX,重置mLastY；
        if (pointCount != lastPointCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        lastPointCount = pointCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                //当宽或高大于屏幕宽或高时，拖动效果认为是移动图片，反之则让ViewPager去处理
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    //解决viewPager的滑动冲突；
                    getParent().requestDisallowInterceptTouchEvent(true);

                }

                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isCanDrag(dx, dy);
                }
                if (isCanDrag) {

                    if (getDrawable() != null) {

                        //判断当前已经到达边界，且还在拉的时候，事件交给ViewPager
                        if (getMatrixRectF().left == 0 && dx > 0)
                        {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        //判断当前已经到达边界，且还在拉的时候，事件交给ViewPager
                        if (getMatrixRectF().right == getWidth() && dx < 0)
                        {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        isCheckLeftAndRight = true;
                        isCheckTopAndBottom = true;

                        //如果宽高小于屏幕宽度，则禁止左右移动；
                        if (rectF.width() < getWidth()) {
                            dx = 0;
                            isCheckLeftAndRight = false;
                        }
                        //如果高度小于屏幕高度，则禁止上下移动
                        if (rectF.height() < getHeight()) {
                            dy = 0;
                            isCheckTopAndBottom = false;
                        }

                        mScaleMatrix.postTranslate(dx, dy);
                        //再次检验，不能将图片移动与屏幕边界出现白边；
                        checkMatrixBounds();
                        setImageMatrix(mScaleMatrix);
                    }
                }

                mLastX = x;
                mLastY = y;

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastPointCount = 0;
                break;
            case MotionEvent.ACTION_DOWN:
                //当宽或高大于屏幕宽或高时，拖动效果认为是移动图片，反之则让ViewPager去处理
                if (rectF.width() > getWidth() || rectF.height() > getHeight()) {
                    //解决viewPager的滑动冲突；
                    getParent().requestDisallowInterceptTouchEvent(true);

                }
                break;

            default:
                break;
        }


        return true;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 我们在onGlobalLayout的回调中，根据图片的宽和高以及屏幕的宽和高，
     * 对图片进行缩放以及移动至屏幕的中心。如果图片很小，那就正常显示，不放大了
     */
    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = getDrawable();
            if (d == null) {
                return;
            }
            int width = getWidth();
            int height = getHeight();

            //取图片的宽和高
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();

            float scale = 1.0f;
            //如果屏幕的宽高大于或者高于屏幕，则缩放至屏幕的宽或者高
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }

            if (dh > height && dh <= width) {
                scale = height * 1.0f / dh;
            }
            // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            initScale = scale;
            //图片移到屏幕中心
            mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            mScaleMatrix.postScale(scale, scale, getWidth() / 2, getHeight() / 2);

            setImageMatrix(mScaleMatrix);
            once = false;

        }
    }

    //在缩放时，进行图片显示范围的控制

    public void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        //如果图片的缩放时的宽高大于屏幕，则控制范围
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }

            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() > height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }
        //如果图片的缩放时的宽高小于屏幕，则让其居中
        if (rectF.width() < width) {
            deltaX = width * 0.5f - rectF.right + rectF.width() * 0.5f;
        }

        if (rectF.height() < height) {
            deltaY = height * 0.5f - rectF.bottom + rectF.height() * 0.5f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    //根据当前图片的Matrix获得图片的范围
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    private void checkMatrixBounds() {
        RectF rectF = getMatrixRectF();

        float deltaX = 0, deltaY = 0;
        //        获取屏幕的宽高；
        final float viewWidth = getWidth();
        final float viewHeight = getHeight();

        //        判断移动或者缩放后，图片显示是否超出屏幕边界；
        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }

        if (rectF.bottom < viewHeight && isCheckTopAndBottom) {
            deltaY = viewHeight - rectF.bottom;
        }

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }

        if (rectF.right < viewWidth && isCheckLeftAndRight) {
            deltaX = viewWidth - rectF.right;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
    }


    private class AutoScaleRunnable implements Runnable {


        private float mTargetScale;
        private float mTempScale;
        private float BIGGER = 1.07f;
        private float SMALLER = 0.93f;

        //缩放的中心
        private float x;
        private float y;


        public AutoScaleRunnable(float targetScale, float x, float y) {
            this.mTargetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getScale() < mTargetScale) {
                mTempScale = BIGGER;
            } else {
                mTempScale = SMALLER;
            }
        }


        @Override
        public void run() {
            //执行缩放操作；
            mScaleMatrix.postScale(mTempScale, mTempScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            final float currentScale = getScale();
            //如果在合法范围内继续缩放；
            if (((mTempScale > 1.0f) && (currentScale < mTargetScale))
                    || ((mTargetScale < 1.0f) && (mTargetScale < currentScale))) {
                ZoomImage.this.postDelayed(this, 16);
            } else { //设置为目标的缩放比例
                final float deltaScale = mTargetScale / currentScale;
                Toast.makeText(getContext(), "deltaScale:mTargetScale:currentScale" + deltaScale
                        + ":" + mTargetScale + ":" + mTargetScale, Toast.LENGTH_SHORT).show();
                mScaleMatrix.postScale(deltaScale, deltaScale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }

        }
    }
}
