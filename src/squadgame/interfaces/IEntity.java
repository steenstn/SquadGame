package squadgame.interfaces;

import squadgame.main.Model;

/**
 * All entities that can move and collide
 * @author Steen
 *
 */
public interface IEntity {

	public void updatePosition(Model model);
	public void checkCollisions(Model model);
	public boolean isActive();
}
