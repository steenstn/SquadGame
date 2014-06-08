package squadgame.pickups;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class SpeedPickup
    extends AbstractPickup {

    private float powerUpSpeed = Soldier.originalMaxSpeed * 2;

    public SpeedPickup(float x, float y) {
        super(x, y);
        this.effectTime = 250;
        this.pickupTime = 550;
        this.scoreForPickup = 20;
        this.paint.setColor(Color.RED);
    }

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        if (pickedUp)
            return;
        c.drawBitmap(image, x + screenX, y + screenY, paint);
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
        soldier.setSpeed(Soldier.originalMaxSpeed);
    }

}
