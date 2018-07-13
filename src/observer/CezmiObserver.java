package observer;

import boardobject.Cezmi;
import boardobject.Observable;
import gui.GameWindow;

public class CezmiObserver extends Observer {

	public CezmiObserver() {

	}

	@Override
	public void update(Observable object) {
		Cezmi cezmi = (Cezmi) object;
		GameWindow.getInstance().getBoardPanel().repaintCezmi(cezmi);
	}

}
