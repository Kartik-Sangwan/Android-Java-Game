package com.example.final_game.Memorize;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.final_game.Infrastructure.GameActivity;
import com.example.final_game.R;
import com.example.final_game.ui.login.DataBaseHelper;
import com.example.final_game.ui.login.MainActivity;

import static com.example.final_game.Memorize.MemoryPresenter.CARDS_LEFT2;
import static com.example.final_game.Memorize.MemoryPresenter.CARDS_LEFT3;
import static com.example.final_game.Memorize.MemoryPresenter.MOVES_LEFT2;
import static com.example.final_game.Memorize.MemoryPresenter.MOVES_LEFT3;
import static com.example.final_game.Memorize.MemoryPresenter.POINTS1;
import static com.example.final_game.Memorize.MemoryPresenter.POINTS2;
import static com.example.final_game.Memorize.MemoryPresenter.POINTS3;
import static com.example.final_game.Memorize.MemoryPresenter.SHARED_PREFS;
import static com.example.final_game.Memorize.MemoryPresenter.TIME1;
import static com.example.final_game.Memorize.MemoryPresenter.TIME2;
import static com.example.final_game.Memorize.MemoryPresenter.TIME3;

public class MemoryOverActivity extends GameActivity {

  @SuppressLint("SetTextI18n")
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_memory_over);
    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    String showMoves2 = sharedPreferences.getString(MOVES_LEFT2, "15");
    String showMoves3 = sharedPreferences.getString(MOVES_LEFT3, "15");
    String cardsLeft2 = sharedPreferences.getString(CARDS_LEFT2, "YES");
    String cardsLeft3 = sharedPreferences.getString(CARDS_LEFT3, "YES");
    String game_time1 = sharedPreferences.getString(TIME1, "00:00");
    int score1 = sharedPreferences.getInt(POINTS1, 0);
    String game_time2 = sharedPreferences.getString(TIME2, "00:00");
    int score2 = sharedPreferences.getInt(POINTS2, 0);
    String game_time3 = sharedPreferences.getString(TIME3, "01:00");
    int score3 = sharedPreferences.getInt(POINTS3, 0);

    Button mainMenu = findViewById(R.id.main);
    Button restart = findViewById(R.id.playAgain);
    TextView gameStatus1 = findViewById(R.id.status1);
    TextView gameTime1 = findViewById(R.id.time1);
    TextView finalScore1 = findViewById(R.id.score1);
    TextView movesLeft2 = findViewById(R.id.moves_left2);
    TextView gameStatus2 = findViewById(R.id.status2);
    TextView gameTime2 = findViewById(R.id.time2);
    TextView finalScore2 = findViewById(R.id.score2);
    TextView movesLeft3 = findViewById(R.id.moves_left3);
    TextView gameStatus3 = findViewById(R.id.status3);
    TextView gameTime3 = findViewById(R.id.time3);
    TextView finalScore3 = findViewById(R.id.score3);
    restart.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent1 = new Intent(MemoryOverActivity.this, MemoryBegin.class);
            startActivity(intent1);
          }
        });

    mainMenu.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent3 = new Intent(MemoryOverActivity.this, GameActivity.class);
            startActivity(intent3);
          }
        });

    if (score1 >= 7) {
      gameStatus1.setText("YOU WON!");
    } else {
      gameStatus1.setText("YOU LOST:(");
    }
    if (cardsLeft2.equals("NO")) {
      if (score2 >= 9) {
        gameStatus2.setText("YOU WON!");
      } else {
        gameStatus2.setText("YOU LOST:(");
      }
    } else {
      gameStatus2.setText("YOU LOST:(");
    }
    if (cardsLeft3.equals("NO")) {
      if (score3 >= 11) {
        gameStatus3.setText("YOU WON!");
      } else {
        gameStatus3.setText("YOU LOST:(");
      }
    } else {
      gameStatus3.setText("YOU LOST:(");
    }
    gameTime1.setText("Time:" + game_time1 + "s");
    finalScore1.setText("Final Score:" + score1);
    movesLeft2.setText("Moves Left:" + showMoves2);
    gameTime2.setText("Time:" + game_time2 + "s");
    finalScore2.setText("Final Score:" + score2);
    movesLeft3.setText("Moves Left:" + showMoves3);
    gameTime3.setText("Time Left:" + game_time3 + "s");
    finalScore3.setText("Final Score:" + score3);
    DataBaseHelper db = MainActivity.getGameDb();
    String username = DataBaseHelper.getUSERNAME();
    db.insertData(
        "GAME1STATS",
        username.substring(0, username.indexOf('@')),
        String.valueOf(score3),
        game_time3,
        showMoves3);
  }
}
