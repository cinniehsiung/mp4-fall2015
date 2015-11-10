package ca.ubc.ece.cpen221.mp4.items.animals;

import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.viruses.ArenaVirus;

public abstract class InfectableArenaAnimal extends AbstractArenaAnimal {
	private String virusStatus = "";
	private int virusLifeSpan = 0;

	/**
	 * 
	 * @param virusName
	 */
	public void infectAnimal(ArenaVirus virus) {
		setVirusStatus(virus);
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

	public void setVirusLifeSpan(int lifespan) {
		virusLifeSpan = lifespan;
	}

	public void decreaseVirusLifeSpan() {
		virusLifeSpan--;

		if (virusLifeSpan <= 0) {
			healVirus();
		}

	}

	public boolean isInfected() {
		return "".equals(virusStatus);
	}

	public void setVirusStatus(ArenaVirus virus) {
		this.virusStatus = virus.getName();
		this.virusLifeSpan = virus.getInfectionTime();
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
