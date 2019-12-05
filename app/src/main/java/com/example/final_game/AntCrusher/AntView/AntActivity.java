package com.example.final_game.AntCrusher.AntView;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.final_game.AntCrusher.playable;
import com.example.final_game.R;

public class AntActivity extends AppCompatActivity implements playable {
  DonutView gameView;
  int receive_intent;
  int soundId;
  SoundPool soundPool;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    receive_intent = (int) getIntent().getExtras().get("background");
    AudioAttributes audioAttributes =
        new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();
    soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
    soundId = soundPool.load(this, R.raw.antsound, 1);

    soundPool.setOnLoadCompleteListener(
        new SoundPool.OnLoadCompleteListener() {
          public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            boolean loaded = true;
          }
        });
    gameView = new DonutView(this, receive_intent, this, "Ant");
    setContentView(gameView);
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
  }

  @Override
  public void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override
  public void play() {
    soundPool.play(soundId, 1, 1, 1, 0, 1f);
  }
}
