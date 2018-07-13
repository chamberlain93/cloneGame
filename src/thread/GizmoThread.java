package thread;

import boardobject.Firildak;
import boardobject.Gizmo;
import enums.ModeEnum;
import enums.StateEnum;
import main.Constants;
import model.BoardModel;

public class GizmoThread implements Runnable {

	@Override
	public void run() {

		BoardModel model = BoardModel.getInstance();

		while (true) {

			if (model.getMode() == ModeEnum.RUNNING_MODE
					&& (model.getState() == StateEnum.PLAY || model.getState() == StateEnum.RESUME)) {
				for (Gizmo gizmo : model.getGizmos()) {
					if (gizmo instanceof Firildak) {
						gizmo.rotate();
					}
					/*
					 * else if (gizmo instanceof Tokat){ if (((Tokat)
					 * gizmo).getRotateFlag() == true){ gizmo.rotate(); } }
					 */
				}
			}

			try {
				Thread.sleep(1000 / Constants.frameRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
