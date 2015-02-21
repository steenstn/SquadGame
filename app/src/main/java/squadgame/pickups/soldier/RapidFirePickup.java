package squadgame.pickups.soldier;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class RapidFirePickup
    extends AbstractSoldierPickup {

    int newReloadTime;

    public RapidFirePickup(float x, float y) {
        super(x, y, 500, 200, 2);
    }

    @Override
    public void activatePickup(Soldier soldier) {
        newReloadTime = soldier.getWeapon().getReloadTime() / 2;
        soldier.getWeapon().setReloadTime(newReloadTime);
    }

    @Override
    public void doEffect(Soldier soldier) {
        soldier.getWeapon().setReloadTime(newReloadTime);
        super.doEffect(soldier);
    }

    @Override
    public void deactivatePickup(Soldier soldier) {
        super.deactivatePickup(soldier);
        soldier.getWeapon().resetReloadTime();
    }

}
