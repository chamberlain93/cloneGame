package domain;

import static org.junit.Assert.*;

import org.junit.Test;
import boardobject.Ball;
import boardobject.Gizmo;
import enums.BoardObjectEnum;
import enums.DirectionEnum;
import enums.LevelEnum;
import enums.StateEnum;
import factory.BoardObjectFactory;
import model.BoardModel;
import model.Player;

public class BoardModelTest {

	BoardModel boardModel;

	public void initializeBoardModelLevel1() {
		// This method is for initializing the boardModel for testing. Level is
		// 1.
		boardModel = BoardModel.getInstance();
		boardModel.setLevel(LevelEnum.LEVEL1);
		int[] coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.SQUARE_TAKOZ, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.TRIANGLE_TAKOZ, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.LEFT_TOKAT, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.RIGHT_TOKAT, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.SQUARE_TAKOZ, new Player(1, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.TRIANGLE_TAKOZ, new Player(1, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.LEFT_TOKAT, new Player(1, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.RIGHT_TOKAT, new Player(1, 0));

		boardModel.initializeBoardModel();
	}

	public void initializeBoardModelLevel2() {
		// This method is for initializing the boardModel for testing. Level is
		// 2.
		boardModel = BoardModel.getInstance();
		boardModel.setLevel(LevelEnum.LEVEL2);
		int[] coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.SQUARE_TAKOZ, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.TRIANGLE_TAKOZ, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.LEFT_TOKAT, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.RIGHT_TOKAT, new Player(0, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.SQUARE_TAKOZ, new Player(1, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.TRIANGLE_TAKOZ, new Player(1, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.LEFT_TOKAT, new Player(1, 0));

		coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addGizmo(coordinates[0], coordinates[1], BoardObjectEnum.RIGHT_TOKAT, new Player(1, 0));

		boardModel.initializeBoardModel();
	}

	@Test
	public void testInitializeBallsLevel1() {
		// This method tests "initializeBoardModelLevel1()" method. It checks if
		// the ball created is in the right place or not.
		initializeBoardModelLevel1();
		assertEquals("X coordinate of the first ball is wrong.", 100, boardModel.getBalls().get(0).getX(), 0);
		assertEquals("Y coordinate of the first ball is wrong.", 380, boardModel.getBalls().get(0).getY(), 0);
	}

	@Test
	public void testInitializeBallsLevel2() {
		// This method tests "initializeBoardModelLevel2()" method. It checks if
		// the balls created are in the right places or not.
		initializeBoardModelLevel2();
		assertEquals("X coordinate of the first ball is wrong.", 100, boardModel.getBalls().get(0).getX(), 0);
		assertEquals("Y coordinate of the first ball is wrong.", 380, boardModel.getBalls().get(0).getY(), 0);
		assertEquals("X coordinate of the second ball is wrong.", 100, boardModel.getBalls().get(1).getX(), 0);
		assertEquals("Y coordinate of the second ball is wrong.", 380, boardModel.getBalls().get(1).getY(), 0);
	}

	@Test
	public void testInitializePlayers() {
		// This method tests "initializePlayers()" method. It checks if the
		// attributes of both Player objects in "players" list in
		// boardModel object are correct or not.
		initializeBoardModelLevel1();
		assertEquals("Score of the first player is wrong.", 0.0, boardModel.getPlayers().get(0).getScore(), 0);
		assertEquals("ID of the first player is wrong.", 0, boardModel.getPlayers().get(0).getId());
		assertEquals("Score of the second player is wrong.", 0.0, boardModel.getPlayers().get(1).getScore(), 0);
		assertEquals("ID of the second player is wrong.", 1, boardModel.getPlayers().get(1).getId());
	}

	@Test
	public void testInitializeCezmis() {
		// This method tests "initializeCezmis()" method. It checks if the
		// attributes of both Cezmi objects in "cezmis" list in
		// boardModel object are correct or not.
		initializeBoardModelLevel1();
		assertEquals("Owner of the first cezmi is wrong.", boardModel.getPlayers().get(0),
				boardModel.getCezmis().get(0).getOwner());
		assertEquals("Owner of the second cezmi is wrong.", boardModel.getPlayers().get(1),
				boardModel.getCezmis().get(1).getOwner());
	}

	@Test
	public void testInitializeEngel() {
		// This method tests "initializeEngel()" method. It checks if the
		// attributes of Engel object in boardModel object are
		// correct or not.
		initializeBoardModelLevel1();
		assertEquals("X coordinate of the engel is wrong", 12.375, boardModel.getEngel().getX(), 0);
		assertEquals("Y coordinate of the engel is wrong", 22.0, boardModel.getEngel().getY(), 0);
	}

	@Test
	public void testInitializeWalls() {
		// This method tests "initializeWalls()" method. It checks if the
		// attributes of both Wall objects in "walls" list in
		// boardModel object are correct or not.
		initializeBoardModelLevel1();
		assertEquals("X coordinate of the upper wall is wrong.", 0, (int) boardModel.getWalls().get(0).getX());
		assertEquals("Y coordinate of the upper wall is wrong.", 0, (int) boardModel.getWalls().get(0).getY());
		assertEquals("X coordinate of the right wall is wrong.", 25, (int) boardModel.getWalls().get(1).getX());
		assertEquals("Y coordinate of the right wall is wrong.", 0, (int) boardModel.getWalls().get(1).getY());
		assertEquals("X coordinate of the bottom wall is wrong.", 0, (int) boardModel.getWalls().get(2).getX());
		assertEquals("Y coordinate of the bottom wall is wrong.", 25, (int) boardModel.getWalls().get(2).getY());
		assertEquals("X coordinate of the left wall is wrong.", 0, (int) boardModel.getWalls().get(3).getX());
		assertEquals("Y coordinate of the left wall is wrong.", 0, (int) boardModel.getWalls().get(3).getY());
	}

	@Test
	public void testInitializeState() {
		// This method tests "initializeState()" method. It checks if the state
		// attribute of boardModel object is "StateEnum.PAUSE"
		// or not.
		initializeBoardModelLevel1();
		assertEquals("The game is in not Pause state.", StateEnum.PAUSE, boardModel.getState());
	}

	@Test
	public void testCurrentPlayer() {
		// This method tests "initializeCurrentPlayer()" method. It checks if
		// the currentPlayer field of boardModel object is first
		// player or not.
		initializeBoardModelLevel1();
		assertEquals("The current player is not the first player.", boardModel.getPlayers().get(0),
				boardModel.getCurrentPlayer());
	}

	@Test
	public void testRandomAvailableCoordinates() {
		// This method tests "randomAvailableCoordinates()" method. It checks if
		// randomly generated coordinate is beetween 0 and 24 or not.
		boardModel = BoardModel.getInstance();
		int[] coordinates = boardModel.randomAvailableCoordinates();
		assertTrue("X coordinate is less than zero.", 0 <= coordinates[0]);
		assertTrue("Y coordinate is less than zero.", 0 <= coordinates[1]);
		assertTrue("X coordinate is bigger than 24.", coordinates[0] <= 24);
		assertTrue("Y coordinate is bigger than 24.", coordinates[1] <= 24);
	}

	@Test
	public void testAddBall() {
		// This method tests addBall() method. It checks if we are able to add a
		// second ball when we are in the first level.
		initializeBoardModelLevel1();
		int[] coordinates = boardModel.randomAvailableCoordinates();
		boardModel.addBall((Ball) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.BALL,
				coordinates[0], coordinates[1]));
		assertEquals("Second ball cannot be added.", 100, (int) boardModel.getBalls().get(1).getX());
	}

	@Test
	public void testMoveGizmo() {
		// This method tests moveGizmo() method. It checks if the initial
		// position of the chosen gizmo becomes empty or not. It also
		// checks if the final position of the chosen gizmo becomes occupied or
		// not.
		initializeBoardModelLevel1();
		boardModel.addGizmo(0, 0, BoardObjectEnum.SQUARE_TAKOZ, new Player(0, 0));
		Gizmo gizmo = boardModel.getGizmos().get(boardModel.getGizmos().size() - 1);
		int[] coordinates = boardModel.randomAvailableCoordinates();
		boardModel.moveGizmo(gizmo, coordinates[0], coordinates[1]);
		assertTrue("Final position is empty in the occupied matrix.",
				boardModel.getOccupied()[coordinates[0]][coordinates[1]]);
	}

	@Test
	public void testDeleteGizmo() {
		// This method tests deleteGizmo() method. It checks if the initial
		// position of the chosen gizmo becomes empty or not.
		initializeBoardModelLevel1();
		boardModel.addGizmo(0, 0, BoardObjectEnum.SQUARE_TAKOZ, new Player(0, 0));
		boardModel.deleteGizmo(10, 10);
		assertTrue("Gizmo could not be deleted.", !boardModel.getOccupied()[0][0]);
	}

	@Test
	public void testMoveCezmi() {
		// This method tests moveCezmi() method. It checks if the final position
		// of the chosen Cezmi is correct or not.
		initializeBoardModelLevel1();
		double oldX = boardModel.getCezmis().get(0).getX();
		boardModel.moveCezmi(0, DirectionEnum.WEST);
		double newX = boardModel.getCezmis().get(0).getX();
		assertEquals("Given cezmi's final position is wrong.", oldX - newX, boardModel.getCezmis().get(0).getVelocity(),
				0);
	}

}
