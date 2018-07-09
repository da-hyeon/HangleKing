package com.hangleking.hdh.hangleking;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

public class Word_Location {


    private int faceValueX , faceValueY;                               //랜덤값 반환 변수

    private int LocationX , LocationY;

    boolean[][] Check = new boolean[5][4];

    private int width , height;

    private int LocX , LocY;

    public Word_Location(Context context){
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        width = display.getWidth() / 5;          //이미지의 x축 크기
        height = display.getHeight() / 5;          //이미지의 y축 크기

    }


    public int rollX() {
        LocationX = (int)(Math.random() * 5);

        switch (LocationX){
            case 0:
                faceValueX = 0;
                break;
            case 1:
                faceValueX = width;
                break;
            case 2:
                faceValueX = width * 2 ;
                break;
            case 3:
                faceValueX = width * 3;
                break;
            case 4:
                faceValueX = width * 4;
                break;
        }

        return faceValueX;
    }

    public int rollY() {
        LocationY = (int)(Math.random() * 4);

        switch (LocationY){
            case 0:
                faceValueY = 0;
                break;
            case 1:
                faceValueY = height ;
                break;
            case 2:
                faceValueY = height * 2 ;
                break;
            case 3:
                faceValueY = height * 3 ;
                break;
        }
        return faceValueY;
    }

    public int getFaceValueX(){
        return faceValueX;
    }

    public int getFaceValueY(){
        return faceValueY;
    }

    public int getLocationX(){
        return LocationX;
    }

    public int getLocationY(){
        return LocationY;
    }

    public void Word_Overlap_True_Check(int LocationX, int LocationY) {

        if (LocationX == 0) {
            LocX = 0;
        } else if (LocationX == width) {
            LocX = 1;
        } else if (LocationX == width * 2) {
            LocX = 2;
        } else if (LocationX == width * 3) {
            LocX = 3;
        } else {
            LocX = 4;
        }

        if (LocationY == 0) {
            LocY = 0;
        } else if (LocationY == height) {
            LocY = 1;
        } else if (LocationY == height * 2) {
            LocY = 2;
        } else {
            LocY = 3;
        }

        if (Check[LocX][LocY] == true) {                //해당 좌표에 단어가 위치해있다면
                rollX();                         //다시 X좌표를 롤링한다
                rollY();                        //다시 Y좌표를 롤링한다
        }else {                //해당 좌표에 단어가 위치해 있지 않다면
                Check[LocX][LocY] = true;        //해당좌표 체크함수를 true로 바꾼다
        }
    }

    public void Word_Overlap_False_Check(int LocationX , int LocationY) {

        if (LocationX == 0) {
            LocX = 0;
        } else if (LocationX == width) {
            LocX = 1;
        } else if (LocationX == width * 2) {
            LocX = 2;
        } else if (LocationX == width * 3) {
            LocX = 3;
        } else if (LocationX == width * 4) {
            LocX = 4;
        }

        if (LocationY == 0) {
            LocY = 0;
        } else if (LocationY == height) {
            LocY = 1;
        } else if (LocationY == height * 2) {
            LocY = 2;
        } else if (LocationY == height * 3) {
            LocY = 3;
        }

        Check[LocX][LocY] = false;
    }
}
