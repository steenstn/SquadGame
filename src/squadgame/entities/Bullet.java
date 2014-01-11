package squadgame.entities;

import android.graphics.Canvas;
import android.graphics.Paint;
import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;

public class Bullet implements IRenderable, IEntity{
	
	float x,y;
	float angle;
	boolean alive;
	private int damage = 20;
	public Bullet(float x, float y, float angle)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.alive = true;
	}
	
	@Override
	public void updatePosition(Model model)
	{
		x += 6 * Math.cos(angle);
		y += 6 * Math.sin(angle);

		if(x > model.screenWidth || x < 0 || y > model.screenHeight || y < 0)
		{
			this.alive = false;
		}
	}
	
	@Override
	public void checkCollisions(Model model)
	{
		for(Enemy enemy : model.enemies)
		{
			if(alive && Functions.rectsOverlap(x, y, 4, 4, 
					enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getWidth()))
			{
				this.alive = false;
				enemy.takeDamage(damage);
			}
		}
		for(Soldier soldier : model.soldiers)
		{
			if(alive && Functions.rectsOverlap(x, y, 4, 4, 
					soldier.getX(), soldier.getY(), soldier.getWidth(), soldier.getWidth()))
			{
				this.alive = false;
				soldier.takeDamage(damage);
			}
		}
		
	}
	
	public boolean isActive() { return alive; }
	
	@Override
	public void render(Canvas c) {
		Paint paint = new Paint();
		paint.setARGB(255,255,255,255);
		c.drawCircle(x+2, y+2, 4, paint);
	}

}
