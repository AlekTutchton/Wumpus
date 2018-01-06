package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


/**
 * 
 * @author alektutchton
 *
 *This model build the Wumpus Room with the random pits and Wumpus.
 */
public class Room {
	private Tile[][] room;
	private ArrayList<Point> pits;
	private Point wumpus;
	
	public Room() {
		
		room = new Tile[12][12];
		pits = new ArrayList<Point>();
		
		
		//fill the room ground, wumpus, blood, pits, slimes, goops.
		fillGround();
		setWumpus();
		setPits();
		fillSlimesAndGoop();
	}

	//puts the Wumpus in a random location on the map and adds the 
	//bloods to the map.
	private void setWumpus() {
		Random rand = new Random();
		wumpus = new Point(rand.nextInt(12), rand.nextInt(12));
		room[wumpus.x][wumpus.y].setContent(Content.WUMPUS);
		
		//fill the bloods with wrap around.
		int blood_x = 0;
		int blood_y = 0;
		for(int r = -2; r <= 2; r++) {
			for(int c = -2; c <= 2; c++) {
				if(r == 0 && c == 0)
					continue;
				if(Math.abs(r) + Math.abs(c) <= 2) {
					blood_x = Math.floorMod((r + wumpus.x), 12);
					blood_y = Math.floorMod((c + wumpus.y), 12);
					room[blood_x][blood_y].setContent(Content.BLOOD);
				}
				
			}
		}
	}

	//Determines the number of Pits, places them randomly.
	private void setPits() {
		Random rand = new Random(2);
		int pitNum = rand.nextInt(2) + 3;
		Point nextPnt;
		
		for(int i = 0; i < pitNum; i++) {
			nextPnt = new Point(rand.nextInt(12), rand.nextInt(12));
			
			//check if the wumpus is are that location or repeats.
			if(nextPnt.equals(wumpus) || pits.contains(nextPnt)) {
				i--;
				continue;
			}
			
			room[nextPnt.x][nextPnt.y].setContent(Content.PIT);
			pits.add(nextPnt);
		}
	}
	
	//Sets the Content of the tiles surrounding the Pits to either
	// a slime or a goo
	private void fillSlimesAndGoop() {
		
		
		for(Point p : pits){
			Point slimePnt = new Point();
			
			for(int r = -1; r <= 1; r++) {
				for(int c = -1; c <= 1; c++) {
					if(r == 0 && c == 0)
						continue;
					if(Math.abs(r) + Math.abs(c) <= 1) {
						slimePnt.setLocation(Math.floorMod((p.x+r), 12), Math.floorMod((p.y+c),12));
						if(slimePnt.equals(wumpus))
							continue;
						else if (room[slimePnt.x][slimePnt.y].hiddenContent() == Content.BLOOD)
							room[slimePnt.x][slimePnt.y].setContent(Content.GOOP);
						else
							room[slimePnt.x][slimePnt.y].setContent(Content.SLIME);
					}
					
				}
			}
		}
		
	}
	
	
	public void fillGround() {
		
		for(int r = 0; r < 12; r++) {
			for(int c = 0; c < 12; c++) {
				room[r][c] = new Tile(Content.GROUND);
			}
		}
	}
	
	
	public ArrayList<Point> getPits() {
		return pits;
	}
	
	public Point getWumpus() {
		return wumpus;
	}
	//return specific Tile.
	public Tile getTile(int x, int y) {
		return room[x][y];
	}
}
