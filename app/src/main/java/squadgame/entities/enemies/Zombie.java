package squadgame.entities.enemies;

import squadgame.main.Functions;
import squadgame.main.MainActivity;
import squadgame.main.Model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;

public class Zombie extends AbstractEnemy {


    private float angle;
    private float targetAngle;
    private float turningSpeed = 0.04f;
    private float targetX, targetY;
    private float movementSpeed = 1.5f;

    public Zombie(float x, float y, float targetX, float targetY) {
        super(x, y, 100, 64, 64);
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
        this.angle = this.targetAngle = (float) (Math.random() * 2 * Math.PI);
        this.health = 100;
        this.width = this.height = Math.round(64.0f * scale);
        this.alive = true;
        paint.setARGB(255, 250, 150, 150);
        paint.setAntiAlias(false);
    }

    @Override
    public void updatePosition(Model model) {
        targetAngle = Functions.getAngleBetweenPoints(x,y, targetX, targetY);
        if(Math.abs(targetX - x) < 2 && Math.abs(targetY-y) < 2) {
            targetX = (float)Math.random()*model.screenWidth;
            targetY = (float)Math.random()*model.screenHeight;
        }

        if (angle < targetAngle) {
            angle += turningSpeed;
        } else {
            angle -= turningSpeed;
        }

        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);

                if (x > model.screenWidth + 3*width || x < -3*width || y > model.screenHeight + width*3 || y < -width*3) {
                    this.alive = false;
                }

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
            c.drawText("tX: " + targetX,resX, resY, paint);
            c.drawText("tY: " + targetY,resX, resY+10, paint);

        }

    }

}
