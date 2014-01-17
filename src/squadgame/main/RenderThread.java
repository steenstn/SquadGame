package squadgame.main;

import squadgame.bullets.AbstractBullet;
import squadgame.bullets.StandardBullet;
import squadgame.entities.Enemy;
import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;
import squadgame.pickups.AbstractPickup;
import squadgame.pickups.HealthPickup;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceHolder;

class RenderThread extends Thread {
	  private boolean run = false;

	  public boolean userPaused = false;
	  private SurfaceHolder sh;
	  private Model model;
	  
	  public RenderThread(SurfaceHolder surfaceHolder, Context context,
	         Handler handler, Model model) {
	    sh = surfaceHolder;
	    this.model = model;
	  }
	  public void doStart() {
	    synchronized (sh) {
	    }
	  }
	  public void run() {
	    while (run) {
	      Canvas c = null;
	      try {
	        c = sh.lockCanvas(null);
	        synchronized (sh) {
	        	model.collisionChecks = 0;
	        	resetQuadTree();
	        	updateSoldiers();
	        	updatePickups();
	        	updateEnemies();
	        	//resetQuadTree();
	        	updateBullets();
	        	doDraw(c);
	        }
	      } finally {
	        if (c != null) {
	          sh.unlockCanvasAndPost(c);
	        }
	      }
	    }
	  }
	    
	  public void setRunning(boolean b) { 
	    run = b;
	  }
	  public void setSurfaceSize(int width, int height) {
	    synchronized (sh) {
	      doStart();
	    }
	  }
	  
	  private void resetQuadTree()
	  {
		  model.quadTree.clear();
		  for (Enemy enemy : model.enemies) {
			  if(enemy.isActive()) {
				  Rectangle enemyDimensions = new Rectangle((int)enemy.getX(), (int)enemy.getY(), enemy.getWidth(), enemy.getWidth());
				  model.quadTree.insert(enemyDimensions);
			  }
		  }
	  }
	  private void updateSoldiers()
	  {
		  for(int i = 0; i < model.soldiers.size(); i++)
		  {
      		Soldier soldier = model.soldiers.get(i);
      		if(soldier.isActive())
      		{
	        		if(model.enemies.size()>0)
	        		{
		        		soldier.targetClosestEnemy(model.enemies);
		        		soldier.shoot(model);
	        		}
	        		soldier.usePickups();
	    	    	soldier.updatePosition(model);
	    	    	soldier.checkCollisions(model);
      		}
      		else
      		{
      			model.soldiers.remove(i);
      			model.renderables.remove(soldier);
      			SoldierPortrait portrait = model.portraits.remove(i);
      			model.renderables.remove(portrait);
      		}
  	    }
	  }
	  
	  private void updatePickups()
	  {
		  for(int i = 0; i < model.pickups.size(); i++)
		  {
			  AbstractPickup pickup = model.pickups.get(i);
			  pickup.countDownTimer();
			  if(pickup.isActive())
			  {
				  pickup.checkCollisions(model);
			  }
			  else
			  {
				  model.pickups.remove(i);
				  model.renderables.remove(pickup);
			  }
			  
		  }
	  }
	  private void updateEnemies()
	  {
		  for(int i = 0; i < model.enemies.size(); i++)
      	{
      		Enemy enemy = model.enemies.get(i);
      		if(enemy.isActive())
      		{
      			enemy.updatePosition(model);
      		}
      		else
      		{
      			double chance = Math.random();
      			if(chance>Model.dropChance)
      				model.createRandomPickup(enemy.getX(), enemy.getY());
      			
      			model.enemies.remove(i);
      			model.renderables.remove(enemy);
      			
      			if(enemy.getHealth()<=0)
      			{
      				model.score += 100;
      			}
      			model.spawnEnemy();
      			if(Math.random()>0.6)
      				model.spawnEnemy();
      		}
      	}
	  }
	  
	  private void updateBullets() {
		  for(int i = 0; i < model.bullets.size(); i++)
      	  {
				AbstractBullet bullet = model.bullets.get(i);
	      		if(bullet.isActive())
	      		{
	        		bullet.updatePosition(model);
	        		bullet.checkCollisions(model);
	      		}
	      		else
	      		{
	      			model.bullets.remove(i);
	      			model.renderables.remove(bullet);
	      		}
      	}
	  }
	  
	  private void doDraw(Canvas canvas) {
	    //canvas.drawColor(Color.BLACK);
		  Paint paint = new Paint();
	    //canvas.drawBitmap(model.background, 0, 0, paint);
	    canvas.drawBitmap(model.background, null, new Rect(0,0,model.screenWidth,model.screenHeight), paint);
	    for(int i = 0; i < model.renderables.size(); i++)
	    {
	    	model.renderables.get(i).render(canvas);
	    }
	    Paint textPaint = new Paint();
	    textPaint.setAntiAlias(true);
	    textPaint.setTextSize(30);
	    textPaint.setColor(Color.WHITE);
	    canvas.drawText("Score: " + model.score, 10, 50, textPaint);
	    if(MainActivity.printDebug)
	    {
	    	canvas.drawText("Enemies: " + model.enemies.size(), 10, 80, textPaint);
		    canvas.drawText("Collision checks: " + model.collisionChecks, 10, 110, textPaint);
	    }
	    if(model.soldiers.size() == 0)
	    {
	    	Paint gameOverPaint = new Paint();
	    	gameOverPaint.setAntiAlias(true);
	    	gameOverPaint.setTextSize(200);
	    	gameOverPaint.setColor(Color.WHITE);
	    	canvas.drawText("GAME OVER", 10, model.screenHeight/2, gameOverPaint);
	    }
	  }
	  
	}