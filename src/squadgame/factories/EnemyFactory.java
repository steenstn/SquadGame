package squadgame.factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import squadgame.entities.Enemy;
import squadgame.main.BitmapResizer;
import squadgame.main.R;

public class EnemyFactory extends AbstractFactory {

	public EnemyFactory(Context context, int screenWidth, int screenHeight) {
		super(context, screenWidth, screenHeight);
		imageOptions.inScaled = false;
		
	}
	
	public Enemy createEnemy(float x, float y) {
		Enemy enemy = new Enemy(x,y);

		float wantedSize = Float.parseFloat(context.getResources().getString(R.dimen.enemy_image_width));
		
		Bitmap enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy, imageOptions);
		float scale = BitmapResizer.calculateScale(wantedSize, enemyImage.getWidth(), screenWidth);
		enemy.setScale(scale);
		enemyImage = BitmapResizer.getResizedBitmap(enemyImage, wantedSize, screenWidth, screenHeight);
		enemy.setImage(enemyImage);
		return enemy;
    	
	}
}
