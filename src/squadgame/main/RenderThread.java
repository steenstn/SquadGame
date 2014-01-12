package squadgame.main;

import squadgame.entities.Bullet;
import squadgame.entities.Enemy;
import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;
import squadgame.pickups.AbstractPickup;
import squadgame.pickups.HealthPickup;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	        	updateSoldiers();
	        	updatePickups();
	        	updateEnemies();
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
      			if(chance>model.dropChance)
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
	  
	  private void updateBullets()
	  {
		  for(int i = 0; i < model.bullets.size(); i++)
      	{
      		Bullet bullet = model.bullets.get(i);
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
	  
	  public void waitOnResume()
	  {
		  while(userPaused)
		  {
			  
		  }
	  }
	  
	  private void doDraw(Canvas canvas) {
	    canvas.drawColor(Color.BLACK);
	    
	    for(int i = 0; i < model.renderables.size(); i++)
	    {
	    	model.renderables.get(i).render(canvas);
	    }
	    Paint textPaint = new Paint();
	    textPaint.setAntiAlias(true);
	    textPaint.setTextSize(30);
	    textPaint.setColor(Color.WHITE);
	    canvas.drawText("Score: " + model.score, 10, 50, textPaint);
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