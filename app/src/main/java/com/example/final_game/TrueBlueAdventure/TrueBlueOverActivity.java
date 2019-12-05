package com.example.final_game.TrueBlueAdventure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_game.Infrastructure.GameActivity;
import com.example.final_game.R;
import com.example.final_game.ui.login.DataBaseHelper;
import com.example.final_game.ui.login.MainActivity;

public class TrueBlueOverActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trueblue_over);
    Button mainMenu = findViewById(R.id.menu3);
    Button rePlayGame = findViewById(R.id.play3_again);
    String showScore = getIntent().getExtras().get("Score").toString();
    String showTime = getIntent().getExtras().get("Time").toString();
    String showLevel = getIntent().getExtras().get("Level").toString();
    TextView score = findViewById(R.id.score3);
    TextView time = findViewById(R.id.time3);
    TextView level = findViewById(R.id.level3);
    rePlayGame.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent2 = new Intent(TrueBlueOverActivity.this, TrueBlueActivity.class);
            startActivity(intent2);
          }
        });

    mainMenu.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent3 = new Intent(TrueBlueOverActivity.this, GameActivity.class);
            startActivity(intent3);
          }
        });
    score.setText("Score: " + showScore);
    time.setText("Time taken: " + showTime + " s");
    level.setText("Levels Completed: " + showLevel);

    DataBaseHelper db = MainActivity.getGameDb();
    String username = DataBaseHelper.getUSERNAME();
    db.insertData(
        "GAME3STATS", username.substring(0, username.indexOf('@')), showScore, showTime, showLevel);
  }
}
