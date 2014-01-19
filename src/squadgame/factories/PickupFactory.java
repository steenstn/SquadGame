package squadgame.factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import squadgame.main.BitmapResizer;
import squadgame.main.R;
import squadgame.pickups.AbstractPickup;
import squadgame.pickups.HealthPickup;
import squadgame.pickups.InvincibilityPickup;
import squadgame.pickups.RapidFirePickup;
import squadgame.pickups.SpeedPickup;

public class PickupFactory extends AbstractFactory {


	private boolean debug = false;
	private int whichPickup = 4;
	
	public PickupFactory(Context context, int screenWidth, int screenHeight) {
		super(context, screenWidth, screenHeight);
	}
	
	public AbstractPickup createRandomPickup(float x, float y)
	{
		if(debug) {
			AbstractPickup pickup;
			switch(whichPickup) {
			case 1:
				pickup = new RapidFirePickup(x,y);
				setUpImage(pickup, R.drawable.health_pickup);
				return pickup;
			case 2:
				pickup = new SpeedPickup(x,y);
				setUpImage(pickup, R.drawable.health_pickup);
				return pickup;
			case 3:
				pickup = new HealthPickup(x,y);
				setUpImage(pickup, R.drawable.health_pickup);
				return pickup;
			case 4:
				pickup = new InvincibilityPickup(x,y);
				setUpImage(pickup, R.drawable.health_pickup);
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
		AbstractPickup pickup;
		if(result > 0.9) {
			pickup = new InvincibilityPickup(x,y);
			setUpImage(pickup, R.drawable.health_pickup);
			return pickup;
		}
		else if(result > 0.8) {
			pickup = new SpeedPickup(x,y);
			setUpImage(pickup, R.drawable.health_pickup);
			return pickup;
		}
		else if(result>0.5) {
			pickup = new RapidFirePickup(x,y);
			setUpImage(pickup, R.drawable.health_pickup);
			return pickup;
		}
		else {
			pickup = new HealthPickup(x,y);
			setUpImage(pickup, R.drawable.health_pickup);
			return pickup;
		}
	}
	
	private void setUpImage(AbstractPickup pickup, int imageId) {

		float wantedSize = Float.parseFloat(context.getResources().getString(R.dimen.pickup_image_width));
		
		Bitmap pickupImage = BitmapFactory.decodeResource(context.getResources(), imageId, imageOptions);
		float scale = BitmapResizer.calculateScale(wantedSize, pickupImage.getWidth(), screenWidth);
		pickup.setScale(scale);
		pickupImage = BitmapResizer.getResizedBitmap(pickupImage, wantedSize, screenWidth, screenHeight);
		pickup.setImage(pickupImage);
		
	}
	
}
