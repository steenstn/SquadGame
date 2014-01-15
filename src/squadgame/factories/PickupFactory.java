package squadgame.factories;

import squadgame.pickups.AbstractPickup;
import squadgame.pickups.HealthPickup;
import squadgame.pickups.InvincibilityPickup;
import squadgame.pickups.RapidFirePickup;
import squadgame.pickups.SpeedPickup;

public class PickupFactory {

	private boolean debug = false;
	private int whichPickup = 4;
	
	public AbstractPickup createRandomPickup(float x, float y)
	{
		if(debug) {
			switch(whichPickup) {
			case 1:
				return new RapidFirePickup(x,y);
			case 2:
				return new SpeedPickup(x,y);
			case 3:
				return new HealthPickup(x,y);
			case 4:
				return new InvincibilityPickup(x,y);
			}
		}
		double result = Math.random();
		
		// The pickups that can be spawned, a higher number is lower chance
		/*if(result>0.9)
		 * spawnRare()
		 * else if(result>0.8)
		 * spawnLessRare()
		 * else
		 * spawnHealthOrWhatever
		 * 
		 *  
		 */
		if(result > 0.9)
			return new InvincibilityPickup(x,y);
		else if(result > 0.8)
			return new SpeedPickup(x,y);
		else if(result>0.5)
			return new RapidFirePickup(x,y);
		else
			return new HealthPickup(x,y);
	}
}
