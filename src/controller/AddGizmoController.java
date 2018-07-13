package controller;

import java.awt.event.MouseEvent;
import enums.BoardObjectEnum;
import enums.ModeEnum;
import enums.StateEnum;
import model.BoardModel;

public class AddGizmoController extends Controller{

	BoardObjectEnum objectType;

	public BoardObjectEnum getObjectType() {
		return objectType;
	}

	public void setObjectType(BoardObjectEnum objectType) {
		this.objectType = objectType;
	}

	/**
	 * @requires Game to be in Building Mode
	 * @modifies The state of the model
	 * @modifies The current player
	 */
	public void buttonClick(BoardObjectEnum objectType) {
		if (BoardModel.getInstance().getMode().equals(ModeEnum.BUILDING_MODE)) {
			BoardModel.getInstance().setState(StateEnum.ADD_GIZMO);
			setObjectType(objectType);
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
		if (BoardModel.getInstance().getState().equals(StateEnum.ADD_GIZMO)) {
			int eventX = (int) (event.getX() / BoardModel.getL());
			int eventY = (int) (event.getY() / BoardModel.getL());

			if (BoardModel.getInstance().getCurrentPlayer().getGizmoNum() < 4) {
				if (BoardModel.getInstance().getCurrentPlayer() == BoardModel.getInstance().getPlayers().get(0)) {
					if (eventX >= 0 && eventX <= 11 && eventY >= 0 && eventY <= 18) {
						BoardModel.getInstance().addGizmo(eventX, eventY, getObjectType(),
								BoardModel.getInstance().getPlayers().get(0));
					}
				} else {
					if (eventX >= 13 && eventX <= 24 && eventY >= 0 && eventY <= 18) {
						BoardModel.getInstance().addGizmo(eventX, eventY, getObjectType(),
								BoardModel.getInstance().getPlayers().get(1));
					}
				}
			}
		}
	}

	@Override
	public void buttonClick() {
		// TODO Auto-generated method stub
		
	}

}
