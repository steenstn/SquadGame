package squadgame.weapons;

import java.util.ArrayList;

import squadgame.bullets.AbstractBullet;
import squadgame.bullets.LaserBullet;
import squadgame.bullets.StandardBullet;
import squadgame.entities.Soldier;

public class LaserGun extends AbstractWeapon {

	public LaserGun(String name, Soldier soldier, int ammo, int damage, int reloadTime) {
		super(name, soldier, ammo, damage, reloadTime);
	}

	@Override
	public ArrayList<AbstractBullet> shoot(float x, float y, float angle) {
		resetReloadCounter();
		ArrayList<AbstractBullet> bullets = new ArrayList<AbstractBullet>();
		// Spawn a bit away from the soldier
		float spawnX = (float) (x+5*Math.cos(angle));
		float spawnY = (float) (y+5*Math.sin(angle));
		
		bullets.add(new LaserBullet(spawnX, spawnY, angle, damage));
		
		return bullets;
	}

}
