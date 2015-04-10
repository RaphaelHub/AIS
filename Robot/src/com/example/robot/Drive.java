package com.example.robot;

public class Drive implements Runnable {

	private Controller controller;

	public Drive(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		int n = 0;
		while (true) {
			if (controller.getStatus() == false) {
				try {
					MainActivity.driveLongCm(70, controller);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return;
			}
			if (controller.getStatus() == false) {
				try {
					MainActivity.driveANGLE(90);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return;
			}
			n++;

		}

	}

}
