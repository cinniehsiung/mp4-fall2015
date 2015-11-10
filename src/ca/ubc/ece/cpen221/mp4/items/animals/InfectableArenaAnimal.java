package ca.ubc.ece.cpen221.mp4.items.animals;

import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.viruses.ArenaVirus;

public abstract class InfectableArenaAnimal extends AbstractArenaAnimal {
	private String virusStatus;
	private int virusLifeSpan;

	/**
	 * 
	 * @param virusName
	 */
	public void infectAnimal(ArenaVirus virus) {
		this.virusStatus = virus.getName();
		this.virusLifeSpan = virus.getInfectionTime();
	}

	/**
	 * 
	 * @return String virus name
	 */
	public String getVirus() {
		return virusStatus;
	}

	public int getVirusLifeSpan() {
		return virusLifeSpan;
	}

	public void decreaseVirusLifeSpan() {
		virusLifeSpan--;

		if (virusLifeSpan <= 0) {
			healVirus();
		}
	}

	public boolean isInfected() {
		return !"".equals(virusStatus);
	}

	public void healVirus() {
		this.virusStatus = "";
		this.virusLifeSpan = 0;
	}

	@Override
	public abstract LivingItem breed();

	@Override
	public abstract String getName();
}
