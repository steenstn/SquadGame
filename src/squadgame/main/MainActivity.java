package squadgame.main;

import squadgame.entities.Enemy;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	public static boolean printDebug = false;
	MainView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        view = new MainView(this);
        setContentView(view);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		view.screenX = savedInstanceState.getFloat("screenX");
        view.screenY = savedInstanceState.getFloat("screenY");
        view.scale = savedInstanceState.getDouble("scale");
        view.offsetX = savedInstanceState.getFloat("offsetX");
        view.offsetY = savedInstanceState.getFloat("offsetY");
        
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		  super.onSaveInstanceState(savedInstanceState);

		  savedInstanceState.putFloat("screenX", view.screenX);
		  savedInstanceState.putFloat("screenY", view.screenY);
		  savedInstanceState.putDouble("scale", view.scale);
		  savedInstanceState.putFloat("offsetX", view.offsetX);
		  savedInstanceState.putFloat("offsetY", view.offsetY);
		  
	}
	@Override
	public void onPause()
	{
		super.onPause();
		view.thread.setRunning(false);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		if(view.thread!=null)
		{
			view.thread.setRunning(true);
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        switch (item.getItemId()) {
	        case R.id.menu_print_debug_info:
	        	MainActivity.printDebug = !MainActivity.printDebug;
	        	return true;
	        case R.id.menu_spawn_pickup:
	        	view.model.createRandomPickup((float)(Math.random()*view.screenWidth),(float)(Math.random()*view.screenHeight));
	        	view.model.createRandomPickup((float)(Math.random()*view.screenWidth),(float)(Math.random()*view.screenHeight));
	        	view.model.createRandomPickup((float)(Math.random()*view.screenWidth),(float)(Math.random()*view.screenHeight));
	        	view.model.createRandomPickup((float)(Math.random()*view.screenWidth),(float)(Math.random()*view.screenHeight));
	        	
	        	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
	}

}
