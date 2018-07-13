package observer;

import boardobject.Observable;
import gui.GameWindow;

public class PlayerObserver extends Observer {

	public PlayerObserver() {

	}

	@Override
	public void update(Observable object) {
		GameWindow.getInstance().updateScores();
	}

}
