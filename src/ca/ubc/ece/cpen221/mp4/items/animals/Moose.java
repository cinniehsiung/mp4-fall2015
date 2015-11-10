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
        
    private static final ImageIcon mooseImage = Util.loadImage("hunter.gif"); //TO CHANGE
    
    
    /**
     * Create a new Moose at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Moose will be created
     */
    public Moose(Location initialLocation) {
        setINITIAL_ENERGY(150);
        setEnergy(150);
        
        setMAX_ENERGY(200);
        setSTRENGTH(180);
        setVIEW_RANGE(7);
        setMIN_BREEDING_ENERGY(120);
        setCOOLDOWN(5);

        setLocation(initialLocation);
        
        
    }

    @Override
    public LivingItem breed() {
        Moose child = new Moose(this.getLocation());
        child.setEnergy(this.getEnergy()/ 2);
        setEnergy(this.getEnergy() / 2);
        return child;
    }


    @Override
    public String getName() {
        return "Moose";
    }

    @Override
    public Command getNextAction(World world) {
        //if there is grass next to it, it will eat it - otherwise hurt all other weaker ArenaAnimals within 1 space of it
        for(Item currentItem : world.searchSurroundings(this)){
            if(currentItem.getPlantCalories() > 0 && currentItem.getLocation().getDistance(this.getLocation()) == 1){
              return new EatCommand(this, currentItem);
            } 
            else if(currentItem.getMeatCalories() > 0 && currentItem.getLocation().getDistance(this.getLocation()) == 1 && currentItem.getStrength() < this.getStrength()){
                currentItem.loseEnergy(100);
            }
        }
        //if nothing to eat, and we have sufficient energy, breed
        if(this.getEnergy() > this.getMinimumBreedingEnergy()){
            return new BreedCommand(this, Util.getRandomEmptyAdjacentLocation(world, this.getLocation()));
        }
        
        int count = 0;
        //otherwise move to a random location       
        Location targetLocation;
        do{
            targetLocation = Util.getRandomEmptyAdjacentLocation(world, this.getLocation());
            if(targetLocation == null){
                return new WaitCommand();
            }
            count++;
        }while(targetLocation.getDistance(this.getLocation()) > 1 && count < 4);  
        
        if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation) && count < 4) {
            return new MoveCommand(this, targetLocation);
        }

        return new WaitCommand();        
    }


    @Override
    public ImageIcon getImage() {
        return mooseImage;
    }

}
