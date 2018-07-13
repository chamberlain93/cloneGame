package boardobject;

import observer.Observer;

public interface Observable {
	
	public void attachObserver(Observer observer);
	public void notifyAllObservers();

}
