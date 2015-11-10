package ca.ubc.ece.cpen221.mp4.items.viruses;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

public class Panacea extends AbstractArenaVirus {
	private static final int CONTAGIOUS_RANGE = 15;
	private static final int INFECTION_TIME = 5;
	private static final ImageIcon IMAGE = Util.loadImage("unknown.gif");
	private static final String VIRUS_NAME = "Panacea";

	// private final AI ai;
	private Location location;
	private int energy = 1;

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
