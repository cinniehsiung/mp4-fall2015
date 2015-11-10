package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.ai.SabreToothTigerAI;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

/**
 * The SabreToothTiger is an {@link ArenaAnimal} that eats all other
 * {@link ArenaAnimal}s
 */
public class SabreToothTiger extends InfectableArenaAnimal {

	// initial stats
	private final int INITIAL_ENERGY = 180;
	private final int MAX_ENERGY = 200;
	private final int STRENGTH = 150;
	private final int VIEW_RANGE = 2;
	private final int MIN_BREEDING_ENERGY = 150;
	private final int COOLDOWN = 2;

	private final AI ai;
	private static final ImageIcon STTImage = Util.loadImage("sabretoothtiger.gif");

	/**
	 * Create a new SabreToothTiger at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the STT will be created
	 */
	public SabreToothTiger(AI sabertoothtigerAI, Location initialLocation) {
		setEnergy(INITIAL_ENERGY);
		setMAX_ENERGY(MAX_ENERGY);
		setSTRENGTH(STRENGTH);
		setVIEW_RANGE(VIEW_RANGE);
		setINITIAL_VIEW_RANGE(VIEW_RANGE);
		setMIN_BREEDING_ENERGY(MIN_BREEDING_ENERGY);
		setCOOLDOWN(COOLDOWN);

		setLocation(initialLocation);
		this.healVirus();
		ai = sabertoothtigerAI;

	}

	@Override
	public LivingItem breed() {
		SabreToothTiger child = new SabreToothTiger(ai, this.getLocation());
		child.setEnergy(this.getEnergy() / 2);
		setEnergy(this.getEnergy() / 2);
		return child;
	}

	@Override
	public String getName() {
		return "SabreToothTiger";
	}

	@Override
	public ImageIcon getImage() {
		return STTImage;
	}

	@Override
	public Command getNextAction(World world) {
		Command nextAction = ai.getNextAction(world, this);
		setEnergy(this.getEnergy() - 1); // Loses 1 energy regardless of action.
		return nextAction;
	}

}
