package com.example.robot;

public class Drive implements Runnable {

	@Override
	public void run() {
		while (true) {
			if (MainActivity.isStopped() == false) {
				 //MainActivity.driveFromTo(MainActivity.x_now,
				 //MainActivity.y_now, MainActivity.theta_now, 200.0,0.0,0.0);
				MainActivity.drive(100);
			} else {
				do {
					MainActivity.turn('l', 90);
				} while (MainActivity.ReadSensorsMain());
				//MainActivity.drive(15);
				MainActivity.setStop(false);
			}
		}
	}
}
