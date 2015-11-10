package ca.ubc.ece.cpen221.mp4.items.viruses;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

/**
 * A {@link Panacea} is an {@link ArenaVirus} that infects {@link ArenaAnimals} by healing the ArenaAnimal to their MAX_ENERGY 
 * for 2 times that the ArenaAnimal calls the getNextAction method. Panacea has an infection time of 2 steps, and a 
 * contagious range of 1.
 *
 */

public class Panacea extends AbstractArenaVirus {
	private static final int CONTAGIOUS_RANGE = 1;
	private static final int INFECTION_TIME = 2;
	private static final ImageIcon IMAGE = Util.loadImage("panacea.gif");
	private static final String VIRUS_NAME = "Panacea";


	/**
	 * Create a new BlackPlague at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the BlackPlague will be created
	 */
	public Panacea(Location initialLocation) {
		setContagiousRange(CONTAGIOUS_RANGE);
		setInfectionTime(INFECTION_TIME);
		setLocation(initialLocation);
	}

	@Override
	public String getName() {
		return VIRUS_NAME;
	}

	public ImageIcon getImage() {
		return IMAGE;
	}
	
	@Override
	public void infectAnimal(InfectableArenaAnimal animal) {
		animal.infectAnimal(this);	
	}

}
