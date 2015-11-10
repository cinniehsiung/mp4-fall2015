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
		// empty constructor
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO: Change this. Implement your own AI rules.
		final int MAX_ENERGY = animal.getMaxEnergy();
		final int BREEDING_ENERGY = MAX_ENERGY - 5;
		final int CURRENT_ENERGY = animal.getEnergy();

		final Location CURRENT_LOCATION = animal.getLocation();
		final int VIEW_RANGE = animal.getViewRange();
		int proximity;
		boolean foxfound = false;

		Location randomLoc;

		Set<Item> surroundingItems = new TreeSet<Item>();
		surroundingItems = world.searchSurroundings(animal);

		// start from immediate proximity then move outwards
		for (proximity = 1; proximity <= VIEW_RANGE; proximity++) {
			for (Item currentItem : surroundingItems) {
				if (currentItem.getLocation().getDistance(CURRENT_LOCATION) == proximity) {

					// if it's a fox, run away
					if (currentItem.getName().equals("Fox")) {
						Direction awayfromFox = Util.getDirectionTowards(currentItem.getLocation(), CURRENT_LOCATION);
						Location wheretorun = new Location(CURRENT_LOCATION, awayfromFox);
						if (Util.isLocationEmpty((World) world, wheretorun)) {
							return new MoveCommand(animal, wheretorun);
						}

						else {
							Direction otherdirection = Util.getDirectionTowards(currentItem.getLocation(), new Location(
									CURRENT_LOCATION, Util.getDirectionTowards(CURRENT_LOCATION, wheretorun)));
							Location whereelsetorun = new Location(CURRENT_LOCATION, otherdirection);
							if (Util.isLocationEmpty((World) world, whereelsetorun)) {
								return new MoveCommand(animal, whereelsetorun);
							}
						}

					}

					// if it's food
					else if (currentItem.getName().equals("grass")) {
						// we only care about it if we are hungry
						if (CURRENT_ENERGY < MAX_ENERGY - currentItem.getPlantCalories()) {
							// if we are next to it, eat it
							if (proximity == 1) {
								return new EatCommand(animal, currentItem);
							}

							// otherwise go towards it
							else {
								Direction towardsFood = Util.getDirectionTowards(CURRENT_LOCATION,
										currentItem.getLocation());
								Location wheretogo = new Location(CURRENT_LOCATION, towardsFood);
								if (Util.isLocationEmpty((World) world, wheretogo)) {
									return new MoveCommand(animal, wheretogo);
								}
							}
						}

					}

					// if it's a rabbit, get away
					// we don't want to fight for the same food
					else if (currentItem.getName().equals("Rabbit")) {
						Direction awayfromFox = Util.getDirectionTowards(currentItem.getLocation(), CURRENT_LOCATION);
						Location wheretorun = new Location(CURRENT_LOCATION, awayfromFox);
						if (Util.isLocationEmpty((World) world, wheretorun)) {
							return new MoveCommand(animal, wheretorun);
						}

						else {
							Direction otherdirection = Util.getDirectionTowards(currentItem.getLocation(), new Location(
									CURRENT_LOCATION, Util.getDirectionTowards(CURRENT_LOCATION, wheretorun)));
							Location whereelsetorun = new Location(CURRENT_LOCATION, otherdirection);
							if (Util.isLocationEmpty((World) world, whereelsetorun)) {
								return new MoveCommand(animal, whereelsetorun);
							}
						}
					}
				}
			}
		}

		// if there is nothing around in the view range
		// then breed if there is enough energy

		Location randomAdjLoc = Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION);
		if (CURRENT_ENERGY >= BREEDING_ENERGY && randomAdjLoc != null) {
			return new BreedCommand(animal, randomAdjLoc);
		}

		if (CURRENT_ENERGY < MAX_ENERGY / 5) {
			return new WaitCommand();
		}

		return moveFromEdge(CURRENT_LOCATION.getX(), CURRENT_LOCATION.getY(), world, animal, CURRENT_LOCATION);
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

	private Command moveFromEdge(int x, int y, ArenaWorld world, ArenaAnimal animal, Location currentLocation) {
		Location eastOf = new Location(currentLocation, Direction.EAST);
		Location southOf = new Location(currentLocation, Direction.SOUTH);
		Location westOf = new Location(currentLocation, Direction.WEST);
		Location northOf = new Location(currentLocation, Direction.NORTH);

		Location randomLoc;

		// if rabbit is at top left quadrant, move east
		if (y <= world.getHeight() / 2 && x <= world.getWidth() / 2 && isLocationEmpty(world, animal, eastOf)) {
			return new MoveCommand(animal, eastOf);
		}
		// if rabbit is at top right quadrant, move south
		else if (y <= world.getHeight() / 2 && x >= world.getWidth() / 2 && isLocationEmpty(world, animal, southOf)) {
			return new MoveCommand(animal, southOf);
		}
		// if rabbit is at bottom right quadrant, move west
		else if (y >= world.getHeight() / 2 && x >= world.getWidth() / 2 && isLocationEmpty(world, animal, westOf)) {
			return new MoveCommand(animal, westOf);
		}
		// if rabbit is at bottom of arena, move north
		else if (y >= world.getHeight() / 2 && x <= world.getWidth() / 2 && isLocationEmpty(world, animal, northOf)) {
			return new MoveCommand(animal, northOf);
		}
		// move in random direction
		else {
			int count = 0;
			do {
				count++;
				randomLoc = Util.getRandomEmptyAdjacentLocation((World) world, currentLocation);
				if (randomLoc == null) {
					return new WaitCommand();
				}
			} while (randomLoc.getDistance(currentLocation) > 1 && count < 4);
			if (Util.isValidLocation(world, randomLoc) && Util.isLocationEmpty((World) world, randomLoc) && count < 4) {
				return new MoveCommand(animal, randomLoc);
			} else {
				return new WaitCommand();
			}
		}
	}
}
