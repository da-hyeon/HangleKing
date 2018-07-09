package com.hangleking.hdh.hangleking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;

public class Word_Loan {

    public int nW_x, nW_y ; // 단어 좌표
    public int sW_x, sW_y ; // 단어 크기
    public int m_rad;
    public Bitmap LoanWord; // 단어 이미지

    public long lastTime_LoanWord; // 경과 시간

    public boolean dead = false; // 터질것인지 여부

    public int alpha = 255; // 이미지의 Alpha (투명도)

    public int index = (int) (Math.random() * 4);          //단어개수만큼 랜덤ㄱ

    public static final int ran[]= { R.drawable.loanword_bag , R.drawable.loanword_game , R.drawable.loanword_gas ,
            R.drawable.loanword_godu , R.drawable.loanword_gomu };

    private int res = ran[index];               //res에 ran에서 랜덤으로 하나 뽑은것을 저장

    //----------------------------------
    // 생성자
    //----------------------------------
    public Word_Loan(int _x, int _y , Context context){

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        nW_x = _x;
        nW_y = _y;

        sW_x = display.getWidth() / 5;          //이미지의 x축 크기
        sW_y = display.getHeight() / 5;          //이미지의 y축 크기

        m_rad = ((sW_x^2 + sW_y^2) / 3);

        LoanWord = BitmapFactory.decodeResource(context.getResources(), res);               //이미지생성
        LoanWord = Bitmap.createScaledBitmap(LoanWord, sW_x, sW_y, true);       //크기조정

        lastTime_LoanWord = System.currentTimeMillis (); // 현재 시각
    }

    //----------------------------------
    // Alpha값 변경
    //----------------------------------
    public boolean WordLoan_MeltHole() {
        alpha -= 5;
        if (alpha <= 0)
            return true;
        else
            return false;
    }
}
