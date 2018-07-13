package observer;

import boardobject.Observable;
import gui.GameWindow;

public class WinPanelObserver extends Observer {

	public WinPanelObserver() {

	}

	@Override
	public void update(Observable object) {
		GameWindow.getInstance().showGameEndFrame();
	}

}
