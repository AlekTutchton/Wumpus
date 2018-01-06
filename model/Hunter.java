package model;

import java.awt.Point;
import java.util.Random;

/**
 * 
 * @author alektutchton
 *This method will model the Hunter player in the Wumpus map
 *It will find an appropriate staring place as well as move the 
 *Hunter.
 */
public class Hunter {
	Point location;
	
	public Hunter(Room room) {
		Random rand = new Random();
		location = new Point();
		
		while(true) {
			location.setLocation(rand.nextInt(12), rand.nextInt(12));
			if(room.getTile(location.x, location.y).getContent() != Content.GROUND)
				continue;
			else {
				room.getTile(location.x, location.y).setHasHunter(true);
				room.getTile(location.x, location.y).setVisited();
				break;
			}
		}
	}
	
	
	public void move(Point newLocation, Room room) {
		this.location = newLocation;
		room.getTile(location.x, location.y).setHasHunter(true);
		room.getTile(location.x, location.y).setVisited();
		
	}
	
	//for Testing
	public Point getLocation() {
		return location;
	}
}
