package boardobject;

import java.awt.Color;

/**
 * 
 * @author UC This class extends Takoz class.
 */
public class SquareTakoz extends Takoz {

	/**
	 * 
	 * @param x:
	 *            X coordinate of the upper left corner of takoz.
	 * @param y:
	 *            Y coordinate of the upper left corner of takoz.
	 * @param height:
	 *            the height of the takoz.
	 * @param width:
	 *            the width of the takoz.
	 * @param color:
	 *            The color of the takoz.
	 * @requires x and y coordinates smaller than 25.
	 * @modifies x, y, height, width and color attributes are modified.
	 * @effects New SquareTakoz instance is created and reflected to the screen.
	 */
	public SquareTakoz(int x, int y, double width, double height, Color color) {
		super(x, y, width, height, color);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Deletes this object.
	 */
	@Override
	public void delete() {
		notifyAllObservers();
	}

	/**
	 * repOk method for SquareTakoz.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		return (getWidth() == 1 && getHeight() == 1);
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub

	}

}
