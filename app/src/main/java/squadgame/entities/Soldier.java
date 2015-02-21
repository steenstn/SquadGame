package squadgame.entities;

import java.util.ArrayList;

import squadgame.bullets.AbstractBullet;
import squadgame.entities.enemies.AbstractEnemy;
import squadgame.interfaces.IEntity;
import squadgame.interfaces.IRenderable;
import squadgame.main.Functions;
import squadgame.main.Model;
import squadgame.pickups.AbstractPickup;
import squadgame.weapons.AbstractWeapon;
import squadgame.weapons.SingleBulletGun;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Soldier
    implements IRenderable, IEntity {

    public static final float originalMaxSpeed = 7;
    public static final float originalGroundSpeed = 3;

    private float x, y;
    private boolean alive;
    private float angle;
    private float speedX, speedY, targetSpeed;
    private int red, green, blue;
    private String name;
    private AbstractWeapon weapon;
    private int width = 64;
    private float scale = 1;
    private float targetX, targetY;
    private AbstractEnemy targetEnemy;
    private SoldierPortrait portrait;
    private float bulletAngle;
    private int health;
    private int maxHealth;
    public ArrayList<AbstractPickup> pickups;
    public Paint healthPaint = new Paint();
    private Bitmap image;
    private Matrix matrix;

    public Soldier(String name, float x, float y, int r, int g, int b) {
        this.name = name;
        this.x = x;
        this.y = y;

        this.targetX = x;
        this.targetY = y;

        healthPaint.setColor(Color.GREEN);
        this.weapon = new SingleBulletGun("Pistol", this, 100, 20, 80);

        this.red = r;
        this.green = g;
        this.blue = b;
        this.health = this.maxHealth = 100;
        this.alive = true;

        this.speedX = speedY = targetSpeed = originalGroundSpeed;

        this.matrix = new Matrix();

        pickups = new ArrayList<AbstractPickup>();

    }

    public String getName() {
        return name;
    }

    public SoldierPortrait getPortrait() {
        return portrait;
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

    public AbstractWeapon getWeapon() {
        return weapon;
    }

    public int red() {
        return red;
    }

    public int green() {
        return green;
    }

    public int blue() {
        return blue;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        this.width *= scale;
    }

    public void setTarget(float x, float y) {
        targetX = x;
        targetY = y;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setSpeed(float speed) {
        this.targetSpeed = speed;
    }

    public void increaseSpeed() {
        targetSpeed = Math.min(targetSpeed*1.2f, originalMaxSpeed);
    }

    public void setPortrait(SoldierPortrait portrait) {
        this.portrait = portrait;
    }

    public void targetClosestEnemy(ArrayList<AbstractEnemy> enemies) {
        if (enemies.size() == 0)
            return;

        float shortestDistance = Functions.getDistanceSquared(x, y, enemies.get(0).getX(), enemies.get(0).getY());
        int indexOfClosestEnemy = 0;

        for (int i = 1; i < enemies.size(); i++) {
            float distance = Functions.getDistanceSquared(x, y, enemies.get(i).getX(), enemies.get(i).getY());

            if (distance < shortestDistance) {
                shortestDistance = distance;
                indexOfClosestEnemy = i;
            }
        }
        targetEnemy = enemies.get(indexOfClosestEnemy);
    }

    public void addAndActivatePickup(AbstractPickup pickup) {
        pickups.add(pickup);
        pickup.activatePickup(this);
    }

    @Override
    public void checkCollisions(Model model) {
        for (AbstractEnemy enemy : model.enemies) {
            model.collisionChecks++;
            if (Functions.rectsOverlap(x, y, width * 0.8f, width * 0.8f, enemy.getX(), enemy.getY(), enemy.getWidth(),
                enemy.getWidth()))
                takeDamage(1);
        }
        /*
        List<Rectangle> returnObjects = new ArrayList<Rectangle>();
        for (Enemy enemy : model.enemies) {
          returnObjects.clear();
          Rectangle soldierDimensions = new Rectangle((int)x, (int)y, width, width);
          model.quadTree.retrieve(returnObjects, soldierDimensions);
         
          for (Rectangle rectangle : returnObjects) {
        	  model.collisionChecks++;
        	  if(Functions.rectsOverlap(x, y, width*0.8f, width*0.8f, 
        			  rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getWidth()))
        			takeDamage(1);
          }
        }*/
    }

    public void usePickups() {
        if (pickups.size() <= 0)
            return;

        for (int i = 0; i < pickups.size(); i++) {
            if (pickups.get(i).isActive()) {
                pickups.get(i).doEffect(this);
            } else {
                pickups.remove(i);
            }
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public void setWeapon(AbstractWeapon weapon) {
        this.weapon = weapon;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0)
            alive = false;
    }

    public boolean isActive() {
        return alive;
    }

    @Override
    public void updatePosition(Model model) {
        float deltaX = targetX - (x + width / 2);
        float deltaY = targetY - (y + width / 2);

        speedX = Math.min(Math.abs(deltaX/2), targetSpeed);
        speedY = Math.min(Math.abs(deltaY/2), targetSpeed);

        if (Math.abs(deltaX) > 1 && Math.abs(deltaY) > 1) {
            angle = (float) (Math.atan2(deltaY, deltaX));

            x += speedX * Math.cos(angle);
            y += speedY * Math.sin(angle);
        }
        else {
            targetSpeed = originalGroundSpeed;
        }

    }

    public void shoot(Model model) {
        weapon.reload();
        float deltaX = targetEnemy.getX() + targetEnemy.getWidth() / 2 - (x + width / 2);
        float deltaY = targetEnemy.getY() + targetEnemy.getWidth() / 2 - (y + width / 2);
        bulletAngle = (float) (Math.atan2(deltaY, deltaX));
        if (weapon.isReloaded() && weapon.hasAmmo()) {

            float bulletX = (float) (x + width / 2 + 0.8 * width * Math.cos(bulletAngle));
            float bulletY = (float) (y + width / 2 + 0.8 * width * Math.sin(bulletAngle));
            ArrayList<AbstractBullet> bullets = weapon.shoot(bulletX, bulletY, bulletAngle);
            for (AbstractBullet bullet : bullets) {
                model.bullets.add(bullet);
                model.renderables.add(bullet);
            }
        }
    }

    @Override
    public void render(Canvas c, float screenX, float screenY) {
        float resX = x + screenX;
        float resY = y + screenY;

        Paint paint = new Paint();
        paint.setARGB(255, red, green, blue);
        paint.setAntiAlias(true);

        Paint textPaint = new Paint();
        textPaint.setARGB(255, 255, 255, 255);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(Model.textScale);

        c.drawCircle(targetX + screenX, targetY + screenY, 5, paint);

        matrix.setTranslate(resX, resY);
        matrix.postRotate((float) (bulletAngle * 180f / Math.PI) + 90, resX + width / 2, resY + width / 2);

        //c.drawBitmap(image, x, y, paint);
        c.drawBitmap(image, matrix, paint);
        //c.drawRect(new Rect((int)x,(int)y,(int)x+ width,(int)y+width), paint);

        c.drawText(name, resX - width / 2, resY - 20, textPaint);

        c.drawRect(resX, resY + width + 1, resX + (((float) health / 100.0f) * (1 + width)), resY + width + 6,
            healthPaint);
        //c.drawText("HP: " + health, x, y, textPaint);
        //c.drawText("Pickups: " + pickups.size(), x, y+20, textPaint);

    }
}
