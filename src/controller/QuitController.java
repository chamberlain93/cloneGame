package controller;

public class QuitController extends Controller{

	/**
	 * @requires Game to be started.
	 * @effects Quits the game.
	 */
	public void buttonClick() {
		System.exit(0);
	}
}
