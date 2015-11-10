package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

/**
 * The Moose is an {@link ArenaAnimal} that only eats Grass. However, it is
 * capable of damaging smaller animals (such as {@link Fox}s {@link Rabbit}s
 * {@link Gnat}s and {@link Penguin}s)
 */
public class Moose extends InfectableArenaAnimal {

	// initial stats
	private final int INITIAL_ENERGY = 150;
	private final int MAX_ENERGY = 200;
	private final int STRENGTH = 180;
	private final int VIEW_RANGE = 7;
	private final int MIN_BREEDING_ENERGY = 120;
	private final int COOLDOWN = 5;

	private final AI ai;
	private static final ImageIcon mooseImage = Util.loadImage("Moose.gif"); // TO
																				// CHANGE

	/**
	 * Create a new Moose at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the Moose will be created
	 */
	public Moose(AI mooseAI, Location initialLocation) {
		setEnergy(INITIAL_ENERGY);
		setMAX_ENERGY(MAX_ENERGY);
		setSTRENGTH(STRENGTH);
		setVIEW_RANGE(VIEW_RANGE);
		setINITIAL_VIEW_RANGE(VIEW_RANGE);
		setMIN_BREEDING_ENERGY(MIN_BREEDING_ENERGY);
		setCOOLDOWN(COOLDOWN);

		setLocation(initialLocation);
		this.healVirus();
		this.ai = mooseAI;
	}

	@Override
	public LivingItem breed() {
		Moose child = new Moose(ai, this.getLocation());
		child.setEnergy(this.getEnergy() / 2);
		setEnergy(this.getEnergy() / 2);
		return child;
	}

	@Override
	public Command getNextAction(World world) {
		Command nextAction = ai.getNextAction(world, this);
		setEnergy(this.getEnergy() - 1); // Loses 1 energy regardless of action.
		return nextAction;
	}

	@Override
	public String getName() {
		return "Moose";
	}

	@Override
	public ImageIcon getImage() {
		return mooseImage;
	}

}
