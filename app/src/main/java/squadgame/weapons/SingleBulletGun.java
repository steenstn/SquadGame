package squadgame.weapons;

import java.util.ArrayList;

import squadgame.bullets.AbstractBullet;
import squadgame.bullets.StandardBullet;
import squadgame.entities.Soldier;

public class SingleBulletGun extends AbstractWeapon {

	public SingleBulletGun(String name, Soldier soldier, int ammo, int damage, int reloadTime) {
		super(name, soldier, ammo, damage, reloadTime);
	}

	@Override
	public ArrayList<AbstractBullet> shoot(float x, float y, float angle) {
		resetReloadCounter();
		ArrayList<AbstractBullet> bullets = new ArrayList<AbstractBullet>();
		bullets.add(new StandardBullet(x, y, angle, damage));
		
		return bullets;
	}

}
