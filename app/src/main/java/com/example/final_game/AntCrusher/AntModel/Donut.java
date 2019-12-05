package com.example.final_game.AntCrusher.AntModel;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/** Creates the Donut. */
public class Donut extends GameCreature {

  public Donut(Bitmap image, int x, int y) {
    super(image, x, y);
  }
  /** Draws the donut on the canvas. */
  public void draw(Canvas canvas) {
    canvas.drawBitmap(image, this.getX(), this.getY(), null);
  }

  @Override
  public void setSpeedPos(int speed, int pos) {}

  @Override
  public void update() {}
}
