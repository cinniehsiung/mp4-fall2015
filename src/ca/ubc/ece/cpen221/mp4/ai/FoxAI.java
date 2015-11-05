package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashSet;
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
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.staff.WorldUI;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {

	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO: Change this. Implement your own AI to make decisions regarding
		// the next action.

		final Location CURRENT_LOCATION = animal.getLocation();
		final int ENERGY = animal.getEnergy();
		final int STRENGTH = animal.getStrength();
		final int MAX_ENERGY = animal.getMaxEnergy();
		final int VIEW_RANGE = animal.getViewRange();
		final int BREEDING_ENERGY = MAX_ENERGY - 20;

		int proximity;

		Set<Item> surroundingItems = new TreeSet<Item>();
		surroundingItems = world.searchSurroundings(animal);

		for (proximity = 1; proximity <= VIEW_RANGE; proximity++) {
			for (Item currentItem : surroundingItems) {
				if (currentItem.getLocation().getDistance(CURRENT_LOCATION) == proximity) {
					// if item is a rabbit,
					if (currentItem.getName().equals("Rabbit") && ENERGY < MAX_ENERGY - currentItem.getMeatCalories()) {
						if (proximity == 1) {
							if (currentItem.getStrength() < STRENGTH) {
								return new EatCommand(animal, currentItem);
							}
						} else {
							Direction toRabbit = Util.getDirectionTowards(CURRENT_LOCATION, currentItem.getLocation());
							Location gotoRabbit = new Location(CURRENT_LOCATION, toRabbit);
							if (Util.isLocationEmpty((World) world, gotoRabbit)) {
								return new MoveCommand(animal, gotoRabbit);
							}
						}
					}

					if (currentItem.getName().equals("Fox")) {
						Direction awayfromFox = Util.getDirectionTowards(currentItem.getLocation(), CURRENT_LOCATION);
						Location ownTurf = new Location(CURRENT_LOCATION, awayfromFox);
						if (Util.isLocationEmpty((World) world, ownTurf)) {
							return new MoveCommand(animal, ownTurf);
						}
					}
				}
			}
		}

		if (ENERGY > BREEDING_ENERGY) {
			return new BreedCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION));
		}

		return new MoveCommand(animal, Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION));

	}

	/**
	 * Helper method to move the animal from the edge of the arena, based on its
	 * current x and y coordinates
	 * 
	 * @param x
	 *            - coordinate of the animal's current location
	 * @param y
	 *            - coordinate of the animal's current location
	 * 
	 *            currently, always moves east/west before north/south***
	 */

	private void moveFromEdge(int x, int y, ArenaWorld world, ArenaAnimal animal, Location currentLocation) {
		// if fox is at left side of the arena, and is further west than north,
		// move east
		if (x < y && x <= 2) {
			animal.moveTo(new Location(currentLocation, Direction.EAST));
		}
		// if fox is at right side of arena, and is further east than south,
		// move west
		else if (x > y && x >= (world.getWidth() - 2)) {
			animal.moveTo(new Location(currentLocation, Direction.WEST));
		}
		// if fox is at top of arena, move south
		else if (y <= 2) {
			animal.moveTo(new Location(currentLocation, Direction.SOUTH));
		}
		// if fox is at bottom of arena, move north
		else if (y >= (world.getHeight() - 2)) {
			animal.moveTo(new Location(currentLocation, Direction.NORTH));
		}
		// move in random direction
		else {
			animal.moveTo(new Location(currentLocation, Util.getRandomDirection()));
		}
	}
}
