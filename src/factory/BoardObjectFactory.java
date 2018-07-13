package factory;

import java.awt.Color;

import boardobject.*;
import enums.BoardObjectEnum;
import enums.LevelEnum;
import model.BoardModel;

public class BoardObjectFactory {

	private static BoardObjectFactory instance;

	/**
	 * Creates a singleton BoardObjectFactory.
	 */
	private BoardObjectFactory() {

	}

	/**
	 * @modifies instance attribute is modified.
	 * @return Returns the singleton BoardObjectFactory.
	 */
	public static BoardObjectFactory getInstance() {
		if (instance == null)
			instance = new BoardObjectFactory();
		return instance;
	}

	/**
	 * 
	 * @param boardObject:
	 *            Type of the gizmo that is wanted to create.
	 * @param x:
	 *            x-coordinate of the gizmo that is wanted to create.
	 * @param y:
	 *            y-coordinate of the gizmo that is wanted to create.
	 * @requires x and y coordinates are positive and smaller than 25.
	 * @effects New Gizmo instance is created and reflected to the screen.
	 * @return Returns the created gizmo.
	 */
	public Gizmo createGizmo(BoardObjectEnum boardObject, int x, int y) {
		BoardModel.getInstance();
		// Following local variables indicate we won't scale down pixels
		// from user input. They will get shrunk inside the factory.
		// Is it ok?
		switch (boardObject) {
		case SQUARE_TAKOZ: // Width and height must be written in L units.
			return new SquareTakoz(x, y, 1.0, 1.0, Color.WHITE);
		case TRIANGLE_TAKOZ:
			return new TriangleTakoz(x, y + 1, 1.0, 1.0, Color.RED);
		case FIRILDAK:
			return new Firildak(x, y, 1.0, 1.0, Color.MAGENTA);
		case LEFT_TOKAT:
			return new LeftTokat(x, y);
		case RIGHT_TOKAT:
			return new RightTokat(x + 1, y);
		default:
			return null;
		}
	}

	/**
	 * 
	 * @param boardObject:
	 *            Type of the board object that is wanted to create.
	 * @param x:
	 *            x-coordinate of the board object that is wanted to create.
	 * @param y:
	 *            y-coordinate of the board object that is wanted to create.
	 * @requires x and y coordinates are positive and smaller than 25.
	 * @effects New BoardObject instance is created and reflected to the screen.
	 * @return Returns the created board object.
	 */
	public BoardObject createBoardObject(BoardObjectEnum boardObject, double x, double y) {
		switch (boardObject) {
		case BALL:
			double vx;
			double vy;
			if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
				vx = (Math.random() * 7) + 3;
				vy = (Math.random() * 7) + 3;
			} else {
				vx = (Math.random() * 15) + 5;
				vy = (Math.random() * 15) + 5;
			}

			return new Ball(x * BoardModel.getL(), y * BoardModel.getL(), vx, -vy);
		case CEZMI:
			return new Cezmi(x, y, new Color(0, 255, 255), 0.5, null);
		case ENGEL:
			return new Engel();
		case CEZERYE:
			return new Cezerye(x, y);
		case WALL:
			return new Wall((int) x, (int) y, 0.0, 0.0); // Primitive type
															// mismatch
		default:
			return null;
		}
	}

}
