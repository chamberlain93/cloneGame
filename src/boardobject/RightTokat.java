package boardobject;

import java.awt.Color;
import enums.DirectionEnum;
import main.Constants;
import observer.GizmoObserver;
import physics.LineSegment;
import physics.Vect;

/**
 * @author UC This concrete class extends Tokat class and implements rotate
 *         method using the superclass's rotateLeft and rotateRight methods
 */

public class RightTokat extends Tokat {

	int rotateCount = 0;
	private RunnableRotate instance = new RunnableRotate();

	class RunnableRotate implements Runnable {

		private boolean locked = false;

		public void run() {

			if (!locked) {
				locked = true;
				setRotatingNow(true);

				for (int i = 0; i < (int) (90.00 / getAngularVelocity()); i++) {
					rotateController();

					try {
						Thread.sleep(1000 / Constants.frameRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				rotateCount += 1;
				locked = false;
				setRotatingNow(false);
			}
		}
	}

	/**
	 * @param x:
	 *            upper left corner x coordinate of the object
	 * @param y:
	 *            upper left corner y coordinate of the object
	 * @requires x and y coordinates are positive and smaller than 25.
	 * @modifies x and y attributes are modified.
	 * @effects New RightTokat instance is created and reflected to the screen.
	 */
	public RightTokat(int x, int y) {
		super(x, y, 0.5, 2, Color.red, false);

		setTopLine(new LineSegment(new Vect(getX() - getWidth(), getY()), new Vect(getX(), getY())));
		setRightLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() + getHeight())));
		setBottomLine(new LineSegment(new Vect(getX() - getWidth(), getY() + getHeight()),
				new Vect(getX(), getY() + getHeight())));
		setLeftLine(new LineSegment(new Vect(getX() - getWidth(), getY()),
				new Vect(getX() - getWidth(), getY() + getHeight())));

		// default value of the height is 2
		// default color is indicated as Red
		attachObserver(new GizmoObserver());
	}

	/**
	 * repOk method for RightTokat.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		return (getY() <= 17 && ((getX() >= 0 && getX() <= 11) || (getX() > 12 && getX() <= 23)));
	}

	public void rotateController() {
		if (rotateCount % 2 == 0) { // rotate to the right
			rotateLeft();
		} else { // rotate to the left
			rotateRight();
		}
	}

	public void rotate() {
		new Thread(instance).start();
	}

	/**
	 * rotate method rotates left if RightTokat is not rotated 90 degrees. else,
	 * it rotates to the right.
	 * 
	 * @modifies angle is modified.
	 * 
	 * 
	 *           /** Deletes this object.
	 */
	public void delete() {
		notifyAllObservers();
	}

	private void swapLines(Vect topLeft, Vect topRight, Vect bottomLeft, Vect bottomRight) {
		this.setLeftLine(new LineSegment(topLeft, bottomLeft));
		this.setRightLine(new LineSegment(topRight, bottomRight));
		this.setTopLine(new LineSegment(topLeft, topRight));
		this.setBottomLine(new LineSegment(bottomLeft, bottomRight));
	}

	@Override
	/**
	 * @modifies direction of the Tokat, width, height
	 * @requires direction is not null
	 * 
	 */
	public void startRotating() {
		double h = getHeight();
		double w = getWidth();

		if (getDirection() == DirectionEnum.SOUTH) {
			Vect topLeft = new Vect(getX() - h, getY() + w);
			Vect topRight = new Vect(getX() - h, getY());
			Vect bottomLeft = new Vect(getX(), getY() + w);
			Vect bottomRight = new Vect(getX(), getY());
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setX(getX() - getHeight());
			setDirection(DirectionEnum.EAST);
		} else if (getDirection() == DirectionEnum.WEST) {
			Vect topLeft = new Vect(getX() - w, getY() - h);
			Vect topRight = new Vect(getX(), getY() - h);
			Vect bottomLeft = new Vect(getX() - w, getY());
			Vect bottomRight = new Vect(getX(), getY());
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setY(getY() - getHeight());
			setDirection(DirectionEnum.SOUTH);
		} else if (getDirection() == DirectionEnum.NORTH) {
			Vect topLeft = new Vect(getX() + h, getY() - w);
			Vect topRight = new Vect(getX() + h, getY());
			Vect bottomLeft = new Vect(getX(), getY() - w);
			Vect bottomRight = new Vect(getX(), getY());
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setX(getX() + getHeight());
			setDirection(DirectionEnum.WEST);
		} else if (getDirection() == DirectionEnum.EAST) {
			Vect topLeft = new Vect(getX() + w, getY() + h);
			Vect topRight = new Vect(getX(), getY() + h);
			Vect bottomLeft = new Vect(getX() + w, getY());
			Vect bottomRight = new Vect(getX(), getY());
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setY(getY() + getHeight());
			setDirection(DirectionEnum.NORTH);
		}
	}

	@Override
	public void move(int x, int y) {
		setX(x);
		setY(y);
		setDirection(getDirection());

		if (getDirection() == DirectionEnum.SOUTH) {
			setTopLine(new LineSegment(new Vect(getX() - getWidth(), getY()), new Vect(getX(), getY())));
			setRightLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() + getHeight())));
			setBottomLine(new LineSegment(new Vect(getX() - getWidth(), getY() + getHeight()),
					new Vect(getX(), getY() + getHeight())));
			setLeftLine(new LineSegment(new Vect(getX() - getWidth(), getY()),
					new Vect(getX() - getWidth(), getY() + getHeight())));
		} else if (getDirection() == DirectionEnum.EAST) {
			setTopLine(new LineSegment(new Vect(getX(), getY() + getWidth()), new Vect(getX(), getY())));
			setRightLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX() + getHeight(), getY())));
			setBottomLine(new LineSegment(new Vect(getX() + getHeight(), getY() + getWidth()),
					new Vect(getX() + getHeight(), getY())));
			setLeftLine(new LineSegment(new Vect(getX(), getY() + getWidth()),
					new Vect(getX() + getHeight(), getY() + getWidth())));
		} else if (getDirection() == DirectionEnum.NORTH) {
			setTopLine(new LineSegment(new Vect(getX() + getWidth(), getY()), new Vect(getX(), getY())));
			setRightLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() - getHeight())));
			setBottomLine(new LineSegment(new Vect(getX() + getWidth(), getY() - getHeight()),
					new Vect(getX(), getY() - getHeight())));
			setLeftLine(new LineSegment(new Vect(getX() + getWidth(), getY()),
					new Vect(getX() + getWidth(), getY() - getHeight())));
		} else if (getDirection() == DirectionEnum.WEST) {
			setTopLine(new LineSegment(new Vect(getX(), getY() - getWidth()), new Vect(getX(), getY())));
			setRightLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX() - getHeight(), getY())));
			setBottomLine(new LineSegment(new Vect(getX() - getHeight(), getY() - getWidth()),
					new Vect(getX() - getHeight(), getY())));
			setLeftLine(new LineSegment(new Vect(getX(), getY() - getWidth()),
					new Vect(getX() - getHeight(), getY() - getWidth())));
		}
	}
}