package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import boardobject.Engel;

public class EngelTest {

	Engel engel;

	@Before
	public void setUp() throws Exception {
		engel = new Engel();
	}

	/**
	 * Test whether repOK() returns true or not after creating the Engel object.
	 */
	@Test
	public void testRepOK() {
		assertTrue("repOK returned false", engel.repOK());
	}

}
