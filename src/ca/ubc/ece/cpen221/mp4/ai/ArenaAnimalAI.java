package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.*;
import ca.ubc.ece.cpen221.mp4.items.animals.*;

public class ArenaAnimalAI implements AI {

	public ArenaAnimalAI() {
		// empty constructor, should be overwritten by individual animals
	}

	/**
	 * returns true if the location is empty
	 * 
	 * @param world
	 *            the world with the location
	 * @param animal
	 *            the animal
	 * @param location
	 *            the location to check if empty
	 * @return true if the location is empty, false otherwise
	 */
	public boolean isLocationEmpty(ArenaWorld world, ArenaAnimal animal, Location location) {
		if (!Util.isValidLocation(world, location)) {
			return false;
		}
		Set<Item> possibleMoves = world.searchSurroundings(animal);
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		Direction dir = Util.getRandomDirection();
		Location targetLocation = new Location(animal.getLocation(), dir);
		Set<Item> possibleEats = world.searchSurroundings(animal);
		Location current = animal.getLocation();
		Iterator<Item> it = possibleEats.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if ((item.getName().equals("Gnat") || item.getName().equals("Rabbit"))
					&& (current.getDistance(item.getLocation()) == 1)) {
				return new EatCommand(animal, item); // arena animals eat gnats
														// and rabbits
			}
		}
		if (Util.isValidLocation(world, targetLocation) && this.isLocationEmpty(world, animal, targetLocation)) {
			return new MoveCommand(animal, targetLocation);
		}
		return new WaitCommand();
	}

}
