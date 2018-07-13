package domain;

import static org.junit.Assert.*;

import org.junit.Test;

import boardobject.LeftTokat;
import boardobject.RightTokat;
import boardobject.Tokat;
import enums.DirectionEnum;

public class TokatTest {

	Tokat leftTokat = new LeftTokat(5, 6);
	Tokat rightTokat = new RightTokat(10, 12);

	/**
	 * Test whether LeftTokat implements true rotation order during building
	 * Mode. Each rotate command should follow these states: NORTH -> EAST ->
	 * SOUTH -> WEST
	 */
	@Test
	public void testLeftTokatStartRotating() {
		assertEquals("Initial direction of leftTokat is not SOUTH", DirectionEnum.SOUTH, leftTokat.getDirection());
		leftTokat.startRotating();
		assertEquals("Second direction of leftTokat is not EAST", DirectionEnum.EAST, leftTokat.getDirection());
		leftTokat.startRotating();
		assertEquals("Third direction of leftTokat is not NORTH", DirectionEnum.NORTH, leftTokat.getDirection());
		leftTokat.startRotating();
		assertEquals("Fourth direction  of leftTokat is not WEST", DirectionEnum.WEST, leftTokat.getDirection());
	}

	/**
	 * Test whether RightTokat implements true rotation order during building
	 * Mode. Each rotate command should follow these states: NORTH -> EAST ->
	 * SOUTH -> WEST
	 */
	@Test
	public void testRightTokatStartRotating() {
		assertEquals("Initial direction of rightTokat is not SOUTH", DirectionEnum.SOUTH, rightTokat.getDirection());
		rightTokat.startRotating();
		assertEquals("Second direction of rightTokat is not EAST", DirectionEnum.EAST, rightTokat.getDirection());
		rightTokat.startRotating();
		assertEquals("Third direction of rightTokat is not NORTH", DirectionEnum.NORTH, rightTokat.getDirection());
		rightTokat.startRotating();
		assertEquals("Fourth direction of rightTokat is not WEST", DirectionEnum.WEST, rightTokat.getDirection());
	}

	/**
	 * Test if the rotation is completed after calling rotate method multiple
	 * times such that total rotation angle is 90 degrees. Second 90 degree
	 * rotate call should change the boolean of isRunningModeRotatingFinished to
	 * false.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testLeftTokatMultipleRotate() {
		leftTokat = new LeftTokat(5, 6);
		double prev = leftTokat.getAngle();
		leftTokat.rotate();
		leftTokat.rotate();

		assertEquals("Rotation is not completed for LeftTokat.", leftTokat.getAngle(), prev, 0.1);

	}

	/**
	 * Test if the rotation is completed after calling rotate method multiple
	 * times such that total rotation angle is 90 degrees. Second 90 degree
	 * rotate call should change the boolean of isRunningModeRotatingFinished to
	 * false.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testRightTokatMultipleRotate() {
		rightTokat = new RightTokat(5, 6);
		double prev = rightTokat.getAngle();
		rightTokat.rotate();
		rightTokat.rotate();

		assertEquals("Rotation is not completed for RightTokat.", rightTokat.getAngle(), prev, 0.1);
	}
}
