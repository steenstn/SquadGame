package squadgame.pickups;

import squadgame.entities.Soldier;
import squadgame.interfaces.IRenderable;
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
	
	public void countDownTimer()
	{
		pickupTime--;
		if(pickupTime < 1)
		{
			this.active = false;
		}
	}
	public abstract void checkCollisions(Model model);
	public float getX() { return x; }
	public float getY() { return y; }
	public int getWidth() { return width; }
	public boolean isActive() { return active; }
	public abstract void activatePickup(Soldier soldier);
	public abstract void deactivatePickup(Soldier soldier);
	
}
