package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

/**
 * The Moose is an {@link ArenaAnimal} that only eats Grass. However, it is capable of damaging smaller animals (such as {@link Fox}s 
 * {@link Rabbit}s {@link Gnat}s and {@link Penguin}s)
 */

public class Moose extends InfectableArenaAnimal{
	private final AI ai;
    private static final ImageIcon mooseImage = Util.loadImage("hunter.gif"); //TO CHANGE
    
    
    /**
     * Create a new Moose at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Moose will be created
     */
    public Moose(AI mooseAI, Location initialLocation) {
        setINITIAL_ENERGY(150);
        setEnergy(150);
        
        setMAX_ENERGY(200);
        setSTRENGTH(180);
        setVIEW_RANGE(7);
        setMIN_BREEDING_ENERGY(120);
        setCOOLDOWN(5);

        setLocation(initialLocation);
        
        this.healVirus();
        this.ai = mooseAI;
    }

    @Override
    public LivingItem breed() {
        Moose child = new Moose(ai, this.getLocation());
        child.setEnergy(this.getEnergy()/ 2);
        setEnergy(this.getEnergy() / 2);
        return child;
    }
    
    @Override
	public Command getNextAction(World world) {
		Command nextAction = ai.getNextAction(world, this);
		setEnergy(this.getEnergy() - 1); // Loses 1 energy regardless of action.
		return nextAction;
	}

    @Override
    public String getName() {
        return "Moose";
    }

    @Override
    public ImageIcon getImage() {
        return mooseImage;
    }

}
