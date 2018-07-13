package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import boardobject.Cezerye;
import model.BoardModel;

public class CezeryeTest {

	Cezerye cezerye;

	@Before
	public void setUp() throws Exception {
		cezerye = new Cezerye();
	}

	/**
	 * Test whether the event is null or not after creating the cezerye object.
	 */
	@Test
	public void testEvent() {
		assertNotNull("Cezerye event is null", cezerye.getEvent());
	}

	/**
	 * Test whether cezerye is deleted from the BoardModel (set as null) or not
	 * after calling delete method.
	 */
	@Test
	public void testDelete() {
		cezerye.delete();
		assertNull("Cezerye is not deleted from BoardModel", BoardModel.getInstance().getCezerye());
	}

	/**
	 * Test whether repOK() returns true or not after creating cezerye object.
	 */

	@Test
	public void testRepOK() {
		assertTrue("repOK returned false for Cezerye", cezerye.repOK());
	}

}
