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
					MainActivity.driveLongCm(100, controller);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					System.out.println("bla1");
					Thread.sleep(1000);
					System.out.println("bla2");
					MainActivity.turnLongDegree(90);
					System.out.println("bla3");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				controller.setStatus(false);
				continue;
			}
//			if (controller.getStatus() == false) {
//				try {
//					MainActivity.driveANGLE(90);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			} else {
//				return;
//			}
			n++;
		}
	}
}
