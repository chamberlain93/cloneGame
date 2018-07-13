package boardobject;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import enums.DirectionEnum;
import enums.LevelEnum;
import main.Constants;
import model.BoardModel;
import model.Player;
import observer.*;
import physics.Circle;
import physics.Geometry;
import physics.Geometry.VectPair;
import physics.LineSegment;
import physics.Vect;

/**
 * 
 * @author UC This class extends BoardObject class and implements Observable
 *         interface. This class includes methods about ball such as moving
 *         ball, changing ball velocity, etc.
 */
public class Ball extends BoardObject implements Observable {

	private double[] range1 = { 0, 200 };
	private double[] range2 = { 100, 400 };
	private double velocityX = Math.random() * getEffectiveRange()[1] * getL() / 6000;
	private double velocityY = Math.random() * getEffectiveRange()[1] * getL() / 6000;
	private Object[] history = new Object[3];
	private List<Observer> observers = new ArrayList<Observer>();
	boolean hitLeftWall = false;
	boolean hitRightWall = false;

	private static double gravity = 5;
	private static double mu = 0.025;
	private static double mu2 = 0.025;
	private static double delta_t = 0.02;

	public static double getDelta_t() {
		return delta_t;
	}

	public static void setDelta_t(double delta_t) {
		Ball.delta_t = delta_t;
	}

	public static void setGravity(double gravity) {
		Ball.gravity = gravity;
	}

	public static void setMu(double mu) {
		Ball.mu = mu;
	}

	public static void setMu2(double mu2) {
		Ball.mu2 = mu2;
	}

	/**
	 * 
	 * @return Returns the ball velocity range for level 1.
	 */
	public double[] getRange1() {
		return range1;
	}

	/**
	 * 
	 * @param range1:
	 *            Allowed range of ball velocity in level 1. Its length is 2.
	 *            First element is the lower limit and second element is the
	 *            upper limit.
	 * @requires double array with length of 2.
	 * @modifies Range of the ball velocity for level 1 is modified.
	 * @effects Sets the range of ball velocity for level 1 to the given range
	 *          range1.
	 */
	public void setRange1(double[] range1) {
		this.range1 = range1;
	}

	/**
	 * 
	 * @return Returns the ball velocity range for level 2.
	 */
	public double[] getRange2() {
		return range2;
	}

	/**
	 * 
	 * @param range2:
	 *            Allowed range of ball velocity in level 2. Its length is 2.
	 *            First element is the lower limit and second element is the
	 *            upper limit.
	 * @requires double array with length of 2.
	 * @modifies RSange of the ball velocity for level 2 is modified.
	 * @effects Sets the range of ball velocity for level 2 to the given range
	 *          range2.
	 */
	public void setRange2(double[] range2) {
		this.range2 = range2;
	}

	/**
	 * 
	 * @return Returns the ball history array where the last Cezmi which the
	 *         ball hit and the last board object which the ball hit are
	 *         recorded.
	 */
	public Object[] getHistory() {
		return history;
	}

	/**
	 * 
	 * @param history:
	 *            History of objects that ball hits. Its length is 2. First
	 *            element is an instance of Cezmi and second one is an instance
	 *            of BoardObject.
	 * @requires Object array with length of 2, and first element is an instance
	 *           of Cezmi and second element is an instance of BoardObject.
	 * @modifies Ball history is modified.
	 * @effects Sets the ball history to the given history array.
	 */
	public void setHistory(Object[] history) {
		this.history = history;
	}

	/**
	 * 
	 * @return Returns the observers of the ball.
	 */
	public List<Observer> getObservers() {
		return observers;
	}

	/**
	 * 
	 * @param observers:
	 *            List of observers that observe the ball.
	 * @requires List of Observer instances.
	 * @modifies Ball observers are modified.
	 * @effects Sets the ball observers to the given observers list.
	 */
	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	/**
	 * 
	 * @return Returns x-velocity of the ball.
	 */
	public double getVelocityX() {
		return velocityX;
	}

	/**
	 * 
	 * @param velocityX:
	 *            X component of ball velocity.
	 * @requires double velocityX in range of the ball velocity according to the
	 *           level.
	 * @modifies x-velocity of the ball is modified.
	 * @effects Sets x-velocity of the ball to the given velocityX. If the given
	 *          velocityX is not in the range, then it is randomly generated.
	 */
	public void setVelocityX(double velocityX) {
		// if(velocityX >= getEffectiveRange()[0] && velocityX <=
		// getEffectiveRange()[1]){
		this.velocityX = velocityX;
		// }else{
		// if(getEffectiveRange()[0] == 0){
		// this.velocityX = (Math.random()*200)/6000;
		// }else{
		// this.velocityX = (Math.random()*300+100)/6000;
		// }
		// }

	}

	/**
	 * 
	 * @return Returns y-velocity of the ball.
	 */
	public double getVelocityY() {
		return velocityY;
	}

	/**
	 * 
	 * @param velocityY:
	 *            Y component of ball velocity.
	 * @requires double velocityY in range of the ball velocity according to the
	 *           level.
	 * @modifies y-velocity of the ball is modified.
	 * @effects Sets y-velocity of the ball to the given velocityY. If the given
	 *          velocityY is not in the range, then it is randomly generated.
	 */
	public void setVelocityY(double velocityY) {
		// if(velocityY >= getEffectiveRange()[0] && velocityY <=
		// getEffectiveRange()[1]){
		this.velocityY = velocityY;
		// }else{
		// if(getEffectiveRange()[0] == 0){
		// this.velocityY = (Math.random()*200)/6000;
		// }else{
		// this.velocityY = (Math.random()*300+100)/6000;
		// }
		// }
	}

	/**
	 * 
	 * @return Returns the gravitational acceleration.
	 */
	public static double getGravity() {
		return gravity;
	}

	/**
	 * 
	 * @return Returns the mu constant.
	 */
	public static double getMu() {
		return mu;
	}

	/**
	 * 
	 * @return Returns the mu2 constant.
	 */
	public static double getMu2() {
		return mu2;
	}

	/**
	 * 
	 * @return Returns the delta_t constant.
	 */
	public static double getDeltaT() {
		return delta_t;
	}

	public boolean isHitLeftWall() {
		return hitLeftWall;
	}

	public void setHitLeftWall(boolean hitLeftWall) {
		this.hitLeftWall = hitLeftWall;
	}

	public boolean isHitRightWall() {
		return hitRightWall;
	}

	public void setHitRightWall(boolean hitRightWall) {
		this.hitRightWall = hitRightWall;
	}

	/**
	 * 
	 * @param x:
	 *            X coordinate of the center of ball.
	 * @param y:
	 *            Y coordinate of the center of ball.
	 * @param velocityX:
	 *            X component of ball velocity.
	 * @param velocityY:
	 *            Y component of ball velocity.
	 * @requires x and y coordinates are positive and smaller than 25.
	 * @modifies x, y, velocityX and velocityY attributes are modified.
	 * @effects New ball instance is created and reflected to the screen.
	 */
	public Ball(double x, double y, double velocityX, double velocityY) {
		super(x, y, 20, 20, new Color(255, 0, 0));
		// Initial default width and height of the ball is 1, and ball color is
		// red.
		setVelocityX(velocityX);
		setVelocityY(velocityY);
		attachObserver(new BallObserver());
	}

	/**
	 * repOk method for Ball.
	 * 
	 * @return Returns true if the representation invariant holds for this;
	 *         otherwise returns false.
	 */
	public boolean repOK() {
		if (!super.repOK()) {
			return false;
		} else {
			if (velocityX >= getEffectiveRange()[0] && velocityX <= getEffectiveRange()[1]
					&& velocityY >= getEffectiveRange()[0] && velocityY <= getEffectiveRange()[1]) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * toString method for Ball.
	 */
	public String toString() {
		return "[Ball= x: " + getX() + ", y: " + getY() + ", width: " + getWidth() + ", height: " + getHeight()
				+ ", color: " + getColor().toString() + ", velocity-x: " + getVelocityX() + " and velocity-y: "
				+ getVelocityY() + "]";
	}

	/**
	 * 
	 * @return Returns the range which is in use according to selected level.
	 */
	public double[] getEffectiveRange() {
		if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
			return range1;
		} else {
			return range2;
		}
	}

	/**
	 * 
	 * @return Calculates the ball velocity and returns it.
	 */
	public double velocity() {
		return Math.sqrt(Math.pow(getVelocityX(), 2) + Math.pow(getVelocityY(), 2));
	}

	/**
	 * 
	 * @param level:
	 *            Game level.
	 * @effects The effect of friction is applied to the ball velocity according
	 *          to selected level.
	 * @return Returns the new calculated velocity of the ball after the
	 *         friction effect.
	 */
	public double newVelocity(LevelEnum level) {
		if (level == LevelEnum.LEVEL1) {
			double hiz = velocity() * (1 - (getMu() * getDeltaT()) - (getMu2() * Math.abs(velocity()) * getDeltaT()));
			return hiz;
		} else {
			return velocity() * (1 - getMu() * 2 * getDeltaT() - getMu2() * Math.abs(velocity()) * 2 * getDeltaT());
		}
	}

	/**
	 * 
	 * @param level:
	 *            Game level.
	 * @modifies x-velocity of the ball is modified.
	 * @effects The effect of friction is applied to x-velocity of the ball
	 *          according to selected level.
	 * @return Returns the new x-velocity of the ball after the friction effect.
	 */
	public double newVelocityX(LevelEnum level) {
		if (getVelocityX() >= 0) {
			if (getVelocityX() > 0) {
				double cos = Math.abs(getVelocityX() / velocity());
				return newVelocity(level) * cos;
			} else {
				return 0;
			}
		} else {
			double cos = Math.abs(getVelocityX() / velocity());
			return -newVelocity(level) * cos;
		}
	}

	/**
	 * 
	 * @param level:
	 *            Game level.
	 * @modifies y-velocity of the ball is modified.
	 * @effects The effect of friction and gravitation are applied to y-velocity
	 *          of the ball according to selected level.
	 * @return Returns the new y-velocity of the ball after the friction and
	 *         gravitational effects.
	 */
	public double newVelocityY(LevelEnum level) {
		if (getVelocityY() >= 0) {
			if (getVelocityY() > 0) {
				double sin = Math.abs(getVelocityY() / velocity());
				return newVelocity(level) * sin + getGravity() * getDeltaT();
			} else {
				return getGravity() * getDeltaT();
			}
		} else {
			double sin = Math.abs(getVelocityY() / velocity());
			return -newVelocity(level) * sin + getGravity() * getDeltaT();
		}
	}

	/**
	 * @modifies Width and height of the ball is modified.
	 * @effects Ball diameter is decreased if total score of player 1 and player
	 *          2 is even.
	 */
	public static void shrinkBall() {
		double player1Score = BoardModel.getInstance().getPlayers().get(0).getScore();
		double player2Score = BoardModel.getInstance().getPlayers().get(1).getScore();
		double totalScore = player1Score + player2Score;
		for (Ball ball : BoardModel.getInstance().getBalls()) {
			if (totalScore % 2 == 0 && ball.getWidth() > 4) {
				ball.setWidth(ball.getWidth() - 2);
				ball.setHeight(ball.getHeight() - 2);
			}
		}
	}

	/**
	 * 
	 * @param cezmi:
	 *            An instance of Cezmi class.
	 * @modifies History of the ball is modified.
	 * @effects Last cezmi which the ball hit is recorded to the history.
	 */
	public void updateCezmiHistory(Cezmi cezmi) {
		getHistory()[0] = cezmi;
	}

	/**
	 * 
	 * @param boardObject:
	 *            An instance of BoardObject class.
	 * @modifies History of the ball is modified.
	 * @effects Last board object which the ball hit is recorded to the history.
	 */
	public void updateObjectHistory(BoardObject boardObject) {
		getHistory()[1] = getHistory()[2];
		getHistory()[2] = boardObject;
	}

	/**
	 * @effects New fictional ball is created in order to represent the next
	 *          position of the ball.
	 * @return Returns the fictional ball of the ball in the next position.
	 */
	public Ball nextPosition() {
		double nextX = getX() + getVelocityX();
		double nextY = getY() + getVelocityY();
		return new Ball(nextX, nextY, getVelocityX(), getVelocityY());
	}

	/**
	 * @effects Checks whether the ball hits any other ball or not.
	 * @return Returns the ball if the ball hits any, else returns null.
	 */
	public Ball collisionCheckWithBalls() {
		Ball nextBall = nextPosition();
		for (Ball ball : BoardModel.getInstance().getBalls()) {
			if (ball != this) {
				if (Math.sqrt(Math.pow(Math.abs(ball.getX() - nextBall.getX()), 2)
						+ Math.pow(Math.abs(ball.getY() - nextBall.getY()), 2)) <= (ball.getWidth() / 2
								+ nextBall.getWidth() / 2)) {
					return ball;
				}
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits any cezmi or not.
	 * @return Returns the cezmi if the ball hits any, else returns null.
	 */
	public Cezmi collisionCheckWithCezmis() {
		Ball nextBall = nextPosition();
		for (Cezmi cezmi : BoardModel.getInstance().getCezmis()) {
			if (Math.sqrt(Math.pow(Math.abs(getL() * cezmi.getX() - nextBall.getX()), 2)
					+ Math.pow(Math.abs(getL() * cezmi.getY() - nextBall.getY()), 2)) <= (getL() * cezmi.getHeight()
							+ nextBall.getWidth() / 2)) {
				return cezmi;
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits the engel or not.
	 * @return Returns the engel if the ball hits it, else returns null.
	 */

	public Engel collisionCheckWithEngel() {
		Ball nextBall = nextPosition();
		Rectangle nextBallRectangle = new Rectangle((int) (nextBall.getX() - nextBall.getWidth() / 2),
				(int) (nextBall.getY() - nextBall.getHeight() / 2), (int) nextBall.getWidth(),
				(int) nextBall.getHeight());
		Engel engel = BoardModel.getInstance().getEngel();
		Rectangle engelRectangle = new Rectangle((int) (getL() * engel.getX()), (int) (getL() * engel.getY()),
				(int) (getL() * engel.getWidth()), (int) (getL() * engel.getHeight()));
		if (nextBallRectangle.intersects(engelRectangle)) {
			return engel;
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits any wall or not.
	 * @return Returns the wall if the ball hits any, else returns null.
	 */
	public Wall collisionCheckWithWalls() {
		Ball nextBall = nextPosition();
		for (int i = 0; i < BoardModel.getInstance().getWalls().size(); i++) {
			switch (i) {
			case 0:
				if (nextBall.getY() <= nextBall.getHeight() / 2) {
					return BoardModel.getInstance().getWalls().get(0);
				}
				break;
			case 1:
				if (nextBall.getX() >= (getL() * 25) - nextBall.getWidth() / 2) {
					return BoardModel.getInstance().getWalls().get(1);
				}
				break;
			case 2:
				if (nextBall.getY() >= (getL() * 25) - nextBall.getHeight() / 2) {
					return BoardModel.getInstance().getWalls().get(2);
				}
				break;
			case 3:
				if (nextBall.getX() <= nextBall.getWidth() / 2) {
					return BoardModel.getInstance().getWalls().get(3);
				}
				break;
			default:
				break;
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits any firildak or not.
	 * @return Returns the firildak if the ball hits any, else returns null.
	 */
	public Firildak collisionCheckWithFirildaks() {
		Ball nextBall = nextPosition();
		for (Gizmo gizmo : BoardModel.getInstance().getGizmos()) {
			if (gizmo instanceof Firildak) {
				Rectangle nextBallRectangle = new Rectangle((int) (nextBall.getX() - nextBall.getWidth() / 2),
						(int) (nextBall.getY() - nextBall.getHeight() / 2), (int) nextBall.getWidth(),
						(int) nextBall.getHeight());
				double centerX = (getL() * gizmo.getX()) + (getL() * gizmo.getWidth()) / 2;
				double centerY = (getL() * gizmo.getY()) + (getL() * gizmo.getHeight()) / 2;
				double margin = (BoardModel.getL() / 2) * Math.sqrt(2);
				Rectangle gizmoRectangle = new Rectangle((int) (centerX - margin), (int) (centerY - margin),
						(int) (margin * 2), (int) (margin * 2));
				if (nextBallRectangle.intersects(gizmoRectangle)) {
					return (Firildak) gizmo;
				}
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits any triangle takoz or not.
	 * @return Returns the triangle takoz if the ball hits any, else returns
	 *         null.
	 */
	public TriangleTakoz collisionCheckWithTriangleTakoz() {
		Ball nextBall = nextPosition();
		for (Gizmo gizmo : BoardModel.getInstance().getGizmos()) {
			if (gizmo instanceof TriangleTakoz) {
				Rectangle nextBallRectangle = new Rectangle((int) (nextBall.getX() - nextBall.getWidth() / 2),
						(int) (nextBall.getY() - nextBall.getHeight() / 2), (int) nextBall.getWidth(),
						(int) nextBall.getHeight());
				LineSegment edge1;
				LineSegment edge2;
				LineSegment hypotenuse;
				if (((TriangleTakoz) gizmo).getDirection() == DirectionEnum.SOUTH_WEST) {
					edge1 = new LineSegment((getL() * gizmo.getX()), (getL() * (gizmo.getY() - 1)),
							(getL() * gizmo.getX()), (getL() * gizmo.getY()));
					edge2 = new LineSegment((getL() * gizmo.getX()), (getL() * gizmo.getY()),
							(getL() * (gizmo.getX() + 1)), (getL() * gizmo.getY()));
					hypotenuse = new LineSegment((getL() * gizmo.getX()), (getL() * (gizmo.getY() - 1)),
							(getL() * (gizmo.getX() + 1)), (getL() * gizmo.getY()));
				} else {
					if (((TriangleTakoz) gizmo).getDirection() == DirectionEnum.SOUTH_EAST) {
						edge1 = new LineSegment((getL() * gizmo.getX()), (getL() * (gizmo.getY() - 1)),
								(getL() * gizmo.getX()), (getL() * gizmo.getY()));
						edge2 = new LineSegment((getL() * (gizmo.getX() - 1)), (getL() * gizmo.getY()),
								(getL() * gizmo.getX()), (getL() * gizmo.getY()));
						hypotenuse = new LineSegment((getL() * gizmo.getX()), (getL() * (gizmo.getY() - 1)),
								(getL() * (gizmo.getX() - 1)), (getL() * gizmo.getY()));
					} else {
						if (((TriangleTakoz) gizmo).getDirection() == DirectionEnum.NORTH_EAST) {
							edge1 = new LineSegment((getL() * (gizmo.getX() - 1)), (getL() * gizmo.getY()),
									(getL() * gizmo.getX()), (getL() * gizmo.getY()));
							edge2 = new LineSegment((getL() * gizmo.getX()), (getL() * gizmo.getY()),
									(getL() * gizmo.getX()), (getL() * (gizmo.getY() + 1)));
							hypotenuse = new LineSegment((getL() * (gizmo.getX() - 1)), (getL() * gizmo.getY()),
									(getL() * gizmo.getX()), (getL() * (gizmo.getY() + 1)));
						} else {
							edge1 = new LineSegment((getL() * gizmo.getX()), (getL() * gizmo.getY()),
									(getL() * (gizmo.getX() + 1)), (getL() * gizmo.getY()));
							edge2 = new LineSegment((getL() * gizmo.getX()), (getL() * gizmo.getY()),
									(getL() * gizmo.getX()), (getL() * (gizmo.getY() + 1)));
							hypotenuse = new LineSegment((getL() * (gizmo.getX() + 1)), (getL() * gizmo.getY()),
									(getL() * gizmo.getX()), (getL() * (gizmo.getY() + 1)));
						}
					}
				}
				if (nextBallRectangle.intersectsLine(edge1.p1().x(), edge1.p1().y(), edge1.p2().x(), edge1.p2().y())
						|| nextBallRectangle.intersectsLine(edge2.p1().x(), edge2.p1().y(), edge2.p2().x(),
								edge2.p2().y())
						|| nextBallRectangle.intersectsLine(hypotenuse.p1().x(), hypotenuse.p1().y(),
								hypotenuse.p2().x(), hypotenuse.p2().y())) {
					return (TriangleTakoz) gizmo;
				}
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits any square takoz or not.
	 * @return Returns the square takoz if the ball hits any, else returns null.
	 */
	public SquareTakoz collisionCheckWithSquareTakoz() {
		Ball nextBall = nextPosition();
		for (Gizmo gizmo : BoardModel.getInstance().getGizmos()) {
			if (gizmo instanceof SquareTakoz) {
				Rectangle nextBallRectangle = new Rectangle((int) (nextBall.getX() - nextBall.getWidth() / 2),
						(int) (nextBall.getY() - nextBall.getHeight() / 2), (int) nextBall.getWidth(),
						(int) nextBall.getHeight());
				Rectangle squareTakozRectangle = new Rectangle((int) (getL() * gizmo.getX()),
						(int) (getL() * gizmo.getY()), (int) (getL() * gizmo.getWidth()),
						(int) (getL() * gizmo.getHeight()));
				if (nextBallRectangle.intersects(squareTakozRectangle)) {
					return (SquareTakoz) gizmo;
				}
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits any left tokat or not.
	 * @return Returns the left tokat if the ball hits any, else returns null.
	 */
	public LeftTokat collisionCheckWithLeftTokat() {
		Ball nextBall = nextPosition();
		for (Gizmo gizmo : BoardModel.getInstance().getGizmos()) {
			if (gizmo instanceof LeftTokat) {
				Rectangle nextBallRectangle = new Rectangle((int) (nextBall.getX() - nextBall.getWidth() / 2),
						(int) (nextBall.getY() - nextBall.getHeight() / 2), (int) nextBall.getWidth(),
						(int) nextBall.getHeight());
				if (nextBallRectangle.intersectsLine((getL() * ((LeftTokat) gizmo).getTopLine().p1().x()),
						(getL() * ((LeftTokat) gizmo).getTopLine().p1().y()),
						(getL() * ((LeftTokat) gizmo).getTopLine().p2().x()),
						(getL() * ((LeftTokat) gizmo).getTopLine().p2().y()))
						|| nextBallRectangle.intersectsLine((getL() * ((LeftTokat) gizmo).getRightLine().p1().x()),
								(getL() * ((LeftTokat) gizmo).getRightLine().p1().y()),
								(getL() * ((LeftTokat) gizmo).getRightLine().p2().x()),
								(getL() * ((LeftTokat) gizmo).getRightLine().p2().y()))
						|| nextBallRectangle.intersectsLine((getL() * ((LeftTokat) gizmo).getBottomLine().p1().x()),
								(getL() * ((LeftTokat) gizmo).getBottomLine().p1().y()),
								(getL() * ((LeftTokat) gizmo).getBottomLine().p2().x()),
								(getL() * ((LeftTokat) gizmo).getBottomLine().p2().y()))
						|| nextBallRectangle.intersectsLine((getL() * ((LeftTokat) gizmo).getLeftLine().p1().x()),
								(getL() * ((LeftTokat) gizmo).getLeftLine().p1().y()),
								(getL() * ((LeftTokat) gizmo).getLeftLine().p2().x()),
								(getL() * ((LeftTokat) gizmo).getLeftLine().p2().y()))) {
					return (LeftTokat) gizmo;
				}
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits any right tokat or not.
	 * @return Returns the right tokat if the ball hits any, else returns null.
	 */
	public RightTokat collisionCheckWithRightTokat() {
		Ball nextBall = nextPosition();
		for (Gizmo gizmo : BoardModel.getInstance().getGizmos()) {
			if (gizmo instanceof RightTokat) {
				Rectangle nextBallRectangle = new Rectangle((int) (nextBall.getX() - nextBall.getWidth() / 2),
						(int) (nextBall.getY() - nextBall.getHeight() / 2), (int) nextBall.getWidth(),
						(int) nextBall.getHeight());
				if (nextBallRectangle.intersectsLine((getL() * ((RightTokat) gizmo).getTopLine().p1().x()),
						(getL() * ((RightTokat) gizmo).getTopLine().p1().y()),
						(getL() * ((RightTokat) gizmo).getTopLine().p2().x()),
						(getL() * ((RightTokat) gizmo).getTopLine().p2().y()))
						|| nextBallRectangle.intersectsLine((getL() * ((RightTokat) gizmo).getRightLine().p1().x()),
								(getL() * ((RightTokat) gizmo).getRightLine().p1().y()),
								(getL() * ((RightTokat) gizmo).getRightLine().p2().x()),
								(getL() * ((RightTokat) gizmo).getRightLine().p2().y()))
						|| nextBallRectangle.intersectsLine((getL() * ((RightTokat) gizmo).getBottomLine().p1().x()),
								(getL() * ((RightTokat) gizmo).getBottomLine().p1().y()),
								(getL() * ((RightTokat) gizmo).getBottomLine().p2().x()),
								(getL() * ((RightTokat) gizmo).getBottomLine().p2().y()))
						|| nextBallRectangle.intersectsLine((getL() * ((RightTokat) gizmo).getLeftLine().p1().x()),
								(getL() * ((RightTokat) gizmo).getLeftLine().p1().y()),
								(getL() * ((RightTokat) gizmo).getLeftLine().p2().x()),
								(getL() * ((RightTokat) gizmo).getLeftLine().p2().y()))) {
					return (RightTokat) gizmo;
				}
			}
		}
		return null;
	}

	/**
	 * @effects Checks whether the ball hits the cezerye or not.
	 * @return Returns the cezerye if the ball hits any, else returns null.
	 */
	public Cezerye collisionCheckWithCezerye() {
		Ball nextBall = nextPosition();
		Cezerye cezerye = BoardModel.getInstance().getCezerye();
		Rectangle nextBallRectangle = new Rectangle((int) (nextBall.getX() - nextBall.getWidth() / 2),
				(int) (nextBall.getY() - nextBall.getHeight() / 2), (int) nextBall.getWidth(),
				(int) nextBall.getHeight());
		if (cezerye != null) {
			Rectangle cezeryeRectangle = new Rectangle((int) (getL() * cezerye.getX()), (int) (getL() * cezerye.getY()),
					(int) (getL() * cezerye.getWidth()), (int) (getL() * cezerye.getHeight()));
			if (nextBallRectangle.intersects(cezeryeRectangle)) {
				return cezerye;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param ball1:
	 *            Ball object.
	 * @param newBall1Velocity:
	 *            New velocity of <code>ball1</code>.
	 * @param ball2:
	 *            Ball object.
	 * @param newBall2Velocity:
	 *            New velocity of <code>ball2</code>.
	 * @effects Changes <code>ball1</code> and <code>ball2</code> velocities.
	 */
	public static void changeVelocities(Ball ball1, Vect newBall1Velocity, Ball ball2, Vect newBall2Velocity) {
		ball1.setVelocityX(newBall1Velocity.x());
		ball1.setVelocityY(newBall1Velocity.y());

		ball2.setVelocityX(newBall2Velocity.x());
		ball2.setVelocityY(newBall2Velocity.y());
	}

	/**
	 * @modifies x coordinate,y coordinate, x-velocity and y-velocity of ball
	 *           are changed.
	 * @effects Ball is moved and reflected from each BoardObject and also
	 *          friction and gravitational effect is applied to velocity of the
	 *          ball.
	 */
	public synchronized void move() {
		if (collisionCheckWithBalls() != null) {
			Ball hitBall = collisionCheckWithBalls();

			Vect ball1Center = new Vect(getX(), getY());
			double mass1 = 1;
			Vect preBall1Velocity = new Vect(getVelocityX(), getVelocityY());
			if (hitBall != null) {
				Vect ball2Center = new Vect(hitBall.getX(), hitBall.getY());
				double mass2 = 1;
				Vect preBall2Velocity = new Vect(hitBall.getVelocityX(), hitBall.getVelocityY());

				VectPair nextBallVelocities = Geometry.reflectBalls(ball1Center, mass1, preBall1Velocity, ball2Center,
						mass2, preBall2Velocity);

				Ball.changeVelocities(this, nextBallVelocities.v1, hitBall, nextBallVelocities.v2);

				updateObjectHistory(hitBall);
				hitBall.updateObjectHistory(this);
			}
		} else {
			if (collisionCheckWithCezmis() != null) {
				Cezmi hitCezmi = collisionCheckWithCezmis();

				Vect ballCenter = new Vect(getX(), getY());
				Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
				Vect hitCezmiCenter = new Vect((getL() * hitCezmi.getX()), (getL() * hitCezmi.getY()));

				Vect nextBallVelocity = Geometry.reflectCircle(hitCezmiCenter, ballCenter, preBallVelocity, 1);

				setHitLeftWall(false);
				setHitRightWall(false);

				setVelocityX(nextBallVelocity.x());
				setVelocityY(nextBallVelocity.y());

				updateCezmiHistory(hitCezmi);
				updateObjectHistory(hitCezmi);
			} else {
				if (collisionCheckWithEngel() != null) {
					Engel hitEngel = collisionCheckWithEngel();

					Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
					Vect nextBallVelocity = new Vect(getVelocityX(), getVelocityY());
					if (getX() + getWidth() / 2 <= (getL() * hitEngel.getX()) && getY() >= (getL() * hitEngel.getY())) {
						LineSegment leftEdge = new LineSegment(
								new Vect((getL() * hitEngel.getX()), (getL() * hitEngel.getY())),
								new Vect((getL() * hitEngel.getX()),
										(getL() * (hitEngel.getY() + hitEngel.getHeight()))));

						nextBallVelocity = Geometry.reflectWall(leftEdge, preBallVelocity, 1);

						updateObjectHistory(hitEngel);

						boolean scoreChanged = Player.updateScores(BoardModel.getInstance().getPlayers(), this);

						if (scoreChanged) {
							Ball.shrinkBall();
						}
					} else {
						if (getX() >= (getL() * hitEngel.getX())
								&& getX() <= (getL() * (hitEngel.getX() + hitEngel.getWidth()))
								&& getY() + getHeight() / 2 <= (getL() * hitEngel.getY())) {
							LineSegment topEdge = new LineSegment(
									new Vect((getL() * hitEngel.getX()), (getL() * hitEngel.getY())),
									new Vect((getL() * (hitEngel.getX() + hitEngel.getWidth())),
											(getL() * hitEngel.getY())));

							nextBallVelocity = Geometry.reflectWall(topEdge, preBallVelocity, 1);

							updateObjectHistory(hitEngel);

							boolean scoreChanged = Player.updateScores(BoardModel.getInstance().getPlayers(), this);

							if (scoreChanged) {
								Ball.shrinkBall();
							}
						} else {
							if ((getX() - getWidth() / 2) >= (getL() * (hitEngel.getX() + hitEngel.getWidth()))
									&& getY() >= (getL() * hitEngel.getY())) {
								LineSegment rightEdge = new LineSegment(
										new Vect((getL() * (hitEngel.getX() + hitEngel.getWidth())),
												(getL() * hitEngel.getY())),
										new Vect((getL() * (hitEngel.getX() + hitEngel.getWidth())),
												(getL() * (hitEngel.getY() + hitEngel.getHeight()))));

								nextBallVelocity = Geometry.reflectWall(rightEdge, preBallVelocity, 1);

								updateObjectHistory(hitEngel);

								boolean scoreChanged = Player.updateScores(BoardModel.getInstance().getPlayers(), this);

								if (scoreChanged) {
									Ball.shrinkBall();
								}
							} else {
								if (getX() <= (getL() * hitEngel.getX()) && getY() <= (getL() * hitEngel.getY())) {
									Vect ballCenter = new Vect(getX(), getY());
									Vect topLeftCornerCircleCenter = new Vect((getL() * hitEngel.getX()) + 1,
											(getL() * hitEngel.getY()) + 1);

									nextBallVelocity = Geometry.reflectCircle(topLeftCornerCircleCenter, ballCenter,
											preBallVelocity);

									updateObjectHistory(hitEngel);

									boolean scoreChanged = Player.updateScores(BoardModel.getInstance().getPlayers(),
											this);

									if (scoreChanged) {
										Ball.shrinkBall();
									}
								} else {
									if (getX() >= (getL() * (hitEngel.getX() + hitEngel.getWidth()))
											&& getY() <= (getL() * hitEngel.getY())) {
										Vect ballCenter = new Vect(getX(), getY());
										Vect topRightCornerCircleCenter = new Vect(
												(getL() * hitEngel.getX() + hitEngel.getWidth()) - 1,
												(getL() * hitEngel.getY()) + 1);

										nextBallVelocity = Geometry.reflectCircle(topRightCornerCircleCenter,
												ballCenter, preBallVelocity);

										updateObjectHistory(hitEngel);

										boolean scoreChanged = Player
												.updateScores(BoardModel.getInstance().getPlayers(), this);

										if (scoreChanged) {
											Ball.shrinkBall();
										}
									}
								}
							}
						}
					}
					setVelocityX(nextBallVelocity.x());
					setVelocityY(nextBallVelocity.y());
				} else {
					if (collisionCheckWithWalls() != null) {
						Wall hitWall = collisionCheckWithWalls();

						if (hitWall == BoardModel.getInstance().getWalls().get(0)
								|| hitWall == BoardModel.getInstance().getWalls().get(2)) {
							boolean hitFloor = false;
							if (hitWall == BoardModel.getInstance().getWalls().get(2)) {
								hitFloor = true;
							} else {
								setVelocityY(0 - getVelocityY());
							}
							updateObjectHistory(hitWall);

							boolean scoreChanged = Player.updateScores(BoardModel.getInstance().getPlayers(), this);

							if (scoreChanged) {
								Ball.shrinkBall();
							}
							if (hitFloor) {
								setHistory(new Object[3]);
								if (BoardModel.getInstance().getLevel().equals(LevelEnum.LEVEL1)) {
									setY(50);
									setX(250);
								} else {
									if (this.equals(BoardModel.getInstance().getBalls().get(0))) {
										setY(50);
										setX(200);
									} else {
										setY(50);
										setX(300);
									}
								}

								double number = Math.random();
								setVelocityY(number * (-1));
								if (number < 0.5) {
									setVelocityX(number * number + 0.5);
								} else {
									setVelocityX((-1) * number * number - 0.5);
								}
							}
						} else {
							if (hitWall == BoardModel.getInstance().getWalls().get(1)
									|| hitWall == BoardModel.getInstance().getWalls().get(3)) {
								setVelocityX(0 - getVelocityX());
								if (hitWall == BoardModel.getInstance().getWalls().get(1)) {
									setHitRightWall(true);
								}
								if (hitWall == BoardModel.getInstance().getWalls().get(3)) {
									setHitLeftWall(true);
								}

								updateObjectHistory(hitWall);
							}
						}
					} else {
						if (collisionCheckWithFirildaks() != null) {
							Firildak hitFirildak = collisionCheckWithFirildaks();

							LineSegment edge1 = new LineSegment((getL() * hitFirildak.getInitTopLeft().x()),
									(getL() * hitFirildak.getInitTopLeft().y()),
									(getL() * hitFirildak.getInitTopRight().x()),
									(getL() * hitFirildak.getInitTopRight().y()));
							LineSegment edge2 = new LineSegment((getL() * hitFirildak.getInitTopRight().x()),
									(getL() * hitFirildak.getInitTopRight().y()),
									(getL() * hitFirildak.getInitBottomRight().x()),
									(getL() * hitFirildak.getInitBottomRight().y()));
							LineSegment edge3 = new LineSegment((getL() * hitFirildak.getInitBottomLeft().x()),
									(getL() * hitFirildak.getInitBottomLeft().y()),
									(getL() * hitFirildak.getInitBottomRight().x()),
									(getL() * hitFirildak.getInitBottomRight().y()));
							LineSegment edge4 = new LineSegment((getL() * hitFirildak.getInitTopLeft().x()),
									(getL() * hitFirildak.getInitTopLeft().y()),
									(getL() * hitFirildak.getInitBottomLeft().x()),
									(getL() * hitFirildak.getInitBottomLeft().y()));

							Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
							Vect nextBallVelocity = new Vect(getVelocityX(), getVelocityY());
							LineSegment hitEdge;
							if (Geometry.timeUntilRotatingWallCollision(edge1,
									new Vect((getL() * (hitFirildak.getCenter().x())),
											(getL() * (hitFirildak.getCenter().y()))),
									hitFirildak.getAngularVelocity(),
									new Circle(new Vect(getX(), getY()), getWidth() / 2), preBallVelocity) <= 0.2) {
								hitEdge = edge1;
							} else {
								if (Geometry.timeUntilRotatingWallCollision(edge2,
										new Vect((getL() * (hitFirildak.getCenter().x())),
												(getL() * (hitFirildak.getCenter().y()))),
										hitFirildak.getAngularVelocity(),
										new Circle(new Vect(getX(), getY()), getWidth() / 2), preBallVelocity) <= 0.2) {
									hitEdge = edge2;
								} else {
									if (Geometry.timeUntilRotatingWallCollision(edge3,
											new Vect((getL() * (hitFirildak.getCenter().x())),
													(getL() * (hitFirildak.getCenter().y()))),
											hitFirildak.getAngularVelocity(),
											new Circle(new Vect(getX(), getY()), getWidth() / 2),
											preBallVelocity) <= 0.2) {
										hitEdge = edge3;
									} else {
										hitEdge = edge4;
									}
								}
							}
							nextBallVelocity = Geometry.reflectRotatingWall(hitEdge,
									new Vect((getL() * (hitFirildak.getCenter().x())),
											(getL() * (hitFirildak.getCenter().y()))),
									hitFirildak.getAngularVelocity(),
									new Circle(new Vect(getX(), getY()), getWidth() / 2), preBallVelocity);

							setVelocityX(nextBallVelocity.x());
							setVelocityY(nextBallVelocity.y());

							updateObjectHistory(hitFirildak);
						} else {
							if (collisionCheckWithTriangleTakoz() != null) {
								TriangleTakoz hitTakoz = collisionCheckWithTriangleTakoz();

								LineSegment edge1;
								LineSegment edge2;
								LineSegment hypotenuse;
								Vect corner1 = new Vect((getL() * hitTakoz.getX()), (getL() * hitTakoz.getY()));
								Vect corner2;
								Vect corner3;
								if (hitTakoz.getDirection() == DirectionEnum.SOUTH_WEST) {
									edge1 = new LineSegment((getL() * hitTakoz.getX()),
											(getL() * (hitTakoz.getY() - 1)), (getL() * hitTakoz.getX()),
											(getL() * hitTakoz.getY()));
									edge2 = new LineSegment((getL() * hitTakoz.getX()), (getL() * hitTakoz.getY()),
											(getL() * (hitTakoz.getX() + 1)), (getL() * hitTakoz.getY()));
									hypotenuse = new LineSegment((getL() * hitTakoz.getX()),
											(getL() * (hitTakoz.getY() - 1)), (getL() * (hitTakoz.getX() + 1)),
											(getL() * hitTakoz.getY()));
									corner2 = new Vect((getL() * hitTakoz.getX()), (getL() * (hitTakoz.getY() - 1)));
									corner3 = new Vect((getL() * (hitTakoz.getX() + 1)), (getL() * hitTakoz.getY()));
								} else {
									if (hitTakoz.getDirection() == DirectionEnum.SOUTH_EAST) {
										edge1 = new LineSegment((getL() * hitTakoz.getX()),
												(getL() * (hitTakoz.getY() - 1)), (getL() * hitTakoz.getX()),
												(getL() * hitTakoz.getY()));
										edge2 = new LineSegment((getL() * (hitTakoz.getX() - 1)),
												(getL() * hitTakoz.getY()), (getL() * hitTakoz.getX()),
												(getL() * hitTakoz.getY()));
										hypotenuse = new LineSegment((getL() * hitTakoz.getX()),
												(getL() * (hitTakoz.getY() - 1)), (getL() * (hitTakoz.getX() - 1)),
												(getL() * hitTakoz.getY()));
										corner2 = new Vect((getL() * hitTakoz.getX()),
												(getL() * (hitTakoz.getY() - 1)));
										corner3 = new Vect((getL() * (hitTakoz.getX() - 1)),
												(getL() * hitTakoz.getY()));
									} else {
										if (hitTakoz.getDirection() == DirectionEnum.NORTH_EAST) {
											edge1 = new LineSegment((getL() * (hitTakoz.getX() - 1)),
													(getL() * hitTakoz.getY()), (getL() * hitTakoz.getX()),
													(getL() * hitTakoz.getY()));
											edge2 = new LineSegment((getL() * hitTakoz.getX()),
													(getL() * hitTakoz.getY()), (getL() * hitTakoz.getX()),
													(getL() * (hitTakoz.getY() + 1)));
											hypotenuse = new LineSegment((getL() * (hitTakoz.getX() - 1)),
													(getL() * hitTakoz.getY()), (getL() * hitTakoz.getX()),
													(getL() * (hitTakoz.getY() + 1)));
											corner2 = new Vect((getL() * (hitTakoz.getX() - 1)),
													(getL() * hitTakoz.getY()));
											corner3 = new Vect((getL() * hitTakoz.getX()),
													(getL() * (hitTakoz.getY() + 1)));
										} else {
											edge1 = new LineSegment((getL() * hitTakoz.getX()),
													(getL() * hitTakoz.getY()), (getL() * (hitTakoz.getX() + 1)),
													(getL() * hitTakoz.getY()));
											edge2 = new LineSegment((getL() * hitTakoz.getX()),
													(getL() * hitTakoz.getY()), (getL() * hitTakoz.getX()),
													(getL() * (hitTakoz.getY() + 1)));
											hypotenuse = new LineSegment((getL() * (hitTakoz.getX() + 1)),
													(getL() * hitTakoz.getY()), (getL() * hitTakoz.getX()),
													(getL() * (hitTakoz.getY() + 1)));
											corner2 = new Vect((getL() * (hitTakoz.getX() + 1)),
													(getL() * hitTakoz.getY()));
											corner3 = new Vect((getL() * hitTakoz.getX()),
													(getL() * (hitTakoz.getY() + 1)));
										}
									}
								}

								Vect perPointToEdge1 = Geometry.perpendicularPoint(edge1, new Vect(getX(), getY()));
								double edge1Length = -1;
								if (perPointToEdge1 != null) {
									edge1Length = Math.sqrt(Math.pow(Math.abs(getX() - perPointToEdge1.x()), 2)
											+ Math.pow(Math.abs(getY() - perPointToEdge1.y()), 2));
								}

								Vect perPointToEdge2 = Geometry.perpendicularPoint(edge2, new Vect(getX(), getY()));
								double edge2Length = -1;
								if (perPointToEdge2 != null) {
									edge2Length = Math.sqrt(Math.pow(Math.abs(getX() - perPointToEdge2.x()), 2)
											+ Math.pow(Math.abs(getY() - perPointToEdge2.y()), 2));
								}

								Vect perPointToHypotenuse = Geometry.perpendicularPoint(hypotenuse,
										new Vect(getX(), getY()));
								double hypotenuseLength = -1;
								if (perPointToHypotenuse != null) {
									hypotenuseLength = Math
											.sqrt(Math.pow(Math.abs(getX() - perPointToHypotenuse.x()), 2)
													+ Math.pow(Math.abs(getY() - perPointToHypotenuse.y()), 2));
								}

								double corner1Length = Math.sqrt(Math.pow(Math.abs(getX() - corner1.x()), 2)
										+ Math.pow(Math.abs(getY() - corner1.y()), 2));

								double corner2Length = Math.sqrt(Math.pow(Math.abs(getX() - corner2.x()), 2)
										+ Math.pow(Math.abs(getY() - corner2.y()), 2));

								double corner3Length = Math.sqrt(Math.pow(Math.abs(getX() - corner3.x()), 2)
										+ Math.pow(Math.abs(getY() - corner3.y()), 2));

								Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
								Vect nextBallVelocity = new Vect(getVelocityX(), getVelocityY());
								if (edge1Length != -1 && edge1Length <= getWidth() / 2) {
									nextBallVelocity = Geometry.reflectWall(edge1, preBallVelocity);

									updateObjectHistory(hitTakoz);
								} else {
									if (edge2Length != -1 && edge2Length <= getWidth() / 2) {
										nextBallVelocity = Geometry.reflectWall(edge2, preBallVelocity);

										updateObjectHistory(hitTakoz);
									} else {
										if (hypotenuseLength != -1 && hypotenuseLength <= getWidth() / 2) {
											nextBallVelocity = Geometry.reflectWall(hypotenuse, preBallVelocity);

											updateObjectHistory(hitTakoz);
										} else {
											if (corner1Length <= getWidth() / 2) {
												nextBallVelocity = Geometry.reflectCircle(corner1,
														new Vect(getX(), getY()), preBallVelocity, 1);

												updateObjectHistory(hitTakoz);
											} else {
												if (corner2Length <= getWidth() / 2) {
													nextBallVelocity = Geometry.reflectCircle(corner2,
															new Vect(getX(), getY()), preBallVelocity, 1);

													updateObjectHistory(hitTakoz);
												} else {
													if (corner3Length <= getWidth() / 2) {
														nextBallVelocity = Geometry.reflectCircle(corner3,
																new Vect(getX(), getY()), preBallVelocity, 1);

														updateObjectHistory(hitTakoz);
													}
												}
											}
										}
									}
								}

								setVelocityX(nextBallVelocity.x());
								setVelocityY(nextBallVelocity.y());
							} else {
								if (collisionCheckWithSquareTakoz() != null) {
									SquareTakoz hitTakoz = collisionCheckWithSquareTakoz();

									Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
									Vect nextBallVelocity = new Vect(getVelocityX(), getVelocityY());
									if (getX() + getWidth() / 2 <= (getL() * hitTakoz.getX())
											&& getY() >= (getL() * hitTakoz.getY())
											&& getY() <= (getL() * (hitTakoz.getY() + hitTakoz.getHeight()))) {
										LineSegment leftEdge = new LineSegment(
												new Vect((getL() * hitTakoz.getX()), (getL() * hitTakoz.getY())),
												new Vect((getL() * hitTakoz.getX()),
														(getL() * (hitTakoz.getY() + hitTakoz.getHeight()))));

										nextBallVelocity = Geometry.reflectWall(leftEdge, preBallVelocity, 1);

										updateObjectHistory(hitTakoz);
									} else {
										if (getX() >= (getL() * hitTakoz.getX())
												&& getX() <= (getL() * (hitTakoz.getX() + hitTakoz.getWidth()))
												&& getY() + getHeight() / 2 <= (getL() * hitTakoz.getY())) {
											LineSegment topEdge = new LineSegment(
													new Vect((getL() * hitTakoz.getX()), (getL() * hitTakoz.getY())),
													new Vect((getL() * (hitTakoz.getX() + hitTakoz.getWidth())),
															(getL() * hitTakoz.getY())));

											nextBallVelocity = Geometry.reflectWall(topEdge, preBallVelocity, 1);

											updateObjectHistory(hitTakoz);
										} else {
											if ((getX() - getWidth() / 2) >= (getL()
													* (hitTakoz.getX() + hitTakoz.getWidth()))
													&& getY() >= (getL() * hitTakoz.getY())
													&& getY() <= (getL() * (hitTakoz.getY() + hitTakoz.getHeight()))) {
												LineSegment rightEdge = new LineSegment(
														new Vect((getL() * (hitTakoz.getX() + hitTakoz.getWidth())),
																(getL() * hitTakoz.getY())),
														new Vect((getL() * (hitTakoz.getX() + hitTakoz.getWidth())),
																(getL() * (hitTakoz.getY() + hitTakoz.getHeight()))));

												nextBallVelocity = Geometry.reflectWall(rightEdge, preBallVelocity, 1);

												updateObjectHistory(hitTakoz);
											} else {
												if (getX() >= (getL() * hitTakoz.getX())
														&& getX() <= (getL() * (hitTakoz.getX() + hitTakoz.getWidth()))
														&& getY() - getHeight() / 2 >= (getL()
																* (hitTakoz.getY() + hitTakoz.getHeight()))) {
													LineSegment bottomEdge = new LineSegment(
															new Vect((getL() * hitTakoz.getX()), (getL()
																	* (hitTakoz.getY() + hitTakoz.getHeight()))),
															new Vect((getL() * (hitTakoz.getX() + hitTakoz.getWidth())),
																	(getL() * (hitTakoz.getY()
																			+ hitTakoz.getHeight()))));

													nextBallVelocity = Geometry.reflectWall(bottomEdge, preBallVelocity,
															1);

													updateObjectHistory(hitTakoz);
												} else {
													if (getX() <= (getL() * hitTakoz.getX())
															&& getY() <= (getL() * hitTakoz.getY())) {
														Vect ballCenter = new Vect(getX(), getY());
														Vect topLeftCornerCircleCenter = new Vect(
																(getL() * hitTakoz.getX()) + 1,
																(getL() * hitTakoz.getY()) + 1);

														nextBallVelocity = Geometry.reflectCircle(
																topLeftCornerCircleCenter, ballCenter, preBallVelocity);

														updateObjectHistory(hitTakoz);
													} else {
														if (getX() >= (getL() * (hitTakoz.getX() + hitTakoz.getWidth()))
																&& getY() <= (getL() * hitTakoz.getY())) {
															Vect ballCenter = new Vect(getX(), getY());
															Vect topRightCornerCircleCenter = new Vect(
																	(getL() * (hitTakoz.getX() + hitTakoz.getWidth()))
																			- 1,
																	(getL() * hitTakoz.getY()) + 1);

															nextBallVelocity = Geometry.reflectCircle(
																	topRightCornerCircleCenter, ballCenter,
																	preBallVelocity);

															updateObjectHistory(hitTakoz);
														} else {
															if (getX() <= (getL() * hitTakoz.getX())
																	&& getY() >= (getL() * (hitTakoz.getY()
																			+ hitTakoz.getHeight()))) {
																Vect ballCenter = new Vect(getX(), getY());
																Vect bottomLeftCornerCircleCenter = new Vect(
																		(getL() * hitTakoz.getX()) + 1,
																		(getL() * (hitTakoz.getY()
																				+ hitTakoz.getHeight())) - 1);

																nextBallVelocity = Geometry.reflectCircle(
																		bottomLeftCornerCircleCenter, ballCenter,
																		preBallVelocity);

																updateObjectHistory(hitTakoz);
															} else {
																if (getX() >= (getL()
																		* (hitTakoz.getX() + hitTakoz.getWidth()))
																		&& getY() >= (getL() * (hitTakoz.getY()
																				+ hitTakoz.getHeight()))) {
																	Vect ballCenter = new Vect(getX(), getY());
																	Vect bottomRightCornerCircleCenter = new Vect(
																			(getL() * (hitTakoz.getX()
																					+ hitTakoz.getWidth())) - 1,
																			(getL() * (hitTakoz.getY()
																					+ hitTakoz.getHeight())) - 1);

																	nextBallVelocity = Geometry.reflectCircle(
																			bottomRightCornerCircleCenter, ballCenter,
																			preBallVelocity);

																	updateObjectHistory(hitTakoz);
																}
															}
														}
													}
												}
											}
										}
									}
									setVelocityX(nextBallVelocity.x());
									setVelocityY(nextBallVelocity.y());
								} else {
									if (collisionCheckWithLeftTokat() != null) {
										LeftTokat hitTokat = collisionCheckWithLeftTokat();

										LineSegment fakeTopLine = new LineSegment(
												getL() * hitTokat.getTopLine().p1().x(),
												getL() * hitTokat.getTopLine().p1().y(),
												getL() * hitTokat.getTopLine().p2().x(),
												getL() * hitTokat.getTopLine().p2().y());
										LineSegment fakeRightLine = new LineSegment(
												getL() * hitTokat.getRightLine().p1().x(),
												getL() * hitTokat.getRightLine().p1().y(),
												getL() * hitTokat.getRightLine().p2().x(),
												getL() * hitTokat.getRightLine().p2().y());
										LineSegment fakeBottomLine = new LineSegment(
												getL() * hitTokat.getBottomLine().p1().x(),
												getL() * hitTokat.getBottomLine().p1().y(),
												getL() * hitTokat.getBottomLine().p2().x(),
												getL() * hitTokat.getBottomLine().p2().y());
										LineSegment fakeLeftLine = new LineSegment(
												getL() * hitTokat.getLeftLine().p1().x(),
												getL() * hitTokat.getLeftLine().p1().y(),
												getL() * hitTokat.getLeftLine().p2().x(),
												getL() * hitTokat.getLeftLine().p2().y());

										Vect perPointToTopLine = Geometry.perpendicularPoint(fakeTopLine,
												new Vect(getX(), getY()));
										double topLineLength = -1;
										if (perPointToTopLine != null) {
											topLineLength = Math
													.sqrt(Math.pow(Math.abs(getX() - perPointToTopLine.x()), 2)
															+ Math.pow(Math.abs(getY() - perPointToTopLine.y()), 2));
										}

										Vect perPointToRightLine = Geometry.perpendicularPoint(fakeRightLine,
												new Vect(getX(), getY()));
										double rightLineLength = -1;
										if (perPointToRightLine != null) {
											rightLineLength = Math
													.sqrt(Math.pow(Math.abs(getX() - perPointToRightLine.x()), 2)
															+ Math.pow(Math.abs(getY() - perPointToRightLine.y()), 2));
										}

										Vect perPointToBottomLine = Geometry.perpendicularPoint(fakeBottomLine,
												new Vect(getX(), getY()));
										double bottomLineLength = -1;
										if (perPointToBottomLine != null) {
											bottomLineLength = Math
													.sqrt(Math.pow(Math.abs(getX() - perPointToBottomLine.x()), 2)
															+ Math.pow(Math.abs(getY() - perPointToBottomLine.y()), 2));
										}

										Vect perPointToLeftLine = Geometry.perpendicularPoint(fakeLeftLine,
												new Vect(getX(), getY()));
										double leftLineLength = -1;
										if (perPointToLeftLine != null) {
											leftLineLength = Math
													.sqrt(Math.pow(Math.abs(getX() - perPointToLeftLine.x()), 2)
															+ Math.pow(Math.abs(getY() - perPointToLeftLine.y()), 2));
										}

										Vect corner1 = fakeTopLine.p1();
										double corner1Length = Math.sqrt(Math.pow(Math.abs(getX() - corner1.x()), 2)
												+ Math.pow(Math.abs(getY() - corner1.y()), 2));

										Vect corner2 = fakeTopLine.p2();
										;
										double corner2Length = Math.sqrt(Math.pow(Math.abs(getX() - corner2.x()), 2)
												+ Math.pow(Math.abs(getY() - corner2.y()), 2));

										Vect corner3 = fakeBottomLine.p1();
										;
										double corner3Length = Math.sqrt(Math.pow(Math.abs(getX() - corner3.x()), 2)
												+ Math.pow(Math.abs(getY() - corner3.y()), 2));

										Vect corner4 = fakeBottomLine.p2();
										;
										double corner4Length = Math.sqrt(Math.pow(Math.abs(getX() - corner4.x()), 2)
												+ Math.pow(Math.abs(getY() - corner4.y()), 2));

										Vect center;
										if (hitTokat.getDirection() == DirectionEnum.SOUTH) {
											center = new Vect(getL() * (hitTokat.getX() + hitTokat.getWidth() / 2),
													getL() * (hitTokat.getY() + hitTokat.getWidth() / 2));
										} else {
											if (hitTokat.getDirection() == DirectionEnum.EAST) {
												center = new Vect(getL() * (hitTokat.getX() + hitTokat.getWidth() / 2),
														getL() * (hitTokat.getY() - hitTokat.getWidth() / 2));
											} else {
												if (hitTokat.getDirection() == DirectionEnum.NORTH) {
													center = new Vect(
															getL() * (hitTokat.getX() - hitTokat.getWidth() / 2),
															getL() * (hitTokat.getY() - hitTokat.getWidth() / 2));
												} else {
													center = new Vect(
															getL() * (hitTokat.getX() - hitTokat.getWidth() / 2),
															getL() * (hitTokat.getY() + hitTokat.getWidth() / 2));
												}
											}
										}

										Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
										Vect nextBallVelocity = new Vect(getVelocityX(), getVelocityY());
										if (topLineLength != -1 && topLineLength <= getWidth() / 2) {
											if (hitTokat.isRotatingNow()) {
												nextBallVelocity = Geometry.reflectRotatingWall(fakeTopLine, center,
														hitTokat.getAngularVelocity() / Constants.frameRate,
														new Circle(new Vect(getX(), getY()), getWidth() / 2),
														preBallVelocity);
											} else {
												nextBallVelocity = Geometry.reflectWall(fakeTopLine, preBallVelocity,
														1);
											}

											updateObjectHistory(hitTokat);
										} else {
											if (rightLineLength != -1 && rightLineLength <= getWidth() / 2) {
												if (hitTokat.isRotatingNow()) {
													nextBallVelocity = Geometry.reflectRotatingWall(fakeRightLine,
															center, hitTokat.getAngularVelocity() / Constants.frameRate,
															new Circle(new Vect(getX(), getY()), getWidth() / 2),
															preBallVelocity);
												} else {
													nextBallVelocity = Geometry.reflectWall(fakeRightLine,
															preBallVelocity, 1);
												}

												updateObjectHistory(hitTokat);
											} else {
												if (bottomLineLength != -1 && bottomLineLength <= getWidth() / 2) {
													if (hitTokat.isRotatingNow()) {
														nextBallVelocity = Geometry.reflectRotatingWall(fakeBottomLine,
																center,
																hitTokat.getAngularVelocity() / Constants.frameRate,
																new Circle(new Vect(getX(), getY()), getWidth() / 2),
																preBallVelocity);
													} else {
														nextBallVelocity = Geometry.reflectWall(fakeBottomLine,
																preBallVelocity, 1);
													}

													updateObjectHistory(hitTokat);
												} else {
													if (leftLineLength != -1 && leftLineLength <= getWidth() / 2) {
														if (hitTokat.isRotatingNow()) {
															nextBallVelocity = Geometry.reflectRotatingWall(
																	fakeLeftLine, center,
																	hitTokat.getAngularVelocity() / Constants.frameRate,
																	new Circle(new Vect(getX(), getY()),
																			getWidth() / 2),
																	preBallVelocity);
														} else {
															nextBallVelocity = Geometry.reflectWall(fakeLeftLine,
																	preBallVelocity, 1);
														}

														updateObjectHistory(hitTokat);
													} else {
														if (corner1Length <= getWidth() / 2) {
															if (hitTokat.isRotatingNow()) {
																nextBallVelocity = Geometry.reflectRotatingCircle(
																		new Circle(corner1, 1), center,
																		hitTokat.getAngularVelocity()
																				/ Constants.frameRate,
																		new Circle(new Vect(getX(), getY()),
																				getWidth() / 2),
																		preBallVelocity);
															} else {
																nextBallVelocity = Geometry.reflectCircle(corner1,
																		(new Vect(getX(), getY())), preBallVelocity);
															}

															updateObjectHistory(hitTokat);
														} else {
															if (corner2Length <= getWidth() / 2) {
																if (hitTokat.isRotatingNow()) {
																	nextBallVelocity = Geometry.reflectRotatingCircle(
																			new Circle(corner2, 1), center,
																			hitTokat.getAngularVelocity()
																					/ Constants.frameRate,
																			new Circle(new Vect(getX(), getY()),
																					getWidth() / 2),
																			preBallVelocity);
																} else {
																	nextBallVelocity = Geometry.reflectCircle(corner2,
																			(new Vect(getX(), getY())),
																			preBallVelocity);
																}

																updateObjectHistory(hitTokat);
															} else {
																if (corner3Length <= getWidth() / 2) {
																	if (hitTokat.isRotatingNow()) {
																		nextBallVelocity = Geometry
																				.reflectRotatingCircle(
																						new Circle(corner3, 1), center,
																						hitTokat.getAngularVelocity()
																								/ Constants.frameRate,
																						new Circle(
																								new Vect(getX(),
																										getY()),
																								getWidth() / 2),
																						preBallVelocity);
																	} else {
																		nextBallVelocity = Geometry.reflectCircle(
																				corner3, (new Vect(getX(), getY())),
																				preBallVelocity);
																	}

																	updateObjectHistory(hitTokat);
																} else {
																	if (corner4Length <= getWidth() / 2) {
																		if (hitTokat.isRotatingNow()) {
																			nextBallVelocity = Geometry
																					.reflectRotatingCircle(
																							new Circle(corner4, 1),
																							center,
																							hitTokat.getAngularVelocity()
																									/ Constants.frameRate,
																							new Circle(
																									new Vect(getX(),
																											getY()),
																									getWidth() / 2),
																							preBallVelocity);
																		} else {
																			nextBallVelocity = Geometry.reflectCircle(
																					corner4, (new Vect(getX(), getY())),
																					preBallVelocity);
																		}

																		updateObjectHistory(hitTokat);
																	}
																}
															}
														}
													}
												}
											}
										}

										setVelocityX(nextBallVelocity.x());
										setVelocityY(nextBallVelocity.y());
									} else {
										if (collisionCheckWithRightTokat() != null) {
											RightTokat hitTokat = collisionCheckWithRightTokat();

											LineSegment fakeTopLine = new LineSegment(
													getL() * hitTokat.getTopLine().p1().x(),
													getL() * hitTokat.getTopLine().p1().y(),
													getL() * hitTokat.getTopLine().p2().x(),
													getL() * hitTokat.getTopLine().p2().y());
											LineSegment fakeRightLine = new LineSegment(
													getL() * hitTokat.getRightLine().p1().x(),
													getL() * hitTokat.getRightLine().p1().y(),
													getL() * hitTokat.getRightLine().p2().x(),
													getL() * hitTokat.getRightLine().p2().y());
											LineSegment fakeBottomLine = new LineSegment(
													getL() * hitTokat.getBottomLine().p1().x(),
													getL() * hitTokat.getBottomLine().p1().y(),
													getL() * hitTokat.getBottomLine().p2().x(),
													getL() * hitTokat.getBottomLine().p2().y());
											LineSegment fakeLeftLine = new LineSegment(
													getL() * hitTokat.getLeftLine().p1().x(),
													getL() * hitTokat.getLeftLine().p1().y(),
													getL() * hitTokat.getLeftLine().p2().x(),
													getL() * hitTokat.getLeftLine().p2().y());

											Vect perPointToTopLine = Geometry.perpendicularPoint(fakeTopLine,
													new Vect(getX(), getY()));
											double topLineLength = -1;
											if (perPointToTopLine != null) {
												topLineLength = Math.sqrt(
														Math.pow(Math.abs(getX() - perPointToTopLine.x()), 2) + Math
																.pow(Math.abs(getY() - perPointToTopLine.y()), 2));
											}

											Vect perPointToRightLine = Geometry.perpendicularPoint(fakeRightLine,
													new Vect(getX(), getY()));
											double rightLineLength = -1;
											if (perPointToRightLine != null) {
												rightLineLength = Math.sqrt(
														Math.pow(Math.abs(getX() - perPointToRightLine.x()), 2) + Math
																.pow(Math.abs(getY() - perPointToRightLine.y()), 2));
											}

											Vect perPointToBottomLine = Geometry.perpendicularPoint(fakeBottomLine,
													new Vect(getX(), getY()));
											double bottomLineLength = -1;
											if (perPointToBottomLine != null) {
												bottomLineLength = Math.sqrt(
														Math.pow(Math.abs(getX() - perPointToBottomLine.x()), 2) + Math
																.pow(Math.abs(getY() - perPointToBottomLine.y()), 2));
											}

											Vect perPointToLeftLine = Geometry.perpendicularPoint(fakeLeftLine,
													new Vect(getX(), getY()));
											double leftLineLength = -1;
											if (perPointToLeftLine != null) {
												leftLineLength = Math.sqrt(
														Math.pow(Math.abs(getX() - perPointToLeftLine.x()), 2) + Math
																.pow(Math.abs(getY() - perPointToLeftLine.y()), 2));
											}

											Vect corner1 = fakeTopLine.p1();
											double corner1Length = Math.sqrt(Math.pow(Math.abs(getX() - corner1.x()), 2)
													+ Math.pow(Math.abs(getY() - corner1.y()), 2));

											Vect corner2 = fakeTopLine.p2();
											double corner2Length = Math.sqrt(Math.pow(Math.abs(getX() - corner2.x()), 2)
													+ Math.pow(Math.abs(getY() - corner2.y()), 2));

											Vect corner3 = fakeBottomLine.p1();
											double corner3Length = Math.sqrt(Math.pow(Math.abs(getX() - corner3.x()), 2)
													+ Math.pow(Math.abs(getY() - corner3.y()), 2));

											Vect corner4 = fakeBottomLine.p2();
											double corner4Length = Math.sqrt(Math.pow(Math.abs(getX() - corner4.x()), 2)
													+ Math.pow(Math.abs(getY() - corner4.y()), 2));

											Vect center;
											if (hitTokat.getDirection() == DirectionEnum.SOUTH) {
												center = new Vect(getL() * (hitTokat.getX() - hitTokat.getWidth() / 2),
														getL() * (hitTokat.getY() + hitTokat.getWidth() / 2));
											} else {
												if (hitTokat.getDirection() == DirectionEnum.EAST) {
													center = new Vect(
															getL() * (hitTokat.getX() + hitTokat.getWidth() / 2),
															getL() * (hitTokat.getY() + hitTokat.getWidth() / 2));
												} else {
													if (hitTokat.getDirection() == DirectionEnum.NORTH) {
														center = new Vect(
																getL() * (hitTokat.getX() + hitTokat.getWidth() / 2),
																getL() * (hitTokat.getY() - hitTokat.getWidth() / 2));
													} else {
														center = new Vect(
																getL() * (hitTokat.getX() - hitTokat.getWidth() / 2),
																getL() * (hitTokat.getY() - hitTokat.getWidth() / 2));
													}
												}
											}

											Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
											Vect nextBallVelocity = new Vect(getVelocityX(), getVelocityY());
											if (topLineLength != -1 && topLineLength <= getWidth() / 2) {
												if (hitTokat.isRotatingNow()) {
													nextBallVelocity = Geometry.reflectRotatingWall(fakeTopLine, center,
															hitTokat.getAngularVelocity() / Constants.frameRate,
															new Circle(new Vect(getX(), getY()), getWidth() / 2),
															preBallVelocity);
												} else {
													nextBallVelocity = Geometry.reflectWall(fakeTopLine,
															preBallVelocity, 1);
												}

												updateObjectHistory(hitTokat);
											} else {
												if (rightLineLength != -1 && rightLineLength <= getWidth() / 2) {
													if (hitTokat.isRotatingNow()) {
														nextBallVelocity = Geometry.reflectRotatingWall(fakeRightLine,
																center,
																hitTokat.getAngularVelocity() / Constants.frameRate,
																new Circle(new Vect(getX(), getY()), getWidth() / 2),
																preBallVelocity);
													} else {
														nextBallVelocity = Geometry.reflectWall(fakeRightLine,
																preBallVelocity, 1);
													}

													updateObjectHistory(hitTokat);
												} else {
													if (bottomLineLength != -1 && bottomLineLength <= getWidth() / 2) {
														if (hitTokat.isRotatingNow()) {
															nextBallVelocity = Geometry.reflectRotatingWall(
																	fakeBottomLine, center,
																	hitTokat.getAngularVelocity() / Constants.frameRate,
																	new Circle(new Vect(getX(), getY()),
																			getWidth() / 2),
																	preBallVelocity);
														} else {
															nextBallVelocity = Geometry.reflectWall(fakeBottomLine,
																	preBallVelocity, 1);
														}

														updateObjectHistory(hitTokat);
													} else {
														if (leftLineLength != -1 && leftLineLength <= getWidth() / 2) {
															if (hitTokat.isRotatingNow()) {
																nextBallVelocity = Geometry.reflectRotatingWall(
																		fakeLeftLine, center,
																		hitTokat.getAngularVelocity()
																				/ Constants.frameRate,
																		new Circle(new Vect(getX(), getY()),
																				getWidth() / 2),
																		preBallVelocity);
															} else {
																nextBallVelocity = Geometry.reflectWall(fakeLeftLine,
																		preBallVelocity, 1);
															}

															updateObjectHistory(hitTokat);
														} else {
															if (corner1Length <= getWidth() / 2) {
																if (hitTokat.isRotatingNow()) {
																	nextBallVelocity = Geometry.reflectRotatingCircle(
																			new Circle(corner1, 1), center,
																			hitTokat.getAngularVelocity()
																					/ Constants.frameRate,
																			new Circle(new Vect(getX(), getY()),
																					getWidth() / 2),
																			preBallVelocity);
																} else {
																	nextBallVelocity = Geometry.reflectCircle(corner1,
																			(new Vect(getX(), getY())),
																			preBallVelocity);
																}

																updateObjectHistory(hitTokat);
															} else {
																if (corner2Length <= getWidth() / 2) {
																	if (hitTokat.isRotatingNow()) {
																		nextBallVelocity = Geometry
																				.reflectRotatingCircle(
																						new Circle(corner2, 1), center,
																						hitTokat.getAngularVelocity()
																								/ Constants.frameRate,
																						new Circle(
																								new Vect(getX(),
																										getY()),
																								getWidth() / 2),
																						preBallVelocity);
																	} else {
																		nextBallVelocity = Geometry.reflectCircle(
																				corner2, (new Vect(getX(), getY())),
																				preBallVelocity);
																	}

																	updateObjectHistory(hitTokat);
																} else {
																	if (corner3Length <= getWidth() / 2) {
																		if (hitTokat.isRotatingNow()) {
																			nextBallVelocity = Geometry
																					.reflectRotatingCircle(
																							new Circle(corner3, 1),
																							center,
																							hitTokat.getAngularVelocity()
																									/ Constants.frameRate,
																							new Circle(
																									new Vect(getX(),
																											getY()),
																									getWidth() / 2),
																							preBallVelocity);
																		} else {
																			nextBallVelocity = Geometry.reflectCircle(
																					corner3, (new Vect(getX(), getY())),
																					preBallVelocity);
																		}

																		updateObjectHistory(hitTokat);
																	} else {
																		if (corner4Length <= getWidth() / 2) {
																			if (hitTokat.isRotatingNow()) {
																				nextBallVelocity = Geometry
																						.reflectRotatingCircle(
																								new Circle(corner4, 1),
																								center,
																								hitTokat.getAngularVelocity()
																										/ Constants.frameRate,
																								new Circle(
																										new Vect(getX(),
																												getY()),
																										getWidth() / 2),
																								preBallVelocity);
																			} else {
																				nextBallVelocity = Geometry
																						.reflectCircle(corner4,
																								(new Vect(getX(),
																										getY())),
																								preBallVelocity);
																			}

																			updateObjectHistory(hitTokat);
																		}
																	}
																}
															}
														}
													}
												}
											}

											setVelocityX(nextBallVelocity.x());
											setVelocityY(nextBallVelocity.y());
										} else {
											if (collisionCheckWithCezerye() != null) {
												Cezerye hitCezerye = collisionCheckWithCezerye();

												Vect preBallVelocity = new Vect(getVelocityX(), getVelocityY());
												Vect nextBallVelocity = new Vect(getVelocityX(), getVelocityY());
												if (getX() + getWidth() / 2 <= (getL() * hitCezerye.getX())
														&& getY() >= (getL() * hitCezerye.getY()) && getY() <= (getL()
																* (hitCezerye.getY() + hitCezerye.getHeight()))) {
													LineSegment leftEdge = new LineSegment(
															new Vect((getL() * hitCezerye.getX()),
																	(getL() * hitCezerye.getY())),
															new Vect((getL() * hitCezerye.getX()), (getL()
																	* (hitCezerye.getY() + hitCezerye.getHeight()))));

													nextBallVelocity = Geometry.reflectWall(leftEdge, preBallVelocity,
															1);

													updateObjectHistory(hitCezerye);

													hitCezerye.getEvent()
															.applyEvent(BoardModel.getInstance().getCezmis());
													hitCezerye.delete();
												} else {
													if (getX() >= (getL() * hitCezerye.getX())
															&& getX() <= (getL()
																	* (hitCezerye.getX() + hitCezerye.getWidth()))
															&& getY()
																	+ getHeight() / 2 <= (getL() * hitCezerye.getY())) {
														LineSegment topEdge = new LineSegment(
																new Vect((getL() * hitCezerye.getX()),
																		(getL() * hitCezerye.getY())),
																new Vect((getL()
																		* (hitCezerye.getX() + hitCezerye.getWidth())),
																		(getL() * hitCezerye.getY())));

														nextBallVelocity = Geometry.reflectWall(topEdge,
																preBallVelocity, 1);

														updateObjectHistory(hitCezerye);

														hitCezerye.getEvent()
																.applyEvent(BoardModel.getInstance().getCezmis());
														hitCezerye.delete();
													} else {
														if ((getX() - getWidth() / 2) <= (getL()
																* (hitCezerye.getX() + hitCezerye.getWidth()))
																&& getY() >= (getL() * hitCezerye.getY())
																&& getY() <= (getL() * (hitCezerye.getY()
																		+ hitCezerye.getHeight()))) {
															LineSegment rightEdge = new LineSegment(
																	new Vect(
																			(getL() * (hitCezerye.getX()
																					+ hitCezerye.getWidth())),
																			(getL() * hitCezerye.getY())),
																	new Vect(
																			(getL() * (hitCezerye.getX()
																					+ hitCezerye.getWidth())),
																			(getL() * (hitCezerye.getY()
																					+ hitCezerye.getHeight()))));

															nextBallVelocity = Geometry.reflectWall(rightEdge,
																	preBallVelocity, 1);

															updateObjectHistory(hitCezerye);

															hitCezerye.getEvent()
																	.applyEvent(BoardModel.getInstance().getCezmis());
															hitCezerye.delete();
														} else {
															if (getX() >= (getL() * hitCezerye.getX())
																	&& getX() <= (getL() * (hitCezerye.getX()
																			+ hitCezerye.getWidth()))
																	&& getY() - getHeight() / 2 >= (getL()
																			* (hitCezerye.getY()
																					+ hitCezerye.getHeight()))) {
																LineSegment bottomEdge = new LineSegment(
																		new Vect((getL() * hitCezerye.getX()),
																				(getL() * (hitCezerye.getY()
																						+ hitCezerye.getHeight()))),
																		new Vect(
																				(getL() * (hitCezerye.getX()
																						+ hitCezerye.getWidth())),
																				(getL() * (hitCezerye.getY()
																						+ hitCezerye.getHeight()))));

																nextBallVelocity = Geometry.reflectWall(bottomEdge,
																		preBallVelocity, 1);

																updateObjectHistory(hitCezerye);

																hitCezerye.getEvent().applyEvent(
																		BoardModel.getInstance().getCezmis());
																hitCezerye.delete();
															} else {
																if (getX() <= (getL() * hitCezerye.getX())
																		&& getY() <= (getL() * hitCezerye.getY())) {
																	Vect ballCenter = new Vect(getX(), getY());
																	Vect topLeftCornerCircleCenter = new Vect(
																			(getL() * hitCezerye.getX()) + 1,
																			(getL() * hitCezerye.getY()) + 1);

																	nextBallVelocity = Geometry.reflectCircle(
																			topLeftCornerCircleCenter, ballCenter,
																			preBallVelocity);

																	updateObjectHistory(hitCezerye);

																	hitCezerye.getEvent().applyEvent(
																			BoardModel.getInstance().getCezmis());
																	hitCezerye.delete();
																} else {
																	if (getX() >= (getL() * (hitCezerye.getX()
																			+ hitCezerye.getWidth()))
																			&& getY() <= (getL() * hitCezerye.getY())) {
																		Vect ballCenter = new Vect(getX(), getY());
																		Vect topRightCornerCircleCenter = new Vect(
																				(getL() * (hitCezerye.getX()
																						+ hitCezerye.getWidth())) - 1,
																				(getL() * hitCezerye.getY()) + 1);

																		nextBallVelocity = Geometry.reflectCircle(
																				topRightCornerCircleCenter, ballCenter,
																				preBallVelocity);

																		updateObjectHistory(hitCezerye);

																		hitCezerye.getEvent().applyEvent(
																				BoardModel.getInstance().getCezmis());
																		hitCezerye.delete();
																	} else {
																		if (getX() <= (getL() * hitCezerye.getX())
																				&& getY() >= (getL() * (hitCezerye
																						.getY() + hitCezerye
																								.getHeight()))) {
																			Vect ballCenter = new Vect(getX(), getY());
																			Vect bottomLeftCornerCircleCenter = new Vect(
																					(getL() * hitCezerye.getX()) + 1,
																					(getL() * (hitCezerye.getY()
																							+ hitCezerye.getHeight()))
																							- 1);

																			nextBallVelocity = Geometry.reflectCircle(
																					bottomLeftCornerCircleCenter,
																					ballCenter, preBallVelocity);

																			updateObjectHistory(hitCezerye);

																			hitCezerye.getEvent().applyEvent(BoardModel
																					.getInstance().getCezmis());
																			hitCezerye.delete();
																		} else {
																			if (getX() >= (getL() * (hitCezerye.getX()
																					+ hitCezerye.getWidth()))
																					&& getY() >= (getL() * (hitCezerye
																							.getY()
																							+ hitCezerye
																									.getHeight()))) {
																				Vect ballCenter = new Vect(getX(),
																						getY());
																				Vect bottomRightCornerCircleCenter = new Vect(
																						(getL() * (hitCezerye.getX()
																								+ hitCezerye
																										.getWidth()))
																								- 1,
																						(getL() * (hitCezerye.getY()
																								+ hitCezerye
																										.getHeight()))
																								- 1);

																				nextBallVelocity = Geometry
																						.reflectCircle(
																								bottomRightCornerCircleCenter,
																								ballCenter,
																								preBallVelocity);

																				updateObjectHistory(hitCezerye);
																				hitCezerye.getEvent().applyEvent(
																						BoardModel.getInstance()
																								.getCezmis());
																				hitCezerye.delete();
																			}
																		}
																	}
																}
															}
														}
													}
												}
												setVelocityX(nextBallVelocity.x());
												setVelocityY(nextBallVelocity.y());
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
		setX(getX() + getVelocityX());
		setY(getY() + getVelocityY());

		setVelocityX(newVelocityX(BoardModel.getInstance().getLevel()));
		setVelocityY(newVelocityY(BoardModel.getInstance().getLevel()));
	}

	public void attachObserver(Observer observer) {
		getObservers().add(observer);
	}

	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

}