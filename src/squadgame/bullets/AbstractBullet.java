package squadgame.bullets;

import android.graphics.Paint;
import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;

public abstract class AbstractBullet implements IRenderable, IEntity {

	protected float x,y;
	protected float angle;
	protected boolean alive;
	protected int damage;
	protected Paint paint;
	
	public AbstractBullet(float x, float y, float angle, int damage)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.alive = true;
		this.damage = damage;
		this.paint = new Paint();
	}

	public boolean isActive() { return alive; }
}
