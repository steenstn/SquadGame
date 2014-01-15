package squadgame.weapons;

import java.util.ArrayList;

import squadgame.entities.Bullet;
import squadgame.entities.Soldier;

public class SingleBulletGun extends AbstractWeapon {

	public SingleBulletGun(String name, Soldier soldier, int ammo, int damage, int reloadTime) {
		super(name, soldier, ammo, damage, reloadTime);
	}

	@Override
	public ArrayList<Bullet> shoot(float x, float y, float angle) {
		resetReloadCounter();
		ArrayList<Bullet> bullets = new ArrayList<Bullet>();
		bullets.add(new Bullet(x, y, angle, damage));
		
		return bullets;
	}

}
