package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class LawnMower extends AbstractArenaVehicle {

    private static final ImageIcon LawnMowerImage = Util.loadImage("tiger.gif"); // TO

    
    /**
     * Create a new Hovercraft at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Hovercraft will be created
     */

    public LawnMower(Location initialLocation) {
        setSTRENGTH(2);  //very low strength b/c lawn mower       
        setVIEW_RANGE(5);
        setINITIAL_COOLDOWN(4); 
        setTURNING_SPEED(2); // can turn at fast speeds
        setcurrentDirection(Util.getRandomDirection());
        setLocation(initialLocation);

        setSPEED_RATE(5); //speeds up and slows down slowly
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
