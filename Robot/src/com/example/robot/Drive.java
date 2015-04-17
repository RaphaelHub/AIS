package com.example.robot;

public class Drive implements Runnable {

	@Override
	public void run() {
		while (!(MainActivity.x_now == 200 && MainActivity.y_now == 200 && MainActivity.theta_now == 0)) {
			System.out.println("inwhile");
			if (!MainActivity.isStopped()) {
				// MainActivity.driveFromTo(MainActivity.x_now,
				// MainActivity.y_now, MainActivity.theta_now, 200.0, 0.0,0.0);
				MainActivity.drive(100);
			} else {
				do {
					MainActivity.turn('l', 90);
				} while (MainActivity.ReadSensorsMain());
				MainActivity.drive(15);
				MainActivity.setStop(false);
			}
		}
		System.out.println("outofwhile");
		MainActivity.comWrite(new byte[] { 'r', '\r', '\n' });
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainActivity.comWrite(new byte[] { 'e', '\r', '\n' });
	}
}
