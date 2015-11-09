package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

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
 *  *
 */
public interface ArenaVehicle extends MoveableItem{

   

    /**
     * Method for getting the cooldown period of a vehicle (represents its speed)
     * @return the current cool-down period
     */
    int getCoolDownPeriod();

    Command getNextAction(World world);
    
    
}
