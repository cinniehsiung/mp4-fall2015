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

public class PenguinAI extends InfectableArenaAnimalAI {

	public PenguinAI() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		InfectableArenaAnimal infectableAnimal = (InfectableArenaAnimal) animal;
		if (infectableAnimal.isInfected()) {
			doInfectedActions(world, (InfectableArenaAnimal) animal);
			
			if(animal.getViewRange() == CATARACTS_REACH){
				return new WaitCommand();
			}
		}
		
		// if there is grass / gnats next to the Penguin, it will eat it
		for (Item currentItem : world.searchSurroundings(animal)) {
			if (currentItem.getPlantCalories() > 0
					&& currentItem.getLocation().getDistance(animal.getLocation()) == 1) {
				return new EatCommand(animal, currentItem);
			} else if (currentItem.getName().equals("Gnat")
					&& currentItem.getLocation().getDistance(animal.getLocation()) == 1
					&& currentItem.getStrength() < animal.getStrength()) {
				return new EatCommand(animal, currentItem);
			}
		}
		// if nothing to eat, but there is a predator near, run away
		for (Item currentItem : world.searchSurroundings(animal)) {
			if (currentItem.getName().equals("Fox") || currentItem.getName().equals("SabreToothTiger")) {
				Direction runAway = Util.getDirectionTowards(currentItem.getLocation(), animal.getLocation());
				if (Util.isLocationEmpty((World) world, new Location(animal.getLocation(), runAway)))
					return new MoveCommand(animal, new Location(animal.getLocation(), runAway));
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
