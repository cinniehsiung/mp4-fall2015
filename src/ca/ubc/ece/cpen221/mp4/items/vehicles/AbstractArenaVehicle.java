package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.vehicles.ArenaVehicle;

public abstract class AbstractArenaVehicle implements ArenaVehicle { // abstract
                                                                     // class
                                                                     // for all
                                                                     // ArenaVehicles

    // Constants for each ArenaVehicle
    private int STRENGTH;
    private int VIEW_RANGE;
    private int INITIAL_COOLDOWN;
    private boolean isDead;
    private int TURNING_SPEED;
    private int SPEED_RATE;

    private int currentCooldown;
    private Direction currentDirection;
    private Location location;

    // Protected methods for setting the constants for each ArenaVehicle
    /**
     * Method to set the strength of an ArenaVehicle.
     * @param int i 
     *          Strength of the ArenaVehicle
     */
    protected void setSTRENGTH(int i) {
        this.STRENGTH = i;
    }
    
    /**
     * Method to set the view range of an ArenaVehicle.
     * 
     * @param int i
     *          View range of the ArenaVehicle.
     */
    protected void setVIEW_RANGE(int i) {
        this.VIEW_RANGE = i;
    }

    /**
     * Method to set the Location of an ArenaVehicle.
     * 
     * @param Location loc
     *          Location of the ArenaVehicle.
     */
    protected void setLocation(Location loc) {
        this.location = loc;
    }

    /**
     * Method to set the initial cooldown (initial speed) of the ArenaVehicle.
     * 
     * @param int i
     *          Initial cooldown of the ArenaVehicle.
     */
    protected void setINITIAL_COOLDOWN(int i) {
        this.INITIAL_COOLDOWN = i;
    }

    /**
     * Method to set the turning speed (constant) of the ArenaVehicle. For an ArenaVehicle to turn, its current speed has to be lower than
     * its turning speed (so its currentCoolDown has to be higher than the TURNING_SPEED). 
     * @param int i 
     *          Turning speed of the ArenaVehicle.
     * 
     */
    protected void setTURNING_SPEED(int i) {
        this.TURNING_SPEED = i;
    }
    
    /**
     * Method to set the current direction the given ArenaVehicle is going towards.
     * 
     * @param Direction dir
     *          Direction that the ArenaVehicle is heading towards.
     */
    protected void setcurrentDirection(Direction dir) {
        this.currentDirection = dir;
    }
    
    /**
     * Method to set the acceleration of the given ArenaVehicle (the rate at which its current speed (or cooldown)) changes.
     * @param int i
     *          The rate at which the speed of the ArenaVehicle will change.
     */
    protected void setSPEED_RATE(int i) {
        this.SPEED_RATE = i;
    }

    @Override
    public void moveTo(Location targetLocation) {
        this.location = targetLocation;
    }

    @Override
    public int getMovingRange() {
        return 1; // can only move one space each step
    }

    /**
     * Method to control the next action of each ArenaVehicle. If the
     * ArenaVehicle is at an edge of the World, it will turn. Otherwise, it will
     * continue in the same direction (currentDirection).
     * 
     * @param World
     *            world The World that the ArenaVehicle is in.
     * 
     * @return Command Command for the ArenaVehicle to control its next action
     *         (eg. MoveCommand, or WaitCommand)
     */
    @Override
    public Command getNextAction(World world) {
        Direction randomDir;
        // if we're at the edge of the world, turn
        if (location.getX() == 0 || location.getX() == world.getWidth() - 1 || location.getY() == 0
                || location.getY() == world.getHeight() - 1) {

            // loop through random directions until it chooses one that isn't
            // the current direction, then turn in new direction
            do {
                randomDir = Util.getRandomDirection();
            } while (randomDir.equals(currentDirection));

            return Turn(randomDir, world);
        }

        // otherwise keep going straight
        else {
            return Accelerate(world);
        }
    }

    @Override
    public Command Accelerate(World world) {
        Location goTowards = new Location(this.location, currentDirection);

        // if we're going towards an empty spot, go forward and increase speed
        // (decrease cooldown)
        if (Util.isLocationEmpty(world, goTowards)) {
            // avoid making cooldown 0
            if (currentCooldown > 1) {
                // decrease cooldown to speed up car
                if (currentCooldown - SPEED_RATE < 0) {
                    currentCooldown = 1;
                } else {
                    currentCooldown = currentCooldown - SPEED_RATE;
                }
            }
            return new MoveCommand(this, goTowards);
        }
        // if spot is occupied, crash
        else {
            // search all items immediately surrounding the vehicle
            for (Item currentItem : world.searchSurroundings(location, 1)) {

                // if the item is in the location we are trying to go towards
                if (currentItem.getLocation().equals(goTowards)) {

                    // if we have more strength, destroy the Item and move into
                    // its space
                    if (STRENGTH > currentItem.getStrength()) {
                        currentItem.loseEnergy(Integer.MAX_VALUE);
                        // slow down after crashing
                        currentCooldown = currentCooldown + SPEED_RATE;
                        return new WaitCommand();
                    }

                    // if not, we are destroyed
                    else {
                        this.isDead = true;
                        return new WaitCommand();
                    }
                }
            }
        }

        return new WaitCommand();
    }

    @Override
    public int getCoolDownPeriod() {
        return currentCooldown;

    }

    @Override
    public Command Turn(Direction dir, World world) {

        // if our current speed is less than the required turning speed, we can
        // turn in the given direction
        if (currentCooldown > TURNING_SPEED && Util.isLocationEmpty(world, new Location(location, dir))) {
            this.currentDirection = dir;
            return new MoveCommand(this, new Location(location, dir));
        }

        // if there is an Item in the way, we will crash into it
        else if (currentCooldown > TURNING_SPEED) {

            // search all Items immediately surrounding the vehicle
            for (Item currentItem : world.searchSurroundings(location, 1)) {
                // check for the one Item in the direction we are trying to go
                // towards
                if (Util.getDirectionTowards(location, currentItem.getLocation()).equals(dir)) {

                    // if we have more strength, destroy the Item and wait for
                    // the next step to move into its space
                    if (STRENGTH > currentItem.getStrength()) {
                        currentItem.loseEnergy(Integer.MAX_VALUE);
                        this.currentDirection = dir;
                        return new WaitCommand();
                    }

                    // if we do not have enough strength, we are destroyed
                    else {
                        this.isDead = true;
                        return new WaitCommand();
                    }
                }
            }
        }

        // if the ArenaVehicle is going too quickly to turn (currentCooldown <
        // TURNING_SPEED), then slow down
        else {
            currentCooldown = currentCooldown + SPEED_RATE;
            return new WaitCommand();
        }

        return new WaitCommand();
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
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void loseEnergy(int energy) {
        // does nothing, a vehicle cannot lose energy
    }

    @Override
    public int getPlantCalories() {
        // vehicles are not plants
        return 0;
    }

    @Override
    public int getMeatCalories() {
        // vehicles are not animals
        return 0;
    }
    
    @Override
    public abstract ImageIcon getImage();

    @Override
    public abstract String getName();

}
