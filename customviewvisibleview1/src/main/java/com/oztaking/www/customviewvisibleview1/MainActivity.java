package com.oztaking.www.customviewvisibleview1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.paint_layout);

        FrameLayout root = (FrameLayout) findViewById(R.id.root);
        root.addView(new MyViewPaint(getApplicationContext()));

    }
}
