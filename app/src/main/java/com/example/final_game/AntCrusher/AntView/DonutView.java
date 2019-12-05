package com.example.final_game.AntCrusher.AntView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.final_game.AntCrusher.AntModel.Donut;
import com.example.final_game.AntCrusher.AntModel.GameThread;
import com.example.final_game.AntCrusher.AntPresenter.AntManagerFactory;
import com.example.final_game.AntCrusher.AntPresenter.AntPresenter;
import com.example.final_game.AntCrusher.playable;
import com.example.final_game.R;

import java.io.Serializable;
import java.util.Date;

/** DonutView class is the whole surface that is visible to the user while playing the game. */
@SuppressLint("ViewConstructor")
public class DonutView extends SurfaceView implements SurfaceHolder.Callback, Serializable {

  /** GameThread manages and time component and starts the game. */
  private GameThread gameThread;
  /** The surface level view of the game */
  Donut donutNew;
  /** The speed of the initial generation of ants */
  private int antGenerationSpeed = 10;
  /** The score paint to display the score. */
  private Paint scorePaint = new Paint();
  /** The score variable that keeps track of current score */
  private int score;

  private Paint livesPaint = new Paint();
  private int lives = 10;
  /** The background picture for our gameView. */
  private Bitmap backgroundPicture;

  /** Int value of background.*/
  private int background;

  private int level = 1;

  private playable activity;

  private AntManagerFactory antManagerFactory = new AntManagerFactory();

  private String creature;

  private long initialTime;

  private AntPresenter antPresenter;

  /**
   * Construct the thread.
   *
   * @param context is the environment of this game.
   */
  public DonutView(Context context, int background, playable activity, String creature) {
    super(context);
    this.activity = activity;
    this.antPresenter = new AntPresenter(antManagerFactory, this);
    this.setFocusable(true);
    this.getHolder().addCallback(this);
    score = 0;
    this.background = background;
    this.creature = creature;
  }

  /** Updates the canvas that is viewed on screen. */
  public void update() {
    antPresenter.update();
  }

  /** Decreases lives left by 1*/
  public void decreaseLife() {
    if (lives - 1 >= 0) {
      lives--;
    }
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    Bitmap scaledBackground = Bitmap.createScaledBitmap(backgroundPicture, 2000, 3000, false);
    canvas.drawBitmap(scaledBackground, 0, 0, null);
    scorePaint.setColor(-16776961);
    scorePaint.setTextSize(80);
    scorePaint.setUnderlineText(true);
    canvas.drawText("Score : " + score, 20, 60, scorePaint);
    livesPaint.setColor(-65536);
    livesPaint.setTextSize(70);
    canvas.drawText("Lives Left : " + lives, 500, 60, livesPaint);
    canvas.drawText("Level: " + level, 650, 2000, scorePaint);
    donutNew.draw(canvas);
    antManagerFactory.draw(canvas);
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    Bitmap antBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.ant);

    Bitmap donutBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.donut);
    donutNew = new Donut(donutBitmap1, this.getWidth() / 2 - donutBitmap1.getWidth() / 2, 10);
    backgroundPicture = BitmapFactory.decodeResource(getResources(), this.background);

    antManagerFactory.createCreature(creature, antBitmap1, this, antGenerationSpeed);

    gameThread = new GameThread(this.getHolder(), this);
    gameThread.setRunning(true);
    gameThread.start();

    Date startDate = new Date();
    if (level == 1) {
      initialTime = startDate.getTime();
    }

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    boolean retry = true;
    while (retry) {
      try {
        gameThread.setRunning(false);
        gameThread.join();
      } catch (Exception e) {
        e.printStackTrace();
      }
      retry = false;
    }
  }

  public long returnStartTime(){
    return initialTime;
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return antPresenter.onTouchEvent(event);
  }

  /** These are all the Setter and Getter that are required in order to communicate to the Presenter
   * and update the state of the view of the game.*/
  public void setScore(int score) {
    this.score = score;
  }

  public GameThread getGameThread() {
    return gameThread;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void play() {
    activity.play();
  }

  public void setSpeed(int speed) {
    antGenerationSpeed = speed;
  }

  public void startNewActivity(Intent intent) {
    this.getContext().startActivity(intent);
  }
}
