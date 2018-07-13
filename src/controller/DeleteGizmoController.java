package controller;

import java.awt.event.MouseEvent;
import enums.ModeEnum;
import enums.StateEnum;
import model.BoardModel;

public class DeleteGizmoController extends Controller{

	/**
	 * @requires Game to be in Building Mode
	 * @modifies The state of the model
	 * @modifies The current player
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.BUILDING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.DELETE_GIZMO);
		}
	}

	/**
	 * @param event
	 *            The mouse event that triggered this method
	 * @requires Game to be in Add Gizmo State
	 * @modifies The list of gizmos in the model
	 * @modifies The array that represents occupied space
	 */
	public void boardClick(MouseEvent event) {

		if (BoardModel.getInstance().getState().equals(StateEnum.DELETE_GIZMO)) {
			int eventX = (int) (event.getX() / BoardModel.getL());
			int eventY = (int) (event.getY() / BoardModel.getL());
			boolean operationState;
			if (BoardModel.getInstance().getCurrentPlayer() == BoardModel.getInstance().getPlayers().get(0)) {
				if (eventX >= 0 && eventX <= 11 && eventY >= 0 && eventY <= 18) {
					operationState = BoardModel.getInstance().deleteGizmo(event.getX(), event.getY());
					if (operationState) {
						BoardModel.getInstance().getCurrentPlayer()
								.setGizmoNum(BoardModel.getInstance().getCurrentPlayer().getGizmoNum() - 1);
					}
				}
			} else {
				if (eventX >= 13 && eventX <= 24 && eventY >= 0 && eventY <= 18) {
					operationState = BoardModel.getInstance().deleteGizmo(event.getX(), event.getY());
					if (operationState) {
						BoardModel.getInstance().getCurrentPlayer()
								.setGizmoNum(BoardModel.getInstance().getCurrentPlayer().getGizmoNum() - 1);
					}
				}
			}
		}

	}
}
