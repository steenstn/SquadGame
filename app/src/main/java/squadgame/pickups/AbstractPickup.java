package squadgame.pickups;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import squadgame.interfaces.IRenderable;

public abstract class AbstractPickup implements IRenderable{

    private float x;
    private float y;
    // Time until the pickup disappears after it has been dropped
    private int pickupTime;
    protected boolean pickedUp; // If this pickup has been picked up
    private boolean active;
    private int scoreForPickup;
    protected Bitmap image;

    private Paint paint;

    public AbstractPickup(float x, float y, int pickupTime, int scoreForPickup) {
        this.x = x;
        this.y = y;
        this.pickupTime = pickupTime;
        this.scoreForPickup = scoreForPickup;
        pickedUp = false;
        active = true;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
    }


    @Override
    public void render(Canvas c, float screenX, float screenY) {
        if (pickedUp)
            return;
        c.drawBitmap(image, getX() + screenX, getY() + screenY, paint);
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void countDownTimer() {
        if(!pickedUp) {
            pickupTime--;
        }
        if(pickupTime < 1) {
            this.active = false;
        }
    }

    public boolean isActive() { return active; }
    public float getX() { return x; }
    public float getY() { return y; }
    public int getScoreForPickup() { return scoreForPickup; }
    protected void deactivate() {
        active = false;
    }

}
