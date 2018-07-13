package domain;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;

import boardobject.Cezmi;
import enums.LevelEnum;
import model.BoardModel;
import model.Player;
import physics.Vect;

public class CezmiTest {

	Player firstPlayer = new Player(0, 0); // creating first player instance to
											// give as parameter to first cezmi
	Player secondPlayer = new Player(0, 0);// creating second player instance to
											// give as parameter to second cezmi
	Cezmi firstCezmi = new Cezmi(5.0, 0.0, Color.PINK, 1.0, firstPlayer); // creating
																			// firstCezmi
																			// instance
	Cezmi secondCezmi = new Cezmi(15.0, 0.0, Color.PINK, 2.5, secondPlayer); // creating
																				// secondCezmi
																				// instance

	@Test
	public void testFirstCezmi() {
		assertEquals(5.0, firstCezmi.getX(), 0.01); // checking whether the
													// construction was
													// successful
		assertEquals(1.0, firstCezmi.getVelocity(), 0.01); // checking whether
															// the construction
															// was successful
	}

	@Test
	public void testSecondCezmi() {
		assertEquals(15.0, secondCezmi.getX(), 0.01); // checking whether the
														// construction was
														// successful
		assertEquals(2.5, secondCezmi.getVelocity(), 0.01); // checking whether
															// the construction
															// was successful
	}

	@Test
	public void testRightLimit() {
		firstCezmi.setRightLimit(new Vect(12.5, 0)); // setting moving to right
														// limit for firstCezmi
		secondCezmi.setRightLimit(new Vect(25, 0)); // setting moving to right
													// limit for secondCezmi
		assertEquals(12.5, firstCezmi.getRightLimit().x(), 0.01); // checking
																	// whether
																	// the
																	// setting
																	// process
																	// was
																	// successful
		assertEquals(25.0, secondCezmi.getRightLimit().x(), 0.01); // checking
																	// whether
																	// the
																	// setting
																	// process
																	// was
																	// successful
	}

	@Test
	public void testLeftLimit() {
		firstCezmi.setLeftLimit(new Vect(0, 0)); // setting moving to left limit
													// for firstCezmi
		secondCezmi.setLeftLimit(new Vect(12.5, 0)); // setting moving to left
														// limit for secondCezmi
		assertEquals(0.0, firstCezmi.getLeftLimit().x(), 0.01); // checking
																// whether the
																// setting
																// process was
																// successful
		assertEquals(12.5, secondCezmi.getLeftLimit().x(), 0.01); // checking
																	// whether
																	// the
																	// setting
																	// process
																	// was
																	// successful
	}

	@Test
	public void testMoveLeft() {
		firstCezmi.setRightLimit(new Vect(12.5, 0));
		secondCezmi.setRightLimit(new Vect(25.0, 0));
		firstCezmi.setLeftLimit(new Vect(0.0, 0));
		secondCezmi.setLeftLimit(new Vect(12.5, 0));

		BoardModel.getInstance().setLevel(LevelEnum.LEVEL1);
		BoardModel.getInstance().setCezmis(new ArrayList<Cezmi>());
		BoardModel.getInstance().getCezmis().add(firstCezmi);
		BoardModel.getInstance().getCezmis().add(secondCezmi);
		firstCezmi.moveLeft();// calling moving to left method for firstCezmi
		secondCezmi.moveLeft(); // calling moving to left method for secondCezmi
		assertEquals(4.0, firstCezmi.getX(), 0.01); // checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(15.0, secondCezmi.getX(), 0.01); // checking whether the
														// new location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveLeft(); // calling moving to left method for firstCezmi
		secondCezmi.moveLeft(); // calling moving to left method for secondCezmi
		assertEquals(3.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(15.0, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveLeft();// calling moving to left method for firstCezmi
		secondCezmi.moveLeft();// calling moving to left method for secondCezmi
		assertEquals(2.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(15.0, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveLeft();// calling moving to left method for firstCezmi
		secondCezmi.moveLeft();// calling moving to left method for secondCezmi
		assertEquals(1.0, firstCezmi.getX(), 0.01); // checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(15.0, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveLeft();// calling moving to left method for firstCezmi
		secondCezmi.moveLeft();// calling moving to left method for secondCezmi
		assertEquals(1.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(15.0, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

	}

	public void testMoveRight() {
		firstCezmi.setLeftLimit(new Vect(0.0, 0));// setting moving to left
													// limit for firstCezmi
		firstCezmi.setRightLimit(new Vect(12.5, 0));// setting moving to right
													// limit for firstCezmi
		secondCezmi.setLeftLimit(new Vect(12.5, 0));// setting moving to left
													// limit for secondCezmi
		secondCezmi.setRightLimit(new Vect(25.0, 0)); // setting moving to right
														// limit for secondCezmi

		firstCezmi.moveRight();// calling moving to right method for firstCezmi
		secondCezmi.moveRight();// calling moving to right method for
								// secondCezmi
		assertEquals(6.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(17.5, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveRight();// calling moving to right method for firstCezmi
		secondCezmi.moveRight();// calling moving to right method for
								// secondCezmi
		assertEquals(7.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(20.0, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveRight();// calling moving to right method for firstCezmi
		secondCezmi.moveRight();// calling moving to right method for
								// secondCezmi
		assertEquals(8.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(22.5, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.setVelocity(2.0);// setting the velocity of firstCezmi to 2.0

		firstCezmi.moveRight();// calling moving to right method for firstCezmi
		secondCezmi.moveRight();// calling moving to right method for
								// secondCezmi
		assertEquals(9.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(22.5, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveRight();// calling moving to right method for firstCezmi
		secondCezmi.moveRight();// calling moving to right method for
								// secondCezmi
		assertEquals(10.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(22.5, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

		firstCezmi.moveRight();// calling moving to right method for firstCezmi
		secondCezmi.moveRight();// calling moving to right method for
								// secondCezmi
		assertEquals(11.0, firstCezmi.getX(), 0.01);// checking whether the new
													// location of firstCezmi is
													// the expected location
		assertEquals(22.5, secondCezmi.getX(), 0.01);// checking whether the new
														// location of
														// secondCezmi is the
														// expected location

	}

}
