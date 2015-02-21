package squadgame.entities.enemies;

import squadgame.main.MainActivity;
import squadgame.main.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

public class Zombie extends AbstractEnemy {


    private float angle;
    private float targetAngle;
    private float turningSpeed = 0.04f;
    private float movementSpeed = 1f;

    public Zombie(float x, float y) {
        super(x, y, 100, 64, 64);
        this.x = x;
        this.y = y;
        this.angle = this.targetAngle = (float) (Math.random() * 2 * Math.PI);
        this.health = 100;
        this.width = this.height = Math.round(64.0f * scale);
        this.alive = true;
        paint.setARGB(255, 250, 150, 150);
        paint.setAntiAlias(false);
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

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        float resX = x + screenX;
        float resY = y + screenY;
        matrix.setTranslate(resX, resY);
        matrix.postRotate((float) (angle * 180f / Math.PI) + 90, resX + width / 2, resY + width / 2);

        c.drawRect(resX,resY,resX+width,resY+width,paint);//.drawBitmap(image, matrix, paint);

        if (MainActivity.printDebug) {
            c.drawRect(resX, resY + width + 1, resX + (((float) health / 100.0f) * (1 + width)), resY + width + 6,
                healthPaint);
        }

    }

}
