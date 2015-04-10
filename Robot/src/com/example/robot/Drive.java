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
			if (controller.isStopped() == false) {
				try {
					MainActivity.driveLongCm(100, controller);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(3000);
					controller.setTurnLock(true);
					System.out.println("LOCK" + true);
					MainActivity.turnLongDegree(90);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("LOCK2" + false);
				controller.setStop(false);	//wird nicht gesetzt
				controller.setTurnLock(false);
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
