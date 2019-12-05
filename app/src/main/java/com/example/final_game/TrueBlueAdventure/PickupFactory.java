package com.example.final_game.TrueBlueAdventure;

import android.graphics.Canvas;

public class PickupFactory {
    private Pickup pickup;
    private TrueBlueView gv;

    PickupFactory(String type, TrueBlueView view) {
        if (type.equals("powerup")) {
            this.gv = view;
            this.pickup = new PowerUp(view);
        }
        else if (type.equals("fuel")) {
            this.gv = view;
            this.pickup = new FuelUp(view);
        }
    }
    void drawPickup(Canvas canvas) {
        pickup.drawPickup(canvas);
    }

    void move() {
        pickup.move();
    }

    boolean getCollected() {
        return pickup.getCollected();
    }

}
