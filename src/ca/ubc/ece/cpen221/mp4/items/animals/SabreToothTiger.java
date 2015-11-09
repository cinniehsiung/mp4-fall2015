package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

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
 * The SabreToothTiger is an {@link ArenaAnimal} that eats all other {@link ArenaAnimal}s
 */
public class SabreToothTiger extends AbstractArenaAnimal {

    private static final ImageIcon STTImage = Util.loadImage("trucks.gif"); //TO CHANGE

    /**
     * Create a new SabreToothTiger at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the STT will be created
     */
    public SabreToothTiger(Location initialLocation) {
        setINITIAL_ENERGY(150);
        setEnergy(150);

        setMAX_ENERGY(200);
        setSTRENGTH(150);
        setVIEW_RANGE(2);
        setMIN_BREEDING_ENERGY(150);
        setCOOLDOWN(2);
        setLocation(initialLocation); 
                                        
    }
    
    @Override
    public Command getNextAction(World world) {
        //if there is any ArenaAnimal next to the STT, it will eat it
        for(Item currentItem : world.searchSurroundings(this)){
            if(currentItem.getMeatCalories() > 0 && currentItem.getLocation().getDistance(this.getLocation()) == 1 && currentItem.getStrength() < this.getStrength()){
              return new EatCommand(this, currentItem);
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
        
        if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)&& count < 4) {
            return new MoveCommand(this, targetLocation);
        }

        return new WaitCommand(); 
        
        
    }
    
    @Override
    public LivingItem breed() {
        
        SabreToothTiger child = new SabreToothTiger(this.getLocation());
        child.setEnergy(this.getEnergy() / 2);
        setEnergy(this.getEnergy() / 2);
        return child;
    }

    @Override
    public String getName() {
        return "SabreToothTiger";
    }

    @Override
    public ImageIcon getImage() {
        return STTImage;
    }

  

}
