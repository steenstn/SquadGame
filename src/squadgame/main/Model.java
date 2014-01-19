package squadgame.main;

import java.util.ArrayList;

import squadgame.bullets.AbstractBullet;
import squadgame.entities.Enemy;
import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;
import squadgame.factories.EnemyFactory;
import squadgame.factories.PickupFactory;
import squadgame.factories.SoldierFactory;
import squadgame.interfaces.IRenderable;
import squadgame.pickups.AbstractPickup;
import squadgame.weapons.LaserGun;
import squadgame.weapons.MultipleBulletsGun;
import squadgame.weapons.SingleBulletGun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Model {

	//Chance for drop of pickup, the random number must be bigger for a drop
	public static final float dropChance = 0.0f;
	public static float textScale = 10;
	
	public ArrayList<Soldier> soldiers;
	public ArrayList<SoldierPortrait> portraits;
	public ArrayList<Enemy> enemies;
	public ArrayList<IRenderable> renderables;
	public ArrayList<AbstractBullet> bullets;
	public ArrayList<AbstractPickup> pickups;
	public int collisionChecks = 0;
	private PickupFactory pickupFactory;
	private EnemyFactory enemyFactory;
	private SoldierFactory soldierFactory;
	
	public int screenWidth;
	public int screenHeight;
	public int score = 0;
	public Context context;
	public Bitmap background;
	private BitmapFactory.Options imageScale= new BitmapFactory.Options();
	
	public QuadTree quadTree;
	
	public Model(Context ctx)
	{
		this.context = ctx;
		WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    screenWidth = size.x;
	    screenHeight = size.y;
	    textScale = BitmapResizer.calculateScale(0.2f, 12, screenWidth);
	    enemyFactory = new EnemyFactory(ctx, screenWidth,screenHeight);
	    soldierFactory = new SoldierFactory(ctx, screenWidth,screenHeight);
	    pickupFactory = new PickupFactory(ctx, screenWidth,screenHeight);
	    
	    quadTree = new QuadTree(0, new Rectangle(-500, -500, screenWidth+500, screenHeight+500));
	    background = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
	    imageScale.inScaled = false;
	    
	    soldiers = new ArrayList<Soldier>();
	    Soldier soldier1 = soldierFactory.createSoldier("Bengelohanna", 100, 100, 255, 0, 0, R.drawable.soldier_red);//new Soldier("Bengelohanna", 100,100,255,0,0);
	    soldier1.setWeapon(new MultipleBulletsGun("Shotgun", soldier1, 200, 10, 80));
	    
	    Soldier soldier2 = soldierFactory.createSoldier("Pepper Jack", 100,screenHeight/2,0,0,255, R.drawable.soldier_blue);
	    soldier2.setWeapon(new SingleBulletGun("Machine gun", soldier2, 200, 10, 30));
	    
	    Soldier soldier3 = soldierFactory.createSoldier("Berik", screenWidth-200,100,0,255,0, R.drawable.soldier_green);
	    soldier3.setWeapon(new LaserGun("Laser", soldier3, 200, 40, 110));
	    
	    Soldier soldier4 = soldierFactory.createSoldier("Mustaffan", screenWidth-200,screenHeight/2,255,255,0, R.drawable.soldier_yellow);
	    soldiers.add(soldier1);
	    soldiers.add(soldier2);
	    soldiers.add(soldier3);
	    soldiers.add(soldier4);
	    
	    
	    portraits = new ArrayList<SoldierPortrait>();
	    int soldierWidth = soldiers.get(0).getWidth();
	    portraits.add(new SoldierPortrait(soldiers.get(0),0,screenHeight-2*soldierWidth));
	    portraits.add(new SoldierPortrait(soldiers.get(1),2.1f*soldierWidth,screenHeight-2*soldierWidth));
	    
	    portraits.add(new SoldierPortrait(soldiers.get(2),screenWidth-3.1f*soldierWidth-soldierWidth,screenHeight-2*soldierWidth));
	    portraits.add(new SoldierPortrait(soldiers.get(3),screenWidth-2*soldierWidth,screenHeight-2*soldierWidth));
	    
	    enemies = new ArrayList<Enemy>();
	    //enemies.add(new Enemy(500,500));
	    bullets = new ArrayList<AbstractBullet>();
	    pickups = new ArrayList<AbstractPickup>();
	    addRenderables();

	    spawnEnemy();
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
    	Enemy enemy = enemyFactory.createEnemy(newX, newY);
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

		// Set the images for all the soldiers
	    renderables = new ArrayList<IRenderable>();
	    //soldiers.get(0).setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_red));  
    	renderables.add(soldiers.get(0));
    	
    	//soldiers.get(1).setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_blue));  
    	renderables.add(soldiers.get(1));
    	
    	//soldiers.get(2).setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_green));  
    	renderables.add(soldiers.get(2));
    	
    	//soldiers.get(3).setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.soldier_yellow));  
    	renderables.add(soldiers.get(3));
	 	
	    
	    for(SoldierPortrait portrait : portraits)
	    {
	    	renderables.add(portrait);
	    }
	   /* for(Enemy enemy:enemies)
	    {
	    	enemy.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy));
	    	renderables.add(enemy);
	    }*/
	}
}
