package controller;

import java.awt.event.MouseEvent;
import enums.ModeEnum;
import enums.StateEnum;
import model.BoardModel;

public class RotateGizmoController extends Controller{

	/**
	 * @requires Game to be in Building Mode
	 * @modifies The state of the model
	 * @modifies The current player
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.BUILDING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.ROTATE_GIZMO);
		}
	}

	/**
	 * @requires Game to be in Rotate Gizmo State
	 * @modifies The list of gizmos in the model
	 */
	public void boardClick(MouseEvent event) {
		int eventX = (int) (event.getX() / BoardModel.getL());
		int eventY = (int) (event.getY() / BoardModel.getL());

		if (BoardModel.getInstance().getState().equals(StateEnum.ROTATE_GIZMO)) {
			if (BoardModel.getInstance().getCurrentPlayer() == BoardModel.getInstance().getPlayers().get(0)) {
				if (eventX >= 0 && eventX <= 11 && eventY >= 0 && eventY <= 18) {
					BoardModel.getInstance().rotateGizmo(event.getX(), event.getY());
				}
			} else {
				if (eventX >= 13 && eventX <= 24 && eventY >= 0 && eventY <= 18) {
					BoardModel.getInstance().rotateGizmo(event.getX(), event.getY());
				}
			}
		}
	}
}
