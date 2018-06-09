package com.oztaking.www.customviewgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setContentView(R.layout.custom_layout);
        setContentView(R.layout.custom_wrapcontent_layout);
//        setContentView(R.layout.custom_layout_z);
    }
}
