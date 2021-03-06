package squadgame.main;

import squadgame.bullets.AbstractBullet;
import squadgame.entities.enemies.AbstractEnemy;
import squadgame.entities.Soldier;
import squadgame.entities.SoldierPortrait;
import squadgame.entities.enemies.Runner;
import squadgame.entities.enemies.Zombie;
import squadgame.pickups.soldier.AbstractSoldierPickup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

class RenderThread
    extends Thread {
    private boolean run = false;

    public boolean userPaused = false;
    private SurfaceHolder sh;
    private Model model;

    public RenderThread(SurfaceHolder surfaceHolder, Context context, Handler handler, Model model) {
        sh = surfaceHolder;
        this.model = model;
    }

    public void doStart() {
        synchronized (sh) {
        }
    }

    public void run() {
        while (run) {
            Canvas c = null;
            try {
                c = sh.lockCanvas(null);
                synchronized (sh) {
                    model.collisionChecks = 0;
                    resetQuadTree();
                    updateSoldiers();
                    updatePickups();
                    updateEnemies();
                    //resetQuadTree();
                    updateBullets();
                    doDraw(c);
                }
            } finally {
                if (c != null) {
                    sh.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean b) {
        run = b;
    }

    public void setSurfaceSize(int width, int height) {
        synchronized (sh) {
            doStart();
        }
    }

    private void resetQuadTree() {
        model.quadTree.clear();
        for (AbstractEnemy enemy : model.enemies) {
            if (enemy.isActive()) {
                Rectangle enemyDimensions = new Rectangle((int) enemy.getX(), (int) enemy.getY(), enemy.getWidth(),
                    enemy.getWidth());
                model.quadTree.insert(enemyDimensions);
            }
        }
    }

    private void updateSoldiers() {
        for (int i = 0; i < model.soldiers.size(); i++) {
            Soldier soldier = model.soldiers.get(i);
            if (soldier.isActive()) {
                if (model.enemies.size() > 0) {
                    soldier.targetClosestEnemy(model.enemies);
                    soldier.shoot(model);
                }
                soldier.usePickups();
                soldier.updatePosition(model);
                soldier.checkCollisions(model);
            } else {
                model.soldiers.remove(i);
                model.renderables.remove(soldier);
                SoldierPortrait portrait = model.portraits.remove(i);
                model.renderables.remove(portrait);
            }
        }
    }

    private void updatePickups() {
        for (int i = 0; i < model.pickups.size(); i++) {
            AbstractSoldierPickup pickup = model.pickups.get(i);
            pickup.countDownTimer();
            if (pickup.isActive()) {
                pickup.checkCollisions(model);
            } else {
                model.pickups.remove(i);
                model.renderables.remove(pickup);
            }

        }
    }

    private void updateEnemies() {
        for (int i = 0; i < model.enemies.size(); i++) {
            AbstractEnemy enemy = model.enemies.get(i);
            if (enemy.isActive()) {
                enemy.updatePosition(model);
            } else {
                double chance = Math.random();
                if (chance > Model.dropChance) {
                    model.createRandomPickup(enemy.getX(), enemy.getY());
                }

                model.enemies.remove(i);
                model.renderables.remove(enemy);

                if (enemy.getHealth() <= 0) {
                    model.increaseScore(10);
                }
                model.spawnEnemyOutsideScreen(Zombie.class);
                if (Math.random() > 0.8) {
                    model.spawnEnemyOutsideScreen(Runner.class);
                }
            }
        }
    }

    private void updateBullets() {
        for (int i = 0; i < model.bullets.size(); i++) {
            AbstractBullet bullet = model.bullets.get(i);
            if (bullet.isActive()) {
                bullet.updatePosition(model);
                bullet.checkCollisions(model);
            } else {
                model.bullets.remove(i);
                model.renderables.remove(bullet);
            }
        }
    }

    private void doDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        canvas.drawRect(0, 0, model.screenWidth, model.screenHeight, paint);
        drawBackground(canvas, paint);
        drawRenderables(canvas, paint);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(Model.textScale * 1.5f);
        textPaint.setColor(Color.WHITE);
        Paint scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setTextSize(Model.textScale * 2.5f);
        scorePaint.setColor(Color.WHITE);
        canvas.drawText("Score: " + model.getScore(), 2, 50, scorePaint);

        if (MainActivity.printDebug) {
            canvas.drawText("Enemies: " + model.enemies.size(), 10, 80, textPaint);
            canvas.drawText("Collision checks: " + model.collisionChecks, 10, 110, textPaint);
            canvas.drawText("Bullets: " + model.bullets.size(), 10, 130, textPaint);
        }

        if (model.soldiers.size() == 0) {
            Paint gameOverPaint = new Paint();
            gameOverPaint.setAntiAlias(true);
            gameOverPaint.setTextSize(Model.textScale * 10);
            gameOverPaint.setColor(Color.WHITE);
            canvas.drawText("GAME OVER", 10, model.screenHeight / 2, gameOverPaint);
        }
    }

    private void drawBackground(Canvas canvas, Paint paint) {

        model.getLevel().render(canvas, MainView.screenX, MainView.screenY);
    }

    private void drawRenderables(Canvas canvas, Paint paint) {
        for (int i = 0; i < model.renderables.size(); i++) {
            model.renderables.get(i).render(canvas, MainView.screenX, MainView.screenY);
        }
    }
}
