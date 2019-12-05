package com.example.final_game.Memorize;

import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_game.R;

class MemoryFactory {
  /** The Context which stores the AppCompatActivity which is using this class */
  private AppCompatActivity context;

  /**
   * Construct a new MemoryFactory the Memorize! game, which creates and returns MemoryInterface
   * Object used by the game.
   */
  MemoryFactory(AppCompatActivity activity) {
    context = activity;
  }

  /** Creates and returns MemoryInterface * Object used by the game. */
  MemoryInterface getMemoryObjects(String type, int slot) {
    if (type.equals("Player")) {
      return new MemorizePlayer(
          (TextView) context.findViewById(R.id.text_moves),
          (TextView) context.findViewById(R.id.text_points),
          (TextView) context.findViewById(R.id.level),
          (TextView) context.findViewById(R.id.text_threshold),
          (Chronometer) context.findViewById(R.id.stopWatch));
    } else if (type.equals("Card")) {
      return new PlayingCard((ImageView) context.findViewById(slot));
    } else {
      return new MemoryInterface() {
        @Override
        public void setVisibility() {}
      };
    }
  }
}
