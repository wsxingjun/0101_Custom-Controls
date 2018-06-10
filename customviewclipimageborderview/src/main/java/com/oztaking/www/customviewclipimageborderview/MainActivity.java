package com.oztaking.www.customviewclipimageborderview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.oztaking.www.customviewclipimageborderview.zoomImageView.ClipImageLayout;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private ClipImageLayout mClipImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClipImageLayout = (ClipImageLayout) findViewById(R.id.cp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
             case R.id.id_action_clip:

                 Bitmap bitmap = mClipImageLayout.clip();
                 ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                 byte[] datas = bos.toByteArray();

                 Intent intent = new Intent(this,ShowImageAcitivity.class);
                 intent.putExtra("bitmap",datas);
                 startActivity(intent);

                 break;
             default:
                  break;
        }

        return super.onOptionsItemSelected(item);
    }
}
