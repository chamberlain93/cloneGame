package main;

import gui.GameWindow;

public class Main {

	public static void main(String[] args) {
		GameWindow myGameWindow = GameWindow.getInstance();
		myGameWindow.pack();
		myGameWindow.setVisible(true);
		myGameWindow.setResizable(false);
	}

}
