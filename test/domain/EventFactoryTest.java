package domain;

import static org.junit.Assert.*;

import org.junit.Test;

import events.Event;
import factory.EventFactory;

public class EventFactoryTest {

	@Test
	public void generateEventTest() {
		// This method checks if an eventFactory is able to generate an event or
		// not. It checks if the event created is null or not.
		Event event = EventFactory.getInstance().generateEvent();
		assertTrue("EventFactory failed generating an event", event != null);
	}

}
