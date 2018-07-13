package thread;

import java.util.Random;

import boardobject.Cezerye;
import enums.ModeEnum;
import enums.StateEnum;
import main.Constants;
import model.BoardModel;

public class CezeryeThread implements Runnable {

	private double waitingCounter = 0;
	private double appearCounter = 0;

	private boolean isInWaitingMode = false;
	private boolean waitFinished = false;
	private double randomWaitTime;

	private Cezerye cezerye;

	@Override
	public void run() {
		BoardModel model = BoardModel.getInstance();
		cezerye = model.getCezerye();
		boolean firstInitialized = (cezerye != null);

		if (firstInitialized) {
			if (cezerye.getTime() != 0) {
				appearCounter = cezerye.getTime();
			} else {
				model.setCezerye(null);
			}
		}

		while (true) {

			if (model.getMode() == ModeEnum.RUNNING_MODE
					&& (model.getState() == StateEnum.PLAY || model.getState() == StateEnum.RESUME)) {
				if (model.getCezerye() == null && !isInWaitingMode) {
					Random rand = new Random();
					randomWaitTime = rand.nextInt(25) + 5;
					isInWaitingMode = true;
					appearCounter = 0;
				} else if (model.getCezerye() == null && isInWaitingMode && !waitFinished) {
					waitingCounter += 0.02;
					appearCounter = 0;
					if (waitingCounter >= randomWaitTime) {
						if (model.getCezerye() == null) {
							cezerye = new Cezerye();
						}
						waitingCounter = 0;
						isInWaitingMode = false;
						waitFinished = true;
					}
				} else if (model.getCezerye() != null) {
					appearCounter += 0.02;
					cezerye.setTime(appearCounter);
					if (appearCounter > 5.0) {
						model.setCezerye(null);
						cezerye.delete();
						appearCounter = 0;
					}
					isInWaitingMode = false;
					waitFinished = false;
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
