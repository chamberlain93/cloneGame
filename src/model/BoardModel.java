package model;

import java.awt.Polygon;
import java.util.ArrayList;
import boardobject.*;
import enums.BoardObjectEnum;
import enums.DirectionEnum;
import enums.LevelEnum;
import enums.ModeEnum;
import enums.StateEnum;
import factory.BoardObjectFactory;
import observer.Observer;
import observer.WinPanelObserver;
import physics.Vect;

public class BoardModel implements Observable {

	private static final int L = 20;
	private static BoardModel boardModel;
	private ArrayList<Ball> balls;
	private ArrayList<Player> players;
	private ArrayList<Cezmi> cezmis;
	private Engel engel;
	private ArrayList<Wall> walls;
	private ArrayList<Gizmo> gizmos;
	private ModeEnum mode;
	private StateEnum state;

	private boolean[][] occupied;
	private LevelEnum level;
	private ArrayList<Observer> observers;
	private Cezerye cezerye;
	private Player currentPlayer;

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * @effects initializes "occupied" and "gizmos" fields.
	 * @modifies occupied, multidimensional array of booleans. gizmos, arraylist
	 *           of Gizmo objects.
	 **/
	private BoardModel() {
		setBalls(new ArrayList<Ball>());
		setPlayers(new ArrayList<Player>());
		initializePlayers();
		setCezmis(new ArrayList<Cezmi>());
		initializeEngel();
		setWalls(new ArrayList<Wall>());
		initializeWalls();
		initializeGizmos();
		setMode(ModeEnum.BUILDING_MODE);
		initializeState();
		initializeOccupied();
		setObservers(new ArrayList<Observer>());
		setCurrentPlayer(getPlayers().get(0));
		initializeObservers();
	}

	/**
	 * @effects initializes "balls", "players", "cezmis", "engel", "walls",
	 *          "state", "currentPlayer, "observers" and "cezerye" fields.
	 * @modifies balls, an arraylist of Ball objects. players, an arraylist of
	 *           Player objects. cezmis, an arraylist of Cezmi objects. engel,
	 *           an Engel object. walls, an arraylist of Wall objects. state, an
	 *           Enum. currentPlayer, a Player object. observers, an arraylist
	 *           of Observer objects. cezerye, a Cezerye object.
	 **/
	public void initializeBoardModel() {
		initializeBalls();
		initializeCezerye();
		initializeCezmis();
	}

	/**
	 * @effects If boardModel is null, creates new BoardModel and returns it.
	 *          Else it returns boardModel directly.
	 * @modifies boardModel, which is a BoardModel object
	 **/
	public static BoardModel getInstance() {
		if (boardModel == null)
			boardModel = new BoardModel();
		return boardModel;
	}

	/**
	 * @effects If level is LEVEL1, it creates a ball which is located at 5L
	 *          above the left Cezmi and adds it to an arraylist named "balls".
	 *          If level is LEVEL2, it creates two balls, one located at 5L
	 *          above the left Cezmi and other located at 5L above the right
	 *          Cezmi, then it adds these ball objectes to an arraylist named
	 *          "balls".
	 * @modifies balls, which is an ArrayList of Ball objects.
	 **/
	private void initializeBalls() {
		if (level == LevelEnum.LEVEL1) {
			Ball ball1 = (Ball) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.BALL, 5, 19);
			getBalls().add(ball1);
		} else {
			Ball ball1 = (Ball) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.BALL, 5, 19);
			Ball ball2 = (Ball) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.BALL, 15, 19);
			getBalls().add(ball1);
			getBalls().add(ball2);
		}
	}

	/**
	 * @effects Creates two Player objects one with id=0 and score=0, and other
	 *          one with id=1 and score=0. And adds these Player objects to an
	 *          arraylist named "players".
	 * @modifies players, an ArrayList of Player objects.
	 **/
	private void initializePlayers() {
		Player player1 = new Player(0, 0);
		Player player2 = new Player(1, 0);
		players.add(player1);
		players.add(player2);
	}

	/**
	 * @effects Creates two Cezmi objects one at x=5L and y=25L, and other one
	 *          at x=15L and y=25L. Then sets the first cezmi's owner as the
	 *          first player and sets the second cezmi's owner as the second
	 *          player. Then adds these Cezmi objects to an arraylist named
	 *          "cezmis". objects to an arraylist named "cezmis".
	 * @modifies players, an ArrayList of Player objects.
	 **/
	private void initializeCezmis() {
		Cezmi cezmi1 = (Cezmi) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.CEZMI, 5, 25);
		Cezmi cezmi2 = (Cezmi) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.CEZMI, 15, 25);

		cezmi1.setOwner(players.get(0));
		cezmi2.setOwner(players.get(1));
		cezmi1.setLimits();
		cezmi2.setLimits();
		cezmis.add(cezmi1);
		cezmis.add(cezmi2);
	}

	/**
	 * @effects Creates an Engel object at x=12.375L and y=22L and sets engel
	 *          field to this object.
	 * @modifies engel, which is an Engel object.
	 **/
	private void initializeEngel() {
		engel = (Engel) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.ENGEL, 12.375, 22);
	}

	/**
	 * @effects Creates 4 walls, one located at the top side of the board, one
	 *          located at the right side of the board, one located at the
	 *          bottom side of the board and one located at the left side of the
	 *          board. Then adds these walls to an arraylist named "walls".
	 * @modifies walls, which is an arraylist of Wall objects.
	 **/
	private void initializeWalls() {
		Wall upperWall = (Wall) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.WALL, 0, 0);
		Wall rightWall = (Wall) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.WALL, 25, 0);
		Wall bottomWall = (Wall) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.WALL, 0, 25);
		Wall leftWall = (Wall) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.WALL, 0, 0);

		walls.add(upperWall);
		walls.add(rightWall);
		walls.add(bottomWall);
		walls.add(leftWall);
	}

	/**
	 * @effects Initializes an arraylist of Gizmo objects named gizmos.
	 * @modifies gizmos, an arraylist of Gizmo objects.
	 **/
	private void initializeGizmos() {
		gizmos = new ArrayList<Gizmo>();
	}

	/**
	 * @effects Initializes state field which is an Enum.
	 * @modifies state, which is an Enum.
	 **/
	private void initializeState() {
		state = StateEnum.PAUSE;
	}

	/**
	 * @effects Initializes a multidimensional array of booleans named
	 *          "occupied". Then it sets all booleans to "false".
	 * @modifies occupied, which is a multidimensional array of booleans.
	 **/
	private void initializeOccupied() {
		occupied = new boolean[25][25];

		for (int i = 0; i < 25; i++) {
			for (int j = 0; j < 25; j++) {
				occupied[i][j] = false;
			}
		}
	}

	/**
	 * @effects Initializes "observers" which is an arrayList of Observer
	 *          objects and then adds a winPanel Observer to this ArrayList.
	 * @modifies observers, which is an arrayList of Observer objects
	 **/
	private void initializeObservers() {
		attachObserver(new WinPanelObserver());
	}

	/**
	 * @effects Finds a random and empty location for placing a cezerye. Then
	 *          places a cezerye object to this place.
	 * @modifies cezerye, which is a Cezerye object.
	 **/
	private void initializeCezerye() {
		int[] coordinates = randomAvailableCoordinates();
		cezerye = (Cezerye) BoardObjectFactory.getInstance().createBoardObject(BoardObjectEnum.CEZERYE, coordinates[0],
				coordinates[1]);
	}

	/**
	 * @effects Finds a random location.
	 * @return Returns an array consists of random coordinates.
	 **/
	private int[] randomCoordinates() {
		int[] coordinates = new int[2];
		int randomX = (int) (Math.random() * 20 + 2);
		int randomY = (int) (Math.random() * 18);
		coordinates[0] = randomX;
		coordinates[1] = randomY;
		return coordinates;
	}

	/**
	 * @effects Finds a random location and if this location is empty, it
	 *          returns this location. Else it finds another random location
	 *          until this location is empty.
	 * @return Returns an array of random and available coordinate pair.
	 **/
	public int[] randomAvailableCoordinates() {
		int[] coordinates = randomCoordinates();
		int x = coordinates[0];
		int y = coordinates[1];
		if (occupied[x][y])
			return randomAvailableCoordinates();
		else
			return coordinates;
	}

	/**
	 * @requires A ball object which is not null.
	 * @effects Adds the ball object to an arraylist named "balls".
	 * @modifies balls, which is an arraylist of Ball objects.
	 * @param ball
	 **/
	public void addBall(Ball ball) {
		balls.add(ball);
	}

	/**
	 * @effects Moves the first ball.
	 * @modifies First ball's coordinates.
	 **/
	public void moveBall1() {
		balls.get(0).move();
	}

	/**
	 * @effects Moves the second ball.
	 * @modifies Second ball's coordinates.
	 **/
	public void moveBall2() {
		balls.get(1).move();
	}

	/**
	 * @requires 0<=x<=500, 0<=y<=500 and type is an enum which is equal to one
	 *           of "BALL, CEZMI, ENGEL, CEZERYE, FIRILDAK, TRIANGLE_TAKOZ,
	 *           SQUARE_TAKOZ, LEFT_TOKAT, RIGHT_TOKAT, WALL".
	 * @effects Finds the grid corresponding to the coordinates of the mouse
	 *          click. Creates a Gizmo object at this grid. adds this Gizmo
	 *          object to an arraylist named gizmos. Sets the coordinates of
	 *          this grid at the occupied matrix to "true". Checks if the size
	 *          of the "gizmos" arraylist is 4, if it is, it sets the
	 *          currentPlayer as the second player.
	 * @modifies gizmos, an ArrayList of Gizmo objects. occupied, a
	 *           multidimensional array of booleans. currentPlayer, a Player
	 *           object.
	 * @param x:
	 *            X coordinate of the gizmo that will be added.
	 * @param y:
	 *            Y coordinate of the gizmo that will be added.
	 * @param type
	 **/
	public boolean addGizmo(int x, int y, BoardObjectEnum type, Player owner) {
		if (!occupied[x][y]) {
			Gizmo gizmo = (Gizmo) BoardObjectFactory.getInstance().createGizmo(type, x, y);

			if (type.equals(BoardObjectEnum.SQUARE_TAKOZ)) {
				gizmos.add(gizmo);
				occupied[x][y] = true;
				gizmo.setOwner(owner);
				owner.setGizmoNum(owner.getGizmoNum() + 1);
				gizmo.notifyAllObservers();
				return true;
			} else if (type.equals(BoardObjectEnum.TRIANGLE_TAKOZ)) {
				gizmos.add(gizmo);
				occupied[x][y] = true;
				gizmo.setOwner(owner);
				owner.setGizmoNum(owner.getGizmoNum() + 1);
				gizmo.notifyAllObservers();
				return true;
			} else if (type.equals(BoardObjectEnum.FIRILDAK)) {
				boolean available = true;
				for (int i = x - 1; i <= x + 1; i++) {
					for (int j = y - 1; j <= y + 1; j++) {
						if (getCurrentPlayer() == getPlayers().get(0)) {
							if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
								available = false;
								break;
							}
						} else {
							if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
								available = false;
								break;
							}
						}
					}
					if (!available) {
						break;
					}
				}

				if (available) {
					gizmos.add(gizmo);
					gizmo.setOwner(owner);
					owner.setGizmoNum(owner.getGizmoNum() + 1);
					gizmo.notifyAllObservers();
					for (int i = x - 1; i <= x + 1; i++) {
						for (int j = y - 1; j <= y + 1; j++) {
							occupied[i][j] = true;
						}
					}
					return true;
				}
			} else if (type.equals(BoardObjectEnum.LEFT_TOKAT)) {
				boolean available = true;
				for (int i = x; i <= x + 1; i++) {
					for (int j = y; j <= y + 1; j++) {
						if (getCurrentPlayer() == getPlayers().get(0)) {
							if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
								available = false;
								break;
							}
						} else {
							if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
								available = false;
								break;
							}
						}
					}
					if (!available) {
						break;
					}
				}

				if (available) {
					gizmos.add(gizmo);
					gizmo.setOwner(owner);
					owner.setGizmoNum(owner.getGizmoNum() + 1);
					gizmo.notifyAllObservers();
					for (int i = x; i <= x + 1; i++) {
						for (int j = y; j <= y + 1; j++) {
							occupied[i][j] = true;
						}
					}
					return true;
				}
			} else if (type.equals(BoardObjectEnum.RIGHT_TOKAT)) {
				boolean available = true;
				for (int i = x - 1; i <= x; i++) {
					for (int j = y; j <= y + 1; j++) {
						if (getCurrentPlayer() == getPlayers().get(0)) {
							if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
								available = false;
								break;
							}
						} else {
							if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
								available = false;
								break;
							}
						}
					}
					if (!available) {
						break;
					}
				}

				if (available) {
					gizmos.add(gizmo);
					gizmo.setOwner(owner);
					owner.setGizmoNum(owner.getGizmoNum() + 1);
					gizmo.notifyAllObservers();
					for (int i = x - 1; i <= x; i++) {
						for (int j = y; j <= y + 1; j++) {
							occupied[i][j] = true;
						}
					}
					return true;
				}
			}

			else {

				return false;
			}
		} else {
			System.out.println("mm");
			return false;
		}
		System.out.println("nn");
		return false;
	}

	/**
	 * @requires 0<=x<=500
	 * @effects Finds the location which corresponds to the mouseclick.
	 * @param x
	 * @return Returns an integer corresponds to the mouseclick.
	 **/
	private int normalizeCoordinate(double x) {
		double doubleX = x / L;
		return (int) doubleX;
	}

	/**
	 * @requires 0<=x<=500 and 0<=y<=500
	 * @effects Finds the gizmo at (x,y) coordinates and rotates it.
	 * @modifies Angle of the gizmo at (x,y) coordinates.
	 * @param x:
	 *            X coordinate of the gizmo that will be rotated.
	 * @param y:
	 *            Y coordinate of the gizmo that will be rotated.
	 **/
	public boolean deleteGizmo(double x, double y) {
		int normalizedX = normalizeCoordinate(x);
		int normalizedY = normalizeCoordinate(y);
		boolean operationState = false;
		for (int i = 0; i < gizmos.size(); i++) {
			Gizmo gizmo = gizmos.get(i);
			if (gizmo instanceof SquareTakoz) {
				if (gizmo.getX() == normalizedX && gizmo.getY() == normalizedY) {
					gizmos.remove(gizmo);
					gizmo.delete();
					operationState = true;
					occupied[(int) gizmo.getX()][(int) gizmo.getY()] = false;
					break;
				}
			} else if (gizmo instanceof TriangleTakoz) {
				if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.SOUTH_WEST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX();
					xCoordinates[2] = (int) gizmo.getX() + 1;

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY() - 1;
					yCoordinates[2] = (int) gizmo.getY();
					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						occupied[(int) gizmo.getX()][(int) (gizmo.getY() - 1)] = false;
						break;
					}
				} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.SOUTH_EAST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX() - 1;
					xCoordinates[2] = (int) gizmo.getX();

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY();
					yCoordinates[2] = (int) gizmo.getY() - 1;
					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						occupied[(int) (gizmo.getX() - 1)][(int) (gizmo.getY() - 1)] = false;
						break;
					}
				} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.NORTH_EAST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX();
					xCoordinates[2] = (int) gizmo.getX() - 1;

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY() + 1;
					yCoordinates[2] = (int) gizmo.getY();
					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						occupied[(int) (gizmo.getX() - 1)][(int) gizmo.getY()] = false;
						break;
					}
				} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.NORTH_WEST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX() + 1;
					xCoordinates[2] = (int) gizmo.getX();

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY();
					yCoordinates[2] = (int) gizmo.getY() + 1;
					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						occupied[(int) gizmo.getX()][(int) gizmo.getY()] = false;
						break;
					}
				}
			} else if (gizmo instanceof Firildak) {
				if (gizmo.getX() == normalizedX && gizmo.getY() == normalizedY) {
					gizmos.remove(gizmo);
					gizmo.delete();
					operationState = true;
					for (int k = (int) (gizmo.getX() - 1); k <= (int) (gizmo.getX() + 1); k++) {
						for (int j = (int) (gizmo.getY() - 1); j <= (int) (gizmo.getY() + 1); j++) {
							occupied[k][j] = false;
						}
					}
					break;
				}
			} else if (gizmo instanceof LeftTokat) {
				if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.SOUTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L + L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[2] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
							for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.EAST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L - L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
							for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.NORTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L - L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
							for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.WEST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L + L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L + L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
							for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				}
			} else if (gizmo instanceof RightTokat) {
				if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.SOUTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L - L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[2] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
							for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.EAST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L + L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L + L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
							for (int j = (int) (gizmo.getY()); j <= (int) (gizmo.getY() + 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.NORTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L + L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX()); k <= (int) (gizmo.getX() + 1); k++) {
							for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.WEST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L - L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						gizmos.remove(gizmo);
						gizmo.delete();
						operationState = true;
						for (int k = (int) (gizmo.getX() - 2); k <= (int) (gizmo.getX() - 1); k++) {
							for (int j = (int) (gizmo.getY() - 2); j <= (int) (gizmo.getY() - 1); j++) {
								occupied[k][j] = false;
							}
						}
						break;
					}
				}
			}

		}
		return operationState;
	}

	/**
	 * @requires 0<=x<=25 and 0<=y<=25
	 * @effects Finds the gizmo at (x,y) location and returns it.
	 * @param x
	 * @param y
	 * @return Returns the gizmo at (x,y) location.
	 **/
	private Gizmo findGizmoAtGivenCoordinates(int x, int y) {
		for (int i = 0; i < gizmos.size(); i++) {
			Gizmo gizmo = gizmos.get(i);
			if (gizmo.getX() == x && gizmo.getY() == y)
				return gizmo;
		}
		return null;
	}

	/**
	 * @effects Finds the gizmo at coordinates (x,y) and deletes it. Also sets
	 *          the element at xth row and yth column of the occupied matrix to
	 *          false.
	 * @modifies occupied, a multidimensional array of booleans.
	 * @requires 0<=x<=500, 0<=y<=500
	 * @param x:
	 *            X coordinate of the gizmo that will be deleted.
	 * @param y:
	 *            Y coordinate of the gizmo that will be deleted.
	 **/
	public void rotateGizmo(double x, double y) {
		int normalizedX = normalizeCoordinate(x);
		int normalizedY = normalizeCoordinate(y);

		for (int i = 0; i < gizmos.size(); i++) {
			Gizmo gizmo = gizmos.get(i);
			if (gizmo instanceof SquareTakoz) {
				if (gizmo.getX() == normalizedX && gizmo.getY() == normalizedY) {
					break;
				}
			} else if (gizmo instanceof TriangleTakoz) {
				if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.SOUTH_WEST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX();
					xCoordinates[2] = (int) gizmo.getX() + 1;

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY() - 1;
					yCoordinates[2] = (int) gizmo.getY();

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						((TriangleTakoz) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.SOUTH_EAST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX() - 1;
					xCoordinates[2] = (int) gizmo.getX();

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY();
					yCoordinates[2] = (int) gizmo.getY() - 1;
					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						((TriangleTakoz) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.NORTH_EAST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX();
					xCoordinates[2] = (int) gizmo.getX() - 1;

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY() + 1;
					yCoordinates[2] = (int) gizmo.getY();
					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						((TriangleTakoz) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((TriangleTakoz) gizmo).getDirection().equals(DirectionEnum.NORTH_WEST)) {
					int[] xCoordinates = new int[3];
					int[] yCoordinates = new int[3];
					xCoordinates[0] = (int) gizmo.getX();
					xCoordinates[1] = (int) gizmo.getX() + 1;
					xCoordinates[2] = (int) gizmo.getX();

					yCoordinates[0] = (int) gizmo.getY();
					yCoordinates[1] = (int) gizmo.getY();
					yCoordinates[2] = (int) gizmo.getY() + 1;
					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 3);
					if (polygon.contains(x / L, y / L)) {
						((TriangleTakoz) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				}
			} else if (gizmo instanceof Firildak) {
				if (gizmo.getX() == normalizedX && gizmo.getY() == normalizedY) {
					break;
				}
			} else if (gizmo instanceof LeftTokat) {
				if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.SOUTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L + L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[2] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((LeftTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.EAST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L - L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((LeftTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.NORTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L - L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((LeftTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((LeftTokat) gizmo).getDirection().equals(DirectionEnum.WEST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L + L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L + L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((LeftTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				}
			} else if (gizmo instanceof RightTokat) {
				if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.SOUTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L - L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[2] = (int) ((gizmo.getY() + 2) * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((RightTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.EAST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L + L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L + L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((RightTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.NORTH)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L);
					xCoordinates[2] = (int) (gizmo.getX() * L + L / 2);
					xCoordinates[3] = (int) (gizmo.getX() * L + L / 2);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - 2 * L);
					yCoordinates[3] = (int) ((gizmo.getY()) * L);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((RightTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				} else if (((RightTokat) gizmo).getDirection().equals(DirectionEnum.WEST)) {
					int[] xCoordinates = new int[4];
					xCoordinates[0] = (int) (gizmo.getX() * L);
					xCoordinates[1] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[2] = (int) (gizmo.getX() * L - 2 * L);
					xCoordinates[3] = (int) (gizmo.getX() * L);

					int[] yCoordinates = new int[4];
					yCoordinates[0] = (int) ((gizmo.getY()) * L);
					yCoordinates[1] = (int) ((gizmo.getY()) * L);
					yCoordinates[2] = (int) ((gizmo.getY()) * L - L / 2);
					yCoordinates[3] = (int) ((gizmo.getY()) * L - L / 2);

					Polygon polygon = new Polygon(xCoordinates, yCoordinates, 4);
					if (polygon.contains(x, y)) {
						((RightTokat) gizmo).startRotating();
						gizmo.notifyAllObservers();
						break;
					}
				}
			}

		}
	}

	/**
	 * @effects Finds the gizmo at coordinates (oldX,oldY) and moves it to
	 *          (newX, newY). Also sets the element at (oldX)th row and (oldY)th
	 *          column of occupied matrix to false and sets the element at
	 *          (oldX)th row and (oldY)th column of occupied matrix to true.
	 * @modifies occupied, a multidimensional array of booleans.
	 * @requires 0<=oldX<=500, 0<=oldY<=500, 0<=newX<=500, 0<=newY<=500
	 * @param oldX:
	 *            Old X coordinate of the chosen Gizmo.
	 * @param oldY:
	 *            Old Y coordinate of the chosen Gizmo.
	 * @param newX:
	 *            New X coordinate of the chosen Gizmo.
	 * @param newY:
	 *            New Y coordinate of the chosen Gizmo.
	 **/
	public boolean moveGizmo(Gizmo gizmo, int newX, int newY) {
		if (!occupied[newX][newY]) {
			if (gizmo instanceof SquareTakoz) {
				getGizmos().remove(gizmo);
				gizmo.notifyAllObservers();
				gizmo.setX(newX);
				gizmo.setY(newY);
				getGizmos().add(gizmo);
				occupied[newX][newY] = true;
				gizmo.notifyAllObservers();
				return true;
			} else {
				if (gizmo instanceof TriangleTakoz) {
					if (((TriangleTakoz) gizmo).getDirection() == DirectionEnum.SOUTH_WEST) {
						getGizmos().remove(gizmo);
						gizmo.notifyAllObservers();
						gizmo.setX(newX);
						gizmo.setY(newY + 1);
						getGizmos().add(gizmo);
						occupied[newX][newY] = true;
						gizmo.notifyAllObservers();
						return true;
					} else {
						if (((TriangleTakoz) gizmo).getDirection() == DirectionEnum.SOUTH_EAST) {
							getGizmos().remove(gizmo);
							gizmo.notifyAllObservers();
							gizmo.setX(newX + 1);
							gizmo.setY(newY + 1);
							getGizmos().add(gizmo);
							occupied[newX][newY] = true;
							gizmo.notifyAllObservers();
							return true;
						} else {
							if (((TriangleTakoz) gizmo).getDirection() == DirectionEnum.NORTH_EAST) {
								getGizmos().remove(gizmo);
								gizmo.notifyAllObservers();
								gizmo.setX(newX + 1);
								gizmo.setY(newY);
								getGizmos().add(gizmo);
								occupied[newX][newY] = true;
								gizmo.notifyAllObservers();
								return true;
							} else {
								if (((TriangleTakoz) gizmo).getDirection() == DirectionEnum.NORTH_WEST) {
									getGizmos().remove(gizmo);
									gizmo.notifyAllObservers();
									gizmo.setX(newX);
									gizmo.setY(newY);
									getGizmos().add(gizmo);
									occupied[newX][newY] = true;
									gizmo.notifyAllObservers();
									return true;
								}
							}
						}
					}
				} else {
					if (gizmo instanceof Firildak) {
						boolean available = true;
						for (int i = newX - 1; i <= newX + 1; i++) {
							for (int j = newY - 1; j <= newY + 1; j++) {
								if (getCurrentPlayer() == getPlayers().get(0)) {
									if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
										available = false;
										break;
									}
								} else {
									if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
										available = false;
										break;
									}
								}
							}
							if (!available) {
								break;
							}
						}

						if (available) {
							getGizmos().remove(gizmo);
							gizmo.notifyAllObservers();
							gizmo.move(newX, newY);
							getGizmos().add(gizmo);
							((Firildak) gizmo).setCenter(new Vect(newX + 0.5, newY + 0.5));
							gizmo.notifyAllObservers();
							for (int i = newX - 1; i <= newX + 1; i++) {
								for (int j = newY - 1; j <= newY + 1; j++) {
									occupied[i][j] = true;
								}
							}
							return true;
						}
					} else {
						if (gizmo instanceof LeftTokat) {
							if (((LeftTokat) gizmo).getDirection() == DirectionEnum.SOUTH) {
								boolean available = true;
								for (int i = newX; i <= newX + 1; i++) {
									for (int j = newY; j <= newY + 1; j++) {
										if (getCurrentPlayer() == getPlayers().get(0)) {
											if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
												available = false;
												break;
											}
										} else {
											if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
												available = false;
												break;
											}
										}
									}
									if (!available) {
										break;
									}
								}

								if (available) {
									getGizmos().remove(gizmo);
									gizmo.notifyAllObservers();
									gizmo.move(newX, newY);
									getGizmos().add(gizmo);
									gizmo.notifyAllObservers();
									for (int i = newX; i <= newX + 1; i++) {
										for (int j = newY; j <= newY + 1; j++) {
											occupied[i][j] = true;
										}
									}
									return true;
								}
							} else {
								if (((LeftTokat) gizmo).getDirection() == DirectionEnum.EAST) {
									boolean available = true;
									for (int i = newX; i <= newX + 1; i++) {
										for (int j = newY - 1; j <= newY; j++) {
											if (getCurrentPlayer() == getPlayers().get(0)) {
												if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
													available = false;
													break;
												}
											} else {
												if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
													available = false;
													break;
												}
											}
										}
										if (!available) {
											break;
										}
									}

									if (available) {
										getGizmos().remove(gizmo);
										gizmo.notifyAllObservers();
										gizmo.move(newX, newY + 1);
										getGizmos().add(gizmo);
										gizmo.notifyAllObservers();
										for (int i = newX; i <= newX + 1; i++) {
											for (int j = newY - 1; j <= newY; j++) {
												occupied[i][j] = true;
											}
										}
										return true;
									}
								} else {
									if (((LeftTokat) gizmo).getDirection() == DirectionEnum.NORTH) {
										boolean available = true;
										for (int i = newX - 1; i <= newX; i++) {
											for (int j = newY - 1; j <= newY; j++) {
												if (getCurrentPlayer() == getPlayers().get(0)) {
													if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
														available = false;
														break;
													}
												} else {
													if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
														available = false;
														break;
													}
												}
											}
											if (!available) {
												break;
											}
										}

										if (available) {
											getGizmos().remove(gizmo);
											gizmo.notifyAllObservers();
											gizmo.move(newX + 1, newY + 1);
											getGizmos().add(gizmo);
											gizmo.notifyAllObservers();
											for (int i = newX - 1; i <= newX; i++) {
												for (int j = newY - 1; j <= newY; j++) {
													occupied[i][j] = true;
												}
											}
											return true;
										}
									} else {
										if (((LeftTokat) gizmo).getDirection() == DirectionEnum.WEST) {
											boolean available = true;
											for (int i = newX - 1; i <= newX; i++) {
												for (int j = newY; j <= newY + 1; j++) {
													if (getCurrentPlayer() == getPlayers().get(0)) {
														if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
															available = false;
															break;
														}
													} else {
														if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
															available = false;
															break;
														}
													}
												}
												if (!available) {
													break;
												}
											}

											if (available) {
												getGizmos().remove(gizmo);
												gizmo.notifyAllObservers();
												gizmo.move(newX + 1, newY);
												getGizmos().add(gizmo);
												gizmo.notifyAllObservers();
												for (int i = newX - 1; i <= newX; i++) {
													for (int j = newY; j <= newY + 1; j++) {
														occupied[i][j] = true;
													}
												}
												return true;
											}
										}
									}
								}
							}
						} else {
							if (gizmo instanceof RightTokat) {
								if (((RightTokat) gizmo).getDirection() == DirectionEnum.SOUTH) {
									boolean available = true;
									for (int i = newX - 1; i <= newX; i++) {
										for (int j = newY; j <= newY + 1; j++) {
											if (getCurrentPlayer() == getPlayers().get(0)) {
												if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
													available = false;
													break;
												}
											} else {
												if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
													available = false;
													break;
												}
											}
										}
										if (!available) {
											break;
										}
									}

									if (available) {
										getGizmos().remove(gizmo);
										gizmo.notifyAllObservers();
										gizmo.move(newX + 1, newY);
										getGizmos().add(gizmo);
										gizmo.notifyAllObservers();
										for (int i = newX - 1; i <= newX; i++) {
											for (int j = newY; j <= newY + 1; j++) {
												occupied[i][j] = true;
											}
										}
										return true;
									}
								} else {
									if (((RightTokat) gizmo).getDirection() == DirectionEnum.EAST) {
										boolean available = true;
										for (int i = newX; i <= newX + 1; i++) {
											for (int j = newY; j <= newY + 1; j++) {
												if (getCurrentPlayer() == getPlayers().get(0)) {
													if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
														available = false;
														break;
													}
												} else {
													if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
														available = false;
														break;
													}
												}
											}
											if (!available) {
												break;
											}
										}

										if (available) {
											getGizmos().remove(gizmo);
											gizmo.notifyAllObservers();
											gizmo.move(newX, newY);
											getGizmos().add(gizmo);
											gizmo.notifyAllObservers();
											for (int i = newX; i <= newX + 1; i++) {
												for (int j = newY; j <= newY + 1; j++) {
													occupied[i][j] = true;
												}
											}
											return true;
										}
									} else {
										if (((RightTokat) gizmo).getDirection() == DirectionEnum.NORTH) {
											boolean available = true;
											for (int i = newX; i <= newX + 1; i++) {
												for (int j = newY - 1; j <= newY; j++) {
													if (getCurrentPlayer() == getPlayers().get(0)) {
														if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
															available = false;
															break;
														}
													} else {
														if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
															available = false;
															break;
														}
													}
												}
												if (!available) {
													break;
												}
											}

											if (available) {
												getGizmos().remove(gizmo);
												gizmo.notifyAllObservers();
												gizmo.move(newX + 1, newY + 1);
												getGizmos().add(gizmo);
												gizmo.notifyAllObservers();
												for (int i = newX; i <= newX + 1; i++) {
													for (int j = newY - 1; j <= newY; j++) {
														occupied[i][j] = true;
													}
												}
												return true;
											}
										} else {
											if (((RightTokat) gizmo).getDirection() == DirectionEnum.WEST) {
												boolean available = true;
												for (int i = newX - 1; i <= newX; i++) {
													for (int j = newY - 1; j <= newY; j++) {
														if (getCurrentPlayer() == getPlayers().get(0)) {
															if (i < 0 || j < 0 || i > 11 || j > 18 || occupied[i][j]) {
																available = false;
																break;
															}
														} else {
															if (i < 13 || j < 0 || i > 24 || j > 18 || occupied[i][j]) {
																available = false;
																break;
															}
														}
													}
													if (!available) {
														break;
													}
												}

												if (available) {
													getGizmos().remove(gizmo);
													gizmo.notifyAllObservers();
													gizmo.move(newX + 1, newY + 1);
													getGizmos().add(gizmo);
													gizmo.notifyAllObservers();
													for (int i = newX - 1; i <= newX; i++) {
														for (int j = newY - 1; j <= newY; j++) {
															occupied[i][j] = true;
														}
													}
													return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public void hideGizmo(Gizmo gizmo) {
		getGizmos().remove(gizmo);
		gizmo.notifyAllObservers();
	}

	public void showGizmo(Gizmo gizmo) {
		getGizmos().add(gizmo);
		gizmo.notifyAllObservers();
	}

	/**
	 * @effects If cezmiIndex is 0, it moves the Cezmi at the left side of the
	 *          board in the given direction. If it's 1, it moves the Cezmi at
	 *          the right side of the board in the given direction.
	 * @requires 0<=cezmiIndex<=1 and direction is DirectionEnum.LEFT or
	 *           DirectionEnum.RIGHT
	 * @param cezmiIndex:
	 *            is equal to 0 for the first Cezmi and 1 for the second Cezmi.
	 * @param direction:
	 *            Direction that the Cezmi will go to.
	 **/
	public void moveCezmi(int cezmiIndex, DirectionEnum direction) {
		Cezmi cezmi = cezmis.get(cezmiIndex);
		if (direction == DirectionEnum.WEST)
			cezmi.moveLeft();
		else
			cezmi.moveRight();
	}

	public static void resetBoardModel() {
		boardModel = new BoardModel();
	}

	public void triggerTokat(Player player) {

	}

	/**
	 * @return Returns an arraylist of Ball objects named balls.
	 */
	public ArrayList<Ball> getBalls() {
		return balls;
	}

	/**
	 * @effects Sets balls field, which is an arraylist of Ball objects, to
	 *          balls input.
	 * @modifies balls, an arraylist of Ball objects.
	 * @param balls
	 **/
	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	/**
	 * @return Returns L which is an int.
	 **/
	public static int getL() {
		return L;
	}

	/**
	 * @return Returns cezmis, which is an ArrayList of Cezmi objects.
	 **/
	public ArrayList<Cezmi> getCezmis() {
		return cezmis;
	}

	/**
	 * @effects Sets cezmis field, which is an arraylist of Cezmi objects, to
	 *          cezmis input.
	 * @modifies cezmis, an arraylist of Cezmi objects.
	 * @param cezmis
	 **/
	public void setCezmis(ArrayList<Cezmi> cezmis) {
		this.cezmis = cezmis;
	}

	/**
	 * @return Returns engel, which is an Engel object.
	 **/
	public Engel getEngel() {
		return engel;
	}

	/**
	 * @effects Sets engel field, which is an Engel object, to engel input.
	 * @modifies engel, an Engel object.
	 * @param engel
	 **/
	public void setEngel(Engel engel) {
		this.engel = engel;
	}

	/**
	 * @return Returns walls, which is an ArrayList of Wall objects.
	 **/
	public ArrayList<Wall> getWalls() {
		return walls;
	}

	/**
	 * @effects Sets walls field, which is an arraylist of Wall objects, to
	 *          walls input.
	 * @modifies walls, an arraylist of Wall objects.
	 * @param walls
	 **/
	public void setWalls(ArrayList<Wall> walls) {
		this.walls = walls;
	}

	/**
	 * @return Returns gizmos, which is an ArrayList of Gizmo objects.
	 **/
	public ArrayList<Gizmo> getGizmos() {
		return gizmos;
	}

	/**
	 * @effects Sets gizmos field, which is an arraylist of Gizmo objects, to
	 *          gizmos input.
	 * @modifies walls, an arraylist of Gizmo objects.
	 * @param gizmos
	 **/
	public void setGizmos(ArrayList<Gizmo> gizmos) {
		this.gizmos = gizmos;
	}

	/**
	 * @return Returns mode, which is an Enum.
	 **/
	public ModeEnum getMode() {
		return mode;
	}

	/**
	 * @effects Sets mode field, which is an Enum, to mode input.
	 * @modifies mode, an Enum.
	 * @param mode
	 **/
	public void setMode(ModeEnum mode) {
		this.mode = mode;
	}

	/**
	 * @return Returns state, which is an Enum.
	 **/
	public StateEnum getState() {
		return state;
	}

	/**
	 * @effects Sets state field, which is an Enum, to state input.
	 * @modifies state, an Enum.
	 * @param state
	 **/
	public void setState(StateEnum state) {
		this.state = state;
	}

	/**
	 * @return Returns players, which is an ArrayList of Player objects.
	 **/
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @effects Sets players field, which is an arraylist of Player objects, to
	 *          players input.
	 * @modifies players, an arraylist of Player objects.
	 * @param players
	 **/
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * @return Returns occupied, which is a multidimensional array of booleans.
	 **/
	public boolean[][] getOccupied() {
		return occupied;
	}

	/**
	 * @effects Sets occupied field, which is a multidimensional array of
	 *          booleans, to occupied input.
	 * @modifies occupied, a multidimensional array of booleans.
	 * @param occupied
	 **/
	public void setOccupied(boolean[][] occupied) {
		this.occupied = occupied;
	}

	/**
	 * @return Returns level, which is an Enum
	 **/
	public LevelEnum getLevel() {
		return level;
	}

	/**
	 * @effects: Sets level field, which is an Enum, to level input.
	 * @modifies: level, a Level object.
	 * @param level
	 **/
	public void setLevel(LevelEnum level) {
		this.level = level;
	}

	/**
	 * @return Returns observers, which is an arraylist of Observer objects.
	 **/
	public ArrayList<Observer> getObservers() {
		return observers;
	}

	/**
	 * @effects Sets observers field, which is an arraylist of Observer objects,
	 *          to observers input.
	 * @modifies level, a Level object.
	 * @param observers
	 **/
	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;
	}

	/**
	 * @return Returns cezerye, which is a Cezerye object.
	 **/
	public Cezerye getCezerye() {
		return cezerye;
	}

	/**
	 * @effects Sets cezerye field, which is a Cezerye object, to cezerye input.
	 * @modifies cezerye, which is a Cezerye object.
	 * @param cezerye
	 **/
	public void setCezerye(Cezerye cezerye) {
		this.cezerye = cezerye;
	}

	@Override
	public void attachObserver(Observer observer) {
		getObservers().add(observer);
	}

	@Override
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
}