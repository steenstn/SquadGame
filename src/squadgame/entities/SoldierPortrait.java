package squadgame.entities;

import squadgame.interfaces.IRenderable;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SoldierPortrait implements IRenderable{

	private float x,y;
	private float width = 150, height = 150;
	private Soldier soldier;
	private boolean selected = false;
	
	public SoldierPortrait(Soldier soldier, float x, float y)
	{
		this.soldier = soldier;
		this.soldier.setPortrait(this);
		this.x = x;
		this.y = y;
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
	  	textPaint.setTextSize(20);
	  	c.drawText("reload: " + soldier.getReloadCounter(), x, y+20, textPaint);
	  	
	  	c.drawRect(x, y, x+width, y+height, paint);
	  	c.drawText(soldier.getName(), x, y+height, textPaint);
	  	
	  	if(selected)
	  	{
		  	Paint selectionPaint = new Paint();
		  	selectionPaint.setARGB(255, 255, 255, 255);
		  	selectionPaint.setStyle(Paint.Style.STROKE);
		  	c.drawRect(soldier.getX()-soldier.getWidth(),soldier.getY()-soldier.getWidth(),
	  			soldier.getX()+2*soldier.getWidth(),soldier.getY()+2*soldier.getWidth(),selectionPaint);
	  	}
	}
}
