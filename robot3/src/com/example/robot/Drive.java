package com.example.robot;

public class Drive implements Runnable {

	@Override
	public void run() {
		try{
		while (!(MainActivity.x_now == 200 && MainActivity.y_now == 0 && MainActivity.theta_now == 0)) {
			if (!MainActivity.isStopped()) {
				MainActivity.driveFromTo(MainActivity.x_now,
				MainActivity.y_now, MainActivity.theta_now, 200, 0, 0);
				//MainActivity.drive(200);
			} else {
				do {
					MainActivity.turn(90);
					Thread.sleep(2000);
					
					MainActivity.drive(20, true);
					Thread.sleep(2000);
					MainActivity.turn(-90);
					Thread.sleep(2000);
				} while (MainActivity.ReadSensorsMain());
				MainActivity.setStop(false);
			}
		}
		System.out.println(MainActivity.x_now + " " + 
				MainActivity.y_now + " " + MainActivity.theta_now);
	
	}catch(InterruptedException e){}
	}
}
