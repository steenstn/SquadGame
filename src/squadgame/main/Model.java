package squadgame.main;

import java.util.ArrayList;

import squadgame.entities.Bullet;
import squadgame.entities.Enemy;
import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;
import squadgame.factories.PickupFactory;
import squadgame.interfaces.IRenderable;
import squadgame.pickups.AbstractPickup;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Model {

	public ArrayList<Soldier> soldiers;
	public ArrayList<SoldierPortrait> portraits;
	public ArrayList<Enemy> enemies;
	public ArrayList<IRenderable> renderables;
	public ArrayList<Bullet> bullets;
	public ArrayList<AbstractPickup> pickups;
	
	private PickupFactory pickupFactory = new PickupFactory();
	public int screenWidth;
	public int screenHeight;
	public int score = 0;
	public Model(Context ctx)
	{
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    screenWidth = size.x;
	    screenHeight = size.y;
	    
	    
	    soldiers = new ArrayList<Soldier>();
	    soldiers.add(new Soldier("Bengelohanna", 100,100,255,0,0));
	    soldiers.add(new Soldier("Pepper Jack", 100,screenHeight/2,0,0,255));
	    soldiers.add(new Soldier("J�rgen Etwas", screenWidth-200,100,0,255,0));
	    soldiers.add(new Soldier("Mustaffan", screenWidth-200,screenHeight/2,255,255,0));
	    
	    portraits = new ArrayList<SoldierPortrait>();
	    portraits.add(new SoldierPortrait(soldiers.get(0),50,screenHeight-200));
	    portraits.add(new SoldierPortrait(soldiers.get(1),250,screenHeight-200));
	    
	    portraits.add(new SoldierPortrait(soldiers.get(2),screenWidth-250-150-50,screenHeight-200));
	    portraits.add(new SoldierPortrait(soldiers.get(3),screenWidth-250,screenHeight-200));
	    
	    enemies = new ArrayList<Enemy>();
	    enemies.add(new Enemy(500,500));
	    
	    bullets = new ArrayList<Bullet>();
	    pickups = new ArrayList<AbstractPickup>();
	    addRenderables();
	}
	
	public void spawnEnemy()
	{
		float newX = 0, newY = 0;
		// Try and spawn away from the players
		for(int i = 0; i < 20; i++)
		{
			boolean positionIsOk = true;
			newX = (float)(Math.random()*screenWidth);
			newY = (float)(Math.random()*screenHeight);
			
			for(Soldier soldier : soldiers)
			{
				if(Functions.rectsOverlap(newX, newY, 1, 1,
						soldier.getX()-5*soldier.getWidth(), soldier.getY()-5*soldier.getWidth(), 5*soldier.getWidth(), 5*soldier.getWidth()))
				{
					positionIsOk = false;
				}
				
			}
			if(positionIsOk)
			{
				break;
			}
		}
		Enemy enemy = new Enemy(newX,newY);
    	enemies.add(enemy);
    	renderables.add(enemy);
	}
	
	public void createRandomPickup(float x, float y)
	{
		AbstractPickup pickup = pickupFactory.createRandomPickup(x, y);
		pickups.add(pickup);
		renderables.add(pickup);
	}
	private void addRenderables()
	{

	    renderables = new ArrayList<IRenderable>();
	    for(Soldier soldier : soldiers)
	    {
	    	renderables.add(soldier);
	    }
	    for(SoldierPortrait portrait : portraits)
	    {
	    	renderables.add(portrait);
	    }
	    for(Enemy enemy:enemies)
	    {
	    	renderables.add(enemy);
	    }
	}
}
