package com.hangleking.hdh.hangleking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends View {

    int width, height; // View의 크기
    Bitmap imgBack; // 배경
    Bitmap ui; // 게임 하단 UI
    Bitmap character; // 게임 하단 캐릭터

    //public int Word_Cycle = (int)(Math.random() * 1000) + 500;                //단어생성주기
    public int Word_Create_Cycle = 500;                //단어생성주기
    public int Word_Broken_Cycle = 1500;       //단어삭제주기

    public int LIFE = 10;               //목숨
    public int SCORE = 0;               //점수
    public int Correct_Score = 0;      //맞은 갯수
    public int Wrong_Score = 0;        //틀린 갯수
    public int Combo = 0;               //콤보
    public int Combostack = 0;               //콤보stack

    int Ani_Num_Broken_Counter = 0, Ani_Num_Broken = 0;           //단어부서짐 애니메이션 변수
    int Start_Count = 2;

    Start_Count mStartCount;         //시작카운트 객체 선언
    Word_Location word_location;      //단어위치 객체 선언

    ArrayList<Word_Native> mWordNative;              //고유어 ArrayList 선언
    ArrayList<Word_Loan> mWordLoan;                  //외래어 ArrayList 선언
    ArrayList<Word_Broken> mWordBroken;              //단어부서짐 ArrayList 선언
    ArrayList<Word_Answer_Correct> mWordCorrect;         //맞음 ArrayList 선언
    ArrayList<Word_Answer_Wrong> mWordWrong;         //틀림 ArrayList 선언

    int Native_Word_X, Native_Word_Y;           //고유어 X, Y값 저장변수
    int Loan_Word_X, Loan_Word_Y;           //외래어 X, Y값 저장변수

    SoundPool sound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);           //효과음

    int CorrectSound = sound.load(getContext(), R.raw.correct, 1);                     //맞을때 효과음
    int WrongSound = sound.load(getContext(), R.raw.wrong, 1);                         //틀릴때 효과음


    boolean StartCheck = false;

    public GameView(Context context) {
        super(context);

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        mStartCount = new Start_Count(getContext()); // 시작카운트 객체 생성

        width = display.getWidth(); // View의 가로 폭
        height = display.getHeight(); // View의 세로 높이

        word_location = new Word_Location(getContext());

        imgBack = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_back);
        imgBack = Bitmap.createScaledBitmap(imgBack, width, height, true);

        ui = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameui);
        ui = Bitmap.createScaledBitmap(ui, width, height / 5, true);

        character = BitmapFactory.decodeResource(context.getResources(), R.drawable.character);
        character = Bitmap.createScaledBitmap(character, width / 5, height / 5, true);

        mWordNative = new ArrayList<Word_Native>(); // 고유어 ArrayList 생성
        mWordLoan = new ArrayList<Word_Loan>(); // 고유어 ArrayList 생성
        mWordBroken = new ArrayList<Word_Broken>(); // 단어부서짐 ArrayList 생성
        mWordCorrect = new ArrayList<Word_Answer_Correct>(); // 틀림 ArrayList 생성
        mWordWrong = new ArrayList<Word_Answer_Wrong>(); // 틀림 ArrayList 생성

        mHandler.sendEmptyMessageDelayed(0, 10); // Handler 호출
        Word_Time_Handler.sendEmptyMessageDelayed(0, Word_Create_Cycle);  //단어생성 핸들러
        Start_Count_Handler.sendEmptyMessageDelayed(0, 1000);  //시작카운트 핸들러
    }

    //----------------------------------
    // 그려주는부분
    //----------------------------------
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(imgBack, 0, 0, null);
        canvas.drawBitmap(ui, 0, height - (height / 5), null);
        canvas.drawBitmap(character, 0, height - (height / 5), null);

        Melt_Word();           //일정시간 후 단어 녹이기
        Delete_Word();         //단어삭제 체크

        Paint paint = new Paint();                                      //글씨를 써주기 위함
        paint.setColor(Color.WHITE);
        paint.setTextSize(character.getWidth() / 7);

        //목숨 그리는 부분
        canvas.drawText(" X ", character.getWidth(), height - (character.getHeight() / 3), paint);
        canvas.drawText(" " + LIFE + " ", character.getWidth() + 100, height - (character.getHeight() / 3), paint);

        //점수 그리는 부분
        canvas.drawText(SCORE + "", width / 2, (float) (height - (character.getHeight() / 1.6)), paint);

        //맞은 갯수 그리는 부분
        canvas.drawText(Correct_Score + "", (float) (width / 1.7), (float) (height - (character.getHeight() / 3.7)), paint);

        //틀린 갯수 그리는 부분
        canvas.drawText(Wrong_Score + "", (float) (width / 1.1), (float) (height - (character.getHeight() / 1.6)), paint);


        //콤보 그리는 부분
        if (Combostack >= Combo) {
            Combo = Combostack;
        }
        canvas.drawText(Combo + "", (float) (width / 1.1), (float) (height - (character.getHeight() / 3.7)), paint);

        //----------------------------------
        // 시작카운트 그리기
        //----------------------------------
        paint.setAlpha(mStartCount.alpha);
        if(Start_Count >= 0) {
            canvas.drawBitmap(mStartCount.Count[Start_Count], (float)(mStartCount.nW_x * 1.2), mStartCount.nW_y / 2, paint);
        }


        //----------------------------------
        // 고유어 그리기
        //----------------------------------
        for (Word_Native tNative_Word : mWordNative) {                //단어그리기
            paint.setAlpha(tNative_Word.alpha);
            canvas.drawBitmap(tNative_Word.NativeWord, tNative_Word.nW_x, tNative_Word.nW_y, paint);
            word_location.rollX();
            word_location.rollY();
            //word_location.Word_Overlap_True_Check(word_location.getFaceValueX() , word_location.getFaceValueY());
            Native_Word_X = word_location.getFaceValueX();            //랜덤함수 값 저장
            Native_Word_Y = word_location.getFaceValueY();            //랜덤함수 값 저장
        }

        //----------------------------------
        // 외래어 그리기
        //----------------------------------
        for (Word_Loan tLoan_Word : mWordLoan) {                //단어그리기
            paint.setAlpha(tLoan_Word.alpha);
            canvas.drawBitmap(tLoan_Word.LoanWord, tLoan_Word.nW_x, tLoan_Word.nW_y, paint);
            word_location.rollX();
            word_location.rollY();
            //word_location.Word_Overlap_True_Check(word_location.getFaceValueX() , word_location.getFaceValueY());
            Loan_Word_X = word_location.getFaceValueX();            //랜덤함수 값 저장
            Loan_Word_Y = word_location.getFaceValueY();            //랜덤함수 값 저장
        }


        //----------------------------------
        // 단어부서짐 그리기
        //----------------------------------
        Ani_Num_Broken_Counter++;
        Ani_Num_Broken = Ani_Num_Broken_Counter % 40 / 10;
        for (Word_Broken tWord_Broken : mWordBroken) {                //단어그리기
            paint.setAlpha(tWord_Broken.alpha);
            canvas.drawBitmap(tWord_Broken.Broken_Word[Ani_Num_Broken], tWord_Broken.nW_x, tWord_Broken.nW_y, paint);
        }

        //----------------------------------
        // 단어맞음 그리기
        //----------------------------------
        for (Word_Answer_Correct tWord_Answer_Correct : mWordCorrect) {                //단어그리기
            paint.setAlpha(tWord_Answer_Correct.alpha);
            canvas.drawBitmap(tWord_Answer_Correct.Correct, tWord_Answer_Correct.nW_x, tWord_Answer_Correct.nW_y, paint);
        }


        //----------------------------------
        // 단어틀림 그리기
        //----------------------------------
        for (Word_Answer_Wrong tWord_Answer_Wrong : mWordWrong) {                //단어그리기
            paint.setAlpha(tWord_Answer_Wrong.alpha);
            canvas.drawBitmap(tWord_Answer_Wrong.Wrong, tWord_Answer_Wrong.nW_x, tWord_Answer_Wrong.nW_y, paint);
        }
        paint.setAlpha(255);
    }

    //----------------------------------
    // 단어 제거
    //----------------------------------
    private void Delete_Word() {

        //시작카운트 삭제
        if (mStartCount.dead == true) {
            StartCheck = true;
        }

        //고유어삭제
        for (int i = mWordNative.size() - 1; i >= 0; i--) {
            if (mWordNative.get(i).dead == true)
                mWordNative.remove(i);
            //word_location.Word_Overlap_False_Check(mWordNative.get(i).nW_x , mWordNative.get(i).nW_y);          //체크해제
        }

        //외래어 삭제
        for (int i = mWordLoan.size() - 1; i >= 0; i--) {
            if (mWordLoan.get(i).dead == true)
                mWordLoan.remove(i);
            //word_location.Word_Overlap_False_Check(mWordLoan.get(i).nW_x , mWordLoan.get(i).nW_y);          //체크해제
        }

        //단어부서짐 삭제
        for (int i = mWordBroken.size() - 1; i >= 0; i--) {
            if (mWordBroken.get(i).dead == true)
                mWordBroken.remove(i);
        }

        //맞음삭제
        for (int i = mWordCorrect.size() - 1; i >= 0; i--) {
            if (mWordCorrect.get(i).dead == true)
                mWordCorrect.remove(i);
        }

        //틀림삭제
        for (int i = mWordWrong.size() - 1; i >= 0; i--) {
            if (mWordWrong.get(i).dead == true)
                mWordWrong.remove(i);
        }
    }

    //----------------------------------
    // 단어 녹이기
    //----------------------------------
    private void Melt_Word() {
        long thisTime = System.currentTimeMillis();

        //고유어 녹이기
        for (int i = mWordNative.size() - 1; i >= 0; i--) {
            if (thisTime - mWordNative.get(i).lastTime_NativeWord >= Word_Broken_Cycle) {
                //if (mWordNative.get(i).WordNative_MeltHole() == true) {
                mWordCorrect.add(new Word_Answer_Correct(mWordNative.get(i).nW_x, mWordNative.get(i).nW_y, getContext()));
                mWordNative.get(i).dead = true;
                SCORE += 10;
                Correct_Score++;
                Combostack++;
                // }
            }
        }

        //외래어 녹이기
        for (int i = mWordLoan.size() - 1; i >= 0; i--) {
            if (thisTime - mWordLoan.get(i).lastTime_LoanWord >= Word_Broken_Cycle) {
                //if (mWordLoan.get(i).WordLoan_MeltHole() == true) {
                mWordWrong.add(new Word_Answer_Wrong(mWordLoan.get(i).nW_x, mWordLoan.get(i).nW_y, getContext()));
                mWordLoan.get(i).dead = true;
                LIFE--;
                Wrong_Score++;
                Combostack = 0;
                // }
            }
        }

        //단어부서짐 녹이기
        for (int i = mWordBroken.size() - 1; i >= 0; i--) {
            if (thisTime - mWordBroken.get(i).lastTime_LoanWord >= 300) {
                mWordBroken.get(i).dead = true;
            }
        }

        //맞음 녹이기
        for (int i = mWordCorrect.size() - 1; i >= 0; i--) {
            if (thisTime - mWordCorrect.get(i).lastTime_Wrong >= 300) {
                mWordCorrect.get(i).dead = true;
            }
        }

        //틀림 녹이기
        for (int i = mWordWrong.size() - 1; i >= 0; i--) {
            if (thisTime - mWordWrong.get(i).lastTime_Wrong >= 300) {
                mWordWrong.get(i).dead = true;
            }
        }
    }

    //------------------------------------
    // onTouch Event
    //------------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Click_Check_Word(x, y);
        }
        return true;
    }

    //----------------------------------
    // 단어 클릭 처리
    //----------------------------------
    private void Click_Check_Word(int x, int y) {
        //고유어 클릭체크
        for (Word_Native tWord_Native : mWordNative) {
            if (Math.pow(tWord_Native.nW_x - x + 170, 2) + Math.pow(tWord_Native.nW_y - y + 100, 2) <= Math.pow(tWord_Native.m_rad - 50, 2)) {
                mWordBroken.add(new Word_Broken(tWord_Native.nW_x, tWord_Native.nW_y, getContext()));
                mWordWrong.add(new Word_Answer_Wrong(tWord_Native.nW_x, tWord_Native.nW_y, getContext()));
                sound.play(WrongSound, 1.0F, 1.0F, 1, 0, 1.0F);
                Ani_Num_Broken_Counter = 0;
                tWord_Native.dead = true;
                LIFE--;
                Wrong_Score++;
                Combostack = 0;
            }
        }

        //외래어 클릭체크
        for (Word_Loan tWord_Loan : mWordLoan) {
            if (Math.pow(tWord_Loan.nW_x - x + 170, 2) + Math.pow(tWord_Loan.nW_y - y + 100, 2) <= Math.pow(tWord_Loan.m_rad - 50, 2)) {
                mWordBroken.add(new Word_Broken(tWord_Loan.nW_x, tWord_Loan.nW_y, getContext()));
                mWordCorrect.add(new Word_Answer_Correct(tWord_Loan.nW_x, tWord_Loan.nW_y, getContext()));
                sound.play(CorrectSound, 1.0F, 1.0F, 1, 0, 1.0F);
                Ani_Num_Broken_Counter = 0;
                tWord_Loan.dead = true;
                SCORE += 10;
                Correct_Score++;
                Combostack++;
            }
        }
    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            invalidate(); // View를 다시 그림
            mHandler.sendEmptyMessageDelayed(0, 10);
        }
    }; // Handler

    //단어생성 핸들러
    Handler Word_Time_Handler = new Handler() {
        public void handleMessage(Message msg) {
            int Ran = (int) (Math.random() * 2);          //고유어 , 외래어 랜덤호출
            if(StartCheck) {
                if (Ran == 0) {
                    mWordNative.add(new Word_Native(Native_Word_X, Native_Word_Y, getContext()));
                } else {
                    mWordLoan.add(new Word_Loan(Loan_Word_X, Loan_Word_Y, getContext()));
                }
            }
            Word_Time_Handler.sendEmptyMessageDelayed(0, Word_Create_Cycle);
        }
    }; // Handler

    Handler Start_Count_Handler = new Handler() {
        public void handleMessage(Message msg) {

            if (Start_Count < 0) {
                mStartCount.dead = true;
            } else {
                Start_Count--;
            }
            Start_Count_Handler.sendEmptyMessageDelayed(0, 1000);
        }
    }; // Handler
}