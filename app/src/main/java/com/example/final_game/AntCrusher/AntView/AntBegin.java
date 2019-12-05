package com.example.final_game.AntCrusher.AntView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_game.R;

/** Displays a play button for the user to begin the game. */
public class AntBegin extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ant_begin);
    ImageButton ant = findViewById(R.id.imageButton3);

    ant.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent2 = new Intent(AntBegin.this, AntActivity.class);
            startActivity(intent2);
          }
        });
  }
}
