package ca.ubc.ece.cpen221.mp4.items.viruses;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

/**
 * A {@link BlackPlague} is an {@link ArenaVirus} that damages infected {@link ArenaAnimals} (causes them to lose a 
 * specified amount of energy) every time the ArenaAnimal calls the getNextAction method. The infection lasts for 
 * 5 steps, and has a contagious range of 10.  
 * 
 */

public class BlackPlague extends AbstractArenaVirus {

	private static final int CONTAGIOUS_RANGE = 10;
	private static final int INFECTION_TIME = 5;
	private static final ImageIcon IMAGE = Util.loadImage("blackplague.gif");
	private static final String VIRUS_NAME = "BlackPlague";
	
	
	 /**
     * Create a new BlackPlague at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the BlackPlague will be created
     */
	public BlackPlague(Location initialLocation){
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
