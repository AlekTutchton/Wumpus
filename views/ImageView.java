package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Content;
import model.Direction;
import model.Tile;
import model.Wumpus;

public class ImageView extends BorderPane implements Observer{

	private Wumpus wumpus;
	private Canvas canvas;
	private GraphicsContext gc;
	private Button[][] shootButtons = null;
	private GridPane shootPanel;
	private Label label;
	private HBox hbox;
	
	public ImageView(Wumpus currGame) {
		wumpus = currGame;
		
		//win or loose notification.
		label = new Label();
		this.setCenter(label);

		shootPanel = new GridPane();
	    initializeShootPanel();
	   
		//HBox setting
		hbox = new HBox();
		hbox.setAlignment(Pos.BASELINE_CENTER);
		hbox.getChildren().add(shootPanel);
		this.setBottom(hbox);
		
		//set up canvas
		canvas = new Canvas(600,600);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 600, 600);
		this.setTop(canvas);
		
	}

	//sets the NSWE shoot buttons
	private void initializeShootPanel() {
	    Font myFont = new Font("Courier New", 18);
	    shootButtons = new Button[3][3];
	    ButtonListener handler = new ButtonListener();
	    
	    //Shoot North Button
	    shootButtons[0][1] = new Button();
	    shootButtons[0][1].setFont(myFont);
	    shootButtons[0][1].setOnAction(handler);
	    shootButtons[0][1].setTextFill(Color.web("#0076a3"));
	    shootButtons[0][1].setText("N");
	    shootPanel.add(shootButtons[0][1], 1, 0);
	    
	    //Shoot South Button
	    shootButtons[2][1] = new Button();
	    shootButtons[2][1].setFont(myFont);
	    shootButtons[2][1].setOnAction(handler);
	    shootButtons[2][1].setTextFill(Color.web("#0076a3"));
	    shootButtons[2][1].setText("S");
	    shootPanel.add(shootButtons[2][1], 1, 2);
	    
	    //Shoot West Button
	    shootButtons[1][0] = new Button();
	    shootButtons[1][0].setFont(myFont);
	    shootButtons[1][0].setOnAction(handler);
	    shootButtons[1][0].setTextFill(Color.web("#0076a3"));
	    shootButtons[1][0].setText("W");
	    shootPanel.add(shootButtons[1][0], 0, 1);

	    
	    //Shoot East Button
	    shootButtons[1][2] = new Button();
	    shootButtons[1][2].setFont(myFont);
	    shootButtons[1][2].setOnAction(handler);
	    shootButtons[1][2].setTextFill(Color.web("#0076a3"));
	    shootButtons[1][2].setText("E");
	    shootPanel.add(shootButtons[1][2], 2, 1);
	    
	    //reset the Win/Lose label.
	    label.setText("");
	}

	// This method is called the observes to update the game.
	@Override
	public void update(Observable o, Object arg) {

		wumpus = (Wumpus) o;
		drawBoard();
		label.setText("");
		
		if(wumpus.isDead() == 1){
			label.setText("You have been eaten by the Wumpus!");
			endGame();
		}
		if(wumpus.isDead() == 2){
			label.setText("You have fallen into a Pit!");
			endGame();
		}
	}
	
	//show the end game positions. 
	public void endGame() {
		int xlocation = 0;
		int ylocation = 0;
		
		for (int r = 0; r < 12; r++) {
			for(int c = 0; c <12; c++){
				wumpus.getRoom().getTile(r, c).setVisited();
				gc.drawImage(new Image(wumpus.getRoom().getTile(r, c).getContent().getImage()), xlocation, ylocation);
				xlocation += 50;
			}
			xlocation = 0;
			ylocation += 50;
		}
	}
	
	public void drawBoard() {
		int xlocation = 0;
		int ylocation = 0;
		
		for(int r = 0; r < 12; r++) {
			for(int c = 0; c < 12; c++) {
				Tile curr = wumpus.getRoom().getTile(r, c);
				
				if(curr.visisted()){
					gc.fillRect(xlocation, ylocation, 50, 50);
					if(curr.hasHunter() && curr.hiddenContent() == Content.GROUND)
						gc.drawImage(new Image(wumpus.getRoom().getTile(r, c).getContent().getImage()), xlocation, ylocation);
					if(curr.hiddenContent() != Content.GROUND){
						gc.drawImage(new Image(wumpus.getRoom().getTile(r, c).hiddenContent().getImage()), xlocation, ylocation);
						gc.drawImage(new Image(wumpus.getRoom().getTile(r, c).getContent().getImage()), xlocation, ylocation);
					}
				}
				else 
					gc.drawImage(new Image(Content.GROUND.getImage()), xlocation, ylocation);
					xlocation += 50;
			}
			xlocation = 0;
			ylocation +=50;
		}
		
	}
	
	//Handler for the shoot buttons.
	public class ButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button buttonClicked = (Button) event.getSource();
	
			//shooting North or South doesn't matter. Neither does West or East.
			if(buttonClicked.getText() == "N" || buttonClicked.getText() == "S"){
				if(wumpus.shootArrow(Direction.NORTH)) 
					label.setText("YOU WIN!");
				else
					label.setText("YOU LOSE!");
						
			}
			else if(buttonClicked.getText() == "W" || buttonClicked.getText() == "E")
				if(wumpus.shootArrow(Direction.WEST))
					label.setText("YOU WIN!");
				else
					label.setText("YOU LOSE!");	
			endGame();
			}
	}
}
