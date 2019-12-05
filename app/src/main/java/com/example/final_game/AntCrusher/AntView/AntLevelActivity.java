package com.example.final_game.AntCrusher.AntView;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_game.R;

public class AntLevelActivity extends AppCompatActivity {
  int level;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_level);

    level = (int) getIntent().getExtras().get("Level");

    TextView text = findViewById(R.id.newLevel);

    text.setText("LEVEL UP");

    Thread thread =
        new Thread() {
          @Override
          public void run() {
            try {
              sleep(2000);
              finish();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        };
    thread.start();
  }

  @Override
  protected void onPause() {
    super.onPause();
    finish();
  }
}
