package squadgame.pickups;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class RapidFirePickup
    extends AbstractPickup {

    int newReloadTime;

    public RapidFirePickup(float x, float y) {
        super(x, y);
        this.effectTime = 200;
        this.pickupTime = 500;
        this.scoreForPickup = 2;
        this.paint.setColor(Color.YELLOW);
    }

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        if (pickedUp)
            return;
        c.drawBitmap(image, x + screenX, y + screenY, paint);
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
