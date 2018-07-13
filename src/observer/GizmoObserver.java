package observer;

import boardobject.Observable;
import boardobject.*;
import gui.*;
import model.*;
import enums.*;

public class GizmoObserver extends Observer {

	public GizmoObserver() {

	}

	@Override
	public void update(Observable object) {
		if (BoardModel.getInstance().getMode() == ModeEnum.BUILDING_MODE) {
			if (object instanceof SquareTakoz) {
				GameWindow.getInstance().getBuildingPanel().repaintSquareTakoz((SquareTakoz) object);
			} else {
				if (object instanceof TriangleTakoz) {
					GameWindow.getInstance().getBuildingPanel().repaintTriangleTakoz((TriangleTakoz) object);
				} else {
					if (object instanceof Firildak) {
						GameWindow.getInstance().getBuildingPanel().repaintFirildak((Firildak) object);
					} else {
						if (object instanceof LeftTokat) {
							GameWindow.getInstance().getBuildingPanel().repaintLeftTokat((LeftTokat) object);
						} else {
							if (object instanceof RightTokat) {
								GameWindow.getInstance().getBuildingPanel().repaintRightTokat((RightTokat) object);
							}
						}
					}
				}
			}
		} else {
			if (object instanceof SquareTakoz) {
				GameWindow.getInstance().getBoardPanel().repaintSquareTakoz((SquareTakoz) object);
			} else {
				if (object instanceof TriangleTakoz) {
					GameWindow.getInstance().getBoardPanel().repaintTriangleTakoz((TriangleTakoz) object);
				} else {
					if (object instanceof Firildak) {
						GameWindow.getInstance().getBoardPanel().repaintFirildak((Firildak) object);
					} else {
						if (object instanceof LeftTokat) {
							GameWindow.getInstance().getBoardPanel().repaintLeftTokat((LeftTokat) object);
						} else {
							if (object instanceof RightTokat) {
								GameWindow.getInstance().getBoardPanel().repaintRightTokat((RightTokat) object);
							}
						}
					}
				}
			}
		}

	}

}
