package squadgame.main;

import java.util.Random;


public class Functions {

	public static float getDistanceSquared(float x1, float y1, float x2, float y2)
	{
		return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
	}
	public static float randomFromString(String inputString)
	{
		Random random = new Random(inputString.hashCode());
		return (float)(random.nextInt());
	}
	public static float clamp(float x, float bottom, float top)
	{
		if(x > top)
			return top;
		else if(x < bottom)
			return bottom;
		else
			return x;
	}
	
	public static boolean rectsOverlap(float x, float y, float width, float height, 
			float x2, float y2, float width2, float height2)
	{
		if (x < x2+width2 && x+width > x2 && y < y2+height2 && y+height > y2) 
			return true;
		else
			return false;
	}

    public static float getAngleBetweenPoints(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return (float) (Math.atan2(deltaY, deltaX));
    }
}
