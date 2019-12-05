package com.example.final_game.TrueBlueAdventure;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;

import com.example.final_game.R;
import com.example.final_game.ui.login.MainActivity;

public class TrueBlueActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_trueblue);
    ImageButton highScore = findViewById(R.id.tb_high_score);

    highScore.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Cursor cur = MainActivity.getGameDb().getAllData("GAME3STATS");
            if (cur.getCount() == 0) {
              System.out.println("Error no data found");
              showMessage("ERROR", "NOTHING FOUND IN DATABASE");
            } else {
              StringBuilder stringBuffer = new StringBuilder();
              while (cur.moveToNext()) {
                stringBuffer.append("ID: ").append(cur.getString(0)).append("\n");
                stringBuffer.append("Name: ").append(cur.getString(1)).append("\n");
                stringBuffer.append("Score: ").append(cur.getString(2)).append("\n");
                stringBuffer.append("Time: ").append(cur.getString(3)).append("\n");
                stringBuffer.append("Level Reached: ").append(cur.getString(4)).append("\n\n");
              }
              showMessage("DATA FOUND", stringBuffer.toString());
            }
          }
        });
  }
  /** Start and switch to the Begin Activity */
  public void begin(View view) {
    Intent intent = new Intent(this, Begin.class);
    startActivity(intent);
    finish();
  }
  /** Show the Alertdialog box of the data extracted form the database. */
  public void showMessage(String message, String data) {
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setCancelable(true);
    alert.setTitle(message);
    alert.setMessage(data);
    alert.show();
  }
}
