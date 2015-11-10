package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

/**
 * Our Moose AI.
 */
public class MooseAI extends InfectableArenaAnimalAI {

	public MooseAI() {
		// empty constructor
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// if the animal is infected, have the virus do stuff
		InfectableArenaAnimal infectableAnimal = (InfectableArenaAnimal) animal;
		if (infectableAnimal.isInfected()) {
			doInfectedActions(world, (InfectableArenaAnimal) animal);
			if (animal.getViewRange() == CATARACTS_REACH) {
				return new WaitCommand();
			}
		}

		// if there is grass next to it, it will eat it - otherwise hurt all
		// other weaker ArenaAnimals within 1 space of it
		for (Item currentItem : world.searchSurroundings(animal)) {
			if (currentItem.getPlantCalories() > 0
					&& currentItem.getLocation().getDistance(animal.getLocation()) == 1) {
				return new EatCommand(animal, currentItem);
			} else if (currentItem.getMeatCalories() > 0
					&& currentItem.getLocation().getDistance(animal.getLocation()) == 1
					&& currentItem.getStrength() < animal.getStrength()) {
				currentItem.loseEnergy(100);
			}
		}

		Location randomAdjLoc = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
		// if nothing to eat, and we have sufficient energy, breed
		if (animal.getEnergy() > animal.getMinimumBreedingEnergy() && randomAdjLoc != null) {
			return new BreedCommand(animal, randomAdjLoc);
		}

		int count = 0;
		// otherwise move to a random location
		Location targetLocation;
		do {
			targetLocation = Util.getRandomEmptyAdjacentLocation((World) world, animal.getLocation());
			if (targetLocation == null) {
				return new WaitCommand();
			}
			count++;
		} while (targetLocation.getDistance(animal.getLocation()) > 1 && count < 4);

		if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty((World) world, targetLocation)
				&& count < 4) {
			return new MoveCommand(animal, targetLocation);
		}

		return new WaitCommand();
	}

}
