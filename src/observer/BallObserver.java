package observer;

import boardobject.Ball;
import boardobject.Observable;
import gui.GameWindow;
import model.BoardModel;

public class BallObserver extends Observer {

	public BallObserver() {

	}

	@Override
	public void update(Observable object) {
		Ball ball = (Ball) object;
		GameWindow.getInstance().getBoardPanel().repaintBall(ball);

	}

}
