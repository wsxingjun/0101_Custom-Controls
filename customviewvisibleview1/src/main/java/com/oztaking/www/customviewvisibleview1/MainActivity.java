package com.oztaking.www.customviewvisibleview1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private Button mButtonReset;
    //    private MyView view;
    private MyViewFingerPaint view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.paint_layout);
//        setContentView(R.layout.acitivty_myviewfingerpaint_layout);
//        setContentView(R.layout.acitivty_myview);

//        FrameLayout root = (FrameLayout) findViewById(R.id.root);
//        root.addView(new MyViewPaint(getApplicationContext()));
//        root.addView(new MyViewPaintPathAndText(getApplicationContext()));
//        root.addView(new MyViewRange(getApplicationContext()));
//        root.addView(new MyViewDrawText(getApplicationContext()));
//        root.addView(new MyViewBezier(getApplicationContext()));

//         view = (MyViewFingerPaint) findViewById(R.id.myviewFingerPaint);
//         mButtonReset = (Button) findViewById(R.id.bt_reset);
//          mButtonReset.setOnClickListener(this);

        //贝塞尔动画
//        MyViewBoBo myViewBoBo = new MyViewBoBo(getApplicationContext());
//        root.addView(myViewBoBo);
//        myViewBoBo.startAnim();

//        FrameLayout root = (FrameLayout) findViewById(R.id.root);
//        root.addView(new MyViewPaintAll(getApplicationContext()));

//
//        FrameLayout root = (FrameLayout) findViewById(R.id.root);
//        root.addView(new MyViewColorMatrix08(getApplicationContext()));

//        //Canvas 画布
//        FrameLayout root = (FrameLayout) findViewById(R.id.root);
//        root.addView(new MyViewCanvas13(getApplicationContext()));

        //Canvas 画布
        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        root.addView(new MyViewQQDeleteDemo15(getApplicationContext()));
    }

//
//    @Override
//    public void onClick(View v){
////        view.reset();
//        view.resetCanvas();
//    }

}
