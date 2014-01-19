package squadgame.factories;

import android.content.Context;
import android.graphics.BitmapFactory;

public abstract class AbstractFactory {

	Context context;
	int screenWidth, screenHeight;
	BitmapFactory.Options imageOptions;
	
	public AbstractFactory(Context context, int screenWidth, int screenHeight) {
		this.context = context;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.imageOptions = new BitmapFactory.Options();
	}
}
