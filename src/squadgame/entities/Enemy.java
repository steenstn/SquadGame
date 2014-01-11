package squadgame.entities;

import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.Model;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy implements IRenderable, IEntity{

	private float x,y;
	private float angle;
	private float targetAngle;
	private int health;
	private int width;
	private boolean alive;
	
	public Enemy(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.angle = this.targetAngle = (float) (Math.random()*2*Math.PI);
		this.health = 100;
		this.width = 25;
		this.alive = true;
	}
	
	@Override
	public void updatePosition(Model model)
	{
		if(Math.abs(angle-targetAngle) < 0.1)
		{
			targetAngle = (float) (Math.random()*2*Math.PI);
		}
		
		if(angle < targetAngle)
			angle+=0.1;
		else
			angle-=0.1;
		
		x += 0.5*Math.cos(angle);
		y += 0.5*Math.sin(angle);
		
		if(x > model.screenWidth+width || x < -width || y > model.screenHeight+width || y < -width)
		{
			this.alive = false;
		}
		
	}
	
	public void takeDamage(int damage)
	{
		health -= damage;
		if(health <= 0)
			this.alive = false;
	}
	public float getX() { return x; }
	public float getY() { return y; }
	public int getWidth() { return width; }
	public int getHealth() { return health; }
	
	@Override
	public boolean isActive()
	{
		return alive;
	}

	@Override
	public void checkCollisions(Model model) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(Canvas c) {
		Paint paint = new Paint();
		paint.setARGB(255, 150, 150, 150);
		c.drawCircle(x+width/2, y+width/2, width, paint);
		
	}


}
