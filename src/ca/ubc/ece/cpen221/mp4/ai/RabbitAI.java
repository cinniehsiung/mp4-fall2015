package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
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

	private int closest = 10; // max number; greater than rabbit's view range
	private int temp;
	private boolean foxFound;

	public RabbitAI() {
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO: Change this. Implement your own AI rules.
		final int MAX_ENERGY = animal.getMaxEnergy();
		final int BREEDING_ENERGY = 50;
		final Location CURRENT_LOCATION = animal.getLocation();
		final int CURRENT_ENERGY = animal.getEnergy();
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
					if (proximity == 1) {
						if (currentItem.getName().equals("Fox")) {
							Direction awayfromFox = Util.getDirectionTowards(currentItem.getLocation(),
									CURRENT_LOCATION);
							return new MoveCommand(animal, new Location(CURRENT_LOCATION, awayfromFox));
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
							return new MoveCommand(animal, new Location(CURRENT_LOCATION, towardsFood));
						}
					}
				}
			}
		}

		return new MoveCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION));
	}
}
