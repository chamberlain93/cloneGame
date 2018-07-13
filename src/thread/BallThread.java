package thread;

import enums.ModeEnum;
import enums.StateEnum;
import main.Constants;
import model.BoardModel;

public class BallThread implements Runnable {

	public void run() {

		BoardModel model = BoardModel.getInstance();

		while (true) {
			if (model.getMode() == ModeEnum.RUNNING_MODE && model.getBalls().get(0) != null
					&& (model.getState() == StateEnum.PLAY || model.getState() == StateEnum.RESUME)) {
				model.moveBall1();
			}

			// 50 FPS = 1000 / 50
			try {
				Thread.sleep(1000 / Constants.frameRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
