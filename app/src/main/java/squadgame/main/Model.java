package squadgame.main;

import java.util.ArrayList;

import squadgame.bullets.AbstractBullet;
import squadgame.bullets.StandardBullet;
import squadgame.entities.enemies.AbstractEnemy;
import squadgame.entities.enemies.Runner;
import squadgame.entities.enemies.Zombie;
import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;
import squadgame.factories.EnemyFactory;
import squadgame.factories.PickupFactory;
import squadgame.factories.SoldierFactory;
import squadgame.interfaces.IRenderable;
import squadgame.levels.Level;
import squadgame.levels.LevelReader;
import squadgame.pickups.soldier.AbstractSoldierPickup;
import squadgame.weapons.MultipleBulletsGun;
import squadgame.weapons.SingleBulletGun;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Model {

    //Chance for drop of pickup, the random number must be bigger for a drop
    public static final float dropChance = 0.0f;
    public static float textScale = 10;

    public ArrayList<Soldier> soldiers;
    public ArrayList<SoldierPortrait> portraits;
    public ArrayList<AbstractEnemy> enemies;
    public ArrayList<IRenderable> renderables;
    public ArrayList<AbstractBullet> bullets;
    public ArrayList<AbstractSoldierPickup> pickups;
    public int collisionChecks = 0;
    private PickupFactory pickupFactory;
    private EnemyFactory enemyFactory;
    private SoldierFactory soldierFactory;
    public Bitmap backgroundTileset;
    private Level level;

    public int screenWidth;
    public int screenHeight;
    private int score = 0;
    public Context context;
    public Bitmap background;
    private BitmapFactory.Options imageScale = new BitmapFactory.Options();

    public QuadTree quadTree;

    public Model(Context ctx) {
        this.context = ctx;

        LevelReader lr = new LevelReader(ctx);
        backgroundTileset = BitmapFactory.decodeResource(context.getResources(), R.drawable.tiles);

        level = new Level(backgroundTileset);
        lr.readLevelFromFile(level, R.raw.level1);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        textScale = BitmapResizer.calculateScale(0.2f, 12, screenWidth);
        enemyFactory = new EnemyFactory(ctx, screenWidth, screenHeight);
        soldierFactory = new SoldierFactory(ctx, screenWidth, screenHeight);
        pickupFactory = new PickupFactory(ctx, screenWidth, screenHeight);

        quadTree = new QuadTree(0, new Rectangle(-500, -500, screenWidth + 500, screenHeight + 500));
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);

        imageScale.inScaled = false;

        soldiers = new ArrayList<Soldier>();
        Soldier soldier1 = soldierFactory.createSoldier("Bengelohanna", 200, screenHeight/2, 255, 0, 0, R.drawable.soldier_red);//new Soldier("Bengelohanna", 100,100,255,0,0);
        soldier1.setWeapon(new MultipleBulletsGun("Shotgun", soldier1, 200, 10, 80));

        Soldier soldier2 = soldierFactory.createSoldier("Rubachandra", screenWidth-200, screenHeight / 2, 0, 0, 255,
            R.drawable.soldier_blue);
        soldier2.setWeapon(new SingleBulletGun("Machine gun", soldier2, 200, 15, 25));
/*
        Soldier soldier3 = soldierFactory.createSoldier("Pepper Jack", screenWidth - 200, 100, 0, 255, 0,
            R.drawable.soldier_green);
        soldier3.setWeapon(new LaserGun("Laser", soldier3, 200, 40, 110));

        Soldier soldier4 = soldierFactory.createSoldier("Mustaffan", screenWidth - 200, screenHeight / 2, 255, 255, 0,
            R.drawable.soldier_yellow);*/
        soldiers.add(soldier1);
        soldiers.add(soldier2);
       // soldiers.add(soldier3);
       // soldiers.add(soldier4);

        portraits = new ArrayList<SoldierPortrait>();
        int soldierWidth = soldiers.get(0).getWidth();
        portraits.add(new SoldierPortrait(soldiers.get(0), 2*soldierWidth, screenHeight - 2 * soldierWidth));
        portraits.add(new SoldierPortrait(soldiers.get(1), screenWidth - 4 * soldierWidth, screenHeight - 2
                * soldierWidth));
        //portraits.add(new SoldierPortrait(soldiers.get(1), 2.1f * soldierWidth, screenHeight - 2 * soldierWidth));
/*
        portraits.add(new SoldierPortrait(soldiers.get(2), screenWidth - 3.1f * soldierWidth - soldierWidth,
            screenHeight - 2 * soldierWidth));
        portraits.add(new SoldierPortrait(soldiers.get(3), screenWidth - 2 * soldierWidth, screenHeight - 2
                * soldierWidth));
*/
        enemies = new ArrayList<AbstractEnemy>();
        //enemies.add(new Enemy(500,500));
        bullets = new ArrayList<AbstractBullet>();
        pickups = new ArrayList<AbstractSoldierPickup>();
        addRenderables();

        for (int i = 0; i < 3; i++) {
            spawnEnemyOutsideScreen(Zombie.class);
        }
    }

    public void spawnBomb(float x, float y) {
        for(int i = 0; i < 10; i++) {
            AbstractBullet bullet = new StandardBullet(x, y, (float)(Math.random()*2*Math.PI), 40);
            bullets.add(bullet);
            renderables.add(bullet);
        }
    }

    public void spawnEnemyOutsideScreen(Class<? extends AbstractEnemy> enemyType) {
        int whichSide = (int) (Math.round(Math.random() * 3) + 1);
        float newX = 0, newY = 0;

        if (whichSide == 0) {
            newX = 0 - MainView.screenX;
            newY = (float) (Math.random() * screenHeight) - MainView.screenY;
        } else if (whichSide == 1) {
            newX = screenWidth - MainView.screenX;
            newY = (float) (Math.random() * screenHeight) - MainView.screenY;
        } else if (whichSide == 2) {
            newX = (float) (Math.random() * screenWidth) - MainView.screenX;
            newY = 0 - MainView.screenY;
        } else if (whichSide == 3) {
            newX = (float) (Math.random() * screenWidth) - MainView.screenX;
            newY = screenHeight - MainView.screenY;

        }
        AbstractEnemy enemy;
        if(enemyType == Runner.class) {
            enemy = spawnRunnerWithRandomSoldierAsTarget(newX, newY);
        } else {
            enemy = enemyFactory.spawnZombie(newX, newY);
        }
        enemies.add(enemy);
        renderables.add(enemy);
    }

    private Runner spawnRunnerWithRandomSoldierAsTarget(float x, float y) {
        // Get a random soldier, make sure it's within array size
        int randomIndex = (int)Math.min(Math.round(Math.random()*soldiers.size()), soldiers.size()-1);
        if(randomIndex < 0 || randomIndex >= soldiers.size()) {
            return enemyFactory.spawnRunner(x, y, screenWidth / 2, screenHeight / 2);
        }
        Soldier s = soldiers.get(randomIndex);
        return enemyFactory.spawnRunner(x, y, s.getCenterX(), s.getCenterY());
    }

    public void createRandomPickup(float x, float y) {
        AbstractSoldierPickup pickup = pickupFactory.createRandomPickup(x, y);
        pickups.add(pickup);
        renderables.add(pickup);
    }

    public void spawnRunner() {

        Runner runner = enemyFactory.spawnRunner(400, 400, 500,500);
        enemies.add(runner);
        renderables.add(runner);
    }

    private void addRenderables() {
        renderables = new ArrayList<IRenderable>();
        for(Soldier s : soldiers) {
            renderables.add(s);
        }
        for (SoldierPortrait portrait : portraits) {
            renderables.add(portrait);
        }
    }

    public void increaseScore(int value) {
        score+= value*soldiers.size();
    }

    public int getScore() {
        return score;
    }

    public Level getLevel() {
        return level;
    }
}
