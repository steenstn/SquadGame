package squadgame.entities.enemies;

import android.graphics.Canvas;

import squadgame.main.Model;

public class Runner extends AbstractEnemy {

    private float angle;
    private float movementSpeed = 4f;
    public Runner(float x, float y, float angle) {
        super(x,y, 40, 64, 64);
        this.angle = angle;
    }
    @Override
    public void updatePosition(Model model) {
        x += movementSpeed * Math.cos(angle);
        y += movementSpeed * Math.sin(angle);

        if (x > model.screenWidth + width || x < -width || y > model.screenHeight + width || y < -width) {
            this.alive = false;
        }
    }


}
