package squadgame.bullets;

import squadgame.entities.enemies.AbstractEnemy;
import squadgame.entities.Soldier;
import squadgame.main.Functions;
import squadgame.main.Model;
import android.graphics.Canvas;

public class StandardBullet
    extends AbstractBullet {

    private float speed = 8;

    public StandardBullet(float x, float y, float angle, int damage) {
        super(x, y, angle, damage);
    }

    @Override
    public void updatePosition(Model model) {

        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);

        if (lifeTime-- <= 0) {
            this.alive = false;
        }
        /*
        if (x > model.screenWidth || x < 0 || y > model.screenHeight || y < 0) {
            this.alive = false;
        }*/
    }

    @Override
    public void checkCollisions(Model model) {

        for (AbstractEnemy enemy : model.enemies) {
            model.collisionChecks++;
            if (alive
                    && Functions.rectsOverlap(x, y, 4, 4, enemy.getX(), enemy.getY(), enemy.getWidth(),
                        enemy.getWidth())) {
                this.alive = false;
                enemy.takeDamage(damage);
            }
        }

        for (Soldier soldier : model.soldiers) {
            model.collisionChecks++;
            if (alive
                    && Functions.rectsOverlap(x, y, 4, 4, soldier.getX(), soldier.getY(), soldier.getWidth(),
                        soldier.getWidth())) {
                this.alive = false;
                soldier.takeDamage(damage);
            }
        }

    }

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        float resX = x + screenX;
        float resY = y + screenY;
        paint.setARGB(255, 255, 255, 255);
        c.drawCircle(resX + 2, resY + 2, 4, paint);
    }

}
