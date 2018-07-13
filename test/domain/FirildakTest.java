package domain;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import boardobject.Firildak;

public class FirildakTest {

	Firildak firildak;

	@Before
	public void setUp() throws Exception {
		firildak = new Firildak(6, 5, 1, 1, Color.BLUE);
	}

	/**
	 * Test whether repOK() returns true or not after creating the firildak
	 * object.
	 */
	@Test
	public void testRepOK() {
		assertTrue("repOK() returned false for Firildak", firildak.repOK());
	}

	/**
	 * Test whether repOK() still returns true or not after rotating the
	 * Firildak.
	 */
	@Test
	public void testRepOKAfterRotate() {
		firildak.rotate();
		assertTrue("repOK() returned false for Firildak after rotation", firildak.repOK());
	}

}
