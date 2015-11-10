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
 * The Penguin is an {@link ArenaAnimal} that eats {@link grass} and
 * {@link Gnat}s. It has amazing resilience, and is able to evade most
 * predators.
 * 
 *
 */
public class Penguin extends InfectableArenaAnimal {

	// initial fields
	private final int INITIAL_ENERGY = 80;
	private final int MAX_ENERGY = 100;
	private final int STRENGTH = 80;
	private final int VIEW_RANGE = 4;
	private final int MIN_BREEDING_ENERGY = 40;
	private final int COOLDOWN = 2;

	private final AI ai;
	private static final ImageIcon PenguinImage = Util.loadImage("penguin.gif"); // TO
																					// CHANGE

	/**
	 * Create a new Penguin at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the Penguin will be created
	 */

	public Penguin(AI penguinAI, Location initialLocation) {
		setEnergy(INITIAL_ENERGY);
		setMAX_ENERGY(MAX_ENERGY);
		setSTRENGTH(STRENGTH);
		setVIEW_RANGE(VIEW_RANGE);
		setINITIAL_VIEW_RANGE(VIEW_RANGE);
		setMIN_BREEDING_ENERGY(MIN_BREEDING_ENERGY);
		setCOOLDOWN(COOLDOWN);

		setLocation(initialLocation);
		this.ai = penguinAI;
		this.healVirus();
	}

	@Override
	public LivingItem breed() {
		Penguin child = new Penguin(ai, this.getLocation());
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
		return "Penguin";
	}

	@Override
	public ImageIcon getImage() {
		return PenguinImage;
	}

}
