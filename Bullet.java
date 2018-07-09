package com.hangleking.hdh.hangleking;

/**
 * Created by user on 2016-11-21.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Bullet {
    public int b_x, b_y; // 총알구멍 좌표
    public Bitmap hole[] = new Bitmap[3]; // 총알구멍 이미지
    public int bw, bh; // 총알구멍 size
    public long lastTime_Bullet; // 경과 시간
    public int alpha = 255; // 이미지의 Alpha (투명도)

    //----------------------------------
    // 생성자(Constructor)
    //----------------------------------
    public Bullet(int _x, int _y, Context context) {
        b_x = _x;
        b_y = _y;
        hole[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hole );
        hole[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hole );
        hole[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.hole );

        for(int i = 0;  i < hole.length; i++) {
            bw = hole[i].getWidth() / 2;
            bh = hole[i].getHeight() / 2;
        }
        lastTime_Bullet = System.currentTimeMillis (); // 현재 시각
    }

    // Alpha값 변경
//----------------------------------
    public boolean Bullet_MeltHole() {
        alpha -= 5;
        if (alpha <= 0)
            return true;
        else
            return false;
    }

}
