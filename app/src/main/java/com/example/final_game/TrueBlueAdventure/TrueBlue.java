package com.example.final_game.TrueBlueAdventure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.final_game.R;

class TrueBlue {
  /**
   * tbframe: the current frame of tb velocity: the speed at which true blue accelerates downward
   * gravity: how much true blue falls tbX: tb's x coordinate tbY: tb's y coordinate
   */
  private int tbFrame, velocity, gravity, tbX, tbY;
  /** The gameview tb is displayed in * */
  private TrueBlueView gv;
  /** an array of the frames of tb * */
  private Bitmap[] tb;
  /** Whether tb is playing the game* */
  private boolean state;
  /** the rec representation for tb * */
  private Rect tbRect;

  TrueBlue(TrueBlueView gv) {
    tbFrame = 0; // the current frame for true blue
    velocity = 0;
    gravity = 3;
    this.gv = gv;
    state = false;
    tb = new Bitmap[3];
    tb[0] = BitmapFactory.decodeResource(gv.getResources(), R.drawable.trueblue_frame_0);
    tb[1] = BitmapFactory.decodeResource(gv.getResources(), R.drawable.trueblue_frame_1);
    tb[2] = BitmapFactory.decodeResource(gv.getResources(), R.drawable.trueblue_frame_2);
    tbX = gv.getScreenWidth() / 2 - tb[0].getWidth() / 2;
    tbY = gv.getScreenHeight() / 2 - tb[0].getHeight() / 2;
    tbRect = new Rect(tbX, tbY, tbX + tb[0].getWidth(), tbY + tb[0].getHeight());
  }

  /** animates tb * */
  void animateTB() {
    if (tbFrame == 0) {
      tbFrame = 1;
    } else if (tbFrame == 1) {
      tbFrame = 2;
    } else {
      tbFrame = 0;
    }
  }

  /** draw's tb * */
  void drawTB(Canvas canvas) {
    canvas.drawBitmap(tb[tbFrame], null, tbRect, null);
  }

  /** draws tb's invisible rectangle* */
  void drawTBRect(Canvas canvas) {
    Paint p = new Paint();
    p.setColor(Color.TRANSPARENT);
    canvas.drawRect(tbRect, p);
  }

  /** When the player touches the screen make tb go higher * */
  void tbOnTouch(int action) {
    if (action == MotionEvent.ACTION_DOWN) { // if the Tap is detected on the screen
      velocity = -30; // increase true blue's upward velocity
      state = true;
    }
  }

  /** Gets the state of tb * */
  boolean getState() {
    return state;
  }

  /** toggles the state of tb * */
  void setState() {
    state = !state;
  }

  /** causes tb to fall towards the ground * */
  void tbFall() {
    if (tbY < gv.getScreenHeight() - tb[0].getHeight() || velocity < 0) {
      velocity += gravity;
      tbY += velocity;
      if (tbY < 0) {
        tbY = 0;
      }
      tbRect = new Rect(tbX, tbY, tbX + tb[0].getWidth(), tbY + tb[0].getHeight());
    }
    if (tbRect.bottom >= gv.getScreenHeight()) {
      gv.gameOver();
    }
  }
  /** get tb's rectangle's x coordinate * */
  int getTbX() {
    return tbX;
  }
  /** get tb's rectangle's y coordinate* */
  int getTbY() {
    return tbY;
  }
  /** get tb's rectangle * */
  Rect getTbRect() {
    return tbRect;
  }
}
