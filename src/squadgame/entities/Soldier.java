package squadgame.entities;

import java.util.ArrayList;

import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Soldier implements IRenderable{

	private float x, y;

	private float angle;
	private float speed;
	private int red,green,blue;
	private String name;
	private int width = 30;
	private float targetX, targetY;
	private int reloadCounter;
	private Enemy targetEnemy;
	private SoldierPortrait portrait;
	private float bulletAngle;
	private int health;
	private boolean alive;
	
	
	public Soldier(String name, float x, float y,int r, int g,int b)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		
		this.targetX = x;
		this.targetY = y;
		
		this.red = r;
		this.green = g;
		this.blue = b;
		this.health = 100;
		this.alive = true;
		
		this.speed = 3;
		this.reloadCounter = 10;
	}
	
	public String getName() { return name; }
	public SoldierPortrait getPortrait() { return portrait; }
	public float getX() { return x; }
	public float getY() { return y; }
	public int getWidth() { return width; }
	public int red() { return red; }
	public int green() { return green; }
	public int blue() { return blue; }
	
	public void setTarget(float x, float y)
	{
		targetX = x;
		targetY = y;
	}
	public void setPortrait(SoldierPortrait portrait)
	{
		this.portrait = portrait;
	}
	
	public void targetClosestEnemy(ArrayList<Enemy> enemies)
	{
		if(enemies.size() == 0)
			return;
		
		float shortestDistance = Functions.getDistanceSquared(x, y, 
				enemies.get(0).getX(), enemies.get(0).getY());
		int indexOfClosestEnemy = 0;
		
		for(int i = 1; i < enemies.size(); i++)
		{
			float distance = Functions.getDistanceSquared(x, y, 
					enemies.get(i).getX(), enemies.get(i).getY());
			
			if(distance < shortestDistance)
			{
				shortestDistance = distance;
				indexOfClosestEnemy = i;
			}
		}
		targetEnemy = enemies.get(indexOfClosestEnemy);
	}
	
	public void checkCollisions(Model model)
	{
		for(Enemy enemy : model.enemies)
		{
			if(Functions.rectsOverlap(x, y, width*0.8f, width*0.8f, enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getWidth()))
				takeDamage(1);
		}
	}
	public void takeDamage(int damage)
	{
		health -= damage;
		if(health <=0)
			alive = false;
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	public void updatePosition()
	{
		
		float deltaX = targetX - x;
		float deltaY = targetY - y;
		if(Math.abs(deltaX) > 1 && Math.abs(deltaY) > 1)
		{
			angle = (float) (Math.atan2(deltaY, deltaX));
			
			x += speed * Math.cos(angle);
			y += speed * Math.sin(angle);
		}
		
	}
	
	public void shoot(Model model)
	{
		reloadCounter--;
		float deltaX = targetEnemy.getX() - x;
		float deltaY = targetEnemy.getY() - y;
		bulletAngle = (float) (Math.atan2(deltaY, deltaX));
		if(reloadCounter <= 0)
		{
			reloadCounter = 100;
			
				
			float bulletX = (float) (x + 1.5*width*Math.cos(bulletAngle));
			float bulletY = (float) (y + 1.5*width*Math.sin(bulletAngle));
			Bullet bullet = new Bullet(bulletX,bulletY,bulletAngle);
			model.bullets.add(bullet);
			model.renderables.add(bullet);
		}
	}
	
	@Override
	public void render(Canvas c)
	{
		Paint paint = new Paint();
		paint.setARGB(255, red,green,blue);
	  	paint.setAntiAlias(true);
	  	
	  	Paint textPaint = new Paint();
	  	textPaint.setARGB(255, 255, 255, 255);
	  	textPaint.setAntiAlias(true);
	  	textPaint.setTextSize(20);
	  	c.drawCircle(x+width/2, y+width/2, width, paint);
	  	c.drawLine(x+width/2, y+width/2,
	  			(float)(x+width/2+2*width*Math.cos(bulletAngle)),  (float)(y+width/2+2*width*Math.sin(bulletAngle)), textPaint);
	  	c.drawText(name, x, y-20, textPaint);
	  	c.drawText("HP: " + health, x, y, textPaint);
	  	c.drawCircle(targetX, targetY,5,paint);
  	
	}

}
