package squadgame.bullets;

import android.graphics.Canvas;
import android.graphics.Paint;
import squadgame.entities.Enemy;
import squadgame.entities.Soldier;
import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;

public class StandardBullet extends AbstractBullet {
	
	
	public StandardBullet(float x, float y, float angle, int damage) {
		super(x, y, angle, damage);
	}

	@Override
	public void updatePosition(Model model)
	{
		x += 7 * Math.cos(angle);
		y += 7 * Math.sin(angle);

		if(x > model.screenWidth || x < 0 || y > model.screenHeight || y < 0)
		{
			this.alive = false;
		}
	}
	
	@Override
	public void checkCollisions(Model model)
	{
		for(Enemy enemy : model.enemies) {
			if(alive && Functions.rectsOverlap(x, y, 4, 4, 
					enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getWidth())) {
				this.alive = false;
				enemy.takeDamage(damage);
			}
		}
		for(Soldier soldier : model.soldiers) {
			if(alive && Functions.rectsOverlap(x, y, 4, 4, 
			   soldier.getX(), soldier.getY(), soldier.getWidth(), soldier.getWidth())) {
				this.alive = false;
				soldier.takeDamage(damage);
			}
		}
		
	}
	
	@Override
	public void render(Canvas c) {
		paint.setARGB(255,255,255,255);
		c.drawCircle(x+2, y+2, 4, paint);
	}

}
