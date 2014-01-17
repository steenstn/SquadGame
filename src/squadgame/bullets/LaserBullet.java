package squadgame.bullets;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import squadgame.entities.Enemy;
import squadgame.entities.Soldier;
import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;

public class LaserBullet extends AbstractBullet {
	
	ArrayList<IEntity> hitTargets;
	
	public LaserBullet(float x, float y, float angle, int damage) {
		super(x, y, angle, damage);
		hitTargets = new ArrayList<IEntity>();
		paint.setAntiAlias(true);
		paint.setStrokeWidth(2.0f);
	}

	@Override
	public void updatePosition(Model model)	{
	}
	
	
	// Raytrace to check all collisions with the laser
	@Override
	public void checkCollisions(Model model)
	{
		Vec2 l = new Vec2((float) Math.cos(angle), (float) Math.sin(angle));
		Vec2 o = new Vec2(x ,y);
		
		// (l * (o-c))^2 - l^2((o-c)^2-r^2)
		// http://en.wikipedia.org/wiki/Line%E2%80%93sphere_intersection
		if(!isActive())
			return;
		
		for(Enemy enemy : model.enemies) {
			Vec2 c = new Vec2(enemy.getCenterX(), enemy.getCenterY());
			float r = (enemy.getWidth()/2) * (enemy.getWidth()/2);
			
			float underSquareRoot = checkLineIntersection(c, l, o, r);
			underSquareRoot = (float) Math.sqrt(underSquareRoot);
			if(underSquareRoot >= 0) {
				if(hitInFrontOfOrigin(c, l, o, underSquareRoot)) {
					enemy.takeDamage(damage);
				}
			}
		}
		
		for(Soldier soldier : model.soldiers) {
			Vec2 c = new Vec2(soldier.getCenterX(), soldier.getCenterY());
			float r = (soldier.getWidth()/2) * (soldier.getWidth()/2);
			
			float underSquareRoot = checkLineIntersection(c, l, o, r);
			underSquareRoot = (float) Math.sqrt(underSquareRoot);
			if(underSquareRoot >= 0) {
				if(hitInFrontOfOrigin(c, l, o, underSquareRoot)) {
					soldier.healthPaint.setColor(Color.RED);//;takeDamage(damage);
				}
				
			}
		}
		alive = false;
		
	}
	
	private float checkLineIntersection(Vec2 c, Vec2 l, Vec2 o, float r)	{
		float part1 = (l.dot(o.minus(c)));
		part1*=part1;
		
		float part2 =  ( (o.minus(c)).dot(o.minus(c)) - r);
		
		float res = part1 - part2;
		return res;
	}
	
	private boolean hitInFrontOfOrigin(Vec2 c, Vec2 l, Vec2 o, float distance) {
		float startPoint = -(l.dot(o.minus(c)));
		float res1 = (startPoint + distance);
		float res2 = (startPoint - distance);
		if( res1 > 1 ||
		   res2 > 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public void render(Canvas c) {
		paint.setARGB(255,255,255,255);
		//c.drawCircle(x+2, y+2, 4, paint);
		c.drawLine(x, y, x + (float)(1000.0f*Math.cos(angle)), y + (float)(1000.0f*Math.sin(angle)), paint);
	}
	
	private class Vec2 {
		public float x,y;
		
		public Vec2(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public Vec2 minus(Vec2 in) {
			return new Vec2(x - in.x, y - in.y);
		}
		public Vec2 multiply(float in) {
			x*=in;
			y*=in;
			return new Vec2(x,y);
		}
		public float dot(Vec2 in) {
			return in.x * x + in.y * y;
		}
	}

}
