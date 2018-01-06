package model;

public class Tile {
	private Content content;
	private Content hasHunter;
	private boolean visited;
	
	public Tile(Content content) {
		this.content = content;
		this.visited = false;
	}
	
	//return the content of the tile, unless it has been visited
	//or has the hunter.
	public Content getContent() {
		if(hasHunter == Content.HUNTER)
			return hasHunter;
		else
			return content;
	}
	
	//returns the hidden content of the tile
	public Content hiddenContent() {
		return content;
	}
	

	//add the Hunter to the Tile.
	public void setHasHunter(boolean bool) {
		if(bool)
			this.hasHunter = Content.HUNTER;
		else {
			this.hasHunter = null;
		}
	}
	
	//return if the Tile has a hunter.
	public boolean hasHunter() {
		if(hasHunter == Content.HUNTER)
			return true;
		return false;
	}
	
	//set the Tile's content.
	public void setContent(Content newCont) {
		this.content = newCont;
	}
	
	//return if the Tile has been visited and visible
	public boolean visisted() {
		return visited;
	}
	
	//set the the Tile to visited.
	public void setVisited() {
		this.visited = true;
	}
	
	//return the corresponding Char to the TextView
	public char getChar() {
	
		if(!visited)
			return 'X';
		else if(hasHunter == Content.HUNTER) 
			return 'H';
		else if(content == Content.BLOOD) 
			return 'B';
		else if(content == Content.GOOP) 
			return 'G';
		else if(content == Content.SLIME) 
			return 'S';
		else if(content == Content.WUMPUS) 
			return 'W';
		else if(content == Content.PIT) 
			return 'P';
		else
			return ' ';
	}	
}