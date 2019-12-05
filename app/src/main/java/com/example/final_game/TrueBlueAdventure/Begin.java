package com.example.final_game.TrueBlueAdventure;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class Begin extends Activity {

  TrueBlueView gameView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    gameView = new TrueBlueView(this);
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
}
