package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

/**
 * A Helicopter is an {@link ArenaVehicle} that is able to move quickly and turn at high speeds. It also accelerates at a moderate
 * rate (coolDown changes by +/- 2)  
 *
 */
public class Helicopter extends AbstractArenaVehicle {
    
    private final int STRENGTH = 80;
    private final int INITIAL_COOLDOWN = 5;
    private final int TURNING_SPEED = 5;
    private final int SPEED_RATE = 2;

   
    
    
    private static final ImageIcon HelicopterImage = Util.loadImage("helicopter.gif"); // TO

    
    /**
     * Create a new Helicopter at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Helicopter will be created
     */

    public Helicopter(Location initialLocation) {
        setSTRENGTH(STRENGTH);         
        setINITIAL_COOLDOWN(INITIAL_COOLDOWN);
        setTURNING_SPEED(TURNING_SPEED);
        setcurrentDirection(Util.getRandomDirection());
        setLocation(initialLocation);
        //rate at which the Helicopter will accelerate/decelerate 
        setSPEED_RATE(SPEED_RATE);
    }        
    
    @Override
    public ImageIcon getImage() {
        return HelicopterImage;
    }

    @Override
    public String getName() {
        return "Helicopter";
    }

  
}
