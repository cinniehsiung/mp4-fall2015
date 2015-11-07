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

public class Moose implements ArenaAnimal{
        
    private static final int INITIAL_ENERGY = 150;
    private static final int MAX_ENERGY = 200;
    private static final int STRENGTH = 100;
    private static final int VIEW_RANGE = 7;
    private static final int MIN_BREEDING_ENERGY = 120;
    private static final int COOLDOWN = 5;
    private boolean isDead;
    
    private static final ImageIcon mooseImage = Util.loadImage("hunter.gif"); //TO CHANGE
    
    private Location location;
    private int energy;
    
    /**
     * Create a new Moose at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Moose will be created
     */
    public Moose(Location initialLocation) {
        this.location = initialLocation;
        
        this.energy = INITIAL_ENERGY;
    }

    @Override
    public LivingItem breed() {
        Moose child = new Moose(location);
        child.energy = energy / 2;
        this.energy = energy / 2;
        return child;
    }

    @Override
    public void eat(Food food) {
        energy = Math.min(MAX_ENERGY, energy + food.getPlantCalories());
    }

    @Override
    public String getName() {
        return "Moose";
    }

    @Override
    public Command getNextAction(World world) {
        //if there is grass next to it, it will eat it - otherwise hurt all other weaker ArenaAnimals within 1 space of it
        for(Item currentItem : world.searchSurroundings(this)){
            if(currentItem.getPlantCalories() > 0 && currentItem.getLocation().getDistance(location) == 1){
              return new EatCommand(this, currentItem);
            } 
            else if(currentItem.getMeatCalories() > 0 && currentItem.getLocation().getDistance(location) == 1 && currentItem.getStrength() < STRENGTH){
                currentItem.loseEnergy(100);
            }
        }
        //if nothing to eat, and we have sufficient energy, breed
        if(energy > MIN_BREEDING_ENERGY){
            return new BreedCommand(this, Util.getRandomEmptyAdjacentLocation(world, location));
        }
        
        //otherwise move to a random location       
        Location targetLocation;
        do{
            targetLocation = Util.getRandomEmptyAdjacentLocation(world, location);
        }while(targetLocation.getDistance(location) > 1);  
        
        if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
            return new MoveCommand(this, targetLocation);
        }

        return new WaitCommand();        
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public void moveTo(Location targetLocation) {
        location = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return 1;
    }

    @Override
    public ImageIcon getImage() {
        return mooseImage;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public int getStrength() {
        return STRENGTH;
    }

    @Override
    public void loseEnergy(int energy) {
        this.energy = this.getEnergy() - energy;        
    }

    @Override
    public boolean isDead() {
        return energy <=0;
    }

    @Override
    public int getPlantCalories() {
        return 0;
    }

    @Override
    public int getMeatCalories() {
        return energy;
    }

    @Override
    public int getCoolDownPeriod() {
        return COOLDOWN;
    }

    @Override
    public int getMaxEnergy() {
        return MAX_ENERGY;
    }

    @Override
    public int getViewRange() {
        return VIEW_RANGE;
    }

    @Override
    public int getMinimumBreedingEnergy() {
        
        return MIN_BREEDING_ENERGY;
    }

}
