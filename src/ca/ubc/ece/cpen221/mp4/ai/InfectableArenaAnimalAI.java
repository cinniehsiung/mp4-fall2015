package ca.ubc.ece.cpen221.mp4.ai;

import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.InfectableArenaAnimal;

public abstract class InfectableArenaAnimalAI extends ArenaAnimalAI {
	public InfectableArenaAnimalAI() {
		// TODO Auto-generated constructor stub
	}

	final private int BLACK_PLAGUE_DAMAGE = 1000;
	final private int CATARACTS_REACH = 10; // (int) Math.random()*10;

	@Override
	public abstract Command getNextAction(ArenaWorld world, ArenaAnimal animal);

	public void doInfectedActions(ArenaWorld world, InfectableArenaAnimal animal) {
		String virusStatus = animal.getVirus();

		if ("BlackPlague".equals(virusStatus)) {
			animal.loseEnergy(Integer.MAX_VALUE);
		}

		if ("Cataracts".equals(virusStatus)) {
			animal.setVIEW_RANGE(CATARACTS_REACH);
		}
		
		if ("Panacea".equals(virusStatus)) {
			animal.setEnergy(animal.getMaxEnergy());
		}
		
		animal.decreaseVirusLifeSpan();
	}
}
