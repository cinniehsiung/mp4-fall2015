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
	private final AI ai;
	private static final ImageIcon STTImage = Util.loadImage("sabretoothtiger.gif"); // TO
																			// CHANGE

	/**
	 * Create a new SabreToothTiger at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the STT will be created
	 */
	public SabreToothTiger(AI sabertoothtigerAI, Location initialLocation) {
		setINITIAL_ENERGY(150);
		setEnergy(150);

		setMAX_ENERGY(200);
		setSTRENGTH(150);
		setVIEW_RANGE(2);
		setMIN_BREEDING_ENERGY(150);
		setCOOLDOWN(2);
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
