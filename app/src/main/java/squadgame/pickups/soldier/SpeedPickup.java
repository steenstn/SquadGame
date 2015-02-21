package squadgame.pickups.soldier;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class SpeedPickup
    extends AbstractSoldierPickup {

    private float powerUpSpeed = Soldier.originalMaxSpeed * 1.5f;

    public SpeedPickup(float x, float y) {
        super(x, y, 550, 250, 2);
    }


    @Override
    public void activatePickup(Soldier soldier) {
        soldier.setSpeed(powerUpSpeed);
    }

    @Override
    public void doEffect(Soldier soldier) {
        soldier.setSpeed(powerUpSpeed);
        super.doEffect(soldier);
    }

    @Override
    public void deactivatePickup(Soldier soldier) {
        super.deactivatePickup(soldier);
        soldier.setSpeed(Soldier.originalGroundSpeed);
    }

}
