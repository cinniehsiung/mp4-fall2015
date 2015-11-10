package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.ai.AI;
import ca.ubc.ece.cpen221.mp4.items.viruses.AbstractArenaVirus;

public class Helicopter extends AbstractArenaVehicle {

    private static final ImageIcon HelicopterImage = Util.loadImage("helicopter.gif"); // TO

    
    /**
     * Create a new Helicopter at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Helicopter will be created
     */

    public Helicopter(Location initialLocation) {
        setSTRENGTH(80);        
        setVIEW_RANGE(8);
        setINITIAL_COOLDOWN(5);
        setTURNING_SPEED(5);
        setcurrentDirection(Util.getRandomDirection());
        setLocation(initialLocation);

        setSPEED_RATE(2);
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
