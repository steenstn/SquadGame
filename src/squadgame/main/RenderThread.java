package squadgame.main;

import squadgame.entities.Bullet;
import squadgame.entities.Enemy;
import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

class RenderThread extends Thread {
	  private boolean run = false;

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
      			model.enemies.remove(i);
      			model.renderables.remove(enemy);
      			
      			if(enemy.getHealth()<=0)
      			{
      				model.kills++;
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
	    canvas.drawText("Kills: " + model.kills, 10, 50, textPaint);
	  }
	  
	}