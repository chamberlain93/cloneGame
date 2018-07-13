package domain;

import static org.junit.Assert.*;

import org.junit.Test;

import boardobject.Wall;

public class WallTest {

	/**
	 * Test if wall length is 25 or not. At least one of the values (width or
	 * height) should be equal to 25.
	 */
	@Test
	public void testWallLength() {
		Wall wall = new Wall(0, 0, 25, 0.2);
		assertTrue("Wall length is not 25", (wall.getWidth() == 25) || (wall.getHeight() == 25));
	}
}
