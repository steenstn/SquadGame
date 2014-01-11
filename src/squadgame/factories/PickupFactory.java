package squadgame.factories;

import squadgame.pickups.AbstractPickup;
import squadgame.pickups.HealthPickup;

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
		return new HealthPickup(x,y);
	}
}
