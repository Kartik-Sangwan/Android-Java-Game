package com.example.final_game.AntCrusher.AntPresenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;

import com.example.final_game.AntCrusher.AntModel.GameCreature;
import com.example.final_game.AntCrusher.AntView.AntOverActivity;
import com.example.final_game.AntCrusher.AntView.DonutView;
import com.example.final_game.R;

import java.util.Date;

/** AntPresenter is the mediator between the model creatures and the GameSurfaceView class
 DonutView. It is responsible for the logic layer separation between model and view.*/
public class AntPresenter {

  private AntManagerFactory antManagerFactory;
  private DonutView donutView;
  private boolean touch = false;
  private GameCreature removedAnt;
  private int score;
  private int level = 1;
  private int lives = 10;
  private int antGenerationSpeed = 10;

  public AntPresenter(AntManagerFactory antManagerFactory, DonutView donutView) {
    this.antManagerFactory = antManagerFactory;
    this.donutView = donutView;
  }

  /** Listens to user click(UI) and passes the information to the view and model so that
   both can be updated accordingly.*/
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      double buttonX = event.getX();
      double buttonY = event.getY();

      for (int i = 0; i < antManagerFactory.size(); i++) {
        GameCreature creature = antManagerFactory.getCreatures().get(i);
        if (creature.getX() < buttonX && buttonX < creature.getX() + creature.getWidth()
                && creature.getY() < buttonY && buttonY < creature.getY() + creature.getHeight()) {
          touch = true;
          donutView.play();
          removedAnt = creature;
          this.update();
          score += 10;
          donutView.setScore(score);
          return true;
        }
      }
      touch = false;
      return false;
    } else {
      touch = false;
      return false;
    }
  }
  /** Update contains all the concrete logic implementation of the features and statistics in the
   game. It passes any change in the state of the game to both model and view so both can update
   themselves.*/
  public void update() {
    Bitmap antBitmap1 = BitmapFactory.decodeResource(donutView.getResources(), R.drawable.ant);
    if (touch) {
      antManagerFactory.removeCreature(removedAnt);
      if (antManagerFactory.size() == 0) {
        level += 1;
        donutView.setLevel(level);
        antGenerationSpeed += 2;
        donutView.setSpeed(antGenerationSpeed);
        antManagerFactory.createCreature("Ant", antBitmap1, donutView, antGenerationSpeed);
      }
    } else {
      antManagerFactory.update();
    }

    if (lives == 0) {

      donutView.getGameThread().setRunning(false);
      Date finalDate = new Date();
      float currTime = (finalDate.getTime() - donutView.returnStartTime()) / 1000F;
      Intent newGameIntent = new Intent(donutView.getContext(), AntOverActivity.class);

      newGameIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      newGameIntent.putExtra("Score", score);
      newGameIntent.putExtra("Time", currTime);
      newGameIntent.putExtra("Level", level);

      donutView.startNewActivity(newGameIntent);
    }

    for (int i = 0; i < antManagerFactory.size(); i++) {
      GameCreature ant = antManagerFactory.getCreatures().get(i);
      if (ant.getY() < 500) {
        ant.setSpeedPos(ant.getSpeed() + 1, 3000);
        lives -= 1;
        System.out.println("LIVES  ARE "+ lives);
        donutView.decreaseLife();
      }
    }
  }
}
