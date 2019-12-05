package com.example.final_game.Memorize;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

/** A Player of Memorize Game */
class MemorizePlayer implements MemoryInterface {
  /** Total moves available to the Player */
  private int playerMoves;
  /** Points earned by the Player */
  private int playerPoints;
  /** The Text on the screen which keeps track of the moves left */
  private TextView textMoves;
  /** The Text on the screen which keeps track of the points earned by the player */
  private TextView textPoints;
  /** The stop watch which keeps track of the time spent by the player to complete the game */
  private Chronometer chronometer;
  /** The Text on the screen which keeps displays the points player should earn by the end to win */
  private TextView threshold;
  /** The Text on the screen which keeps track of the current level the player is on. */
  private TextView currLevel;

  /**
   * Construct a new player of the Memorize! game, allots the number of moves they are allowed to
   * make, sets their timer, and initializes how many points they start off with.
   */
  MemorizePlayer(TextView tv1, TextView tv2, TextView tv3, TextView tv4, Chronometer meter) {
    playerMoves = 15;
    this.currLevel = tv3;
    this.textMoves = tv1;
    this.chronometer = meter;
    this.playerPoints = 0;
    this.textPoints = tv2;
    this.threshold = tv4;
  }

  /**
   * Update the corresponding text box in the layout after a match is attempted to reflect how many
   * moves the player has left.
   */
  void setTextMoves() {
    String new_text = "Moves Left:" + this.getMovesLeft();
    this.textMoves.setText(new_text);
  }

  /**
   * Update the corresponding text box in the layout after a match is attempted to reflect how many
   * points the player has.
   */
  void setTextPoints() {
    String new_text = "Score:" + this.getPlayerPoints();
    this.textPoints.setText(new_text);
  }

  /** Returns this player's timer. */
  Chronometer getChronometer() {
    return chronometer;
  }

  /** Returns how many moves this player has left. */
  int getMovesLeft() {
    return playerMoves;
  }

  /** Returns this player's point tally. */
  int getPlayerPoints() {
    return playerPoints;
  }

  /** Increase this player's points. */
  void increasePlayerPoints() {
    this.playerPoints = this.playerPoints + 2;
  }

  /** If this player has any points, decrease them. */
  void decreasePlayerPoints() {
    if (this.playerPoints != 0) {
      this.playerPoints--;
    }
  }

  /** Decrease how many remaining moves this player has. */
  void decreasePlayerMoves() {
    this.playerMoves--;
  }

  /** Set whether the moves counter will be visible or not */
  public void setVisibility() {
    this.textMoves.setVisibility(View.INVISIBLE);
  }

  /** Set the current level */
  @SuppressLint("SetTextI18n")
  void setLevel(int num) {
    if (num == 1) {
      currLevel.setText("LEVEL-I");
      threshold.setText("/7");
    } else if (num == 2) {
      currLevel.setText("LEVEL-II");
      threshold.setText("/9");
    } else {
      currLevel.setText("LEVEL-III");
      threshold.setText("/11");
    }
  }
}
