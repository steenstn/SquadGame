package squadgame.pickups.soldier;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class HealthPickup
    extends AbstractSoldierPickup {

    public HealthPickup(float x, float y) {
        super(x, y, 500, 15, 1);
    }


    @Override
    public void activatePickup(Soldier soldier) {
    }

    @Override
    public void deactivatePickup(Soldier soldier) {
        super.deactivatePickup(soldier);
    }

    @Override
    public void doEffect(Soldier soldier) {
        soldier.setHealth(soldier.getHealth() + 1);
        super.doEffect(soldier);
    }

}
