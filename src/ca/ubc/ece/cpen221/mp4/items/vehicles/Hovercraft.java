package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

/**
 * A Hovercraft is an {@link ArenaVehicle} that moves slowly and accelerates at a slow rate. However, it has a large strength
 * and is very bulky. It also requires an extremely low speed to turn.
 *
 */
public class Hovercraft extends AbstractArenaVehicle{
    
    private final int STRENGTH = 500;
    private final int INITIAL_COOLDOWN = 20;
    private final int TURNING_SPEED = 15;
    private final int SPEED_RATE = 2;

    private static final ImageIcon HovercraftImage = Util.loadImage("hovercraft.gif"); // TO

    
    /**
     * Create a new Hovercraft at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Hovercraft will be created
     */

    public Hovercraft(Location initialLocation) {
        setSTRENGTH(STRENGTH);        
        setINITIAL_COOLDOWN(INITIAL_COOLDOWN); //really slow initially
        setTURNING_SPEED(TURNING_SPEED); //needs slow speed to turn 
        setcurrentDirection(Util.getRandomDirection());
        setLocation(initialLocation);
        //rate at which the Hovercraft will accelerate/decelerate         
        setSPEED_RATE(SPEED_RATE); //speeds up and slows down slowly
    }        
    
    @Override
    public ImageIcon getImage() {
        return HovercraftImage;
    }

    @Override
    public String getName() {
        return "Hovercraft";
    }


}
