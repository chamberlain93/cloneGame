package events;

import java.util.ArrayList;
import boardobject.Ball;
import boardobject.Cezmi;
import enums.LevelEnum;
import main.Constants;
import model.BoardModel;

/**
 * 
 * @author UC This class implements the Event Class This class contains methods
 *         such as nulling a cezmi's velocity and initiating the velocity of
 *         cezmi
 */
public class FreezeEvent implements Event {

	Cezmi lastHit;
	Cezmi affected;
	Ball ball;
	double oldVel; // to keep the velocity before the event

	/**
	 * 
	 * @param cezmis:
	 *            The cezmi objects in our BoardModel
	 * @effects changes one of the cezmi's velocity in our BoardModel to zero
	 *          according to the ball history
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

			lastHit = (Cezmi) ball.getHistory()[0];
			if (lastHit == cezmis.get(0)) {
				affected = cezmis.get(1);
				oldVel = affected.getVelocity(); // setting the oldVel to the
													// velocity before applying
													// the event.
			} else {
				affected = cezmis.get(0);
				oldVel = affected.getVelocity(); // setting the oldVel to the
													// velocity before applying
													// the event.
			}
			affected.setVelocity(0);
			affected.notifyAllObservers(); // notify.
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
	 * @effects changes the velocity of the cezmi affected by the freeze event
	 *          back to it's former velocity.
	 */
	@Override
	public void finishEvent() {
		affected.setVelocity(oldVel); // setting the velocity to oldVel.
		affected.notifyAllObservers(); // notify

	}
}
