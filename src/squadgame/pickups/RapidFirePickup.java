package squadgame.pickups;

import squadgame.entities.Soldier;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RapidFirePickup extends AbstractPickup {

	
	public RapidFirePickup(float x, float y)
	{
		super(x,y);
		this.effectTime = 200;
		this.pickupTime = 500;
		this.scoreForPickup = 20;
		this.paint.setColor(Color.YELLOW);
	}
	
	
	@Override
	public void render(Canvas c) {
		if(pickedUp)
			return;
		c.drawCircle(x, y, width, paint);
	}
	@Override
	public void activatePickup(Soldier soldier) {
		soldier.getWeapon().setReloadTime(10);
	}

	@Override
	public void doEffect(Soldier soldier) {
		soldier.getWeapon().setReloadTime(10);
		super.doEffect(soldier);
	}
	@Override
	public void deactivatePickup(Soldier soldier) {
		super.deactivatePickup(soldier);
		soldier.getWeapon().resetReloadTime();
	}





}
