package squadgame.pickups;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class HealthPickup
    extends AbstractPickup {

    public HealthPickup(float x, float y) {
        super(x, y);
        this.effectTime = 15;
        this.pickupTime = 500;
        this.scoreForPickup = 1;
        this.paint.setColor(Color.GREEN);
    }

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        if (pickedUp) {
            return;
        }
        c.drawBitmap(image, x + screenX, y + screenY, paint);
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
