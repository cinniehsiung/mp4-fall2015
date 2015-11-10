package ca.ubc.ece.cpen221.mp4.items.viruses;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

/**
 * A {@link Cataracts} is an {@link ArenaVirus} that infects {@link ArenaAnimals} and causes them to be immobile for 3 times
 * that the ArenaAnimal calls the getNextAction method after the initial infection. Cataracts has a infection time of 3 steps, 
 * and a range of 10. 
 *
 */
public class Cataracts extends AbstractArenaVirus {
	private static final int CONTAGIOUS_RANGE = 10;
	private static final int INFECTION_TIME = 3;
	private static final ImageIcon IMAGE = Util.loadImage("cataracts.gif");
	private static final String VIRUS_NAME = "Cataracts";

	/**
	 * Create a new BlackPlague at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the BlackPlague will be created
	 */
	public Cataracts(Location initialLocation) {
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
