package com.example.final_game.TrueBlueAdventure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

abstract class Pickup {
  /** pX: pickups's x coordinate pY: pickups's y coordinate */
  private int pX, pY;
  /** The gameview pickup is displayed in * */
  private TrueBlueView gv;
  /** a Btmap of pickup * */
  private Bitmap pickupBM;
  /** the rect representation for fuel * */
  private Rect pRect;
  /** status if pickup has been collected * */
  private boolean collected;
  /** Store the start pixel number */
  private int startPixel;
  /** Store the end pixel number */
  private int endPixel;

  Pickup(TrueBlueView gv, int pickupBM, int startPixel, int endPixel) {
    this.startPixel = startPixel;
    this.endPixel = endPixel;
    this.gv = gv;
    this.pickupBM = BitmapFactory.decodeResource(gv.getResources(), pickupBM);
    pX = this.startPixel;
    pY =
        gv.getScreenHeight() / 2
            - this.pickupBM.getHeight() / 2
            + getRandomNumberInRange(-300, 300);
    pRect = new Rect(pX, pY, pX + 70, pY + 70);
    collected = false;
  }

  /** Draw the Pickup Object on the Canvas */
  void drawPickup(Canvas canvas) {
    Rect tb = gv.tb.getTbRect();
    canvas.drawBitmap(pickupBM, null, pRect, null);
    if (getIntersectTb(pRect, tb)) {
      pickupAction();
      collected = true;
    }
  }

  /** Returns a random integer x such that (min <= x <= max) * */
  private static int getRandomNumberInRange(int min, int max) {

    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }

    Random r = new Random();
    return r.nextInt((max - min) + 1) + min;
  }

  /** move method for pickup. * */
  void move() {
    if (pX < (endPixel)) {
      pX = startPixel;
      pY = gv.getScreenHeight() / 2 - pickupBM.getHeight() / 2 + getRandomNumberInRange(-300, 300);
      collected = false;
    }
    pX -= 10;
    pY += getRandomNumberInRange(-70, 70);
    pRect = new Rect(pX, pY, pX + 70, pY + 70);
  }

  /** return true if TrueBlue intersects with the pickup and false otherwise * */
  private boolean getIntersectTb(Rect pRect, Rect tbRect) {
    return tbRect.intersect(pRect);
  }

  /** return whether this pick up was collected or not */
  boolean getCollected() {
    return !collected;
  }

  abstract void pickupAction();

  /** Return the View Object */
  TrueBlueView getGv() {
    return gv;
  }
}
