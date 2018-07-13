package boardobject;

import java.awt.Color;

/**
 * 
 * @author UC This class defines the Engel (barrier) between two Cezmis located
 *         at the horizontal center of the GameBoard.
 */
public class Engel extends BoardObject {

	/**
	 * 
	 * @modifies: x,y, width, height, color default x coordinate = 12.5 - 0.25 /
	 *            2 = half of the screen - width / 2 default y coordinate =
	 *            board height - 3 = above 3L default width = 0.25 default
	 *            height = 3 default color = ORANGE
	 */

	public Engel() {
		super(12.5 - 0.25 / 2, 25 - 3, 0.25, 3.0, Color.ORANGE);
	}

	/**
	 * repOk method for Engel.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		return (getX() == 12.5 - 0.25 / 2 && getY() == 25 - 3 && getWidth() == 0.25 && getHeight() == 3);
	}

}
