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
		this.x = x;
		this.y = y;
		this.effectTime = 1;
		this.pickupTime = 500;
		this.width = 15;
		this.active = true;
		this.pickedUp = false;
	}
	
	
	@Override
	public void checkCollisions(Model model) {
		for(Soldier soldier : model.soldiers)
		{
			if(Functions.rectsOverlap(x, y, width, width,
					soldier.getX(), soldier.getY(), soldier.getWidth(), soldier.getWidth()))
				{
					soldier.addPickup(this);
					pickedUp = true;
				}
		}
	}
	
	@Override
	public void render(Canvas c) {
		Paint paint = new Paint();
		paint.setColor(Color.CYAN);
		c.drawCircle(x, y, width, paint);
	}
	@Override
	public void activatePickup(Soldier soldier) {
		if(effectTime > 0 && pickedUp)
		{
			soldier.setHealth(soldier.getHealth() + 15);
			this.effectTime--;
		}
		else
		{
			deactivatePickup(soldier);
		}
	}
	@Override
	public void deactivatePickup(Soldier soldier) {
		active = false;
	}



}
