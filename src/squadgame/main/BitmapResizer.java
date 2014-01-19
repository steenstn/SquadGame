package squadgame.main;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapResizer {

	/**
	 * Takes an image and resizes it to be the wanted width and height
	 * @param image - The image to resize
	 * @param wantedWidth - Wanted width(% of screenwidth)
	 * @param screenWidth - Width of screen
	 * @param screenHeight - height of screen
	 * @return - The resized image
	 */
	public static Bitmap getResizedBitmap(Bitmap image, float wantedWidth, int screenWidth, int screenHeight) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    
	    // Calculate how to scale the image
	    float scale = calculateScale(wantedWidth, width, screenWidth);

	    Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);
	
	    Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	public static float calculateScale(float wantedWidth, int imageWidth, int screenWidth) {
		return (wantedWidth * (float) screenWidth) / (float)imageWidth;
	}


}
