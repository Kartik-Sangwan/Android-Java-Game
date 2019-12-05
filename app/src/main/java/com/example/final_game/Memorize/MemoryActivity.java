package com.example.final_game.Memorize;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.final_game.R;

public class MemoryActivity extends AppCompatActivity {
  MemoryView game_view;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow()
        .setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    String theme = getIntent().getExtras().get("Theme?").toString();
    int level = (int) getIntent().getExtras().get("Level?");
    Toast.makeText(getApplicationContext(), "LEVEL " + level, Toast.LENGTH_LONG).show();
    if (theme.equals("Light")) {
      setContentView(R.layout.activity_memorygame);
      MemoryPresenter presenter = new MemoryPresenter(this, theme, level);
      game_view = new MemoryView(this, presenter, "L");
    } else {
      setContentView(R.layout.activity_memorygame_dark);
      MemoryPresenter presenter = new MemoryPresenter(this, theme, level);
      game_view = new MemoryView(this, presenter, "D");
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }
}
