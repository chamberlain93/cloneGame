package gui;

import controller.Controller;

public class ClickHandler {
	/**
	 * @description 策略模式中的context类
	 * @author chamberlain
	 *
	 */
	Controller controller;
	public ClickHandler(){}
	public void setController(Controller controller){
		this.controller=controller;
	}
	public void buttonClick(){
		controller.buttonClick();
	}

}
