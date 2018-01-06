package model;

//Enums for the WumpusRoom images and text values.
public enum Content{
	BLOOD("file:images/Blood.png"),
	GOOP("file:images/Goop.png"),
	GROUND("file:images/Ground.png"),
	SLIME("file:images/Slime.png"),
	PIT("file:images/SlimePit.png"),
	HUNTER("file:images/TheHunter.png"),
	WUMPUS("file:images/Wumpus.png");
	
	private String img;
	
	Content(String img){
		this.img = img;
	}
	
	public String getImage(){
		return img;
	}
}
