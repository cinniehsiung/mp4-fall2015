package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;


/**
 * The Penguin is an {@link ArenaAnimal} that eats {@link grass} and {@link Gnat}s. 
 * It has amazing resilience, and is able to evade most predators.
 * 
 *
 */
public class Penguin extends AbstractArenaAnimal{

    private static final ImageIcon PenguinImage = Util.loadImage("woman.gif"); //TO CHANGE

    /**
     * Create a new Penguin at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Penguin will be created
     */
    public Penguin(Location initialLocation) {
        setINITIAL_ENERGY(80);
        setEnergy(80);

        setMAX_ENERGY(150);
        setSTRENGTH(80);
        setVIEW_RANGE(8);
        setMIN_BREEDING_ENERGY(40);
        setCOOLDOWN(2);
        setLocation(initialLocation); 
                                        
    }
    
    @Override
    public LivingItem breed() {
        Penguin child = new Penguin(this.getLocation());
        child.setEnergy(this.getEnergy()/2);
        setEnergy(this.getEnergy()/2);
        return child; 
        
    }

    @Override
    public String getName() {
        return "Penguin";
    }
    
    @Override
    public Command getNextAction(World world){
      //if there is grass / gnats next to the Penguin, it will eat it
        for(Item currentItem : world.searchSurroundings(this)){
            if(currentItem.getPlantCalories() > 0 && currentItem.getLocation().getDistance(this.getLocation()) == 1){
              return new EatCommand(this, currentItem);
            }
            else if(currentItem.getName().equals("Gnat") && currentItem.getLocation().getDistance(this.getLocation()) == 1 && currentItem.getStrength() < this.getStrength()){
                return new EatCommand(this, currentItem);
            }
        }
        //if nothing to eat, but there is a predator near, run away
        for(Item currentItem : world.searchSurroundings(this)){
            if(currentItem.getName().equals("Fox") || currentItem.getName().equals("SabreToothTiger")){
                Direction runAway = Util.getDirectionTowards(currentItem.getLocation(), this.getLocation());
                if(Util.isLocationEmpty(world, new Location(this.getLocation(), runAway)))
                    return new MoveCommand(this, new Location(this.getLocation(), runAway));
            }
        }
        
        
        //if nothing to eat or escape from, and we have sufficient energy, breed
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
        return PenguinImage;
    }

}
