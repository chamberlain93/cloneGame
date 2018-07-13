package domain;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import boardobject.Ball;
import boardobject.Cezmi;
import boardobject.Firildak;
import boardobject.RightTokat;
import boardobject.SquareTakoz;
import enums.LevelEnum;
import model.BoardModel;
import model.Player;
import observer.BallObserver;
import observer.Observer;

public class BallTest {

	private BoardModel board = BoardModel.getInstance();
	private Ball ball;

	@Before
	public void setUp() {
		ball = new Ball(2, 5, 20, 25);
		board.initializeBoardModel();
		board.setLevel(LevelEnum.LEVEL1);
	}

	/**
	 * Test whether the range of first ball is 0 and 200 or not.
	 */
	@Test
	public void testGetRange1() {
		assertEquals(0, ball.getRange1()[0], 0.01);
		assertEquals(200, ball.getRange1()[1], 0.01);
	}

	/**
	 * Test whether setRange method sets lower and upper ranges for ball or not.
	 */
	@Test
	public void testSetRange1() {
		double[] temp = { 10, 20 };
		ball.setRange1(temp);
		assertEquals(10, ball.getRange1()[0], 0.01);
		assertEquals(20, ball.getRange1()[1], 0.01);
	}

	/**
	 * Test whether the range of second ball is 100 and 400 or not.
	 */
	@Test
	public void testGetRange2() {
		assertEquals(100, ball.getRange2()[0], 0.01);
		assertEquals(400, ball.getRange2()[1], 0.01);
	}

	/**
	 * Test whether setRange method sets lower and upper ranges for ball or not.
	 */
	@Test
	public void testSetRange2() {
		double[] temp = { 10, 20 };
		ball.setRange2(temp);
		assertEquals(10, ball.getRange2()[0], 0.01);
		assertEquals(20, ball.getRange2()[1], 0.01);
	}

	/**
	 * Test if getHistory method of the Ball class works after setHistory method
	 * is called.
	 */
	@Test
	public void testGetHistory() {
		Cezmi cezmi = new Cezmi(1, 2, Color.BLUE, 1, new Player(0, 0));
		SquareTakoz squareTakoz = new SquareTakoz(5, 6, 1, 1, Color.ORANGE);
		Object[] temp = { cezmi, squareTakoz };
		ball.setHistory(temp);
		assertEquals(cezmi, ball.getHistory()[0]);
		assertEquals(squareTakoz, ball.getHistory()[1]);
	}

	/**
	 * Test if setHistory method of the Ball class works after setHistory method
	 * is called twice.
	 */
	@Test
	public void testSetHistory() {
		Cezmi cezmi = new Cezmi(1, 25, Color.BLUE, 1, new Player(0, 0));
		SquareTakoz squareTakoz = new SquareTakoz(5, 6, 1, 1, Color.ORANGE);
		Object[] temp = { cezmi, squareTakoz };
		ball.setHistory(temp);
		assertEquals(cezmi, ball.getHistory()[0]);
		assertEquals(squareTakoz, ball.getHistory()[1]);

		Cezmi cezmi1 = new Cezmi(5, 25, Color.YELLOW, 1, new Player(1, 0));
		SquareTakoz squareTakoz1 = new SquareTakoz(15, 9, 1, 1, Color.GREEN);
		Object[] temp1 = { cezmi1, squareTakoz1 };
		ball.setHistory(temp1);
		assertEquals(cezmi1, ball.getHistory()[0]);
		assertEquals(squareTakoz1, ball.getHistory()[1]);
	}

	/**
	 * Test whether getObservers() method works or not after attaching two
	 * observers.
	 */
	@Test
	public void testGetObservers() {
		ball.setObservers(new ArrayList<Observer>());
		Observer observer = new BallObserver();
		ball.attachObserver(observer);
		Observer observer1 = new BallObserver();
		ball.attachObserver(observer1);
		assertEquals(observer, ball.getObservers().get(0));
		assertEquals(observer1, ball.getObservers().get(1));
	}

	/**
	 * Test whether setObservers() method overwrites to the previous observers
	 * list or not.
	 */
	@Test
	public void testSetObservers() {
		ball.setObservers(new ArrayList<Observer>());
		Observer observer = new BallObserver();
		ball.attachObserver(observer);
		assertEquals(observer, ball.getObservers().get(0));
		Observer observer1 = new BallObserver();
		List<Observer> temp = new ArrayList<Observer>();
		temp.add(observer1);
		ball.setObservers(temp);
		assertEquals(observer1, ball.getObservers().get(0));
	}

	/**
	 * Test if getVelocityX() returns correct value or not.
	 */
	@Test
	public void testGetVelocityX() {
		assertEquals(20, ball.getVelocityX(), 0.01);
	}

	/**
	 * Test if getVelocityY() returns correct value or not.
	 */
	@Test
	public void testGetVelocityY() {
		assertEquals(25, ball.getVelocityY(), 0.01);
	}

	/**
	 * Test if getGravity() returns what the gravity is set.
	 */
	@Test
	public void testGetGravity() {
		assertEquals(5, Ball.getGravity(), 0.01);
	}

	/**
	 * Test if getMu() returns what the mu is set.
	 */
	@Test
	public void testGetMu() {
		assertEquals(0.025, Ball.getMu(), 0.01);
	}

	/**
	 * Test if getMu2() returns what the mu2 is set.
	 */
	@Test
	public void testGetMu2() {
		assertEquals(0.025, Ball.getMu2(), 0.01);
	}

	/**
	 * Test if getDeltaT() returns what the deltaT is set.
	 */
	@Test
	public void testGetDeltaT() {
		assertEquals(0.02, Ball.getDeltaT(), 0.01);
	}

	/**
	 * Test whether the lower and upper bounds of the range values are changed
	 * when we changed the Level of the game or not.
	 */
	@Test
	public void testGetEffectiveRange() {
		assertEquals(ball.getRange1()[0], ball.getEffectiveRange()[0], 0.01);
		assertEquals(ball.getRange1()[1], ball.getEffectiveRange()[1], 0.01);

		board.setLevel(LevelEnum.LEVEL2);
		assertEquals(ball.getRange2()[0], ball.getEffectiveRange()[0], 0.01);
		assertEquals(ball.getRange2()[1], ball.getEffectiveRange()[1], 0.01);
	}

	/**
	 * Test whether the euclidean velocity of the ball is calculated correct or
	 * not.
	 */
	@Test
	public void testVelocity() {
		ball.setVelocityX(3);
		ball.setVelocityY(4);
		assertEquals(5, ball.velocity(), 0.01);
	}

	/**
	 * Test whether the newVelocity of the Ball is calculated correct depending
	 * on the level of the game or not.
	 */
	@Test
	public void testNewVelocity() {
		ball.setVelocityX(15);
		ball.setVelocityY(20);
		assertEquals(24.675, ball.newVelocity(LevelEnum.LEVEL1), 0.01);
		assertEquals(24.349, ball.newVelocity(LevelEnum.LEVEL2), 0.01);
	}

	/**
	 * Test whether the newVelocityX of the Ball is calculated correct depending
	 * on the level of the game or not.
	 */
	@Test
	public void testNewVelocityX() {
		ball.setVelocityX(15);
		ball.setVelocityY(20);
		assertEquals(14.805, ball.newVelocityX(LevelEnum.LEVEL1), 0.01);
		assertEquals(14.609, ball.newVelocityX(LevelEnum.LEVEL2), 0.01);

		ball.setVelocityX(0);
		ball.setVelocityY(5);
		assertEquals(0, ball.newVelocityX(LevelEnum.LEVEL1), 0.01);
		assertEquals(0, ball.newVelocityX(LevelEnum.LEVEL2), 0.01);

		ball.setVelocityX(-15);
		ball.setVelocityY(20);
		assertEquals(-14.805, ball.newVelocityX(LevelEnum.LEVEL1), 0.01);
		assertEquals(-14.609, ball.newVelocityX(LevelEnum.LEVEL2), 0.01);
	}

	/**
	 * Test whether the newVelocityY of the Ball is calculated correct depending
	 * on the level of the game or not.
	 */
	@Test
	public void testNewVelocityY() {
		ball.setVelocityX(15);
		ball.setVelocityY(20);
		assertEquals(19.84, ball.newVelocityY(LevelEnum.LEVEL1), 0.01);
		assertEquals(19.58, ball.newVelocityY(LevelEnum.LEVEL2), 0.01);

		ball.setVelocityX(5);
		ball.setVelocityY(0);
		assertEquals(0.1, ball.newVelocityY(LevelEnum.LEVEL1), 0.01);
		assertEquals(0.1, ball.newVelocityY(LevelEnum.LEVEL2), 0.01);

		ball.setVelocityX(15);
		ball.setVelocityY(-20);
		assertEquals(-19.64, ball.newVelocityY(LevelEnum.LEVEL1), 0.01);
		assertEquals(-19.38, ball.newVelocityY(LevelEnum.LEVEL2), 0.01);
	}

	/**
	 * Test whether the ball shrinks or not depending on the scores of the
	 * players. In some conditions, shrinkBall() should not change width or
	 * height (expected case)
	 */
	@Test
	public void testShrinkBall() {
		Player player1 = new Player(0, 0);
		Player player2 = new Player(1, 0);
		ArrayList<Player> player = new ArrayList<Player>();
		player.add(player1);
		player.add(player2);
		BoardModel.getInstance().addBall(ball);
		BoardModel.getInstance().setPlayers(player);
		Ball.shrinkBall();
		assertEquals(18, ball.getWidth(), 0.01);
		assertEquals(18, ball.getHeight(), 0.01);

		player1 = new Player(0, 1);
		player2 = new Player(0, 0);
		player = new ArrayList<Player>();
		player.add(player1);
		player.add(player2);
		BoardModel.getInstance().addBall(ball);
		BoardModel.getInstance().setPlayers(player);
		ball.setWidth(20);
		ball.setHeight(20);
		Ball.shrinkBall();
		assertEquals(20, ball.getWidth(), 0.01);
		assertEquals(20, ball.getHeight(), 0.01);

		player1 = new Player(0, 3);
		player2 = new Player(0, 5);
		player = new ArrayList<Player>();
		player.add(player1);
		player.add(player2);
		BoardModel.getInstance().addBall(ball);
		BoardModel.getInstance().setPlayers(player);
		ball.setWidth(4);
		ball.setHeight(4);
		Ball.shrinkBall();
		assertEquals(4, ball.getWidth(), 0.01);
		assertEquals(4, ball.getHeight(), 0.01);

		player1 = new Player(0, 4);
		player2 = new Player(0, 5);
		player = new ArrayList<Player>();
		player.add(player1);
		player.add(player2);
		BoardModel.getInstance().addBall(ball);
		BoardModel.getInstance().setPlayers(player);
		ball.setWidth(4);
		ball.setHeight(4);
		Ball.shrinkBall();
		assertEquals(4, ball.getWidth(), 0.01);
		assertEquals(4, ball.getHeight(), 0.01);
	}

	/**
	 * Test if updateCezmiHistory() method works or not.
	 */
	@Test
	public void testUpdateCezmiHistory() {
		Cezmi cezmi = new Cezmi(5, 25, Color.BLUE, 2, new Player(0, 0));
		ball.updateCezmiHistory(cezmi);
		assertEquals(cezmi, ball.getHistory()[0]);
	}

	/**
	 * Test if testUpdateObjectHistory() method works or not. Correct
	 * implementation would store last 2 objects that updateObjectHistory method
	 * took as argument.
	 */
	@Test
	public void testUpdateObjectHistory() {
		Cezmi cezmi = new Cezmi(5, 25, Color.BLUE, 2, new Player(0, 0));
		SquareTakoz squareTakoz = new SquareTakoz(1, 1, 1, 1, Color.GREEN);
		RightTokat rightTokat = new RightTokat(6, 8);
		Object[] mockHistory = { cezmi, squareTakoz, rightTokat };
		ball.setHistory(mockHistory);
		Firildak firildak = new Firildak(5, 10, 1, 1, Color.RED);
		ball.updateObjectHistory(firildak);
		assertEquals(rightTokat, ball.getHistory()[1]);
		assertEquals(firildak, ball.getHistory()[2]);
	}

	/**
	 * Test whether the next position of the ball is calculated correctly
	 * depending on the velocities and coordinates given.
	 */
	@Test
	public void testNextPosition() {
		Ball next = ball.nextPosition();
		assertEquals(22, next.getX(), 0.01);
		assertEquals(30, next.getY(), 0.01);
		assertEquals(20, next.getVelocityX(), 0.01);
		assertEquals(25, next.getVelocityY(), 0.01);
	}

}
