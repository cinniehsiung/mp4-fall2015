package ca.ubc.ece.cpen221.mp4.items.viruses;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

public interface ArenaVirus extends MoveableItem, Actor {

	/**
	 * Method that infects the target animal with the current virus.
	 * 
	 * @param target
	 *            animal to be infected
	 */
	public void infectAnimal(InfectableArenaAnimal animal);

	/**
	 * Returns the maximum distance that the virus may infect animals. For
	 * example, if this method returns 1, the virus can only infect animals
	 * directly adjacent to it.
	 * 
	 * @return the maximum contagious distance
	 */
	int getContagiousRange();

	/**
	 * Returns the number of steps the virus designation will stay on the
	 * animal.
	 * 
	 * @return the infection time
	 */
	int getInfectionTime();

	/**
	 * Returns the cooldown period between two consecutive actions. This
	 * represents the number of steps passed between two actions.
	 *
	 * @return the number of steps between actions
	 */
	int getCoolDownPeriod();
}
