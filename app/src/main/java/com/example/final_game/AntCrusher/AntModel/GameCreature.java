package com.example.final_game.AntCrusher.AntModel;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/** Super class for all creatures and items created. */
public abstract class GameCreature {

  Bitmap image;
  private final int width;
  private final int height;
  private int x;
  private int y;
  private int speed;

  GameCreature(Bitmap image, int x, int y) {
    this.image = image;
    this.x = x;
    this.y = y;
    this.width = image.getWidth();
    this.height = image.getHeight();
  }
  /** Getter for width of creature. */
  public int getWidth() {
    return width;
  }
  /** Getter for height of creature. */
  public int getHeight() {
    return height;
  }
  /** Setter for X coordinate of creature. */
  public void setX(int x) {
    this.x = x;
  }
  /** Setter for Y coordinate of creature. */
  public void setY(int y) {
    this.y = y;
  }
  /** Getter for X coordinate of creature. */
  public int getX() {
    return x;
  }

  /** Getter for Y coordinate of creature. */
  public int getY() {
    return y;
  }

  /** Setter for speed  of creature. */
  public void setSpeed(int speed) {
    this.speed = speed;
  }
  /** Getter for speed  of creature. */
  public int getSpeed() {
    return speed;
  }

  /** update must be implemented by all creatures that want to change their state.*/
  public abstract void update();

  /** Draw is a method the creatures use to display themselves on the gameSurface Canvas.*/
  public abstract void draw(Canvas canvas);

  /** Setter for the speed and position of the object that implements move behaviour.*/
  public abstract void setSpeedPos(int speed, int pos);
}
