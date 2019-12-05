package com.example.final_game.TrueBlueAdventure;

import com.example.final_game.R;

class PowerUp extends Pickup {
  public PowerUp(TrueBlueView gv) {
    super(gv, R.drawable.powerup, 1500, -500);
  }

  @Override
  public void pickupAction() {
    getGv().increaseScore();
  }
}
