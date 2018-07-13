package boardobject;

import java.awt.Color;
import model.BoardModel;

/**
 * 
 * @author UC This class defines the borders of the GameBoard and implements
 *         tiny walls.
 */

public class Wall extends BoardObject {

	/**
	 * 
	 * @param x:
	 *            upper left corner x coordinate of the rectangle wall
	 * @param y:
	 *            upper left corner y coordinate of the rectangle wall
	 * @param width:
	 *            width of the rectangle wall
	 * @param height:
	 *            height of the rectangle wall
	 * @requires x and y coordinates, width and height are positive and smaller
	 *           than 25.
	 * @modifies x, y, width and height attributes are modified.
	 * @effects New Wall instance is created and reflected to the screen.
	 */
	public Wall(int x, int y, double width, double height) {
		super(x, y, width, height, Color.BLACK);
	}

	// for
	// upper wall (0): x: 0, y: 0, width= 25, height: 1/L
	// right wall (1): x: 25, y: 0, width= 1/L, height: 25
	// bottom wall (2): x: 0, y: 25, width= 25, height: 1/L
	// left wall (3): x: 0, y: 0, width= 1/L, height: 25

	// type: rectangle

	/**
	 * repOk method for Wall.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		int L = BoardModel.getL();

		boolean cond1 = (getX() == 0 && getY() == 0 && getWidth() == 25 && getHeight() == 1.0 / L);
		boolean cond2 = (getX() == 25 && getY() == 0 && getWidth() == 1.0 / L && getHeight() == 25);
		boolean cond3 = (getX() == 0 && getY() == 25 && getWidth() == 25 && getHeight() == 1.0 / L);
		boolean cond4 = (getX() == 0 && getY() == 0 && getWidth() == 1.0 / L && getHeight() == 25);

		return cond1 || cond2 || cond3 || cond4;
	}

}
