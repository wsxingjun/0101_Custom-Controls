package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

/**
 * 自定义控件之绘图篇（三）：区域（Range）
 * https://blog.csdn.net/harvic880925/article/details/39056701
 *
 * @function
 */

public class MyViewRange extends View {

    Paint mPaint;
    Context mContext;

    public MyViewRange(Context context) {
        super(context);
        mContext = context;
        mPaint = new Paint();
    }

    public MyViewRange(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

/*        构造Region
        1、基本构造函数
        public Region()  //创建一个空的区域
        public Region(Region region) //拷贝一个region的范围
        public Region(Rect r)  //创建一个矩形的区域
        public Region(int left, int top, int right, int bottom) //创建一个矩形的区域

        上面的四个构造函数，第一个还要配合其它函数使用，暂时不提。
        第二个构造函数是通过其它的Region来复制一个同样的Region变量
        第三个，第四个才是常规的，根据一个矩形或矩形的左上角和右下角点构造出一个矩形区域

        2、间接构造
        public void setEmpty()  //置空
        public boolean set(Region region)
        public boolean set(Rect r)
        public boolean set(int left, int top, int right, int bottom)
        public boolean setPath(Path path, Region clip)//后面单独讲*/

     /*   这是Region所具有的一系列Set方法，我这里全部列了出来，下面一一对其讲解：
        注意：无论调用Set系列函数的Region是不是有区域值，当调用Set系列函数后，原来的区域值就会被替换成Set函数里的区域。
        SetEmpty（）：从某种意义上讲置空也是一个构造函数，即将原来的一个区域变量变成了一个空变量，
                要再利用其它的Set方法重新构造区域。
        set(Region region)：利用新的区域值来替换原来的区域
        set(Rect r)：利用矩形所代表的区域替换原来的区域
        set(int left, int top, int right, int bottom)：同样，根据矩形的两个点构造出矩形区域来替换原来的区域值
        setPath(Path path, Region clip)：根据路径的区域与某区域的交集，构造出新区域，这个后面具体讲解

        举个小例子，来说明一个Set系列函数的替换概念：

        关于重写新建一个类，并派生自view，并且要重写OnDraw函数的问题我就不再讲了，
        有问题的同学，可以参考下《android Graphics（一）：概述及基本几何图形绘制》，
        当然最后我也会给出相关的源码，直接看源码也行。

        下面写了一个函数，先把Set函数注释起来，看看画出来的区域的位置，然后开启Set函数，然后再看画出来的区域
        注：里面有个函数drawRegion(Canvas canvas,Region rgn,Paint paint),只知道它可以画出指定的区域就可以了，
        具体里面是什么意思，后面我们再仔细讲。 */

        /**
         * 未开启Set函数时
         */

//        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(2);
//
//        Region region0 = new Region(10, 10, 100, 100);
//        region0.set(100,100,150,150);  //构造出矩形区域来替换原来的区域值，重新生成一个区域；
//        drawRegion(canvas,region0,mPaint);


        /* 使用SetPath（）构造不规则区域
    boolean setPath (Path path, Region clip)

    参数说明：
    Path path：用来构造的区域的路径
    Region clip：与前面的path所构成的路径取交集，并将两交集设置为最终的区域

    由于路径有很多种构造方法，而且可以轻意构造出非矩形的路径，这就摆脱了前面的构造函数只能构造矩形区域的限制。
    但这里有个问题是要指定另一个区域来取共同的交集，当然如果想显示路径构造的区域，
    Region clip参数可以传一个比Path范围大的多的区域，取完交集之后，当然是Path参数所对应的区域

    下面，先构造一个椭圆路径，然后在SetPath时，传进去一个比Path小的矩形区域，让它们两个取交集*/


//        mPaint.setStyle(Paint.Style.STROKE);
//        //构造一个椭圆路径
//        Path ovalPath = new Path();
//        RectF rectF = new RectF(50, 50, 200, 500);
//        ovalPath.addOval(rectF, Path.Direction.CW);
//
//        Region region1 = new Region();
//        Region region2 = new Region(50, 50, 200, 300);
//        region1.setPath(ovalPath,region2);
//        drawRegion(canvas,region1,mPaint);


//        构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

//        Paint paint = new Paint();
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);

        canvas.drawRect(rect1,mPaint);
        canvas.drawRect(rect2,mPaint);

        Region region3 = new Region(rect1);
        Region region4 = new Region(rect2);

        region3.op(region4, Region.Op.INTERSECT);

//        Paint paint1 = new Paint();
//        canvas.translate(20,20);
//        canvas.rotate(-60);
//        canvas.scale(0.5f,0.5f);

        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
//        drawRegion(canvas,region3,mPaint);

//        canvas.skew(0.577f,0);
//        drawRegion(canvas,region3,mPaint);

        canvas.drawColor(Color.RED);
        Toast.makeText(getContext(),"width:height:" + canvas.getWidth()+":"+canvas.getHeight(),
                Toast.LENGTH_SHORT).show();
        canvas.clipRect(new RectF(10,10,650,650));
        canvas.drawColor(Color.CYAN);
        canvas.save();

        canvas.clipRect(new RectF(50,50,550,550));
        canvas.drawColor(Color.CYAN);
        canvas.save();

        canvas.clipRect(new RectF(100,100,450,450));
        canvas.drawColor(Color.BLACK);
        canvas.save();

        canvas.clipRect(new RectF(150,150,350,350));
        canvas.drawColor(Color.WHITE);
        canvas.save();

        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.drawColor(Color.YELLOW);

    }

    private void drawRegion(Canvas canvas, Region region, Paint paint) {

 /*       对于特定的区域，我们都可以使用多个矩形来表示其大致形状。事实上，如果矩形足够小，一定数量的矩形就能够精确表示区域的形状，也就是说，一定数量的矩形所合成的形状，也可以代表区域的形状。RegionIterator类，实现了获取组成区域的矩形集的功能，其实RegionIterator类非常简单，总共就两个函数，一个构造函数和一个获取下一个矩形的函数；
        RegionIterator(Region region) //根据区域构建对应的矩形集
        boolean	next(Rect r) //获取下一个矩形，结果保存在参数Rect r 中*/
        RegionIterator iterator = new RegionIterator(region);
        Rect rect = new Rect();  //此处不可以是rectF;

        while (iterator.next(rect)){
            canvas.drawRect(rect,mPaint);
        }

    }




}
