package factory;

import java.util.Random;

import events.Event;
import events.FreezeEvent;
import events.MagnifyEvent;
import events.ShrinkEvent;

public class EventFactory {
	private static EventFactory instance;

	/**
	 * Creates a singleton EventFactory.
	 */
	private EventFactory() {

	}

	/**
	 * @modifies instance attribute is modified.
	 * @return Returns the singleton EventFactory.
	 */
	public static EventFactory getInstance() {
		if (instance == null)
			instance = new EventFactory();
		return instance;
	}

	/**
	 * @effects An event is randomly generated.
	 * @return Returns the randomly generated event.
	 */
	public Event generateEvent() {
		Event event = new MagnifyEvent();
		Random random = new Random();
		int x = random.nextInt(3);
		switch (x) {
		case 0:
			event = new FreezeEvent();
			break;
		case 1:
			event = new ShrinkEvent();
			break;
		}
		return event;
	}
}
