package boardobject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import enums.DirectionEnum;
import main.Constants;
import observer.Observer;
import physics.Angle;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * 
 * @author UC The abstract Tokat class defines one of the subclass of Gizmo
 *         superclass and implements Rotatable and Observable interfaces. This
 *         class is a base for LeftTokat and RigthTokat concerete classes and
 *         defines rotateLeft and rotateRight methods.
 */

public abstract class Tokat extends Gizmo implements Rotatable, Observable {

	private double angularVelocity;
	private double angle;

	private List<Observer> observers = new ArrayList<Observer>();
	private DirectionEnum direction;
	private LineSegment topLine;
	private LineSegment rightLine;
	private LineSegment bottomLine;
	private LineSegment leftLine;
	private boolean rotatingNow = false;
	private Circle tailCircle;
	private Circle headCircle;

	private Vect cor;
	private Vect[] points = new Vect[6];

	private boolean isLeft;

	/**
	 * 
	 * @param x:
	 *            upper left corner x coordinate of the object
	 * @param y:
	 *            upper left corner y coordinate of the object
	 * @param width:
	 *            width of the rectangle Tokat object
	 * @param height:
	 *            height of the rectangle Tokat object
	 * @param color:
	 *            color of the object Tokat constructor sets parameters to the
	 *            instance variables. angularVelocity is set as defined in
	 *            documentation. 1080 degrees per second initial angle is -90
	 *            degrees, Tokat is looking downward. initial direction is
	 *            NORTH.
	 * @requires x and y coordinates, width and height are positive and smaller
	 *           than 25.
	 * @modifies x, y, width, height and color attributes are modified.
	 * @effects New Tokat instance is created and reflected to the screen.
	 */
	public Tokat(int x, int y, double width, double height, Color color, boolean isLeft) {
		super(x, y, width, height, color);

		this.isLeft = isLeft;
		setAngularVelocity(Constants.tokatAngularVelocity / getFrameRate());
		setAngle(0);

		double rad = getWidth() / 2;

		if (isLeft) {
			cor = new Vect(getX() + rad, getY() + rad);
			points[0] = new Vect(getX(), getY());
			points[1] = new Vect(getX() + getWidth(), getY());
			points[2] = new Vect(getX(), getY() + getHeight());
			points[3] = new Vect(getX() + getWidth(), getY() + getHeight());
		} else {
			cor = new Vect(getX() - rad, getY() - rad);
			points[0] = new Vect(getX() - getWidth(), getY());
			points[1] = new Vect(getX(), getY());
			points[2] = new Vect(getX() - getWidth(), getY() + getHeight());
			points[3] = new Vect(getX(), getY() + getHeight());
		}

		updateCenters();
		setDirection(DirectionEnum.SOUTH);
	}

	public Circle getTailCircle() {
		return tailCircle;
	}

	private void updateCenters() {
		points[4] = new Vect((points[0].x() + points[1].x()) / 2, (points[0].y() + points[1].y()) / 2); // head
																										// //
																										// center
		points[5] = new Vect((points[2].x() + points[3].x()) / 2, (points[2].y() + points[3].y()) / 2); // tail
																										// //
																										// center
	}

	/**
	 * @modifies swaps height and width values of Tokat.
	 */
	public void swapWidthHeight() {
		double temp = getWidth();
		setWidth(getHeight());
		setHeight(temp);
	}

	/**
	 * @requires angle of the Tokat is not smaller than -180.
	 * @modifies angle of the Tokat
	 */
	public void rotateLeft() {
		setAngle(getAngle() + getAngularVelocity());

		if (getAngle() <= -90) {
			setAngle(-90);
		}

		Vect[] rotatedPoints = new Vect[points.length];
		for (int i = 0; i < points.length; i++) {
			rotatedPoints[i] = Geometry.rotateAround(points[i], cor, new Angle(getAngle() * Math.PI / 180.0));
		}

		setTopLine(new LineSegment(rotatedPoints[0], rotatedPoints[1]));
		setBottomLine(new LineSegment(rotatedPoints[2], rotatedPoints[3]));
		setLeftLine(new LineSegment(rotatedPoints[0], rotatedPoints[2]));
		setRightLine(new LineSegment(rotatedPoints[1], rotatedPoints[3]));
		setHeadCircle(new Circle(rotatedPoints[4].x(), rotatedPoints[4].y(), getWidth() / 2));
		setTailCircle(new Circle(rotatedPoints[5].x(), rotatedPoints[5].y(), getWidth() / 2));

		notifyAllObservers();
	}

	public void setTailCircle(Circle tailCircle) {
		this.tailCircle = tailCircle;
	}

	/**
	 * @requires angle of the Tokat is not greater than 0 degrees.
	 * @modifies angle of the Tokat
	 */

	public void rotateRight() {
		setAngle(getAngle() - getAngularVelocity());

		if (getAngle() >= 90) {
			setAngle(90);
		}

		Vect[] rotatedPoints = new Vect[points.length];
		for (int i = 0; i < points.length; i++) {
			rotatedPoints[i] = Geometry.rotateAround(points[i], cor, new Angle(getAngle() * Math.PI / 180.0));
		}

		setTopLine(new LineSegment(rotatedPoints[0], rotatedPoints[1]));
		setBottomLine(new LineSegment(rotatedPoints[2], rotatedPoints[3]));
		setLeftLine(new LineSegment(rotatedPoints[0], rotatedPoints[2]));
		setRightLine(new LineSegment(rotatedPoints[1], rotatedPoints[3]));
		setHeadCircle(new Circle(rotatedPoints[4].x(), rotatedPoints[4].y(), getWidth() / 2));
		setTailCircle(new Circle(rotatedPoints[5].x(), rotatedPoints[5].y(), getWidth() / 2));
		notifyAllObservers();

	}

	/**
	 * @requires param observer is not null
	 * @modifies observers list is updated
	 */
	@Override
	public void attachObserver(Observer observer) {
		observers.add(observer);
	}

	/**
	 * This method calls each observer's update method in the observers list.
	 */
	@Override
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

	public DirectionEnum getDirection() {
		return direction;
	}

	public void setDirection(DirectionEnum direction) {
		this.direction = direction;
		double radius = getWidth() / 2;
		double w = getWidth();
		double h = getHeight();

		if (isLeft) {
			if (direction == DirectionEnum.SOUTH) {
				cor = new Vect(getX() + radius, getY() + radius);
				points[0] = new Vect(getX(), getY());
				points[1] = new Vect(getX() + w, getY());
				points[2] = new Vect(getX(), getY() + h);
				points[3] = new Vect(getX() + w, getY() + h);
			} else if (direction == DirectionEnum.NORTH) {
				cor = new Vect(getX() - radius, getY() - radius);
				points[0] = new Vect(getX(), getY());
				points[1] = new Vect(getX() - w, getY());
				points[2] = new Vect(getX(), getY() - h);
				points[3] = new Vect(getX() - w, getY() - h);
			} else if (direction == DirectionEnum.EAST) {
				cor = new Vect(getX() + radius, getY() - radius);
				points[0] = new Vect(getX(), getY());
				points[1] = new Vect(getX(), getY() - w);
				points[2] = new Vect(getX() + h, getY());
				points[3] = new Vect(getX() + h, getY() - w);

			} else if (direction == DirectionEnum.WEST) {
				cor = new Vect(getX() - radius, getY() + radius);
				points[0] = new Vect(getX(), getY());
				points[1] = new Vect(getX(), getY() + w);
				points[2] = new Vect(getX() - h, getY());
				points[3] = new Vect(getX() - h, getY() + w);
			}
		} else {
			if (direction == DirectionEnum.SOUTH) {
				cor = new Vect(getX() - radius, getY() + radius);
				points[0] = new Vect(getX() - w, getY());
				points[1] = new Vect(getX(), getY());
				points[2] = new Vect(getX() - w, getY() + h);
				points[3] = new Vect(getX(), getY() + h);
			} else if (direction == DirectionEnum.NORTH) {
				cor = new Vect(getX() + radius, getY() - radius);
				points[0] = new Vect(getX() + w, getY());
				points[1] = new Vect(getX(), getY());
				points[2] = new Vect(getX() + w, getY() - h);
				points[3] = new Vect(getX(), getY() - h);
			} else if (direction == DirectionEnum.EAST) {
				cor = new Vect(getX() + radius, getY() + radius);
				points[0] = new Vect(getX(), getY() + w);
				points[1] = new Vect(getX(), getY());
				points[2] = new Vect(getX() + h, getY() + w);
				points[3] = new Vect(getX() + h, getY());

			} else if (direction == DirectionEnum.WEST) {
				cor = new Vect(getX() - radius, getY() - radius);
				points[0] = new Vect(getX(), getY() - w);
				points[1] = new Vect(getX(), getY());
				points[2] = new Vect(getX() - h, getY() - w);
				points[3] = new Vect(getX() - h, getY());
			}
		}

		updateCenters();
		setHeadCircle(new Circle(points[4].x(), points[4].y(), radius));
		setTailCircle(new Circle(points[5].x(), points[5].y(), radius));
	}

	/**
	 * 
	 * @return gets frameRate from AnimationWindow
	 */

	public Circle getHeadCircle() {
		return headCircle;
	}

	public void setHeadCircle(Circle headCircle) {
		this.headCircle = headCircle;
	}

	public boolean isRotatingNow() {
		return rotatingNow;
	}

	public void setRotatingNow(boolean rotatingNow) {
		this.rotatingNow = rotatingNow;
	}

	public Vect getCor() {
		return cor;
	}

	public void setCor(Vect cor) {
		this.cor = cor;
	}

	private int getFrameRate() {
		return Constants.frameRate;
	}

	public LineSegment getTopLine() {
		return topLine;
	}

	public void setTopLine(LineSegment topLine) {
		this.topLine = topLine;
	}

	public LineSegment getRightLine() {
		return rightLine;
	}

	public void setRightLine(LineSegment rightLine) {
		this.rightLine = rightLine;
	}

	public LineSegment getBottomLine() {
		return bottomLine;
	}

	public void setBottomLine(LineSegment bottomLine) {
		this.bottomLine = bottomLine;
	}

	public LineSegment getLeftLine() {
		return leftLine;
	}

	public void setLeftLine(LineSegment leftLine) {
		this.leftLine = leftLine;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

}
