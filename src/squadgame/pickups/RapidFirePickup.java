package squadgame.pickups;

import squadgame.entities.Soldier;
import squadgame.main.Functions;
import squadgame.main.Model;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RapidFirePickup extends AbstractPickup {

	
	public RapidFirePickup(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.effectTime = 200;
		this.pickupTime = 500;
		this.width = 15;
		this.active = true;
		this.pickedUp = false;
	}
	
	
	@Override
	public void render(Canvas c) {
		if(pickedUp)
			return;
		Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		c.drawCircle(x, y, width, paint);
	}
	@Override
	public void activatePickup(Soldier soldier) {
		soldier.setReloadCounter(10);
		
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
	@Override
	public void deactivatePickup(Soldier soldier) {
		soldier.setReloadCounter(Soldier.originalReloadSpeed);
		active = false;
	}





}
