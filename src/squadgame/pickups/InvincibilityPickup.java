package squadgame.pickups;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;

public class InvincibilityPickup extends AbstractPickup {

	int health;
	public InvincibilityPickup(float x, float y)
	{
		super(x,y);
		this.effectTime = 350;
		this.pickupTime = 500;
		this.scoreForPickup = 10;
		this.paint.setColor(Color.CYAN);
	}
	
	
	@Override
	public void render(Canvas c) {
		if(pickedUp)
			return;
		c.drawCircle(x, y, width, paint);
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
