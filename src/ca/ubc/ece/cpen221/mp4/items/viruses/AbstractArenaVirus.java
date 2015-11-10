package ca.ubc.ece.cpen221.mp4.items.viruses;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

public abstract class AbstractArenaVirus implements ArenaVirus {

	private int CONTAGIOUS_RANGE;
	private int INFECTION_TIME;
	private ImageIcon image;
	private boolean isDead;

	private AI ai;

	private Location location;
	private int ENERGY = 1; // energy is either 1 or 0 (virus is only alive or
						// dead)
	private int MOVING_RANGE = 1; // always 1 regardless of virus

	protected void setContagiousRange(int range) {
		this.CONTAGIOUS_RANGE = range;
	}

	protected void setInfectionTime(int time) {
		this.INFECTION_TIME = time;
	}

	protected void setLocation(Location l) {
		this.location = l;
	}

	@Override
	public void moveTo(Location targetLocation) {
		location = targetLocation;
	}

	@Override
	public int getMovingRange() {
		return MOVING_RANGE; // should always be 1
	}

	@Override
	public ImageIcon getImage() {
		return image;
	}

	@Override
	public abstract String getName();

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getStrength() {
		return 0; // does not apply to viruses
	}

	@Override
	public void loseEnergy(int energy) {
		//this.ENERGY = energy; // should only be either 0 or 1

	}

	@Override
	public boolean isDead() {
		return this.ENERGY <= 0;
	}

	@Override
	public int getPlantCalories() {
		// viruses aren't plants
		return 0;
	}

	@Override
	public int getMeatCalories() {
		// viruses aren't animals
		return 0;
	}

	@Override
	public int getCoolDownPeriod() {
		return 1;
	}

	@Override
	public int getContagiousRange() {
		return CONTAGIOUS_RANGE;
	}

	@Override
	public int getInfectionTime() {
		return INFECTION_TIME;
	}

	@Override
	public abstract void infectAnimal(InfectableArenaAnimal animal);

	/**
	 * Method for getting the next action.
	 * 
	 * @param world
	 * @return
	 */
	public Command getNextAction(World world) {
		Set<Item> surroundingItems = new TreeSet<Item>();
		surroundingItems = world.searchSurroundings(location, this.getContagiousRange());

		for (Item currentItem : surroundingItems) {
			if (currentItem.getName().equals("Moose") || currentItem.getName().equals("Penguin")
					|| currentItem.getName().equals("SabreToothTiger")) {
				infectAnimal((InfectableArenaAnimal) currentItem);
			}
		}

		int count = 4;
		Location toMove;
		do {
			toMove = new Location(location, Util.getRandomDirection());
			count--;
		} while (!Util.isLocationEmpty(world, toMove) && count != 0);

		if (count != 0) {
			return new MoveCommand(this, toMove);
		}
		
		return new WaitCommand();
	}
}
