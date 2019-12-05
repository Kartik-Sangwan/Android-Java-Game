package com.example.final_game.AntCrusher.AntPresenter;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.final_game.AntCrusher.AntModel.GameCreature;
import com.example.final_game.AntCrusher.AntView.DonutView;

import java.util.ArrayList;

/** Interface that must be implemented by any type of manager. It describes the basic functionality
 of a manager.*/
public interface AntGameInterface { // imlpemented by ant manager.
  void createAnts(Bitmap image, DonutView donut, int speed);

  void draw(Canvas canvas);

  void update();

  int size();

  void remove(GameCreature creature);

  ArrayList<GameCreature> getCreatures();
}
