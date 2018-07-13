package events;

import java.util.ArrayList;
import boardobject.Ball;
import boardobject.Cezmi;
import enums.LevelEnum;
import main.Constants;
import model.BoardModel;

/**
 * 
 * @author UC This class implements Event class This class contains methods such
 *         as enlarging a cezmis area.
 */

public class MagnifyEvent implements Event { // Ideally; magnify without
												// touching X-Coordinate,
												// in a right-to-left manner.
	Cezmi lastHit; // Object Reference
	Cezmi affected; // Object Reference
	Ball ball;

	/**
	 * 
	 * @param cezmis:
	 *            The cezmi objects in our BoardModel
	 * @effects enlarges one of the cezmi's area in our BoardModel according to
	 *          the ball history
	 */
	@Override
	public void applyEvent(ArrayList<Cezmi> cezmis) {

		if (BoardModel.getInstance().getCezerye() == BoardModel.getInstance().getBalls().get(0).getHistory()[2]) {
			// There must be a way to check Class name rather than comparing 2
			// objects.
			// TO-DO
			ball = BoardModel.getInstance().getBalls().get(0);
		} else if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL2) {
			if (BoardModel.getInstance().getCezerye() == BoardModel.getInstance().getBalls().get(1).getHistory()[2])
				ball = BoardModel.getInstance().getBalls().get(1);
		}

		if (ball != null && ball.getHistory()[0] != null) {
			lastHit = (Cezmi) ball.getHistory()[0]; // Guarantee that first
													// index will contain last
													// Cezmi hit.
			if (lastHit == cezmis.get(0)) { // "==" EQUALITY check -> Do they
											// both refer to the same object in
											// memory?
				affected = cezmis.get(0);
			} else {
				affected = cezmis.get(1);
			}
			if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL1) {
				if (affected.getX() + affected.getWidth() > affected.getRightLimit().x()) { // Push
																							// to
																							// left
																							// if
																							// Cezmi
																							// is
																							// near
																							// Right
																							// Limit.
					affected.setX(affected.getRightLimit().x() - affected.getWidth());
				}
				if (affected.getX() - affected.getWidth() < affected.getLeftLimit().x()) { // Push
																							// to
																							// right
																							// if
																							// Cezmi
																							// is
																							// near
																							// Left
																							// Limit.
					affected.setX(affected.getLeftLimit().x() + affected.getWidth());
				}
			} else {
				if (BoardModel.getInstance().getLevel() == LevelEnum.LEVEL2) {
					if (affected == BoardModel.getInstance().getCezmis().get(0)) {
						if (affected.getY() == 25) {
							if (affected.getX() + affected.getWidth() > affected.getRightLimit().x()) { // Push
																										// to
																										// left
																										// if
																										// Cezmi
																										// is
																										// near
																										// Right
																										// Limit.
								affected.setX(affected.getRightLimit().x() - affected.getWidth());
							}
							// Push to right if Cezmi is near Left Limit.
							if (affected.getX() - affected.getWidth() < 0) {
								affected.setX(affected.getWidth());
							}
						} else {
							if (affected.getX() == 0) {
								if (affected.getY() + affected.getWidth() > 25) {
									affected.setY(25 - affected.getWidth());
								}
								if (affected.getY() - affected.getWidth() < affected.getLeftLimit().y()) {
									affected.setY(affected.getWidth());
								}
							}
						}
					} else {
						if (affected == BoardModel.getInstance().getCezmis().get(1)) {
							if (affected.getY() == 25) {
								if (affected.getX() + affected.getWidth() > 25) { // Push
																					// to
																					// left
																					// if
																					// Cezmi
																					// is
																					// near
																					// Right
																					// Limit.
									affected.setX(25 - affected.getWidth());
								}
								if (affected.getX() - affected.getWidth() < affected.getLeftLimit().x()) { // Push
																											// to
																											// right
																											// if
																											// Cezmi
																											// is
																											// near
																											// Left
																											// Limit.
									affected.setX(affected.getLeftLimit().x() + affected.getWidth());
								}
							} else {
								if (affected.getX() == 25) {
									if (affected.getY() + affected.getWidth() > 25) {
										affected.setY(25 - affected.getWidth());
									}
									if (affected.getY() - affected.getWidth() < affected.getRightLimit().y()) {
										affected.setY(affected.getWidth());
									}
								}
							}
						}
					}
				}
			}
			affected.setY(affected.getY() - affected.getHeight() * 0.2);
			affected.setWidth(1.4 * affected.getWidth()); // Magnify
			affected.setHeight(1.4 * affected.getHeight()); // Magnify
			affected.notifyAllObservers(); // Notify and reflect results in GUI.
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					finishEvent();
				}
			}, Constants.finishEventTime);
		}
	}

	/**
	 * 
	 * @effects Minimizes the area of the cezmi affected by the magnify event
	 *          back to it's former area.
	 */
	@Override
	public void finishEvent() {
		affected.setWidth(affected.getWidth() / 1.4); // Shrink
		affected.setHeight(affected.getHeight() / 1.4); // Shrink
		affected.setY(affected.getY() + affected.getHeight() * 0.2);
		affected.notifyAllObservers(); // Notify and reflect results in GUI.
	}

}
