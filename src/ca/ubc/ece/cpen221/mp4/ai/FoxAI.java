package ca.ubc.ece.cpen221.mp4.ai;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;
import ca.ubc.ece.cpen221.mp4.items.animals.*;
import ca.ubc.ece.cpen221.mp4.staff.WorldImpl;
import ca.ubc.ece.cpen221.mp4.staff.WorldUI;

/**
 * Your Fox AI.
 */
public class FoxAI extends AbstractAI {
    private int closest = 2; // max number; greater than fox's view range

    public FoxAI() {

    }

    @Override
    public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
        // TODO: Change this. Implement your own AI to make decisions regarding
        // the next action.

        final Location CURRENT_LOCATION = animal.getLocation();
        final int ENERGY = animal.getEnergy();
        final int STRENGTH = animal.getStrength();
        final int MAX_ENERGY = animal.getMaxEnergy();
        final int VIEW_RANGE = animal.getViewRange();

        int count = 1;

        int xCoord = CURRENT_LOCATION.getX();
        int yCoord = CURRENT_LOCATION.getY();

        Location eastOf = new Location(CURRENT_LOCATION, Direction.EAST);
        Location westOf = new Location(CURRENT_LOCATION, Direction.WEST);
        Location northOf = new Location(CURRENT_LOCATION, Direction.NORTH);
        Location southOf = new Location(CURRENT_LOCATION, Direction.SOUTH);
        Location currentItemLocation;


        Set<Item> surroundingItems = new TreeSet<Item>();
        surroundingItems = world.searchSurroundings(animal);

        // if there is nothing around, either breed or move
        if (surroundingItems.isEmpty()) {

            // breed if energy is greater than or equal to 100 (starting energy)
            if (ENERGY >= 60) {
                return new BreedCommand(animal, Util.getRandomEmptyAdjacentLocation((World)world, CURRENT_LOCATION));
            } else {
                moveFromEdge(xCoord, yCoord, world, animal, CURRENT_LOCATION);
            }
        }

        else {
            outer: while (count <= VIEW_RANGE) {
                
                if(count > 3 && ENERGY > 70){
                    LivingItem baby = animal.breed();
                    baby.moveTo(westOf);
                    ((World) world).addItem(baby);
                    ((World) world).addActor(baby);
                    
                    break outer;                                       
                    
                }
                else{
                    animal.moveTo(Util.getRandomEmptyAdjacentLocation((World)world, CURRENT_LOCATION));
                }
                
                for (Item currentItem : world.searchSurroundings(animal)) {
                    if (currentItem.getLocation().getDistance(CURRENT_LOCATION) == count) {
                        // if item is a rabbit,
                        if (currentItem.getName().equals("Rabbit")) {
                            if (count == 1) {
                                
                                animal.eat(currentItem);
                                currentItem.loseEnergy(100);
                                break outer;
                            } else {
                                currentItemLocation = currentItem.getLocation();
                                if (currentItemLocation.getX() > xCoord && isLocationEmpty(world, animal, eastOf)) {
                                    animal.moveTo(eastOf);
                                    break outer;
                                } else
                                    if (currentItemLocation.getX() < xCoord && isLocationEmpty(world, animal, westOf)) {
                                    animal.moveTo(westOf);
                                    break outer;
                                } else if (currentItemLocation.getY() > yCoord
                                        && isLocationEmpty(world, animal, southOf)) {
                                    animal.moveTo(southOf);
                                    break outer;
                                } else if (currentItemLocation.getY() < yCoord
                                        && isLocationEmpty(world, animal, northOf)) {
                                    animal.moveTo(northOf);
                                    break outer;
                                }
                            }

                        }
                        // iterate through all surrounding items
                    }
                }
                count++;
            }
        }

        return new WaitCommand();
    }

    /**
     * Helper method to move the animal from the edge of the arena, based on its
     * current x and y coordinates
     * 
     * @param x
     *            - coordinate of the animal's current location
     * @param y
     *            - coordinate of the animal's current location
     * 
     *            currently, always moves east/west before north/south***
     */

    private void moveFromEdge(int x, int y, ArenaWorld world, ArenaAnimal animal, Location currentLocation) {
        // if fox is at left side of the arena, and is further west than north,
        // move east
        if (x < y && x <= 2) {
            animal.moveTo(new Location(currentLocation, Direction.EAST));
        }
        // if fox is at right side of arena, and is further east than south,
        // move west
        else if (x > y && x >= (world.getWidth() - 2)) {
            animal.moveTo(new Location(currentLocation, Direction.WEST));
        }
        // if fox is at top of arena, move south
        else if (y <= 2) {
            animal.moveTo(new Location(currentLocation, Direction.SOUTH));
        }
        // if fox is at bottom of arena, move north
        else if (y >= (world.getHeight() - 2)) {
            animal.moveTo(new Location(currentLocation, Direction.NORTH));
        }
        // move in random direction
        else {
            animal.moveTo(new Location(currentLocation, Util.getRandomDirection()));
        }
    }
}
