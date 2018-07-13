package boardobject;

import java.awt.Color;
import model.BoardModel;

/**
 * 
 * @author UC This class is an abstract super class of every board object types.
 *
 */

public abstract class BoardObject {

	private static final int L = BoardModel.getL();

	private double x;
	private double y;
	private double width;
	private double height;
	private Color color;

	/**
	 * 
	 * @return Returns the x-coordinate of the board object.
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @param x:
	 *            x-coordinate for the board object.
	 * @requires <code>x</code> must be between 0 and 25.
	 * @modifies x-coordinate of the board object is changed to <code>x</code>.
	 * @effects Sets the x-coordinate of the board object to the given
	 *          <code>x</code>.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @return Returns the y-coordinate of the board object.
	 */
	public double getY() {
		return y;
	}

	/**
	 * 
	 * @param y:
	 *            y-coordinate for the board object.
	 * @requires <code>y</code> must be between 0 and 25.
	 * @modifies y-coordinate of the board object is changed to <code>y</code>.
	 * @effects Sets the y-coordinate of the board object to the given
	 *          <code>y</code>.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * 
	 * @return Returns the color of the board object.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * 
	 * @param color:
	 *            x-coordinate for the board object.
	 * @modifies color of the board object is changed to <code>color</code>.
	 * @effects Sets the color of the board object to the given
	 *          <code>color</code>.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 
	 * @return Returns the width of the board object.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * 
	 * @param width:
	 *            width of the board object.
	 * @requires <code>width</code> must be between 0 and 25.
	 * @modifies width of the board object is changed to <code>width</code>.
	 * @effects Sets the width of the board object to the given
	 *          <code>width</code>.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * 
	 * @return Returns the height of the board object.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * 
	 * @param height:
	 *            height of the board object.
	 * @requires <code>height</code> must be between 0 and 25.
	 * @modifies height of the board object is changed to <code>height</code>.
	 * @effects Sets the height of the board object to the given
	 *          <code>height</code>.
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * 
	 * @return Returns the basic unit L.
	 */
	public static int getL() {
		return L;
	}

	/**
	 * 
	 * @param x:
	 *            x-coordinate of the board object.
	 * @param y:
	 *            y-coordinate of the board object.
	 * @param width:
	 *            width of the board object.
	 * @param height:
	 *            height of the board object.
	 * @param color:
	 *            color of the board object.
	 * @requires x-coordinate, y-coordinate, width and height are positive and
	 *           smaller than 25.
	 * @modifies x, y, width, height and color attributes are modified.
	 * @effects New BoardObject instance is created.
	 */
	public BoardObject(double x, double y, double width, double height, Color color) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}

	/**
	 * repOk method for BoardObject.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		if (x >= 0 && x <= 25 && y >= 0 && y <= 25 && width >= 0 && width <= 25 && height >= 0 && height <= 25) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * toString method for BoardObject.
	 */
	public String toString() {
		return "[BoardObject= x: " + getX() + ", y: " + getY() + ", width: " + getWidth() + ", height: " + getHeight()
				+ " and color: " + getColor().toString() + "]";
	}

}
