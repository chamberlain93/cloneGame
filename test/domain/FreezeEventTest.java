package domain;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.*;

import boardobject.Ball;
import boardobject.Cezerye;
import boardobject.Cezmi;
import events.Event;
import events.FreezeEvent;
import model.BoardModel;
import model.Player;

public class FreezeEventTest {

	@Test
	public void testFreeze() {
		Event event = new FreezeEvent(); // manually creating a FreezeEvent.
		Player testPlayer1 = new Player(0, 1); // creating first player for
												// cezmi1 construction
		Player testPlayer2 = new Player(1, 1); // creating second player for
												// cezmi2 construction
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
		testCezmi1.setLimits();
		testCezmi2.setLimits();
		ArrayList<Cezmi> cezmis; // creating the ArrayList of cezmis to give it
									// to applyEvent as parameter
		Ball testBall = new Ball(100, 100, 5, -10); // creating a ball instance
													// to give a test history
		Cezerye testCezerye = new Cezerye(); // creating the test cezerye to
												// give it to ball test history
		Object[] testHistory = new Object[3]; // creating the testhistory for
												// the ball.

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

		event.applyEvent(cezmis); // applying the event to cezmis arraylist
		assertTrue(testCezmi2.getVelocity() == 0); // checking whether the
													// testCezmi2's velocity is
													// effected by the event.
		testCezmi2.moveRight(); // moving cezmi with velocity equal to 0
		assertTrue(testCezmi2.getX() == 15); // checking whether testCezmi's
												// location hasn't changed.
		// event.finishEvent(); // finishing the event
		// assertTrue(testCezmi2.getVelocity() != 0); // checking whether
		// testCezmi2's velocity is back to normal
	}

}
