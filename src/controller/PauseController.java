package controller;

import enums.ModeEnum;
import enums.StateEnum;
import gui.GameWindow;
import model.BoardModel;

public class PauseController extends Controller{

	/**
	 * @requires Game to be in Running Mode
	 * @modifies The state of the model
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.RUNNING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.PAUSE);
			GameWindow.getInstance().getBoardPanel()
					.removeKeyListener(GameWindow.getInstance().getBoardPanel().getMultiKeyListener());
			GameWindow.getInstance().getBoardPanel().getTimer().stop();
		}
	}
}
