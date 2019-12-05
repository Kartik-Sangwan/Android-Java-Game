package com.example.final_game.Infrastructure;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.final_game.R;
import com.example.final_game.ui.login.MainActivity;

public class LeaderboardActivity extends AppCompatActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leaderboard);

    Button sortByScore1 = findViewById(R.id.score1);
    Button sortByScore2 = findViewById(R.id.score2);
    Button sortByScore3 = findViewById(R.id.score3);
    Button sortByName1 = findViewById(R.id.name1);
    Button sortByName2 = findViewById(R.id.name2);
    Button sortByName3 = findViewById(R.id.name3);
    Button sortByMoves1 = findViewById(R.id.moves1);
    Button sortByLevel1 = findViewById(R.id.level1);
    Button sortByLevel2 = findViewById(R.id.level2);
    final Button gameActivity = findViewById(R.id.menu);

    sortByName1.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByName("GAME1STATS");
            formatData(cur, 1);
          }
        });

    sortByName2.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByName("GAME2STATS");
            formatData(cur, 2);
          }
        });

    sortByName3.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByName("GAME3STATS");
            formatData(cur, 2);
          }
        });

    sortByScore1.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByStat1("GAME1STATS");
            formatData(cur, 1);
          }
        });

    sortByScore2.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByStat1("GAME2STATS");
            formatData(cur, 2);
          }
        });

    sortByScore3.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByStat1("GAME3STATS");
            formatData(cur, 3);
          }
        });

    sortByMoves1.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByStat3("GAME1STATS");
            formatData(cur, 1);
          }
        });

    sortByLevel1.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByStat3("GAME2STATS");
            formatData(cur, 2);
          }
        });

    sortByLevel2.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getDataByStat3("GAME3STATS");
            formatData(cur, 3);
          }
        });

    gameActivity.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent(LeaderboardActivity.this, GameActivity.class);
            startActivity(intent);
          }
        });
  }

  public void formatData(Cursor cur, int num) {
    if (cur.getCount() == 0) {
      System.out.println("Error no data found");
      showMessage("ERROR", "NOTHING FOUND IN DATABASE");
    } else {
      StringBuilder stringBuffer = new StringBuilder();
      while (cur.moveToNext()) {
        stringBuffer.append("id: ").append(cur.getString(0)).append("\n");
        stringBuffer.append("name: ").append(cur.getString(1)).append("\n");
        stringBuffer.append("score: ").append(cur.getString(2)).append("\n");
        stringBuffer.append("time: ").append(cur.getString(3)).append("\n");
        if (num == 1) {
          stringBuffer.append("Moves Left: ").append(cur.getString(4)).append("\n\n");
        } else {
          stringBuffer.append("Level Reached: ").append(cur.getString(4)).append("\n\n");
        }
      }
      showMessage("DATA FOUND", stringBuffer.toString());
    }
  }

  public void showMessage(String message, String data) {
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setCancelable(true);
    alert.setTitle(message);
    alert.setMessage(data);
    alert.show();
  }
}
