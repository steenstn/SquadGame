package squadgame.weapons;

import java.util.ArrayList;

import squadgame.entities.Bullet;
import squadgame.entities.Soldier;

public class MultipleBulletsGun extends AbstractWeapon {

	public MultipleBulletsGun(String name, Soldier soldier, int ammo, int damage, int reloadTime) {
		super(name, soldier, ammo, damage, reloadTime);
	}

	@Override
	public ArrayList<Bullet> shoot(float x, float y, float angle) {
		resetReloadCounter();
		float spread = 0.2f;
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		bullets.add(new Bullet(x, y, angle, damage));
		bullets.add(new Bullet(x, y, (float) (angle+Math.random()*spread), damage));
		bullets.add(new Bullet(x, y, (float) (angle-Math.random()*spread), damage));
		return bullets;
	}

}
