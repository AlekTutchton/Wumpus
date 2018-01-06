package controller;

import java.util.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Direction;
import model.Wumpus;
import views.ImageView;
import views.TextView;

public class WumpusMain extends Application {

  public static void main(String[] args) {
   launch(args);

  }

  private Wumpus wumpus;
  
  private MenuBar menuBar;
 
  private Observer currentView;
  private Observer imageView;
  private Observer textView;
  
  private BorderPane window;
  public static final int width = 600;
  public static final int height = 800;
  
  @Override
  public void start(Stage stage) throws Exception {
   stage.setTitle("Hunt the Wumpus 17");
   window = new BorderPane();
   Scene scene = new Scene(window, width, height);
   
   scene.setOnKeyPressed(new KeyListener());
   
   setupMenus();
   window.setTop(menuBar);
   
   initializeFirstGame();

  //set up Views and default imageView
  imageView = new ImageView(wumpus);
  textView = new TextView(wumpus);
  wumpus.addObserver(imageView);
  wumpus.addObserver(textView);
  setViewTo(imageView);
  	
   stage.setScene(scene);
   stage.show();
   wumpus.startNewGame();
  }

private void initializeFirstGame() {
	wumpus = new Wumpus();	
}

private void setupMenus() {
	MenuItem newGame = new MenuItem("New Game");
	Menu menu = new Menu("Menu");
	menu.getItems().add(newGame);
	
	MenuItem textV = new MenuItem("TextView");
	MenuItem imageV = new MenuItem("ImageView");
	Menu views = new Menu("Views");
	views.getItems().addAll(imageV, textV);
	menu.getItems().add(views);
	
	menuBar = new MenuBar();
	menuBar.getMenus().addAll(menu);
	
	//Add listeners to the view menu items
    MenuItemListener menuListener = new MenuItemListener();
    newGame.setOnAction(menuListener);
    textV.setOnAction(menuListener);
    imageV.setOnAction(menuListener);
	
}

//set the View chosen to the currentView
public void setViewTo(Observer newView){
	window.setCenter(null);
	currentView = newView;
	window.setCenter((Node) currentView);
}

private class MenuItemListener implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent e) {
		//Get the string version of which menu item was clicked
		String clicked = ((MenuItem) e.getSource()).getText();
		
		if(clicked.equals("New Game"))
			wumpus.startNewGame();
		else if (clicked.equals("TextView"))
			setViewTo(textView);
		else if (clicked.equals("ImageView"))
			setViewTo(imageView);
		
	}	
}
private class KeyListener implements EventHandler<KeyEvent> {

	@Override
	public void handle(KeyEvent event) {
		
		if(wumpus.isRunning()) {
			if(KeyCode.UP == event.getCode())
				wumpus.moveHunter(Direction.NORTH);
			else if(KeyCode.LEFT == event.getCode())
				wumpus.moveHunter(Direction.WEST);
			else if(KeyCode.DOWN == event.getCode())
				wumpus.moveHunter(Direction.SOUTH);
			else if(KeyCode.RIGHT == event.getCode())
				wumpus.moveHunter(Direction.EAST);
		}
	}
}
}