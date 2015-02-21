package squadgame.entities.enemies;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.MainActivity;
import squadgame.main.Model;

public abstract class AbstractEnemy
        implements IRenderable, IEntity {

    AbstractEnemy(float x, float y, int health, int width, int height) {
        this.x = x;
        this.y = y;
        this.health = health;
        alive = true;

        this.width = Math.round(width * scale);
        this.height =Math.round(height * scale);
        matrix = new Matrix();
        healthPaint.setColor(Color.GREEN);
    }
    protected float x, y;

    protected int health;
    protected int width, height;
    protected boolean alive;
    protected Bitmap image;
    protected Paint healthPaint = new Paint();
    protected float scale = 1;

    protected Paint paint = new Paint();
    protected Matrix matrix;

    public float getX() {
        return x;
    }

    public float getCenterX() {
        return x + width / 2;
    }

    public float getCenterY() {
        return y + width / 2;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public float getScale() {
        return scale;
    }

    public void setImage(Bitmap image) {
        image = Bitmap.createBitmap(image);
    }


    public void setScale(float scale) {
        this.scale = scale;
        this.width *= scale;
    }


    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0)
            this.alive = false;
    }

    public int getHealth() {
        return health;
    }


    @Override
    public boolean isActive() {
        return alive;
    }

    @Override
    public void checkCollisions(Model model) {
    }

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        float resX = x + screenX;
        float resY = y + screenY;
        matrix.setTranslate(resX, resY);
        Paint tempPaint = new Paint();
        tempPaint.setColor(Color.CYAN);
        c.drawRect(resX,resY,resX+width,resY+width,tempPaint);//.drawBitmap(image, matrix, paint);

        if (MainActivity.printDebug) {
            c.drawRect(resX, resY + width + 1, resX + (((float) health / 100.0f) * (1 + width)), resY + width + 6,
                    healthPaint);
        }

    }
}
