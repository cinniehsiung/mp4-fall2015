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
import ca.ubc.ece.cpen221.mp4.items.animals.*;

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

		// look around fox's surroundings
		int proximity;
		Set<Item> surroundingItems = new TreeSet<Item>();
		surroundingItems = world.searchSurroundings(animal);

		// check things closest to fox first
		for (proximity = 1; proximity <= VIEW_RANGE; proximity++) {
			for (Item currentItem : surroundingItems) {
				if (currentItem.getLocation().getDistance(CURRENT_LOCATION) == proximity) {
					// if item is a rabbit 
					if (currentItem.getName().equals("Rabbit")) {
						if (proximity == 1) {
							// eat rabbit if next to the rabbit
							if (currentItem.getStrength() < STRENGTH) {
								return new EatCommand(animal, currentItem);
							}
						} else {
							//otherwise go towards the rabbit
							Direction toRabbit = Util.getDirectionTowards(CURRENT_LOCATION, currentItem.getLocation());
							Location gotoRabbit = new Location(CURRENT_LOCATION, toRabbit);
							if (Util.isLocationEmpty((World) world, gotoRabbit)) {
								return new MoveCommand(animal, gotoRabbit);
							}
						}
					}

					// if item is fox, spread out, don't compete for food
					if ((currentItem.getName().equals("Fox") || currentItem.getName().equals("grass")) && proximity < 2) {
						Direction awayfromFox = Util.getDirectionTowards(currentItem.getLocation(), CURRENT_LOCATION);
						Location ownTurf = new Location(CURRENT_LOCATION, oppositeDir(awayfromFox));
						if (Util.isLocationEmpty((World) world, ownTurf)) {
							return new MoveCommand(animal, ownTurf);
						}
					}
				}
			}
		}
		
		// if nothing is around the fox,
		Location randomAdjLoc = Util.getRandomEmptyAdjacentLocation((World) world, CURRENT_LOCATION);
		
		// breed if enough energy
		if (ENERGY > BREEDING_ENERGY && randomAdjLoc != null) {
			return new BreedCommand(animal, randomAdjLoc);
		}

		//otherwise move systematically around clockwise
		Command move = moveFromEdge(CURRENT_LOCATION.getX(), CURRENT_LOCATION.getY(), world, animal, CURRENT_LOCATION);
		return move;
		
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

	 */

	private Command moveFromEdge(int x, int y, ArenaWorld world, ArenaAnimal animal, Location currentLocation) {
		Location eastOf = new Location(currentLocation, Direction.EAST);
		Location southOf = new Location(currentLocation, Direction.SOUTH);
		Location westOf = new Location(currentLocation, Direction.WEST);
		Location northOf = new Location(currentLocation, Direction.NORTH);
		
		Location randomLoc;
	    
	    // if fox is at top left quadrant, move east
		if (y <= world.getHeight()/2 && x <= world.getWidth()/2 && isLocationEmpty(world, animal, eastOf)) {
			return new MoveCommand(animal, eastOf);
		}
		// if fox is at top right quadrant, move south
		else if (y <= world.getHeight()/2 && x >= world.getWidth()/2 && isLocationEmpty(world,animal,southOf)) {
			return new MoveCommand(animal, southOf);
		}
		// if fox is at bottom right quadrant, move west
		else if (y >= world.getHeight()/2 && x >= world.getWidth()/2 && isLocationEmpty(world, animal, westOf)) {
			return new MoveCommand(animal, westOf);
		}
		// if fox is at bottom of arena, move north
		else if (y >= world.getHeight()/2 && x <= world.getWidth()/2 && isLocationEmpty(world, animal, northOf)) {
			return new MoveCommand(animal, northOf);
		}
		// move in random direction
		else {
		    int count = 0;
		    do{
		    randomLoc = Util.getRandomEmptyAdjacentLocation((World) world, currentLocation);
		    if(randomLoc == null){
                return new WaitCommand();
            }
		    count++;
		    } while(randomLoc.getDistance(currentLocation) > 1 && count < 4);
		    
		    if(Util.isValidLocation(world, randomLoc) && Util.isLocationEmpty((World) world, randomLoc) && count < 4){
		        return new MoveCommand(animal, randomLoc);
		    }
		    else{
		        return new WaitCommand();
		    }
		}
	}
}
