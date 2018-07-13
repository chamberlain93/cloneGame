package domain;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import boardobject.Ball;
import boardobject.Cezerye;
import boardobject.Cezmi;
import events.Event;
import events.ShrinkEvent;
import model.BoardModel;
import model.Player;

public class ShrinkEventTest {

	Event event = new ShrinkEvent(); // manually creating a ShrinkEvent
	Player testPlayer1 = new Player(0, 1); // creating first player for cezmi1
											// construction
	Player testPlayer2 = new Player(1, 1); // creating second player for cezmi2
											// construction
	Cezmi testCezmi1 = new Cezmi(5, 18, Color.BLUE, 1, testPlayer1); // creating
																		// the
																		// first
																		// test
																		// cezmi
	Cezmi testCezmi2 = new Cezmi(15, 18, Color.ORANGE, 1, testPlayer2); // creating
																		// the
																		// second
																		// test
																		// cezmi
	ArrayList<Cezmi> cezmis; // creating the ArrayList of cezmis to give it to
								// applyEvent as parameter
	Ball testBall = new Ball(100, 100, 5, -10); // creating a ball instance to
												// give a test history
	Cezerye testCezerye = new Cezerye(); // creating the test cezerye to give it
											// to ball test history
	Object[] testHistory = new Object[3]; // creating the testhistory for the
											// ball.

	@Before
	public void setUp() {
		cezmis = new ArrayList<Cezmi>();
		testCezerye.setEvent(event);
		BoardModel.getInstance().setCezmis(cezmis);
		BoardModel.getInstance().setCezerye(testCezerye);
		BoardModel.getInstance().setBalls(new ArrayList<Ball>());
		BoardModel.getInstance().getBalls().add(testBall);

		testHistory[0] = testCezmi1; // setting the first input of ball history
										// to testCezmi1
		testHistory[2] = testCezerye; // setting the second input of ball
										// history to testCezerye
		testBall.setHistory(testHistory); // giving the history to the ball

		cezmis.add(testCezmi1); // adding testCezmi1 to cezmis arraylist
		cezmis.add(testCezmi2); // adding testCezmi2 to cezmis arraylist
	}

	@Test
	public void testShrink() {

		double prevW = testCezmi2.getWidth();
		double prevH = testCezmi2.getHeight();
		event.applyEvent(cezmis); // applying event to cezmis arraylist

		assertEquals(prevH / 1.4, testCezmi2.getHeight(), 0.01);// checking
																// whether
																// testCezmi1's
																// height is
																// equal to 2/2
		assertEquals(prevW / 1.4, testCezmi2.getWidth(), 0.01);// checking
																// whether
																// testCezmi1's
																// width is
																// equal to 2/2
		// event.finishEvent(); // finishing the event
		// assertEquals(prevH, testCezmi2.getHeight(), 0.01);// checking whether
		// testCezmi1's height is back to normal
		// assertEquals(prevW, testCezmi2.getWidth(), 0.01);// checking whether
		// testCezmi1's width is back to normal
	}
}
