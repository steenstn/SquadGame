package squadgame.factories;

import squadgame.entities.Soldier;
import squadgame.main.BitmapResizer;
import squadgame.main.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SoldierFactory extends AbstractFactory {

	public SoldierFactory(Context context, int screenWidth, int screenHeight) {
		super(context, screenWidth, screenHeight);
	}
	
	public Soldier createSoldier(String name, float x, float y, int r, int g, int b, int imageId) {
		Soldier soldier = new Soldier(name, x, y, r, g, b);

		float wantedSize = Float.parseFloat(context.getResources().getString(R.dimen.soldier_image_width));
		
		Bitmap enemyImage = BitmapFactory.decodeResource(context.getResources(), imageId, imageOptions);
		float scale = BitmapResizer.calculateScale(wantedSize, enemyImage.getWidth(), screenWidth);
		soldier.setScale(scale);
		enemyImage = BitmapResizer.getResizedBitmap(enemyImage, wantedSize, screenWidth, screenHeight);
		soldier.setImage(enemyImage);
		return soldier;
	}

}
