package model;

import java.awt.Point;
import java.util.Observable;

public class Wumpus extends Observable {

	private Room room;
	private Hunter hunter;
	private int hasShot = 0; //used to determine if the player has shot the arrow.
	
	public Wumpus() {
		
		room = new Room();
		hunter = new Hunter(room);
				
		setChanged();
		notifyObservers();
	}
	

	//returns the full room.
	public Room getRoom() {
		return room;
	}
	
	//For Testing
	public Hunter gethunter() {
		return hunter;
	}
	
	
	//returns if the hunter is safe.
	public boolean isSafe() {
		if(room.getTile(hunter.location.x, hunter.location.y).hiddenContent() != Content.GROUND)
			return false;
		return true;
	}
	
	//return if the player has ended the game.
	public boolean isRunning() {
		if(isDead() != 0 || hasShot == 1)
			return false;
		return true;
	}
	
	//Find if the player walked into Wumpus or Pit
	public int isDead() {
		if(hunter.location.equals(room.getWumpus()))
				return 1;
		for(Point p : room.getPits()){
			if(hunter.location.equals(p))
				return 2;
		}
		return 0;
	}
	
	
	//move the Hunter according to Direction.
	public void moveHunter(Direction direction){
		if(direction == Direction.NORTH) {
			room.getTile(hunter.location.x, hunter.location.y).setHasHunter(false);
			hunter.location.setLocation(Math.floorMod(hunter.location.x -1, 12), hunter.location.y);
			hunter.move(hunter.location, room);
		}
		else if(direction == Direction.SOUTH) {
			room.getTile(hunter.location.x, hunter.location.y).setHasHunter(false);
			hunter.location.setLocation(Math.floorMod(hunter.location.x  + 1, 12), hunter.location.y);
			hunter.move(hunter.location, room);
		}
		else if (direction == Direction.WEST) {
			room.getTile(hunter.location.x, hunter.location.y).setHasHunter(false);
			hunter.location.setLocation(hunter.location.x, Math.floorMod(hunter.location.y -1, 12));
			hunter.move(hunter.location, room);
		}
		else if (direction == Direction.EAST) {
			room.getTile(hunter.location.x, hunter.location.y).setHasHunter(false);
			hunter.location.setLocation(hunter.location.x, Math.floorMod(hunter.location.y +1, 12));
			hunter.move(hunter.location, room);
		}
		
		setChanged();
		notifyObservers();
	}
	
	//return true if the player wins, false if they shot themselves.
	public boolean shootArrow(Direction dir) {
		hasShot = 1;
		if(dir == Direction.NORTH) {
			if(hunter.location.y == room.getWumpus().y)
				return true;
		}
		
		if(dir == Direction.WEST){
			if(hunter.location.x == room.getWumpus().x)
				return true;
		}
		return false; 
	}
	
	
	//Initialize new game.
	public void startNewGame() {
		room = new Room();
		hunter = new Hunter(room);
		hasShot = 0;
		
		setChanged();
		notifyObservers("startNewGame()");
	}
	
	
	//Print the game board by chars.
	public String toString() {
		String str = "";	
		for(int r = 0; r < 12; r++) {
			for(int c = 0; c < 12; c++) {
				str += "" + room.getTile(r, c).getChar()+ " ";
			}
		if(r < 12) 
			str += "\n";
		}
		
		return str;
	}
}
