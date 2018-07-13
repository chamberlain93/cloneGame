package domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import boardobject.SquareTakoz;
import boardobject.TriangleTakoz;
import enums.BoardObjectEnum;
import enums.DirectionEnum;
import factory.BoardObjectFactory;

public class TakozTest {

	SquareTakoz sqTakoz;
	TriangleTakoz triTakoz;

	@Before
	public void setUp() throws Exception {
		sqTakoz = (SquareTakoz) BoardObjectFactory.getInstance().createGizmo(BoardObjectEnum.SQUARE_TAKOZ, 5, 5);
		triTakoz = (TriangleTakoz) BoardObjectFactory.getInstance().createGizmo(BoardObjectEnum.TRIANGLE_TAKOZ, 7, 7);
	}

	/**
	 * Test whether repOK() returns true or not after creating SquareTakoz
	 * object.
	 */
	@Test
	public void testRepOKSquareTakoz() {
		assertTrue("repOK() returned false for SquareTakoz", sqTakoz.repOK());
	}

	/**
	 * * Test whether repOK() returns true or not after creating TriangleTakoz
	 * object.
	 */
	@Test
	public void testRepOKTriangleTakoz() {
		assertTrue("repOK() returned false for TriangleTakoz", triTakoz.repOK());
	}

	/**
	 * Test whether repOK() returns false or not after changing SquareTakoz
	 * object's height.
	 */
	@Test
	public void testRepOKSquareTakozAfterChange() {
		sqTakoz.setHeight(2);
		assertFalse("repOK() returned true for SquareTakoz after modification (expected false)", sqTakoz.repOK());
	}

	/**
	 * Test whether repOK() returns false or not after changing TriangleTakoz
	 * object's height.
	 */
	@Test
	public void testRepOKTriangleTakozAfterChange() {
		triTakoz.setHeight(2);
		assertFalse("repOK() returned true for TriangleTakoz after modification (expected false)", triTakoz.repOK());
	}

	/**
	 * Test the order of rotation directions of TriangleTakoz. Starting from
	 * SOUTH_WEST, rotation should follow this order: SW->SE->NE->NW
	 */
	@Test
	public void testRotatingForTriangleTakoz() {
		assertEquals("Initial direction is not SOUTH_WEST", triTakoz.getDirection(), DirectionEnum.SOUTH_WEST);
		triTakoz.startRotating();
		assertEquals("Direction is not SOUTH_EAST", triTakoz.getDirection(), DirectionEnum.SOUTH_EAST);
		triTakoz.startRotating();
		assertEquals("Direction is not NORTH_EAST", triTakoz.getDirection(), DirectionEnum.NORTH_EAST);
		triTakoz.startRotating();
		assertEquals("Direction is not NORTH_WEST", triTakoz.getDirection(), DirectionEnum.NORTH_WEST);
	}
}
