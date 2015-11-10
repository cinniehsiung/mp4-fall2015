package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

/**
 * A LawnMower is a {@link ArenaVehicle} that is only able to run over {@link grass}. It is able to move quickly, 
 * accelerate quickly, and turn at fast speeds, but is extremely frail. 
 *
 */
public class LawnMower extends AbstractArenaVehicle {

    private final int STRENGTH = 6;
    private final int INITIAL_COOLDOWN = 5;
    private final int TURNING_SPEED = 1;
    private final int SPEED_RATE = 4;
    
    private static final ImageIcon LawnMowerImage = Util.loadImage("lawnmower.gif"); // TO

    
    
    /**
     * Create a new LawnMower at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the LawnMower will be created
     */

    public LawnMower(Location initialLocation) {
        setSTRENGTH(STRENGTH);  //very low strength b/c lawn mower       
        setINITIAL_COOLDOWN(INITIAL_COOLDOWN); 
        setTURNING_SPEED(TURNING_SPEED); // can turn at fast speeds
        setcurrentDirection(Util.getRandomDirection());
        setLocation(initialLocation);
        //rate at which the LawnMower will accelerate/decelerate 

        setSPEED_RATE(SPEED_RATE); //speeds up and slows down slowly
    }        
    
    @Override
    public ImageIcon getImage() {
        return LawnMowerImage;
    }

    @Override
    public String getName() {
        return "LawnMower";
    }


}
