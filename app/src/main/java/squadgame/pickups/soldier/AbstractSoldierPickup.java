package squadgame.pickups.soldier;

import android.graphics.Bitmap;
import android.graphics.Paint;
import squadgame.entities.Soldier;
import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;
import squadgame.pickups.AbstractPickup;

public abstract class AbstractSoldierPickup extends AbstractPickup{


	// The time the effect is active
	private int effectTime;
	protected int width;
	protected float scale;
	
	public AbstractSoldierPickup(float x, float y, int pickupTime, int effectTime, int scoreForPickup) {
        super(x, y, pickupTime, scoreForPickup);
        this.effectTime = effectTime;
		this.width = 16;
	}

	
	public void setUpImage(Bitmap image) {
		
	}
	public void checkCollisions(Model model) {
		for(Soldier soldier : model.soldiers) {
			model.collisionChecks++;
			if(!pickedUp && Functions.rectsOverlap(getX(), getY(), width, width,
					soldier.getX(), soldier.getY(), soldier.getWidth(), soldier.getWidth())) {
					pickedUp = true;
					soldier.addAndActivatePickup(this);
					model.increaseScore(getScoreForPickup());
				}
		}
	}
	public int getWidth() { return width; }
	public int getEffectTime() { return effectTime; }
	public void setScale(float scale) {
		this.scale = scale;
		this.width *= scale;
	}

	public abstract void activatePickup(Soldier soldier);
	
	public void doEffect(Soldier soldier) {
		this.effectTime--;
		if(effectTime < 0) {
			deactivatePickup(soldier);
		}
	}
	
	public void deactivatePickup(Soldier soldier) {
		deactivate();
	}
	
}
