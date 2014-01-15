package squadgame.entities;

import java.util.ArrayList;

import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;
import squadgame.pickups.AbstractPickup;
import squadgame.weapons.AbstractWeapon;
import squadgame.weapons.Pistol;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Soldier implements IRenderable, IEntity{

	public static final float originalMaxSpeed = 3;
	private float x, y;
	private float angle;
	private float speed;
	private int red,green,blue;
	private String name;
	private AbstractWeapon weapon;
	private int width = 64;
	private float targetX, targetY;
	private Enemy targetEnemy;
	private SoldierPortrait portrait;
	private float bulletAngle;
	private int health;
	private int maxHealth;
	private boolean alive;
	public ArrayList<AbstractPickup> pickups;
	private Bitmap image;
	private Matrix matrix;
	
	public Soldier(String name, float x, float y,int r, int g,int b)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		
		this.targetX = x;
		this.targetY = y;
		
		this.weapon = new Pistol("Pistol", this, 100, 20, 80);
		
		this.red = r;
		this.green = g;
		this.blue = b;
		this.health = this.maxHealth = 100;
		this.alive = true;
		
		this.speed = 3;
		
		this.matrix = new Matrix();
		
		pickups = new ArrayList<AbstractPickup>();
		
	}
	
	public String getName() { return name; }
	public SoldierPortrait getPortrait() { return portrait; }
	
	public float getX() { return x; }
	public float getY() { return y; }
	public int getWidth() { return width; }
	public AbstractWeapon getWeapon() { return weapon; }
	public int red() { return red; }
	public int green() { return green; }
	public int blue() { return blue; }
	
	public void setTarget(float x, float y)
	{
		targetX = x;
		targetY = y;
	}
	
	public void setImage(Bitmap image)
	{
		this.image = image;
	}
	
	public void setSpeed(float speed)
	{
		this.speed = speed;
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
	
	public void addAndActivatePickup(AbstractPickup pickup)
	{
		pickups.add(pickup);
		pickup.activatePickup(this);
	}
	
	@Override
	public void checkCollisions(Model model)
	{
		for(Enemy enemy : model.enemies)
		{
			if(Functions.rectsOverlap(x, y, width*0.8f, width*0.8f, enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getWidth()))
				takeDamage(1);
		}
	}
	
	public void usePickups()
	{
		if(pickups.size() <= 0)
			return;
		
		for(int i = 0; i < pickups.size(); i++)
		{
			if(pickups.get(i).isActive())
			{
				pickups.get(i).doEffect(this);
			}
			else
			{
				pickups.remove(i);
			}	
		}
	}
	
	public int getHealth()
	{
		return health;
	}
	public void setHealth(int health)
	{
		this.health = health;
		if(this.health>maxHealth)
		{
			this.health = maxHealth;
		}
	}
	
	public void takeDamage(int damage)
	{
		health -= damage;
		if(health <=0)
			alive = false;
	}
	
	public boolean isActive()
	{
		return alive;
	}
	
	@Override
	public void updatePosition(Model model)
	{
		float deltaX = targetX - (x+width/2);
		float deltaY = targetY - (y+width/2);
		if(Math.abs(deltaX) > 1 && Math.abs(deltaY) > 1)
		{
			angle = (float) (Math.atan2(deltaY, deltaX));
			
			x += speed * Math.cos(angle);
			y += speed * Math.sin(angle);
		}
		
	}
	
	public void shoot(Model model)
	{
		System.out.println(this.name  + " shoots!");
		weapon.reload();
		if(weapon.isReloaded() && weapon.hasAmmo()) {
			float deltaX = targetEnemy.getX()+targetEnemy.getWidth() - (x+width/2);
			float deltaY = targetEnemy.getY()+targetEnemy.getWidth() - (y+width/2);
			bulletAngle = (float) (Math.atan2(deltaY, deltaX));
			
			float bulletX = (float) (x+width/2 + 0.8*width*Math.cos(bulletAngle));
			float bulletY = (float) (y+width/2 + 0.8*width*Math.sin(bulletAngle));
			Bullet bullet = weapon.shoot(bulletX, bulletY, bulletAngle);

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

	  	c.drawCircle(targetX, targetY,5,paint);
	  	
	  	//matrix.setScale(0.5f,0.5f);

	  	matrix.setTranslate(x, y);
	  	matrix.postRotate((float) (bulletAngle * 180f/Math.PI)+90,x+width/2,y+width/2);
	  	

	  	//c.drawBitmap(image, x, y, paint);
	  	c.drawBitmap(image, matrix, paint);
	  	//c.drawRect(new Rect((int)x,(int)y,(int)x+ width,(int)y+width), paint);
	  	
	  	c.drawText(name, x-width/2, y-20, textPaint);
	  	
	  	Paint healthPaint = new Paint();
	  	healthPaint.setColor(Color.GREEN);
	  	c.drawRect(x, y+width+1, x+(((float)health/100.0f)*(1+width)), y+width+6, healthPaint);
	  	//c.drawText("HP: " + health, x, y, textPaint);
	  	//c.drawText("Pickups: " + pickups.size(), x, y+20, textPaint);
	  	
	  	
	}

}
