package com.example.final_game.AntCrusher.AntModel;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ant extends GameCreature implements AntMove {

  /** speed of the ant. */
  private int speed;

  /** Creates an Ant. */
  public Ant(Bitmap image, int x, int y, int speed) {
    super(image, x, y);
    this.speed = speed;
  }

  /** Draws the ant on the canvas. */
  public void draw(Canvas canvas) {
    canvas.drawBitmap(image, this.getX(), this.getY(), null);
  }

  /** Updates the position of the ant. */
  public void update() {
    int randomSpeed = (int) (Math.random() * speed * 2);
    this.setY(this.getY() - randomSpeed);
  }
  /** Returns the speed of the ant. */
  public int getSpeed() {
    return speed;
  }
  /** Sets the speed of the ant. */
  public void setSpeedPos(int speed, int y) {
    this.setSpeed(speed);
    this.setY(y);
  }
}
