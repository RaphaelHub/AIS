package com.example.robot;

public class Drive implements Runnable {

	private Controller controller;

	public Drive(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		//MainActivity main = new MainActivity(controller);
		while (true) {
			if (controller.isStopped() == false) {
				//MainActivity.driveFromTo(MainActivity.x_now, MainActivity.y_now, MainActivity.theta_now, 100.0,0.0,0.0);
				MainActivity.drive(50);
			} 
			else {
				// surround object
				MainActivity.turn('l', 90);
				MainActivity.drive(20);
				controller.setStop(false);
			}
		}
	}
}
