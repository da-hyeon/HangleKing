package com.hangleking.hdh.hangleking;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        //-----------------------배경음악 재생
        player = MediaPlayer.create(this, R.raw.gamebgm);
        //player.start();

        //player.setLooping(true);


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        player.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        //player.start();
        super.onResume();
    }


}