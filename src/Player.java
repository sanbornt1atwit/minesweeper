import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Player {
	//x and y location data
	//this is NOT connected to the GridPane
	private int x;
	private int y;
	private static int[][] bombs;
	private static int[][] played = new int[10][10];

	
	//the player object knows about its graphic
	private static Rectangle graphic;
	private static Rectangle flagged;
	private static Rectangle opened;
	private static Rectangle bomb;

	/**
	 * Constructor, sets location and graphic
	 * 
	 * @param x    x-Location data for the element
	 * @param y    y-Location data for the element
	 * @param size Physical size of the graphic (Rectangle)
	 */
	Player(int x, int y, int size,int[][] locationOfBombs){
		this.x=x;
		this.y=y;
		bombs=locationOfBombs;
		graphic=new Rectangle(size,size);
		graphic.setFill(Color.LIGHTBLUE);
	}
	
	/**
	 * Method that moves the player to a new x,y location
	 * in the GridPane p.
	 * 
	 * This method is not safe, if the passed in GridPane
	 * does not contain the player, the program will crash.
	 * It may be better to include a reference to the GridPane
	 * as part of the class data.
	 * 
	 * @param x New x-location
	 * @param y New y-location
	 * @param p The GridPane that this player belongs to
	 */
	public void move(int x, int y, GridPane pane) {
		//update the Player class data
		this.x=x;
		this.y=y;
		//remove the player graphic from the grid
		pane.getChildren().remove(graphic);
		//add the player graphic to the grid in
		//its new location
		pane.add(graphic, x, y); //THIS is now we connect to the GridPane
	}
	public static void flag(int x, int y, GridPane pane) {
		flagged = new Rectangle(77,77);
		flagged.setFill(Color.GREEN);
		pane.add(flagged, x, y);
	}
	public static void open(int x, int y, GridPane pane, int neighbors) {
		opened = new Rectangle(77,77);
		opened.setFill(Color.YELLOW);
		bomb = new Rectangle(77,77);
		bomb.setFill(Color.RED);
		if(bombs[x][y] == 1) {
			pane.add(bomb,x,y);
			main.canPlay = false;
		}else {
			if(played[x][y] == 0) {
			String n = String.valueOf(neighbors);;
			Label l = new Label(("	  " + n));
			pane.add(opened,x,y);
			pane.add(l, x, y);	
			played[x][y] = 1;
			}
			else
				return;
		}

	}
	public static void clearSquare(int x, int y, GridPane pane) {
		if(bombs[x][y] == 0) {
			pane.getChildren().remove(flagged);
		}
	}
	public static void resetPlayed() {
		for(int i = 0; i < 10; i++) {
			for(int k = 0; k < 10; k++) {
			played[i][k] = 0;
			}
		}
	}
	/**
	 * Getter of the element graphic
	 * @return Rectangle graphic (for adding to the pane)
	 */
	public Rectangle getGraphic() {
		return graphic;
	}
	
	/**
	 * Getter for the x coordinate
	 * @return x-location
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for the y coordinate
	 * @return y-location
	 */
	public int getY() {
		return y;
	}
}