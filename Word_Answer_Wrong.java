package com.hangleking.hdh.hangleking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

public class Word_Answer_Wrong {
    public int nW_x, nW_y ; // 이미지 좌표
    public int sW_x, sW_y ; // 이미지 크기

    public Bitmap Wrong; // 이미지

    public long lastTime_Wrong; // 경과 시간

    public boolean dead = false; // 터질것인지 여부

    public int alpha = 255; // 이미지의 Alpha (투명도)
    //----------------------------------
    // 생성자
    //----------------------------------
    public Word_Answer_Wrong(int _x, int _y , Context context){

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();


        nW_x = _x;
        nW_y = _y;

        sW_x = display.getWidth() / 5;          //이미지의 x축 크기
        sW_y = display.getHeight() / 3;          //이미지의 y축 크기

        Wrong = BitmapFactory.decodeResource(context.getResources(), R.drawable.x);               //이미지생성
        Wrong = Bitmap.createScaledBitmap(Wrong, sW_x / 2, sW_y / 2, true);       //크기조정

        lastTime_Wrong = System.currentTimeMillis (); // 현재 시각
    }
}
