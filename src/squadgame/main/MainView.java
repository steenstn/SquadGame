package squadgame.main;

import java.util.ArrayList;

import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class MainView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener, OnScaleGestureListener {

	private static final int INVALID_POINTER_ID = -1;
	// The ‘active pointer’ is the one currently moving our object.
	private int mActivePointerId = INVALID_POINTER_ID;

	int pointerIndex = INVALID_POINTER_ID;
	int pointerIndex2 = INVALID_POINTER_ID;
	boolean movingScreen;
	
	int screenWidth;
	int screenHeight;
	float screenX = -100, screenY = 0, offsetX = 0, offsetY = 0;
	float oldScreenX = screenX;
	float oldScreenY = screenY;
	float oldX;
	float oldY;
	float oldX2, oldY2;
	float zoomCenterX = 0, zoomCenterY = 0;
	ScaleGestureDetector scaleDetector = new ScaleGestureDetector(getContext(), this);
	private SurfaceHolder sh;
	private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	public RenderThread thread;
	Context ctx;

	double scale = 2;
	public Model model;
	
	public MainView(Context context) {
		super(context);
	    sh = getHolder();
	    sh.addCallback(this);
	    paint.setColor(Color.BLUE);
	    paint.setStyle(Style.FILL);
		ctx = context;
	    setFocusable(true); // make sure we get key events
	    setOnTouchListener(this);
	    WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
	    Display display = wm.getDefaultDisplay();
	    Point size = new Point();
	    display.getSize(size);
	    screenWidth = size.x;
	    screenHeight = size.y;
	    offsetX = screenWidth/2;
	    offsetY = -screenHeight/2;
	    zoomCenterX = screenWidth/2;
	    zoomCenterY = screenHeight/2;
	    
	    model = new Model(context);
	    
	}
		
	  public RenderThread getThread() {
		  return thread;
	  }
	  
	  @Override
	  public void surfaceCreated(SurfaceHolder holder) {
	    thread = new RenderThread(sh, ctx, new Handler(), model);
	    thread.setRunning(true);
	    thread.start();
	  }
	  @Override
	  public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) 	  {      
	    thread.setSurfaceSize(width, height);
	  }
	  @Override
	  public void surfaceDestroyed(SurfaceHolder holder) {
	    boolean retry = true;
	    thread.setRunning(false);
	    while (retry) {
	      try {
	        thread.join();
	        retry = false;
	      } catch (InterruptedException e) {
	      }
	    }
	  }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		scaleDetector.onTouchEvent(event);
		switch(event.getAction() & MotionEvent.ACTION_MASK)
		{
			case MotionEvent.ACTION_DOWN:
				oldX = (event.getX());
				oldY = (event.getY());
				mActivePointerId = event.getPointerId(0);
			break;
			
			case MotionEvent.ACTION_POINTER_DOWN:
				pointerIndex2 = event.getActionIndex();
				oldX2 = (event.getX(pointerIndex2));
				oldY2 = (event.getY(pointerIndex2));
			break;
			
			case MotionEvent.ACTION_MOVE:
				int numTouch = event.getPointerCount();
				if(numTouch == 1)
				{
					movingScreen = true;
					pointerIndex = event.findPointerIndex(mActivePointerId);
					
					float x = event.getX(pointerIndex);
					float y = event.getY(pointerIndex);
					
					float dx = x - oldX;
					float dy = y - oldY;
					
					screenX+= dx;
					screenY-= dy;
					oldX = x;
					oldY = y;
					
				}
			break;
			
			case MotionEvent.ACTION_UP:
				for(SoldierPortrait portrait : model.portraits)
				{
					if(Functions.rectsOverlap(event.getX(), event.getY(), 1, 1, 
						portrait.getX(), portrait.getY(), portrait.getWidth(), portrait.getHeight()))
					{
						portrait.toggleSelected();
						return true;
					}
				}
				for(Soldier soldier : model.soldiers)
				{
					// If a soldier is clicked on, select only that soldier
					if(Functions.rectsOverlap(event.getX(), event.getY(), 1, 1, 
							soldier.getX()-soldier.getWidth(), soldier.getY()-soldier.getWidth(), 3*soldier.getWidth(), 3*soldier.getWidth()))
						{
							for(SoldierPortrait portrait : model.portraits)
							{	
								portrait.setSelected(false);
							}
							soldier.getPortrait().toggleSelected();
							return true;
						}
				}
				ArrayList<Soldier> selectedSoldiers = new ArrayList<Soldier>();
				
				for(SoldierPortrait portrait : model.portraits)
				{
					if(portrait.isSelected())
					{
						selectedSoldiers.add(portrait.getSoldier());
					}
				}
				orderSelectedSoldiers(selectedSoldiers, event.getX(), event.getY());
				mActivePointerId = INVALID_POINTER_ID;
			break;
			
			case MotionEvent.ACTION_CANCEL: {
		        mActivePointerId = INVALID_POINTER_ID;
		        break;
		    }
			case MotionEvent.ACTION_POINTER_UP: {
				
		        // Extract the index of the pointer that left the touch sensor
		        pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
		                >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		        final int pointerId = event.getPointerId(pointerIndex);
		        if (pointerId == mActivePointerId) {
		            // This was our active pointer going up. Choose a new
		            // active pointer and adjust accordingly.
		            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
		            oldX = event.getX(newPointerIndex);
		            oldY = event.getY(newPointerIndex);
		            mActivePointerId = event.getPointerId(newPointerIndex);
		        }
		        pointerIndex2 = INVALID_POINTER_ID;
		        break;
		    }
		
		}

		return true;
	}
  	
	public void orderSelectedSoldiers(ArrayList<Soldier> soldiers, float x, float y)
	{
		if(soldiers.size()<=0)
			return;
		float offset = soldiers.get(0).getWidth()*3;
		switch(soldiers.size())
		{
		case 1:
			soldiers.get(0).setTarget(x, y);
			break;
		case 2:
			soldiers.get(0).setTarget(x-offset, y);
			soldiers.get(1).setTarget(x+offset, y);
			break;
		case 3:
			soldiers.get(0).setTarget(x-offset, y);
			soldiers.get(1).setTarget(x, y-offset);
			soldiers.get(2).setTarget(x+offset, y);
			break;
		case 4:
			soldiers.get(0).setTarget(x-offset, y);
			soldiers.get(1).setTarget(x, y-offset);
			soldiers.get(2).setTarget(x+offset, y);
			soldiers.get(3).setTarget(x, y+offset);
			break;	
		}
	}
	
	@Override
	public boolean onScale(ScaleGestureDetector detector) {

		if(detector.getCurrentSpan() - detector.getPreviousSpan() > 0)
			scale *= 1+(detector.getCurrentSpan() - detector.getPreviousSpan()) / 400.0f;
		else if(detector.getCurrentSpan() - detector.getPreviousSpan() < 0)
			scale /= 1+Math.abs(detector.getCurrentSpan() - detector.getPreviousSpan()) / 400.0f;
		
		screenX = (float) (oldScreenX * scale);
		screenY = (float) (oldScreenY * scale);
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		oldScreenY = (float) (screenY / scale);
		oldScreenX = (float) (screenX / scale);
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		
	}
  	
	
}
