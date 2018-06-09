package com.oztaking.www.customviewprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private HorizontalProgressBarWithNum mHorizontalProgressBar,mPb;
    private static final int MSG_PROGRESS_UPDATE = 0x110;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int progress1 = mHorizontalProgressBar.getProgress();
            int progress2 = mPb.getProgress();
            if (progress1 <= 100 || progress2 <= 100){
                progress1 +=5;
                progress2 +=5;
                mHorizontalProgressBar.setProgress(progress1);
                mPb.setProgress(progress2);
            }

            if (progress1 >= 100 || progress2 >= 100){
                mHandler.removeMessages(MSG_PROGRESS_UPDATE);
            }


            mHandler.sendEmptyMessageDelayed(MSG_PROGRESS_UPDATE,100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_progressbar);
        mHorizontalProgressBar = (HorizontalProgressBarWithNum) findViewById(R.id.cpb_customHorizontalprogressBar);
        mPb = (HorizontalProgressBarWithNum) findViewById(R.id.pb);
        mHandler.sendEmptyMessage(MSG_PROGRESS_UPDATE);

    }
}
