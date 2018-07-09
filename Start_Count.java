package com.hangleking.hdh.hangleking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

public class Start_Count {
    public Bitmap Count[] = new Bitmap[3]; // 단어부서짐 이미지

    public int nW_x, nW_y ; // 단어 좌표
    public int sW_x, sW_y ; // 단어 크기

    public boolean dead = false; // 터질것인지 여부

    public long lastTime_StartTime; // 경과 시간

    public int alpha = 255; // 이미지의 Alpha (투명도)

    //----------------------------------
    // 생성자
    //----------------------------------
    public Start_Count(Context context){

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        nW_x = display.getWidth()  / 3;
        nW_y = display.getHeight() / 2;

        sW_x = display.getWidth()  / 4;          //이미지의 x축 크기
        sW_y = display.getHeight() / 3;          //이미지의 y축 크기

        Count[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.count_1 );
        Count[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.count_2 );
        Count[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.count_3 );

//        for(int i = 0; i < Count.length; i++) {
//            Count[i] = Bitmap.createScaledBitmap(Count[i], sW_x, sW_y, true);       //크기조정
//        }

        lastTime_StartTime = System.currentTimeMillis (); // 현재 시각
    }


}
