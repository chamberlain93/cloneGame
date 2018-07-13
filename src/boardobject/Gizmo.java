package boardobject;

import java.awt.Color;
import java.util.ArrayList;
import model.Player;
import observer.Observer;

public abstract class Gizmo extends BoardObject {

	private Player owner;
	private ArrayList<Observer> observers;

	/**
	 * 
	 * @param x:
	 *            x-coordinate of this object.
	 * @param y:
	 *            y-coordinate of this object.
	 * @param width:
	 *            Width of this object.
	 * @param height:
	 *            Height of this object.
	 * @param color:
	 *            Color of this object.
	 * @requires x, y coordinates and width and height are positive and smaller
	 *           than 25.
	 * @modifies x, y, width and height attributes are modified.
	 * @effects New Gizmo instance is created and reflected to the screen.
	 */
	public Gizmo(double x, double y, double width, double height, Color color) {
		super(x, y, width, height, color);
		setObservers(new ArrayList<Observer>());
	}

	public abstract void rotate();

	public abstract void delete();

	public abstract void move(int x, int y);

	public abstract void notifyAllObservers();

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public ArrayList<Observer> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;
	}

}
