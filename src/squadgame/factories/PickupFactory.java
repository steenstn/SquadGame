package squadgame.factories;

import squadgame.pickups.AbstractPickup;
import squadgame.pickups.HealthPickup;
import squadgame.pickups.RapidFirePickup;
import squadgame.pickups.SpeedPickup;

public class PickupFactory {

	
	public AbstractPickup createRandomPickup(float x, float y)
	{
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
		if(result > 0.6)
			return new SpeedPickup(x,y);
		else if(result>0.5)
			return new RapidFirePickup(x,y);
		else
			return new HealthPickup(x,y);
	}
}
