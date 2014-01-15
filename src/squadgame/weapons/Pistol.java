package squadgame.weapons;

import squadgame.entities.Bullet;
import squadgame.entities.Soldier;

public class Pistol extends AbstractWeapon {

	public Pistol(String name, Soldier soldier, int ammo, int damage, int reloadTime) {
		super(name, soldier, ammo, damage, reloadTime);
	}

	@Override
	public Bullet shoot(float x, float y, float angle) {
		resetReloadCounter();
		return new Bullet(x, y, angle, damage);
	}

}
