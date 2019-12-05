package com.example.final_game.AntCrusher.AntModel;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.final_game.AntCrusher.AntView.DonutView;

/** GameThread class which manages threading, updating and calling draw. */
public class GameThread extends Thread {

  /** The canvas container. */
  private final SurfaceHolder surfaceHolder;
  /** Where the ants,donut and the game surface is drawn. */
  private DonutView gameView;
  /** Whether the thread is running. */
  private boolean running;

  /**
   * Construct the thread.
   *
   * @param surfaceHolder the canvas container.
   * @param gameView where the ants, donut, score etc are drawn.
   */
  public GameThread(SurfaceHolder surfaceHolder, DonutView gameView) {
    super();
    this.surfaceHolder = surfaceHolder;
    this.gameView = gameView;
  }

  /**
   * The run method of the thread.
   *
   * <p>While the thread is running it continuously update the gameView and draws the updated one.
   */
  @Override
  public void run() {
    while (running) {
      Canvas canvas = null;
      try {
        canvas = this.surfaceHolder.lockCanvas();
        synchronized (surfaceHolder) {
          this.gameView.update();
          this.gameView.draw(canvas);
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (canvas != null) {
          try {
            surfaceHolder.unlockCanvasAndPost(canvas);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public void setRunning(boolean running) {
    this.running = running;
  }
}
