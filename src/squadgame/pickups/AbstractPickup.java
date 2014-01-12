package squadgame.pickups;

import android.graphics.Paint;
import squadgame.entities.Soldier;
import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;

public abstract class AbstractPickup implements IRenderable{

	// Time until the pickup disappears after it has been dropped
	protected int pickupTime;
	// The time the effect is active
	protected int effectTime;
	protected float x,y;
	protected int width;
	protected boolean active;
	protected boolean pickedUp; // If this pickup has been picked up
	protected int scoreForPickup = 0;
	protected Paint paint;
	public AbstractPickup(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.active = true;
		this.pickedUp = false;
		this.width = 16;
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
	}
	
	public void countDownTimer()
	{
		if(!pickedUp)
		{
			pickupTime--;
		}
		if(pickupTime < 1)
		{
			this.active = false;
		}
	}
	public void checkCollisions(Model model) {
		for(Soldier soldier : model.soldiers)
		{
			if(!pickedUp && Functions.rectsOverlap(x, y, width, width,
					soldier.getX(), soldier.getY(), soldier.getWidth(), soldier.getWidth()))
				{
					pickedUp = true;
					soldier.addPickup(this);
					model.score += scoreForPickup;
				}
		}
	}
	public float getX() { return x; }
	public float getY() { return y; }
	public int getWidth() { return width; }
	public boolean isActive() { return active; }
	public abstract void activatePickup(Soldier soldier);
	public abstract void doEffect(Soldier soldier);
	public abstract void deactivatePickup(Soldier soldier);
	
}
