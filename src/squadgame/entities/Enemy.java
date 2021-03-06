package squadgame.entities;

import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.MainActivity;
import squadgame.main.Model;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Enemy
    implements IRenderable, IEntity {

    private float x, y;

    private float angle;
    private float targetAngle;
    private int health;
    private int width, height;
    private boolean alive;
    private static Bitmap image;
    private Paint healthPaint = new Paint();
    private float scale = 1;
    private float turningSpeed = 0.04f;
    private float movementSpeed = 1f;

    Paint paint = new Paint();
    private Matrix matrix;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        this.angle = this.targetAngle = (float) (Math.random() * 2 * Math.PI);
        this.health = 100;
        this.width = this.height = Math.round(64.0f * scale);
        this.alive = true;
        matrix = new Matrix();
        healthPaint.setColor(Color.GREEN);
        paint.setARGB(255, 150, 150, 150);
        paint.setAntiAlias(false);
    }

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
        Enemy.image = Bitmap.createBitmap(image);
    }

    public void setScale(float scale) {
        this.scale = scale;
        this.width *= scale;
    }

    @Override
    public void updatePosition(Model model) {
        if (Math.abs(angle - targetAngle) < 0.1) {
            targetAngle = (float) (Math.random() * 2 * Math.PI);
        }

        if (angle < targetAngle) {
            angle += turningSpeed;
        } else {
            angle -= turningSpeed;
        }

        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);
        /*
                if (x > model.screenWidth + width || x < -width || y > model.screenHeight + width || y < -width) {
                    this.alive = false;
                }*/

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
        matrix.postRotate((float) (angle * 180f / Math.PI) + 90, resX + width / 2, resY + width / 2);

        c.drawBitmap(image, matrix, paint);

        if (MainActivity.printDebug) {
            c.drawRect(resX, resY + width + 1, resX + (((float) health / 100.0f) * (1 + width)), resY + width + 6,
                healthPaint);
        }

    }

}
