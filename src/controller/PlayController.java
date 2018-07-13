package controller;

import boardobject.Gizmo;
import boardobject.LeftTokat;
import boardobject.RightTokat;
import enums.ModeEnum;
import enums.StateEnum;
import model.BoardModel;

public class PlayController extends Controller{

	/**
	 * @requires Game to be in Running Mode
	 * @modifies The state of the model
	 */
	public void buttonClick() {
		if (BoardModel.getInstance().getMode() == ModeEnum.BUILDING_MODE) {
			BoardModel.getInstance().setMode(ModeEnum.RUNNING_MODE);
		}
		if (BoardModel.getInstance().getMode() == ModeEnum.RUNNING_MODE) {
			BoardModel.getInstance().setState(StateEnum.PLAY);
		}
	}

	/**
	 * @param event
	 *            The event that triggered this method
	 * @requires Game to be in Play State
	 * @modifies The list of gizmos, tokats and firildaks in the model
	 */
	public void updateModel(Character key) {
		BoardModel model = BoardModel.getInstance();

		if (key == model.getPlayers().get(0).getLeftKey().toUpperCase().charAt(0)) {
			model.getCezmis().get(0).moveLeft();
		} else if (key == model.getPlayers().get(0).getRightKey().toUpperCase().charAt(0)) {
			model.getCezmis().get(0).moveRight();
		} else if (key == model.getPlayers().get(1).getLeftKey().toUpperCase().charAt(0)) {
			model.getCezmis().get(1).moveLeft();
		} else if (key == model.getPlayers().get(1).getRightKey().toUpperCase().charAt(0)) {
			model.getCezmis().get(1).moveRight();
		} else if (key == model.getPlayers().get(0).getLeftTokatKey().toUpperCase().charAt(0)) {
			for (Gizmo gizmo : model.getGizmos()) {
				if (gizmo instanceof LeftTokat && gizmo.getOwner() == model.getPlayers().get(0)) {
					gizmo.rotate();
				}
			}
		} else if (key == model.getPlayers().get(0).getRightTokatKey().toUpperCase().charAt(0)) {
			for (Gizmo gizmo : model.getGizmos()) {
				if (gizmo instanceof RightTokat && gizmo.getOwner() == model.getPlayers().get(0)) {
					gizmo.rotate();
				}
			}
		} else if (key == model.getPlayers().get(1).getLeftTokatKey().toUpperCase().charAt(0)) {
			for (Gizmo gizmo : model.getGizmos()) {
				if (gizmo instanceof LeftTokat && gizmo.getOwner() == model.getPlayers().get(1)) {
					gizmo.rotate();
				}
			}
		} else if (key == model.getPlayers().get(1).getRightTokatKey().toUpperCase().charAt(0)) {
			for (Gizmo gizmo : model.getGizmos()) {
				if (gizmo instanceof RightTokat && gizmo.getOwner() == model.getPlayers().get(1)) {
					gizmo.rotate();
				}
			}
		}

	}

}
