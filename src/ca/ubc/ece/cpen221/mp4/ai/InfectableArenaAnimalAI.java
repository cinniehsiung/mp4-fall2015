package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

public abstract class InfectableArenaAnimalAI extends ArenaAnimalAI {
	public InfectableArenaAnimalAI() {
		// empty constructor, to be overwritten by individual animals
	}

	final private int BLACK_PLAGUE_DAMAGE = 20;
	public final int CATARACTS_REACH = -1;

	@Override
	public abstract Command getNextAction(ArenaWorld world, ArenaAnimal animal);

	/**
	 * Applies virus actions to the infected animal.
	 * 
	 * @param world
	 *            the current world
	 * @param animal
	 *            the infected animal to apply the virus actions to
	 */
	public void doInfectedActions(ArenaWorld world, InfectableArenaAnimal animal) {
		// get what virus the animal is infected with
		String virusStatus = animal.getVirus();

		// do appropriate actions
		if ("BlackPlague".equals(virusStatus)) {
			animal.loseEnergy(BLACK_PLAGUE_DAMAGE);
		}

		if ("Cataracts".equals(virusStatus)) {
			animal.setVIEW_RANGE(CATARACTS_REACH);
		}

		if ("Panacea".equals(virusStatus)) {
			animal.setEnergy(animal.getMaxEnergy());
		}

		// decrease the virus life span / heal the animal if the virus life span
		// is 0
		animal.decreaseVirusLifeSpan();
	}
}
