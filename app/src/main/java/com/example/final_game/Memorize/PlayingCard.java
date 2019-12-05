package com.example.final_game.Memorize;

// https://developer.android.com/reference/android/widget/ImageView and
// https://developer.android.com/reference/android/view/View used to learn.
import android.view.View;
import android.widget.ImageView;

/** Playing card that needs to be matched with the other card. */
class PlayingCard implements MemoryInterface {
  /** The Image of the PlayingCard view on the screen */
  private ImageView imageview;

  PlayingCard(ImageView iv) {
    this.imageview = iv;
  }
  /** get the ImageView of the card */
  ImageView getImageView() {
    return this.imageview;
  }

  /** Set the front view of the card to the image */
  void setImage(int image) {
    this.imageview.setImageResource(image);
  }

  /** Set whether the card will be visible on screen or not */
  public void setVisibility() {
    this.imageview.setVisibility(View.INVISIBLE);
  }

  /** get the visibility of the playing card */
  int getVisibility() {
    return this.imageview.getVisibility();
  }

  /** Set whether the view of the card; front or back view. */
  void set_enable(boolean bool) {
    this.imageview.setEnabled(bool);
  }
}
