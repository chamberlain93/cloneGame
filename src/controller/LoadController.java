package controller;

import java.io.File;
import enums.ModeEnum;
import enums.StateEnum;
import fileIO.XMLFileLoader;
import model.BoardModel;

public class LoadController extends Controller{

	/**
	 * @requires Game to be in Building Mode
	 * @modifies The state of the model
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.BUILDING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.LOAD);
		}
	}

	/**
	 * @param file
	 *            The file wanted to be loaded
	 * @requires Game to be in Load State
	 * @modifies The model
	 */
	public String loadBoard(File file) {
		if (BoardModel.getInstance().getState().equals(StateEnum.LOAD)) {
			XMLFileLoader fileLoader = new XMLFileLoader();
			return fileLoader.loadFile(file);
		} else
			return null;
	}
}
