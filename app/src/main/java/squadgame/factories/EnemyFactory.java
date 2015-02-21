package squadgame.factories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import squadgame.entities.enemies.Runner;
import squadgame.entities.enemies.Zombie;
import squadgame.main.BitmapResizer;
import squadgame.main.Functions;
import squadgame.main.R;

public class EnemyFactory extends AbstractFactory {

	public EnemyFactory(Context context, int screenWidth, int screenHeight) {
		super(context, screenWidth, screenHeight);
		imageOptions.inScaled = false;
		
	}
	
	public Zombie spawnZombie(float x, float y) {
		Zombie zombie = new Zombie(x,y);

		float wantedSize = Float.parseFloat(context.getResources().getString(R.dimen.enemy_image_width));
		
		Bitmap enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy, imageOptions);
		float scale = BitmapResizer.calculateScale(wantedSize, enemyImage.getWidth(), screenWidth);
		zombie.setScale(scale);
		enemyImage = BitmapResizer.getResizedBitmap(enemyImage, wantedSize, screenWidth, screenHeight);
		zombie.setImage(enemyImage);
		return zombie;
	}

    public Runner spawnRunner(float x, float y, float targetX, float targetY) {
        return new Runner(x, y, Functions.getAngleBetweenPoints(x, y, targetX, targetY));
    }
}
