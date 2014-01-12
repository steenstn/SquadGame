package squadgame.pickups;

import squadgame.entities.Soldier;
import squadgame.main.Functions;
import squadgame.main.Model;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HealthPickup extends AbstractPickup {

	public HealthPickup(float x, float y)
	{
		super(x,y);
		this.effectTime = 1;
		this.pickupTime = 500;
		this.scoreForPickup = 10;
		this.paint.setColor(Color.CYAN);
	}
	
	
	@Override
	public void render(Canvas c) {
		c.drawCircle(x, y, width, paint);
	}
	@Override
	public void activatePickup(Soldier soldier) {
		
		soldier.setHealth(soldier.getHealth() + 15);
		
	}
	@Override
	public void deactivatePickup(Soldier soldier) {
		active = false;
	}


	@Override
	public void doEffect(Soldier soldier) {
		if(effectTime > 0 && pickedUp)
		{
			this.effectTime--;
		}
		else
		{
			deactivatePickup(soldier);
		}
	}



}
