package boardobject;

import java.awt.Color;
import java.util.ArrayList;
import enums.LevelEnum;
import model.BoardModel;
import model.Player;
import observer.CezmiObserver;
import observer.Observer;
import physics.Vect;

/**
 * @author UC This class extends BoardObject and implements Observable
 *         interface. It has contains all required information about player
 *         avatar, Cezmi.
 */
public class Cezmi extends BoardObject implements Observable {

	private double velocity;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private Player owner;
	private Vect leftLimit, rightLimit;

	/**
	 * 
	 * @param x:
	 *            x-coordinate of the center of Cezmi.
	 * @param y:
	 *            y-coordinate of the center of Cezmi.
	 * @param color:
	 *            Color of Cezmi.
	 * @param velocityX:
	 *            x-velocity of Cezmi.
	 * @param owner:
	 *            Player who owns the Cezmi.
	 * @requires x and y coordinates smaller than 25.
	 * @modifies x, y, color, velocityX and owner attributes are modified.
	 * @effects New Cezmi object is created and reflected on screen.
	 */
	public Cezmi(double x, double y, Color color, double velocity, Player owner) {
		super(x, y, 2.0, 1.0, color);
		setVelocity(velocity);
		attachObserver(new CezmiObserver());
		setOwner(owner);
		setObservers(new ArrayList<Observer>());
		attachObserver(new CezmiObserver());
	}

	/**
	 * toString method for Cezmi.
	 */
	public String toString() {
		return "[Cezmi= x: " + getX() + ", y: " + getY() + ", width: " + getWidth() + ", height: " + getHeight()
				+ ", color: " + getColor().toString() + ", velocity: " + getVelocity() + " and owner: "
				+ getOwner().toString() + "]";
	}

	/**
	 * 
	 * @modifies The X-coordinate of the Cezmi.
	 * @effects Decrements the X-coordinate of the Cezmi by it's velocity.
	 */
	public void moveLeft() {
		if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
			double newX = this.getX() - this.getVelocity();
			if (newX - getWidth() / 2 >= getLeftLimit().x()) {
				this.setX(newX);
				this.notifyAllObservers();
			}
		} else {
			if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL2) {
				if (getOwner().getId() == 0) {
					if (getY() == 25) {
						double newX = getX() - getVelocity();
						if (newX >= BoardModel.getInstance().getWalls().get(3).getX()) {
							setX(newX);
							notifyAllObservers();
						} else {
							setX(0);
							double newY = getY() - getVelocity();
							setY(newY);
							notifyAllObservers();
						}
					} else {
						if (getX() == 0) {
							double newY = getY() - getVelocity();
							if (newY - getWidth() / 2 >= getLeftLimit().y()) {
								setY(newY);
								notifyAllObservers();
							}
						}
					}
				} else {
					if (getOwner().getId() == 1) {
						if (getX() == 25) {
							double newY = getY() + getVelocity();
							if (newY <= 25) {
								setY(newY);
								notifyAllObservers();
							} else {
								setY(25);
								double newX = getX() - getVelocity();
								setX(newX);
								notifyAllObservers();
							}
						} else {
							if (getY() == 25) {
								double newX = getX() - getVelocity();
								if (newX - getWidth() / 2 >= getLeftLimit().x()) {
									setX(newX);
									notifyAllObservers();
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * @modifies The Left Limit and the Right Limit of the Cezmi.
	 */
	public void setLimits() {
		Engel engel = BoardModel.getInstance().getEngel();
		ArrayList<Wall> walls = BoardModel.getInstance().getWalls();
		if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
			if (this.getOwner().getId() == 0) {
				this.setLeftLimit(new Vect(walls.get(3).getX() + walls.get(3).getWidth(), 25));
				this.setRightLimit(new Vect(engel.getX(), 25));
			} else if (this.getOwner().getId() == 1) {
				this.setLeftLimit(new Vect(engel.getX() + engel.getWidth(), 25));
				this.setRightLimit(new Vect(walls.get(1).getX(), 25));
			}
		} else {
			if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL2) {
				if (this.getOwner().getId() == 0) {
					this.setLeftLimit(new Vect(walls.get(3).getX() + walls.get(3).getWidth(), 0));
					this.setRightLimit(new Vect(engel.getX(), 25));
				} else if (this.getOwner().getId() == 1) {
					this.setLeftLimit(new Vect(engel.getX() + engel.getWidth(), 25));
					this.setRightLimit(new Vect(walls.get(1).getX(), 0));
				}
			}
		}

	}

	/**
	 * 
	 * @modifies The X-coordinate of the Cezmi.
	 * @effects Increments the X-coordinate of the Cezmi by it's velocity.
	 */
	public void moveRight() {
		if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
			double newX = this.getX() + this.getVelocity();
			if (newX + getWidth() / 2 <= getRightLimit().x()) {
				this.setX(newX);
				this.notifyAllObservers();
			}
		} else {
			if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL2) {
				if (getOwner().getId() == 0) {
					if (getX() == 0) {
						double newY = getY() + getVelocity();
						if (newY <= 25) {
							setY(newY);
							notifyAllObservers();
						} else {
							setY(25);
							double newX = getX() + getVelocity();
							setX(newX);
							notifyAllObservers();
						}
					} else {
						if (getY() == 25) {
							double newX = getX() + getVelocity();
							if (newX + getWidth() / 2 <= getRightLimit().x()) {
								setX(newX);
								notifyAllObservers();
							}
						}
					}
				} else {
					if (getOwner().getId() == 1) {
						if (getY() == 25) {
							double newX = getX() + getVelocity();
							if (newX <= 25) {
								setX(newX);
								notifyAllObservers();
							} else {
								setX(25);
								double newY = getY() - getVelocity();
								setY(newY);
								notifyAllObservers();
							}
						} else {
							if (getX() == 25) {
								double newY = getY() - getVelocity();
								if (newY - getWidth() / 2 >= getRightLimit().y()) {
									setY(newY);
									notifyAllObservers();
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * repOk method for Cezmi.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
			if (!super.repOK() || owner.equals(null) || leftLimit.x() > 25 || leftLimit.x() < 0 || rightLimit.x() > 25
					|| rightLimit.x() < 0) {
				return false;
			} else {
				return true;
			}
		} else {
			if (!super.repOK() || owner.equals(null) || leftLimit.x() > 25 || leftLimit.x() < 0 || rightLimit.x() > 25
					|| rightLimit.x() < 0 || leftLimit.y() > 25 || leftLimit.y() < 0 || rightLimit.y() > 25
					|| rightLimit.y() < 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public Vect getLeftLimit() {
		return leftLimit;
	}

	public void setLeftLimit(Vect leftLimit) {
		this.leftLimit = leftLimit;
	}

	public Vect getRightLimit() {
		return rightLimit;
	}

	public void setRightLimit(Vect rightLimit) {
		this.rightLimit = rightLimit;
	}

	public ArrayList<Observer> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * 
	 * @param observer:
	 *            The observer which observes this object.
	 * @modifies The list of observers of the Cezmi.
	 * @effects Adds a new entry to the list of observers of the Cezmi.
	 */
	@Override
	public void attachObserver(Observer observer) {
		getObservers().add(observer);

	}

	/**
	 * 
	 * @modifies The graphical reflection of the Cezmi.
	 * @effects Notifies all the observers of the Cezmi.
	 */
	@Override
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

}