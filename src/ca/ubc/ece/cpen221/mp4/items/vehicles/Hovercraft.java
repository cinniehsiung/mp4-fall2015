package ca.ubc.ece.cpen221.mp4.items.vehicles;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;

public class Hovercraft extends AbstractArenaVehicle{

    private static final ImageIcon HovercraftImage = Util.loadImage("hovercraft.gif"); // TO

    
    /**
     * Create a new Hovercraft at <code>initialLocation</code>. The
     * <code>initialLocation</code> must be valid and empty.
     *
     * @param initialLocation
     *            the location where the Hovercraft will be created
     */

    public Hovercraft(Location initialLocation) {
        setSTRENGTH(100);        
        setVIEW_RANGE(5);
        setINITIAL_COOLDOWN(20); //really slow initially
        setTURNING_SPEED(15); //needs slow speed to turn 
        setcurrentDirection(Util.getRandomDirection());
        setLocation(initialLocation);

        setSPEED_RATE(1); //speeds up and slows down slowly
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
