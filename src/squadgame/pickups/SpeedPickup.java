package squadgame.pickups;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class SpeedPickup extends AbstractPickup {

	private float powerUpSpeed = Soldier.originalMaxSpeed * 2;
	public SpeedPickup(float x, float y)
	{
		super(x,y);
		this.effectTime = 250;
		this.pickupTime = 550;
		this.scoreForPickup = 20;
		this.paint.setColor(Color.RED);
	}
	@Override
	public void render(Canvas c) {
		if(pickedUp)
			return;
		c.drawCircle(x, y, width, paint);
	}

	@Override
	public void activatePickup(Soldier soldier) {
		soldier.setSpeed(powerUpSpeed);
	}

	@Override
	public void doEffect(Soldier soldier) {
		this.effectTime--;
		soldier.setSpeed(powerUpSpeed);
		if(effectTime < 1)
			deactivatePickup(soldier);
	}

	@Override
	public void deactivatePickup(Soldier soldier) {
		soldier.setSpeed(Soldier.originalMaxSpeed);
		
	}

}
