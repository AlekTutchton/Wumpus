package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;

import org.junit.Test;

import model.Content;
import model.Direction;
import model.Hunter;
import model.Tile;
import model.Wumpus;

public class WumpusTest {

  @Test
  public void testWumpusBloodsAndSlimes() {
    Wumpus game = new Wumpus();
    Point wumpus = game.getRoom().getWumpus();
    
    //Test for the blood
    for(int r = -2; r < 3; r++){
    	for(int c = -2; c < 3; c++){
    		Tile curr = game.getRoom().getTile(Math.floorMod((r + wumpus.x), 12), Math.floorMod((c + wumpus.y), 12));
    		
    		if(r == 0 && c == 0)
    			assertTrue(curr.hiddenContent() == Content.WUMPUS);
    		
    		else if(Math.abs(r) + Math.abs(c) <= 2) {
    			assertTrue(curr.hiddenContent() == Content.BLOOD || curr.hiddenContent() == Content.GOOP ||
    						curr.hiddenContent() == Content.PIT);
    		}
    		
    		else {
    			assertTrue(curr.hiddenContent() == Content.SLIME || curr.hiddenContent() == Content.GROUND);
    		}
    	}
    }
  }
  
  
  @Test
  public void testPitsSlimes() {
	  Wumpus game = new Wumpus();
	  ArrayList<Point> pits = game.getRoom().getPits();
	  
		for(Point p : pits){
			for(int r = -1; r <= 1; r++) {
				for(int c = -1; c <= 1; c++) {
					
					Tile curr = game.getRoom().getTile(Math.floorMod((p.x+r), 12), Math.floorMod((p.y+c),12));
					if(r == 0 && c == 0)
						assertTrue(curr.hiddenContent() == Content.PIT);
					
					else if(Math.abs(r) + Math.abs(c) <= 1) {
						assertTrue(curr.hiddenContent() == Content.SLIME || curr.hiddenContent() == Content.GOOP ||
								curr.hiddenContent() == Content.WUMPUS);
					}
					
				}
			}
		}
  }
  
  @Test
  public void testHunterMove() {
	  Wumpus game = new Wumpus();
	  Hunter hunter = game.gethunter();
	  Point oldPoint = new Point();
	  
	  //test first point
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).visisted());
	 
	  oldPoint.setLocation(hunter.getLocation());
	  game.moveHunter(Direction.NORTH);
	  
	  //test Old Point.
	  assertTrue(game.getRoom().getTile(oldPoint.x, oldPoint.y).visisted());
	  assertFalse(game.getRoom().getTile(oldPoint.x, oldPoint.y).hasHunter());
	  
	  //Test new Point.
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).visisted());
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).hasHunter());

	  oldPoint.setLocation(hunter.getLocation());
	  game.moveHunter(Direction.WEST);
	  
	  //test Old Point.
	  assertTrue(game.getRoom().getTile(oldPoint.x, oldPoint.y).visisted());
	  assertFalse(game.getRoom().getTile(oldPoint.x, oldPoint.y).hasHunter());
	  
	  //Test new Point.
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).visisted());
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).hasHunter());

	  oldPoint.setLocation(hunter.getLocation());
	  
	  game.moveHunter(Direction.SOUTH);
	  
	  //test Old Point.
	  assertTrue(game.getRoom().getTile(oldPoint.x, oldPoint.y).visisted());
	  assertFalse(game.getRoom().getTile(oldPoint.x, oldPoint.y).hasHunter());
	  
	  //Test new Point.
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).visisted());
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).hasHunter());

	  
	  oldPoint.setLocation(hunter.getLocation());
	  game.moveHunter(Direction.EAST);
	  
	  //test Old Point.
	  assertTrue(game.getRoom().getTile(oldPoint.x, oldPoint.y).visisted());
	  assertFalse(game.getRoom().getTile(oldPoint.x, oldPoint.y).hasHunter());
	  
	  //Test new Point.
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).visisted());
	  assertTrue(game.getRoom().getTile(hunter.getLocation().x, hunter.getLocation().y).hasHunter());	  
  }

  }
