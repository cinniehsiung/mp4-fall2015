package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Set;
import java.util.TreeSet;

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
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {
	public RabbitAI() {
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO: Change this. Implement your own AI rules.
		final int MAX_ENERGY = animal.getMaxEnergy();
		final int BREEDING_ENERGY = MAX_ENERGY;
		final int CURRENT_ENERGY = animal.getEnergy();
		
		final Location CURRENT_LOCATION = animal.getLocation();
		final int VIEW_RANGE = animal.getViewRange();
		int proximity;

		Set<Item> surroundingItems = new TreeSet<Item>();
		surroundingItems = world.searchSurroundings(animal);

		if (CURRENT_ENERGY == MAX_ENERGY) {
			return new BreedCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION));
		}

		for (proximity = 1; proximity <= VIEW_RANGE; proximity++) {
			for (Item currentItem : surroundingItems) {
				if (currentItem.getLocation().getDistance(CURRENT_LOCATION) == proximity) {
					if (proximity < 2) {
						if (currentItem.getName().equals("Fox") || currentItem.getName().equals("Rabbit")) {
							Direction awayfromFox = Util.getDirectionTowards(currentItem.getLocation(),
									CURRENT_LOCATION);
							Location wheretorun = new Location(CURRENT_LOCATION, awayfromFox);
							if (Util.isLocationEmpty((World) world, wheretorun)) {
								return new MoveCommand(animal, wheretorun);
							}

						} else if (currentItem.getName().equals("grass") && CURRENT_ENERGY != MAX_ENERGY) {
							return new EatCommand(animal, currentItem);
						}
					}

					else {
						if (CURRENT_ENERGY > BREEDING_ENERGY) {
							return new BreedCommand(animal,
									Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION));
						} else if (currentItem.getName().equals("grass")) {
							Direction towardsFood = Util.getDirectionTowards(CURRENT_LOCATION,
									currentItem.getLocation());
							Location wheretogo = new Location(CURRENT_LOCATION, towardsFood);
							if (Util.isLocationEmpty((World) world, wheretogo)) {
								return new MoveCommand(animal, wheretogo);
							}
						}
					}
				}
			}
		}

		return new MoveCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION));
	}
}
