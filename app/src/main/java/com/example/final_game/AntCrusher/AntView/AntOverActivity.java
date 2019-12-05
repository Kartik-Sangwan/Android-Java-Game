package com.example.final_game.AntCrusher.AntView;

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

/** Views the Game over Page, once game is completed. */
public class AntOverActivity extends AppCompatActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_ant_over);

    Button mainMenu = findViewById(R.id.all_games);

    String showScore = getIntent().getExtras().get("Score").toString();
    String showTime = getIntent().getExtras().get("Time").toString();
    String showLevel = getIntent().getExtras().get("Level").toString();

    Button rePlayGame = findViewById(R.id.play_again);

    TextView score = findViewById(R.id.score_id);
    TextView time = findViewById(R.id.time_id);
    TextView level = findViewById(R.id.level_id);

    rePlayGame.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent2 = new Intent(AntOverActivity.this, AntCrusherCustomize.class);
            System.out.println("reached play again");
            startActivity(intent2);
          }
        });

    mainMenu.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent3 = new Intent(AntOverActivity.this, GameActivity.class);
            startActivity(intent3);
          }
        });

    score.setText("Score: " + showScore);
    time.setText("Time taken: " + showTime + " s");
    level.setText("Level: " + showLevel);
    DataBaseHelper db = MainActivity.getGameDb();
    String user = db.getUSERNAME();
    db.insertData("GAME2STATS", user.substring(0, user.indexOf('@')), showScore, showTime, showLevel);
  }
}
