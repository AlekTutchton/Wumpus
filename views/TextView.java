package views;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Content;
import model.Direction;
import model.Wumpus;

public class TextView extends BorderPane implements Observer{

	private Wumpus wumpus;
	private Label statusButton;
	private Button[][] shootButtons = null;
	private GridPane shootPanel;
	private TextArea gameTxt;
	private HBox hbox;
	
	
	final public static String safeMsg = "Safe for Now...";
	
	public TextView(Wumpus currGame) {
		wumpus = currGame;
		
		
		//set the game text Area
		gameTxt = new TextArea();
		gameTxt.setEditable(false);
		gameTxt.setFocusTraversable(false);
		gameTxt.setStyle("-fx-font-family: monospace; -fx-font-size: 34");
		BorderPane.setAlignment(gameTxt, Pos.TOP_CENTER);
		BorderPane.setMargin(gameTxt, new Insets(10, 30, 50, 30));
		this.setCenter(gameTxt);
		
		//set the shoot button panel
		shootPanel = new GridPane();
		initializeShootPanel();
		
		//set the Status Button
	    statusButton = new Label(safeMsg);
	    statusButton.setFont(new Font("Arial", 17));
	    statusButton.setTextFill(Color.web("#0076a3"));
	    
	    //HBox setting
	    hbox = new HBox(150);
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(shootPanel, statusButton);
	    BorderPane.setAlignment(hbox, Pos.BOTTOM_CENTER);
	    this.setBottom(hbox);
	}

	//Called by the Observers to update the status button on the
	//current game.
	@Override
	public void update(Observable o, Object arg) {
		
		wumpus = (Wumpus) o;
		statusButton.setText(dangerWarning());
		if(wumpus.isDead() == 1) {
			statusButton.setText("You have been eaten by the Wumpus!");
			endGame();
		}
		if(wumpus.isDead() == 2) {
			statusButton.setText("You have fallen into a Pit");
			endGame();
		}
		
		gameTxt.setText(""  + wumpus.toString());
		
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
	}

	private String dangerWarning() {
		String warning = safeMsg;
		if(!wumpus.isSafe())
			warning = "I Smell Somthing Foul";
		
		return warning;
	}
	private void endGame() {
		for(int r = 0; r < 12; r++){
			for(int c = 0; c <12; c++){
				if(wumpus.getRoom().getTile(r, c).hiddenContent() != Content.GROUND)
					wumpus.getRoom().getTile(r, c).setVisited();
			}
		}
		
		gameTxt.setText("" + wumpus.toString());
	}
	
	
	//Handler for the shoot buttons.
	private class ButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			Button buttonClicked = (Button) event.getSource();
			
			//shooting North or South doesn't matter. Neither does West or East.
			if(buttonClicked.getText() == "N" || buttonClicked.getText() == "S"){
				if(wumpus.shootArrow(Direction.NORTH)) 
					statusButton.setText("YOU WIN!");
				else
					statusButton.setText("YOU LOSE!");
						
			}
			else if(buttonClicked.getText() == "W" || buttonClicked.getText() == "E")
				if(wumpus.shootArrow(Direction.WEST))
					statusButton.setText("YOU WIN!");
				else
					statusButton.setText("YOU LOSE!");	
			endGame();
			
		}
		
	}
}
