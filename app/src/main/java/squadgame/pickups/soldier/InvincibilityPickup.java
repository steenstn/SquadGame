package squadgame.pickups.soldier;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class InvincibilityPickup
    extends AbstractSoldierPickup {

    int health;

    public InvincibilityPickup(float x, float y) {
        super(x, y, 500, 350, 2);
    }


    @Override
    public void activatePickup(Soldier soldier) {
        health = soldier.getHealth();
        soldier.healthPaint.setColor(Color.CYAN);
    }

    @Override
    public void deactivatePickup(Soldier soldier) {
        super.deactivatePickup(soldier);
        soldier.healthPaint.setColor(Color.GREEN);
    }

    @Override
    public void doEffect(Soldier soldier) {
        soldier.setHealth(health);
        super.doEffect(soldier);
    }

}
