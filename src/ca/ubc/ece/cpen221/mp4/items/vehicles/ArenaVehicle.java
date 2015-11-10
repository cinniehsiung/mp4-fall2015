package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Actor;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.MoveableItem;


/**
 * Abstractions for ArenaVehicles, extending upon MovableItems.
 * Each ArenaVehicle can run over (destroy) everything with a lower strength but will crash (be destroyed) 
 * when running into something with a greater strength. Like real vehicles, your vehicles should build momentum when moving,
 * so it takes time for them to accelerate or brake or turn; they can change directions only at low speed. 
 * Note that the speed of a Vehicle is controlled by the cool-down period.
 *  
 *  
 * This interface adds the Turn and Accelerate methods specific to ArenaVehicles.
 */
public interface ArenaVehicle extends MoveableItem, Actor {
     
     /**
      * Method to turn an ArenaVehicle in a given Direction the given World.
      * 
      * @param Direction dir 
      *             Direction to turn in
      * @param World world
      *             World that the ArenaVehicle is in
      * @return Command
      *             Command to change the movement of the vehicle (it will either move in the given Direction dir (MoveCommand) 
      *             or crash into an Item if something is in its way (WaitCommand))
      */
     Command Turn(Direction dir, World world);
     
     /**
      * Method to accelerate an ArenaVehicle. It will continue going in the same direction as in the previous step.
      * 
      * @param World world 
      *             World that the ArenaVehicle is in
      * @return Command
      *             Command to continue moving at a faster speed (MoveCommand) if there is nothing to crash into,
      *             otherwise will crash into an Item and destroy it or be destroyed (WaitCommand)
      */
     Command Accelerate(World world);


}
