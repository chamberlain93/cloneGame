package thread;

import enums.LevelEnum;
import enums.ModeEnum;
import enums.StateEnum;
import main.Constants;
import model.BoardModel;

public class SecondBallThread implements Runnable {

	@Override
	public void run() {

		BoardModel model = BoardModel.getInstance();

		while (true) {
			if (model.getMode() == ModeEnum.RUNNING_MODE && model.getLevel() == LevelEnum.LEVEL2
					&& model.getBalls().get(1) != null
					&& (model.getState() == StateEnum.PLAY || model.getState() == StateEnum.RESUME)) {
				model.moveBall2();
			}

			try {
				Thread.sleep(1000 / Constants.frameRate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
