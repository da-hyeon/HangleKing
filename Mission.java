package com.hangleking.hdh.hangleking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

public class Mission {
    public Bitmap Mission[] = new Bitmap[6]; // 단어부서짐 이미지

    public int nW_x, nW_y ; // 단어 좌표
    public int sW_x = 0, sW_y = 0; // 단어 크기

    public boolean dead = false; // 터질것인지 여부

    public long lastTime_Mission; // 경과 시간

    public int alpha = 0; // 이미지의 Alpha (투명도)

    //----------------------------------
    // 생성자
    //----------------------------------
    public Mission(Context context){

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        nW_x = display.getWidth()  / 3;
        nW_y = display.getHeight() / 2;

        Mission[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mission_1 );
        Mission[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mission_2 );
        Mission[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mission_3 );
        Mission[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mission_4 );
        Mission[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mission_5 );
        Mission[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.missionclear );



        lastTime_Mission = System.currentTimeMillis (); // 현재 시각
    }

    //----------------------------------
    // Alpha값 변경
    //----------------------------------
    public boolean Mission_Alpha() {
        alpha += 5;
        if (alpha >= 255)
            return true;
        else
            return false;
    }
}
