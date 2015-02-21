package squadgame.factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import squadgame.main.BitmapResizer;
import squadgame.main.R;
import squadgame.pickups.soldier.AbstractSoldierPickup;
import squadgame.pickups.soldier.HealthPickup;
import squadgame.pickups.soldier.InvincibilityPickup;
import squadgame.pickups.soldier.RapidFirePickup;
import squadgame.pickups.soldier.SpeedPickup;

public class PickupFactory extends AbstractFactory {


	private boolean debug = false;
	private int whichPickup = 4;
	private int speedResource, healthResource, invincibilityResource, rapidFireResource;
	
	public PickupFactory(Context context, int screenWidth, int screenHeight) {
		super(context, screenWidth, screenHeight);
		healthResource = R.drawable.health_pickup;
		speedResource = R.drawable.speed_pickup;
		invincibilityResource = R.drawable.invincibility_pickup;
		
		rapidFireResource = R.drawable.rapid_fire_pickup;
	}
	
	public AbstractSoldierPickup createRandomPickup(float x, float y)
	{
		if(debug) {
			AbstractSoldierPickup pickup;
			switch(whichPickup) {
			case 1:
				pickup = new RapidFirePickup(x,y);
				setUpImage(pickup, rapidFireResource);
				return pickup;
			case 2:
				pickup = new SpeedPickup(x,y);
				setUpImage(pickup, speedResource);
				return pickup;
			case 3:
				pickup = new HealthPickup(x,y);
				setUpImage(pickup, healthResource);
				return pickup;
			case 4:
				pickup = new InvincibilityPickup(x,y);
				setUpImage(pickup, invincibilityResource);
				return pickup;
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
		AbstractSoldierPickup pickup;
		if(result > 0.9) {
			pickup = new InvincibilityPickup(x,y);
			setUpImage(pickup, invincibilityResource);
			return pickup;
		}
		else if(result > 0.8) {
			pickup = new SpeedPickup(x,y);
			setUpImage(pickup, speedResource);
			return pickup;
		}
		else if(result>0.5) {
			pickup = new RapidFirePickup(x,y);
			setUpImage(pickup, rapidFireResource);
			return pickup;
		}
		else {
			pickup = new HealthPickup(x,y);
			setUpImage(pickup, healthResource);
			return pickup;
		}
	}
	
	private void setUpImage(AbstractSoldierPickup pickup, int imageId) {

		float wantedSize = Float.parseFloat(context.getResources().getString(R.dimen.pickup_image_width));
		
		Bitmap pickupImage = BitmapFactory.decodeResource(context.getResources(), imageId, imageOptions);
		float scale = BitmapResizer.calculateScale(wantedSize, pickupImage.getWidth(), screenWidth);
		pickup.setScale(scale);
		pickupImage = BitmapResizer.getResizedBitmap(pickupImage, wantedSize, screenWidth, screenHeight);
		pickup.setImage(pickupImage);
		
	}
	
}
