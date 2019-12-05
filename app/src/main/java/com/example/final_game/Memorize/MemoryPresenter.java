package com.example.final_game.Memorize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

import com.example.final_game.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MemoryPresenter {
  /** Array List of all cards displayed on the screen */
  private List<PlayingCard> cardArray;
  /** Array List of all card images displayed on the screen */
  private List<int[]> imageArray;
  /** Player of the Game */
  private MemorizePlayer player;
  /** First and Second selected indexes of the cardArray */
  private int firstSelect, secondSelect;
  /** Denotes which number of the card selected */
  private int firstCard, secondCard;
  /** Denotes which card is being selected (Whether its the first card selection or second) */
  private int cardNum = 1;
  /** The back image of all the cards */
  private int cardBackView;
  /** The String storing the theme of the level */
  private String theme;
  /** The Integer storing the level the player is on */
  private int level;
  /** The MemoryFactory Object which stores the instance of that object */
  private MemoryFactory factory;

  /**
   * The String which stores the reference to the Shared Preference object where all the stats are
   * stored.
   */
  static final String SHARED_PREFS = "sharedPrefs";
  /** The String which stores level 1 points in Shared Preferences. */
  static final String POINTS1 = "points1";
  /** The String which stores level 1 time in Shared Preferences. */
  static final String TIME1 = "time1";
  /** The String which stores level 2 points in Shared Preferences. */
  static final String POINTS2 = "points2";
  /** The String which stores level 2 time in Shared Preferences. */
  static final String TIME2 = "time2";
  /** The String which stores level 2 moves left in Shared Preferences. */
  static final String MOVES_LEFT2 = "movesLeft2";
  /** The String which stores level 2 cards left to match in Shared Preferences. */
  static final String CARDS_LEFT2 = "cardsLeft2";
  /** The String which stores level 3 points in Shared Preferences. */
  static final String POINTS3 = "points3";
  /** The String which stores level 3 time in Shared Preferences. */
  static final String TIME3 = "time3";
  /** The String which stores level 3 moves left in Shared Preferences. */
  static final String MOVES_LEFT3 = "movesLeft3";
  /** The String which stores level 3 cards left to match in Shared Preferences. */
  static final String CARDS_LEFT3 = "cardsLeft3";
  /** The Shared Preferences object. */
  private SharedPreferences sharedPreferences;
  /** The MemoryActivity object. */
  private MemoryActivity context;

  MemoryPresenter(MemoryActivity activity, String background, int levelNum) {
    context = activity;
    factory = new MemoryFactory(context);
    player = (MemorizePlayer) factory.getMemoryObjects("Player", 1);
    theme = background;
    level = levelNum;
    this.player.setLevel(level);
    if (level == 1) {
      player.setVisibility();
    }
    initializeCardArray();
    sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
  }

  /**
   * Initializes the Card Array instance variable and create and stores instances of Playing Card in
   * it.
   */
  private void initializeCardArray() {
    cardArray = new ArrayList<>();
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_11));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_12));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_13));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_14));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_21));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_22));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_23));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_24));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_31));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_32));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_33));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_34));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_41));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_42));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_43));
    cardArray.add((PlayingCard) factory.getMemoryObjects("Card", R.id.iv_44));
  }

  /** Initialize + shuffle an array of card placeholders corresponding to respective card images. */
  void initializeImages(int[] images, int backView) {
    cardBackView = backView;
    imageArray = new ArrayList<>();
    for (int i = 1; i <= 8; i++) {
      int[] arr = new int[] {100 + i, images[i - 1]};
      imageArray.add(arr);
    }
    for (int i = 1; i <= 8; i++) {
      int[] arr = new int[] {200 + i, images[i - 1]};
      imageArray.add(arr);
    }
    Collections.shuffle(imageArray);
  }

  List<PlayingCard> getCardArray() {
    return cardArray;
  }

  /** Show the cards to the player for a few seconds, then flip them over and make them playable. */
  void show() {
    for (PlayingCard playCard : cardArray) {
      playCard.set_enable(false);
    }
    for (PlayingCard playCard : cardArray) {
      int image = imageArray.get(cardArray.indexOf(playCard))[1];
      playCard.setImage(image);
    }
    Handler handler = new Handler();
    handler.postDelayed(
        new Runnable() {
          @Override
          public void run() {
            for (PlayingCard playCard : cardArray) {
              playCard.setImage(cardBackView);
            }
            for (PlayingCard playCard : cardArray) {
              playCard.set_enable(true);
            }
          }
        },
        2500);
  }

  /** Sets the Timer in the game based on the level the player is currently on */
  void setTimer() {
    if (level == 3) {
      player.getChronometer().setCountDown(true);
      long timeInMilli = 60 * 1000;
      player.getChronometer().setBase(SystemClock.elapsedRealtime() + timeInMilli);
      player.getChronometer().start();
      // check every one second whether the timer hits 0 or not.
      Handler handler = new Handler();
      handler.postDelayed(
          new Runnable() {
            @Override
            public void run() {
              checkLevel3End();
            }
          },
          1000);
    } else {
      player.getChronometer().setBase(SystemClock.elapsedRealtime());
      player.getChronometer().start();
    }
  }

  /**
   * Temporarily stores selected cards and makes them unresponsive until all selections have been
   * made so a comparison can be performed.
   */
  void setSelection(PlayingCard card) {
    // Set the image of card to the image view
    int image = imageArray.get(cardArray.indexOf(card))[1];
    card.setImage(image);
    // check the selection of two cards and store them temporarily
    if (cardNum == 1) {
      firstCard = imageArray.get(cardArray.indexOf(card))[0];
      if (firstCard > 200) {
        firstCard = firstCard - 100;
      }
      cardNum = 2;
      firstSelect = this.cardArray.indexOf(card);
      // Make this card unresponsive
      card.set_enable(false);

    } else {
      secondCard = imageArray.get(cardArray.indexOf(card))[0];
      if (secondCard > 200) {
        secondCard = secondCard - 100;
      }
      cardNum = 1;
      secondSelect = this.cardArray.indexOf(card);
      // Make all cards unresponsive
      for (PlayingCard playCard : cardArray) {
        playCard.set_enable(false);
      }
      // https://developer.android.com/reference/android/os/Handler.html &
      // https://stackoverflow.com/questions/15136199/when-to-use-handler-post-when-to-new-thread
      // After selecting the two cards delay the game by 400 milliseconds to check whether the two
      // cards match or not and proceed ahead.
      Handler handler = new Handler();
      handler.postDelayed(
          new Runnable() {
            @Override
            public void run() {
              compare();
            }
          },
          400);
    }
  }

  /**
   * Compare selected cards to see if they match and adjust player's points and the selected cards'
   * visibility in the layout accordingly.
   */
  private void compare() {
    // If card matches make them invisible
    if (firstCard == secondCard) {
      cardArray.get(firstSelect).setVisibility();
      cardArray.get(secondSelect).setVisibility();
      // Increase the points by 2 for correct match
      MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.cardmatchsound);
      mediaPlayer.start();
      player.increasePlayerPoints();
      player.setTextPoints();

    } else {
      // Decrease the points by 1 for incorrect match
      MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.cardfailsound);
      mediaPlayer.start();
      player.decreasePlayerPoints();
      player.setTextPoints();
      // Load back the front images again if
      for (PlayingCard card : cardArray) {
        card.setImage(cardBackView);
      }
    }
    player.decreasePlayerMoves();
    player.setTextMoves();
    // Make all cards responsive again
    for (PlayingCard card : cardArray) {
      card.set_enable(true);
    }
    if (level == 1) {
      endLevel1(checkVisibility());
    } else if (level == 2) {
      endLevel2(checkLevel2End());
    } else {
      checkLevel3End();
    }
  }

  /** Return whether or not all cards on screen are invisible. */
  private boolean checkVisibility() {
    for (PlayingCard item : cardArray) {
      if (item.getVisibility() != View.INVISIBLE) {
        return false;
      }
    }
    return true;
  }

  /** Check if end-game conditions have been met. */
  private boolean checkLevel2End() {
    return player.getMovesLeft() == 0 || checkVisibility();
  }

  /** Check if end-game conditions have been met. */
  private void checkLevel3End() {
    player
        .getChronometer()
        .setOnChronometerTickListener(
            new Chronometer.OnChronometerTickListener() {
              @Override
              public void onChronometerTick(Chronometer chronometer) {
                CharSequence elapsedMillis = chronometer.getText();
                if ((elapsedMillis).equals("00:00")) {
                  endLevel3(true);
                }
              }
            });
    endLevel3(checkLevel2End());
  }

  /**
   * End level 1 if all end-game conditions have been met, track player stats, and navigate to level
   * 2 screen.
   */
  private void endLevel1(boolean check) {
    int playerPoints = player.getPlayerPoints();
    if (check) {
      CharSequence elapsedMillis = player.getChronometer().getText();
      player.getChronometer().stop();
      Intent intent = new Intent(context, MemoryActivity.class);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt(POINTS1, playerPoints);
      editor.putString(TIME1, String.valueOf(elapsedMillis));
      editor.apply();
      if (theme.equals("L")) {
        intent.putExtra("Theme?", "Light");
      } else {
        intent.putExtra("Theme?", "Dark");
      }
      intent.putExtra("Level?", 2);
      context.startActivity(intent);
    }
  }
  /**
   * End level 2 if all end-game conditions have been met, track player stats, and navigate to level
   * 3 screen.
   */
  private void endLevel2(boolean check) {
    int playerMoves = player.getMovesLeft();
    int playerPoints = player.getPlayerPoints();
    boolean bool1 = checkVisibility();
    if (check) {
      CharSequence elapsedMillis = player.getChronometer().getText();
      player.getChronometer().stop();
      Intent intent = new Intent(context, MemoryActivity.class);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt(POINTS2, playerPoints);
      editor.putString(TIME2, String.valueOf(elapsedMillis));
      editor.putString(MOVES_LEFT2, String.valueOf(playerMoves));
      if (bool1) {
        editor.putString(CARDS_LEFT2, "NO");
      } else {
        editor.putString(CARDS_LEFT2, "YES");
      }
      editor.apply();
      if (theme.equals("L")) {
        intent.putExtra("Theme?", "Light");
      } else {
        intent.putExtra("Theme?", "Dark");
      }
      intent.putExtra("Level?", 3);
      context.startActivity(intent);
    }
  }

  /**
   * End game if all end-game conditions have been met, track player stats, and navigate to
   * game-over screen.
   */
  private void endLevel3(boolean check) {
    int playerMoves = player.getMovesLeft();
    int playerPoints = player.getPlayerPoints();
    boolean bool1 = checkVisibility();
    if (check) {
      CharSequence elapsedMillis = player.getChronometer().getText();
      player.getChronometer().stop();
      Intent intent = new Intent(context, MemoryOverActivity.class);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt(POINTS3, playerPoints);
      editor.putString(TIME3, String.valueOf(elapsedMillis));
      editor.putString(MOVES_LEFT3, String.valueOf(playerMoves));
      if (bool1) {
        editor.putString(CARDS_LEFT3, "NO");
      } else {
        editor.putString(CARDS_LEFT3, "YES");
      }
      editor.apply();
      context.startActivity(intent);
    }
  }
}
