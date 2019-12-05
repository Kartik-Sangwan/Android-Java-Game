package com.example.final_game.TrueBlueAdventure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.example.final_game.R;

import java.util.Date;
import java.util.Random;

public class TrueBlueView extends View {
  // This will be the custom view class for Game3
  /** TB class * */
  TrueBlue tb;
  /** Tower class * */
  Tower cn; // Tower class

  PickupFactory powerup; // PowerUp class
  PickupFactory fuelup; // FuelUp class
  Handler handler;
  Runnable runnable;
  final int delayNum = 30;
  /** Background img * */
  Bitmap background;

  private Random random;
  Display display;
  Point point;
  /** height and width of the screen* */
  int screenWidth, screenHeight; // Height and Width of device.

  Rect rect;
  /** The score paint to display the score. */
  private Paint scorePaint = new Paint();

  /** The fuel paint to display the fuel. */
  private Paint fuelPaint = new Paint();

  private int randomX;
  private int randomY;
  private Random rand = new Random();
  /** The score variable that keeps track of current score */
  private int score = 0;

  private int fuel = 100;
  private int fuelConsumption = 10;

  /** The level paint to display the level. */
  private Paint levelPaint = new Paint();

  private Paint levelUpPaint = new Paint();
  /** The level variable that keeps track of current level */
  private int level = 1;

  long start;

  /** Initializer for gameview* */
  public TrueBlueView(Context context) {
    super(context);
    handler = new Handler();
    runnable =
        new Runnable() {
          @Override
          public void run() {
            invalidate(); // This should call onDraw.
          }
        };
    background = BitmapFactory.decodeResource(getResources(), R.drawable.game3_background);
    display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
    point = new Point();
    display.getSize(point);
    screenWidth = point.x;
    screenHeight = point.y;
    randomX = rand.nextInt(500);
    randomY = rand.nextInt(1500);
    rect = new Rect(0, 0, screenWidth, screenHeight);
    cn = new Tower(this); // created CN Tower
    cn.moveTower(); // moves CN Tower
    tb = new TrueBlue(this); // creates TrueBlue
    powerup = new PickupFactory("powerup",this);
    fuelup = new PickupFactory("fuel",this);
    Date startDate = new Date();
    start = startDate.getTime();
  }

  /** When something is drawn in the gameview* */
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // This is where we will draw our view for Game3.
    canvas.drawBitmap(background, null, rect, null);
    // Creates the score
    scorePaint.setColor(Color.BLUE);
    scorePaint.setTextSize(80);
    canvas.drawText("Score : " + score, 20, 60, scorePaint);
    // Creates the fuel
    fuelPaint.setColor(Color.RED);
    fuelPaint.setTextSize(80);
    canvas.drawText("Fuel : " + fuel + "%", 20, 1700, fuelPaint);
    // Creates the level
    levelPaint.setColor(Color.MAGENTA);
    levelPaint.setTextSize(80);
    canvas.drawText("Level : " + level, 770, 60, levelPaint);
    if (score > 0 && score % 5 == 0) {
      levelUpPaint.setColor(Color.BLACK);
      levelUpPaint.setTextSize(80);
      canvas.drawText("LEVEL UP!", randomX, randomY, levelUpPaint);
    }
    // true blue falls
    if (tb.getState()) {
      tb.drawTBRect(canvas);
      // animate tb
      tb.animateTB();
      // cause tb to fall
      tb.tbFall();
      // draw the towers
      cn.drawTower(canvas); // Endless number of CN Tower is created.
    }
    // displays true blue in the center
    tb.drawTB(canvas);
    if (powerup.getCollected()) {
      powerup.drawPickup(canvas);
    }
    powerup.move();
    if (fuelup.getCollected()) {
      fuelup.drawPickup(canvas);
    }
    fuelup.move();
    handler.postDelayed(runnable, delayNum);
  }

  /** when you tap the screen * */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    tb.tbOnTouch(action);
    return true;
  }

  /** returns the screenwidth * */
  public int getScreenWidth() {
    return screenWidth;
  }

  /** returns screen height * */
  public int getScreenHeight() {
    return screenHeight;
  }

  /** what to do when the game ends * */
  public void gameOver() {
    tb.setState();
    Date finalDate = new Date();
    float currTime = (finalDate.getTime() - start) / 1000F;
    Intent intent = new Intent(getContext(), TrueBlueOverActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    intent.putExtra("Score", score);

    intent.putExtra("Time", currTime);

    intent.putExtra("Level", level);
    getContext().startActivity(intent);
  }

  /** increases the score * */
  public void increaseScore() {
    score++;
    randomX = rand.nextInt(500);
    randomY = rand.nextInt(1500);
  }

  public void increaseFuel() {
    if (fuel < 51) {
      fuel += 50;
    } else {
      fuel = 100;
    }
  }

  public void decreaseFuel() {
    fuel -= fuelConsumption;
  }

  public void increaseLevel() {
    level++;
    fuel += 20;
    fuelConsumption += 1;
  }

  public void checkFuel() {
    if (fuel <= 0) {
      gameOver();
    }
  }
}
