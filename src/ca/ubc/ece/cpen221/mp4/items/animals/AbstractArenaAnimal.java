package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.viruses.ArenaVirus;

/**
 * abstract class for other arena animals (not fox or rabbit)
 */
public abstract class AbstractArenaAnimal implements ArenaAnimal {

	// required fields
	private int INITIAL_ENERGY;
	private int energy = INITIAL_ENERGY;
	private int MAX_ENERGY;
	private int STRENGTH;
	private int VIEW_RANGE;
	private int INITIAL_VIEW_RANGE;
	private int MIN_BREEDING_ENERGY;
	private int COOLDOWN;
	private ImageIcon image;
	private Location location;

	@Override
	public void eat(Food food) {
		energy = Math.min(MAX_ENERGY, energy + food.getMeatCalories());
	}

	/**
	 * Method to set the initial energy of the arena animal
	 * 
	 * @param i
	 *            the initial energy
	 */
	protected void setINITIAL_ENERGY(int i) {
		this.INITIAL_ENERGY = i;
	}

	/**
	 * Method to set the current energy of the arena animal
	 * 
	 * @param i
	 *            the new energy
	 */
	public void setEnergy(int i) {
		this.energy = i;
	}

	/**
	 * Method to set the max energy of the arena animal
	 * 
	 * @param i
	 *            the max energy
	 */
	protected void setMAX_ENERGY(int i) {
		this.MAX_ENERGY = i;
	}

	/**
	 * Method to set the strength of the arena animal
	 * 
	 * @param i
	 *            the strength of the arena animal
	 */
	protected void setSTRENGTH(int i) {
		this.STRENGTH = i;
	}

	/**
	 * Method to set the initial view range of the arena animal
	 * 
	 * @param i
	 *            the initial view range
	 */
	protected void setINITIAL_VIEW_RANGE(int i) {
		this.INITIAL_VIEW_RANGE = i;
	}

	/**
	 * Method to set the view range of the arena animal
	 * 
	 * @param i
	 *            the view range
	 */
	public void setVIEW_RANGE(int i) {
		this.VIEW_RANGE = i;
	}

	/**
	 * Method to set the minimum breeding energy of the arena animal
	 * 
	 * @param i
	 *            the minimum breeding energy
	 */
	protected void setMIN_BREEDING_ENERGY(int i) {
		this.MIN_BREEDING_ENERGY = i;
	}

	/**
	 * Method to set the cool down of the arena animal
	 * 
	 * @param i
	 *            the cool down
	 */
	protected void setCOOLDOWN(int i) {
		this.COOLDOWN = i;
	}

	/**
	 * Method to set the location of the arena animal
	 * 
	 * @param l
	 *            the location
	 */
	protected void setLocation(Location l) {
		this.location = l;
	}

	@Override
	public int getCoolDownPeriod() {
		return COOLDOWN;
	}

	@Override
	public int getEnergy() {
		return energy;
	}

	@Override
	public ImageIcon getImage() {
		return image;
	}

	@Override
	public Location getLocation() {
		return this.location;
	}

	@Override
	public int getMaxEnergy() {
		return MAX_ENERGY;
	}

	@Override
	public int getMeatCalories() {
		return energy;
	}

	@Override
	public int getMinimumBreedingEnergy() {
		return MIN_BREEDING_ENERGY;
	}

	@Override
	public int getMovingRange() {
		return 1; // can only move to adjacent locations.
	}

	@Override
	public int getPlantCalories() {
		return 0; // arena animals are not plants
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public int getViewRange() {
		return VIEW_RANGE;
	}

	public int getInitialViewRange() {
		return INITIAL_VIEW_RANGE;
	}

	@Override
	public boolean isDead() {
		return this.energy <= 0;
	}

	@Override
	public void loseEnergy(int energyLoss) {
		this.energy = this.energy - energyLoss;
	}

	@Override
	public void moveTo(Location targetLocation) {
		location = targetLocation;
	}

	@Override
	public abstract LivingItem breed();
	
	@Override
	public abstract String getName();

	@Override
	public abstract Command getNextAction(World world);
}
