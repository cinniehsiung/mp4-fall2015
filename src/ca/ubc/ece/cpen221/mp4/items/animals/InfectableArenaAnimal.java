package ca.ubc.ece.cpen221.mp4.items.animals;

import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.viruses.ArenaVirus;

public abstract class InfectableArenaAnimal extends AbstractArenaAnimal {
	private String virusStatus;
	private int virusLifeSpan;

	/**
	 * Method to infect this animal with the given arena virus
	 * 
	 * @param virus
	 *            the virus to infect the animal with
	 */
	public void infectAnimal(ArenaVirus virus) {
		this.virusStatus = virus.getName();
		this.virusLifeSpan = virus.getInfectionTime();
	}

	/**
	 * Method to return the name of the virus this animal is infected with
	 * 
	 * @return virusName the name of the virus
	 */
	public String getVirus() {
		return virusStatus;
	}

	/**
	 * Method to return the remaining lifespan of the virus the animal is
	 * infected with.
	 * 
	 * If the lifespan is 0, the animal should be healed/not have a virus
	 * 
	 * @return viruslifespan the remaining lifespan of the virus
	 */
	public int getVirusLifeSpan() {
		return virusLifeSpan;
	}

	/**
	 * Method to decrease the virus lifespan of this animal by 1. If the virus
	 * lifespan is less than 0, this method heals the animal and removes the
	 * virus.
	 */
	public void decreaseVirusLifeSpan() {
		virusLifeSpan--;

		if (virusLifeSpan <= 0) {
			healVirus();
		}
	}

	/**
	 * Method to heal this animal of any virus. The method should change any
	 * altered stats back to their initial numbers.
	 */
	public void healVirus() {
		this.virusStatus = "";
		this.virusLifeSpan = 0;
		this.setVIEW_RANGE(this.getInitialViewRange());
	}

	/**
	 * Method to check whether this animal is infected with a virus.
	 * 
	 * @return true if the animal is infected, false otherwise
	 */
	public boolean isInfected() {
		return !"".equals(virusStatus);
	}

	@Override
	public abstract LivingItem breed();

	@Override
	public abstract String getName();
}
