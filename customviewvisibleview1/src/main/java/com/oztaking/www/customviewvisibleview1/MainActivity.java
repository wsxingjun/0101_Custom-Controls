package com.oztaking.www.customviewvisibleview1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButtonReset;
    private MyViewFingerPaint view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.paint_layout);
        setContentView(R.layout.acitivty_myviewfingerpaint_layout);

//        FrameLayout root = (FrameLayout) findViewById(R.id.root);
//        root.addView(new MyViewPaint(getApplicationContext()));
//        root.addView(new MyViewPaintPathAndText(getApplicationContext()));
//        root.addView(new MyViewRange(getApplicationContext()));
//        root.addView(new MyViewDrawText(getApplicationContext()));
//        root.addView(new MyViewBezier(getApplicationContext()));

        view = (MyViewFingerPaint) findViewById(R.id.myviewFingerPaint);
        mButtonReset = (Button) findViewById(R.id.bt_reset);
          mButtonReset.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        view.resetCanvas();
    }
}
