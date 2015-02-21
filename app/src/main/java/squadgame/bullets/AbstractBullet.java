package squadgame.bullets;

import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import android.graphics.Paint;

public abstract class AbstractBullet
    implements IRenderable, IEntity {

    protected float x, y;
    protected float angle;
    protected boolean alive;
    protected int damage;
    protected Paint paint;
    protected int lifeTime = 500;

    public AbstractBullet(float x, float y, float angle, int damage) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.alive = true;
        this.damage = damage;
        this.paint = new Paint();
    }

    public boolean isActive() {
        return alive;
    }
}
