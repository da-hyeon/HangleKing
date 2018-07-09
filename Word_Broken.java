package com.hangleking.hdh.hangleking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

public class Word_Broken {
    public Bitmap Broken_Word[] = new Bitmap[4]; // 단어부서짐 이미지

    public int nW_x, nW_y ; // 단어 좌표
    public int sW_x, sW_y ; // 단어 크기

    public boolean dead = false; // 터질것인지 여부

    public long lastTime_LoanWord; // 경과 시간

    public int alpha = 255; // 이미지의 Alpha (투명도)

    //----------------------------------
    // 생성자
    //----------------------------------
    public Word_Broken(int _x, int _y , Context context){

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        nW_x = _x;
        nW_y = _y;

        sW_x = display.getWidth() / 5;          //이미지의 x축 크기
        sW_y = display.getHeight() /5;          //이미지의 y축 크기

        Broken_Word[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.broken_word1 );
        Broken_Word[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.broken_word2 );
        Broken_Word[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.broken_word3 );
        Broken_Word[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.broken_word4 );

        for(int i = 0; i < Broken_Word.length; i++) {
            Broken_Word[i] = Bitmap.createScaledBitmap(Broken_Word[i], sW_x, sW_y, true);       //크기조정
        }

        lastTime_LoanWord = System.currentTimeMillis (); // 현재 시각
    }
}
