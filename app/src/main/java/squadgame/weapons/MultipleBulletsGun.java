package squadgame.weapons;

import java.util.ArrayList;

import squadgame.bullets.AbstractBullet;
import squadgame.bullets.StandardBullet;
import squadgame.entities.Soldier;

public class MultipleBulletsGun extends AbstractWeapon {

	public MultipleBulletsGun(String name, int ammo, int damage, int reloadTime) {
		super(name, ammo, damage, reloadTime);
	}

	@Override
	public ArrayList<AbstractBullet> shoot(float x, float y, float angle) {
		resetReloadCounter();
		float spread = 0.2f;
		ArrayList<AbstractBullet> bullets = new ArrayList<AbstractBullet>();
		bullets.add(new StandardBullet(x, y, angle, damage));
		bullets.add(new StandardBullet(x, y, (float) (angle+Math.random()*spread), damage));
		bullets.add(new StandardBullet(x, y, (float) (angle-Math.random()*spread), damage));
		return bullets;
	}

}
