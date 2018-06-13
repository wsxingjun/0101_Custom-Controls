package com.oztaking.www.customviewvisibleview1;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @function 自定义控件之绘图篇（二）：路径及文字
 * blogAddress:https://blog.csdn.net/harvic880925/article/details/38926877
 */

public class MyViewPaintPathAndText extends View {
    private Paint mPaint;
    private Context mContext;

    int currentX = 50;
    int currentY = 50;

    public MyViewPaintPathAndText(Context context) {
        super(context);
        mContext = context;
        mPaint = new Paint();
    }

    public MyViewPaintPathAndText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
//        void drawPath (Path path, Paint paint)


        /**
         *【1】直线路经
         * void moveTo (float x1, float y1):直线的开始点；即将直线路径的绘制点定在（x1,y1）的位置；
         * void lineTo (float x2, float y2)：直线的结束点，又是下一次绘制直线路径的开始点；lineTo（）可以一直用；
         * void close ():如果连续画了几条直线，但没有形成闭环，调用Close()会将路径首尾点连接起来，形成闭环；
         *
         */

        Path path = new Path();

        path.moveTo(10, 10);
        path.lineTo(10, 100);
        path.lineTo(100, 100);
        path.lineTo(150, 150);
//        path.close();
        canvas.drawPath(path, mPaint);

        /**
         * 【2】矩形路径
         *void addRect (float left, float top, float right, float bottom, Path.Direction dir)
         *void addRect (RectF rect, Path.Direction dir)
         *这里Path类创建矩形路径的参数与上篇canvas绘制矩形差不多，唯一不同的一点是增加了Path.Direction参数；
         *Path.Direction有两个值：
         *Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
         *Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；
         */
        mPaint.setColor(Color.GRAY);
        Path CCWpath = new Path();
        RectF rectF = new RectF(200, 10, 300, 110);
        CCWpath.addRect(rectF, Path.Direction.CCW); //逆时针；
        canvas.drawPath(CCWpath, mPaint);

        mPaint.setColor(Color.DKGRAY);
        Path CWpath = new Path();
        RectF rectF1 = new RectF(320, 10, 420, 110);
        CWpath.addRect(rectF1, Path.Direction.CW);//顺时针;
        canvas.drawPath(CWpath, mPaint);

        //依据路径绘制文字
        String Text = "abcdefghijklmnopq";
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.CYAN);
        mPaint.setTextSize(20);
        canvas.drawTextOnPath(Text, CWpath, 0, 20, mPaint);
        canvas.drawTextOnPath(Text, CCWpath, 0, 20, mPaint);


        /**
         * 【3】圆角矩形路径
         *
         * void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
         * void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
         * 这里有两个构造函数，部分参数说明如下：
         * 第一个构造函数：可以定制每个角的圆角大小：
         * float[] radii：必须传入8个数值，分四组，分别对应每个角所使用的椭圆的横轴半径和纵轴半径，
         *    如｛x1,y1,x2,y2,x3,y3,x4,y4｝，其中，x1,y1对应第一个角的（左上角）用来产生圆角的椭圆的横轴半径和纵轴半径，
         *    其它类推……
         * 第二个构造函数：只能构建统一圆角大小
         * float rx：所产生圆角的椭圆的横轴半径；
         * float ry：所产生圆角的椭圆的纵轴半径；
         *
         */
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.RED);
        Path path1 = new Path();
        RectF rectF2 = new RectF(430, 10, 540, 110);
        float[] rountRect = {10, 20, 10, 25, 15, 25, 20, 30};
        path1.addRoundRect(rectF2, rountRect, Path.Direction.CW);
        canvas.drawPath(path1, mPaint);

        Path path2 = new Path();
        RectF rectF3 = new RectF(550, 10, 660, 110);
        path2.addRoundRect(rectF3, 20, 20, Path.Direction.CCW);
        canvas.drawPath(path2, mPaint);

        /**
         * 【4】圆形路径
         *void addCircle (float x, float y, float radius, Path.Direction dir)
         * 参数说明：
         * float x：圆心X轴坐标
         * float y：圆心Y轴坐标
         * float radius：圆半径
         *
         */
        mPaint.setColor(Color.RED);
        Path path3 = new Path();
        path3.addCircle(60, 180, 50, Path.Direction.CW);
        canvas.drawPath(path3, mPaint);

        /**
         * 【5】椭圆路径
         *void addOval (RectF oval, Path.Direction dir)
         *参数说明：
         *RectF oval：生成椭圆所对应的矩形
         *Path.Direction :生成方式，与矩形一样，分为顺时针与逆时针，意义完全相同，不再重复
         */

        mPaint.setColor(Color.DKGRAY);
        RectF rectF4 = new RectF(150, 150, 260, 250);
        Path path4 = new Path();
        path4.addOval(rectF4, Path.Direction.CW);

        canvas.drawPath(path4, mPaint);

        /**
         * 【6】弧形路径
         * void addArc (RectF oval, float startAngle, float sweepAngle)
         * 参数：
         * RectF oval：弧是椭圆的一部分，这个参数就是生成椭圆所对应的矩形；
         * float startAngle：开始的角度，X轴正方向为0度
         * float sweepAngel：持续的度数；
         *
         */
        mPaint.setColor(Color.RED);
        Path path5 = new Path();
        RectF rectF5 = new RectF(270, 150, 380, 250);
        path5.addArc(rectF5, 90, 270);
        canvas.drawPath(path5, mPaint);

        /**
         * 【7】
         * //普通设置
         * paint.setStrokeWidth (5);//设置画笔宽度
         * paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
         * paint.setStyle(Paint.Style.FILL);//绘图样式，对于设文字和几何图形都有效
         * paint.setTextAlign(Align.CENTER);//设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
         * paint.setTextSize(12);//设置文字大小

         * //样式设置
         * paint.setFakeBoldText(true);//设置是否为粗体文字
         * paint.setUnderlineText(true);//设置下划线
         * paint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
         * paint.setStrikeThruText(true);//设置带有删除线效果

         * //其它设置
         *paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
         */
        String text1 = "张三李四王麻子";
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(50);

        mPaint.setTextScaleX(2);  //字体水平拉伸
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(text1, 10, 450, mPaint);

        mPaint.setTextScaleX(1); //字体水平还原
        mPaint.setTextSize(100);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(text1, 10, 550, mPaint);

        mPaint.setTextScaleX(1); //字体水平还原
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setFakeBoldText(true);
        mPaint.setUnderlineText(true);
        mPaint.setTextSkewX((float) 0.8);
        mPaint.setStrikeThruText(true);

        canvas.drawText(text1, 10, 350, mPaint);


        /**
         * 普通水平绘制
         * void drawText (String text, float x, float y, Paint paint)
         * void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
         * void drawText (String text, int start, int end, float x, float y, Paint paint)
         * void drawText (char[] text, int index, int count, float x, float y, Paint paint)
         * 说明：
         * 第一个构造函数：最普通简单的构造函数；
         * 第三、四个构造函数：实现截取一部分字体给图；
         * 第二个构造函数：最强大，因为传入的可以是charSequence类型字体，所以可以实现绘制带图片的扩展文字（待续），
         * 而且还能截取一部分绘制
         * 这几个函数就不再多说了，很简单，前面我们也一直在用第一个构造函数，文字截取一般用不到，
         * 我也不多说了，浪费时间，可能大家看到有个构造函数中，可以传入charSequence类型的字符串，
         * charSequence是可以利用spannableString来构造有图片的字符串的，那这里是不是可以画出带有图片的字符串来呢 ，
         * ，实际证明，canvas画图是不支持Span替换的。所以这里的charSequence跟普通的String没有任何区别的
         */

       /* 【8】指定个个文字位置
        void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
        void drawPosText (String text, float[] pos, Paint paint)

        说明：
        第一个构造函数：实现截取一部分文字绘制；

        参数说明：
        char[] text：要绘制的文字数组
        int index:：第一个要绘制的文字的索引
        int count：要绘制的文字的个数，用来算最后一个文字的位置，从第一个绘制的文字开始算起
        float[] pos：每个字体的位置，同样两两一组，如｛x1,y1,x2,y2,x3,y3……｝*/


        mPaint.setColor(Color.RED);  //设置画笔颜色
        mPaint.setFakeBoldText(false);
        mPaint.setUnderlineText(false);
        mPaint.setTextSkewX((float) -0.25);
        mPaint.setStrikeThruText(false);

        mPaint.setStrokeWidth(5);//设置画笔宽度
        mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.setTextSize(50);//设置文字大小
        mPaint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充
        float[] pos = new float[]{
                20, 650,
                20, 750,
                20, 850,
                20, 950
        };
        String s = "朋友你好";
        canvas.drawPosText(s, pos, mPaint);

        /**
         * [9]沿路径绘制
         void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
         void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)

         参数说明：

         有关截取部分字体绘制相关参数（index,count），没难度，就不再讲了，下面首重讲hOffset、vOffset
         float hOffset  : 与路径起始点的水平偏移距离
         float vOffset  : 与路径中心的垂直偏移量
         */

        mPaint.setStrokeWidth(5);//设置画笔宽度
        mPaint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.setTextSize(30);//设置文字大小
        mPaint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充

        String string = "0 1 2 3 4 5 6 7 8 9 ";

        Path path6 = new Path();
        path6.addCircle(200, 650, 80, Path.Direction.CCW);
        canvas.drawPath(path6, mPaint);

        Path path7 = new Path();
        path7.addCircle(200, 850, 80, Path.Direction.CW);
        canvas.drawPath(path7, mPaint);

        mPaint.setTextSkewX(0);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);//设置画笔宽度
        canvas.drawTextOnPath(string, path6, 0, 30, mPaint);

        canvas.drawTextOnPath(string, path7, 0, 30, mPaint);


        /**
         * 【10】字体样式设置
         * paint.setTypeface(typeface);
         Typeface相关
         概述：Typeface是专门用来设置字体样式的，通过paint.setTypeface()来指定。
         可以指定系统中的字体样式，也可以指定自定义的样式文件中获取。要构建Typeface时，
         可以指定所用样式的正常体、斜体、粗体等，如果指定样式中，没有相关文字的样式就会用系统默认的样式来显示
         ，一般默认是宋体。

         创建Typeface：
         Typeface	create(String familyName, int style) //直接通过指定字体名来加载系统中自带的文字样式
         Typeface	create(Typeface family, int style)     //通过其它Typeface变量来构建文字样式
         Typeface	createFromAsset(AssetManager mgr, String path) //通过从Asset中获取外部字体来显示字体样式
         Typeface	createFromFile(String path)//直接从路径创建
         Typeface	createFromFile(File path)//从外部路径来创建字体样式
         Typeface	defaultFromStyle(int style)//创建默认字体

         上面的各个参数都会用到Style变量,Style的枚举值如下:
         Typeface.NORMAL  //正常体
         Typeface.BOLD	 //粗体
         Typeface.ITALIC	 //斜体
         Typeface.BOLD_ITALIC //粗斜体

         （1）、使用系统中的字体
         从上面创建Typeface的所有函数中可知，使用系统中自带的字体有下面两种方式来构造Typeface：

         Typeface	defaultFromStyle(int style)//创建默认字体
         Typeface	create(String familyName, int style) //直接通过指定字体名来加载系统中自带的文字样式

         其实，第一个仅仅是使用系统默认的样式来绘制字体，基本没有可指定性，就不再讲了，
         使用起来难度也不大，下面只以第二个构造函数为例，指定宋体绘制：

         */

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setTextSize(60);
        paint.setStyle(Paint.Style.STROKE);

//        String failyName = "黑体";
//        String failyName = "楷体";
        String failyName = "宋体";
        Typeface font = Typeface.create(failyName, Typeface.NORMAL);
        paint.setTypeface(font);
        canvas.drawText("字体设置：黑体", 300, 650, paint);

        /**
         * 自定义字体
         * 自定义字体的话，我们就需要从外部字体文件加载我们所需要的字形的，从外部文件加载字形所使用的Typeface构造函数如下面三个：
         Typeface	createFromAsset(AssetManager mgr, String path) //通过从Asset中获取外部字体来显示字体样式
         Typeface	createFromFile(String path)//直接从路径创建
         Typeface	createFromFile(File path)//从外部路径来创建字体样式

         后面两个从路径加载难度不大，而我们一般也不会用到，这里我们说说从Asset文件中加载；

         首先在Asset下建一个文件夹，命名为Fonts，然后将字体文件jian_luobo.ttf 放入其中
         */


        Paint paint1 = new Paint();
        paint1.setColor(Color.DKGRAY);

        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(2);
        paint1.setTextSize(60);
        paint1.setStyle(Paint.Style.FILL);

        AssetManager assets = mContext.getAssets();
        Typeface typeface = Typeface.createFromAsset(assets, "font/FZSTK.TTF");
//        Typeface typeface = Typeface.createFromAsset(assets, "font/tips.ttf");
        paint1.setTypeface(typeface);
        canvas.drawText("中国欢迎您！", 300, 800, paint1);

    }



}
