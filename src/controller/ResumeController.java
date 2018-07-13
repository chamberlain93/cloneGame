package controller;

import enums.ModeEnum;
import enums.StateEnum;
import gui.GameWindow;
import model.BoardModel;

public class ResumeController extends Controller{

	/**
	 * @requires Game to be in Running Mode
	 * @requires Game to be in Pause State
	 * @modifies The state of the model
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.RUNNING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.RESUME);
			GameWindow.getInstance().getBoardPanel()
					.addKeyListener(GameWindow.getInstance().getBoardPanel().getMultiKeyListener());
			GameWindow.getInstance().getBoardPanel().requestFocus();
			GameWindow.getInstance().getBoardPanel().getTimer().start();
		}
	}
}
