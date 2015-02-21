package squadgame.weapons;

import java.util.ArrayList;

import squadgame.bullets.AbstractBullet;
import squadgame.entities.Soldier;

public abstract class AbstractWeapon {

	protected int defaultReloadTime;
	private String name;
	protected Soldier soldier;
	protected int ammo;
	protected int damage;
	private int reloadTime;
	private int reloadCounter;
	private boolean reloaded = true;
	
	/**
	 * Constructor for the weapon
	 * @param name - Name of weapon
	 * @param soldier - The soldier that the weapon belongs to
	 * @param ammo - How much ammo does the weapon have
	 * @param damage - How much damage does each Bullet from the weapon make
	 * @param reloadTime - How long time before shots
	 */
	public AbstractWeapon(String name, Soldier soldier, int ammo, int damage, int reloadTime) {
		this.name = name;
		this.soldier = soldier;
		this.ammo = ammo;
		this.damage = damage;
		this.reloadTime = this.reloadCounter = this.defaultReloadTime = reloadTime;
		this.reloaded = true;
	}
	
	public void reload() {
		reloadCounter--;
		if(reloadCounter <= 0) {
			reloadCounter = reloadTime;
			reloaded = true;
		}
	}
	
	public String getName() {
		return name;
	}
	public boolean isReloaded() {
		return reloaded;
	}
	
	public void setReloadTime(int value) {
		this.reloadTime = value > 10 ? value : 10;
	}
	
	public int getReloadTime() {
		return reloadTime;
	}
	
	public void resetReloadTime() {
		this.reloadTime = defaultReloadTime;
	}
	
	public boolean hasAmmo() {
		return ammo >= 0;
	}
	
	public abstract ArrayList<AbstractBullet> shoot(float x, float y, float angle);
	
	protected void resetReloadCounter() {
		reloadCounter = reloadTime;
		reloaded = false;
	}
}
