package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.*;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {
	private int closest = 2; // max number; greater than fox's view range

	
	
	public FoxAI() {

	}

	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		// TODO: Change this. Implement your own AI to make decisions regarding
		// the next action.
	    
	    final int COOLDOWN = animal.getCoolDownPeriod();	        
	    final Location CURRENT_LOCATION = animal.getLocation();
	    final int ENERGY = animal.getEnergy();
	    final int STRENGTH = animal.getStrength();
	    final int MAX_ENERGY = animal.getMaxEnergy();
	    
	    Set<Item> surroundingItems = new HashSet<Item>();
	    
	    if(surroundingItems.isEmpty()){
	        //nothing around, either breed or move
	    }
	    else{
	        for(Item currentItem : surroundingItems){
	            //iterate through all surrounding items
	        }
	    }
	    
	    
		return new WaitCommand();
	}

}
