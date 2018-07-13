package boardobject;

import java.awt.Color;
import java.util.ArrayList;
import events.Event;
import factory.EventFactory;
import model.BoardModel;
import observer.CezeryeObserver;
import observer.Observer;

/**
 * 
 * @author UC The concrete Cezerye class defines one of the subclass of Gizmo
 *         superclass and implements Observable interfaces. Cezerye object is a
 *         speacial object that occurs in the game at random times with random
 *         locations. Hitting the cezerye would generate a number of special
 *         events.
 */

public class Cezerye extends BoardObject implements Observable {

	private Event event;
	private double time;

	private ArrayList<Observer> observers = new ArrayList<Observer>();

	/**
	 * 
	 * @modifies x, y, width, height and color x and y coordinates are chosen
	 *           randomly.
	 */
	public Cezerye() {
		super(0, 0, 1, 1, Color.RED);
		int[] coordinates = BoardModel.getInstance().randomAvailableCoordinates();
		this.setX(coordinates[0]);
		this.setY(coordinates[1]);
		this.setEvent(EventFactory.getInstance().generateEvent());

		attachObserver(new CezeryeObserver());
		BoardModel.getInstance().setCezerye(this);
		notifyAllObservers();
	}

	public Cezerye(double x, double y) {
		super(x, y, 1, 1, Color.RED);
		this.setEvent(EventFactory.getInstance().generateEvent());

		attachObserver(new CezeryeObserver());
		BoardModel.getInstance().setCezerye(this);
		notifyAllObservers();
	}

	/**
	 * This method removes cezerye from the BoardModel and notifies all
	 * observers.
	 * 
	 * @modifies width and height are modified.
	 */

	public void delete() {
		BoardModel.getInstance().setCezerye(null);
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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) { // for JUnit testing.
		this.event = event;
	}

	/**
	 * repOk method for Cezerye.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		return (getX() >= 0 && getX() < 25 && getY() >= 0 && getY() < 19 && getWidth() == 1 && getHeight() == 1);
	}

	/**
	 * 
	 * @param time:
	 *            Time for appearance/disappearance of Cezerye.
	 */
	public void setTime(double time) {
		this.time = time;
	}

	public double getTime() {
		return time;
	}

}
