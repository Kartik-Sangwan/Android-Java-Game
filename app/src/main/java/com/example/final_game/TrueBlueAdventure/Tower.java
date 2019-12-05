package com.example.final_game.TrueBlueAdventure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.final_game.R;

import java.util.Random;

// A class to create CN Tower in game3.

class Tower {
  /** Images of the top and bottom tower * */
  private Bitmap topTower, bottomTower;
  /** Distance between top and bottom tower * */
  private int distance = 700;
  /** the maximum and minimum heights of the towers * */
  private int minTower, maxTower;
  /** the number of towers * */
  private int numOfTowers = 4;
  /** the level counter * */
  private int levelCounter = 0;
  /** distance between towers * */
  private int distBetweenTowers;
  /** all the x coordinates for the towers * */
  private int[] cnX = new int[numOfTowers];
  /** all the y coordinates for the towers * */
  private int[] cnY = new int[numOfTowers];
  /** the tower velocity * */
  private int towerVelocity = 10;

  private Random random;
  /** the game view tower is created in * */
  private TrueBlueView gv;
  /** The rectangle for the top tower * */
  private Rect topTowerRect;
  /** The rectangle for the bottom tower * */
  private Rect bottomTowerRect;

  /** Initializes the tower * */
  Tower(TrueBlueView gv) {
    this.gv = gv;
    topTower = BitmapFactory.decodeResource(gv.getResources(), R.drawable.cn_tower_down);
    bottomTower = BitmapFactory.decodeResource(gv.getResources(), R.drawable.cn_tower_up);
    topTowerRect =
        new Rect(
            cnX[0],
            cnY[0] - topTower.getHeight(),
            cnX[0] + topTower.getWidth(),
            cnY[0] - topTower.getHeight() + topTower.getHeight());
    bottomTowerRect =
        new Rect(
            cnX[0],
            cnY[0] + distance,
            cnX[0] + bottomTower.getWidth(),
            cnY[0] + distance + bottomTower.getHeight());
  }
  /** Moves numOfTowers and makes them move across the display* */
  void moveTower() {

    distBetweenTowers = gv.getScreenWidth() * 3 / 4;
    minTower = distance / 2;
    maxTower = gv.getScreenHeight() - minTower - distance;
    random = new Random();

    for (int i = 0; i < numOfTowers; i++) {
      cnX[i] = gv.getScreenWidth() + i * distBetweenTowers;
      cnY[i] = minTower + random.nextInt(maxTower - minTower + 1);
    }
  }
  /** Draws the tower in the gameview * */
  void drawTower(Canvas canvas) {
    // used the numOfTowers and creates endless number of CN towers occurring at random.

    for (int i = 0; i < numOfTowers; i++) {
      cnX[i] -= towerVelocity;
      if (cnX[i] < -topTower.getWidth()) {
        cnX[i] += numOfTowers * distBetweenTowers;
        cnY[i] = minTower + random.nextInt(maxTower - minTower + 1);
      }
      Paint p = new Paint();
      p.setARGB(128, 255, 255, 255);
      Paint s = new Paint();
      s.setColor(Color.TRANSPARENT);
      topTowerRect =
          new Rect(cnX[i], cnY[i] - topTower.getHeight(), cnX[i] + topTower.getWidth(), cnY[i]);
      bottomTowerRect =
          new Rect(
              cnX[i],
              cnY[i] + distance,
              cnX[i] + bottomTower.getWidth(),
              cnY[i] + distance + bottomTower.getHeight());
      Rect score =
          new Rect(
              cnX[i] + topTower.getWidth(),
              cnY[i],
              cnX[i] + topTower.getWidth() + 1,
              cnY[i] + distance);
      canvas.drawBitmap(topTower, null, topTowerRect, null);
      canvas.drawBitmap(bottomTower, null, bottomTowerRect, null);
      canvas.drawRect(topTowerRect, p);
      canvas.drawRect(bottomTowerRect, p);
      canvas.drawRect(score, s);
      Rect tb = gv.tb.getTbRect();
      if (getIntersectTb(bottomTowerRect, tb) || getIntersectTb(topTowerRect, tb)) {
        gv.gameOver();
      }
      if (inBetweenTowers()) {
        gv.increaseScore();
        gv.decreaseFuel();
        gv.checkFuel();
        levelCounter++;
        if (levelCounter % 5 == 0) {
          towerVelocity += 5;
          gv.increaseLevel();
        }
      }
    }
  }

  /** Check whether TrueBlue passes between the two towers */
  private boolean inBetweenTowers() {
    if (topTowerRect.right - gv.tb.getTbX() >= 0
        && topTowerRect.right - gv.tb.getTbX() < towerVelocity) {
      return topTowerRect.bottom < gv.tb.getTbY() && bottomTowerRect.top > gv.tb.getTbY();
    }
    return false;
  }
  /** Finds out if a towerrect and tbrect intersect* */
  private boolean getIntersectTb(Rect TowerRect, Rect tbRect) {
    return tbRect.intersect(TowerRect);
  }
}
