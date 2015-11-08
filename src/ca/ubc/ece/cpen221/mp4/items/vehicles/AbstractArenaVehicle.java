package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;
import ca.ubc.ece.cpen221.mp4.items.vehicles.ArenaVehicle;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Gnat;

public abstract class AbstractArenaVehicle implements ArenaVehicle{ //abstract class for implementing all vehicles
    
    private int STRENGTH;
    private int VIEW_RANGE;
    private int INITIAL_COOLDOWN;
    private int currentCooldown;
    private ImageIcon image;
    private boolean isDead;
    private int TURNING_SPEED;
    private int energy;
    
    private Direction currentDirection;    
    private Location location;
    
    
    protected void setSTRENGTH(int i) {
        this.STRENGTH = i;
    }
    protected void setVIEW_RANGE(int i) {
        this.VIEW_RANGE = i;
    }
    protected void setLocation(Location loc) {
        this.location = loc;
    }
    protected void setINITIAL_COOLDOWN(int i){
        this.INITIAL_COOLDOWN = i;
    }
    protected void setTURNING_SPEED(int i){
        this.TURNING_SPEED = i;
    }
    protected void setcurrentDirection(Direction dir){
        this.currentDirection = dir;
    }
    protected void setENERGY(int i){
        this.energy = i;
    }

    
    @Override
    public void moveTo(Location targetLocation){
        this.location = targetLocation;
    }

    @Override
    public int getMovingRange(){
        return 1; //can only move one space each step
    }
    
    public Command getNextAction(World world) {
        if(Math.random() > 0.5){
        
        return Accelerate(world);
        }
        else{
            return Turn(Util.getRandomDirection(), world);
        }
    }
    
    
    public Command Accelerate(World world){
        Location goTowards = new Location(this.location, currentDirection);
        
        //if we're going towards an empty spot, go forward and increase speed (cooldown)
        if(Util.isLocationEmpty(world, goTowards)){
            currentCooldown++;
            return new MoveCommand(this, goTowards);
        }
        //if spot is occupied, crash
        else{
          //search all items immediately surrounding the vehicle
            for(Item currentItem : world.searchSurroundings(location, 1)){
                
                //if the item is in the location we are trying to go towards
                if(currentItem.getLocation().equals(goTowards)){
                    
                    //if we have more strength, destroy the Item and move into its space
                    if(STRENGTH > currentItem.getStrength()){
                        currentItem.loseEnergy(Integer.MAX_VALUE);
                        currentCooldown++;
                        return new MoveCommand(this, goTowards);
                    }
                    
                    //if not, we are destroyed
                    else{
                        this.isDead = true;
                        this.energy = 0;
                        return new WaitCommand();
                    }
                }
            }
        }
        
        return new WaitCommand();
    }

    @Override
    public int getCoolDownPeriod(){
        return currentCooldown;
        
    }
    
    
    public Command Turn(Direction dir, World world){
        
        //if our current cooldown (speed) is less than the required turning speed, turn
        if(currentCooldown < TURNING_SPEED && Util.isLocationEmpty(world, new Location(location, dir))){
            this.currentDirection = dir;
            return new MoveCommand(this,new Location(location, dir));
        }
        //if there is something in the way, we will crash into it
        else if(currentCooldown < TURNING_SPEED){
            
            //search all items immediately surrounding the vehicle
            for(Item currentItem : world.searchSurroundings(location, 1)){
                
                //check for the one item in the direction we are trying to go towards
                if(Util.getDirectionTowards(location, currentItem.getLocation()).equals(dir)){
                    
                    //if we have more strength, destroy the Item and move into its space
                    if(STRENGTH > currentItem.getStrength()){
                        currentItem.loseEnergy(Integer.MAX_VALUE);
                        this.currentDirection = dir;
                        return new MoveCommand(this, new Location(location, dir));
                    }
                    
                    //if not, we are destroyed
                    else{
                        this.isDead = true;
                        this.energy = 0;
                        return new WaitCommand();
                    }
                }
            }
        }
               
        //if not, then slow down (reduce cooldown)
        else{
            currentCooldown--;
            return new WaitCommand();
        }
        
        return new WaitCommand();
    }
    
    
    @Override
    public abstract ImageIcon getImage();

    @Override
    public abstract String getName();

    @Override
    public Location getLocation(){
        return location;
        
    }

    @Override
    public int getStrength(){
        return STRENGTH;
        
    }
    
    @Override
    
    public boolean isDead(){
        return isDead;
        
    }

}
