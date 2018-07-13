package boardobject;

import java.awt.Color;
import enums.DirectionEnum;
import main.Constants;
import observer.GizmoObserver;
import physics.LineSegment;
import physics.Vect;

/**
 * 
 * @author UC This concrete class extends Tokat class and implements rotate
 *         method using the superclass's rotateLeft and rotateRight methods
 */

public class LeftTokat extends Tokat {

	private int rotateCount = 0;
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
				double p1x = Math.round((getTopLine().p1().x() * 100)) / 100.0;
				double p1y = Math.round((getTopLine().p1().y() * 100)) / 100.0;
				double p2x = Math.round((getTopLine().p2().x() * 100)) / 100.0;
				double p2y = Math.round((getTopLine().p2().y() * 100)) / 100.0;

				System.out.println("top line: " + p1x + "-" + p1y + "-" + p2x + "-" + p2y);
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
	 * @effects New LeftTokat instance is created and reflected to the screen.
	 */
	public LeftTokat(int x, int y) {
		super(x, y, 0.5, 2, Color.BLUE, true);

		setTopLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX() + getWidth(), getY())));
		setRightLine(new LineSegment(new Vect(getX() + getWidth(), getY()),
				new Vect(getX() + getWidth(), getY() + getHeight())));
		setBottomLine(new LineSegment(new Vect(getX(), getY() + getHeight()),
				new Vect(getX() + getWidth(), getY() + getHeight())));
		setLeftLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() + getHeight())));

		// default value of the height is 2
		// default color is indicated as Green
		attachObserver(new GizmoObserver());
	}

	/**
	 * repOk method for LeftTokat.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		return (getY() <= 17 && ((getX() >= 0 && getX() <= 11) || (getX() > 12 && getX() <= 23)));
	}

	/**
	 * rotate method rotates right if LeftTokat is not rotated 90 degrees. else,
	 * it rotates to the left.
	 * 
	 * @modifies angle is modified.
	 */

	public void rotateController() {
		if (rotateCount % 2 == 0) { // rotate to the right
			rotateRight();
		} else { // rotate to the left
			rotateLeft();
		}
	}

	public void rotate() {
		new Thread(instance).start();
	}

	/**
	 * Deletes this object.
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
		double dif = h - getWidth();

		if (getDirection() == DirectionEnum.SOUTH) {
			Vect topLeft = new Vect(getX(), getY() + h);
			Vect topRight = new Vect(getX(), getY() + dif);
			Vect bottomLeft = new Vect(getX() + h, getY() + h);
			Vect bottomRight = new Vect(getX() + h, getY() + dif);
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setY(getY() + getHeight());
			setDirection(DirectionEnum.EAST);
		} else if (getDirection() == DirectionEnum.EAST) {
			Vect topLeft = new Vect(getX() + h, getY());
			Vect topRight = new Vect(getX() + dif, getY());
			Vect bottomLeft = new Vect(getX() + h, getY() - h);
			Vect bottomRight = new Vect(getX() + dif, getY() - h);
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setX(getX() + getHeight());
			setDirection(DirectionEnum.NORTH);
		} else if (getDirection() == DirectionEnum.NORTH) {
			Vect topLeft = new Vect(getX(), getY() - h);
			Vect topRight = new Vect(getX(), getY() - dif);
			Vect bottomLeft = new Vect(getX() - h, getY() - h);
			Vect bottomRight = new Vect(getX() - h, getY() - dif);
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setY(getY() - getHeight());
			setDirection(DirectionEnum.WEST);
		} else if (getDirection() == DirectionEnum.WEST) {
			Vect topLeft = new Vect(getX() - h, getY());
			Vect topRight = new Vect(getX() - dif, getY());
			Vect bottomLeft = new Vect(getX() - h, getY() + h);
			Vect bottomRight = new Vect(getX() - dif, getY() + h);
			swapLines(topLeft, topRight, bottomLeft, bottomRight);

			setX(getX() - getHeight());
			setDirection(DirectionEnum.SOUTH);
		}
	}

	@Override
	public void move(int x, int y) {
		setX(x);
		setY(y);
		setDirection(getDirection());
		if (getDirection() == DirectionEnum.SOUTH) {
			setTopLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX() + getWidth(), getY())));
			setRightLine(new LineSegment(new Vect(getX() + getWidth(), getY()),
					new Vect(getX() + getWidth(), getY() + getHeight())));
			setBottomLine(new LineSegment(new Vect(getX(), getY() + getHeight()),
					new Vect(getX() + getWidth(), getY() + getHeight())));
			setLeftLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() + getHeight())));
		} else if (getDirection() == DirectionEnum.EAST) {
			setTopLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() - getWidth())));
			setRightLine(new LineSegment(new Vect(getX(), getY() - getWidth()),
					new Vect(getX() + getHeight(), getY() - getWidth())));
			setBottomLine(new LineSegment(new Vect(getX() + getHeight(), getY()),
					new Vect(getX() + getHeight(), getY() - getWidth())));
			setLeftLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX() + getHeight(), getY())));
		} else if (getDirection() == DirectionEnum.NORTH) {
			setTopLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX() - getWidth(), getY())));
			setRightLine(new LineSegment(new Vect(getX() - getWidth(), getY()),
					new Vect(getX() - getWidth(), getY() - getHeight())));
			setBottomLine(new LineSegment(new Vect(getX(), getY() - getHeight()),
					new Vect(getX() - getWidth(), getY() - getHeight())));
			setLeftLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() - getHeight())));
		} else if (getDirection() == DirectionEnum.WEST) {
			setTopLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX(), getY() + getWidth())));
			setRightLine(new LineSegment(new Vect(getX(), getY() + getWidth()),
					new Vect(getX() - getHeight(), getY() + getWidth())));
			setBottomLine(new LineSegment(new Vect(getX() - getHeight(), getY()),
					new Vect(getX() - getHeight(), getY() + getWidth())));
			setLeftLine(new LineSegment(new Vect(getX(), getY()), new Vect(getX() - getHeight(), getY())));
		}

	}
}
