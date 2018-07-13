package observer;

import boardobject.Cezerye;
import boardobject.Observable;
import gui.GameWindow;

public class CezeryeObserver extends Observer {

	public CezeryeObserver() {

	}

	@Override
	public void update(Observable object) {
		if (object instanceof Cezerye) {
			Cezerye cezerye = (Cezerye) object;
			GameWindow.getInstance().getBoardPanel().repaintCezerye(cezerye);
		}
	}

}
