package controller;

import enums.ModeEnum;
import enums.StateEnum;
import fileIO.XMLFileSaver;
import model.BoardModel;

public class SaveController extends Controller{

	/**
	 * @requires Game to be in Building Mode
	 * @modifies The state of the model
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.BUILDING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.SAVE);
		} else if (BoardModel.getInstance().getMode().equals(ModeEnum.RUNNING_MODE)
				&& BoardModel.getInstance().getState() == StateEnum.PAUSE) {
			BoardModel.getInstance().setState(StateEnum.SAVE);

		}
	}

	/**
	 * @param path
	 *            The directory that is desired to be the save location
	 * @requires Game to be in Save State
	 * @modifies The model
	 */
	public void saveBoard(String path) {
		if (BoardModel.getInstance().getState().equals(StateEnum.SAVE)) {
			XMLFileSaver fileSaver = new XMLFileSaver();
			fileSaver.saveFile(path);
		}
	}
}
