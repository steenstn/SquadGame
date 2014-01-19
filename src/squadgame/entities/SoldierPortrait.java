package squadgame.entities;

import squadgame.interfaces.IRenderable;
import squadgame.main.MainActivity;
import squadgame.main.Model;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SoldierPortrait implements IRenderable{

	private float x,y;
	private float width, height;
	private Soldier soldier;
	private boolean selected = false;
	
	public SoldierPortrait(Soldier soldier, float x, float y)
	{
		this.soldier = soldier;
		this.soldier.setPortrait(this);
		this.x = x;
		this.y = y;
		this.width = soldier.getWidth()*2;
		this.height = soldier.getWidth()*2;
	}
	
	public void setSelected(boolean value)
	{
		selected = value;
	}
	
	public void toggleSelected()
	{
		selected = !selected;
	}
	
	public boolean isSelected() { return selected; }
	public Soldier getSoldier() { return soldier; }
	public float getX() { return x; }
	public float getY() { return y; }
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
	@Override
	public void render(Canvas c)
	{
		Paint paint = new Paint();
		paint.setARGB(selected == true ? 255 : 125, soldier.red(), soldier.green(), soldier.blue());
	  	paint.setAntiAlias(true);

	  	Paint textPaint = new Paint();
	  	textPaint.setARGB(255, 255, 255, 255);
	  	textPaint.setAntiAlias(true);
	  	textPaint.setTextSize(Model.textScale);
	  	
	  	c.drawRect(x, y, x+width, y+height, paint);
	  	c.drawText(soldier.getName(), x, y+20, textPaint);
	  	c.drawText("HP: " + soldier.getHealth(), x, y+40, textPaint);
	  	
	  	if(MainActivity.printDebug) { // Print all picked up pickups and show their time
	  		Paint tempPaint = new Paint();
	  		tempPaint.setARGB(150,0,255,0);
		  	for(int i = 0; i < soldier.pickups.size(); i++) {
		  		c.drawRect(soldier.getX(), 20+soldier.getY()+20*i, 
		  				soldier.getX()+soldier.pickups.get(i).getEffectTime()/2, -20+soldier.getY()+20*i+20, tempPaint);
		  		
		  		c.drawText(soldier.pickups.get(i).getClass().getSimpleName(),
		  				soldier.getX(), 20+soldier.getY()+20*i, textPaint);
		  	}
	  	}
	  	c.drawText(soldier.getWeapon().getName(), x, y+height, textPaint);
	  	
	  	
	  	
	  	if(selected)
	  	{
		  	Paint selectionPaint = new Paint();
		  	selectionPaint.setARGB(255, 255, 255, 255);
		  	selectionPaint.setStyle(Paint.Style.STROKE);
		  	c.drawRect(soldier.getX(),soldier.getY(),
	  			soldier.getX()+soldier.getWidth(),soldier.getY()+soldier.getWidth(),selectionPaint);
	  	}
	}
}
