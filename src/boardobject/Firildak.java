package boardobject;

import java.awt.Color;
import java.util.ArrayList;
import observer.GizmoObserver;
import observer.Observer;
import physics.Angle;
import physics.Geometry;
import physics.Vect;

/**
 * 
 * @author UC The abstract Firildak class defines one of the subclass of Gizmo
 *         superclass and implements Observable interface. Firildak is one of
 *         the Gizmo's that have 1Lx1L size and some constant angular velocity
 *         rotating around its center.
 */

public class Firildak extends Gizmo implements Observable {

	private Vect initTopLeft;
	private Vect initTopRight;
	private Vect initBottomLeft;
	private Vect initBottomRight;
	private double angularVelocity;
	private Angle angle;
	private Vect center;

	private ArrayList<Observer> observers = new ArrayList<Observer>();

	/**
	 * 
	 * @param color:
	 *            color of the firildak object
	 * @param x:
	 *            upper left corner x coordinate of the object
	 * @param y:
	 *            upper left corner y coordinate of the object
	 * @param width:
	 *            width of the firildak rectangle
	 * @param height:
	 *            height of the firildak rectangle
	 * @param color:
	 *            color of the firildak rectangle
	 * @requires x and y should be in the editable area
	 * @modifies x,y, color, width, height, color
	 */

	public Firildak(int x, int y, double width, double height, Color color) {
		super(x, y, width, height, color);
		setInitTopLeft(new Vect(getX(), getY()));
		setInitTopRight(new Vect(getX() + getWidth(), getY()));
		setInitBottomLeft(new Vect(getX(), getY() + getHeight()));
		setInitBottomRight(new Vect(getX() + getWidth(), getY() + getHeight()));
		center = new Vect(getX() + getWidth() / 2, getY() + getHeight() / 2);

		if (getX() < 12) {
			setAngle(new Angle(0.05 * Math.PI));
		} else {
			setAngle(new Angle(-0.05 * Math.PI));
		}

		attachObserver(new GizmoObserver());
	}

	/**
	 * repOk method for Firildak.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		return (getX() >= 0 && getX() < 25 && getY() >= 0 && getY() < 19 && getWidth() == 1 && getHeight() == 1);
	}

	/**
	 * This method increases the Firildak angle by angular velocity.
	 * notifyAllObservers() method is called after each increment.
	 */
	@Override
	public void rotate() {
		setInitTopLeft(Geometry.rotateAround(getInitTopLeft(), center, getAngle()));
		setInitTopRight(Geometry.rotateAround(getInitTopRight(), center, getAngle()));
		setInitBottomLeft(Geometry.rotateAround(getInitBottomLeft(), center, getAngle()));
		setInitBottomRight(Geometry.rotateAround(getInitBottomRight(), center, getAngle()));
	}

	/**
	 * Deletes this object.
	 */
	@Override
	public void delete() {
		notifyAllObservers();
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

	/**
	 * @param observer:
	 *            any class that implements Observer interface with update()
	 *            method
	 * @requires observer is not null
	 * @modifies observers
	 */
	@Override
	public void attachObserver(Observer observer) {
		observers.add(observer);
	}

	public Vect getInitTopLeft() {
		return initTopLeft;
	}

	public void setInitTopLeft(Vect initTopLeft) {
		this.initTopLeft = initTopLeft;
	}

	public Vect getInitTopRight() {
		return initTopRight;
	}

	public void setInitTopRight(Vect initTopRight) {
		this.initTopRight = initTopRight;
	}

	public Vect getInitBottomLeft() {
		return initBottomLeft;
	}

	public void setInitBottomLeft(Vect initBottomLeft) {
		this.initBottomLeft = initBottomLeft;
	}

	public Vect getInitBottomRight() {
		return initBottomRight;
	}

	public void setInitBottomRight(Vect initBottomRight) {
		this.initBottomRight = initBottomRight;
	}

	public double getAngularVelocity() {
		return angularVelocity;
	}

	public void setAngularVelocity(double angularVelocity) {
		this.angularVelocity = angularVelocity;
	}

	public Angle getAngle() {
		return angle;
	}

	public void setAngle(Angle angle) {
		this.angle = angle;
	}

	public Vect getCenter() {
		return center;
	}

	public void setCenter(Vect center) {
		this.center = center;
	}

	public ArrayList<Observer> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;
	}

	public void move(int x, int y) {
		setX(x);
		setY(y);
		setInitTopLeft(new Vect(getX(), getY()));
		setInitTopRight(new Vect(getX() + 1, getY()));
		setInitBottomLeft(new Vect(getX(), getY() + 1));
		setInitBottomRight(new Vect(getX() + 1, getY() + 1));
	}

}
