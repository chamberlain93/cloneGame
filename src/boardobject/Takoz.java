package boardobject;

import java.awt.Color;
import java.util.ArrayList;
import observer.*;

public abstract class Takoz extends Gizmo implements Observable {

	/**
	 * 
	 * @param x:
	 *            x-coordinate of top left corner of this object.
	 * @param y:
	 *            y-coordinate of top left corner of this object.
	 * @param width:
	 *            Width of this object.
	 * @param height:
	 *            Height of this object.
	 * @param color:
	 *            Color of this object:
	 */
	public Takoz(double x, double y, double width, double height, Color color) {
		super(x, y, width, height, color);
		attachObserver(new GizmoObserver());
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	/**
	 * Notifies all observers that are observing this object in order to reflect
	 * the object to the screen.
	 */
	public void notifyAllObservers() {
		for (Observer observer : getObservers()) {
			observer.update(this);
		}
	}

	/**
	 * Adds observers to this object.
	 */
	public void attachObserver(Observer observer) {
		ArrayList<Observer> list = getObservers();
		list.add(observer);
		setObservers(list);
	}

}
