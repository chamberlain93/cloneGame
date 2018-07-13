package boardobject;

import java.awt.Color;
import enums.DirectionEnum;

/**
 * 
 * @author UC This class extends Takoz class and implements Rotatable interface.
 *         This class includes methods such as setting the direction of a
 *         triangle takoz, rotating a triangle takoz, etc.
 */
public class TriangleTakoz extends Takoz implements Rotatable {

	private DirectionEnum direction;

	/**
	 * 
	 * @return The current direction enumeration of the triangle takoz.
	 */
	public DirectionEnum getDirection() {
		return direction;
	}

	/**
	 * 
	 * @param direction:
	 *            The new direction for the triangle takoz.
	 * @effects Sets the direction enumeration of the triangle takoz to the
	 *          given direction.
	 */
	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
	}

	/**
	 * 
	 * @param x:
	 *            X coordinate of the upper left corner of triangle takoz.
	 * @param y:
	 *            Y coordinate of the upper left corner of triangle takoz.
	 * @param height:
	 *            the height of the triangle takoz.
	 * @param width:
	 *            the width of the triangle takoz.
	 * @param color:
	 *            The color of the triangle takoz.
	 * @requires x and y coordinates smaller than 25.
	 * @modifies x, y, height, width and color attributes are modified.
	 * @effects New triangleTakoz instance is created and reflected to the
	 *          screen.
	 */
	public TriangleTakoz(int x, int y, double width, double height, Color color) {
		super(x, y, width, height, color);
		direction = DirectionEnum.SOUTH_WEST;
	}

	/**
	 * 
	 * @modifies direction enum of the triangle takoz.
	 * @effects Sets the direction enumeration of the triangle takoz to the new
	 *          direction according to the old direction.
	 */
	@Override
	public void startRotating() {
		// TODO Auto-generated method stub
		if (direction == DirectionEnum.SOUTH_WEST) {
			direction = DirectionEnum.SOUTH_EAST;
			setX(getX() + 1);
		} else if (direction == DirectionEnum.SOUTH_EAST) {
			direction = DirectionEnum.NORTH_EAST;
			setY(getY() - 1);
		} else if (direction == DirectionEnum.NORTH_EAST) {
			direction = DirectionEnum.NORTH_WEST;
			setX(getX() - 1);
		} else {
			direction = DirectionEnum.SOUTH_WEST;
			setY(getY() + 1);
		}
	}

	/**
	 * Deletes this object.
	 */
	@Override
	public void delete() {
		notifyAllObservers();
	}

	/**
	 * repOk method for TriangleTakoz.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		boolean cond1 = (getWidth() == 1 && getHeight() == 1);
		boolean cond2 = direction == DirectionEnum.SOUTH_EAST || direction == DirectionEnum.SOUTH_WEST
				|| direction == DirectionEnum.NORTH_EAST || direction == DirectionEnum.NORTH_WEST;

		return cond1 && cond2;
	}

	@Override
	public void move(int x, int y) {
		// TODO Auto-generated method stub

	}
}
