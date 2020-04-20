import java.awt.Label;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class main extends Application {
	
	//Variables to determine gird and window size
	public final static int GRIDHEIGHT=10;
	public final static int GRIDWIDTH=10;
	public final static int WINDOWHEIGHT=800;
	public final static int WINDOWWIDTH=800;
	public static int bombs = 20;
	public final static int[][] locationOfBombs = new int[10][10];
	public static boolean canPlay = true;
	static Stage display;
	Scene startGame, endGame;


	
	@Override
	public void start(Stage primaryStage) {
		display = primaryStage;
		display.setTitle("Minesweeper");
		StackPane layoutStart = new StackPane();
		Scene startGame = new Scene(layoutStart, 800, 800);
		Button start = new Button();
		start.setText("Start Game");
		start.setMinHeight(380);
		start.setMinWidth(800);
		start.setFont(Font.font(50));
		Label rules = new Label();
		rules.setText("How to play:");
		Label ruleOne = new Label();
		Label ruleTwo = new Label();
		Label ruleThree = new Label();
		
		ruleOne.setText("Use the arrow keys to control where you are selecting");
		ruleTwo.setText("Enter key uncovers your current square");
		ruleThree.setText("Shift key places a flag on the current square");
		EventHandler<ActionEvent> OnPressStart = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent ex) 
            { 
            	canPlay = true;
            	game(primaryStage);
		}
		};
		start.setOnAction(OnPressStart);
		layoutStart.getChildren().add(start);
		display.setScene(startGame);
		
		display.show();

	}
	public void game(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			bombPlace(locationOfBombs);
			//This assumes square window and graphic!
			//Stroke size must be subtracted...
			int graphicSize=WINDOWHEIGHT/GRIDHEIGHT - 3;
			
			//create all of the grid location and
			//add them to the GridPane;
			for(int i=0;i<GRIDHEIGHT;i++) {
				for (int j=0;j<GRIDWIDTH;j++) {
					root.add(new Grid(i,j,graphicSize).getGraphic(), i, j);
				}
			}
			
			//create player object and add player to gridpane
			Player p = new Player(5,5,graphicSize,locationOfBombs);
			root.add(p.getGraphic(), 5, 5);
			
			//setting up keyboard interaction
			root.setOnKeyPressed(
					(e)->{
						int x=p.getX();
						int y=p.getY();
						
						//moves the player piece based on key input
						//the val variable and the if statement
						//ensure that you don't move past the edge
						//of the grid.
						if(canPlay) {
						if(e.getCode()==KeyCode.UP) {
							int val=y-1;
							if(val<0) val=0;
							p.move(x,val,root);
						}
						if(e.getCode()==KeyCode.DOWN) {
							int val=y+1;
							if(val>GRIDHEIGHT-1) val=GRIDHEIGHT-1;
							p.move(x,val,root);
						}
						if(e.getCode()==KeyCode.LEFT) {
							int val=x-1;
							if(val<0) val=0;
							p.move(val,y,root);
						}
						if(e.getCode()==KeyCode.RIGHT) {
							int val=x+1;
							if(val>GRIDWIDTH-1) val=GRIDWIDTH-1;
							p.move(val,y,root);
						}
						if(e.getCode()==KeyCode.SHIFT) {
							Player.flag(x, y, root);
						}
						if(e.getCode()==KeyCode.ENTER) {
							Player.open(x, y, root, getNeighbors(x,y,root,locationOfBombs));
						}
						if(e.getCode()==KeyCode.NUMPAD0) {
							Player.clearSquare(x, y, root);
						}
						}
						else {
							VBox layout = new VBox(20);
							Scene endGame = new Scene(layout, 800, 800);
							Button youlose = new Button();
							youlose.setText("You Lose!");
							youlose.setMinHeight(380);
							youlose.setMinWidth(800);
							youlose.setFont(Font.font(50));
							Button restart = new Button();
							restart.setMinHeight(380);
							restart.setMinWidth(800);
							restart.setTranslateY(10);
							restart.setFont(Font.font(50));
							restart.setText("Click to restart");
							layout.getChildren().addAll(restart, youlose);
							EventHandler<ActionEvent> OnPressrestart = new EventHandler<ActionEvent>() { 
					            public void handle(ActionEvent ex) 
					            { 
					            	canPlay = true;
								start(primaryStage);
							}
							};
							restart.setOnAction(OnPressrestart);
							EventHandler<ActionEvent> OnPressyoulose = new EventHandler<ActionEvent>() { 
					            public void handle(ActionEvent ex) 
					            { 
					            	canPlay = true;
								start(primaryStage);
							}
							};
							youlose.setOnAction(OnPressyoulose);

							primaryStage.setScene(endGame);
							
						}
					}
				);
			
			//Standard JavaFX stuff to show the window.
			Scene scene = new Scene(root,WINDOWWIDTH,WINDOWHEIGHT);
			display.setScene(scene);
			root.requestFocus();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

    public int getNeighbors(int x, int y, GridPane pane, int[][] locationOfBombs) {
    	int neighbors = 0;
        int[] points = new int[] {
              -1, -1,
              -1, 0,
              -1, 1,
              0, -1,
              0, 1,
              1, -1,
              1, 0,
              1, 1
        };

       for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = x + dx;
            int newY = y + dy;

            if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
            	if(locationOfBombs[newX][newY] == 1)
            		neighbors++;
            }
        }

        return neighbors;
    }
    
    
	public void bombPlace(int[][] locationOfBombs){
		for(int i = 0; i < 10; i++) {
			for(int k = 0; k < 10; k++) {

				if(Math.random() > .8 && bombs > 0) {
					locationOfBombs[i][k] = 1;
					bombs--;
				}else if(bombs > 0 && i ==10 && k ==10) {
					i = 0;
					k = 0;
					
				}
			}
		}

	}
	public boolean hasBomb(int x, int y, int[][] locationOfBombs) {
		if(locationOfBombs[x][y] == 1)
			return true;
		return false;
		
	}

	public static void main(String[] args) {
		launch(args);
		for(int i = 0; i < 10; i++) {
			for(int k = 0; k < 10; k++) {
			System.out.println(locationOfBombs[i][k]);
			}
			
			}
	}
}