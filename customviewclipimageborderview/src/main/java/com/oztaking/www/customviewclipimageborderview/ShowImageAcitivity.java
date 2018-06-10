package com.oztaking.www.customviewclipimageborderview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ShowImageAcitivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_acitivity);

        mImageView = (ImageView) findViewById(R.id.id_showImage);
        byte[] bitmaps = getIntent().getByteArrayExtra("bitmap");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmaps,0,bitmaps.length);
        if (bitmap != null){
            mImageView.setImageBitmap(bitmap);
        }

    }
}
